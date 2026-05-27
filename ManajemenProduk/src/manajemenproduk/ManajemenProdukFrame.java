package manajemenproduk;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManajemenProdukFrame extends JFrame {

    private final StackProduk stack = new StackProduk();

    // ── Table ─────────────────────────────────────────────────────────────────
    private DefaultTableModel tableModel;
    private JTable table;

    // ── Form Input ────────────────────────────────────────────────────────────
    private JTextField tfNama, tfHarga, tfKategori, tfStok, tfCari;

    // ── Log Area ──────────────────────────────────────────────────────────────
    private JTextArea logArea;

    public ManajemenProdukFrame() {
        setTitle("Manajemen Produk — Toko Kecil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(buildHeaderPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildLogPanel(),    BorderLayout.SOUTH);

        // sambungkan logger ke text area
        AppLogger.setLogArea(logArea);
        AppLogger.log("Aplikasi Manajemen Produk dimulai.");

        // data dummy biar langsung keliatan
        tambahDummy();
    }

    // ── UI Builders ───────────────────────────────────────────────────────────

    private JPanel buildHeaderPanel() {
        JPanel p = new JPanel();
        p.setBackground(new Color(30, 100, 200));
        JLabel lbl = new JLabel("📦 Manajemen Produk");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        p.add(lbl);
        return p;
    }

    private JSplitPane buildCenterPanel() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildTablePanel(), buildFormPanel());
        split.setDividerLocation(520);
        split.setResizeWeight(0.6);
        return split;
    }

    private JScrollPane buildTablePanel() {
        String[] cols = {"ID", "Nama", "Harga (Rp)", "Kategori", "Stok"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(180, 210, 255));
        return new JScrollPane(table);
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Form Produk"));
        p.setPreferredSize(new Dimension(320, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        tfNama     = new JTextField();
        tfHarga    = new JTextField();
        tfKategori = new JTextField();
        tfStok     = new JTextField();
        tfCari     = new JTextField();

        // ── Input Fields ──────────────────────────────────────────────────────
        int row = 0;
        addFormRow(p, gbc, row++, "Nama Produk :",  tfNama);
        addFormRow(p, gbc, row++, "Harga (Rp) :",   tfHarga);
        addFormRow(p, gbc, row++, "Kategori :",      tfKategori);
        addFormRow(p, gbc, row++, "Stok :",          tfStok);

        // ── Tombol TAMBAH & HAPUS ─────────────────────────────────────────────
        JPanel btnPanel1 = new JPanel(new GridLayout(1, 2, 6, 0));
        JButton btnTambah = tombol("➕ Tambah", new Color(34, 139, 34));
        JButton btnHapus  = tombol("🗑 Hapus",  new Color(200, 50, 50));
        btnTambah.addActionListener(e -> actionTambah());
        btnHapus .addActionListener(e -> actionHapus());
        btnPanel1.add(btnTambah);
        btnPanel1.add(btnHapus);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        p.add(btnPanel1, gbc);

        // ── Separator ─────────────────────────────────────────────────────────
        gbc.gridy = row++;
        p.add(new JSeparator(), gbc);

        // ── Search ────────────────────────────────────────────────────────────
        gbc.gridwidth = 1;
        addFormRow(p, gbc, row++, "Cari Nama :", tfCari);

        JPanel btnPanel2 = new JPanel(new GridLayout(1, 2, 6, 0));
        JButton btnCari  = tombol("🔍 Cari",    new Color(30, 100, 200));
        JButton btnReset = tombol("↺ Reset",     new Color(120, 120, 120));
        btnCari .addActionListener(e -> actionCari());
        btnReset.addActionListener(e -> refreshTable(stack.getAll()));
        btnPanel2.add(btnCari);
        btnPanel2.add(btnReset);

        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        p.add(btnPanel2, gbc);

        // ── Separator ─────────────────────────────────────────────────────────
        gbc.gridy = row++;
        p.add(new JSeparator(), gbc);

        // ── Sort ──────────────────────────────────────────────────────────────
        JLabel lblSort = new JLabel("Urutkan :", SwingConstants.LEFT);
        gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
        p.add(lblSort, gbc);

        JPanel btnPanel3 = new JPanel(new GridLayout(1, 2, 6, 0));
        JButton btnSortH = tombol("↑ Harga",    new Color(160, 100, 20));
        JButton btnSortK = tombol("↑ Kategori", new Color(160, 100, 20));
        btnSortH.addActionListener(e -> refreshTable(stack.sortByHarga()));
        btnSortK.addActionListener(e -> refreshTable(stack.sortByKategori()));
        btnPanel3.add(btnSortH);
        btnPanel3.add(btnSortK);

        gbc.gridy = row;
        p.add(btnPanel3, gbc);

        return p;
    }

    private JPanel buildLogPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Debug Log"));
        p.setPreferredSize(new Dimension(0, 120));
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        logArea.setBackground(new Color(30, 30, 30));
        logArea.setForeground(new Color(180, 255, 180));
        p.add(new JScrollPane(logArea), BorderLayout.CENTER);
        return p;
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    private void actionTambah() {
        try {
            String nama     = tfNama.getText().trim();
            String kategori = tfKategori.getText().trim();
            if (nama.isEmpty() || kategori.isEmpty())
                throw new IllegalArgumentException("Nama dan Kategori wajib diisi!");

            double harga = Double.parseDouble(tfHarga.getText().trim());
            int    stok  = Integer.parseInt(tfStok.getText().trim());
            if (harga < 0 || stok < 0)
                throw new IllegalArgumentException("Harga dan Stok tidak boleh negatif!");

            Produk p = new Produk(nama, harga, kategori, stok);
            stack.push(p);
            refreshTable(stack.getAll());
            clearForm();

        } catch (NumberFormatException ex) {
            showError("Harga dan Stok harus berupa angka!");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void actionHapus() {
        // ambil dari baris yang dipilih di tabel
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Pilih produk di tabel yang ingin dihapus!");
            return;
        }
        String nama = tableModel.getValueAt(selectedRow, 1).toString();
        try {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Hapus produk \"" + nama + "\"?", "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                stack.hapusByNama(nama);
                refreshTable(stack.getAll());
            }
        } catch (StokKosongException ex) {
            AppLogger.log("ERROR » " + ex.getMessage());
            showError(ex.getMessage());
        }
    }

    private void actionCari() {
        String query = tfCari.getText().trim();
        if (query.isEmpty()) {
            refreshTable(stack.getAll());
            return;
        }
        List<Produk> hasil = stack.cariByNama(query);
        if (hasil.isEmpty()) {
            showInfo("Produk dengan nama \"" + query + "\" tidak ditemukan.");
        }
        refreshTable(hasil);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void refreshTable(List<Produk> list) {
        tableModel.setRowCount(0);
        for (Produk p : list) {
            tableModel.addRow(new Object[]{
                p.getId(), p.getNama(),
                String.format("%.0f", p.getHarga()),
                p.getKategori(), p.getStok()
            });
        }
    }

    private void clearForm() {
        tfNama.setText("");
        tfHarga.setText("");
        tfKategori.setText("");
        tfStok.setText("");
    }

    private void addFormRow(JPanel p, GridBagConstraints gbc, int row,
                            String label, JTextField field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(field, gbc);
    }

    private JButton tombol(String text, Color bg) {
    JButton b = new JButton(text);
    b.setBackground(bg);
    b.setForeground(Color.WHITE);
    b.setFocusPainted(false);
    b.setFont(new Font("Segoe UI", Font.BOLD, 12));
    b.setOpaque(true);           // ← tambah ini
    b.setBorderPainted(false);   // ← tambah ini
    return b;
}
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void tambahDummy() {
        stack.push(new Produk("Indomie Goreng",  3500,  "Makanan",  100));
        stack.push(new Produk("Aqua 600ml",      4000,  "Minuman",  50));
        stack.push(new Produk("Sabun Lifebuoy",  8000,  "Kebersihan", 30));
        stack.push(new Produk("Kopi Kapal Api",  12000, "Minuman",  25));
        stack.push(new Produk("Chitato",         9500,  "Makanan",  40));
        refreshTable(stack.getAll());
    }
}