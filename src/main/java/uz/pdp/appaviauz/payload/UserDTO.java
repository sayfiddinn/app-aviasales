package uz.pdp.appaviauz.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotEmpty(message = "name cannot be empty")
    private String username;
    @NotEmpty(message = "name cannot be empty")
    private String email;
    @NotEmpty(message = "name cannot be empty")
    private String password;
    @Pattern(regexp = "^\\+998(9[012345789]|6[125679]|7[01234569])[0-9]{7}$"
            , message = "phone number not valid")
    private String phoneNumber;
    @NotEmpty(message = "name cannot be empty")
    private String passportId;
}
