package com.mycompany.farminista;

public class Penjualan {
    private int idPenjualan;
    private int idProduk;
    private int jumlah;
    private double total;

    public Penjualan(int idPenjualan, int idProduk, int jumlah, double total) {
        this.idPenjualan = idPenjualan;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.total = total;
    }

    // Getter dan Setter
    public int getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
