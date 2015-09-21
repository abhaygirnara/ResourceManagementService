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
import resource.management.service.document.User;

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
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Gson GSON = new Gson();
    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);
    private DB mongoDB;
    private DBCollection mongoCollection;

    public UserResource() {

    }

    @Inject
    public UserResource(DB mongoDB) {
        this.mongoDB = mongoDB;
        setCollection(mongoDB);
    }

    private void setCollection(DB mongoDB) {
        mongoCollection = mongoDB.getCollection("users");
    }

    @Path("/get/{id}")
    @GET
    public Response getUserDetails(@PathParam("id") String id) {
        if (StringUtils.isEmpty(id)) {
            LOG.warn("Invalid data. Returning bad request error.");
            return Response.status(ErrorResponse.ErrorCode.MISSING_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.MISSING_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        try {
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.USER);
            BasicDBObject document = (BasicDBObject) serviceAdapter.getDetails(id, mongoCollection);
            return Response.ok(GSON.toJson(document), MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            LOG.error("Error while getting user details for id : " + id, e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }

    }

    @Path("/get")
    @GET
    public Response fetchUsers() {
        List<Object> listOfUsers = null;
        try {
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.USER);
            listOfUsers = serviceAdapter.findByParams(null, mongoCollection);
            return Response.ok(GSON.toJson(listOfUsers), MediaType.APPLICATION_JSON).build();
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
        User user = null;
        try {
            user = GSON.fromJson(json, User.class);
        } catch (Exception e) {
            LOG.warn("Invalid JSON object : bad request.");
            return Response.status(ErrorResponse.ErrorCode.INVALID_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INVALID_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        try {
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.USER);
            user = (User) serviceAdapter.create(user, mongoCollection);
        } catch (Exception e) {
            LOG.error("Error while creating a user for user : " + user.getId(), e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.CREATED).entity(user.toString()).build();
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
        User user = null;
        try {
            user = GSON.fromJson(json, User.class);
        } catch (Exception e) {
            LOG.warn("Invalid JSON object : bad request.");
            return Response.status(ErrorResponse.ErrorCode.INVALID_PARAMS.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INVALID_PARAMS.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();
        }
        try {
            ServiceAdapter serviceAdapter = ResourceManagementServiceFactory.getInstance().getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE.USER);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            serviceAdapter.updateDetails(user, params, mongoCollection);
        } catch (Exception e) {
            LOG.error("Error while updating a user for user : " + user.getId(), e);
            return Response.status(ErrorResponse.ErrorCode.INTERNAL_ERROR.getStatus())
                    .entity(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_ERROR.getMessage()).toJson())
                    .type(MediaType.APPLICATION_JSON).build();

        }
        return Response.ok().build();
    }

}
