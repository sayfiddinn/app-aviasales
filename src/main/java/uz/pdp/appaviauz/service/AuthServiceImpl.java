package uz.pdp.appaviauz.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.Role;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.Verify;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;
import uz.pdp.appaviauz.exception.BadRequestException;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.filter.JwtProvider;
import uz.pdp.appaviauz.payload.EmailDetails;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.payload.SignDTO;
import uz.pdp.appaviauz.payload.SignUpDTO;
import uz.pdp.appaviauz.repository.RoleRepository;
import uz.pdp.appaviauz.repository.UserRepository;
import uz.pdp.appaviauz.repository.VerifyRepositary;
import uz.pdp.appaviauz.util.Utils;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uz.pdp.appaviauz.util.Path.LINK_CHECK_EMAIL;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    private final VerifyRepositary verifyRepositary;
    private final BaseService baseService;

    @Override
    public ResultMessage signUp(SignUpDTO signUpDTO) {
        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            return new ResultMessage(false,
                    "User exist with email " + signUpDTO.getEmail());
        }
        checkPhoneNumber(signUpDTO.getPhoneNumber());

        User user = getUserFromDTO(new User(), signUpDTO);
        Role role = roleRepository.findByRoleType(RoleTypeEnum.USER)
                .orElseThrow(() -> new ConflictException("Some wrong"));
        user.setRole(role);
        userRepository.save(user);
        sendCodeToEmail(user);
        return new ResultMessage(true, "User saved successfully");
    }

    private void checkPhoneNumber(String phoneNumber) {
        String regex = "^\\+998(9[012345789]|6[125679]|7[01234569])[0-9]{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new BadRequestException("Phone number not valid");
        }
        if (userRepository.existNumberAndNonDeleted(phoneNumber)) {
            throw new ConflictException("There is currently an active user on this phone number");
        }
    }


    public void sendCodeToEmail(User user) {
        String link = LINK_CHECK_EMAIL + user.getId() + "&code=" + user.getCode();
        verifyRepositary.save(new Verify(link));
        try {
            emailService.sendSimpleMailForAuth(new EmailDetails(
                    user.getEmail(), link, null), user.getCode());
        } catch (MessagingException e) {
            throw new ConflictException(e.getMessage());
        }
    }


    private User getUserFromDTO(User user, SignUpDTO signUpDTO) {
        String password = signUpDTO.getPassword();
        if (password.isBlank() || !Objects.equals(password, signUpDTO.getRePassword())) {
            throw new BadRequestException("Re password wrong");
        }
        user.setCode(generateCode());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setName(signUpDTO.getUsername());
        user.setPassportId(signUpDTO.getPassportId());
        user.setPhoneNumber(signUpDTO.getPhoneNumber());
        return user;
    }

    @Override
    public ResultMessage signIn(SignDTO signDTO) {
        User user = userRepository.findByEmail(signDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("This email not found"));
        if (user.isDeleted()) throw new NotFoundException("This user not found");
        if (!user.isEnabled())
            return new ResultMessage(false, "Please confirm your account first");
        if (!passwordEncoder.matches(signDTO.getPassword(), user.getPassword())) {
            return new ResultMessage(false, "Passwords are not equals");
        }
        String token = jwtProvider.generateJWT(user.getEmail());
        return new ResultMessage(true, Utils.BEARER + token);
    }


    @Override
    public ResultMessage checkEmail(UUID id, String code) {
        baseService.checkVerify(LINK_CHECK_EMAIL + id + "&code=" + code);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("This user id not found"));
        if (Objects.equals(user.getCode(), code)) {
            user.setEnabled(true);
            userRepository.save(user);
            return new ResultMessage(true, "User authorized successfully");
        }
        return new ResultMessage(false, "Please check the link and try again a few moments later");
    }


    public String generateCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append((int) (Math.random() * 10));
        }
        return builder.toString();
    }
}
