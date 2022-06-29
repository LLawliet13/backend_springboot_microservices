CREATE DATABASE  IF NOT EXISTS `mock2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mock2`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mock2
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `billId` bigint NOT NULL AUTO_INCREMENT,
  `purchaseDate` date NOT NULL,
  `totalPrice` int NOT NULL,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`billId`),
  KEY `userId` (`userId`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `bill_chk_1` CHECK ((`totalPrice` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,'2022-02-20',5000,1),(2,'2022-03-22',7000,1),(3,'2022-04-25',10000,2);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_detail`
--

DROP TABLE IF EXISTS `bill_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_detail` (
  `billDetailId` bigint NOT NULL AUTO_INCREMENT,
  `billDetailPrice` bigint DEFAULT NULL,
  `billDetailQuantity` int DEFAULT NULL,
  `billId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  PRIMARY KEY (`billDetailId`),
  KEY `FKol3hfr3ntsxaocgw9ccxgulc1` (`billId`),
  KEY `FKl8pjpldrt8rjg9seu5cp4eoro` (`productId`),
  CONSTRAINT `FKl8pjpldrt8rjg9seu5cp4eoro` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`),
  CONSTRAINT `FKol3hfr3ntsxaocgw9ccxgulc1` FOREIGN KEY (`billId`) REFERENCES `bill` (`billId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_detail`
--

LOCK TABLES `bill_detail` WRITE;
/*!40000 ALTER TABLE `bill_detail` DISABLE KEYS */;
INSERT INTO `bill_detail` VALUES (1,5000,1,1,1);
/*!40000 ALTER TABLE `bill_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billdetail`
--

DROP TABLE IF EXISTS `billdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billdetail` (
  `billDetailId` bigint NOT NULL AUTO_INCREMENT,
  `billDetailPrice` bigint NOT NULL,
  `billDetailQuantity` int NOT NULL,
  `billId` bigint DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  PRIMARY KEY (`billDetailId`),
  KEY `productId` (`productId`),
  KEY `billId` (`billId`),
  CONSTRAINT `billdetail_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`),
  CONSTRAINT `billdetail_ibfk_2` FOREIGN KEY (`billId`) REFERENCES `bill` (`billId`),
  CONSTRAINT `billdetail_chk_1` CHECK ((`billDetailPrice` > 0)),
  CONSTRAINT `billdetail_chk_2` CHECK ((`billDetailQuantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billdetail`
--

LOCK TABLES `billdetail` WRITE;
/*!40000 ALTER TABLE `billdetail` DISABLE KEYS */;
INSERT INTO `billdetail` VALUES (1,2000,2,1,1),(2,3000,2,1,2),(3,4000,1,2,4),(4,3000,1,2,3),(5,10000,3,3,2);
/*!40000 ALTER TABLE `billdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cartId` bigint NOT NULL AUTO_INCREMENT,
  `productId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `cartQuantity` int NOT NULL,
  PRIMARY KEY (`cartId`),
  KEY `userId` (`userId`),
  KEY `productId` (`productId`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`),
  CONSTRAINT `cart_chk_1` CHECK ((`cartQuantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,1,2),(2,3,1,1),(3,4,2,3);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `categoryId` int NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Phone Update 2'),(2,'bike'),(4,'Iphone'),(5,'IphoneXS'),(6,'IphoneE');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_status`
--

DROP TABLE IF EXISTS `delivery_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_status` (
  `deliveryStatusId` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(100) NOT NULL,
  `billId` bigint DEFAULT NULL,
  `deliveryTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deliveryStatusId`),
  KEY `billId` (`billId`),
  CONSTRAINT `delivery_status_ibfk_1` FOREIGN KEY (`billId`) REFERENCES `bill` (`billId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_status`
--

LOCK TABLES `delivery_status` WRITE;
/*!40000 ALTER TABLE `delivery_status` DISABLE KEYS */;
INSERT INTO `delivery_status` VALUES (1,'delivered',1,NULL),(2,'delivered',2,NULL),(3,'not delivered',3,NULL);
/*!40000 ALTER TABLE `delivery_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `typeOfAction` varchar(10) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `action` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productId` bigint NOT NULL AUTO_INCREMENT,
  `productName` varchar(50) NOT NULL,
  `productPrice` bigint NOT NULL,
  `productQuantity` int DEFAULT NULL,
  `productRating` float DEFAULT NULL,
  `categoryId` int DEFAULT NULL,
  PRIMARY KEY (`productId`),
  KEY `categoryId` (`categoryId`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`),
  CONSTRAINT `product_chk_1` CHECK ((`productQuantity` > 0)),
  CONSTRAINT `product_chk_2` CHECK ((`productRating` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'abced',100000,10,3,1),(2,'samsung',3000,20,4.3,1),(3,'honda',8000,5,4.4,2),(4,'abc',100000,10,1,1),(35,'abc',100000,10,2,1),(36,'xx10',100000,10,1,1),(37,'xx11',100000,10,1,1),(38,'xx111\n',100000,10,1,1),(39,'xx1112',100000,10,1,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productmedia`
--

DROP TABLE IF EXISTS `productmedia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productmedia` (
  `productMediaId` bigint NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL,
  `productId` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`productMediaId`),
  KEY `FKth3lbgpl0h1t6whpmb62lll3x` (`productId`),
  CONSTRAINT `FKth3lbgpl0h1t6whpmb62lll3x` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productmedia`
--

LOCK TABLES `productmedia` WRITE;
/*!40000 ALTER TABLE `productmedia` DISABLE KEYS */;
INSERT INTO `productmedia` VALUES (8,'C:\\upload_mooc2\\36\\Screenshot (1991).png',36,'image/png'),(9,'C:\\upload_mooc2\\37\\Screenshot (1991).png',37,'image/png'),(10,'C:\\upload_mooc2\\38\\Screenshot (1991).png',38,'image/png'),(11,'C:\\upload_mooc2\\35\\Screenshot (1991).png',35,'image/png');
/*!40000 ALTER TABLE `productmedia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `roleId` int NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(150) NOT NULL,
  `userAddress` varchar(100) DEFAULT NULL,
  `userFullname` varchar(100) NOT NULL,
  `userPhone` varchar(10) NOT NULL,
  `userEmail` varchar(50) NOT NULL,
  `userDob` date NOT NULL,
  `userGender` varchar(1) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user','$2a$10$Ccib7AbOzg8T/Y1B9IeTV.ecY7bngJHmgB10xh2UhNjF4EKzNAOP2','thai binh','dung tnt','0123456789','dung@gmail.com','2001-03-13','m'),(2,'admin','$2a$10$Ccib7AbOzg8T/Y1B9IeTV.ecY7bngJHmgB10xh2UhNjF4EKzNAOP2','Hanoi2','Le Hung','0987333222','lehung@gmail.com','1999-02-02','m'),(3,'user1','$2a$10$E.NIDDFF0dRwbFCRukujq.63KAcDssYBnt8OFHkDB5sqWcDy/EDg2','thai binh 2','dung tnt ne ','0123456789','dung@gmail.com','2001-03-13','f'),(5,'user1','$2a$10$536q.QHnoe6nJ0eFhY.Jk.vH3hg46D2O72BS3S7K9JeocJLzTFvfG','thai binh 2','dung tnt ne ','0123456789','dung@gmail.com','2001-03-13','f');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_product_rating`
--

DROP TABLE IF EXISTS `user_product_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_product_rating` (
  `userProductRatingId` bigint NOT NULL AUTO_INCREMENT,
  `productId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `vote` int DEFAULT NULL,
  PRIMARY KEY (`userProductRatingId`),
  KEY `userId` (`userId`),
  KEY `productId` (`productId`),
  CONSTRAINT `user_product_rating_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `user_product_rating_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_product_rating`
--

LOCK TABLES `user_product_rating` WRITE;
/*!40000 ALTER TABLE `user_product_rating` DISABLE KEYS */;
INSERT INTO `user_product_rating` VALUES (1,1,1,2),(2,1,2,4),(3,3,2,5);
/*!40000 ALTER TABLE `user_product_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `roleId` int NOT NULL,
  `userId` bigint NOT NULL,
  KEY `userId` (`userId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,2),(1,5);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-28 20:22:46
