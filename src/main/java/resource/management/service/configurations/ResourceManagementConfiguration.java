package resource.management.service.configurations;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import resource.management.service.utils.MongoConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceManagementConfiguration extends Configuration {

    @NotNull
    public String environment;

    @Valid
    @NotNull
    private MongoConfiguration mongoConfiguration = new MongoConfiguration();

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setMongoConfiguration(MongoConfiguration mongoConfiguration) {
        this.mongoConfiguration = mongoConfiguration;
    }

    public void setSwaggerBundleConfiguration(SwaggerBundleConfiguration swaggerBundleConfiguration) {
        this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    }

    public String getEnvironment() {
        return environment;
    }

    public MongoConfiguration getMongoConfiguration() {
        return mongoConfiguration;
    }

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }
}
