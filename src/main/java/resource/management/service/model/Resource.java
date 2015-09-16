package resource.management.service.model;

import com.google.gson.Gson;

import resource.management.service.utils.Constants;
import resource.management.service.utils.Constants.PROCESS_TYPE;
import resource.management.service.utils.Constants.RESOURCE_TYPE;
import resource.management.service.utils.Constants.STATUS;

public class Resource {
    private static final Gson GSON = new Gson();
    private String id;
    private String name;
    private RESOURCE_TYPE resourceType;
    private int capacity;
    private STATUS status;
    private PROCESS_TYPE processType;
    private int priority;
    private String providerId;

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

    public RESOURCE_TYPE getResourceType() {
        return resourceType;
    }

    public void setResourceType(RESOURCE_TYPE resourceType) {
        this.resourceType = resourceType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public PROCESS_TYPE getProcessType() {
        return processType;
    }

    public void setProcessType(PROCESS_TYPE processType) {
        this.processType = processType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
