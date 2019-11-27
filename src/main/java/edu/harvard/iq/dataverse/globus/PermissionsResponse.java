package edu.harvard.iq.dataverse.globus;

public class PermissionsResponse {
    private String code;
    private String resource;
    private String DATA_TYPE;
    private String requestId;
    private String accessId;
    private String message;

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public String getResource() {
        return resource;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }
}
