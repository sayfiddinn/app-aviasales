package uz.pdp.appaviauz.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appaviauz.payload.SignDTO;
import uz.pdp.appaviauz.payload.SignUpDTO;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.AuthControllerImpl.*;

@RequestMapping(Path.BASE_PATH_AUTH)
public interface AuthController {
    @PostMapping(SIGN_UP)
    ResponseEntity<?> signUp(@RequestBody
                             @Valid SignUpDTO signUpDTO);

    @PostMapping(SIGN_IN)
    ResponseEntity<?> signIn(@RequestBody SignDTO signDTO);

    @GetMapping(CHECK_EMAIL)
    ResponseEntity<?> checkEmail(
            @RequestParam(name = "user_id") UUID userId
            , @RequestParam(name = "code") String code);

    @GetMapping(CHECK_MODER)
    ResponseEntity<?> checkModerator(
            @RequestParam(name = "user_id") UUID userId
            , @RequestParam(name = "email") String email);
}
