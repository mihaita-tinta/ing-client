package com.ing.client.rest;

import com.ing.client.model.Account;
import com.ing.client.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AccountController {


    @Autowired
    AccountService service;

    @GetMapping("/accounts")
    public Flux<Account> getAccounts(@RequestParam String authorization) {
        return service.findAll(authorization);
    }
}
