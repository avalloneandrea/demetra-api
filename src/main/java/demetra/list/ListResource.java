package demetra.list;

import demetra.domain.ListIngredient;
import demetra.domain.Recipe;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/list")
@Tag(name = "List Resource", description = "Operations about shopping lists")
public class ListResource {

    @Inject
    ListService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Build a shopping list given the recipes to make")
    public List<ListIngredient> getList(@Parameter(description = "Recipes to make") List<Recipe> recipes) {
        return service.getList(recipes);
    }

}
