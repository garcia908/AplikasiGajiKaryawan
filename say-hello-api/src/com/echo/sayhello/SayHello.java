/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.echo.sayhello;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author UsEr
 */
public interface SayHello extends Remote {
    String sayHello(String nama) throws RemoteException;
}
