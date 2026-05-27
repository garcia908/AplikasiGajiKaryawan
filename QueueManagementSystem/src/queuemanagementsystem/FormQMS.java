package queuemanagementsystem;
 
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
 
public class FormQMS extends JFrame {
 
    // ===== DATA =====
    private int nomorUrut = 0;
    private int sedangDilayani = 0;
    private Queue<Integer> antrianQueue = new LinkedList<>();
    private final String[] KOLOM = {"No. Antrian", "Nama", "Keperluan", "Status", "Jam Daftar"};
    private DefaultTableModel tableModel;
 
    // ===== KOMPONEN UI =====
    private JLabel lblNomorSedang;
    private JLabel lblNamaSedang;
    private JLabel lblTotalAntrian;
    private JTextField txtNama;
    private JTextField txtKeperluan;
    private JTable tabelAntrian;
    private JButton btnAmbilNomor;
    private JButton btnPanggil;
    private JButton btnSelesai;
    private JButton btnReset;
 
    // ===== WARNA =====
    private final Color BIRU      = new Color(41, 128, 185);
    private final Color HIJAU     = new Color(39, 174, 96);
    private final Color ORANYE    = new Color(230, 126, 34);
    private final Color MERAH     = new Color(192, 57, 43);
    private final Color BG_GELAP  = new Color(44, 62, 80);
    private final Color BG_TERANG = new Color(236, 240, 241);
 
    public FormQMS() {
        initComponents();
        setTitle("Sistem Manajemen Antrian (QMS)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(800, 560));
        setLocationRelativeTo(null);
    }
 
    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_TERANG);
 
        add(buatHeader(), BorderLayout.NORTH);
        add(buatPanelKiri(), BorderLayout.WEST);
        add(buatPanelKanan(), BorderLayout.CENTER);
        add(buatFooter(), BorderLayout.SOUTH);
 
        btnAmbilNomor.addActionListener(e -> ambilNomor());
        btnPanggil.addActionListener(e -> panggilBerikutnya());
        btnSelesai.addActionListener(e -> selesaiDilayani());
        btnReset.addActionListener(e -> resetAntrian());
 
