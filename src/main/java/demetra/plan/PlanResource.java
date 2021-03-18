package demetra.plan;

import demetra.domain.Recipe;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/plan")
public class PlanResource {

    @Inject
    PlanService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> getPlan() {
        return service.getPlan();
    }

}
