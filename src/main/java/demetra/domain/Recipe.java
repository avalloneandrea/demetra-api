package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe extends PanacheEntity {

    @Column
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Tag> tags = new HashSet<>();

}