/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.farminista;

/**
 *
 * @author ASUS
 */
public class Produk {
    private int id;
    private String nama;
    private int stok;

    public Produk(int id, String nama, int stok) {
        this.id = id;
        this.nama = nama;
        this.stok = stok;
    }

    // Getters dan Setters
    public int getId()
    { 
        return id; 
    }
    public String getNama()
    { 
        return nama; 
    }
    public int getStok() 
    { 
        return stok; 
    }
}
