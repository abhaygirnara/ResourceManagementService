package resource.management.service.documents;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * TaskDetail contains the information about given shipment at Task level.
 */
@Getter @Setter public class TaskDetail {
    private String hubId;
    private Date date;
    private String dt;
    private String shipmentId;
    private String errorType;
}
