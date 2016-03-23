package cz.muni.fi.pv168;

import java.util.ArrayList;
import java.util.List;
import java.lang.UnsupportedOperationException;

/**
 * Created by lubomir.viluda on 16.3.2016.
 */
public class PaymentManagerImpl implements PaymentManager {

    private List<Payment> payments = null;

    public PaymentManagerImpl() {
        payments = new ArrayList<Payment>();
    }

    public Payment createPayment(Payment payment) {

        return null;
    }

    public void updatePayment(Payment payment) {
        return ;
    }

    public Payment findPayment(Long id) {
        return null;
    }

    public List<Payment> allPayments() {
        return new ArrayList<Payment>();
    }

    public List<Payment> findPaymentsByAccount(Account account) {
        return null;
    }

    public List<Payment> findPaymentsBySenderAccount(Account account) {
        return null;
    }

    public List<Payment> findPaymentsByReceiverAccount(Account account) {
        return null;
    }
}
