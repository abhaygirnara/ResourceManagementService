package resource.management.service.modules;

import java.net.UnknownHostException;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.MongoClient;

import resource.management.service.utils.MongoConfiguration;

public class GuiceConfigurationModule extends AbstractModule {

    private MongoConfiguration mongoConfiguration;

    public GuiceConfigurationModule(MongoConfiguration mongoConfiguration) {
        this.mongoConfiguration = mongoConfiguration;
    }

    @Override
    protected void configure() {}

    @Provides
    public DB getDB() {
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(mongoConfiguration.getHost(),
                    mongoConfiguration.getPort());
            final DB db = mongoClient.getDB(mongoConfiguration.getDbName());
            return db;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
