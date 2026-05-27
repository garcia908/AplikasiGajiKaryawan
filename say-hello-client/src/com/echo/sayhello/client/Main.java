package com.echo.sayhello.client;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormClient().setVisible(true);
            }
        });
    }
}