package uz.pdp.appaviauz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appaviauz.entity.Attachment;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.service.AttachmentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "attachment-controller", description = "Attachment controller management")
@SecurityRequirement(name = "bearerAuth")
public class AttachmentControllerImpl implements AttachmentController {
    private final AttachmentService attachmentService;
    static final String VIEW = "/view/{Id}";
    static final String VIEW_USER = "/view_user/{user_id}";
    static final String DOWNLOAD = "/download/{Id}";


    @Override
    public ResponseEntity<?> viewFile(UUID Id) {
        Attachment attachment = attachmentService.getFindById(Id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(attachment.getContentType()));
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename(attachment.getOriginalName()).build());
        return new ResponseEntity<>(attachment.getData(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> viewUserFile(UUID user_id) {
        ResultMessage resultMessage = attachmentService.viewUserFile(user_id);
        return ResponseEntity
                .status(resultMessage.getSuccess()
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND)
                .body(resultMessage);
    }

    @Override
    public ResponseEntity<?> downloadFile(UUID Id) {
        Attachment attachment = attachmentService.getFindById(Id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(attachment.getContentType()));
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(attachment.getOriginalName()).build());
        return new ResponseEntity<>(attachment.getData(), headers, HttpStatus.OK);
    }


}
