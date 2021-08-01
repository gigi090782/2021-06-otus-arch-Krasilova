package ru.krasilova.otus.microservices.controller;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.krasilova.otus.microservices.model.User;
import ru.krasilova.otus.microservices.service.UserService;

@RestController
public class UserController {

    private final UserService userService;
    private final Counter requestCount;
    private final Counter requestErrorCount;

    public UserController(UserService userService, CollectorRegistry collectorRegistry, CollectorRegistry requestErrorCount) {
        this.userService = userService;
        this.requestCount = Counter.build()
                .name("request_count")
                .help("Number requests.")
                .register(collectorRegistry);
        this.requestErrorCount = Counter.build()
                .name("request_error_count")
                .help("Number of error requests.")
                .register(requestErrorCount);
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        requestCount.inc();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/error")
    public ResponseEntity<String> getError() {
        requestErrorCount.inc();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        requestCount.inc();
        var httpStatus = HttpStatus.CREATED;
        User user = null;
        try {
            user = userService.create(newUser);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(httpStatus).build();
        }
        return ResponseEntity.status(httpStatus).body(user);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User>  findUserById(@PathVariable Long userId) {
        requestCount.inc();
        var httpStatus = HttpStatus.OK;
        User user = null;
        try {
            user = userService.findById(userId);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(user);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        requestCount.inc();
        var httpStatus = HttpStatus.NO_CONTENT;
        try {
            userService.delete(userId);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).build();
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable Long userId) {
        requestCount.inc();
        var httpStatus = HttpStatus.OK;
        User user = null;
        try {
            user = userService.update(newUser, userId);
        } catch (Exception ex) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(user);
    }

}
