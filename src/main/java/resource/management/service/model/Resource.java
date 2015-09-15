package resource.management.service.model;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import resource.management.service.utils.Constants.PROCESS_TYPE;
import resource.management.service.utils.Constants.RESOURCE_TYPE;
import resource.management.service.utils.Constants.STATUS;

@Getter
@Setter
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

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
