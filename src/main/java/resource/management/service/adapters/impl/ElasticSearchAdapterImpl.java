package resource.management.service.adapters.impl;

import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.management.service.adapters.ElasticSearchAdapter;
import resource.management.service.documents.BagDetail;
import resource.management.service.documents.TaskDetail;
import resource.management.service.responses.ErrorResponse;
import resource.management.service.utils.ElasticSearchServiceClient;
import resource.management.service.utils.ElasticSearchUtil;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by abhay.girnara on 07/10/15.
 */

public class ElasticSearchAdapterImpl implements ElasticSearchAdapter {

    private ElasticSearchServiceClient elasticSearchServiceClient;
    private static final String BAG_INDEX = "bag";
    private static final String BAG_INDEX_TYPE = "bag_type";
    private static final String TASK_INDEX = "task";
    private static final String TASK_INDEX_TYPE = "task_type";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Gson GSON = new Gson();
    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchAdapterImpl.class);

    @Override public Response pushData(String json, String type) throws Exception {
        if (BAG_INDEX.equalsIgnoreCase(type)) {
            BagDetail bagDetail = GSON.fromJson(json, BagDetail.class);
            if (validData(bagDetail)) {
                bagDetail.setDate(parseDate(bagDetail.getDt(), DATE_FORMAT));
                ElasticSearchUtil
                    .pushBagDetailData(bagDetail, elasticSearchServiceClient, BAG_INDEX,
                        BAG_INDEX_TYPE);
                return Response.status(javax.ws.rs.core.Response.Status.CREATED)
                    .entity("".toString()).build();
            }
        } else if (TASK_INDEX.equalsIgnoreCase(type)) {
            TaskDetail taskDetail = GSON.fromJson(json, TaskDetail.class);
            if (validData(taskDetail)) {
                taskDetail.setDate(parseDate(taskDetail.getDt(), DATE_FORMAT));
                ElasticSearchUtil
                    .pushTaskDetailData(taskDetail, elasticSearchServiceClient, TASK_INDEX,
                        TASK_INDEX_TYPE);
                return Response.status(javax.ws.rs.core.Response.Status.CREATED)
                    .entity("".toString()).build();
            }
        }
        LOG.error("Invalid json data/type request pushing data to elastic cluster type : " + type
            + " json data : " + json);
        return Response.status(ErrorResponse.ErrorCode.MISSING_PARAMS.getStatus())
            .entity(new ErrorResponse(ErrorResponse.ErrorCode.MISSING_PARAMS.getMessage()).toJson())
            .type(MediaType.APPLICATION_JSON).build();

    }

    @Inject public void setElasticSearchServiceClient(
        ElasticSearchServiceClient elasticSearchServiceClient) {
        this.elasticSearchServiceClient = elasticSearchServiceClient;
    }

    private boolean validData(BagDetail bagDetail) {
        if (bagDetail == null || StringUtils.isEmpty(bagDetail.getBagId()) || StringUtils
            .isEmpty(bagDetail.getCocCode()) || StringUtils.isEmpty(bagDetail.getDestHubId())
            || StringUtils.isEmpty(bagDetail.getErrorType()) || StringUtils
            .isEmpty(bagDetail.getFinalDestHubId()) || StringUtils.isEmpty(bagDetail.getSrcHubId())
            || StringUtils.isEmpty(bagDetail.getDt())) {
            return false;
        }
        return true;
    }

    private boolean validData(TaskDetail taskDetail) {
        if (taskDetail == null || StringUtils.isEmpty(taskDetail.getHubId()) || StringUtils
            .isEmpty(taskDetail.getShipmentId()) || StringUtils.isEmpty(taskDetail.getErrorType())
            || StringUtils.isEmpty(taskDetail.getDt())) {
            return false;
        }
        return true;
    }

    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}
