package com.ing.client.model;

import reactor.core.publisher.Flux;

import java.util.List;

public class AccountResponse {

    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
