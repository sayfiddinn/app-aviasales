package uz.pdp.appaviauz.exception.e;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.pdp.appaviauz.exception.*;
import uz.pdp.appaviauz.feign.TelegramFeign;
import uz.pdp.appaviauz.util.HTML;


import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private final TelegramFeign telegramFeign;

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handlerCustomException(BadRequestException badRequestException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomException customException = new CustomException(
                badRequestException.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, badRequest);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handlerCustomException(ForbiddenException forbiddenException) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        CustomException customException = new CustomException(
                forbiddenException.getMessage(),
                forbidden,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, forbidden);
    }

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handlerCustomException(ConflictException conflictException) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        CustomException customException = new CustomException(
                conflictException.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, conflict);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handlerCustomException(NullPointerException nullPointerException) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        CustomException customException = new CustomException(
                nullPointerException.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, conflict);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handlerCustomException(NotFoundException notFoundException) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CustomException customException = new CustomException(
                notFoundException.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, notFound);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handlerCustomException(UnauthorizedException unauthorizedException) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        CustomException customException = new CustomException(
                unauthorizedException.getMessage(),
                unauthorized,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, unauthorized);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerCustomException(IllegalArgumentException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomException customException = new CustomException(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, badRequest);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handlerCustomException() {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        CustomException customException = new CustomException(
                "You have not access",
                unauthorized,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, unauthorized);
    }

    @ExceptionHandler(value = {SizeLimitExceededException.class})
    public ResponseEntity<Object> handlerCustomException(SizeLimitExceededException exception) {
        HttpStatus conflict = HttpStatus.CONFLICT;
        CustomException customException = new CustomException(
                exception.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, conflict);
    }

    @ExceptionHandler(value = {MultipartException.class})
    public ResponseEntity<Object> handlerCustomException(MultipartException multipartException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomException customException = new CustomException(
                multipartException.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, badRequest);
    }

    @ExceptionHandler(value = {ContentTypeException.class})
    public ResponseEntity<Object> handlerCustomException(ContentTypeException contentTypeException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomException customException = new CustomException(
                contentTypeException.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, badRequest);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> otherRunTimeErrors(Exception exception) {
        telegramFeign.sendMessageToUser(new SendMessage("856845004",exception.toString()));
        log.error("Defined unknown RuntimeException :", exception);
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomException customException = new CustomException(
                "some error",
                badRequest,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, badRequest);
    }
    @ExceptionHandler(value = FileSystemNotFoundException.class)
    public ResponseEntity<?> otherRunTimeErrors(FileSystemNotFoundException e) {
        telegramFeign.sendMessageToUser(new SendMessage("856845004",e.toString()));
        return new ResponseEntity<>(HTML.error404, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.
                getBindingResult().
                getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
