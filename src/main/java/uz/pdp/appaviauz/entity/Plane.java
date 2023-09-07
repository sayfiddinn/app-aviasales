package uz.pdp.appaviauz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.appaviauz.entity.base.AbsEntity;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"plane_number", "plane_company"})})
public class Plane extends AbsEntity {
    @Column(nullable = false)
    private Integer seats;
    @Column(name = "plane_number", nullable = false)
    private String planeNumber;
    @Column(name = "plane_company", nullable = false)
    private String planeCompany;
}
