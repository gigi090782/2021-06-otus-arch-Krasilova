package ru.krasilova.otus.microservices.controller;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.krasilova.otus.microservices.model.Order;
import ru.krasilova.otus.microservices.service.OrderService;
import ru.krasilova.otus.microservices.service.TokenService;
import ru.krasilova.otus.microservices.service.VersionService;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final VersionService versionService;
    private final TokenService tokenService;
    private final Counter requestCount;


    public OrderController(OrderService orderService, CollectorRegistry collectorRegistry, CollectorRegistry requestErrorCount, VersionService versionService, TokenService tokenService) {
        this.orderService = orderService;
        this.requestCount = Counter.build()
                .name("request_count")
                .help("Number requests.")
                .register(collectorRegistry);
        this.versionService = versionService;
        this.tokenService = tokenService;
    }


    @GetMapping("/health")
    public ResponseEntity<String> getStatus() {
        requestCount.inc();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/version")
    public Integer getVersion() {
        final var versionOptional = versionService.find();
        if (versionOptional.isPresent()) {
            return versionOptional.get().getVersion();
        }
        return 0;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return orderService.findAll();
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createUser(@RequestBody Order newOrder, HttpServletRequest request) {
        requestCount.inc();
        var httpStatus = HttpStatus.CREATED;
        Order user = null;
        try {
            tokenService.checkToken(request);
            user = orderService.create(newOrder);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(httpStatus).build();
        }
        final var version = versionService.find();
        return ResponseEntity.status(httpStatus)
                .eTag(String.valueOf(version.get().getVersion()))
                .body(user);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> findUserById(@PathVariable Long orderId) {
        requestCount.inc();
        var httpStatus = HttpStatus.OK;
        Order order = null;
        try {
            order = orderService.findById(orderId);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(order);
    }
}
