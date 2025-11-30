-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: demglaz
-- ------------------------------------------------------
-- Server version	10.11.6-MariaDB-0+deb12u1.astra1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `idcategories` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`idcategories`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES
(1,'Мужская обувь'),
(2,'Женская обувь'),
(3,'Детская обувь'),
(4,'Спортивная обувь'),
(5,'Кроссовки'),
(6,'Туфли'),
(7,'Ботинки'),
(8,'Сапоги'),
(9,'Сандалии'),
(10,'Тапочки'),
(11,'Балетки'),
(12,'Лоферы'),
(13,'Мокасины'),
(14,'Эспадрильи'),
(15,'Оксфорды'),
(16,'Дерби'),
(17,'Броги'),
(18,'Слипоны'),
(19,'Кеды'),
(20,'Угги');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufacturers`
--

DROP TABLE IF EXISTS `manufacturers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manufacturers` (
  `idmanufacturers` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`idmanufacturers`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacturers`
--

LOCK TABLES `manufacturers` WRITE;
/*!40000 ALTER TABLE `manufacturers` DISABLE KEYS */;
INSERT INTO `manufacturers` VALUES
(1,'Nike'),
(2,'Adidas'),
(3,'Reebok'),
(4,'Puma'),
(5,'Skechers'),
(6,'Ecco'),
(7,'Geox'),
(8,'Clarks'),
(9,'Salomon'),
(10,'Timberland'),
(11,'Dr. Martens'),
(12,'Converse'),
(13,'Vans'),
(14,'New Balance'),
(15,'Asics'),
(16,'Under Armour'),
(17,'Columbia'),
(18,'Merrell'),
(19,'Birkenstock'),
(20,'Crocs');
/*!40000 ALTER TABLE `manufacturers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderitems` (
  `idorderitems` int(11) NOT NULL AUTO_INCREMENT,
  `idorder` int(11) NOT NULL,
  `idproduct` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`idorderitems`),
  KEY `idorder` (`idorder`),
  KEY `idproduct` (`idproduct`),
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`idorder`) REFERENCES `orders` (`idorders`) ON DELETE CASCADE,
  CONSTRAINT `orderitems_ibfk_2` FOREIGN KEY (`idproduct`) REFERENCES `products` (`idproducts`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
INSERT INTO `orderitems` VALUES
(3,2,2,2),
(4,2,6,1),
(5,3,4,1),
(6,3,5,1),
(7,4,7,1),
(8,4,8,1),
(9,5,9,1),
(10,5,10,2),
(11,6,11,1),
(12,6,12,1),
(13,7,13,2),
(14,7,14,1),
(15,8,15,1),
(16,8,16,1),
(17,9,17,1),
(18,9,18,1),
(19,10,19,2),
(20,10,20,1),
(22,11,2,1),
(23,12,3,1),
(24,12,4,1),
(25,13,5,2),
(26,13,6,1),
(27,14,7,1),
(28,14,8,1),
(29,15,9,1),
(30,15,10,1),
(31,16,11,2),
(32,16,12,1),
(33,17,13,1),
(34,17,14,1),
(35,18,15,1),
(36,18,16,2),
(37,19,17,1),
(38,19,18,1),
(39,20,19,1),
(40,20,20,1);
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `idorders` int(11) NOT NULL AUTO_INCREMENT,
  `ordernumber` varchar(50) DEFAULT NULL,
  `orderdate` datetime NOT NULL,
  `deliverydate` datetime DEFAULT NULL,
  `idstatus` int(11) NOT NULL,
  `idpickuppoint` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`idorders`),
  UNIQUE KEY `ordernumber` (`ordernumber`),
  KEY `idstatus` (`idstatus`),
  KEY `idpickuppoint` (`idpickuppoint`),
  KEY `iduser` (`iduser`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`idstatus`) REFERENCES `orderstatuses` (`idorderstatuses`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`idpickuppoint`) REFERENCES `pickuppoints` (`idpickuppoints`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`iduser`) REFERENCES `users` (`idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES
(2,'SHOES002','2024-03-13 16:45:00','2024-03-18 11:30:00',5,2,5),
(3,'SHOES003','2024-03-14 09:15:00','2024-03-20 16:00:00',5,3,6),
(4,'SHOES004','2024-03-15 13:20:00','2024-03-22 12:00:00',4,4,7),
(5,'SHOES005','2024-03-16 17:50:00','2024-03-25 14:30:00',4,5,8),
(6,'SHOES006','2024-03-17 11:10:00',NULL,3,6,9),
(7,'SHOES007','2024-03-18 14:25:00',NULL,3,7,10),
(8,'SHOES008','2024-03-19 09:40:00',NULL,2,8,11),
(9,'SHOES009','2024-03-20 16:15:00',NULL,2,9,12),
(10,'SHOES010','2024-03-21 12:30:00',NULL,1,10,13),
(11,'SHOES011','2024-03-22 15:20:00',NULL,1,11,14),
(12,'SHOES012','2024-03-23 10:45:00',NULL,1,12,15),
(13,'SHOES013','2024-03-24 13:55:00',NULL,1,13,16),
(14,'SHOES014','2024-03-25 17:10:00',NULL,1,14,17),
(15,'SHOES015','2024-03-26 08:30:00',NULL,1,15,18),
(16,'SHOES016','2024-03-27 14:40:00',NULL,1,16,19),
(17,'SHOES017','2024-03-28 11:25:00',NULL,1,17,20),
(18,'SHOES018','2024-03-29 16:50:00',NULL,1,18,4),
(19,'SHOES019','2024-03-30 09:15:00',NULL,1,19,5),
(20,'SHOES020','2024-03-31 13:20:00',NULL,1,20,6),
(21,'213123','2024-10-09 00:00:00','2025-10-09 00:00:00',1,1,4);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderstatuses`
--

DROP TABLE IF EXISTS `orderstatuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderstatuses` (
  `idorderstatuses` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`idorderstatuses`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderstatuses`
--

LOCK TABLES `orderstatuses` WRITE;
/*!40000 ALTER TABLE `orderstatuses` DISABLE KEYS */;
INSERT INTO `orderstatuses` VALUES
(1,'оформлен'),
(2,'подтвержден'),
(3,'собирается'),
(4,'готов к выдаче'),
(5,'выдан'),
(6,'отменен');
/*!40000 ALTER TABLE `orderstatuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pickuppoints`
--

DROP TABLE IF EXISTS `pickuppoints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pickuppoints` (
  `idpickuppoints` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `street` varchar(100) NOT NULL,
  `housenumber` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`idpickuppoints`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pickuppoints`
--

LOCK TABLES `pickuppoints` WRITE;
/*!40000 ALTER TABLE `pickuppoints` DISABLE KEYS */;
INSERT INTO `pickuppoints` VALUES
(1,'Москва','ул. Арбат','25','Центральный обувной магазин'),
(2,'Санкт-Петербург','Невский пр-т','42','Обувной дом на Невском'),
(3,'Екатеринбург','ул. Вайнера','15','Уральский обувной центр'),
(4,'Новосибирск','Красный пр-т','56','Сибирский обувной магазин'),
(5,'Казань','ул. Баумана','33','Татарстан обувной'),
(6,'Нижний Новгород','ул. Большая Покровская','18','Нижегородский обувной'),
(7,'Ростов-на-Дону','ул. Садовая','27','Южный обувной центр'),
(8,'Краснодар','ул. Красная','64','Кубанский обувной'),
(9,'Воронеж','пр-т Революции','39','Воронежский обувной'),
(10,'Самара','ул. Ленинградская','22','Самарский обувной дом'),
(11,'Волгоград','пр-т Ленина','51','Волгоградский обувной'),
(12,'Пермь','ул. Ленина','48','Пермский обувной центр'),
(13,'Уфа','ул. Ленина','29','Башкирский обувной'),
(14,'Красноярск','ул. Карла Маркса','36','Красноярский обувной'),
(15,'Челябинск','ул. Кирова','42','Челябинский обувной'),
(16,'Омск','ул. Ленина','15','Омский обувной магазин'),
(17,'Тюмень','ул. Республики','53','Тюменский обувной'),
(18,'Иркутск','ул. Урицкого','17','Иркутский обувной центр'),
(19,'Хабаровск','ул. Муравьева-Амурского','24','Хабаровский обувной'),
(20,'Владивосток','ул. Светланская','31','Владивостокский обувной');
/*!40000 ALTER TABLE `pickuppoints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `idproducts` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `idcategory` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `idmanufacturer` int(11) DEFAULT NULL,
  `idsupplier` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `idunit` int(11) DEFAULT NULL,
  `stockquantity` int(11) NOT NULL DEFAULT 0,
  `discount` int(11) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`idproducts`),
  KEY `idcategory` (`idcategory`),
  KEY `idmanufacturer` (`idmanufacturer`),
  KEY `idsupplier` (`idsupplier`),
  KEY `idunit` (`idunit`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`idcategory`) REFERENCES `categories` (`idcategories`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`idmanufacturer`) REFERENCES `manufacturers` (`idmanufacturers`),
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`idsupplier`) REFERENCES `suppliers` (`idsuppliers`),
  CONSTRAINT `products_ibfk_4` FOREIGN KEY (`idunit`) REFERENCES `units` (`idunits`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES
(2,'Кроссовки Adidas Ultraboost',1,'Беговые кроссовки с технологией Boost',1,1,15990,1,12,15,'/home/8277@stud.pgt.su/Изображения/Screenshot_20250908_135509.png'),
(3,'Туфли женские Ecco Soft',1,'Элегантные туфли из мягкой кожи',1,1,8990,1,20,5,''),
(4,'Кроссовки Puma RS-X',5,'Стильные кроссовки в ретро-дизайне',4,1,10990,1,18,20,'/images/puma_rs_x.jpg'),
(5,'Ботинки мужские Reebok Classic',7,'Кожаные ботинки для повседневной носки',3,3,11990,1,10,0,'/images/reebok_classic.jpg'),
(6,'Кроссовки детские Skechers',3,'Яркие детские кроссовки с подсветкой',5,4,5990,1,25,25,'/images/skechers_kids.jpg'),
(7,'Сапоги женские зимние',8,'Теплые сапоги на меху для зимы',6,2,14990,1,8,30,'/images/winter_boots.jpg'),
(8,'Кроссовки Nike Jordan',5,'Легендарные баскетбольные кроссовки',1,1,21990,1,5,5,'/images/nike_jordan.jpg'),
(9,'Ботинки Timberland Premium',7,'Водоотталкивающие ботинки премиум-класса',10,5,18990,1,7,10,'/images/timberland_premium.jpg'),
(10,'Сандалии Birkenstock Arizona',9,'Ортопедические сандалии для комфорта',19,6,7990,1,22,15,'/images/birkenstock_arizona.jpg'),
(11,'Кеды Converse All Star',19,'Классические кеды для повседневной носки',12,7,4990,1,30,20,'/images/converse_all_star.jpg'),
(12,'Туфли-лодочки женские',6,'Элегантные туфли для офиса',8,8,12990,1,12,0,'/images/loafer_shoes.jpg'),
(13,'Кроссовки New Balance 574',5,'Универсальные кроссовки для города',14,9,8990,1,18,12,'/images/new_balance_574.jpg'),
(14,'Сапоги Dr. Martens 1460',8,'Культовые ботинки с воздушной подушкой',11,10,15990,1,6,8,'/images/dr_martens_1460.jpg'),
(15,'Мокасины мужские Geox',13,'Дышащие мокасины для офиса',7,11,11990,1,14,10,'/images/geox_moccasins.jpg'),
(16,'Слипоны Vans Classic',18,'Удобные слипоны для расслабленного стиля',13,12,4590,1,25,25,'/images/vans_classic.jpg'),
(17,'Кроссовки Asics Gel-Kayano',4,'Профессиональные беговые кроссовки',15,13,13990,1,9,15,'/images/asics_gel_kayano.jpg'),
(18,'Угги женские натуральные',20,'Теплые угги из овчины',16,14,9990,1,11,30,'/images/ugg_boots.jpg'),
(19,'Балетки женские кожаные',1,'Удобные балетки для повседневной носки',1,1,3490,1,28,5,'Screenshot_20250908_135423.png'),
(20,'Оксфорды мужские классические',15,'Элегантные оксфорды для делового стиля',18,16,16990,1,8,0,'/images/oxford_shoes.jpg'),
(21,'asdas',1,'asdas',1,1,6512,1,21312,25,'Screenshot_20250908_131917.png');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suppliers` (
  `idsuppliers` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`idsuppliers`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES
(1,'ООО \"СпортОбувь\"'),
(2,'АО \"МоднаяОбувь\"'),
(3,'ЗАО \"Еврообувь\"'),
(4,'ООО \"ДетскийМир\"'),
(5,'ИП \"СтильнаяОбувь\"'),
(6,'ООО \"ОбувьОпт\"'),
(7,'АО \"СпортМастер\"'),
(8,'ЗАО \"ЛюксОбувь\"'),
(9,'ООО \"КомфортСтиль\"'),
(10,'ИП \"КлассикаОбуви\"'),
(11,'ООО \"ТрендФут\"'),
(12,'АО \"ЭлитОбувь\"'),
(13,'ЗАО \"СезонОбуви\"'),
(14,'ООО \"МирКроссовок\"'),
(15,'ИП \"ОбувьДляВсех\"'),
(16,'ООО \"ПрофОбувь\"'),
(17,'АО \"СтильКачество\"'),
(18,'ЗАО \"КомфортПлюс\"'),
(19,'ООО \"ОбувьПремиум\"'),
(20,'ИП \"УютНогам\"');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `units` (
  `idunits` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`idunits`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES
(1,'пара'),
(2,'штука'),
(3,'комплект');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `idusers` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `middlename` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idusers`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
(1,'admin','admin123','administrator','Смирнов','Алексей','Петрович'),
(2,'manager1','manager123','manager','Ковалева','Мария','Дмитриевна'),
(3,'manager2','manager456','manager','Петров','Иван','Сергеевич'),
(4,'client1','client111','client','Новиков','Артем','Сергеевич'),
(5,'client2','client222','client','Орлова','Виктория','Андреевна'),
(6,'client3','client333','client','Федоров','Дмитрий','Игоревич'),
(7,'client4','client444','client','Соколова','Елена','Владимировна'),
(8,'client5','client555','client','Морозов','Сергей','Александрович'),
(9,'client6','client666','client','Волкова','Ольга','Николаевна'),
(10,'client7','client777','client','Кузнецов','Павел','Викторович'),
(11,'client8','client888','client','Лебедева','Анна','Михайловна'),
(12,'client9','client999','client','Семенов','Максим','Олегович'),
(13,'client10','client101','client','Иванова','Татьяна','Борисовна'),
(14,'client11','client102','client','Григорьев','Андрей','Васильевич'),
(15,'client12','client103','client','Попова','Наталья','Юрьевна'),
(16,'client13','client104','client','Васильев','Роман','Денисович'),
(17,'client14','client105','client','Михайлова','Ирина','Анатольевна'),
(18,'client15','client106','client','Никитин','Владимир','Петрович'),
(19,'client16','client107','client','Захарова','Светлана','Ивановна'),
(20,'client17','client108','client','Белов','Константин','Федорович');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'demglaz'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-25 14:03:03
