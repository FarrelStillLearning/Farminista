package com.mycompany.farminista;

import java.util.Scanner;

public class Farminista {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Farminista System!");
            System.out.println("1. Sign up");
            System.out.println("2. Sign in");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleSignUp(scanner);
                    break;
                case 2:
                    handleSignIn(scanner);
                    break;
                case 3:
                    exit = true; // Keluar dari aplikasi
                    System.out.println("Terima kasih telah menggunakan aplikasi Farminista.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    // Metode untuk menangani proses Sign Up
    private static void handleSignUp(Scanner scanner) {
        System.out.println("Enter a username: ");
        String username = scanner.nextLine();
        System.out.println("Enter a password: ");
        String password = scanner.nextLine();

        // Set default role as "pengguna"
        String role = "pengguna"; 

        // Buat objek user dan registrasi
        User user = new User(username, password, role);
        if (user.register()) {
            System.out.println("Sign up successful!");
        } else {
            System.out.println("Sign up failed.");
        }
    }

    // Metode untuk menangani proses Sign In
    private static void handleSignIn(Scanner scanner) {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        // Buat objek user untuk login
        User user = new User(username, password);
        if (user.login()) {
            System.out.println("Sign in successful!");

            // Periksa peran pengguna setelah login
            if (user.isAdmin()) {
                // Admin menu
                AdminServices adminServices = new AdminServices();
                adminServices.showMenu(); // Menu untuk admin
            } else {
                // Tampilkan sub-menu setelah login untuk pengguna biasa
                showSubMenu(scanner); 
            }
        } else {
            System.out.println("Sign in failed.");
        }
    }

    // Sub-menu setelah pengguna berhasil login
    private static void showSubMenu(Scanner scanner) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println("\nMenu Rekap:");
            System.out.println("1. Rekap Bibit");
            System.out.println("2. Rekap Produk");
            System.out.println("3. Rekap Penjualan");
            System.out.println("4. Kembali ke Menu Utama");

            int subMenuChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (subMenuChoice) {
                case 1:
                    RekapDataService rekapDataService = new RekapDataService();
                    showUserMenu(scanner, rekapDataService); // Tampilkan menu rekap bibit
                    break;
                case 2:
                    ProdukService produkService = new ProdukService();
                    showRekapProdukMenu(scanner, produkService);
                    break;
                case 3:
                    PenjualanService penjualanService = new PenjualanService();
                    showPenjualanMenu(scanner, penjualanService); // Menu untuk penjualan
                    break;
                case 4:
                    backToMainMenu = true; // Kembali ke menu utama (sign-in/sign-up)
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Metode untuk menampilkan menu pengguna (Rekap Bibit)
    private static void showUserMenu(Scanner scanner, RekapDataService rekapDataService) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu Rekap Bibit:");
            System.out.println("1. Tambah Data Bibit");
            System.out.println("2. Tampilkan Data Transaksi");
            System.out.println("3. Hapus Data Transaksi");
            System.out.println("4. Edit Data Transaksi");
            System.out.println("5. Kembali ke Menu Rekap");

            int userChoice = scanner.nextInt();
            scanner.nextLine(); // Mengkonsumsi newline

            switch (userChoice) {
                case 1:
                    rekapDataService.tambahData(); // Tambah data bibit
                    break;
                case 2:
                    rekapDataService.tampilkanData(); // Tampilkan data transaksi
                    break;
                case 3:
                    System.out.print("Masukkan ID Transaksi yang ingin dihapus: ");
                    int idTransaksi = scanner.nextInt();
                    rekapDataService.hapusData(idTransaksi); // Hapus data transaksi
                    break;
                case 4:
                    System.out.print("Masukkan ID Transaksi yang ingin diedit: ");
                    int idTransaksiEdit = scanner.nextInt();
                    //rekapDataService.editData(idTransaksiEdit); // Edit data transaksi
                    break;
                case 5:
                    exit = true; // Kembali ke menu rekap
                    System.out.println("Kembali ke Menu Rekap.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Metode untuk menampilkan menu Rekap Produk
    private static void showRekapProdukMenu(Scanner scanner, ProdukService produkService) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nRekap Produk Menu:");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Lihat Stok Produk");
            System.out.println("3. Kembali ke menu sebelumnya");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    produkService.tambahProduk(); // Menambah produk baru
                    break;
                case 2:
                    //produkService.stokProduk(); // Menampilkan stok produk berdasarkan id_bibit
                    break;
                case 3:
                    exit = true; // Kembali ke menu sebelumnya
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Metode untuk menampilkan menu Penjualan
    private static void showPenjualanMenu(Scanner scanner, PenjualanService penjualanService) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenu Penjualan:");
            System.out.println("1. Tambah Penjualan");
            System.out.println("2. Lihat Data Penjualan");
            System.out.println("3. Kembali ke menu sebelumnya");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //penjualanService.tambahPenjualan(); // Menambah penjualan baru
                    break;
                case 2:
                    //penjualanService.lihatPenjualan(); // Menampilkan data penjualan
                    break;
                case 3:
                    exit = true; // Kembali ke menu sebelumnya
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