        txtNama.addActionListener(e -> txtKeperluan.requestFocus());
        txtKeperluan.addActionListener(e -> ambilNomor());
    }
 
    private JPanel buatHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_GELAP);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));
 
        JLabel lblJudul = new JLabel("🎫  SISTEM MANAJEMEN ANTRIAN", SwingConstants.LEFT);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);
 
        JLabel lblSub = new JLabel("Queue Management System", SwingConstants.RIGHT);
        lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblSub.setForeground(new Color(189, 195, 199));
 
        panel.add(lblJudul, BorderLayout.WEST);
        panel.add(lblSub, BorderLayout.EAST);
        return panel;
    }
 
    private JPanel buatPanelKiri() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_TERANG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 8));
        panel.setPreferredSize(new Dimension(300, 0));
 
        panel.add(buatDisplayNomor());
        panel.add(Box.createVerticalStrut(12));
        panel.add(buatFormInput());
        panel.add(Box.createVerticalStrut(12));
        panel.add(buatKontrolPetugas());
        panel.add(Box.createVerticalGlue());
 
        return panel;
    }
 
    private JPanel buatDisplayNomor() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(HIJAU);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        panel.setMaximumSize(new Dimension(300, 160));
 
        JLabel lblKet = new JLabel("NOMOR SEDANG DILAYANI", SwingConstants.CENTER);
        lblKet.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblKet.setForeground(new Color(214, 255, 214));
 
        lblNomorSedang = new JLabel("-", SwingConstants.CENTER);
        lblNomorSedang.setFont(new Font("Segoe UI", Font.BOLD, 62));
        lblNomorSedang.setForeground(Color.WHITE);
 
        lblNamaSedang = new JLabel("", SwingConstants.CENTER);
        lblNamaSedang.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNamaSedang.setForeground(new Color(214, 255, 214));
 
        lblTotalAntrian = new JLabel("Menunggu: 0 orang", SwingConstants.CENTER);
        lblTotalAntrian.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTotalAntrian.setForeground(new Color(214, 255, 214));
 
        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        panelInfo.setOpaque(false);
        panelInfo.add(lblNamaSedang);
        panelInfo.add(lblTotalAntrian);
 
        panel.add(lblKet, BorderLayout.NORTH);
        panel.add(lblNomorSedang, BorderLayout.CENTER);
        panel.add(panelInfo, BorderLayout.SOUTH);
 
        return panel;
    }
 
    private JPanel buatFormInput() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(2, 8, 8, 8),
                "Daftar Antrian Baru"
            )
        ));
        panel.setMaximumSize(new Dimension(300, 170));
 
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 5, 2, 5);
        g.weightx = 1.0;
 
        g.gridx = 0; g.gridy = 0;
        panel.add(buatLabel("Nama Pelanggan:"), g);
 
        g.gridy = 1;
        txtNama = buatTextField("Masukkan nama...");
        panel.add(txtNama, g);
 
        g.gridy = 2;
        panel.add(buatLabel("Keperluan:"), g);
 
        g.gridy = 3;
        txtKeperluan = buatTextField("Masukkan keperluan...");
        panel.add(txtKeperluan, g);
 
        g.gridy = 4;
        g.insets = new Insets(8, 5, 5, 5);
        btnAmbilNomor = buatTombol("Ambil Nomor Antrian", BIRU);
        panel.add(btnAmbilNomor, g);
 
        return panel;
    }
 
    private JPanel buatKontrolPetugas() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 6));
        panel.setBackground(BG_TERANG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(2, 8, 8, 8),
                "Kontrol Petugas"
            )
        ));
        panel.setMaximumSize(new Dimension(300, 145));
 
        btnPanggil = buatTombol("Panggil Berikutnya", HIJAU);
        btnSelesai = buatTombol("Selesai Dilayani", ORANYE);
        btnReset   = buatTombol("Reset Semua Antrian", MERAH);
 
        panel.add(btnPanggil);
        panel.add(btnSelesai);
        panel.add(btnReset);
 
        return panel;
    }
 
    private JPanel buatPanelKanan() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_TERANG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 8, 15, 15));
 
        JPanel subHeader = new JPanel(new BorderLayout());
        subHeader.setBackground(BG_TERANG);
        subHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
 
        JLabel lblJudulTabel = new JLabel("Daftar Semua Antrian");
        lblJudulTabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblJudulTabel.setForeground(BG_GELAP);
        subHeader.add(lblJudulTabel, BorderLayout.WEST);
 
        tableModel = new DefaultTableModel(KOLOM, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        tabelAntrian = new JTable(tableModel);
        tabelAntrian.setRowHeight(30);
        tabelAntrian.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelAntrian.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelAntrian.getTableHeader().setBackground(BG_GELAP);
        tabelAntrian.getTableHeader().setForeground(Color.WHITE);
        tabelAntrian.setGridColor(new Color(220, 220, 220));
        tabelAntrian.setSelectionBackground(new Color(174, 214, 241));
        tabelAntrian.setShowVerticalLines(false);
        tabelAntrian.setIntercellSpacing(new Dimension(0, 1));
 
        int[] lebar = {90, 140, 180, 100, 90};
        for (int i = 0; i < lebar.length; i++) {
            tabelAntrian.getColumnModel().getColumn(i).setPreferredWidth(lebar[i]);
        }
 
        tabelAntrian.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());
 
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabelAntrian.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelAntrian.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
 
        JScrollPane scrollPane = new JScrollPane(tabelAntrian);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
 
        panel.add(subHeader, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
 
        return panel;
    }
 
    private JPanel buatFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG_GELAP);
        panel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
 
        JLabel lbl = new JLabel("Sistem Manajemen Antrian  |  Pemrograman II");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(new Color(149, 165, 166));
        panel.add(lbl);
 
        return panel;
    }
 
    // ===================================================
    // LOGIKA ANTRIAN
    // ===================================================
 
    private void ambilNomor() {
        String nama = txtNama.getText().trim();
        String keperluan = txtKeperluan.getText().trim();
 
        if (nama.isEmpty()) {
            showWarning("Nama pelanggan tidak boleh kosong!");
            txtNama.requestFocus();
            return;
        }
        if (keperluan.isEmpty()) {
            showWarning("Keperluan tidak boleh kosong!");
            txtKeperluan.requestFocus();
            return;
        }
 
        nomorUrut++;
        String waktu = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String nomorStr = String.format("%03d", nomorUrut);
 
        tableModel.addRow(new Object[]{nomorStr, nama, keperluan, "Menunggu", waktu});
        antrianQueue.add(nomorUrut);
 
        txtNama.setText("");
        txtKeperluan.setText("");
        txtNama.requestFocus();
 
        updateDisplay();
 
        JOptionPane.showMessageDialog(this,
            "Nomor Antrian Anda: " + nomorStr + "\nNama: " + nama + "\nKeperluan: " + keperluan + "\n\nSilakan menunggu dipanggil.",
            "Berhasil Daftar",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
 
    private void panggilBerikutnya() {
        if (antrianQueue.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada antrian yang menunggu!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
 
        if (sedangDilayani > 0) {
            updateStatusRow(sedangDilayani, "Selesai");
        }
 
        sedangDilayani = antrianQueue.poll();
        String nomorStr = String.format("%03d", sedangDilayani);
        String namaPelanggan = getNamaPelanggan(sedangDilayani);
 
        lblNomorSedang.setText(nomorStr);
        lblNamaSedang.setText(namaPelanggan);
        updateStatusRow(sedangDilayani, "Dilayani");
        updateDisplay();
    }
 
    private void selesaiDilayani() {
        if (sedangDilayani == 0) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada yang sedang dilayani!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
 
        updateStatusRow(sedangDilayani, "Selesai");
        sedangDilayani = 0;
        lblNomorSedang.setText("-");
        lblNamaSedang.setText("");
        updateDisplay();
    }
 
    private void resetAntrian() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin mereset semua antrian?\nData yang sudah ada akan dihapus.",
            "Konfirmasi Reset",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            nomorUrut = 0;
            sedangDilayani = 0;
            antrianQueue.clear();
            tableModel.setRowCount(0);
            lblNomorSedang.setText("-");
            lblNamaSedang.setText("");
            updateDisplay();
        }
    }
 
    // ===================================================
    // HELPER
    // ===================================================
 
    private void updateStatusRow(int nomor, String status) {
        String target = String.format("%03d", nomor);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (target.equals(tableModel.getValueAt(i, 0))) {
                tableModel.setValueAt(status, i, 3);
                break;
            }
        }
    }
 
    private String getNamaPelanggan(int nomor) {
        String target = String.format("%03d", nomor);
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (target.equals(tableModel.getValueAt(i, 0))) {
                return (String) tableModel.getValueAt(i, 1);
            }
        }
        return "";
    }
 
    private void updateDisplay() {
        lblTotalAntrian.setText("Menunggu: " + antrianQueue.size() + " orang");
    }
 
    private void showWarning(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }
 
    private JTextField buatTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setToolTipText(placeholder);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }
 
    private JLabel buatLabel(String teks) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(new Color(100, 100, 100));
        return lbl;
    }
 
    private JButton buatTombol(String teks, Color warna) {
        JButton btn = new JButton(teks);
        btn.setBackground(warna);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    // ===================================================
    // RENDERER STATUS
    // ===================================================
 
    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
 
            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
 
            if (!isSelected) {
                String status = value != null ? value.toString() : "";
                switch (status) {
                    case "Menunggu":
                        setBackground(new Color(253, 243, 207));
                        setForeground(new Color(138, 109, 59));
                        break;
                    case "Dilayani":
                        setBackground(new Color(209, 236, 241));
                        setForeground(new Color(31, 97, 141));
                        break;
                    case "Selesai":
                        setBackground(new Color(212, 239, 223));
                        setForeground(new Color(30, 132, 73));
                        break;
                    default:
                        setBackground(Color.WHITE);
                        setForeground(Color.BLACK);
                }
            }
            return c;
        }
    }
}