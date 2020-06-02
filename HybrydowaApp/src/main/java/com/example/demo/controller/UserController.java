package com.example.demo.controller;

import com.example.demo.model.ERole;
import com.example.demo.model.User;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

//    @Autowired
//    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            System.out.println("Brak użytkownika o podanym id");
            return null;
        } else {
            return user.get();
        }
    }

    @GetMapping("/findUser")
    public User findUser(@RequestParam long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            System.out.println("Brak użytkownika o podanym id");
            return null;
        } else {
            return user.get();
        }
    }

    @GetMapping("/giveAdmin")
    ResponseEntity<Object> giveAdmin(@RequestParam long id) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            userService.giveUserRole(optionalUser.get(), ERole.ROLE_ADMIN);
            return ResponseEntity.ok().body(new MessageResponse("User '" + optionalUser.get() + "' has admin role now."));
        }
        return ResponseEntity.notFound().header("User with id " + id + " not found.").build();
    }

    @GetMapping("/giveUser")
    ResponseEntity<Object> giveUser(@RequestParam long id) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            userService.giveUserRole(optionalUser.get(), ERole.ROLE_USER);
            return ResponseEntity.ok().body(new MessageResponse("User '" + optionalUser.get() + "' has user role now."));
        }
        return ResponseEntity.notFound().header("User with id " + id + " not found.").build();
    }

    @GetMapping("/userDelete")
    ResponseEntity<Object> deleteUser(@RequestParam long id) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            userService.deleteUser(optionalUser.get());
            return ResponseEntity.ok().body(new MessageResponse("User '" + optionalUser.get() + "' was successfuly deleted."));
        } else {
            return ResponseEntity.notFound().header("User with id " + id + " not found.").build();
        }
    }

//    @PostMapping("/user/add")
//    ResponseEntity<?> addUser(@Valid @RequestBody SignupRequest signupRequest) {
//        if (userService.existsByUsername(signupRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userService.existsByEmail(signupRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        if (signupRequest.getPassword().isEmpty()) {
//            //todo generate password
//        }
//
//        User user = new User(signupRequest.getUsername(),
//                signupRequest.getEmail(),
//                encoder.encode(signupRequest.getPassword()));
//
//        Set<String> strRoles = signupRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        userService.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
}
