package manajemenproduk;

import javax.swing.JTextArea;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Debugging / Logging System
public class AppLogger {
    private static JTextArea logArea;
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void setLogArea(JTextArea area) {
        logArea = area;
    }

    public static void log(String pesan) {
        String entry = "[" + LocalTime.now().format(FMT) + "] " + pesan + "\n";
        System.out.print(entry);
        if (logArea != null) {
            logArea.append(entry);
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }
}