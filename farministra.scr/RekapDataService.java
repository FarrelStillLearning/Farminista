package com.mycompany.farminista;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class RekapDataService {
    private final TransaksiBibitDAO transaksiBibitDAO = new TransaksiBibitDAO();

    public void tambahData() {
        Scanner scanner = new Scanner(System.in);
        
        // Input data transaksi
        System.out.print("Masukkan ID Supply Bibit: ");
        int idSupplyBibit = scanner.nextInt();
        System.out.print("Masukkan ID Petani: ");
        int idPetani = scanner.nextInt();
        System.out.print("Masukkan Jumlah Bibit (kg): ");
        int jumlah = scanner.nextInt();
        System.out.print("Masukkan Total Harga: ");
        double totalHarga = scanner.nextDouble();
        System.out.print("Masukkan Status Tanam: ");
        String statusTanam = scanner.next();
        Date tanggalSupply = new Date(System.currentTimeMillis()); // Menggunakan tanggal saat ini

        // Membuat objek TransaksiBibit
        TransaksiBibit transaksi = new TransaksiBibit(0, idSupplyBibit, idPetani, jumlah, totalHarga, statusTanam, tanggalSupply);
        
        // Menyimpan transaksi
        transaksiBibitDAO.tambahTransaksi(transaksi);
    }

    public void tampilkanData() {
        List<TransaksiBibit> transaksiList = transaksiBibitDAO.getAllTransaksi();
        System.out.println("\nData Transaksi Bibit:");
        for (TransaksiBibit transaksi : transaksiList) {
            System.out.println("ID Transaksi: " + transaksi.getIdTransaksi() +
                               ", ID Bibit: " + transaksi.getIdSupplyBibit() +
                               ", Jumlah: " + transaksi.getJumlah() +
                               ", Total Harga: " + transaksi.getTotalHarga() +
                               ", Status Tanam: " + transaksi.getStatusTanam() +
                               ", Tanggal Supply: " + transaksi.getTanggalSupply());
        }
    }

    public void hapusData(int idTransaksi) {
        transaksiBibitDAO.hapusTransaksi(idTransaksi);
    }

    public void editData(TransaksiBibit transaksi) {
        transaksiBibitDAO.editTransaksi(transaksi);
    }
}
