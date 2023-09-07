package uz.pdp.appaviauz.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appaviauz.entity.Attachment;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.enums.FileStatus;
import uz.pdp.appaviauz.entity.enums.RoleTypeEnum;
import uz.pdp.appaviauz.exception.BadRequestException;
import uz.pdp.appaviauz.exception.ContentTypeException;
import uz.pdp.appaviauz.exception.NotFoundException;
import uz.pdp.appaviauz.payload.ResultMessage;
import uz.pdp.appaviauz.repository.AerodromeRepository;
import uz.pdp.appaviauz.repository.AttachmentRepo;
import uz.pdp.appaviauz.repository.UserRepository;
import uz.pdp.appaviauz.util.Utils;
import uz.pdp.appaviauz.util.Validation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.appaviauz.util.Path.*;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepo attachmentRepo;
    private final UserRepository userRepository;
    private final BaseService baseService;
    private final AerodromeRepository aerodromeRepository;

    @Override
    public ResultMessage saveFile(MultipartFile multipartFile, @NonNull UUID objectId) {
        if (multipartFile.getContentType() == null) {
            return new ResultMessage(false, FILE_FAILED);
        }
        Validation.checkContentType(multipartFile.getContentType());
        if (!userRepository.existsById(objectId) && !aerodromeRepository.existsById(objectId))
            throw new BadRequestException("Some wrong");
        Attachment attachment = new Attachment(FileStatus.ACTIVE);
        makerFile(multipartFile, attachment, objectId);
        return new ResultMessage(true, attachment);
    }


    @Override
    public ResultMessage deleteFile(@NonNull UUID Id, @NonNull UUID objectId) {
        checkObject(Id, objectId);

        attachmentRepo.updateFileStatusById(FileStatus.DRAFT.name(), Id);
        return new ResultMessage(true, DELETED);
    }

    @Override
    public ResultMessage updateFile(@NonNull UUID Id, MultipartFile multipartFile,
                                    @NonNull UUID objectId) {
        if (multipartFile.getContentType() == null) {
            return new ResultMessage(false, FILE_FAILED);
        }
        Validation.checkContentType(multipartFile.getContentType());
        checkObject(Id, objectId);
        Attachment attachment = attachmentRepo.findById(Id)
                .orElseThrow(() -> new NotFoundException("Attachment" + NOT_FOUND));
        makerFile(multipartFile, attachment, null);
        return new ResultMessage(true, UPDATED);
    }

    @Override
    public Attachment getFindById(UUID Id) {
        return attachmentRepo.findByIdAndFileStatus(Id, FileStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("File" + NOT_FOUND));
    }

    @Override
    public ResultMessage viewUserFile(UUID userId) {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("User" + NOT_FOUND);
        List<UUID> all = attachmentRepo
                .findAllAttachmentIdByObjectId(userId, FileStatus.ACTIVE.name());
        if (all.isEmpty()) return new ResultMessage(false, "Files not found");
        return new ResultMessage(true, all);
    }

    private void makerFile(MultipartFile multipartFile, Attachment attachment, UUID id) {
        attachment.setOriginalName(multipartFile.getOriginalFilename());
        attachment.setSystemName(LocalDateTime.now() + "." +
                getExt(multipartFile.getOriginalFilename()));
        attachment.setSize(multipartFile.getSize());
        attachment.setContentType(multipartFile.getContentType());
        if (id != null) attachment.setObjectId(id);
        try {
            attachment.setData(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        attachmentRepo.save(attachment);
    }

    private String getExt(String name) {
        String ext = null;
        if (name != null && !name.isEmpty()) {
            int dot = name.lastIndexOf('.');
            if (dot > 0 && dot <= name.length() - 2) {
                ext = name.substring(dot + 1);
            }
        }
        return ext;
    }

    private void checkObject(UUID id, UUID objectId) {
        Attachment attachment = attachmentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Attachment" + NOT_FOUND));
        if (Objects.equals(attachment.getFileStatus(), FileStatus.DRAFT))
            throw new NotFoundException("Attachment" + NOT_FOUND);

        User user = baseService.getUser();
        if (userRepository.existsById(objectId)) {
            if (!Objects.equals(user.getId(), objectId) ||
                    !Objects.equals(attachment.getObjectId(), objectId)) {
                throw new BadRequestException(NOT_ALLOWED);
            }
        } else if (aerodromeRepository.existsById(objectId)) {
            RoleTypeEnum roleType = user.getRole().getRoleType();
            if (!roleType.equals(RoleTypeEnum.SUPER_ADMIN) && !roleType.equals(RoleTypeEnum.ADMIN))
                throw new BadRequestException(NOT_ALLOWED);
        } else throw new BadRequestException("Some wrong");
    }

}
