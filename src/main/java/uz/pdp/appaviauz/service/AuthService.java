package uz.pdp.appaviauz.service;

import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.SignDTO;
import uz.pdp.appaviauz.payload.SignUpDTO;

import java.util.UUID;

public interface AuthService {
    ResultMessage signUp(SignUpDTO signUpDTO);

    ResultMessage signIn(SignDTO signDTO);

    ResultMessage checkEmail(UUID id, String code);
}
