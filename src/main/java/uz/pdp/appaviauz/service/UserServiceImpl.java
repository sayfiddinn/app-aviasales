package uz.pdp.appaviauz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.entity.FlightSchedule;
import uz.pdp.appaviauz.entity.PurchasedTicket;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.mapper.MyMapper;
import uz.pdp.appaviauz.payload.PasswordDTO;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.UserDTO;
import uz.pdp.appaviauz.repository.PurchasedTicketRepo;
import uz.pdp.appaviauz.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.appaviauz.util.Path.DELETED;
import static uz.pdp.appaviauz.util.Path.UPDATED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PurchasedTicketRepo purchasedTicketRepo;
    private final MyMapper mapper;
    private final BaseService baseService;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;

    @Override
    public ResultMessage showProfile() {
        User user = baseService.getUser();
        return new ResultMessage(true, user);
    }

    @Override
    public ResultMessage editProfile(UserDTO userDTO) {
        User user = baseService.getUser();
        checkEmail(userDTO.getEmail(), user.getId());
        mapper.userDtoToUser(userDTO, user);
        user.setName(userDTO.getUsername());
        userRepository.save(user);
        return new ResultMessage(true, UPDATED);
    }

    private void checkEmail(String email, UUID userId) {
        User userByEmail = userRepository.findByEmail(email).orElse(null);
        if (userByEmail != null && !Objects.equals(userByEmail.getId(), userId))
            throw new ConflictException("This email exists");
    }


    @Override
    public ResultMessage deleteProfile() {
        User user = baseService.getUser();
        user.setDeleted(true);
        userRepository.save(user);
        return new ResultMessage(true, DELETED);
    }

    @Override
    public ResultMessage showFlightHistory() {
        List<FlightSchedule> flights = new ArrayList<>();
        List<PurchasedTicket> tickets = purchasedTicketRepo
                .findByUser(baseService.getUser());
        if (tickets.isEmpty())
            return new ResultMessage(false, "Flights not found");
        for (PurchasedTicket ticket : tickets) {
            flights.add(ticket.getFrom());
            if (ticket.getBack() != null) flights.add(ticket.getBack());
        }
        return new ResultMessage(true, flights);
    }

    @Override
    public ResultMessage showTicketHistory() {
        List<PurchasedTicket> tickets = purchasedTicketRepo
                .findByUser(baseService.getUser());
        if (!tickets.isEmpty()) return new ResultMessage(true, tickets);
        return new ResultMessage(false, "Tickets not found");
    }

    @Override
    public ResultMessage changePassword(PasswordDTO passwordDTO) {
        User user = baseService.getUser();
        if (!passwordEncoder
                .matches(passwordDTO.getOldPassword(), user.getPassword())) {
            return new ResultMessage(false, "Old password wrong");
        }
        String newPassword = passwordDTO.getNewPassword();
        if (newPassword.isBlank()
                || !Objects.equals(passwordDTO.getRePassword(), newPassword)) {
            return new ResultMessage(false, "Re password wrong");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new ResultMessage(true, UPDATED);
    }

    @Override
    public ResultMessage addFile(MultipartFile multipartFile) {
        UUID id = baseService.getUser().getId();
        return attachmentService.saveFile(multipartFile, id);
    }

    @Override
    public ResultMessage showFile() {
        return attachmentService
                .viewUserFile(baseService.getUser().getId());
    }

    @Override
    public ResultMessage deleteFile(UUID fileId) {
        return attachmentService
                .deleteFile(fileId, baseService.getUser().getId());
    }

    @Override
    public ResultMessage updateFile(UUID fileId, MultipartFile multipartFile) {
        return attachmentService.updateFile(fileId, multipartFile,
                baseService.getUser().getId());
    }

}
