package resource.management.service.model;

import java.util.List;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import resource.management.service.utils.Constants.PROCESS_TYPE;
import resource.management.service.utils.Constants.STATUS;

@Getter
@Setter
public class ProcessingArea {
    private static final Gson GSON = new Gson();
    private List<Resource> resources;
    private List<StorageBean> storageBeans;
    private PROCESS_TYPE processType;
    private int capacity;
    private int priority;
    private String configId;
    private STATUS status;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
