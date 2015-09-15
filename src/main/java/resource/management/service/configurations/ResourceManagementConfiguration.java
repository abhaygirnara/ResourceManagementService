package resource.management.service.configurations;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import resource.management.service.utils.MongoConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ResourceManagementConfiguration extends Configuration {

    @NotNull
    public String environment;

    @Valid
    @NotNull
    private MongoConfiguration mongoConfiguration = new MongoConfiguration();

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

}
