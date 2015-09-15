package resource.management.service.model;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import resource.management.service.utils.Constants.STORAGE_BEAN_STATUS;
import resource.management.service.utils.Constants.STORAGE_BEAN_TYPE;

@Getter
@Setter
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

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
