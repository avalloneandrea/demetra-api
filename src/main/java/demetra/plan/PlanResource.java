package demetra.plan;

import demetra.domain.Recipe;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/plan")
@Tag(name = "PlanResource", description = "Operations about meal plans")
public class PlanResource {

    @Inject
    PlanService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Build a meal plan given the number of days to cover")
    public List<Recipe> getPlan(@Parameter(description = "Number of days to cover") @QueryParam("days") @DefaultValue("7") int days) {
        return service.getPlan(days);
    }

}
