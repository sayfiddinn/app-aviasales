package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.RoleDTO;
import uz.pdp.appaviauz.service.RoleService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "role-controller", description = "Role controller management, admin and above can access")
@SecurityRequirement(name = "bearerAuth")
public class RoleControllerImpl implements RoleController {
    private final RoleService roleService;
    static final String ID = "/{id}";
    static final String USER_ID = "/{user_id}";

    @Override
    public HttpEntity<?> getRoles() {
        ResultMessage result = roleService.getRoles();
        return ResponseEntity
                .status(result.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND)
                .body(result.getObject());
    }

    @Override
    public HttpEntity<?> getRole(Integer id) {
        ResultMessage result = roleService.getRole(id);
        return ResponseEntity.ok(result.getObject());
    }

    @Override
    public HttpEntity<?> addRole(RoleDTO roleDTO) {
        ResultMessage result = roleService.addRole(roleDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @Override
    public HttpEntity<?> updateRole(RoleDTO roleDTO, Integer id) {
        ResultMessage result = roleService.updateRole(roleDTO, id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(result);
    }

    @Override
    public HttpEntity<?> deleteRole(Integer id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }

    @Override
    public HttpEntity<?> changeRole(UUID userId, Integer roleId) {
        ResultMessage result = roleService.changeRole(userId, roleId);
        return ResponseEntity
                .status(result.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(result);
    }
}
