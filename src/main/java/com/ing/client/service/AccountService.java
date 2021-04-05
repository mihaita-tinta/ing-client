package com.ing.client.service;


import com.ing.client.config.ClientConfigurationProperties;
import com.ing.client.model.Account;
import com.ing.client.model.AccountResponse;
import net.danlucian.psd2.ing.flow.Flow;
import net.danlucian.psd2.ing.rpc.Client;
import net.danlucian.psd2.ing.security.ClientSecrets;
import net.danlucian.psd2.ing.security.SecurityUtil;
import net.danlucian.psd2.ing.time.DateUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class AccountService extends Client {

    private final WebClient client;
    private final Flow flow;
    private final ClientConfigurationProperties props;

    public AccountService(ClientSecrets clientSecrets, WebClient client, Flow flow, ClientConfigurationProperties props) {
        super(clientSecrets, props.getScopes());
        this.client = client;
        this.flow = flow;
        this.props = props;
    }

    public Flux<Account> findAll(String customerAccessToken) {


        final String currentDate = DateUtil.getCurrentDateAsString();
        final String digest = SecurityUtil.generateDigestAndConvert("");

        final String signature = SecurityUtil.generateSignature(
                clientSecrets.getClientSigningKey(),
                signingTemplate("get", "/v3/accounts", currentDate, digest)
        );
        final String authorization =
                "keyId=\"" + props.getKeyId() + "\",algorithm=\"rsa-sha256\",headers=\"(request-target) date digest\",signature=\"" + signature + "\"";

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("v3/accounts")
                        .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + customerAccessToken)
                .header("Digest", digest)
                .header("Date", DateUtil.getCurrentDateAsString())
                .header("Signature", authorization)
                .exchangeToFlux(res -> {

                    if (res.statusCode().is2xxSuccessful())
                        return res.bodyToMono(AccountResponse.class)
                                .flatMapMany(accounts -> Flux.fromIterable(accounts.getAccounts()));

                    return res.bodyToMono(String.class)
                            .flatMapMany(s -> Flux.error(new IllegalArgumentException("error: " + s)));

                });


    }
}
