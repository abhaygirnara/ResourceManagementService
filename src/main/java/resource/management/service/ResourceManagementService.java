package resource.management.service;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Duration;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import resource.management.service.adapters.ElasticSearchAdapter;
import resource.management.service.adapters.impl.ElasticSearchAdapterImpl;
import resource.management.service.configurations.ResourceManagementConfiguration;
import resource.management.service.configurations.WorkerConfiguration;
import resource.management.service.resources.ElasticSearchResource;
import resource.management.service.utils.ElasticSearchServiceClient;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class ResourceManagementService extends Application<ResourceManagementConfiguration> {

    public static void main(String[] arguments) throws Exception {
        if (arguments.length == 0) {
            arguments = new String[] {"server", "src/main/resources/prod-config.yml"};
        }
        new ResourceManagementService().run(arguments);
    }

    @Override public void initialize(Bootstrap<ResourceManagementConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<ResourceManagementConfiguration>() {
            @Override protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                ResourceManagementConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override public String getName() {
        return "ResourceManagementService";
    }

    @Override
    public void run(ResourceManagementConfiguration configuration, Environment environment)
        throws Exception {
        WorkerConfiguration workerConf = configuration.workerConfiguration;
        ExecutorService executor = environment.lifecycle().executorService("resource-service-%d")
            .maxThreads(workerConf.numMaxThreads).minThreads(workerConf.numMinThreads)
            .keepAliveTime(Duration.seconds(workerConf.idleTimeInSecs))
            .rejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
            .shutdownTime(Duration.seconds(workerConf.shutDownTimeInSecs))
            .workQueue(new ArrayBlockingQueue<Runnable>(workerConf.queueCapacity)).build();

        Injector inj = createInjector(configuration, executor);
        environment.jersey().register(inj.getInstance(ElasticSearchResource.class));
        environment.jersey().register(inj.getInstance(MultiPartFeature.class));
    }

    private Injector createInjector(final ResourceManagementConfiguration conf,
        final ExecutorService executor) {
        return Guice.createInjector(new AbstractModule() {
            @Override protected void configure() {
                bind(ResourceManagementConfiguration.class).toInstance(conf);
                bind(ElasticSearchServiceClient.class)
                    .toInstance(new ElasticSearchServiceClient(conf.elasticConfiguration));
                bind(ElasticSearchAdapter.class).toInstance(new ElasticSearchAdapterImpl());
            }
        });
    }
}
