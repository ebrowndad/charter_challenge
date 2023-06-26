package com.brownmunoz.pricingchallenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Transaction {

    private String customer;
    private LocalDate date;
    private BigDecimal price;

    public Transaction(String customer, LocalDate date, BigDecimal price) {
        this.customer = customer;
        this.date = date;
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }


}
