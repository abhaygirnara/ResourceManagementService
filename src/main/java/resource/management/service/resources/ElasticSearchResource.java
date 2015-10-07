package resource.management.service.resources;

import com.google.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import resource.management.service.adapters.ElasticSearchAdapter;
import resource.management.service.responses.ErrorResponse;



/**
 * This class is responsible for handling the post request for pushing the data to elastic cluster.s
 */

@Path("/elasticsearch")
@Produces(MediaType.APPLICATION_JSON)
public class ElasticSearchResource {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchResource.class);
    private ElasticSearchAdapter elasticSearchAdapter;

    @Path("/push")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json, @QueryParam("type") String type) {
        if (StringUtils.isEmpty(json) && StringUtils.isEmpty(type)) {
            LOG.warn("Invalid data. Returning bad request error.");
            return Response.status(ErrorResponse.ErrorCode.MISSING_PARAMS.getStatus()).entity(
                new ErrorResponse(ErrorResponse.ErrorCode.MISSING_PARAMS.getMessage()).toJson())
                .type(MediaType.APPLICATION_JSON).build();
        }

        try {
            LOG.info("Pushing data: " +json +" of type: "+type + " into elastic cluster");
            return elasticSearchAdapter.pushData(json, type);
        } catch (Exception e) {
            LOG.error("Error while pushing data json : " + json +" type: " + type+"to elastic cluster : " + "", e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus()).entity(
                new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @Inject
    public void setElasticSearchAdapterImpl(ElasticSearchAdapter elasticSearchAdapter) {
        this.elasticSearchAdapter = elasticSearchAdapter;
    }


}
