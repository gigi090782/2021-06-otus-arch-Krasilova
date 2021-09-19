package ru.krasilova.otus.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.microservices.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
