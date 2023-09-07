package uz.pdp.appaviauz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.Verify;
import uz.pdp.appaviauz.entity.enums.FileStatus;
import uz.pdp.appaviauz.exception.BadRequestException;
import uz.pdp.appaviauz.exception.ConflictException;
import uz.pdp.appaviauz.repository.AttachmentRepo;
import uz.pdp.appaviauz.repository.VerifyRepositary;

import java.time.LocalDateTime;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class BaseService {
    private final VerifyRepositary verifyRepositary;
    private final AttachmentRepo attachmentRepo;

    public void checkVerify(String link) {
        Verify verify = verifyRepositary.findByLinkAndVerifiedFalse(link)
                .orElseThrow(() -> new ConflictException("This link not found"));
        verify.setVerified(true);
        verifyRepositary.save(verify);
        LocalDateTime time = verify.getTime();
        if (time.isBefore(LocalDateTime.now().minusMinutes(30))) {
            throw new ConflictException("This link time expired");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteNeedlessVerify() {
        attachmentRepo.deleteAllByFileStatus(FileStatus.DRAFT);
        verifyRepositary.deleteAllByVerifiedTrue();
        verifyRepositary.deleteAllByTimeBefore(
                LocalDateTime.now().minusHours(1));

    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            throw new ConflictException("Some wrong");
        User user = (User) authentication.getPrincipal();
        if (user.isDeleted()) throw new BadRequestException("Profile not found");
        return user;
    }

}
