package com.example.project1.services;

import java.util.*;

import org.springframework.data.domain.Page;

import com.example.project1.dto.UserDto;
import com.example.project1.entities.*;

public interface UserService {

   void saveUser(UserDto userDto);

   void saveUserforRole(User user);

   User findByEmail(String email);

   List<Role> getAllRole();

   List<User> findAllUsers();

   //List<User> searchUserDetails(String keyword);

   User getUserById(Long id);

   List<User> findUserWithSorting(String field);

   public Page < User > findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword);
 
}
