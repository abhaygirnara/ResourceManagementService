package resource.management.service.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MongoConfiguration {

    private String dbName;
    private String host;
    private int port;

}
