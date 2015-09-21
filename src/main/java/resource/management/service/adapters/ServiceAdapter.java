package resource.management.service.adapters;

import com.mongodb.DBCollection;

import java.util.List;
import java.util.Map;

/**
 * Created by abhay.girnara on 18/09/15.
 */
public interface ServiceAdapter {

    public Object getDetails(String id, DBCollection mongoCollection);
    public Object updateDetails(Object obj, Map<String, Object> params, DBCollection mongoCollection);
    public void removeDetails(Map<String, Object> params, DBCollection mongoCollection);
    public List<Object> findByParams(Map<String, Object> params, DBCollection mongoCollection);
    public Object create(Object obj, DBCollection mongoCollection);
}
