package pl.edu.pb.wi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.dao.RoleRepository;
import pl.edu.pb.wi.dao.UserRepository;
import pl.edu.pb.wi.dao.entity.Role;
import pl.edu.pb.wi.dao.entity.RoleEnum;
import pl.edu.pb.wi.dao.entity.User;
import pl.edu.pb.wi.exception.RoleNotFoundException;
import pl.edu.pb.wi.payload.request.SignupRequest;
import pl.edu.pb.wi.serviceInterface.UserService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void register(SignupRequest signupRequest) throws RoleNotFoundException {
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
                    .orElseThrow(() -> new RoleNotFoundException("Error: Role RoleEnum.ROLE_USER does not exist."));
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
                            .orElseThrow(() -> new RoleNotFoundException("Error: Role RoleEnum.ROLE_USER does not exist."));
                } catch (RoleNotFoundException roleNotFoundExeption) {
                    isRoleFound.set(false);
                }
                roles.add(userRole);
            });
        }

        if (!isRoleFound.get()) throw new RoleNotFoundException("Role does not exist.");

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

    public User giveRole(Long id, RoleEnum roleEnum) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleRepository.findByName(roleEnum);

            role.ifPresent(roles::add);
            user.setRoles(roles);
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public void delete(Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    public User save(User user) {
        if (user.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleRepository.findByName(RoleEnum.ROLE_USER);

            role.ifPresent(roles::add);
            user.setRoles(roles);
        }
        return userRepository.save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {
        save(new User("Daniel", "d.demczuk@student.pb.edu.pl", "$2a$10$hrI96sTm9sZnxtIGg.9skOx9kKmxiK.ydw4oZ5vLEjcsFCeRqKx0u")); //Daniel1
        save(new User("Tomek", "t.szeligowski@student.pb.edu.pl", "$2a$10$eqJtCNTtqulO.wSKc3T5S.oRDFqjE5HQ14JyFG5Iwwh3uDbDFY37m")); //Tomasz1
        save(new User("Paweł", "p.raglis@student.pb.edu.pl", "$2a$10$Jux1QwQWj12VmNtNu9g9KukU0WPlsm3Wxi2KhuAlAJ6SToxQe1WNK")); //Pawel1
        save(new User("Alicja", "a.karczewska@student.pb.edu.pl", "$2a$10$k8T4k0GgidxTHR7xQPM5pe/oF2v4aCDjgb6PROj0aTIFm2JXGHjw2")); //Alicja1

        giveRole(4L, RoleEnum.ROLE_ADMIN);
        giveRole(1L, RoleEnum.ROLE_ADMIN);
        giveRole(2L, RoleEnum.ROLE_USER);
        giveRole(3L, RoleEnum.ROLE_USER);
    }

}
