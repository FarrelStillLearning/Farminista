CREATE DATABASE  IF NOT EXISTS `farminista` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `farminista`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: farminista
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bibit`
--

DROP TABLE IF EXISTS `bibit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bibit` (
  `id_bibit` int NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_bibit`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bibit`
--

LOCK TABLES `bibit` WRITE;
/*!40000 ALTER TABLE `bibit` DISABLE KEYS */;
INSERT INTO `bibit` VALUES (1,'Bawang'),(2,'Cabai Ijo'),(3,'Cabai Merah'),(4,'Daun Teh');
/*!40000 ALTER TABLE `bibit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `penjualan`
--

DROP TABLE IF EXISTS `penjualan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `penjualan` (
  `id_penjualan` int NOT NULL AUTO_INCREMENT,
  `id_produk` int DEFAULT NULL,
  `jumlah` int DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_penjualan`),
  KEY `id_produk` (`id_produk`),
  CONSTRAINT `penjualan_ibfk_1` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `penjualan`
--

LOCK TABLES `penjualan` WRITE;
/*!40000 ALTER TABLE `penjualan` DISABLE KEYS */;
/*!40000 ALTER TABLE `penjualan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `petani`
--

DROP TABLE IF EXISTS `petani`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `petani` (
  `id_petani` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_petani`),
  UNIQUE KEY `Username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petani`
--

LOCK TABLES `petani` WRITE;
/*!40000 ALTER TABLE `petani` DISABLE KEYS */;
INSERT INTO `petani` VALUES (1,'admin','admin123','admin'),(2,'test123','test123','pengguna'),(14,'petani','petani123','pengguna');
/*!40000 ALTER TABLE `petani` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produk`
--

DROP TABLE IF EXISTS `produk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produk` (
  `id_produk` int NOT NULL AUTO_INCREMENT,
  `id_bibit` int DEFAULT NULL,
  `stok` int DEFAULT NULL,
  `harga_produk` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_produk`),
  KEY `id_bibit` (`id_bibit`),
  CONSTRAINT `produk_ibfk_1` FOREIGN KEY (`id_bibit`) REFERENCES `bibit` (`id_bibit`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produk`
--

LOCK TABLES `produk` WRITE;
/*!40000 ALTER TABLE `produk` DISABLE KEYS */;
INSERT INTO `produk` VALUES (1,1,12,24000.00),(2,1,30,24000.00),(3,1,18,24000.00);
/*!40000 ALTER TABLE `produk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id_supplier` int NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_supplier`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'PT.Farrel'),(2,'opik'),(3,'Sinar Labib Jaya');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supply_bibit`
--

DROP TABLE IF EXISTS `supply_bibit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supply_bibit` (
  `id_supply_bibit` int NOT NULL AUTO_INCREMENT,
  `id_bibit` int DEFAULT NULL,
  `id_supplier` int DEFAULT NULL,
  `harga` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_supply_bibit`),
  KEY `id_bibit` (`id_bibit`),
  KEY `id_supplier` (`id_supplier`),
  CONSTRAINT `supply_bibit_ibfk_1` FOREIGN KEY (`id_bibit`) REFERENCES `bibit` (`id_bibit`),
  CONSTRAINT `supply_bibit_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supply_bibit`
--

LOCK TABLES `supply_bibit` WRITE;
/*!40000 ALTER TABLE `supply_bibit` DISABLE KEYS */;
INSERT INTO `supply_bibit` VALUES (1,1,1,12000.00),(2,3,2,12.00),(3,4,3,50000.00),(4,2,1,24000.00);
/*!40000 ALTER TABLE `supply_bibit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaksi_beli_bibit`
--

DROP TABLE IF EXISTS `transaksi_beli_bibit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi_beli_bibit` (
  `id_transaksi` int NOT NULL AUTO_INCREMENT,
  `id_supply_bibit` int DEFAULT NULL,
  `id_petani` int DEFAULT NULL,
  `jumlah` int DEFAULT NULL,
  `total_harga` decimal(10,2) DEFAULT NULL,
  `status_tanam` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tanggal_supply` date DEFAULT NULL,
  PRIMARY KEY (`id_transaksi`),
  KEY `id_supply_bibit` (`id_supply_bibit`),
  KEY `id_petani` (`id_petani`),
  CONSTRAINT `transaksi_beli_bibit_ibfk_1` FOREIGN KEY (`id_supply_bibit`) REFERENCES `supply_bibit` (`id_supply_bibit`),
  CONSTRAINT `transaksi_beli_bibit_ibfk_2` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_beli_bibit`
--

LOCK TABLES `transaksi_beli_bibit` WRITE;
/*!40000 ALTER TABLE `transaksi_beli_bibit` DISABLE KEYS */;
INSERT INTO `transaksi_beli_bibit` VALUES (1,1,1,5,60000.00,'selesai','2024-10-19'),(2,1,1,5,60000.00,'selesai','2024-10-19'),(4,3,1,3,150000.00,'Pending','2024-10-20'),(5,1,1,4,96000.00,'Pending','2024-10-20'),(6,1,1,20,480000.00,'Pending','2024-10-20'),(7,1,1,2,48000.00,'Pending','2024-10-20');
/*!40000 ALTER TABLE `transaksi_beli_bibit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaksi_tanam_panen`
--

DROP TABLE IF EXISTS `transaksi_tanam_panen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi_tanam_panen` (
  `id_tanam_panen` int NOT NULL AUTO_INCREMENT,
  `id_transaksi` int DEFAULT NULL,
  `waktu_tanam` date DEFAULT NULL,
  `waktu_panen` date DEFAULT NULL,
  `jumlah_panen` int DEFAULT NULL,
  PRIMARY KEY (`id_tanam_panen`),
  KEY `id_transaksi` (`id_transaksi`),
  CONSTRAINT `transaksi_tanam_panen_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi_beli_bibit` (`id_transaksi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi_tanam_panen`
--

LOCK TABLES `transaksi_tanam_panen` WRITE;
/*!40000 ALTER TABLE `transaksi_tanam_panen` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaksi_tanam_panen` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-20 23:13:48
