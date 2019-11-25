package edu.harvard.iq.dataverse.globus;

import java.util.List;

public class ClientToken {
    private String accessToken;
    private String scope;
    private String resourceServer;
    private String expiresIn;
    private String tokenType;
    private List<ClientToken> otherTokens;

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setResourceServer(String resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void setOtherTokens(List<ClientToken> otherTokens) {
        this.otherTokens = otherTokens;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getResourceServer() {
        return resourceServer;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public List<ClientToken> getOtherTokens() {
        return otherTokens;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

}
