package resource.management.service.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.constraints.NotNull;

public class ResourceManagementConfiguration extends Configuration {

    @NotNull public String environment;

    @JsonProperty("swagger") public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @NotNull public ElasticSearchServiceConfiguration elasticConfiguration;

    @NotNull public WorkerConfiguration workerConfiguration = new WorkerConfiguration();

}
