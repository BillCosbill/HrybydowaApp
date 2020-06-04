package pl.edu.pb.wi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.dao.entity.User;
import pl.edu.pb.wi.payload.request.RoleChangeRequest;
import pl.edu.pb.wi.serviceInterface.UserService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/find")
    public User findUser(@RequestParam Long id) {
        return userService.findById(id);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/give")
    public User giveAdmin(@RequestBody RoleChangeRequest roleChangeRequest) {
        return userService.giveRole(roleChangeRequest.getId(), roleChangeRequest.getRole());
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam Long id) {
        userService.delete(id);
    }
}
