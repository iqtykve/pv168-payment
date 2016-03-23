package cz.muni.fi.pv168;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Created by lubomir.viluda on 16.3.2016.
 */
public class Payment {

    private BigDecimal amount;
    private ZonedDateTime date;
    private String message;
    private boolean sended;
    private Long id;
    private Account receiver;
    private Account sender;

    Payment(BigDecimal amount, ZonedDateTime date, String message, boolean sended, Long id, Account receiver, Account sender) {

        this.amount = amount;
        this.date = date;
        this.message = message;
        this.sended = sended;
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
    }

    public Payment() {
        this.amount = null;
        this.date = null;
        this.message = null;
        this.sended = false;
        this.id = null;
        this.receiver = null;
        this.sender = null;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSended() {
        return sended;
    }

    public boolean getSended() {
        return sended;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public boolean equals(Payment other){
        return false;
    }
}
