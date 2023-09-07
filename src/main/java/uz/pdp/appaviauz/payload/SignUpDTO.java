package uz.pdp.appaviauz.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    @NotEmpty(message = "username cannot be empty")
    private String username;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
            message = "Password is not valid")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @NotEmpty(message = "re password cannot be empty")
    private String rePassword;
    @Pattern(regexp = "^\\+998(9[012345789]|6[125679]|7[01234569])[0-9]{7}$"
            , message = "phone number not valid")
    @NotEmpty(message = "phone number cannot be empty")
    private String phoneNumber;
    @NotEmpty(message = "passport Id cannot be empty")
    private String passportId;

}
