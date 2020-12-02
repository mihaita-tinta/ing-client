package com.ing.client.service;

import com.ing.client.config.ClientConfigurationProperties;
import net.danlucian.psd2.ing.flow.Flow;
import net.danlucian.psd2.ing.rpc.Client;
import net.danlucian.psd2.ing.rpc.payload.ApplicationAccessToken;
import net.danlucian.psd2.ing.rpc.payload.Country;
import net.danlucian.psd2.ing.rpc.payload.CustomerAccessToken;
import net.danlucian.psd2.ing.rpc.payload.PreflightUrl;
import net.danlucian.psd2.ing.security.ClientSecrets;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class GrantingService extends Client {

    private final Flow flow;
    private final ClientConfigurationProperties properties;

    public GrantingService(ClientSecrets clientSecrets, Flow flow, ClientConfigurationProperties properties) {
        super(clientSecrets, properties.getScopes());
        this.flow = flow;
        this.properties = properties;
    }

    public PreflightUrl getPreflightUrl() {
        Country country = Country.Romania;
        ApplicationAccessToken applicationAccessToken = flow.getAppAccessToken();
        URL redirectBackUrl = properties.getRedirectBackUrl();
        PreflightUrl preflightUrl = flow.getPreflightUrl(applicationAccessToken, redirectBackUrl, country);

        preflightUrl.enrichUri(
                country,
                applicationAccessToken.getClientId(),
                redirectBackUrl.toString(),
                scopes
        );
        return preflightUrl;
    }

    public CustomerAccessToken getCustomerAccessToken(String authorizationCode) {
        ApplicationAccessToken applicationAccessToken = flow.getAppAccessToken();
        return flow.getCustomerAccessToken(applicationAccessToken, authorizationCode);
    }

}
