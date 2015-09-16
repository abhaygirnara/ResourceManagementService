package resource.management.service.model;

import java.util.List;

import com.google.gson.Gson;

import resource.management.service.utils.Constants;
import resource.management.service.utils.Constants.PROCESS_TYPE;
import resource.management.service.utils.Constants.STATUS;

public class ProcessingArea {
    private static final Gson GSON = new Gson();
    private List<Resource> resources;
    private List<StorageBean> storageBeans;
    private PROCESS_TYPE processType;
    private int capacity;
    private int priority;
    private String configId;
    private STATUS status;

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
