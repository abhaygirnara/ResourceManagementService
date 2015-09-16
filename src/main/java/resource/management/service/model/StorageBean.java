package resource.management.service.model;

import com.google.gson.Gson;

import resource.management.service.utils.Constants;
import resource.management.service.utils.Constants.STORAGE_BEAN_STATUS;
import resource.management.service.utils.Constants.STORAGE_BEAN_TYPE;

public class StorageBean {
    private static final Gson GSON = new Gson();
    private String id;
    private String name;
    private STORAGE_BEAN_STATUS status;
    private int capacity;
    private STORAGE_BEAN_TYPE type;
    private int emptyWeight;
    private int currentWeight;
    private int maxWeight;
    private int priority;

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

    public STORAGE_BEAN_STATUS getStatus() {
        return status;
    }

    public void setStatus(STORAGE_BEAN_STATUS status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public STORAGE_BEAN_TYPE getType() {
        return type;
    }

    public void setType(STORAGE_BEAN_TYPE type) {
        this.type = type;
    }

    public int getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(int emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
