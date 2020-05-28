package com.example.demo.controllers;

import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            System.out.println("Brak użytkownika o podanym id");
            return null;
        } else {
            return user.get();
        }
    }

    @GetMapping("/findUser")
    public User findUser(@RequestParam long id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            System.out.println("Brak użytkownika o podanym id");
            return null;
        } else {
            return user.get();
        }
    }

    @GetMapping("/giveAdmin")
    void giveAdmin(@RequestParam long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
            user.get().setRoles(roles);

            userRepository.save(user.get());
            System.out.println("Nadano prawa administratora dla " + user.get().getUsername());
        }
    }

    @GetMapping("/giveUser")
    void giveUser(@RequestParam long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_USER).get());
            user.get().setRoles(roles);

            userRepository.save(user.get());
            System.out.println("Nadano prawa użytkownika dla " + user.get().getUsername());
        }
    }

    @GetMapping("/userDelete")
    void deleteUser(@RequestParam long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            userRepository.delete(user.get());
            System.out.println("Pomyślnie usunięto użytkownika");
        }
        else {
            System.out.println("Nie znaleziono użytkownika poprzez id");
        }
    }
}
