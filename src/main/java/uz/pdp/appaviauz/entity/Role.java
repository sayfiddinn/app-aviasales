package uz.pdp.appaviauz.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appaviauz.entity.base.AbsIntEntity;
import uz.pdp.appaviauz.entity.enums.Authority;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Role extends AbsIntEntity {
    @CollectionTable(uniqueConstraints = {
            @UniqueConstraint(columnNames = {"role_id", "authorities"})})
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, updatable = false)
    private RoleTypeEnum roleType;

    public Role(RoleTypeEnum roleType) {
        this.roleType = roleType;
    }

}
