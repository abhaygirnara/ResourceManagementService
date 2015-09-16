package resource.management.service.model;

import java.util.List;

import com.google.gson.Gson;

import resource.management.service.utils.Constants;
import resource.management.service.utils.Constants.HUB_TYPE;
import resource.management.service.utils.Constants.HUB_ZONE;
import resource.management.service.utils.Constants.STATUS;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public HUB_ZONE getHubZone() {
        return hubZone;
    }

    public void setHubZone(HUB_ZONE hubZone) {
        this.hubZone = hubZone;
    }

    public String getCoc() {
        return coc;
    }

    public void setCoc(String coc) {
        this.coc = coc;
    }

    public HUB_TYPE getHubType() {
        return hubType;
    }

    public void setHubType(HUB_TYPE hubType) {
        this.hubType = hubType;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public List<ProcessingArea> getProcessingAreas() {
        return processingAreas;
    }

    public void setProcessingAreas(List<ProcessingArea> processingAreas) {
        this.processingAreas = processingAreas;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
