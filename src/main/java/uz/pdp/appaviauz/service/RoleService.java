package uz.pdp.appaviauz.service;

import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.RoleDTO;

import java.util.UUID;


public interface RoleService {
    ResultMessage getRoles();

    ResultMessage getRole(Integer id);

    ResultMessage addRole(RoleDTO roleDTO);

    ResultMessage updateRole(RoleDTO roleDTO, Integer id);

    ResultMessage deleteRole(Integer id);

    ResultMessage changeRole(UUID userId, Integer roleId);
}
