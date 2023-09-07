package uz.pdp.appaviauz.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import uz.pdp.appaviauz.entity.base.AbsAudit;

import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "city_id"})})
public class Aerodrome extends AbsAudit {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private City city;
    @JsonIgnore
    @CollectionTable(uniqueConstraints = {
            @UniqueConstraint(columnNames = {"aerodrome_id", "planes_id"})})
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Plane> planes;
    @OneToOne
    private Attachment attachment;
}

