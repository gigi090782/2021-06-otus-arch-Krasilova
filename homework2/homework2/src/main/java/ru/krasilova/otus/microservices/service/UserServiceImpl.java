package ru.krasilova.otus.microservices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.microservices.exeptions.UserNotFoundException;
import ru.krasilova.otus.microservices.model.User;
import ru.krasilova.otus.microservices.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User newUser, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setUserName(newUser.getUserName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
    }


    @Override
    public void delete(Long userId) {

        final var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User is not found"));
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User is not found"));
    }
}
