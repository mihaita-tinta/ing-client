package com.ing.client.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import net.danlucian.psd2.ing.flow.Flow;
import net.danlucian.psd2.ing.flow.implementation.SandboxAuthorizationFlow;
import net.danlucian.psd2.ing.security.ClientSecrets;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class ClientConfiguration {
    private static final Logger log = getLogger(ClientConfiguration.class);

    @Bean
    public ClientSecrets secrets(ClientConfigurationProperties props) throws IOException {
        return new ClientSecrets(
                props.getClientCertificate(),
                props.getClientKey(),
                props.getSigningCertificate(),
                props.getSigningKey()
        );
    }

    @Bean
    public Flow flow(ClientSecrets clientSecrets, ClientConfigurationProperties props) {

        return new SandboxAuthorizationFlow(clientSecrets, props.getScopes());
    }


    @Bean
    @Primary
    public WebClient apiClient(ClientSecrets secrets, ClientConfigurationProperties props) throws IOException {


        SslContext sslContext = SslContextBuilder
                .forClient()
                .keyManager(secrets.getClientSigningKey(), secrets.getClientSigningCert())
                .build();

        HttpClient httpClient = HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl(props.getHost())
                .clientConnector(connector)
                .filters(exchangeFilterFunctions -> {
                    if (props.isDebug()) {
                        exchangeFilterFunctions.add(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                            StringBuilder sb = new StringBuilder("Request: \n");
                            sb.append("url: ");
                            sb.append(clientRequest.url());
                            sb.append(", headers: ");
                            clientRequest
                                    .headers()
                                    .forEach((name, values) -> {
                                        sb.append(name);
                                        sb.append(": [");
                                        values.forEach(value -> sb.append(value));
                                        sb.append("]\n");
                                    });
                            log.info(sb.toString());
                            return Mono.just(clientRequest);
                        }));
                        exchangeFilterFunctions.add(ExchangeFilterFunction.ofResponseProcessor(res -> {
                            StringBuilder sb = new StringBuilder("Response: \n");
                            sb.append("headers: ");
                            res
                                    .headers()
                                    .asHttpHeaders()
                                    .forEach((name, values) -> {
                                        sb.append(name);
                                        sb.append(":");
                                        values.forEach(value -> sb.append(value));
                                        sb.append(" ");
                                    });
                            log.info(sb.toString());
                            return Mono.just(res);
                        }));
                    }
                })
                .build();
    }
}
