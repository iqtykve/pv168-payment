package cz.muni.fi.pv168;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;

/**
 *
 * @author Ondra
 */
public interface AccountManager {
    void createAccount(Account account) throws ServiceFailureException;
    void updateAccount(Account account) throws ServiceFailureException;
    void deleteAccount(Account account) throws ServiceFailureException;
    Account findAccount(int id) throws ServiceFailureException;
    List<Account> findAllAccount() throws ServiceFailureException;
}
