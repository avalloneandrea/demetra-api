package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Ingredient extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Enumerated(EnumType.STRING)
    public Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Unit unit = Unit.g;

}
