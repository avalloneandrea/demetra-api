package demetra.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient extends PanacheEntity {

    @JsonBackReference
    @ManyToOne(optional = false)
    public Recipe recipe;

    @ManyToOne(optional = false)
    public Ingredient ingredient;

    @Column(nullable = false)
    public int quantity;

}
