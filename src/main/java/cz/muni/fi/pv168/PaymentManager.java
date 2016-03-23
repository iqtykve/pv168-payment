package cz.muni.fi.pv168;

import java.util.List;

/**
 * Created by lubomir.viluda on 16.3.2016.
 */
public interface PaymentManager {
    Payment createPayment(Payment payment);    // better name send ?
    void updatePayment(Payment payment);
    Payment findPayment(Long id);
    List<Payment> allPayments();
    List<Payment> findPaymentsByAccount(Account account);
    List<Payment> findPaymentsBySenderAccount(Account account);
    List<Payment> findPaymentsByReceiverAccount(Account account);
}
