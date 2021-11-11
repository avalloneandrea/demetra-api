package demetra.recipe;

import demetra.domain.Recipe;
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

@Path("recipe")
@Tag(name = "RecipeResource", description = "Operations about recipes")
public class RecipeResource {

    @Inject
    RecipeService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List the available recipes")
    public List<Recipe> list(@Parameter(description = "Query to filter results") @QueryParam("query") String query) {
        return service.list(query);
    }

}
