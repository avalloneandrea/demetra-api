package demetra.recipe;

import demetra.domain.Recipe;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class RecipeService {

    public List<Recipe> listAll(String query) {
        String pattern = "%" + query.toLowerCase() + "%";
        return Recipe.list("lower(name) like ?1", pattern);
    }

}
