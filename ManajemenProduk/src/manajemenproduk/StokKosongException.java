package manajemenproduk;

// Custom Exception — Exception Handling
public class StokKosongException extends Exception {
    public StokKosongException(String message) {
        super(message);
    }
}