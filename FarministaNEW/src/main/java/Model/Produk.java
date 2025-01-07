package model;

import java.time.LocalDate;

public class Produk {
    private int idProduk;
    private String bibit;
    private int idPetani;
    private int idBibit;
    private int jumlahPanen;

    // Constructor
    
    public Produk(int idProduk, int idPetani, int idBibit, String bibit) {
            this.idProduk = idProduk;
            this.idPetani = idPetani;
            this.idBibit = idBibit;
            this.bibit = bibit;
        }
   
    public Produk(int idProduk, String bibit) {
        this.idProduk = idProduk;
        this.bibit = bibit;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public int getIdProduk() {
        return idProduk;
    }
    
    public void setIdPetani(int idPetani) {
        this.idPetani = idPetani;
    }

    public int getIdPetani() {
        return idPetani;
    }
    
    public void setBibit(String bibit) {
        this.bibit = bibit;
    }

    public String getBibit() {
        return bibit;
    }
    
    public void setIdBibit(int idBibit) {
        this.idBibit = idBibit;
    }

    public int getIdBibit() {
        return idBibit;
    }

    public int getStok() {
        return jumlahPanen;
    }

    public void setStok(int stok) {
        this.jumlahPanen = jumlahPanen;
    }
    
    @Override
    public String toString() {
        return bibit; // This will be displayed in the ComboBox
    }
    
}
