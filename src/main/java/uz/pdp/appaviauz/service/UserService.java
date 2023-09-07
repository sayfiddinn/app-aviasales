package uz.pdp.appaviauz.service;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.payload.PasswordDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.UserDTO;

import java.util.UUID;

public interface UserService {
    ResultMessage showProfile();

    ResultMessage editProfile(UserDTO userDTO);

    ResultMessage deleteProfile();

    ResultMessage showFlightHistory();

    ResultMessage showTicketHistory();

    ResultMessage changePassword(PasswordDTO passwordDTO);

    ResultMessage addFile(MultipartFile multipartFile);

    ResultMessage showFile();

    ResultMessage deleteFile(UUID fileId);

    ResultMessage updateFile(UUID fileId, MultipartFile multipartFile);
}
