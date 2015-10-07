package resource.management.service.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.management.service.documents.BagDetail;
import resource.management.service.documents.TaskDetail;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This Class will be used to build the XContentBuilder for Elastic Search Client.
 * It includes the util function to communicate with elastic search cluster.
 */
public class ElasticSearchUtil {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUtil.class);
    public static Map<String, Boolean> indexMaping = new ConcurrentHashMap<String, Boolean>();

    /**
     * This method is responsible for building the source builder for TaskDetail document.
     *
     * @param taskDetail
     * @return XContentBuilder
     */
    public static XContentBuilder buildSourceBuilderForTaskDetail(TaskDetail taskDetail)
        throws IOException {
        XContentBuilder sourceBuilder = XContentFactory.jsonBuilder().startObject()
            .field("shipment_id", taskDetail.getShipmentId()).field("date", taskDetail.getDate())
            .field("hub_id", taskDetail.getHubId()).field("error_type", taskDetail.getErrorType());
        return sourceBuilder;
    }

    /**
     * This method is responsible for building the content builder for TaskDetail document.
     *
     * @param type elastic search cluster index type
     * @return XContentBuilder
     */
    public static XContentBuilder buildContentBuilderForTaskDetails(String type)
        throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().
            startObject().
            startObject(type).
            startObject("properties").
            startObject("shipment_id").field("index", "analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            startObject("date").field("index", "not_analyzed").
            field("type", "date").field("store", "yes").
            endObject().
            startObject("hub_id").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            startObject("error_type").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            endObject().
            endObject().
            endObject();
        return builder;
    }


    /**
     * This method is responsible for building the source builder for BagDetail document.
     *
     * @param bagDetail
     * @return XContentBuilder
     */
    public static XContentBuilder buildSourceBuilderForBagDetail(BagDetail bagDetail)
        throws IOException {
        XContentBuilder sourceBuilder =
            XContentFactory.jsonBuilder().startObject().field("bag_id", bagDetail.getBagId())
                .field("coc_code", bagDetail.getCocCode()).field("date", bagDetail.getDate())
                .field("src_hub_id", bagDetail.getSrcHubId())
                .field("dest_hub_id", bagDetail.getDestHubId())
                .field("final_dst_hub_id", bagDetail.getFinalDestHubId())
                .field("error_type", bagDetail.getErrorType());
        return sourceBuilder;
    }

    /**
     * This method is responsible for building the content builder for BagDetail document.
     *
     * @param type elastic search cluster index type
     * @return XContentBuilder
     */
    public static XContentBuilder buildContentBuilderForBagDetails(String type) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().
            startObject().
            startObject(type).
            startObject("properties").
            startObject("bag_id").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            startObject("coc_code").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            startObject("date").field("index", "not_analyzed").
            field("type", "date").field("store", "yes").
            endObject().
            startObject("src_hub_id").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            startObject("dest_hub_id").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            startObject("final_dest_hub_id").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            startObject("error_type").field("index", "not_analyzed").
            field("type", "string").field("store", "yes").
            endObject().
            endObject().
            endObject().
            endObject();
        return builder;
    }

    /**
     * This method is responsible for pushing the task detail data to Elastic Search Cluster.
     *
     * @param taskDetail
     * @param elasticSearchServiceClient
     * @return IndexResponse
     */
    public static IndexResponse pushTaskDetailData(TaskDetail taskDetail,
        ElasticSearchServiceClient elasticSearchServiceClient, String indexName, String type)
        throws IOException {
        if ((indexMaping.containsKey(indexName) && !indexMaping.get(indexName)) || (
            !indexMaping.containsKey(indexName) && !elasticSearchServiceClient
                .isIndexExist(indexName))) {
            CreateIndexResponse indexResponse = elasticSearchServiceClient.createIndex(indexName);
            if (indexResponse != null) {
                indexMaping.put(indexName, true);
            }
        }
        String mappingExistKey = indexName + ":" + type;
        if ((indexMaping.containsKey(mappingExistKey) && !indexMaping.get(mappingExistKey)) || (
            !indexMaping.containsKey(mappingExistKey) && !elasticSearchServiceClient
                .isMappingExist(indexName, type))) {
            XContentBuilder contentBuilder = buildContentBuilderForTaskDetails(type);
            elasticSearchServiceClient.buildMapping(indexName, type, contentBuilder);
            indexMaping.put(mappingExistKey, true);
        }
        XContentBuilder sourceContentBuilder = buildSourceBuilderForTaskDetail(taskDetail);
        IndexResponse indexResponse =
            elasticSearchServiceClient.buildIndex(indexName, type, sourceContentBuilder);
        return indexResponse;
    }

    /**
     * This method is responsible for pushing the bag detail data to Elastic Search Cluster.
     *
     * @param bagDetail
     * @param elasticSearchServiceClient
     * @return IndexResponse
     */
    public static IndexResponse pushBagDetailData(BagDetail bagDetail,
        ElasticSearchServiceClient elasticSearchServiceClient, String indexName, String type)
        throws IOException {
        if ((indexMaping.containsKey(indexName) && !indexMaping.get(indexName)) || (
            !indexMaping.containsKey(indexName) && !elasticSearchServiceClient
                .isIndexExist(indexName))) {
            CreateIndexResponse indexResponse = elasticSearchServiceClient.createIndex(indexName);
            if (indexResponse != null) {
                indexMaping.put(indexName, true);
            }
        }
        String mappingExistKey = indexName + ":" + type;
        if ((indexMaping.containsKey(mappingExistKey) && !indexMaping.get(mappingExistKey)) || (
            !indexMaping.containsKey(mappingExistKey) && !elasticSearchServiceClient
                .isMappingExist(indexName, type))) {
            XContentBuilder contentBuilder = buildContentBuilderForBagDetails(type);
            elasticSearchServiceClient.buildMapping(indexName, type, contentBuilder);
            indexMaping.put(mappingExistKey, true);
        }
        XContentBuilder sourceContentBuilder = buildSourceBuilderForBagDetail(bagDetail);
        IndexResponse indexResponse =
            elasticSearchServiceClient.buildIndex(indexName, type, sourceContentBuilder);
        return indexResponse;
    }


    /**
     * This method is responsible for pushing task data to Elastic Search Cluster
     * @param hubData
     * @param elasticSearchServiceClient
     * @param referenceId
     * @param errorType
     * @return void
     */
    //  public static void pushTaskDataToElasticCluster(HubData hubData, ElasticSearchServiceClient elasticSearchServiceClient, String referenceId, String errorType) {
    //    TaskDetail taskDetail = new TaskDetail();
    //    taskDetail.setDate(new Date());
    //    if(hubData !=null && StringUtils.isNotEmpty(referenceId) && StringUtils.isNotEmpty(errorType) && elasticSearchServiceClient != null){
    //      taskDetail.setHubId(hubData.getName());
    //      taskDetail.setShipmentId(referenceId);
    //      taskDetail.setErrorType(errorType);
    //      try {
    //        pushTaskDetailData(taskDetail, elasticSearchServiceClient, TASK_INDEX, TASK_INDEX_TYPE);
    //      }catch(Exception e){
    //         logger.warn("Error while pushing data to elastic cluster for shipmentId"+ taskDetail.getShipmentId(), e);
    //      }
    //    } else {
    //            logger.warn("Invalid Data! TaskDetail Data will not push to elastic cluster");
    //        }
    //    }


    /**
     * This method is responsible for pushing bag data to Elastic Search Cluster
     * @param shipmentBag
     * @param elasticSearchServiceClient
     * @param errorType
     * @return void
     */
    //  public static void pushBagDataToElasticCluster(ShipmentBag shipmentBag, ElasticSearchServiceClient elasticSearchServiceClient, String errorType){
    //    BagDetail bagDetail = new BagDetail();
    //    bagDetail.setDate(new Date());
    //    if(shipmentBag !=null && StringUtils.isNotEmpty(errorType)  && elasticSearchServiceClient != null){
    //      bagDetail.setBagId(shipmentBag.getBagTrackingId());
    //      bagDetail.setCocCode(shipmentBag.getCocCode());
    //      bagDetail.setSrcHubId(shipmentBag.getSrcHubId() + "");
    //      bagDetail.setDestHubId(shipmentBag.getDestHubId() + "");
    //      bagDetail.setFinalDestHubId(shipmentBag.getFinalDestHubId() + "");
    //      bagDetail.setErrorType(errorType);
    //      try {
    //        pushBagDetailData(bagDetail, elasticSearchServiceClient, BAG_INDEX, BAG_INDEX_TYPE);
    //      } catch(Exception e) {
    //        logger.warn("Error while pushing the bag detail data to elastic search cluster for bagId: "+ bagDetail.getBagId(), e);
    //      }
    //    } else {
    //      logger.warn("Invalid Data! BagDetail Data will not push to elastic cluster");
    //    }
    //  }
}
