package demetra.alexa;

import demetra.domain.Account;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.Explode;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterStyle;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("alexa")
@Tag(name = "AlexaResource", description = "Operations about Alexa integration")
public class AlexaResource {

    @Inject
    AlexaService service;

    @POST
    @Path("accounts")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Register an account for the application")
    public Account register(
            @Parameter(description = "Amazon identifier") @FormParam("amazonId") String amazonId,
            @Parameter(description = "Alexa identifier") @FormParam("alexaId") String alexaId) {
        return service.register(amazonId, alexaId);
    }

    @POST
    @Path("items")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add items in the default shopping list given the recipes to make")
    public Response addItems(
            @Parameter(description = "Owner of the shopping list") @FormParam("accountId") String accountId,
            @Parameter(description = "Recipes to make") @FormParam("recipes") List<Long> recipes)
            throws IOException, InterruptedException {
        return service.addItems(accountId, recipes);
    }

}
