package uz.pdp.appaviauz.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.Role;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.enums.Authority;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.mapper.MyMapper;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.RoleDTO;
import uz.pdp.appaviauz.repository.RoleRepository;
import uz.pdp.appaviauz.repository.UserRepository;
import uz.pdp.appaviauz.util.Utils;

import java.util.*;

import static uz.pdp.appaviauz.util.Path.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BaseService baseService;
    private final MyMapper mapper;

    @Override
    public ResultMessage getRoles() {
        List<Role> all = roleRepository.findAll();
        return new ResultMessage(!all.isEmpty(), all);
    }

    @Override
    public ResultMessage getRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role" + NOT_FOUND));
        return new ResultMessage(true, role);
    }

    @Override
    public ResultMessage addRole(RoleDTO roleDTO) {
        checkAndSave(new Role(RoleTypeEnum.OTHER), roleDTO);
        return new ResultMessage(true, SAVED);
    }

    @Override
    public ResultMessage updateRole(RoleDTO roleDTO, Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role" + NOT_FOUND));
        checkRoleType(role.getRoleType());
        checkAndSave(role, roleDTO);
        return new ResultMessage(true, UPDATED);
    }

    private void checkRoleType(RoleTypeEnum roleType) {
        if (!Objects.equals(roleType, RoleTypeEnum.OTHER)) {
            throw new ConflictException("This role can't be deleted and changed");
        }
    }

    private void checkAndSave(Role role, RoleDTO roleDTO) {
        Set<Authority> authorities = roleDTO.getAuthorities();
        Set<Authority> userAuthorities = baseService.getUser().getRole().getAuthorities();
        System.out.println("userAuthorities = " + userAuthorities);
        System.out.println("authorities = " + authorities);
        if (authorities.containsAll(userAuthorities)) {
            throw new ConflictException("You can't create a role with " +
                    "authorities that you do not have");
        }
        mapper.mappingRole(roleDTO, role);
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public ResultMessage deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role" + NOT_FOUND));
        checkRoleType(role.getRoleType());
        List<User> users = userRepository.findAllByRoleId(id);
        Role userRole = roleRepository.findByRoleType(RoleTypeEnum.USER)
                .orElseThrow(() -> new ConflictException("Some wrong"));
        for (User user : users) {
            user.setRole(userRole);
        }
        userRepository.saveAll(users);
        roleRepository.delete(role);
        return new ResultMessage(true, DELETED);
    }

    @Override
    public ResultMessage changeRole(UUID userId, Integer roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User" + NOT_FOUND));
        if (Objects.equals(user.getId(), baseService.getUser().getId()))
            return new ResultMessage(false, "You can't change your role");
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role" + NOT_FOUND));
        if (baseService.getUser().getRole().getAuthorities().contains(Authority.DELETE_ROLE)) {

            if (Objects.equals(user.getRole().getRoleType(), RoleTypeEnum.SUPER_ADMIN) ||
                    !new HashSet<>(Utils.adminAuthority).containsAll(role.getAuthorities())) {
                throw new ConflictException(NOT_ALLOWED);
            }
        } else {

            if (Objects.equals(user.getRole().getRoleType(), RoleTypeEnum.ADMIN)
                    || !new HashSet<>(Utils.moderAuthority).containsAll(role.getAuthorities())
                    || user.getRole().getAuthorities().size() > Utils.moderAuthority.size()) {
                throw new ConflictException(NOT_ALLOWED);
            }
        }
        user.setRole(role);
        userRepository.save(user);
        return new ResultMessage(true, "Role changed successfully");
    }
}
