package pl.edu.pb.wi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.dao.RoleRepository;
import pl.edu.pb.wi.dao.UserRepository;
import pl.edu.pb.wi.dao.entity.ERole;
import pl.edu.pb.wi.dao.entity.Role;
import pl.edu.pb.wi.dao.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User findUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User giveAdmin(Long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleRepository.findByName(ERole.ROLE_ADMIN);
            role.ifPresent(roles::add);
            user.get().setRoles(roles);

            userRepository.save(user.get());
            return user.get();
        }

        return null;
    }

    public User giveUser(Long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleRepository.findByName(ERole.ROLE_USER);
            role.ifPresent(roles::add);
            user.get().setRoles(roles);

            userRepository.save(user.get());
            return user.get();
        }

        return null;
    }

    public void delete(Long id){
        userRepository.findById(id).ifPresent(value -> userRepository.delete(value));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        save(new User("Daniel", "d.demczuk@student.pb.edu.pl", "$2a$10$hrI96sTm9sZnxtIGg.9skOx9kKmxiK.ydw4oZ5vLEjcsFCeRqKx0u")); //Daniel1
        save(new User("Tomek", "t.szeligowski@student.pb.edu.pl", "$2a$10$eqJtCNTtqulO.wSKc3T5S.oRDFqjE5HQ14JyFG5Iwwh3uDbDFY37m")); //Tomasz1
        save(new User("Paweł", "p.raglis@student.pb.edu.pl", "$2a$10$Jux1QwQWj12VmNtNu9g9KukU0WPlsm3Wxi2KhuAlAJ6SToxQe1WNK")); //Pawel1
        save(new User("Alicja", "a.karczewska@student.pb.edu.pl", "$2a$10$k8T4k0GgidxTHR7xQPM5pe/oF2v4aCDjgb6PROj0aTIFm2JXGHjw2")); //Alicja1

        giveAdmin(4L);
        giveAdmin(1L);
        giveUser(2L);
        giveUser(3L);
    }
}
