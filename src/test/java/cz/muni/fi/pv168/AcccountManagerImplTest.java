package cz.muni.fi.pv168;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;



/**
 *
 * @author Ondra
 */
public class AcccountManagerImplTest {

    private DataSource dataSource;
    private AccountManager manager;

    @org.junit.Before
    public void setUp() throws SQLException {
        dataSource = prepareDataSource();
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("CREATE TABLE accounts " +
                    "(ID BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "birthName VARCHAR(20), " +
                    "givenName VARCHAR(30), " +
                    "accountNumber VARCHAR(20), " +
                    "sumAmount NUMERIC(8,3), " +
                    "wasDeleted BOOLEAN)").executeUpdate();
        }
        manager = new AccountManagerImpl(dataSource);//(dataSource);
    }

    @org.junit.After
    public void tearDown() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("DROP TABLE accounts").executeUpdate();
        }
    }

    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        //we will use in memory database
        ds.setDatabaseName("memory:accounts");
        ds.setCreateDatabase("create");
        return ds;
    }

    @Test
    public void createAccount() throws AccountException{
        Account account = newAccount("Franta","Vizl", new BigDecimal("0"), "800/0100",false);
        manager.createAccount(account);
        assertEquals("Franta",account.getBirthName());
        assertEquals("Vizl",account.getGivenName());
        assertEquals(new BigDecimal("0"),account.getSumAmount());
        assertEquals("800/0100",account.getAccountNumber());
        assertEquals(false,account.getWasDeleted());
    }
    
    @Test
    public void createAccountWithBad() throws AccountException{
        Account account0 = newAccount(" ","Vizl", new BigDecimal("0"), "800/0100",false);
        Account account1 = newAccount("Franta"," ", new BigDecimal("0"), "800/0100",false);
        Account account2 = newAccount("Franta"," Vizl", new BigDecimal("-1"), "800/0100",false);
        Account account3 = newAccount("Franta"," ", new BigDecimal("0"), "800",false);
        Account account4 = newAccount("Franta"," ", new BigDecimal("0"), "800/0100",true);
        try {
            manager.createAccount(account0);
            fail("name without chars");
        } catch (IllegalArgumentException ex) {
            //OK
        }
       
        try {
            manager.createAccount(account1);
            fail("sure name without chars");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        try {
            manager.createAccount(account2);
            fail("starting ammount is under 0");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            manager.createAccount(account3);
            fail("wrong account number");
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            manager.createAccount(account4);
            fail("account cant be starting as deactivated");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
    }
    
    @Test
    public void findAllAccount() {
        assertThat(manager.findAllAccount()).isEmpty();

        Account account1 = newAccount("Franta0","Vizl0", new BigDecimal("0"), "800/0100",false);
        Account account2 = newAccount("Franta1","Vizl1", new BigDecimal("0"), "800/0100",false);

        manager.createAccount(account1);
        manager.createAccount(account2);
        
        List<Account> result = new ArrayList<>();
        result =  manager.findAllAccount();
        assertEquals(true,result.contains(account1));
        assertEquals(true,result.contains(account2));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNull() throws Exception {
        manager.createAccount(null);
    }
    
    @Test
    public void updateAccount() throws AccountException {
        Account account1 = newAccount("Franta","Vizl", new BigDecimal("0"), "800/0100",false);
        manager.createAccount(account1);
        
        account1.setSumAmount(new BigDecimal("500"));
        account1.setGivenName("Lojza");
        manager.updateAccount(account1);
        

        assertEquals(new BigDecimal("500"),account1.getSumAmount());
        assertEquals("Lojza",account1.getGivenName());
    }
    
    @Test
    public void updateAccountWithBad() throws AccountException {
        Account account = newAccount("Franta","Vizl", new BigDecimal("0"), "800/0100",false);
        manager.createAccount(account);
        
        account.setGivenName("10");
        try {
            manager.updateAccount(account);
            fail("name without chars");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        account.setBirthName("20");
        try {
            manager.updateAccount(account);
            fail("name without chars");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        account.setAccountNumber("0");
        try {
            manager.updateAccount(account);
            fail("wrong parametr");
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
    }
    private static Account newAccount(String birthName, String givenName, BigDecimal sumAmount, String accountNumber, boolean wasDeleted) {
        Account account = new Account();
        account.setBirthName(birthName);
        account.setGivenName(givenName);
        account.setSumAmount(sumAmount);
        account.setAccountNumber(accountNumber);
        account.setWasDeleted(wasDeleted);
        return account;
    }
}
