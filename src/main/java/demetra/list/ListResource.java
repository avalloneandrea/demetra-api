package demetra.list;

import demetra.domain.RecipeIngredient;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("list")
@Tag(name = "ListResource", description = "Operations about shopping lists")
public class ListResource {

    @Inject
    ListService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Build a shopping list given the recipes to make")
    public List<RecipeIngredient> getList(@Parameter(description = "Recipes to make") @QueryParam("recipes") List<Long> recipes) {
        return service.getList(recipes);
    }

}
