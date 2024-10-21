package com.mycompany.farminista;

import java.sql.*;
import java.util.Scanner;

public class AdminServices {

    public void manageBibit() {
        
    }

    public void manageSupplier() {
        
    }

    public void setHargaSupplierForBibit() {
        
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Bibit");
            System.out.println("2. Manage Supplier");
            System.out.println("3. Set Harga Bibit dari Supplier");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manageBibit(); // Manage bibit
                    break;
                case 2:
                    manageSupplier(); // Manage supplier
                    break;
                case 3:
                    setHargaSupplierForBibit(); // Set harga bibit dari supplier
                    break;
                case 4:
                    System.out.println("Exiting Admin Menu.");
                    break;
                default: 
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);
    }
}