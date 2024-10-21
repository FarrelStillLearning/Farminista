# Farminist

## Overview

Farminista is a system designed to help farmers manage their transactions, products, and sales efficiently. The system provides role-based access for both admins and regular users. Admins can manage users, transactions, and products, while regular users can perform tasks such as viewing and updating their own transaction and product records.

## Features

- **User Registration & Authentication**: Users can sign up and log in to the system. Admins have privileged access to administrative functions.
- **Role-Based Access**: Admins can access a menu for managing users and data, while regular users can access menus for managing their transactions and products.
- **Transaction Management**: Users can add, view, edit, and delete seed transactions (`TransaksiBibit`).
- **Product Management**: Users can add products, check stock, and update product details (`ProdukService`).
- **Sales Management**: Users can manage sales by adding new sales records and viewing existing ones (`PenjualanService`).

## Project Structure

The system is built using Java, with services handling various functionalities such as user management, product management, transaction management, and sales management.

### Core Components

- **Farminista.java**: The main class that runs the program and provides the user interface.
- **User.java**: Handles user information, login, and role-based access.
- **AdminServices.java**: Admin-specific services, such as managing users and rekap data.
- **RekapDataService.java**: Handles transactions related to seed purchases and management.
- **ProdukService.java**: Manages product-related tasks such as adding and viewing product stock.
- **PenjualanService.java**: Manages sales-related tasks such as adding sales and viewing sales records.
- **DAO Classes**: Data Access Object (DAO) classes are responsible for interacting with the database and performing CRUD operations.

### Database Structure

The system uses a database to store information for users, transactions, products, and sales. Below are the core tables:

#### Users Table
- `id_user`: Primary key
- `username`: Username of the user
- `password`: User's password (ideally hashed for security)
- `role`: User role (`admin` or `pengguna`)

#### TransaksiBibit Table
- `id_transaksi`: Primary key
- `id_bibit`: Foreign key linking to the product
- `jumlah`: Amount of seed
- `total_harga`: Total cost of the seed
- `status_tanam`: Status of the seed (`selesai` or `dalam proses`)
- `tanggal_supply`: Date of supply

#### Produk Table
- `id_produk`: Primary key
- `id_bibit`: Foreign key linking to seed type
- `stok`: Product stock
- `jumlah_kg`: Quantity in kilograms

#### Penjualan Table
- `id_penjualan`: Primary key
- `id_produk`: Foreign key linking to product
- `jumlah`: Quantity sold
- `total`: Total sale value

## Setup Instructions

### Prerequisites

- Java 8 or higher
- A database (e.g., MySQL, SQLite) and corresponding JDBC drivers
- Maven (for managing dependencies, if required)

### How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/farminista.git
   cd farminista
