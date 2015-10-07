package resource.management.service.utils;


import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.management.service.configurations.ElasticSearchServiceConfiguration;

/**
 * ElasticSearchServiceClient class is responsible for building the elastic search cluster client, communicate with elastic search cluster and performing the operation on it.
 */
public class ElasticSearchServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceClient.class);
    private ElasticSearchServiceConfiguration configuration;
    private Client client;

    public ElasticSearchServiceClient(ElasticSearchServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * This method will return the Elastic Search Client if it is already initialized otherwise it will call createClient method to initialized the Elastic Search Client.
     *
     * @return Client
     */

    public Client getClient() {
        if (client == null) {
            client = createClient();
        }
        return client;
    }

    /**
     * This method will responsible for creating the Elastic Search client for given Elastic Search cluster.
     *
     * @return Client
     */
    protected Client createClient() {
        if (client == null) {
            try {
                Settings settings = ImmutableSettings.settingsBuilder()
                    .put("cluster.name", configuration.getClusterName())
                    .put("client.transport.sniff", true).build();
                TransportClient transportClient = new TransportClient(settings);
                transportClient = transportClient.addTransportAddress(
                    new InetSocketTransportAddress(configuration.getHost(),
                        configuration.getPort()));

                if (transportClient.connectedNodes().size() == 0) {
                    logger.warn(
                        "There are no active nodes available for the transport, it will be automatically added once nodes are live!");
                }
                client = transportClient;
            } catch (Exception ex) {
                logger.warn("Error occured while creating elastic search client!", ex);
            }
        }

        return client;
    }

    /**
     * This method is responsible for creating the Index in Elastic Search cluster.
     *
     * @param indexName elastic search cluster index
     * @return CreateIndexResponse
     */
    public CreateIndexResponse createIndex(String indexName) {
        IndicesExistsResponse res =
            getClient().admin().indices().prepareExists(indexName).execute().actionGet();
        if (!res.isExists()) {
            CreateIndexRequestBuilder createIndexRequestBuilder =
                getClient().admin().indices().prepareCreate(indexName);
            CreateIndexResponse createIndexResponse =
                createIndexRequestBuilder.execute().actionGet();
            if (!createIndexResponse.isAcknowledged()) {
                logger.warn("Could not create index [" + indexName + "].");
            }
            return createIndexResponse;
        }
        return null;
    }

    /**
     * This method is responsible for building the mapping for given index, type and builder.
     *
     * @param index   elastic search cluster index
     * @param type    elastic search cluster index type
     * @param builder XContentBuilder for building the mapping into elastic search cluster.
     * @return void
     */
    public void buildMapping(String index, String type, XContentBuilder builder) {
        getClient().admin().indices().preparePutMapping(index).setType(type).setSource(builder)
            .execute().actionGet();
    }

    /**
     * This method is responsible for building the index for given sourceBuilder(Contents) of provided index and type.
     *
     * @param index         elastic search cluster index
     * @param type          elastic search cluster index type
     * @param sourceBuilder XContentBuilder for inserting the document into elastic search cluster.
     * @return IndexResponse
     */
    public IndexResponse buildIndex(String index, String type, XContentBuilder sourceBuilder) {
        IndexRequest request = new IndexRequest(index, type).source(sourceBuilder);
        IndexResponse indexResponse = getClient().index(request).actionGet();
        return indexResponse;
    }

    /**
     * This method will return true if the mapping exist in elastic search cluster for given index and type.
     *
     * @param index elastic search cluster index
     * @param type  elastic search cluster index type
     * @return boolean
     */
    public boolean isMappingExist(String index, String type) {
        GetMappingsResponse mappingsResponse =
            getClient().admin().indices().prepareGetMappings(index).setTypes(type).get();
        if (mappingsResponse.getMappings().get(index) == null) {
            return false;
        }
        return mappingsResponse.getMappings().get(index).containsKey(type);
    }

    /**
     * This method is responsible for checking if the index exist or not in Elastic Search Cluster.
     *
     * @param indexName
     * @return boolean
     */
    public boolean isIndexExist(String indexName) {
        IndicesExistsResponse res =
            getClient().admin().indices().prepareExists(indexName).execute().actionGet();
        return res.isExists();
    }
}
