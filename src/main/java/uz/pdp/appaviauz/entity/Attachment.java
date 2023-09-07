package uz.pdp.appaviauz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import uz.pdp.appaviauz.entity.base.AbsAudit;
import uz.pdp.appaviauz.entity.enums.FileStatus;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Attachment extends AbsAudit {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(nullable = false,updatable = false)
    @JsonIgnore
    private UUID objectId;
    @Column(nullable = false)
    private String contentType;
    @JsonIgnore
    @Column(nullable = false)
    private String originalName;
    @JsonIgnore
    @Column(nullable = false)
    private String systemName;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false,columnDefinition = "varchar(255) default 'ACTIVE' ")
    @Enumerated(EnumType.STRING)
    private FileStatus fileStatus;
    @JsonIgnore
    @Column(name = "data")
    @Lob
    private byte[] data;
    public Attachment(FileStatus fileStatus){
        this.fileStatus=fileStatus;
    }
}
