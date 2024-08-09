-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: demo1
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total_price` double NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3oexs31qtfpym0v38fc3o951i` (`user_id`),
  CONSTRAINT `FK3oexs31qtfpym0v38fc3o951i` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,87,1),(2,0,2);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` float DEFAULT NULL,
  `quantity` int NOT NULL,
  `cart_id` bigint DEFAULT NULL,
  `checkout_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1uobyhgl1wvgt1jpccia8xxs3` (`cart_id`),
  KEY `FKf1ybjf84289qeja48317ro13p` (`checkout_id`),
  KEY `FKtbm3puh4cpldywrpuisljh6x` (`product_id`),
  CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKf1ybjf84289qeja48317ro13p` FOREIGN KEY (`checkout_id`) REFERENCES `checkout` (`id`),
  CONSTRAINT `FKtbm3puh4cpldywrpuisljh6x` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (56,87,1,1,NULL,26);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkout` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `cart_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs4p1r23jcw3r43psmma1enoe8` (`cart_id`),
  KEY `FK1o8bwiwtryuyg4skf0jhsiioo` (`user_id`),
  CONSTRAINT `FK1o8bwiwtryuyg4skf0jhsiioo` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FKs4p1r23jcw3r43psmma1enoe8` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
INSERT INTO `checkout` VALUES (23,'H√† N·ªôi','2024-08-09 09:00:13.806000','Thai','Nguyen Vi·ªát','cash','0375549373','Pending',2,2),(24,'TP HCM','2024-08-09 09:47:11.933000','kiet','Nguyen Vi·ªát','paypal','0375549373','Pending',2,2),(25,'h√† n·ªôi','2024-08-09 09:48:51.821000','Quy·ªÅn','Cao VƒÉn ','credit_card','0375549373','Pending',2,2);
/*!40000 ALTER TABLE `checkout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (10);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_dto`
--

DROP TABLE IF EXISTS `order_dto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_dto` (
  `id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `cart_id` bigint DEFAULT NULL,
  `total_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_dto`
--

LOCK TABLES `order_dto` WRITE;
/*!40000 ALTER TABLE `order_dto` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_dto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_group`
--

DROP TABLE IF EXISTS `product_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `group_product_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_group`
--

LOCK TABLES `product_group` WRITE;
/*!40000 ALTER TABLE `product_group` DISABLE KEYS */;
INSERT INTO `product_group` VALUES (5,'supper_admin','2024-08-05 05:39:31.783000','M·ªπ ph·∫©m',100,1),(6,'supper_admin','2024-08-05 05:39:40.161000','ƒê·ªì gia d·ª•ng',500,1),(7,'supper_admin','2024-08-05 05:39:49.614000','Th·ªùi trang',200,1),(8,'supper_admin','2024-08-05 05:40:03.546000','ƒê·ªì c√¥ng ngh·ªá',300,1),(9,'supper_admin','2024-08-05 05:40:35.293000','ƒê·ªì th·ªÉ thao',400,1),(10,'supper_admin','2024-08-05 05:40:50.826000','ƒê·ªì ch∆°i tr·∫ª em',150,0),(11,'supper_admin','2024-08-05 05:41:12.756000','ƒê·ªì d√πng h·ªçc t·∫≠p',250,0),(12,'supper_admin','2024-08-05 05:41:46.640000','Th·ª±c ph·∫©m',450,1),(13,'supper_admin','2024-08-05 05:42:20.805000','Trang tr√≠',345,1),(14,'supper_admin','2024-08-05 05:43:17.700000','Oto',123,1);
/*!40000 ALTER TABLE `product_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_info`
--

DROP TABLE IF EXISTS `product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `group_id` bigint NOT NULL,
  `product_date` datetime(6) DEFAULT NULL,
  `product_desc` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `product_origin` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  `price` float DEFAULT NULL,
  `image` longblob,
  `product_image` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_info`
--

LOCK TABLES `product_info` WRITE;
/*!40000 ALTER TABLE `product_info` DISABLE KEYS */;
INSERT INTO `product_info` VALUES (18,NULL,'2024-08-05 05:45:42.524000',8,'2024-07-24 17:00:00.000000','abcd','macbook','USA',1,234,NULL,NULL,NULL,'0011450_macbook-pro-16-inch-m2-max-38-core-32gb-1tb.jpeg'),(19,NULL,'2024-08-05 05:46:23.357000',5,'2024-07-28 17:00:00.000000','xyz','son','H√†n Qu·ªëc',1,23,NULL,NULL,NULL,'18058_S_01.jpg'),(20,NULL,'2024-08-05 05:47:18.078000',5,'2024-07-01 17:00:00.000000','abcd','k·∫ª m·∫Øt','H√†n Qu·ªëc',1,12,NULL,NULL,NULL,'F000088-1-6.jpg'),(21,NULL,'2024-08-05 05:48:41.941000',8,'2024-07-06 17:00:00.000000','abcdef','Dell inprion 7559','USA',1,654,NULL,NULL,NULL,'68162_laptop_dell_inspiron_7506_2_in_1_i7506_6.png'),(22,NULL,'2024-08-05 05:51:36.404000',14,'2024-06-30 17:00:00.000000','abcded','Oto KIA','H√†n Qu·ªëc',1,2345,NULL,NULL,NULL,'k3-do.png'),(23,NULL,'2024-08-05 05:55:03.276000',14,'2024-07-30 17:00:00.000000','xyz','Oto KN','Trung Qu·ªëc',1,2345,NULL,NULL,NULL,'images.jpg'),(24,NULL,'2024-08-05 05:57:16.768000',13,'2024-07-15 17:00:00.000000','abcdef','B√¨nh trang tr√≠','Vi·ªát Nam',1,54,NULL,NULL,NULL,'decor-trang-tri-15.jpg'),(25,NULL,'2024-08-05 05:57:51.968000',13,'2024-07-15 17:00:00.000000','abcd','ƒê·ªì trang tr√≠ ƒë·ªÉ b√†n','Vi·ªát Nam',1,43,NULL,NULL,NULL,'DC09.jpg'),(26,NULL,'2024-08-05 05:59:48.658000',12,'2024-06-29 17:00:00.000000','abcd','Th·ª±c ph·∫©m ch·ª©c nƒÉng','Vi·ªát Nam',1,87,NULL,NULL,NULL,'vien-uong-rau-cu-4.jpg'),(27,NULL,'2024-08-05 06:03:48.682000',6,'2024-07-15 17:00:00.000000','abcd','B·∫øp t·ª´','Vi·ªát Nam',1,78,NULL,NULL,NULL,'Bep-dien-tu-doi-kangaroo-kg865i.jpg'),(28,NULL,'2024-08-05 06:05:11.211000',7,'2024-07-27 17:00:00.000000','abc','Qu·∫•n √°o nam','Vi·ªát Nam',1,89,NULL,NULL,NULL,'c7db377b177fc8e2ff75a769022dcc23.jpg'),(29,NULL,'2024-08-05 06:05:36.878000',7,'2024-08-01 17:00:00.000000','abc','Qu·∫ßn √°o n·ªØ','H√†n Qu·ªëc',1,44,NULL,NULL,NULL,'fa067f9c701d940ba98ccb05d2d2f438.jpg'),(30,NULL,'2024-08-05 06:06:40.352000',7,'2024-07-13 17:00:00.000000','abc','Qu·∫ßn √°o h√†n qu·ªëc','H√†n Qu·ªëc',1,100,NULL,NULL,NULL,'230e56e49a07865452fdb6081478dbb1.jpg'),(31,NULL,'2024-08-05 06:09:51.625000',9,'2024-07-07 17:00:00.000000','abcd','V·ª£t c·∫ßu l√¥ng','Trung Qu·ªëc',1,120,NULL,NULL,NULL,'yonex-nanoflare-1000-game.jpg'),(32,NULL,'2024-08-05 06:10:55.126000',9,'2024-08-02 17:00:00.000000','abcd','Gi√†y c·∫ßu l√¥ng','Nh·∫≠t B·∫£n',1,54,NULL,NULL,NULL,'a7a416babf1e67403e0f.jpg');
/*!40000 ALTER TABLE `product_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_info_dto`
--

DROP TABLE IF EXISTS `product_info_dto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_info_dto` (
  `id` bigint NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `group_id` bigint NOT NULL,
  `product_date` date DEFAULT NULL,
  `product_desc` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_origin` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `price` float DEFAULT NULL,
  `image` tinyblob,
  `product_image` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_info_dto`
--

LOCK TABLES `product_info_dto` WRITE;
/*!40000 ALTER TABLE `product_info_dto` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_info_dto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_module`
--

DROP TABLE IF EXISTS `product_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_module` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `parent_product_id` bigint DEFAULT NULL,
  `place` bigint NOT NULL,
  `status` int DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_module`
--

LOCK TABLES `product_module` WRITE;
/*!40000 ALTER TABLE `product_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
INSERT INTO `spring_session` VALUES ('1f545adf-8709-45ff-b947-d43a6cbc9687','0a6b57a3-5607-4bbf-9472-03a33ebd3643',1723101901729,1723120526289,90000,1723210526289,NULL),('a36793df-5353-4394-a93e-22bb72cdaab9','26403344-2d50-46d6-9095-0683634c31b5',1723186759166,1723197061849,90000,1723287061849,NULL),('b69d219e-327f-4d2f-a82b-78b14d81bb52','0c72af1d-7d59-49c9-abb1-c8827e09fe7d',1723170360458,1723176238358,90000,1723266238358,NULL);
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
INSERT INTO `spring_session_attributes` VALUES ('1f545adf-8709-45ff-b947-d43a6cbc9687','fullName',_binary '¨\Ì\0t\0supper_admin'),('1f545adf-8709-45ff-b947-d43a6cbc9687','userId',_binary '¨\Ì\0sr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0'),('1f545adf-8709-45ff-b947-d43a6cbc9687','userModuleMenus',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0	w\0\0\0	sr\0com.project.table.UserModuleπ]T¥∞}Ñ\0\0J\0placeL\0icont\0Ljava/lang/String;L\0idt\0Ljava/lang/Long;L\0nameq\0~\0L\0parentIdq\0~\0L\0statust\0Ljava/lang/Integer;L\0urlq\0~\0xp\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0t\0Qu·∫£n tr·ªãsq\0~\0\0\0\0\0\0\0\0\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexq\0~\0	\0\0\0t\0\0sq\0~\0\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsq\0~\0\0\0\0\0\0\0\0t\0Qu·∫£n l√Ω s·∫£n ph·∫©mq\0~\0q\0~\0t\0\0sq\0~\0\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsq\0~\0\0\0\0\0\0\0\0t\0Qu·∫£n l√Ω gi·ªè h√†ngq\0~\0q\0~\0psq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Ng∆∞·ªùi d√πngq\0~\0\nq\0~\0t\0/nguoi-dungsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0S·∫£n ph·∫©mq\0~\0q\0~\0t\0	/san-phamsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0\rHome Shoppingq\0~\0sq\0~\0\r\0\0\0t\0/home-shoppingsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Nh√≥m ng∆∞·ªùi d√πngq\0~\0\nq\0~\0t\0/nhom-nguoi-dungsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Nh√≥m s·∫£n ph·∫©mq\0~\0q\0~\0t\0/nhom-san-phamsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0	t\0	List Oderq\0~\0q\0~\0$t\0/list-orderx'),('1f545adf-8709-45ff-b947-d43a6cbc9687','username',_binary '¨\Ì\0t\0supper_admin'),('a36793df-5353-4394-a93e-22bb72cdaab9','fullName',_binary '¨\Ì\0t\0supper_admin'),('a36793df-5353-4394-a93e-22bb72cdaab9','userId',_binary '¨\Ì\0sr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0'),('a36793df-5353-4394-a93e-22bb72cdaab9','userModuleMenus',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0\nw\0\0\0\nsr\0com.project.table.UserModuleπ]T¥∞}Ñ\0\0J\0placeL\0icont\0Ljava/lang/String;L\0idt\0Ljava/lang/Long;L\0nameq\0~\0L\0parentIdq\0~\0L\0statust\0Ljava/lang/Integer;L\0urlq\0~\0xp\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0t\0Qu·∫£n tr·ªãsq\0~\0\0\0\0\0\0\0\0\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexq\0~\0	\0\0\0t\0\0sq\0~\0\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsq\0~\0\0\0\0\0\0\0\0t\0Qu·∫£n l√Ω s·∫£n ph·∫©mq\0~\0q\0~\0t\0\0sq\0~\0\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsq\0~\0\0\0\0\0\0\0\0t\0Qu·∫£n l√Ω gi·ªè h√†ngq\0~\0q\0~\0psq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Ng∆∞·ªùi d√πngq\0~\0\nq\0~\0t\0/nguoi-dungsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0S·∫£n ph·∫©mq\0~\0q\0~\0t\0	/san-phamsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0\rHome Shoppingq\0~\0q\0~\0t\0/home-shoppingsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Nh√≥m ng∆∞·ªùi d√πngq\0~\0\nq\0~\0t\0/nhom-nguoi-dungsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Nh√≥m s·∫£n ph·∫©mq\0~\0q\0~\0t\0/nhom-san-phamsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0	t\0	List Oderq\0~\0q\0~\0t\0/list-ordersq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0\nt\0Checkoutq\0~\0q\0~\0t\0	/checkoutx'),('a36793df-5353-4394-a93e-22bb72cdaab9','username',_binary '¨\Ì\0t\0supper_admin'),('b69d219e-327f-4d2f-a82b-78b14d81bb52','fullName',_binary '¨\Ì\0t\0supper_admin'),('b69d219e-327f-4d2f-a82b-78b14d81bb52','userId',_binary '¨\Ì\0sr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0'),('b69d219e-327f-4d2f-a82b-78b14d81bb52','userModuleMenus',_binary '¨\Ì\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0\nw\0\0\0\nsr\0com.project.table.UserModuleπ]T¥∞}Ñ\0\0J\0placeL\0icont\0Ljava/lang/String;L\0idt\0Ljava/lang/Long;L\0nameq\0~\0L\0parentIdq\0~\0L\0statust\0Ljava/lang/Integer;L\0urlq\0~\0xp\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsr\0java.lang.Long;ã\‰êÃè#\ﬂ\0J\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp\0\0\0\0\0\0\0t\0Qu·∫£n tr·ªãsq\0~\0\0\0\0\0\0\0\0\0sr\0java.lang.Integer‚†§˜Åá8\0I\0valuexq\0~\0	\0\0\0t\0\0sq\0~\0\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsq\0~\0\0\0\0\0\0\0\0t\0Qu·∫£n l√Ω s·∫£n ph·∫©mq\0~\0q\0~\0t\0\0sq\0~\0\0\0\0\0\0\0\0t\0nav-icon fas fa-tachometer-altsq\0~\0\0\0\0\0\0\0\0t\0Qu·∫£n l√Ω gi·ªè h√†ngq\0~\0q\0~\0psq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Ng∆∞·ªùi d√πngq\0~\0\nq\0~\0t\0/nguoi-dungsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0S·∫£n ph·∫©mq\0~\0q\0~\0t\0	/san-phamsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0\rHome Shoppingq\0~\0q\0~\0t\0/home-shoppingsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Nh√≥m ng∆∞·ªùi d√πngq\0~\0\nq\0~\0t\0/nhom-nguoi-dungsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0t\0Nh√≥m s·∫£n ph·∫©mq\0~\0q\0~\0t\0/nhom-san-phamsq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0	t\0	List Oderq\0~\0q\0~\0t\0/list-ordersq\0~\0\0\0\0\0\0\0\0psq\0~\0\0\0\0\0\0\0\0\nt\0Checkoutq\0~\0q\0~\0t\0	/checkoutx'),('b69d219e-327f-4d2f-a82b-78b14d81bb52','username',_binary '¨\Ì\0t\0supper_admin');
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `group_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (9,NULL,'2024-08-05 05:00:08','qu·∫£n l√Ω ng∆∞·ªùi d√πng v√† s·∫£n ph·∫©m','supper_admin',1),(10,'supper_admin','2024-08-05 05:28:26','ch·ªâ c√≥ th·ªÉ mua h√†ng  v√† thanh to√°n','Ng∆∞·ªùi d√πng',1);
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group_permission`
--

DROP TABLE IF EXISTS `user_group_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_group_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL,
  `module_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group_permission`
--

LOCK TABLES `user_group_permission` WRITE;
/*!40000 ALTER TABLE `user_group_permission` DISABLE KEYS */;
INSERT INTO `user_group_permission` VALUES (1,1,2),(2,1,3),(3,1,5),(4,1,6),(5,1,8),(6,1,9),(7,2,8),(8,1,10);
/*!40000 ALTER TABLE `user_group_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `group_id` bigint NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,NULL,'2024-08-05 05:00:08',NULL,'supper_admin',1,'a02b3395d3b5297ed2bf339be5b915',1,'supper_admin','H√† N·ªôi','0123456789'),(2,'supper_admin','2024-08-05 05:30:04','caovanquyena11@gmail.com','Cao Van Quyen',2,'25d55ad283aa40af464c76d713c7ad',1,'user','mao dien thuan thanh bac ninh trai dat giai ngan ha','0375549373');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info_dto`
--

DROP TABLE IF EXISTS `user_info_dto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info_dto` (
  `id` bigint NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `don_vi_id` bigint DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `group_id` bigint NOT NULL,
  `status` int DEFAULT NULL,
  `ten_don_vi` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info_dto`
--

LOCK TABLES `user_info_dto` WRITE;
/*!40000 ALTER TABLE `user_info_dto` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info_dto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_module`
--

DROP TABLE IF EXISTS `user_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_module` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `place` bigint NOT NULL,
  `status` int DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_module`
--

LOCK TABLES `user_module` WRITE;
/*!40000 ALTER TABLE `user_module` DISABLE KEYS */;
INSERT INTO `user_module` VALUES (1,'nav-icon fas fa-tachometer-alt','Qu·∫£n tr·ªã',0,1,1,''),(2,NULL,'Ng∆∞·ªùi d√πng',1,2,1,'/nguoi-dung'),(3,NULL,'Nh√≥m ng∆∞·ªùi d√πng',1,3,1,'/nhom-nguoi-dung'),(4,'nav-icon fas fa-tachometer-alt','Qu·∫£n l√Ω s·∫£n ph·∫©m',0,1,1,''),(5,NULL,'S·∫£n ph·∫©m',4,2,1,'/san-pham'),(6,NULL,'Nh√≥m s·∫£n ph·∫©m',4,3,1,'/nhom-san-pham'),(7,'nav-icon fas fa-tachometer-alt','Qu·∫£n l√Ω gi·ªè h√†ng',0,1,1,NULL),(8,NULL,'Home Shopping',7,2,1,'/home-shopping'),(9,NULL,'List Oder',7,3,1,'/list-order');
/*!40000 ALTER TABLE `user_module` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-09 16:59:22
