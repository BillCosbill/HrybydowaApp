package pl.edu.pb.wi.dao;

import pl.edu.pb.wi.dao.entity.ERole;
import pl.edu.pb.wi.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
