package demetra.recipe;

import demetra.domain.Recipe;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("recipe")
@Tag(name = "RecipeResource", description = "Operations about recipes")
public class RecipeResource {

    @Inject
    RecipeService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all the available recipes")
    public List<Recipe> listAll(@Parameter(description = "Query to filter results") @QueryParam("query") String query) {
        return service.listAll(query);
    }

}
