package uz.pdp.appaviauz.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.appaviauz.util.Path;

import java.util.UUID;

import static uz.pdp.appaviauz.controller.AttachmentControllerImpl.*;

@RequestMapping(Path.BASE_PATH_FILE)
public interface AttachmentController {
    @GetMapping(VIEW)
    ResponseEntity<?> viewFile(@PathVariable UUID Id);

    @GetMapping(VIEW_USER)
    ResponseEntity<?> viewUserFile(@PathVariable UUID user_id);

    @GetMapping(DOWNLOAD)
    ResponseEntity<?> downloadFile(@PathVariable UUID Id);

}
