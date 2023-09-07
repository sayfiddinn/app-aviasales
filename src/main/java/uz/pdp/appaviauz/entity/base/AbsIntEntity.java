package uz.pdp.appaviauz.entity.base;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class AbsIntEntity extends AbsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

}
