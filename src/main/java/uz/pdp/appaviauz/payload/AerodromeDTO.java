package uz.pdp.appaviauz.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
public class AerodromeDTO {
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @Min(value = 1, message = "seats is not negative")
    private Integer cityId;
}
