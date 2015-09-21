package resource.management.service.adapters;

import com.mongodb.DBCollection;

import java.util.List;
import java.util.Map;

/**
 * Created by abhay.girnara on 18/09/15.
 */
public class HubServiceAdapter implements ServiceAdapter {
    @Override
    public Object getDetails(String id, DBCollection mongoCollection) {
        return null;
    }

    @Override
    public Object updateDetails(Object obj, Map<String, Object> params, DBCollection mongoCollection) {
        return null;
    }

    @Override
    public void removeDetails(Map<String, Object> params, DBCollection mongoCollection) {

    }

    @Override
    public List<Object> findByParams(Map<String, Object> params, DBCollection mongoCollection) {
        return null;
    }

    @Override
    public Object create(Object obj, DBCollection mongoCollection) {
        return null;
    }
}
