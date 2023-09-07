package uz.pdp.appaviauz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.appaviauz.util.HTML;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<?> handleError(HttpServletRequest request) throws IOException {
        String errorPageName = "static/error403.html";
        HttpStatus status = HttpStatus.FORBIDDEN;

        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (Objects.equals(statusCode, 404)) {
            errorPageName = "static/error404.html";
            status = HttpStatus.NOT_FOUND;
        }
        String htmlContent;
        try {
            ClassPathResource resource = new ClassPathResource(errorPageName);
            byte[] contentBytes = Files.readAllBytes(Path.of(resource.getURI()));
            htmlContent = new String(contentBytes);
        } catch (Exception exception) {
            if (Objects.equals(errorPageName, "static/error403.html")) {
                htmlContent = HTML.error403;
            } else {
                htmlContent = HTML.error404;
            }
        }
        return ResponseEntity.status(status).body(htmlContent);
    }

}
