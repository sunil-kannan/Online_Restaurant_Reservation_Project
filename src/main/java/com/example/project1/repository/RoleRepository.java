package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entities.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
   
}
