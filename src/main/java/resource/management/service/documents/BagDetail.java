package resource.management.service.documents;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * BagDetail contains the information about bag at task level.
 */

@Getter @Setter public class BagDetail {
    private String bagId;
    private String cocCode;
    private String dt;
    private Date date;
    private String srcHubId;
    private String destHubId;
    private String finalDestHubId;
    private String errorType;
}
