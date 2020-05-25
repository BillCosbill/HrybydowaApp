package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

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
    //TODO CASCADE - NIE DZIAŁA USUWANIE UŻYTKOWNIKÓW, BO SĄ POŁĄCZENI Z TABELĄ BOOK I ROLE
    @GetMapping("/userDelete")
    void deleteUser(@RequestParam long id){
        System.out.println("SiEMA");
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
