package com.example.studentproject.controller;

import com.example.studentproject.enums.Role;
import com.example.studentproject.model.User;
import com.example.studentproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000/login")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        Optional<User> updatedUser = userService.update(id, newUser);
        return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<User> findUserByName(@RequestParam String name) {
        Optional<User> userOptional = userService.findByName(name);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirmAction(@PathVariable Long id) {
        return ResponseEntity.ok("Операция подтверждена для пользователя с ID: " + id);
    }

    @GetMapping("/searchByLastName")
    public ResponseEntity<User> findByLastName(@RequestParam String lastName) {
        Optional<User> userOptional = userService.findByLastName(lastName);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/searchByRole")
    public ResponseEntity<Optional<User>> findByRole(@RequestParam String role) {
        Optional<User> users = userService.findByRole(role);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUsersCount() {
        long count = userService.getCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getUserRoles() {
        Set<Role> roles = userService.getAllUserRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/block/{id}")
    public ResponseEntity<String> blockUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBlocked(true);
            userService.save(user);
            return ResponseEntity.ok("Пользователь с ID " + id + " успешно заблокирован.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с ID " + id + " не найден.");
        }
    }

    @PostMapping("/unblock/{id}")
    public ResponseEntity<String> unblockUserById(@PathVariable Long id) {
        userService.unblockUserById(id);
        return ResponseEntity.ok("Пользователь с ID " + id + " успешно разблокирован.");
    }

    @GetMapping("/searchByEmail")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/searchByFirstNameAndLastName")
    public ResponseEntity<List<User>> findByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        List<User> users = userService.findByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/searchByRegistrationDate")
    public ResponseEntity<List<User>> findByRegistrationDate(@RequestParam String registrationDate) {
        List<User> users = userService.findByRegistrationDate(registrationDate);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/searchByBlockedStatus")
    public ResponseEntity<List<User>> findByBlockedStatus(@RequestParam Boolean blocked) {
        List<User> users = userService.findByBlockedStatus(blocked);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/changePassword/{id}")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String newPassword) {
        boolean result = userService.changePassword(id, newPassword);
        if (result) {
            return ResponseEntity.ok("Пароль успешно изменен для пользователя с ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с ID " + id + " не найден.");
        }
    }

    @PostMapping("/assignRole/{id}")
    public ResponseEntity<String> assignRole(@PathVariable Long id, @RequestParam String role) {
        boolean result = userService.assignRole(id, role);
        if (result) {
            return ResponseEntity.ok("Роль успешно назначена для пользователя с ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с ID " + id + " не найден.");
        }
    }

    @PostMapping("/sendEmail/{id}")
    public ResponseEntity<String> sendEmail(@PathVariable Long id, @RequestParam String message) {
        boolean result = userService.sendEmail(id, message);
        if (result) {
            return ResponseEntity.ok("Сообщение успешно отправлено пользователю с ID: " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с ID " + id + "не найден.");
        }
    }
}
