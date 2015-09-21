package resource.management.service.utils;

import com.google.inject.Inject;
import com.mongodb.DB;
import resource.management.service.adapters.*;

/**
 * Created by abhay.girnara on 18/09/15.
 */
public class ResourceManagementServiceFactory {
    public UserServiceAdapter userServiceAdapter = new UserServiceAdapter();
    public ResourceServiceAdapter resourceServiceAdapter = new ResourceServiceAdapter();
    public HubServiceAdapter hubServiceAdapter = new HubServiceAdapter();
    public ProviderServiceAdapter providerServiceAdapter = new ProviderServiceAdapter();
    public StorageBeanServiceAdapter storageBeanServiceAdapter = new StorageBeanServiceAdapter();
    public ProcessingAreaServiceAdapter PROCESSING_AREA_ADAPTER = new ProcessingAreaServiceAdapter();
    public static final ResourceManagementServiceFactory INSTANCE = new ResourceManagementServiceFactory();

    public ServiceAdapter getServiceAdapter(Constants.SERVICE_ADAPTER_TYPE type){
        ServiceAdapter serviceAdapter;
        switch(type){
            case HUB:
                serviceAdapter = hubServiceAdapter;
                break;
            case RESOURCE:
                serviceAdapter = resourceServiceAdapter;
                break;
            case PROVIDER:
                serviceAdapter = providerServiceAdapter;
                break;
            case PROCESSING_AREA:
                serviceAdapter = PROCESSING_AREA_ADAPTER;
                break;
            case STORAGE_BEAN:
                serviceAdapter = storageBeanServiceAdapter;
                break;
            case USER:
                serviceAdapter = userServiceAdapter;
                break;
            default:
                serviceAdapter = null;
        }
        return serviceAdapter;
    }

    public static ResourceManagementServiceFactory getInstance(){
        return INSTANCE;
    }

}
