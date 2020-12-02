package com.ing.client.rest;

import com.ing.client.service.GrantingService;
import net.danlucian.psd2.ing.rpc.payload.CustomerAccessToken;
import net.danlucian.psd2.ing.rpc.payload.PreflightUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrantController {


    @Autowired
    GrantingService service;

    @GetMapping("/grants")
    public PreflightUrl redirectUrl() {
        return service.getPreflightUrl();
    }

    @PostMapping("/grants/{code}")
    public CustomerAccessToken getAccessToken(@PathVariable String code) {
        return service.getCustomerAccessToken(code);
    }
}
