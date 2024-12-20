package Model;
public class Bibit {
    private int idBibit;
    private String nama;

    // Constructor
    public Bibit(int idBibit, String nama) {
    this.idBibit = idBibit;
    this.nama = nama;
}

    // Getter dan Setter
    public int getIdBibit() {
        return idBibit;
    }
    
    public void setIdBibit(int idBibit) {
        this.idBibit = idBibit;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

@Override
    public String toString() {
        return nama; // Hanya tampilkan nama bibit di UI
    }
}
