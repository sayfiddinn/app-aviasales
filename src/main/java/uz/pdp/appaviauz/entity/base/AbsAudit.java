package uz.pdp.appaviauz.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbsAudit {

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationTimestamp;
    @UpdateTimestamp
    private Timestamp updateTimestamp;
    @CreatedBy
    private UUID createdBy;
    @LastModifiedBy
    private UUID updatedBy;
}
