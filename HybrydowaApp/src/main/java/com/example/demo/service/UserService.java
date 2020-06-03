package com.example.demo.service;

import com.example.demo.exeption.RoleNotFoundExeption;
import com.example.demo.model.Role;
import com.example.demo.model.RoleEnum;
import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void giveUserRole(User user, RoleEnum role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(role).get());
        user.setRoles(roles);

        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void register(SignupRequest signupRequest) throws RoleNotFoundExeption {
        if (signupRequest.getPassword().isEmpty()) {
            signupRequest.setPassword(generatePassword());
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        AtomicBoolean isRoleFound = new AtomicBoolean(true);

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundExeption("Error: Role RoleEnum.ROLE_USER does not exist."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                RoleEnum roleEnum;
                switch (role) {
                    case "admin":
                        roleEnum = RoleEnum.ROLE_ADMIN;
                        break;
                    case "user":
                        roleEnum = RoleEnum.ROLE_USER;
                        break;
                    default:
                        roleEnum = RoleEnum.ROLE_USER;
                }

                Role userRole = null;
                try {
                    userRole = roleRepository.findByName(roleEnum)
                            .orElseThrow(() -> new RoleNotFoundExeption("Error: Role RoleEnum.ROLE_USER does not exist."));
                } catch (RoleNotFoundExeption roleNotFoundExeption) {
                    isRoleFound.set(false);
                }
                roles.add(userRole);
            });
        }

        if (!isRoleFound.get()) throw new RoleNotFoundExeption("Role does not exist.");

        user.setRoles(roles);
        userRepository.save(user);
    }

    private String generatePassword() {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            password.append(random.nextInt(90) + 35);
        }
        return password.toString();
    }
}
