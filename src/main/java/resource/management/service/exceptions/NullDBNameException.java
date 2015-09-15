package resource.management.service.exceptions;

/**
 * Exception thrown if the {@link com.eeb.dropwizardmongo.factory.MongoFactory} attempts to build
 * a DB object and the configured database name is null.
 */
public class NullDBNameException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8288343020583869094L;
    private static final String message = "Attempt made to create a DB object when the configured database name was null or invalid";

    public NullDBNameException() {
        super(message);
    }

}
