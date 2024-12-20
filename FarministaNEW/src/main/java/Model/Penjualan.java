package model;

import java.time.LocalDate;

public class Penjualan {
    private int idPenjualan;
    private int produkId;
    private int jumlah;
    private double totalHarga;
    private LocalDate tanggal;

    // Constructor
    public Penjualan(int idPenjualan, int produkId, int jumlah, double totalHarga, LocalDate tanggal) {
        this.idPenjualan = idPenjualan;
        this.produkId = produkId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    public Penjualan(int produkId, int jumlah, double totalHarga, LocalDate tanggal) {
        this.produkId = produkId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    // Getters and Setters
    public int getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public int getProdukId() {
        return produkId;
    }

    public void setProdukId(int produkId) {
        this.produkId = produkId;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return "Penjualan{" +
                "idPenjualan=" + idPenjualan +
                ", produkId=" + produkId +
                ", jumlah=" + jumlah +
                ", totalHarga=" + totalHarga +
                ", tanggal=" + tanggal +
                '}';
    }
}
