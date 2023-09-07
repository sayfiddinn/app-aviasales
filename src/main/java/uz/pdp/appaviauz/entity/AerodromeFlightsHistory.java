package uz.pdp.appaviauz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
public class AerodromeFlightsHistory {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String comment;
    @ManyToOne
    private Aerodrome aerodrome;
    @ManyToOne
    private Plane plane;
    @Column(nullable = false)
    private Timestamp date;
    @Column(nullable = false)
    private boolean isArrived;
    @Column(nullable = false)
    private boolean hasLeft;

}
