/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168;

/**
 *
 * @author Ondra
 */
public class AccountException extends Exception{
    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
