package resource.management.service.model;

import java.util.List;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import resource.management.service.utils.Constants.HUB_TYPE;
import resource.management.service.utils.Constants.HUB_ZONE;
import resource.management.service.utils.Constants.STATUS;

@Getter
@Setter
public class Hub {
    private static final Gson GSON = new Gson();
    private String id;
    private String name;
    private int priority;
    private HUB_ZONE hubZone;
    private String coc;
    private HUB_TYPE hubType;
    private String slotId;
    private STATUS status;
    private List<ProcessingArea> processingAreas;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
