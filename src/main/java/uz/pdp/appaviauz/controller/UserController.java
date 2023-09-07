package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.payload.PasswordDTO;
import uz.pdp.appaviauz.payload.UserDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.UserControllerImpl.*;

@PreAuthorize("hasAnyAuthority('SHOW_PROFILE','EDITE_PROFILE','DELETE_PROFILE','SHOW_FLIGHT_HISTORY','SHOW_TICKET_HISTORY')")
@RequestMapping(Path.BASE_PATH_USER)
public interface UserController {
    @GetMapping(PROFILE_INFO)
    ResponseEntity<?> showProfile();

    @PutMapping(EDIT_PROFILE_INFO)
    ResponseEntity<?> editProfile(@RequestBody UserDTO userDTO);

    @DeleteMapping(DELETE_PROFILE)
    ResponseEntity<?> deleteProfile();

    @GetMapping(SHOW_FLIGHT_HISTORY)
    ResponseEntity<?> showFlightHistory();

    @GetMapping(SHOW_TICKET_HISTORY)
    ResponseEntity<?> showTicketHistory();

    @PostMapping(CHANGE_PASSWORD)
    ResponseEntity<?> changePassword(@RequestBody PasswordDTO passwordDTO);
    @Operation(summary = "Upload a file",description = Path.FILE_DESCRIPTION)
    @PostMapping(value = add_file, consumes = {"multipart/form-data"})
    ResponseEntity<?> addFile(@RequestParam(value = "file") MultipartFile multipartFile);


    @GetMapping(show_file)
    ResponseEntity<?> showFile();

    @DeleteMapping(delete_file)
    ResponseEntity<?> deleteFile(@PathVariable UUID file_id);


    @Operation(summary = "Upload a file",description = Path.FILE_DESCRIPTION)
    @PutMapping(value = update_file, consumes = {"multipart/form-data"})
    ResponseEntity<?> updateFile(@PathVariable UUID file_id,
                                 @RequestParam(value = "file") MultipartFile multipartFile);
}
