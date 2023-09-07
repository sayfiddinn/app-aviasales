package uz.pdp.appaviauz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.appaviauz.entity.base.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Ticket extends AbsEntity {
    @ManyToOne
    private FlightSchedule from;
    @ManyToOne
    private FlightSchedule back;
    @Column(nullable = false)
    private Integer fromSeatNumber;
    private Integer backSeatNumber;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer count;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;
}

