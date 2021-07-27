package ru.krasilova.otus.microservices.controller;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
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
