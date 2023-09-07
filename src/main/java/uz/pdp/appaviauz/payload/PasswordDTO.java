package uz.pdp.appaviauz.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {
    @NotEmpty(message = "old Password cannot be empty")
    private String oldPassword;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
            message = "Password is not valid")
    @NotEmpty(message = "new Password cannot be empty")
    private String newPassword;
    @NotEmpty(message = "re Password cannot be empty")
    private String rePassword;
}
