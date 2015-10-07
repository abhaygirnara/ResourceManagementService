package resource.management.service.adapters;


import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by abhay.girnara on 07/10/15.
 */
public interface ElasticSearchAdapter {

    public Response pushData(String json, String type) throws Exception;
}
