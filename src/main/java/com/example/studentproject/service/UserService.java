package com.example.studentproject.service;

import com.example.studentproject.model.User;
import com.example.studentproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    public Optional<User> update(Long id, User newUser) {
        return userRepository.findById(id).map(user -> {
            // Обновляем поля пользователя
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword())); // Хешируем новый пароль
            user.setRole(newUser.getRole());
            return userRepository.save(user);
        });
    }
}
