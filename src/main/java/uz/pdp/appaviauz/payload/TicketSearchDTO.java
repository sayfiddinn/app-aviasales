package uz.pdp.appaviauz.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSearchDTO {
    @NonNull
    private Integer from_id; //city
    @NonNull
    private Integer to_id; //city
    @NonNull
    private Date when;
    private Date back;

}
