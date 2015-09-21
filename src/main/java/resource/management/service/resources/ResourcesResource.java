package resource.management.service.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.management.service.adapters.ServiceAdapter;
import resource.management.service.document.Resource;
import resource.management.service.responses.ErrorResponse;
import resource.management.service.utils.Constants;
import resource.management.service.utils.ResourceManagementServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Returns a list of Ids for the specified collection.
 */
@Path("/resources")
@Produces(MediaType.APPLICATION_JSON)
public class ResourcesResource {

    private static final Gson GSON = new Gson();
    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);
    private DB mongoDB;
    private DBCollection mongoCollection;

    public ResourcesResource() {

    }

    @Inject
    public ResourcesResource(DB mongoDB) {
        this.mongoDB = mongoDB;
        setCollection(mongoDB);
    }

    private void setCollection(DB mongoDB) {
        mongoCollection = mongoDB.getCollection("resources");
    }

    @Path("/{id}")
    @GET
    public Response getResourceDetails(@PathParam("id") String id) {
        if (StringUtils.isEmpty(id)) {
            LOG.warn("Invalid data. Returning bad request error.");
            return Response.status(ErrorResponse.ErrorCode.MISSING_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.MISSING_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        try {
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.RESOURCE);
            BasicDBObject document = (BasicDBObject) serviceAdapter.getDetails(id, mongoCollection);
            return Response.ok(GSON.toJson(document), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            LOG.error("Error while getting resource details for id : " + id, e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }

    }

    @Path("/get")
    @GET
    public Response getResourcesByParams(@QueryParam("processType") String processType) {
        List<Object> listOfResources = null;
        if (StringUtils.isEmpty(processType)) {
            LOG.warn("Invalid data. Returning bad request error.");
            return Response.status(ErrorResponse.ErrorCode.MISSING_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.MISSING_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("processType", processType);
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.RESOURCE);
            listOfResources = serviceAdapter.findByParams(params, mongoCollection);
            return Response.ok(GSON.toJson(listOfResources), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            LOG.error("Error while getting user details for id : ", e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json) {
        if (StringUtils.isEmpty(json)) {
            LOG.warn("Invalid data. Returning bad request error.");
            return Response.status(ErrorResponse.ErrorCode.MISSING_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.MISSING_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        Resource resource = null;
        try {
            resource = GSON.fromJson(json, Resource.class);
        } catch (Exception e) {
            LOG.warn("Invalid JSON object : bad request.");
            return Response.status(ErrorResponse.ErrorCode.INVALID_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INVALID_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        try {
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.RESOURCE);
            resource = (Resource) serviceAdapter.create(resource, mongoCollection);
        } catch (Exception e) {
            LOG.error("Error while creating a resources for user : " + resource.getId(), e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.CREATED).entity(resource.toString()).build();
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") String id, String json) {

        if (StringUtils.isEmpty(json)) {
            LOG.warn("Invalid data. Returning bad request error.");
            return Response.status(ErrorResponse.ErrorCode.MISSING_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.MISSING_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        Resource resource = null;
        try {
            resource = GSON.fromJson(json, Resource.class);
        } catch (Exception e) {
            LOG.warn("Invalid JSON object : bad request.");
            return Response.status(ErrorResponse.ErrorCode.INVALID_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INVALID_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        try {
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.RESOURCE);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            serviceAdapter.updateDetails(resource, params, mongoCollection);
        } catch (Exception e) {
            LOG.error("Error while updating a resources for resourcesId : " + resource.getId(), e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();

        }
        return Response.ok().build();
    }

}
