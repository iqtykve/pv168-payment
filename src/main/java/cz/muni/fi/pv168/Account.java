package cz.muni.fi.pv168;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import java.util.Objects;
/**
 *
 * @author Ondra
 */
public class Account {
    private String birthName;
    private String givenName;
    private String accountNumber;
    private boolean wasDeleted;
    private Long id;
    private BigDecimal sumAmount;

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setWasDeleted(boolean wasDeleted) {
        this.wasDeleted = wasDeleted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getBirthName() {
        return birthName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean getWasDeleted() {
        return wasDeleted;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", birth name=" + birthName + ", given name=" + givenName + ", sum amount=" + sumAmount + ", account number='" + accountNumber + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;
        if ((getAccountNumber().equals(account.getAccountNumber()))
                && (getBirthName().equals(account.getBirthName()))
                && getGivenName().equals(account.getGivenName())) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.birthName);
        hash = 97 * hash + Objects.hashCode(this.givenName);
        hash = 97 * hash + Objects.hashCode(this.accountNumber);
        hash = 97 * hash + (this.wasDeleted ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.sumAmount);
        return hash;
    }

    public Account(){
    }
}
