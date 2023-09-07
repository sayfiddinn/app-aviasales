package uz.pdp.appaviauz.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.appaviauz.entity.base.AbsEntity;
import uz.pdp.appaviauz.entity.enums.FlightStatus;

import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class FlightSchedule extends AbsEntity {

    @ManyToOne
    private Aerodrome from;
    @ManyToOne
    private Aerodrome to;
    @OneToMany
    List<FlightSchedule> transfers;
    @Column(nullable = false)
    private Timestamp flightTime;
    @ManyToOne
    private Plane plane;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FlightStatus flightStatus;

    {
        flightStatus = FlightStatus.NEW;
    }

}
