package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appaviauz.payload.RoleDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.RoleControllerImpl.ID;
import static uz.pdp.appaviauz.controller.RoleControllerImpl.USER_ID;


@RequestMapping(Path.BASE_PATH_ROLE)
public interface RoleController {

    @PreAuthorize("hasAuthority('SHOW_ROLE')")
    @GetMapping
    HttpEntity<?> getRoles();

    @PreAuthorize("hasAuthority('SHOW_ROLE')")
    @GetMapping(ID)
    HttpEntity<?> getRole(@PathVariable Integer id);

    @PreAuthorize("hasAuthority('ADD_ROLE')")
    @PostMapping
    HttpEntity<?> addRole(@RequestBody RoleDTO roleDTO);
    @Operation(summary = "Only super admin can access")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    @PutMapping(ID)
    HttpEntity<?> updateRole(@RequestBody RoleDTO roleDTO,
                             @PathVariable Integer id);
    @Operation(summary = "Only super admin can access")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping(ID)
    HttpEntity<?> deleteRole(@PathVariable Integer id);

    @PreAuthorize("hasAuthority('CHANGE_ROLE')")
    @PostMapping(USER_ID)
    HttpEntity<?> changeRole(@PathVariable(value = "user_id") UUID userId,
                             @RequestParam(value = "role_id") Integer roleId);

}
