package com.ing.client.model;

public class AccountLink {

    private AccountReference balances;
    private AccountReference transactions;

    public AccountReference getBalances() {
        return balances;
    }

    public void setBalances(AccountReference balances) {
        this.balances = balances;
    }

    public AccountReference getTransactions() {
        return transactions;
    }

    public void setTransactions(AccountReference transactions) {
        this.transactions = transactions;
    }
}
