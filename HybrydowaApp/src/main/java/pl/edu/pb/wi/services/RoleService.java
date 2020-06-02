package pl.edu.pb.wi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.edu.pb.wi.BibliotekaApplication;
import pl.edu.pb.wi.dao.RoleRepository;
import pl.edu.pb.wi.dao.entity.ERole;
import pl.edu.pb.wi.dao.entity.Role;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        save(new Role(ERole.ROLE_USER));
        save(new Role(ERole.ROLE_ADMIN));
    }
}
