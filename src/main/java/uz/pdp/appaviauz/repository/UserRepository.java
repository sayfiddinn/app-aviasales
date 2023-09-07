package uz.pdp.appaviauz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findAllByRole_RoleType(RoleTypeEnum role_roleType, Pageable pageable);

    Long countAllByRole_RoleType(RoleTypeEnum role_roleType);

    Optional<User> findByIdAndRole_RoleType(UUID id, RoleTypeEnum role_roleType);

    @Query(nativeQuery = true, value = "select count(*)!=0 from (select id from users where is_deleted=false and phone_number=?1) as a")
    boolean existNumberAndNonDeleted(String phoneNumber);

    List<User> findAllByRoleId(Integer role_id);
    @Query(nativeQuery = true,value =
            "select * from users where role_id in " +
            "(select r.id from role r where r.role_type !=?1)")
    List<User> findAllByRoleTypeNon(String roleType, Pageable creationTimestamp);
    @Query(nativeQuery = true,value =
            "select count(*) from users where role_id in " +
            "(select r.id from role r where r.role_type !=?1)")
    Long countAllByRoleTypeNon(String roleType);

}
