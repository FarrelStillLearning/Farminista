package model;

public class Penjualan {
    private int no;
    private int idPenjualan; // ID penjualan
    private int produkId;           // ID produk
    private int idPetani;           // ID petani
    private int jumlah;              // Jumlah produk yang dijual
    private double totalHarga;       // Total harga penjualan
    private String namaBibit;        // Nama bibit
    private int idBibit;            // ID bibit

    // Constructor dengan ID penjualan
    public Penjualan(int idPenjualan, int produkId, int jumlah, double totalHarga, int idPetani) {
        this.idPenjualan = idPenjualan;
        this.produkId = produkId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.idPetani = idPetani;
    }

    // Constructor tanpa ID penjualan (misalnya untuk insert)
    public Penjualan(int produkId, int idPetani, int jumlah) {
        this(-1, produkId, jumlah, 0.0, idPetani); // Menggunakan constructor lain
    }
    
    public Penjualan(int no, int idPenjualan, String namaBibit, int jumlah, double totalHarga, int produkId, int idBibit) {
        this.idPenjualan = idPenjualan;
        this.produkId = produkId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.idPetani = idPetani; // ID petani
        this.no = no;
        this.namaBibit = namaBibit;
        this.idBibit = idBibit; // ID bibit
    }
    
    public Penjualan(int produkId, String namaBibit) {
        this.produkId = produkId;
        this.namaBibit = namaBibit;
    }

    // Getters and Setters
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
    
    public int getIdPenjualan() {
        return idPenjualan;
    }

    public int getProdukId() {
        return produkId;
    }

    public void setProdukId(int produkId) {
        this.produkId = produkId;
    }

    public int getIdPetani() {
        return idPetani;
    }

    public void setIdPetani(int idPetani) {
        this.idPetani = idPetani;
    }
    
    public int getIdBibit() {
        return idBibit; // Getter untuk idBibit
    }

    public String getNamaBibit() {
        return namaBibit; // Getter untuk namaBibit
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        if (jumlah < 0) {
            throw new IllegalArgumentException("Jumlah tidak boleh negatif.");
        }
        this.jumlah = jumlah;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        if (totalHarga < 0) {
            throw new IllegalArgumentException("Total harga tidak boleh negatif.");
        }
        this.totalHarga = totalHarga;
    }

    @Override
    public String toString() {
        return "Penjualan{" +
                "no=" + no +
                ", idPenjualan=" + idPenjualan +
                ", produkId=" + produkId +
                ", idPetani=" + idPetani +
                ", jumlah=" + jumlah +
                ", totalHarga=" + totalHarga +
                ", namaBibit='" + namaBibit + '\'' +
                ", idBibit=" + idBibit +
                '}';
    }
}
