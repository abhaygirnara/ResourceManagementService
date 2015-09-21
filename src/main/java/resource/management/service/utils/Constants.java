package resource.management.service.utils;

import java.io.Serializable;

public class Constants implements Serializable {

    public enum STATUS {
        ACTIVE, INACTIVE, AVAILABLE;
    }

    public enum RESOURCE_TYPE {
        ARM, PPTL, HANDHELD, MANUAL_STATION, MANUAL_BIN;
    }

    public enum PROCESS_TYPE {
        LOADING, UN_LOADING, SORTATION, STAGING;
    }

    public enum PROVIDER_TYPE {
        GOR, MANUAL;
    }

    public enum STORAGE_BEAN_STATUS {
        OPEN, CLOSED, AVAILABLE
    }

    public enum STORAGE_BEAN_TYPE {
        POLY_BEAN;
    }

    public enum HUB_ZONE {
        NORTH, SOUTH, WEST, EAST;
    }

    public enum HUB_TYPE {
        FCMOTHERHUB, MPMOTHERHUB, PICKUPHUB, DELIVERYHUB;
    }

    public enum SERVICE_ADAPTER_TYPE{
        HUB,
        RESOURCE,
        PROVIDER,
        PROCESSING_AREA,
        STORAGE_BEAN,
        USER
    }

}
