package ru.krasilova.otus.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.microservices.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

}
