package ru.krasilova.otus.microservices.service;


import java.util.List;
import ru.krasilova.otus.microservices.model.Order;

public interface OrderService {

    Order create(Order order);

    Order findById(Long id);

    List<Order> findAll();
}
