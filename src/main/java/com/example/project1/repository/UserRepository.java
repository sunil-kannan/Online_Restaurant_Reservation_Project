package com.example.project1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project1.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User getUserById(Long id);

    @Query(value = "SELECT * FROM users p  WHERE p.name LIKE %:keyword% or p.email LIKE %:keyword% or p.phone_no LIKE %:keyword% ", nativeQuery = true)
    Page<User> searchUserDetails(String keyword, PageRequest pageable);
}
