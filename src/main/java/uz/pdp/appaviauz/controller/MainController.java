package uz.pdp.appaviauz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@Controller
public class MainController {

    @GetMapping("/*")
    public ResponseEntity<Void> home() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("swagger-ui/index.html"))
                .build();
    }
}
