package demetra.plan;

import demetra.domain.Recipe;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/plan")
public class PlanResource {

    @Inject
    PlanService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> getPlan(@QueryParam("days") @DefaultValue("7") int days) {
        return service.getPlan(days);
    }

}
