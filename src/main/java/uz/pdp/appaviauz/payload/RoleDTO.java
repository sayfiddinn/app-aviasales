package uz.pdp.appaviauz.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import uz.pdp.appaviauz.entity.enums.Authority;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NonNull
    private Set<Authority> authorities;
}
