package pl.edu.pb.wi.serviceInterface;

import pl.edu.pb.wi.dao.entity.RoleEnum;
import pl.edu.pb.wi.dao.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User getUser(Long id);

    User findUser(Long id);

    User giveRole(Long id, RoleEnum role);

    void delete(Long id);

    User save(User user);
}
