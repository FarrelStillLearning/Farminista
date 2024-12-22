package model;

import java.time.LocalDate;

public class Penjualan {
    private int idPenjualan;
    private int idProduk;
    private int idPetani;
    private int jumlah;
    private double totalHarga;
   
    // Constructor
    public Penjualan(int idPenjualan, int produkId, int jumlah, double totalHarga) {
        this.idPenjualan = idPenjualan;
        this.idProduk = produkId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
    }

    public Penjualan(int produkId, int jumlah, double totalHarga) {
        this.idProduk = produkId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
    }

    public Penjualan(int i, int idProduk, int idPetani, int jumlahProduk, double totalHarga) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters and Setters
    public int getIdPenjualan() {
        return idPenjualan;
    }

    public void setIdPenjualan(int idPenjualan) {
        this.idPenjualan = idPenjualan;
    }

    public int getProdukId() {
        return idProduk;
    }

    public void setProdukId(int produkId) {
        this.idProduk = produkId;
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


    @Override
    public String toString() {
        return "Penjualan{" +
                "idPenjualan=" + idPenjualan +
                ", produkId=" + idProduk +
                ", jumlah=" + jumlah +
                ", totalHarga=" + totalHarga +
                '}';
    }

    public int getIdPetani() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
