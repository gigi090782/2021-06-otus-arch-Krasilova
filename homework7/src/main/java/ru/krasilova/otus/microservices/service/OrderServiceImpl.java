package ru.krasilova.otus.microservices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.microservices.exeptions.OrderNotFoundException;
import ru.krasilova.otus.microservices.model.Order;
import ru.krasilova.otus.microservices.model.Version;
import ru.krasilova.otus.microservices.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final VersionService versionService;

    public OrderServiceImpl(OrderRepository orderRepository, VersionService versionService) {
        this.orderRepository = orderRepository;
        this.versionService = versionService;
    }

    @Override
    public Order create(Order order) {
        final var versionOptional = versionService.find();
        Version version;
        if (!versionOptional.isPresent()) {
            version = new Version();
        } else {
            version = versionOptional.get();
        }
        version.setVersion(version.getVersion() + 1);
        versionService.save(version);
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Заказ не найден!"));
    }


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}
