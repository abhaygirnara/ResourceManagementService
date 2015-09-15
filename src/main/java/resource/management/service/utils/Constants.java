package resource.management.service.utils;

public class Constants {

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

}
