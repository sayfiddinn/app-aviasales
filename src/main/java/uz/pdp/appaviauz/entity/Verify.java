package uz.pdp.appaviauz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Verify {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String link;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime time;
    @Column(columnDefinition = "boolean default false ")
    private boolean verified;

    public Verify(String link) {
        this.link = link;
    }

}
