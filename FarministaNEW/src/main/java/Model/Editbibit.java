package Model;  
  
public class Editbibit {  
    private int no; // Nomor urut untuk TableView  
    private int idSupplyBibit;  
    private int idBibit;  
    private int idSupplier;  
    private double harga;  
    private String bibit;  
    private String supplier;  
  
    // Constructor untuk kebutuhan TableView  
    public Editbibit(int idSupplyBibit, String bibit, String supplier, double harga) {  
        this.idSupplyBibit = idSupplyBibit; // Inisialisasi ID supply bibit  
        this.bibit = bibit;  
        this.supplier = supplier;  
        this.harga = harga;  
    }  
  
    // Constructor lengkap  
    public Editbibit(int no, String bibit, String supplier, double harga, int idBibit, int idSupplier) {    
        this.no = no;    
        this.bibit = bibit;    
        this.supplier = supplier;    
        this.harga = harga;    
        this.idBibit = idBibit;    
        this.idSupplier = idSupplier;    
    }    
  
    // Getter dan Setter  
    public int getNo() {  
        return no;  
    }  
  
    public void setNo(int no) {  
        this.no = no;  
    }  
  
    public int getIdSupplyBibit() {  
        return idSupplyBibit;  
    }  
  
    public void setIdSupplyBibit(int idSupplyBibit) {  
        this.idSupplyBibit = idSupplyBibit;  
    }  
      
    public int getIdBibit() {  
        return idBibit;  
    }  
  
    public void setIdBibit(int idBibit) {  
        this.idBibit = idBibit;  
    }  
  
    public int getIdSupplier() { // Renamed for consistency  
        return idSupplier;  
    }  
  
    public void setIdSupplier(int idSupplier) { // Renamed for consistency  
        this.idSupplier = idSupplier;  
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
  
    public double getHarga() {  
        return harga;  
    }  
  
    public void setHarga(double harga) {  
        this.harga = harga;  
    }  
}  
