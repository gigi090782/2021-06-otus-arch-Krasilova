package ru.krasilova.otus.microservices.service;


import java.util.List;
import ru.krasilova.otus.microservices.model.User;

public interface UserService {

    User create(User user);

    User update(User user, Long userId);

    void delete(Long userId);

    List<User> findAll();

    User findById(Long userId);
}
