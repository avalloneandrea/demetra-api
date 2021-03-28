package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class Ingredient extends PanacheEntity {

    @Column
    private String name;

    @Column
    private String unit;

}
