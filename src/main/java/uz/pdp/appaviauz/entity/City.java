package uz.pdp.appaviauz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.appaviauz.entity.base.AbsIntEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class City extends AbsIntEntity {
    @ManyToOne
    private Country country;

}
