package resource.management.service.utils;

import com.google.inject.Injector;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import resource.management.service.configurations.ResourceManagementConfiguration;

import javax.validation.Validation;
import java.io.File;

/**
 */
public class StartHelper {

    private static String configFilename;

    public static String getConfigFilename() {
        return configFilename;
    }

    public static void setConfigFilename(String configFilename) {
        StartHelper.configFilename = configFilename;
    }

    public static ResourceManagementConfiguration createConfiguration(String configFilename) {
        ConfigurationFactory<ResourceManagementConfiguration> factory = new ConfigurationFactory<ResourceManagementConfiguration>(
                ResourceManagementConfiguration.class,
                Validation.buildDefaultValidatorFactory().getValidator(),
                Jackson.newObjectMapper(),
                "");
        ResourceManagementConfiguration configuration;
        try {
            configuration = factory.build(new File(configFilename));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return configuration;
    }

    public static void setFileConfigDetails(String[] arguements) {
        for (String str : arguements) {
            if (str.contains(".yml")) {
                setConfigFilename(str);
                break;
            }
        }
    }

}
