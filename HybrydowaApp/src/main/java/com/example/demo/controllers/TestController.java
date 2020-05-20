package com.example.demo.controllers;

import com.example.demo.models.*;

import com.example.demo.repository.BookRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
/*
    BookRepository bookRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public TestController(BookRepository bookRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        Role role1 = new Role(ERole.ROLE_USER);
        Role role2 = new Role(ERole.ROLE_ADMIN);

        Set<Role> user = new HashSet<>();
        Set<Role> admin = new HashSet<>();
        user.add(role1);
        admin.add(role2);
        roleRepository.save(role1);
        roleRepository.save(role2);

        Book book1 = new Book("Pan Tadeusz", "Adam Mickiewicz");
        Book book2 = new Book("Wódka", "Zbigniew Wódecki");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = new ArrayList<>();
        books.add(book1);

        User user1 = new User("user","user@onet.pl","123", books);
        User user2 = new User("admin","admin@onet.pl","123");
        user1.setRoles(user);
        user1.setRoles(admin);

        userRepository.save(user1);
        userRepository.save(user2);




    }
*/
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
