package uz.pdp.appaviauz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appaviauz.entity.Attachment;
import uz.pdp.appaviauz.entity.enums.FileStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findByIdAndFileStatus(UUID id, FileStatus fileStatus);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "update attachment set file_status=?1 where id=?2")
    void updateFileStatusById(String fileStatus, UUID id);

    void deleteAllByFileStatus(FileStatus fileStatus);

    @Query(nativeQuery = true,
            value = "select id from attachment where object_id=?1 " +
                    "and file_status=?2 order by creation_timestamp desc")
    List<UUID> findAllAttachmentIdByObjectId(UUID objectId, String fileStatus);


}
