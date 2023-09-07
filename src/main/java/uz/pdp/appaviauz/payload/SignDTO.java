package uz.pdp.appaviauz.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignDTO {
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
            message = "Password is not valid")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
