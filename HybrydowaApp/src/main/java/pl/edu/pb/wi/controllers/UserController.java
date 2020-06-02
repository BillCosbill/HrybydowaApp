package pl.edu.pb.wi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pb.wi.dao.entity.User;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.services.UserService;

import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping
    public User getUser(@RequestParam Long id){
        return userService.getUser(id);
    }

    @GetMapping("/findUser")
    public User findUser(@RequestParam Long id){
        return userService.findUser(id);
    }

    @PutMapping("/giveAdmin")
    public User giveAdmin(@RequestParam Long id){
        return userService.giveAdmin(id);
    }

    @PutMapping("/giveUser")
    public User giveUser(@RequestParam Long id){
        return userService.giveUser(id);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam Long id){
        userService.delete(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.save(user);
    }
}
