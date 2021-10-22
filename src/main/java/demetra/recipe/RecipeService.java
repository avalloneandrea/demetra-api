package demetra.recipe;

import demetra.domain.Recipe;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class RecipeService {

    public List<Recipe> listAll() {
        return Recipe.listAll();
    }

}
