package com.ing.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private String iban;
    private String name;
    private String currency;
    private String product;

    @JsonProperty("_links")
    private AccountLink links;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public AccountLink getLinks() {
        return links;
    }

    public void setLinks(AccountLink links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
