package resource.management.service.configurations;


import lombok.Getter;
import lombok.Setter;

/**
 * This class contains the Elastic Search Cluster configurations.
 */

@Getter @Setter public class ElasticSearchServiceConfiguration {

    private String host;
    private int port;
    private String clusterName;
}
