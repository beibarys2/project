package com.example.studentproject.service;

import com.example.studentproject.enums.Role;
import com.example.studentproject.model.User;
import com.example.studentproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public Optional<User> findByRole(String findRole) {
        return userRepository.findByRole(findRole);
    }

    public long getCount() {
        return userRepository.count();
    }

    public Set<Role> getAllUserRoles() {
        return userRepository.findAll().stream()
                .map(User::getRole)
                .collect(Collectors.toSet());
    }

    public void unblockUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь с ID " + id + " не найден."));
        user.setBlocked(false);
        userRepository.save(user);
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
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setRole(newUser.getRole());
            return userRepository.save(user);
        });
    }

    // Новые методы

    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<User> findByRegistrationDate(String registrationDate) {
        return userRepository.findByRegistrationDate(registrationDate);
    }

    public List<User> findByBlockedStatus(Boolean blocked) {
        return userRepository.findAll().stream()
                .filter(user -> blocked.equals(user.getBlocked()))
                .collect(Collectors.toList());
    }

    public boolean changePassword(Long id, String newPassword) {
        return userRepository.findById(id).map(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    public boolean assignRole(Long id, String role) {
        return userRepository.findById(id).map(user -> {
            user.setRole(Role.valueOf(role));
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    public boolean sendEmail(Long id, String message) {
        // Имитация отправки сообщения
        return userRepository.findById(id).isPresent();
    }
}
