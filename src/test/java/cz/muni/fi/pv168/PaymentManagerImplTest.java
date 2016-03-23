package cz.muni.fi.pv168;


import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import javax.sql.DataSource;
import static org.junit.Assert.*;
import org.apache.derby.jdbc.*;



/**
 * Created by lubomir.viluda on 16.3.2016.
 */
public class PaymentManagerImplTest {

    @org.junit.Test
    public void testCreatePayment() throws Exception {
        PaymentManagerImpl paymentManager = new PaymentManagerImpl();

        assertTrue(paymentManager.allPayments().isEmpty());
        assertTrue(paymentManager.allPayments().size() == 0);

        paymentManager.createPayment(new Payment());

        assertTrue(paymentManager.allPayments().size() == 1);

        paymentManager.createPayment(new Payment());

        assertTrue(paymentManager.allPayments().size() == 2);
        assertTrue(2 == paymentManager.allPayments().size());
    }

    @org.junit.Test
    public void testCreatePaymentNull() throws Exception {
        PaymentManagerImpl paymentManager = new PaymentManagerImpl();

        try {
            paymentManager.createPayment(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // correct exception
        } catch (Exception ex) {
            fail();
        }
    }


    @org.junit.Test
    public void testCreatePaymentDuplicity() throws Exception {
        PaymentManagerImpl paymentManager = new PaymentManagerImpl();

        Payment p1 = new Payment(new BigDecimal("5.0"), null, null, false, 0L, null, null);
        paymentManager.createPayment(p1);
        paymentManager.updatePayment(p1);

        Payment p2 = new Payment(new BigDecimal("5.0"), null, null, false, 0L, null, null);

        try {
            paymentManager.createPayment(p2);
            fail();
        } catch (InvalidParameterException ex) {
            // correct exception
        } catch (Exception ex) {
            fail();
        }
    }

    @org.junit.Test
    public void testUpdatePayment() throws Exception {
        PaymentManager paymentManager = new PaymentManagerImpl();

        paymentManager.createPayment(new Payment(new BigDecimal("5.0"), null, null, false, 0L, null, null));

        Payment p0 = paymentManager.allPayments().get(0);

        assertTrue(p0.getSended());
        assertTrue(null == p0.getMessage());
        assertEquals(new BigDecimal("5.0"), p0.getAmount());
        assertTrue(null == p0.getDate());
        assertTrue(0L == p0.getId());
        assertTrue(null == p0.getReceiver());
        assertTrue(null == p0.getSender());

        Account a1 = new Account();
        Account a2 = new Account();

        ZonedDateTime time = ZonedDateTime.now();
        Payment p1 = new Payment(new BigDecimal("0.0"), time, "New one", true, 0L, a1, a2);

        paymentManager.updatePayment(p1);

        assertFalse(null == p0.getMessage());
        assertEquals("New one", p0.getMessage());

        assertNotEquals(new BigDecimal("5.0"), p0.getAmount());
        assertEquals(new BigDecimal("0.0"), p0.getAmount());

        assertNotEquals(null, p0.getDate());
        assertEquals(time, p0.getDate());

        assertTrue(p0.isSended());

        assertTrue(0L == p0.getId());

        assertFalse(null == p0.getReceiver());
        assertFalse(null == p0.getSender());
        assertEquals(a1, p0.getReceiver());
        assertEquals(a2, p0.getSender());

        try {
            Payment p3 = new Payment(new BigDecimal("5.0"), null, null, false, 1L, null, null);
            paymentManager.updatePayment(p3);
            fail("should thrown exception");

        } catch (InvalidParameterException ex) {
            // should be fired
        } catch (Exception ex) {
            fail("unexpected exception");
        }

    }

    @org.junit.Test
    public void testEmptyUpgrade()
    {
        PaymentManager paymentManager = new PaymentManagerImpl();
        try {
            paymentManager.updatePayment(null);
            fail();
        } catch (IllegalArgumentException ex) {
            // should be fired
        } catch (Exception ex) {
            fail();
        }
    }

    @org.junit.Test
    public void testFindPayment() throws Exception {
        PaymentManager paymentManager = new PaymentManagerImpl();

        Payment p0 = new Payment(new BigDecimal("5.0"), null, null, false, 0L, null, null);
        Payment p1 = new Payment(new BigDecimal("5.0"), null, null, false, 1L, null, null);
        Payment p2 = new Payment(new BigDecimal("5.0"), null, null, false, 2L, null, null);
        Payment p3 = new Payment(new BigDecimal("5.0"), null, null, false, 3L, null, null);
        Payment p4 = new Payment(new BigDecimal("5.0"), null, null, false, 4L, null, null);


        paymentManager.createPayment(p0);
        paymentManager.createPayment(p1);
        paymentManager.createPayment(p2);
        paymentManager.createPayment(p3);
        paymentManager.createPayment(p4);

        assertTrue(p0.equals(paymentManager.findPayment(0L)));
        assertTrue(p1.equals(paymentManager.findPayment(1L)));
        assertTrue(p2.equals(paymentManager.findPayment(2L)));
        assertTrue(p3.equals(paymentManager.findPayment(3L)));
        assertTrue(p4.equals(paymentManager.findPayment(4L)));
    }

    @org.junit.Test
    public void testFindPaymentOutOfBound() throws Exception {
        PaymentManager paymentManager = new PaymentManagerImpl();
        try {
            paymentManager.findPayment(8L);
            fail();
        } catch (InvalidParameterException ex) {
            // should be fired
        } catch (Exception ex) {
            fail();
        }
    }


    @org.junit.Test
    public void testAllPayments() throws Exception {
        PaymentManager paymentManager = new PaymentManagerImpl();
        ArrayList<Payment> payments = new ArrayList<Payment>();

        Payment p0 = new Payment(new BigDecimal("5.0"), null, null, false, 0L, null, null);
        Payment p1 = new Payment(new BigDecimal("5.0"), null, null, false, 1L, null, null);
        Payment p2 = new Payment(new BigDecimal("5.0"), null, null, false, 2L, null, null);
        Payment p3 = new Payment(new BigDecimal("5.0"), null, null, false, 3L, null, null);
        Payment p4 = new Payment(new BigDecimal("5.0"), null, null, false, 4L, null, null);

        paymentManager.createPayment(p0);
        paymentManager.createPayment(p1);
        paymentManager.createPayment(p2);
        paymentManager.createPayment(p3);
        paymentManager.createPayment(p4);

        payments.add(p0);
        payments.add(p1);
        payments.add(p2);
        payments.add(p3);
        payments.add(p4);

        assert(payments.equals(paymentManager.allPayments()));
    }
}