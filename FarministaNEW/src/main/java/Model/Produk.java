package model;

public class Produk {
    private int idProduk;
    private String namaProduk;
    private int stok;
    private double harga;

    // Constructor
    public Produk(int idProduk, String namaProduk, int stok, double harga) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.stok = stok;
        this.harga = harga;
    }

    public Produk(String namaProduk, int stok, double harga) {
        this.namaProduk = namaProduk;
        this.stok = stok;
        this.harga = harga;
    }

    // Getters and Setters
    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return "Produk{" +
                "idProduk=" + idProduk +
                ", namaProduk='" + namaProduk + '\'' +
                ", stok=" + stok +
                ", harga=" + harga +
                '}';
    }
}
