package com.example.project1.services.servicesImpl;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project1.dto.UserDto;
import com.example.project1.entities.*;
import com.example.project1.repository.*;
import com.example.project1.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPhoneNo(userDto.getPhoneNo());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }
  
    @Override
    public void saveUserforRole(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll(Sort.by("id"));     
    }

    @Override
    public List<User> findUserWithSorting(String field){
        return this.userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }  

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword) {
      Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
            PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        return this.userRepository.searchUserDetails(keyword, pageable);
    }
}

