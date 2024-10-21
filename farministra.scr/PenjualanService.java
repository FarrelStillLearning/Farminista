package com.mycompany.farminista;
import java.util.List;

public class PenjualanService {
    private PenjualanDAO penjualanDAO;

    public PenjualanService() {
        this.penjualanDAO = new PenjualanDAO();
    }

    // Menambahkan penjualan
    public void addPenjualan(int idProduk, int jumlah, double total) {
        Penjualan penjualan = new Penjualan(0, idProduk, jumlah, total); // ID otomatis
        penjualanDAO.addPenjualan(penjualan);
    }

    // Mendapatkan semua penjualan
    public List<Penjualan> getAllPenjualan() {
        return penjualanDAO.getAllPenjualan();
    }

    // Menghapus penjualan
    public void deletePenjualan(int idPenjualan) {
        penjualanDAO.deletePenjualan(idPenjualan);
    }
}
