package resource.management.service.adapters;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import resource.management.service.document.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abhay.girnara on 18/09/15.
 */
public class UserServiceAdapter implements ServiceAdapter {

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
        User user = (User)obj;
        BasicDBObject newDocument = new BasicDBObject();
        BasicDBObject updatedDocument = new BasicDBObject();
        if (user.getAge() != 0) {
            updatedDocument.append("age", user.getAge());
        }
        if (StringUtils.isNotEmpty(user.getName())) {
            updatedDocument.append("name", user.getName());
        }
        if (StringUtils.isNotEmpty(user.getPhone())) {
            updatedDocument.append("phone", user.getPhone());
        }
        newDocument.append("$set", updatedDocument);

        BasicDBObject searchQuery = new BasicDBObject().append("_id", params.get("id"));

        WriteResult writeResult = mongoCollection.update(searchQuery, newDocument);
        return writeResult;
    }

    @Override
    public void removeDetails(Map<String, Object> params, DBCollection mongoCollection) {

    }

    @Override
    public List<Object> findByParams(Map<String, Object> params, DBCollection mongoCollection) {
        final JacksonDBCollection<User, String> coll = JacksonDBCollection.wrap(
                mongoCollection, User.class,
                String.class);
        final DBCursor<User> cursor = coll.find();
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
        User user = (User) obj;
        if (user != null) {
            BasicDBObject basicDBObject = new BasicDBObject();
            basicDBObject.put("_id", user.getId());
            basicDBObject.put("name", user.getName());
            basicDBObject.put("age", user.getAge());
            basicDBObject.put("phone", user.getPhone());
            mongoCollection.insert(basicDBObject);
        }
        return user;
    }
}
