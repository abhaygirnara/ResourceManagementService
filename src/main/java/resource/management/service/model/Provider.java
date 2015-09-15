package resource.management.service.model;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import resource.management.service.utils.Constants.PROVIDER_TYPE;

@Getter
@Setter
public class Provider {
    private static final Gson GSON = new Gson();
    private String id;
    private String name;
    private PROVIDER_TYPE providerType;
    private String data;
    private String instalationId;

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
