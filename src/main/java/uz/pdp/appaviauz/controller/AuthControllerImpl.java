package uz.pdp.appaviauz.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.SignDTO;
import uz.pdp.appaviauz.payload.SignUpDTO;
import uz.pdp.appaviauz.service.AdminService;
import uz.pdp.appaviauz.service.AuthService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "auth-controller", description = "Auth controller management")
public class AuthControllerImpl implements AuthController {
    static final String SIGN_UP = "/signUp";
    static final String SIGN_IN = "/signIn";
    static final String CHECK_EMAIL = "/checkEmail";
    static final String CHECK_MODER = "/checkModerator";
    private final AuthService authService;
    private final AdminService adminService;

    @Override
    public ResponseEntity<?> signUp(@Valid SignUpDTO signUpDTO) {
        ResultMessage resultMessage = authService.signUp(signUpDTO);
        return ResponseEntity.status(
                resultMessage.getSuccess() ?
                        HttpStatus.OK :
                        HttpStatus.CONFLICT
        ).body(resultMessage);
    }


    @Override
    public ResponseEntity<?> signIn(SignDTO signDTO) {
        ResultMessage resultMessage = authService.signIn(signDTO);
        return ResponseEntity.status(
                resultMessage.getSuccess() ?
                        HttpStatus.ACCEPTED :
                        HttpStatus.CONFLICT
        ).body(resultMessage);
    }

    @Override
    public ResponseEntity<?> checkEmail(UUID userId, String code) {
        ResultMessage resultMessage = authService.checkEmail(userId, code);
        return ResponseEntity.status(
                resultMessage.getSuccess() ?
                        HttpStatus.OK :
                        HttpStatus.CONFLICT
        ).body(resultMessage);
    }

    @Override
    public ResponseEntity<?> checkModerator(UUID userId, String email) {
        ResultMessage resultMessage = adminService.checkModerator(userId, email);
        return ResponseEntity.status(
                resultMessage.getSuccess() ?
                        HttpStatus.OK :
                        HttpStatus.CONFLICT
        ).body(resultMessage);
    }
}
