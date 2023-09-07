package uz.pdp.appaviauz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appaviauz.entity.Role;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleType(RoleTypeEnum roleType);
}
