package manajemenproduk;

public class Produk {
    private static int autoId = 1;

    private int    id;
    private String nama;
    private double harga;
    private String kategori;
    private int    stok;

    public Produk(String nama, double harga, String kategori, int stok) {
        this.id       = autoId++;
        this.nama     = nama;
        this.harga    = harga;
        this.kategori = kategori;
        this.stok     = stok;
    }

    public int    getId()          { return id; }
    public String getNama()        { return nama; }
    public double getHarga()       { return harga; }
    public String getKategori()    { return kategori; }
    public int    getStok()        { return stok; }

    public void setNama(String v)     { this.nama     = v; }
    public void setHarga(double v)    { this.harga    = v; }
    public void setKategori(String v) { this.kategori = v; }
    public void setStok(int v)        { this.stok     = v; }

    @Override
    public String toString() {
        return String.format("[%d] %s | Rp%.0f | %s | Stok: %d",
                id, nama, harga, kategori, stok);
    }
}