/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.echo.sayhello.server;
import com.echo.sayhello.SayHello;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author UsEr
 */
public class SayHelloServer extends UnicastRemoteObject implements SayHello {

    public SayHelloServer() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String nama) throws RemoteException {
        System.out.println("Metode sayHello Dijalankan");
        return "Halo " + nama + "! Salam dari Server!";
    }
}
