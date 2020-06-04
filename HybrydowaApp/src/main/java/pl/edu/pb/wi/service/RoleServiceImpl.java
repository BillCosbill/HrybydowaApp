package pl.edu.pb.wi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.dao.RoleRepository;
import pl.edu.pb.wi.dao.entity.Role;
import pl.edu.pb.wi.dao.entity.RoleEnum;
import pl.edu.pb.wi.serviceInterface.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {
        save(new Role(RoleEnum.ROLE_USER));
        save(new Role(RoleEnum.ROLE_ADMIN));
    }
}
