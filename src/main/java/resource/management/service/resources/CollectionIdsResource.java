package resource.management.service.resources;

import com.google.inject.Inject;
import com.mongodb.DB;

import resource.management.service.document.MongoDocument;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns a list of Ids for the specified collection.
 */
@Path("/mongodb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CollectionIdsResource {

    private DB mongoDB;

    public CollectionIdsResource() {

    }

    @Inject
    public CollectionIdsResource(DB mongoDB) {
        this.mongoDB = mongoDB;
    }

    @Path("/{collection}")
    @GET
    public List<MongoDocument> fetch(@PathParam("collection") String collection) {
        System.out.print("MongoDB :" + mongoDB);
        final JacksonDBCollection<MongoDocument, String> coll = JacksonDBCollection.wrap(
                mongoDB.getCollection(collection), MongoDocument.class,
                String.class);
        final DBCursor<MongoDocument> cursor = coll.find();
        final List<MongoDocument> l = new ArrayList<MongoDocument>();

        try {
            while (cursor.hasNext()) {
                l.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return l;
    }

}
