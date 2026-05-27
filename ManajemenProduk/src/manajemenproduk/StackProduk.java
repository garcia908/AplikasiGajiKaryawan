package manajemenproduk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Struktur Data Stack custom berbasis ArrayList
public class StackProduk {
    private final ArrayList<Produk> stack = new ArrayList<>();

    // ── Stack Operations ──────────────────────────────────────────────────────

    public void push(Produk p) {
        stack.add(p);
        AppLogger.log("PUSH  » " + p);
    }

    // Hapus produk teratas — throw jika stok 0
    public Produk pop() throws StokKosongException {
        if (isEmpty()) throw new StokKosongException("Stack kosong, tidak ada produk!");
        Produk p = stack.remove(stack.size() - 1);
        AppLogger.log("POP   » " + p.getNama());
        return p;
    }

    public Produk peek() {
        return isEmpty() ? null : stack.get(stack.size() - 1);
    }

    public boolean isEmpty() { return stack.isEmpty(); }
    public int     size()    { return stack.size(); }

    // Hapus produk berdasarkan nama (tombol Hapus di GUI)
    public void hapusByNama(String nama) throws StokKosongException {
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getNama().equalsIgnoreCase(nama)) {
                if (stack.get(i).getStok() == 0) {
                    throw new StokKosongException(
                        "Stok produk \"" + nama + "\" sudah 0!\nTidak bisa dihapus dari gudang.");
                }
                Produk removed = stack.remove(i);
                AppLogger.log("HAPUS » " + removed.getNama());
                return;
            }
        }
        throw new StokKosongException("Produk \"" + nama + "\" tidak ditemukan di stack.");
    }

    // ── Search ────────────────────────────────────────────────────────────────

    public List<Produk> cariByNama(String query) {
        List<Produk> hasil = new ArrayList<>();
        for (Produk p : stack) {
            if (p.getNama().toLowerCase().contains(query.toLowerCase())) {
                hasil.add(p);
            }
        }
        AppLogger.log("CARI  » query=\"" + query + "\" | ditemukan: " + hasil.size());
        return hasil;
    }

    // ── Sort ──────────────────────────────────────────────────────────────────

    public List<Produk> sortByHarga() {
        List<Produk> sorted = new ArrayList<>(stack);
        sorted.sort(Comparator.comparingDouble(Produk::getHarga));
        AppLogger.log("SORT  » berdasarkan Harga");
        return sorted;
    }

    public List<Produk> sortByKategori() {
        List<Produk> sorted = new ArrayList<>(stack);
        sorted.sort(Comparator.comparing(Produk::getKategori));
        AppLogger.log("SORT  » berdasarkan Kategori");
        return sorted;
    }

    // ── Util ──────────────────────────────────────────────────────────────────

    public List<Produk> getAll() {
        return new ArrayList<>(stack);
    }
}