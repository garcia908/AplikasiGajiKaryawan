package queuemanagementsystem;
 
public class QueueManagementSystem {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormQMS().setVisible(true);
        });
    }
}