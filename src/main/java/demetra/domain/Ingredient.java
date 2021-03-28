package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Ingredient extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String unit;

}
