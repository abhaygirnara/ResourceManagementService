package resource.management.service;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import resource.management.service.configurations.ResourceManagementConfiguration;
import resource.management.service.health.MongoHealthCheck;
import resource.management.service.modules.GuiceConfigurationModule;
import resource.management.service.resources.ResourcesResource;
import resource.management.service.resources.UserResource;
import resource.management.service.utils.StartHelper;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.mongodb.MongoClient;

public class ResourceManagementService extends Application<ResourceManagementConfiguration> {

    private GuiceBundle<ResourceManagementConfiguration> guiceBundle;

    public static void main(String[] arguments) throws Exception {
        if (arguments.length == 0) {
            arguments = new String[] { "server", "src/main/resources/config.yml" };
        }
        StartHelper.setFileConfigDetails(arguments);
        new ResourceManagementService().run(arguments);
    }

    @Override
    public void initialize(Bootstrap<ResourceManagementConfiguration> bootstrap) {
        ResourceManagementConfiguration config = StartHelper.createConfiguration(StartHelper.getConfigFilename());
        guiceBundle = GuiceBundle.<ResourceManagementConfiguration> newBuilder()
                .setConfigClass(ResourceManagementConfiguration.class)
                .enableAutoConfig(getClass().getPackage().getName())
                .addModule(new GuiceConfigurationModule(config.getMongoConfiguration())).build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new SwaggerBundle<ResourceManagementConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                    ResourceManagementConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public String getName() {
        return "ResourceManagementService";
    }

    @Override
    public void run(ResourceManagementConfiguration configuration, Environment environment)
            throws Exception {
        final MongoClient mongoClient = new MongoClient(configuration.getMongoConfiguration().getHost(),
                configuration.getMongoConfiguration().getPort());
        environment.healthChecks().register("mongo", new MongoHealthCheck(mongoClient));
        environment.jersey().register(UserResource.class);
        environment.jersey().register(ResourcesResource.class);
        environment.jersey().register(MultiPartFeature.class);
    }

}
