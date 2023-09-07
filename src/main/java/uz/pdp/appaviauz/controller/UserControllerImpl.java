package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.payload.PasswordDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.UserDTO;
import uz.pdp.appaviauz.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "user-controller", description = "User controller management")
@SecurityRequirement(name = "bearerAuth")
public class UserControllerImpl implements UserController {

    static final String PROFILE_INFO = "/me";
    static final String EDIT_PROFILE_INFO = "/editProfile";
    static final String DELETE_PROFILE = "/deleteProfile";
    static final String SHOW_FLIGHT_HISTORY = "/showFlight";
    static final String SHOW_TICKET_HISTORY = "/showTickets";
    static final String CHANGE_PASSWORD = "/change_password";
    static final String add_file = "/add_file";
    static final String show_file = "/show_file";
    static final String delete_file = "/delete_file/{file_id}";
    static final String update_file = "/update_file/{file_id}";


    private final UserService userService;

    @Override
    public ResponseEntity<?> showProfile() {
        return ResponseEntity.ok(userService.showProfile());
    }

    @Override
    public ResponseEntity<?> editProfile(UserDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(userService.editProfile(userDTO));
    }

    @Override
    public ResponseEntity<?> deleteProfile() {
        return ResponseEntity.ok(userService.deleteProfile());
    }

    @Override
    public ResponseEntity<?> showFlightHistory() {
        ResultMessage resultMessage = userService.showFlightHistory();
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND)
                .body(resultMessage.getObject());
    }

    @Override
    public ResponseEntity<?> showTicketHistory() {
        ResultMessage resultMessage = userService.showTicketHistory();
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND)
                .body(resultMessage.getObject());
    }

    @Override
    public ResponseEntity<?> changePassword(PasswordDTO passwordDTO) {
        ResultMessage resultMessage = userService.changePassword(passwordDTO);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_ACCEPTABLE)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> addFile(MultipartFile multipartFile) {
        ResultMessage resultMessage = userService.addFile(multipartFile);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.CREATED
                        : HttpStatus.CONFLICT)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> showFile() {
        ResultMessage resultMessage = userService.showFile();
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND)
                .body(resultMessage.getObject());
    }

    @Override
    public ResponseEntity<?> deleteFile(UUID file_id) {
        ResultMessage resultMessage = userService.deleteFile(file_id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_ACCEPTABLE)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> updateFile(UUID file_id, MultipartFile multipartFile) {
        ResultMessage resultMessage = userService.updateFile(file_id,multipartFile);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.ACCEPTED
                        : HttpStatus.NOT_MODIFIED)
                .body(resultMessage);
    }
}
