package Model;

public class StatusTanam {
    private int no; // Nomor urut untuk TableView
    private int idTransaksi;
    private int idSupplyBibit;
    private int idSupplier;
    private int idBibit;
    private String bibit;
    private String supplier;
    private String status;
    private String tanggalSupply;
    
    
    public StatusTanam(){
    }
    
    public StatusTanam(int no, int idTransaksi, int idBibit, int idSupplier, String status, String tanggalSupply, int idSupplyBibit) {
        this.no = no;
        this.idTransaksi = idTransaksi;
        this.idBibit = idBibit;
        this.idSupplier = idSupplier;
        this.status = status;
        this.tanggalSupply = tanggalSupply;
        this.idSupplyBibit = idSupplyBibit;
    }
    
    public StatusTanam(int no, int idTransaksi, String bibit, String supplier, String status, String tanggalSupply, int idSupplyBibit) {
        this.no = no;
        this.idTransaksi = idTransaksi;
        this.bibit = bibit;
        this.supplier = supplier;
        this.status = status;
        this.tanggalSupply = tanggalSupply;
        this.idSupplyBibit = idSupplyBibit;
    }
    
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
    
    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }
    
    public int getIdBibit() {
        return idBibit;
    }

    public void setIdBibit(int idBibit) {
        this.idBibit = idBibit;
    }
    
    public String getBibit() {
        return bibit;
    }

    public void setBibit(String bibit) {
        this.bibit = bibit;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggalSupply() {
        return tanggalSupply;
    }

    public void setTanggalSupply(String tanggalSupply) {
        this.tanggalSupply = tanggalSupply;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}
