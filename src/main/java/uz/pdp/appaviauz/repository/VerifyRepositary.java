package uz.pdp.appaviauz.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appaviauz.entity.Verify;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface VerifyRepositary extends JpaRepository<Verify, UUID> {
    Optional<Verify> findByLinkAndVerifiedFalse(String link);

    @Transactional
    void deleteAllByVerifiedTrue();

    @Transactional
    void deleteAllByTimeBefore(LocalDateTime time);

}
