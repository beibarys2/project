package com.example.studentproject.repository;

import com.example.studentproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByLastName(String lastName);
    Optional<User> findByRole(String findRole);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByRegistrationDate(String registrationDate);

    @Override
    long count();
}
