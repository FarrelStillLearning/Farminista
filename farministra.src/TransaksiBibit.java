package com.mycompany.farminista;

import java.sql.Date;

public class TransaksiBibit {
    private int idTransaksi;
    private int idSupplyBibit;
    private int idPetani;
    private int jumlah;
    private double totalHarga;
    private String statusTanam;
    private Date tanggalSupply;

    public TransaksiBibit(int idTransaksi, int idSupplyBibit, int idPetani, int jumlah, double totalHarga, String statusTanam, Date tanggalSupply) {
        this.idTransaksi = idTransaksi;
        this.idSupplyBibit = idSupplyBibit;
        this.idPetani = idPetani;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.statusTanam = statusTanam;
        this.tanggalSupply = tanggalSupply;
    }

    // Getters dan Setters
    public int getIdTransaksi() { return idTransaksi; }
    public int getIdSupplyBibit() { return idSupplyBibit; }
    public int getIdPetani() { return idPetani; }
    public int getJumlah() { return jumlah; }
    public double getTotalHarga() { return totalHarga; }
    public String getStatusTanam() { return statusTanam; }
    public Date getTanggalSupply() { return tanggalSupply; }

    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    public void setIdSupplyBibit(int idSupplyBibit) { this.idSupplyBibit = idSupplyBibit; }
    public void setIdPetani(int idPetani) { this.idPetani = idPetani; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }
    public void setStatusTanam(String statusTanam) { this.statusTanam = statusTanam; }
    public void setTanggalSupply(Date tanggalSupply) { this.tanggalSupply = tanggalSupply; }
}
