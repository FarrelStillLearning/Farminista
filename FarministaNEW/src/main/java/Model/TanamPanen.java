package Model;  
  
public class TanamPanen {
    private int idTanamPanen;
    private int idTransaksi;
    private int no; // Nomor urut  
    private String bibit; // Nama bibit  
    private String supplier; // Nama supplier  
    private String tanggalSupply; // Tanggal supply  
    private String tanggalTanam; // Tanggal tanam  
    private String tanggalPanen; // Tanggal panen  
  
    public TanamPanen(int no, String bibit, String supplier, String tanggalSupply, String tanggalTanam, String tanggalPanen, int idTanamPanen, int idTransaksi) {  
        this.no = no;  
        this.bibit = bibit;  
        this.supplier = supplier;  
        this.tanggalSupply = tanggalSupply;  
        this.tanggalTanam = tanggalTanam;  
        this.tanggalPanen = tanggalPanen;  
        this.idTanamPanen = idTanamPanen;
        this.idTransaksi = idTransaksi; // Inisialisasi id_transaksi  
    }  
    
    public TanamPanen(int no, String bibit, String supplier, String tanggalSupply, String tanggalTanam, String tanggalPanen) {  
        this.no = no;  
        this.bibit = bibit;  
        this.supplier = supplier;  
        this.tanggalSupply = tanggalSupply;  
        this.tanggalTanam = tanggalTanam;  
        this.tanggalPanen = tanggalPanen;   
    }  
    
  
    // Getters and Setters 
    public int getIdTanamPanen() {  
        return idTanamPanen;  
    }  
  
    public void setIdTanamPanen(int idTanamPanen) {  
        this.idTanamPanen = idTanamPanen;  
    }  
    
    public int getIdTransaksi() {  
        return idTransaksi;  
    }  
  
    public void setIdTransaksi(int idTransaksi) {  
        this.idTransaksi = idTransaksi;  
    }  
    
    public int getNo() {  
        return no;  
    }  
  
    public void setNo(int no) {  
        this.no = no;  
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
  
    public String getTanggalSupply() {  
        return tanggalSupply;  
    }  
  
    public void setTanggalSupply(String tanggalSupply) {  
        this.tanggalSupply = tanggalSupply;  
    }  
  
    public String getTanggalTanam() {  
        return tanggalTanam;  
    }  
  
    public void setTanggalTanam(String tanggalTanam) {  
        this.tanggalTanam = tanggalTanam;  
    }  
  
    public String getTanggalPanen() {  
        return tanggalPanen;  
    }  
  
    public void setTanggalPanen(String tanggalPanen) {  
        this.tanggalPanen = tanggalPanen;  
    }  
}  
