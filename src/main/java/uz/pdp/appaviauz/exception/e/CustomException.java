package uz.pdp.appaviauz.exception.e;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class CustomException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}
