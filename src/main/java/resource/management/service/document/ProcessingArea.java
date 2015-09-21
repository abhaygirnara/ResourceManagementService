package resource.management.service.document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import org.mongojack.ObjectId;
import resource.management.service.utils.Constants.PROCESS_TYPE;
import resource.management.service.utils.Constants.STATUS;

public class ProcessingArea {
    private static final Gson GSON = new Gson();
    private String id;
    private List<Resource> resources;
    private List<StorageBean> storageBeans;
    private PROCESS_TYPE processType;
    private int capacity;
    private int priority;
    private String configId;
    private STATUS status;


    @ObjectId
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @ObjectId
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<StorageBean> getStorageBeans() {
        return storageBeans;
    }

    public void setStorageBeans(List<StorageBean> storageBeans) {
        this.storageBeans = storageBeans;
    }

    public PROCESS_TYPE getProcessType() {
        return processType;
    }

    public void setProcessType(PROCESS_TYPE processType) {
        this.processType = processType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
