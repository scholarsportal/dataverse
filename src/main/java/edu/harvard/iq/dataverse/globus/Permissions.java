package edu.harvard.iq.dataverse.globus;

public class Permissions {
    private String DATA_TYPE;
    private String principalType;
    private String principal;
    private String path;
    private String permissions;

    public void setPath(String path) {
        this.path = path;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setPrincipalType(String principalType) {
        this.principalType = principalType;
    }

    public String getPath() {
        return path;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPrincipalType() {
        return principalType;
    }

}
