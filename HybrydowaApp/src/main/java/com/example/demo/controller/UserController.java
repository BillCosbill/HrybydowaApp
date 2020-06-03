package com.example.demo.controller;

import com.example.demo.exeption.RoleNotFoundExeption;
import com.example.demo.model.RoleEnum;
import com.example.demo.model.User;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

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
            userService.giveUserRole(optionalUser.get(), RoleEnum.ROLE_ADMIN);
            return ResponseEntity.ok().body(new MessageResponse("User '" + optionalUser.get() + "' has admin role now."));
        }
        return ResponseEntity.notFound().header("User with id " + id + " not found.").build();
    }

    @GetMapping("/giveUser")
    ResponseEntity<Object> giveUser(@RequestParam long id) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            userService.giveUserRole(optionalUser.get(), RoleEnum.ROLE_USER);
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

    @PostMapping("/user/add")
    ResponseEntity<Object> addUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        try {
            userService.register(signupRequest);
        } catch (RoleNotFoundExeption e) {
            return ResponseEntity.notFound().header(e.getMessage()).build();
        }

        return ResponseEntity.ok(new MessageResponse("User '" + signupRequest.getUsername() + "' was registered successfully!"));
    }
}
