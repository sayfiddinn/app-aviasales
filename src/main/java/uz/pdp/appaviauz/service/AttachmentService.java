package uz.pdp.appaviauz.service;

import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.entity.Attachment;
import uz.pdp.appaviauz.payload.ResultMessage;

import java.util.UUID;


public interface AttachmentService {
    ResultMessage saveFile(MultipartFile multipartFile, @NonNull UUID objectId);

    ResultMessage deleteFile(@NonNull UUID Id, @NonNull UUID objectId);

    ResultMessage updateFile(@NonNull UUID Id, MultipartFile multipartFile,
                             @NonNull UUID objectId);

    @Transactional(readOnly = true)
    Attachment getFindById(UUID Id);

    ResultMessage viewUserFile(UUID userId);
}
