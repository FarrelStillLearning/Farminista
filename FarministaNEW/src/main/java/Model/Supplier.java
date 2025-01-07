package Model;

import javafx.scene.control.cell.PropertyValueFactory;

public class Supplier {

    public static void setCellValueFactory(PropertyValueFactory<Object, Object> propertyValueFactory) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private int id_supplier;
    private String nama;
    private int id_supply_bibit; // Add this field
    private double harga;
    // Constructor
    public Supplier(int id_supplier, String nama, double harga) {
        this.id_supplier = id_supplier;
        this.nama = nama;
        this.harga = harga;
    }
    public Supplier(int id_supply_bibit, int id_supplier, String nama, double harga) {
        this.id_supply_bibit = id_supply_bibit;
        this.id_supplier = id_supplier;
        this.nama = nama;
        this.harga = harga;
    }
    // Getters and setters
    public int getIdSupplier() {
        return id_supplier;
    }

    public String getNama() {
        return nama;
    }

    public int getIdSupplyBibit() {
        return id_supply_bibit; // Get id_supply_bibit
    }
    
    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return nama; // Tampilkan nama supplier di UI
    }
}