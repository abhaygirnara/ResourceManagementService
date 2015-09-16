package resource.management.service.model;

import com.google.gson.Gson;

import resource.management.service.utils.Constants;
import resource.management.service.utils.Constants.PROVIDER_TYPE;

public class Provider {
    private static final Gson GSON = new Gson();
    private String id;
    private String name;
    private PROVIDER_TYPE providerType;
    private String data;
    private String instalationId;

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

    public PROVIDER_TYPE getProviderType() {
        return providerType;
    }

    public void setProviderType(PROVIDER_TYPE providerType) {
        this.providerType = providerType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getInstalationId() {
        return instalationId;
    }

    public void setInstalationId(String instalationId) {
        this.instalationId = instalationId;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

}
