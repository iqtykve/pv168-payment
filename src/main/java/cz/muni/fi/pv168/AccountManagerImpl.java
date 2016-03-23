package cz.muni.fi.pv168;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Ondra
 */
public class AccountManagerImpl implements AccountManager {
    
    private final DataSource dataSource;
    
    public AccountManagerImpl(DataSource dataSource){
        this.dataSource = dataSource;
    };
    private void validate(Account account) throws IllegalArgumentException {
        if(account==null) throw new IllegalArgumentException("grave should not be null");
         
        int res = new BigDecimal("0").compareTo(account.getSumAmount()); //starting amount must be upper then zero
        if( res == 1 )throw new IllegalArgumentException("initial sum account must be positive");
    }
    
    @Override
    public void createAccount(Account account)throws ServiceFailureException {

        validate(account);
        if (account.getId() != null) {
            throw new IllegalArgumentException("grave id is already set");
        }
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO accounts (birthName,givenName,accountNumber,sumAmount,wasDeleted) VALUES (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, account.getBirthName());
            st.setString(2, account.getGivenName());
            st.setString(3, account.getAccountNumber());
            st.setBigDecimal(4, account.getSumAmount());
            st.setBoolean(5, account.getWasDeleted());
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert account " + account);
            }

            ResultSet keyRS = st.getGeneratedKeys();
            account.setId(getKey(keyRS, account));

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting account " + account, ex);
        }
    }
    
    
    private Long getKey(ResultSet keyRS, Account account) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert grave " + account
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert grave " + account
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert grave " + account
                    + " - no key found");
        }
    }
    
    @Override
    public void updateAccount(Account account){
        
    }
    @Override
    public void deleteAccount(Account account){
    
    }
    @Override
    public Account findAccount(int id){
        Account account = new Account();
        return account;
    }
    
    @Override
    public List<Account> findAllAccount() throws ServiceFailureException{
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT id,birthName,givenName,accountNumber,sumAmount,wasDeleted FROM accounts")) {

            ResultSet rs = st.executeQuery();

            List<Account> result = new ArrayList<>();
            while (rs.next()) {
                result.add(resultSetToAccount(rs));
            }
            return result;

        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when retrieving all accounts", ex);
        }
    }
    
    private Account resultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setBirthName(rs.getString("birthName"));
        account.setGivenName(rs.getString("givenName"));
        account.setAccountNumber(rs.getString("accountNumber"));
        account.setSumAmount(rs.getBigDecimal("sumAmount"));
        account.setWasDeleted(rs.getBoolean("wasDeleted"));
        return account;
    }
   
}
