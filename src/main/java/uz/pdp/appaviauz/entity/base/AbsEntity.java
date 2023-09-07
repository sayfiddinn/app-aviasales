package uz.pdp.appaviauz.entity.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class AbsEntity extends AbsAudit {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;


}
