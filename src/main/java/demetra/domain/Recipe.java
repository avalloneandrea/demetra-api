package demetra.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Recipe extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    public Set<Tag> tags = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<RecipeIngredient> recipeIngredients = new HashSet<>();

}
