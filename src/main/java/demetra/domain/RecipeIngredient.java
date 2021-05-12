package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RecipeIngredient extends PanacheEntity {

    @ManyToOne(optional = false)
    public Recipe recipe;

    @ManyToOne(optional = false)
    public Ingredient ingredient;

    @Column(nullable = false)
    public int quantity;

}
