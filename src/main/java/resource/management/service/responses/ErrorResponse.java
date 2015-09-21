package resource.management.service.responses;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;

public class ErrorResponse {
    private static final Gson gson = new Gson();
    public String errorMessage;
    
    public enum ErrorCode {
        MISSING_PARAMS(Response.Status.BAD_REQUEST,"Missing params. Please check!"),
        INVALID_PARAMS(Response.Status.BAD_REQUEST, "Invalid Parameters, Please verify the request parameters"),
        INTERNAL_ERROR(Response.Status.INTERNAL_SERVER_ERROR,"Please try later");
        
        
        private Response.Status status;
        private String message;
        private ErrorCode(Response.Status status, String message) {
            this.status = status;
            this.message = message;
        }
        
        public Response.Status getStatus() {
            return status;
        }
        
        public String getMessage() {
            return message;
        }
    }
    
    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String toJson() {
        return gson.toJson(this);
    }
}

