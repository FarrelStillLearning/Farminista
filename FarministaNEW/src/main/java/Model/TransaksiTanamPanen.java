package Model;

public class TransaksiTanamPanen {
    private int no;
    private int idProduk;
    private String bibit;
    private double hargaProduk;
    private int idBibit;
    private int stok;
    private int idPetani;

    // Constructor for creating a new transaction with all fields
    public TransaksiTanamPanen(int no, int idBibit, String bibit, int stok, double harga, int idPetani) {
        this.no = no;
        this.idBibit = idBibit;
        this.bibit = bibit;
        this.stok = stok;
        this.hargaProduk = harga;
        this.idPetani = idPetani; // Set the id_petani
    }

    // Constructor for updating an existing transaction
    public TransaksiTanamPanen(int no, int idProduk,int idBibit, String bibit, int stok, double hargaProduk) {
        this.no = no;
        this.idProduk = idProduk; // Initialize id_produk
        this.idBibit = idBibit;
        this.bibit = bibit;
        this.stok = stok;
        this.hargaProduk = hargaProduk;
    }

    // Constructor for creating a transaction without 'no' and 'idPetani'
    public TransaksiTanamPanen(int idProduk, String bibit, int stok, double hargaProduk, int idBibit) {
        this.idProduk = idProduk;
        this.bibit = bibit;
        this.stok = stok;
        this.hargaProduk = hargaProduk;
        this.idBibit = idBibit; // Initialize idBibit
    }

    // Getters and Setters
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public double getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(double hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public int getIdBibit() {
        return idBibit;
    }

    public void setIdBibit(int idBibit) {
        this.idBibit = idBibit;
    }

    public int getIdPetani() {
        return idPetani;
    }

    public void setIdPetani(int idPetani) {
        this.idPetani = idPetani;
    }

    public String getBibit() {
        return bibit;
    }

    public void setBibit(String bibit) {
        this.bibit = bibit;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
