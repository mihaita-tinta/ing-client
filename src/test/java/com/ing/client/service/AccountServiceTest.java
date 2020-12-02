package com.ing.client.service;

import net.danlucian.psd2.ing.rpc.payload.CustomerAccessToken;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AccountServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceTest.class);

    @Autowired
    AccountService service;

    @Autowired
    GrantingService grantingService;

    @Test
    public void testPreflightUrl() {

        log.info("test - preflight url: {}", grantingService.getPreflightUrl().getLocation());
    }

    @Test
    public void test() {

        String code = "3d456cf3-1ec5-48ad-86bd-229816a2b45c";
        CustomerAccessToken customerAccessToken = grantingService.getCustomerAccessToken(code);
        StepVerifier
                .create(service.findAll(customerAccessToken.getAccessToken())
                        .log())
                .assertNext(account -> assertNotNull(account))
                .assertNext(account -> assertNotNull(account))
                .assertNext(account -> assertNotNull(account))
                .expectComplete()
                .verify();
    }

}
