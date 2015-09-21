package resource.management.service.adapters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.StringUtils;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import resource.management.service.document.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abhay.girnara on 18/09/15.
 */
public class ResourceServiceAdapter implements ServiceAdapter{
    @Override
    public Object getDetails(String id, DBCollection mongoCollection) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        com.mongodb.DBCursor cursor = mongoCollection.find(whereQuery);
        DBObject document = null;
        if (cursor.hasNext()) {
            document = cursor.next();
        }
        return document;
    }

    @Override
    public Object updateDetails(Object obj, Map<String, Object> params, DBCollection mongoCollection) {
        Resource resource = (Resource)obj;
        BasicDBObject newDocument = new BasicDBObject();
        BasicDBObject updatedDocument = new BasicDBObject();
        updateDocument(resource, updatedDocument);
        newDocument.append("$set", updatedDocument);
        BasicDBObject searchQuery = new BasicDBObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            searchQuery.append(entry.getKey(), entry.getValue());
        }
        WriteResult writeResult = mongoCollection.update(searchQuery, newDocument);
        return writeResult;
    }

    private void updateDocument(Resource resource, BasicDBObject updatedDocument) {
        if (resource.getName() != null) {
            updatedDocument.append("name", resource.getName());
        }
        if (resource.getResourceType() != null) {
            updatedDocument.append("resourceType", resource.getResourceType().toString());
        }
        if (resource.getCapacity() != 0) {
            updatedDocument.append("capacity", resource.getCapacity());
        }
        if(resource.getStatus() != null){
            updatedDocument.append("status", resource.getStatus().toString());
        }
        if(resource.getProcessType() != null){
            updatedDocument.append("processType", resource.getProcessType().toString());
        }
        if(resource.getPriority() !=0){
            updatedDocument.append("priority", resource.getPriority());
        }
        if(StringUtils.isNotEmpty(resource.getProviderId())){
            updatedDocument.append("providerId", resource.getProviderId());
        }
    }

    @Override
    public void removeDetails(Map<String, Object> params, DBCollection mongoCollection) {
        BasicDBObject document = new BasicDBObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            document.put(entry.getKey(), entry.getValue());
        }
        mongoCollection.remove(document);
    }

    @Override
    public List<Object> findByParams(Map<String, Object> params, DBCollection mongoCollection) {
        final JacksonDBCollection<Resource, String> coll = JacksonDBCollection.wrap(
                mongoCollection, Resource.class,
                String.class);
        BasicDBObject searchObject = new BasicDBObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            searchObject.put(entry.getKey(), entry.getValue());
        }
        final DBCursor<Resource> cursor = coll.find(searchObject);
        final List<Object> listOfUsers = new ArrayList<Object>();

        try {
            while (cursor.hasNext()) {
                listOfUsers.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return listOfUsers;
    }

    @Override
    public Object create(Object obj, DBCollection mongoCollection) {
        Resource resource = (Resource) obj;
        if (resource != null) {
            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("_id", resource.getId());
            basicDBObject.put("name", resource.getName());
            basicDBObject.put("resourceType", resource.getResourceType().toString());
            basicDBObject.put("capacity", resource.getCapacity());
            basicDBObject.put("status", resource.getStatus().toString());
            basicDBObject.put("processType", resource.getProcessType().toString());
            basicDBObject.put("priority", resource.getPriority());
            basicDBObject.put("providerId", resource.getProviderId());
            mongoCollection.insert(basicDBObject);
        }
        return resource;
    }
}
