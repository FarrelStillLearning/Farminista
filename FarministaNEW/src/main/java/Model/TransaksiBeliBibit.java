package Model;

public class TransaksiBeliBibit {
    private int no; // Nomor urut untuk TableView
    private int idTransaksi;
    private int idSupplyBibit;
    private int idPetani;
    private int jumlah;
    private double harga;
    private double totalHarga;
    private String bibit;
    private String supplier;
    private String statusTanam;
    private String tanggalSupply;
    private int idSupplier;
    private int idBibit;

    // Constructor lengkap untuk kebutuhan TableView
    public TransaksiBeliBibit(int no, int idTransaksi, String bibit, String supplier, double harga, int jumlah, String tanggalSupply, int idSupplyBibit) {
        this.no = no;
        this.idTransaksi = idTransaksi;
        this.bibit = bibit;
        this.supplier = supplier;
        this.harga = harga;
        this.tanggalSupply = tanggalSupply;
        this.jumlah = jumlah;
    }

    // Constructor untuk data lainnya jika diperlukan
    public TransaksiBeliBibit(int idTransaksi, int idSupplyBibit, int idPetani, int jumlah, double totalHarga, String statusTanam, String tanggalSupply) {
        this.idTransaksi = idTransaksi;
        this.idSupplyBibit = idSupplyBibit;
        this.idPetani = idPetani;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.statusTanam = statusTanam;
        this.tanggalSupply = tanggalSupply;
    }

    // Getter dan Setter untuk semua properti
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIdSupplyBibit() {
        return idSupplyBibit;
    }

    public void setIdSupplyBibit(int idSupplyBibit) {
        this.idSupplyBibit = idSupplyBibit;
    }

    public int getIdPetani() {
        return idPetani;
    }

    public void setIdPetani(int idPetani) {
        this.idPetani = idPetani;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getBibit() {
        return bibit;
    }

    public void setBibit(String bibit) {
        this.bibit = bibit;
    }
    public int getIdBibit() {
        return this.idBibit;  // Asumsikan idSupplyBibit adalah idBibit yang dimaksud
    }

    public int getIdSupplier() {
        return this.idSupplier;  // Atau properti yang sesuai jika idSupplier ada
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStatusTanam() {
        return statusTanam;
    }

    public void setStatusTanam(String statusTanam) {
        this.statusTanam = statusTanam;
    }

    public String getTanggalSupply() {
        return tanggalSupply;
    }

    public void setTanggalSupply(String tanggalSupply) {
        this.tanggalSupply = tanggalSupply;
    }
    
    @Override
    public String toString() {
        return "TransaksiBeliBibit{" +
               "idTransaksi=" + idTransaksi +
               ", idSupplyBibit=" + idSupplyBibit +
               ", idPetani=" + idPetani +
               ", jumlah=" + jumlah +
               ", totalHarga=" + totalHarga +
               ", bibit='" + bibit + '\'' +
               ", supplier='" + supplier + '\'' +
               ", statusTanam='" + statusTanam + '\'' +
               ", tanggalSupply='" + tanggalSupply + '\'' +
               '}';
    }
}
