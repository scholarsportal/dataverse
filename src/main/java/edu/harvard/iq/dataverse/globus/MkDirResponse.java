package edu.harvard.iq.dataverse.globus;

public class MkDirResponse {
    private String dataType;
    private String code;
    private String message;
    private String requestId;
    private String resource;

    public void setCode(String code) {
        this.code = code;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getCode() {
        return code;
    }

    public String getDataType() {
        return dataType;
    }

    public String getMessage() {
        return message;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getResource() {
        return resource;
    }

}
