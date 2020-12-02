package com.ing.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.net.URL;

@ConfigurationProperties("ing")
public class ClientConfigurationProperties {
    private boolean debug;
    private String keyId;
    private URL redirectBackUrl;
    private String host;
    private File clientCertificate;
    private File clientKey;
    private File signingCertificate;
    private File signingKey;

    /**
     * When no scope is explicitly requested the application access token will contain all the scope-tokens available
     * in the certificate that you are using. Refer to the "Reference" section of the PSD2 API
     * you want to consume to see the available scopes.
     */
    private String scopes;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public File getClientCertificate() {
        return clientCertificate;
    }

    public void setClientCertificate(File clientCertificate) {
        this.clientCertificate = clientCertificate;
    }

    public File getClientKey() {
        return clientKey;
    }

    public void setClientKey(File clientKey) {
        this.clientKey = clientKey;
    }

    public File getSigningCertificate() {
        return signingCertificate;
    }

    public void setSigningCertificate(File signingCertificate) {
        this.signingCertificate = signingCertificate;
    }

    public File getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(File signingKey) {
        this.signingKey = signingKey;
    }

    public URL getRedirectBackUrl() {
        return redirectBackUrl;
    }

    public void setRedirectBackUrl(URL redirectBackUrl) {
        this.redirectBackUrl = redirectBackUrl;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
