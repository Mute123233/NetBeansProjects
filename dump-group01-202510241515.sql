-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 1208-1    Database: group01
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
-- Table structure for table `edinitsyizmereniya`
--

DROP TABLE IF EXISTS `edinitsyizmereniya`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `edinitsyizmereniya` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazvanie` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edinitsyizmereniya`
--

LOCK TABLES `edinitsyizmereniya` WRITE;
/*!40000 ALTER TABLE `edinitsyizmereniya` DISABLE KEYS */;
INSERT INTO `edinitsyizmereniya` VALUES
(1,'шт'),
(2,'м'),
(3,'уп'),
(4,'компл'),
(5,'пара');
/*!40000 ALTER TABLE `edinitsyizmereniya` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furnitura`
--

DROP TABLE IF EXISTS `furnitura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `furnitura` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artikul` varchar(255) NOT NULL,
  `nazvanie` varchar(255) NOT NULL,
  `idTsvet` int(11) DEFAULT NULL,
  `novinka` tinyint(1) DEFAULT 0,
  `idEdIzmereniya` int(11) DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idTsvet` (`idTsvet`),
  KEY `fk_furnitura_edizmereniya` (`idEdIzmereniya`),
  CONSTRAINT `Furnitura_ibfk_1` FOREIGN KEY (`idTsvet`) REFERENCES `tsvetfurnitury` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_furnitura_edizmereniya` FOREIGN KEY (`idEdIzmereniya`) REFERENCES `edinitsyizmereniya` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furnitura`
--

LOCK TABLES `furnitura` WRITE;
/*!40000 ALTER TABLE `furnitura` DISABLE KEYS */;
INSERT INTO `furnitura` VALUES
(1,'F001','Пуговицы',1,1,1,'https://furnitura-gusto.ru/upload/iblock/714/71434306904d3850c6cd4d668e985d3c.jpeg'),
(2,'F002','Молния',2,1,2,'https://www.art-fabric.ru/image/cache/data/catalog/fabric/_2020/08_furnitura/011601_1-800x800.jpg'),
(3,'F003','Кнопки',3,0,3,'https://groomix.ru/image/cache/catalog/Furnitura/Knopki_pukli/Snap-O-01-650x650-product_popup.jpg'),
(4,'F004','Пряжка',4,1,4,'https://cs1.livemaster.ru/storage/3e/76/f985ca379c0baf6d9553588d11va--materialy-dlya-tvorchestva-pryazhka-16-mm-2877-furnitura-dlya.jpg'),
(5,'F005','Бретель',5,1,5,'https://v-ajure-shop.ru/wp-content/uploads/2023/05/ART8653.jpg'),
(6,'F006','Крючок',6,0,1,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnluHhjiYP2jfB7gBe4HiE3gCQCj3MlJNZwg&s'),
(7,'F007','Лента',7,1,2,'https://img.joomcdn.net/9e33da17f8b8649df90d3c82b2c2bc9168345097_original.jpeg'),
(8,'F008','Резинка',8,1,3,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTYI8hC4xT_Msj6TzFsKnhY0ZDJvKvrWonwQ&s'),
(9,'F009','Тесьма',9,0,4,'https://queenfatin.com/ill/photos/huge_images/6801.jpg'),
(10,'F010','Шнур',10,1,5,'https://queenfatin.com/ill/photos/huge_images/8506.jpg');
/*!40000 ALTER TABLE `furnitura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `izdelie`
--

DROP TABLE IF EXISTS `izdelie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `izdelie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Nazvanie` varchar(255) NOT NULL,
  `Opisanie` text DEFAULT NULL,
  `Tsena` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `izdelie`
--

LOCK TABLES `izdelie` WRITE;
/*!40000 ALTER TABLE `izdelie` DISABLE KEYS */;
INSERT INTO `izdelie` VALUES
(1,'Платье','Вечернее платье приталенного силуэта',5000.00),
(2,'Брюки','Классические брюки со стрелками',3000.00),
(3,'Юбка','Юбка-карандаш',2500.00),
(4,'Пиджак','Однобортный пиджак',4500.00),
(5,'Рубашка','Мужская сорочка',2800.00),
(6,'Пальто','Двубортное пальто',8000.00),
(7,'Жилет','Свадебный жилет',2000.00),
(8,'Сарафан','Летний сарафан',3200.00),
(9,'Комбинезон','Детский комбинезон',2800.00),
(10,'Куртка','Демисезонная куртка',6000.00);
/*!40000 ALTER TABLE `izdelie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klient`
--

DROP TABLE IF EXISTS `klient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `klient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Familiya` varchar(255) NOT NULL,
  `Imya` varchar(255) NOT NULL,
  `Otchestvo` varchar(255) DEFAULT NULL,
  `Nomertelefona` varchar(20) DEFAULT NULL,
  `bonusi` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klient`
--

LOCK TABLES `klient` WRITE;
/*!40000 ALTER TABLE `klient` DISABLE KEYS */;
INSERT INTO `klient` VALUES
(1,'Волков','Андрей','Михайлович','+79167778899',100),
(2,'Зайцева','Ирина','Александровна','+79165554433',50),
(3,'Соколов','Михаил','Павлович','+79162221100',200),
(4,'Лебедева','Светлана','Олеговна','+79163334455',75),
(5,'Галкин','Артем','Игоревич','+79168889900',150);
/*!40000 ALTER TABLE `klient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `polzovateli`
--

DROP TABLE IF EXISTS `polzovateli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `polzovateli` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `familiya` varchar(50) NOT NULL,
  `imya` varchar(50) NOT NULL,
  `otchestvo` varchar(50) DEFAULT NULL,
  `login` varchar(30) NOT NULL,
  `parol` varchar(100) NOT NULL,
  `nomertelefona` varchar(20) DEFAULT NULL,
  `pasport` varchar(100) DEFAULT NULL,
  `datarozhdeniya` date DEFAULT NULL,
  `datatrudoustroystva` date DEFAULT NULL,
  `foto` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `polzovateli`
--

LOCK TABLES `polzovateli` WRITE;
/*!40000 ALTER TABLE `polzovateli` DISABLE KEYS */;
INSERT INTO `polzovateli` VALUES
(1,'Иванова','Мария','Петровна','ivanova','pass123','+79161234567','4510 123456','1985-03-15','2020-01-10',NULL),
(2,'Петров','Алексей','Сергеевич','petrov','pass456','+79167654321','4511 654321','1990-07-22','2019-05-20',NULL),
(3,'Сидорова','Ольга','Ивановна','sidorova','pass789','+79169998877','4512 789012','1988-11-30','2021-02-15',NULL),
(4,'Кузнецов','Дмитрий','Алексеевич','kuznetsov','pass321','+79161112233','4513 345678','1992-05-10','2018-03-01',NULL),
(5,'Смирнова','Елена','Викторовна','smirnova','pass654','+79164445566','4514 901234','1995-09-05','2022-04-18',NULL),
(6,'Козлов','Сергей','Владимирович','kozlov','pass111','+79160001122','4515 567890','1980-12-10','2017-06-15',''),
(7,'Орлова','Наталья','Дмитриевна','orlova','pass222','+79167776655','4516 123789','1983-04-25','2018-09-20',''),
(8,'Морозов','Иван','Анатольевич','morozov','pass333','+79168887766','4517 456123','1978-08-12','2016-11-05',''),
(9,'Васнецова','Татьяна','Сергеевна','vasnecova','pass444','+79169996655','4518 789456','1987-02-28','2019-07-30','');
/*!40000 ALTER TABLE `polzovateli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postavkafurnitury`
--

DROP TABLE IF EXISTS `postavkafurnitury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postavkafurnitury` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `idFurnitura` int(11) DEFAULT NULL,
  `Kolichestvo` int(11) DEFAULT NULL,
  `tsenaZaYedinitsu` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_postavka_furnitury_furnitura` (`idFurnitura`),
  CONSTRAINT `fk_postavka_furnitury_furnitura` FOREIGN KEY (`idFurnitura`) REFERENCES `furnitura` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postavkafurnitury`
--

LOCK TABLES `postavkafurnitury` WRITE;
/*!40000 ALTER TABLE `postavkafurnitury` DISABLE KEYS */;
INSERT INTO `postavkafurnitury` VALUES
(1,'2024-02-01',1,100,50.00),
(2,'2024-02-02',2,50,120.00),
(3,'2024-02-03',3,200,20.00),
(4,'2024-02-04',4,30,200.00),
(5,'2024-02-05',5,40,150.00);
/*!40000 ALTER TABLE `postavkafurnitury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postavkatkani`
--

DROP TABLE IF EXISTS `postavkatkani`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postavkatkani` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `idTkan` int(11) DEFAULT NULL,
  `Kolichestvo` int(11) DEFAULT NULL,
  `tsenaZaRulon` decimal(10,2) DEFAULT NULL,
  `idRazmer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_postavka_tkani_tkan` (`idTkan`),
  KEY `fk_postavka_tkani_razmer` (`idRazmer`),
  CONSTRAINT `fk_postavka_tkani_razmer` FOREIGN KEY (`idRazmer`) REFERENCES `razmer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_postavka_tkani_tkan` FOREIGN KEY (`idTkan`) REFERENCES `tkan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postavkatkani`
--

LOCK TABLES `postavkatkani` WRITE;
/*!40000 ALTER TABLE `postavkatkani` DISABLE KEYS */;
INSERT INTO `postavkatkani` VALUES
(1,'2024-02-01',1,10,2500.00,1),
(2,'2024-02-02',2,5,1800.00,2),
(3,'2024-02-03',3,8,2200.00,3),
(4,'2024-02-04',4,12,3000.00,4),
(5,'2024-02-05',5,6,3500.00,5);
/*!40000 ALTER TABLE `postavkatkani` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodavec`
--

DROP TABLE IF EXISTS `prodavec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodavec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `polzovatelid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `polzovatelid` (`polzovatelid`),
  CONSTRAINT `prodavec_ibfk_1` FOREIGN KEY (`polzovatelid`) REFERENCES `polzovateli` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodavec`
--

LOCK TABLES `prodavec` WRITE;
/*!40000 ALTER TABLE `prodavec` DISABLE KEYS */;
INSERT INTO `prodavec` VALUES
(1,1),
(2,2),
(6,6),
(4,7);
/*!40000 ALTER TABLE `prodavec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodazhafurnitury`
--

DROP TABLE IF EXISTS `prodazhafurnitury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodazhafurnitury` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProdavets` int(11) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `idFurnitura` int(11) DEFAULT NULL,
  `Kolichestvo` int(11) DEFAULT NULL,
  `tsenaZaYedinitsu` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_prodazha_furnitury_prodavets` (`idProdavets`),
  KEY `fk_prodazha_furnitury_furnitura` (`idFurnitura`),
  CONSTRAINT `fk_prodazha_furnitury_furnitura` FOREIGN KEY (`idFurnitura`) REFERENCES `furnitura` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_prodazha_furnitury_prodavets` FOREIGN KEY (`idProdavets`) REFERENCES `prodavec` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodazhafurnitury`
--

LOCK TABLES `prodazhafurnitury` WRITE;
/*!40000 ALTER TABLE `prodazhafurnitury` DISABLE KEYS */;
INSERT INTO `prodazhafurnitury` VALUES
(1,1,'2025-09-06',1,10,80.00),
(2,2,'2025-10-06',2,2,150.00),
(3,1,'2024-03-12',3,20,30.00),
(4,2,'2025-10-01',4,1,250.00),
(5,1,'2024-03-14',5,3,180.00);
/*!40000 ALTER TABLE `prodazhafurnitury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodazhatkani`
--

DROP TABLE IF EXISTS `prodazhatkani`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prodazhatkani` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProdavets` int(11) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `idTkan` int(11) DEFAULT NULL,
  `Kolichestvo` int(11) DEFAULT NULL,
  `tsenaZaRulon` decimal(10,2) DEFAULT NULL,
  `idRazmer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_prodazha_tkani_prodavets` (`idProdavets`),
  KEY `fk_prodazha_tkani_tkan` (`idTkan`),
  KEY `fk_prodazha_tkani_razmer` (`idRazmer`),
  CONSTRAINT `fk_prodazha_tkani_prodavets` FOREIGN KEY (`idProdavets`) REFERENCES `prodavec` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_prodazha_tkani_razmer` FOREIGN KEY (`idRazmer`) REFERENCES `razmer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_prodazha_tkani_tkan` FOREIGN KEY (`idTkan`) REFERENCES `tkan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodazhatkani`
--

LOCK TABLES `prodazhatkani` WRITE;
/*!40000 ALTER TABLE `prodazhatkani` DISABLE KEYS */;
INSERT INTO `prodazhatkani` VALUES
(1,1,'2025-10-06',1,2,3000.00,1),
(2,2,'2024-03-11',2,1,2500.00,2),
(3,1,'2024-03-12',3,3,2800.00,3),
(4,2,'2024-03-13',4,1,3500.00,4),
(5,1,'2024-03-14',5,2,4000.00,5);
/*!40000 ALTER TABLE `prodazhatkani` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rastsvetka`
--

DROP TABLE IF EXISTS `rastsvetka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rastsvetka` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazvanie` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rastsvetka`
--

LOCK TABLES `rastsvetka` WRITE;
/*!40000 ALTER TABLE `rastsvetka` DISABLE KEYS */;
INSERT INTO `rastsvetka` VALUES
(1,'Однотонная'),
(2,'Полоска'),
(3,'Цветочек'),
(4,'Горох'),
(5,'Камуфляж'),
(6,'Абстракция'),
(7,'Клетка'),
(8,'Пейсли'),
(9,'Этника'),
(10,'Геометрия');
/*!40000 ALTER TABLE `rastsvetka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `razmer`
--

DROP TABLE IF EXISTS `razmer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `razmer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Shirina` int(11) DEFAULT NULL,
  `Dlina` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `razmer`
--

LOCK TABLES `razmer` WRITE;
/*!40000 ALTER TABLE `razmer` DISABLE KEYS */;
INSERT INTO `razmer` VALUES
(1,150,100),
(2,140,100),
(3,130,100),
(4,120,100),
(5,110,100),
(6,150,50),
(7,140,50),
(8,130,50),
(9,120,50),
(10,110,50);
/*!40000 ALTER TABLE `razmer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shveya`
--

DROP TABLE IF EXISTS `shveya`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shveya` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `polzovatelid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `polzovatelid` (`polzovatelid`),
  CONSTRAINT `shveya_ibfk_1` FOREIGN KEY (`polzovatelid`) REFERENCES `polzovateli` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shveya`
--

LOCK TABLES `shveya` WRITE;
/*!40000 ALTER TABLE `shveya` DISABLE KEYS */;
INSERT INTO `shveya` VALUES
(1,3),
(2,4),
(3,5);
/*!40000 ALTER TABLE `shveya` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skidka`
--

DROP TABLE IF EXISTS `skidka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `skidka` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `procent` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skidka`
--

LOCK TABLES `skidka` WRITE;
/*!40000 ALTER TABLE `skidka` DISABLE KEYS */;
INSERT INTO `skidka` VALUES
(1,0),
(2,5),
(3,10),
(4,15),
(5,20),
(6,25),
(7,30);
/*!40000 ALTER TABLE `skidka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sostav`
--

DROP TABLE IF EXISTS `sostav`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sostav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazvanie` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nazvanie` (`nazvanie`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sostav`
--

LOCK TABLES `sostav` WRITE;
/*!40000 ALTER TABLE `sostav` DISABLE KEYS */;
INSERT INTO `sostav` VALUES
(9,'Акрил'),
(6,'Вискоза'),
(8,'Кашемир'),
(4,'Лен'),
(10,'Нейлон'),
(2,'Полиэстер'),
(1,'Хлопок'),
(3,'Шелк'),
(5,'Шерсть'),
(7,'Эластан');
/*!40000 ALTER TABLE `sostav` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sostavprocent`
--

DROP TABLE IF EXISTS `sostavprocent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sostavprocent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tkanyid` int(11) NOT NULL,
  `sostavid` int(11) NOT NULL,
  `procent` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tkanyid` (`tkanyid`),
  KEY `sostavid` (`sostavid`),
  CONSTRAINT `sostavprocent_ibfk_1` FOREIGN KEY (`tkanyid`) REFERENCES `tkan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sostavprocent_ibfk_2` FOREIGN KEY (`sostavid`) REFERENCES `sostav` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sostavprocent`
--

LOCK TABLES `sostavprocent` WRITE;
/*!40000 ALTER TABLE `sostavprocent` DISABLE KEYS */;
INSERT INTO `sostavprocent` VALUES
(1,1,1,100),
(2,2,2,100),
(3,3,1,98),
(4,3,7,2),
(5,4,3,100),
(6,5,4,100),
(7,6,5,100),
(8,7,6,100),
(9,8,8,100),
(10,9,9,100),
(11,10,10,100);
/*!40000 ALTER TABLE `sostavprocent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statusraboty`
--

DROP TABLE IF EXISTS `statusraboty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statusraboty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazvanie` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statusraboty`
--

LOCK TABLES `statusraboty` WRITE;
/*!40000 ALTER TABLE `statusraboty` DISABLE KEYS */;
INSERT INTO `statusraboty` VALUES
(1,'Принят'),
(2,'В работе'),
(3,'Готов к выдаче'),
(4,'Выдан'),
(5,'Отменен');
/*!40000 ALTER TABLE `statusraboty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tkan`
--

DROP TABLE IF EXISTS `tkan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tkan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artikul` varchar(255) NOT NULL,
  `nazvanie` varchar(255) NOT NULL,
  `kategoriya` varchar(255) DEFAULT NULL,
  `idTsvet` int(11) DEFAULT NULL,
  `idRastsvetka` int(11) DEFAULT NULL,
  `novinka` tinyint(1) DEFAULT 1,
  `foto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tkan_rastsvetka` (`idRastsvetka`),
  KEY `fk_tkan_tsvet` (`idTsvet`),
  CONSTRAINT `fk_tkan_rastsvetka` FOREIGN KEY (`idRastsvetka`) REFERENCES `rastsvetka` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tkan_tsvet` FOREIGN KEY (`idTsvet`) REFERENCES `tsvettkani` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tkan`
--

LOCK TABLES `tkan` WRITE;
/*!40000 ALTER TABLE `tkan` DISABLE KEYS */;
INSERT INTO `tkan` VALUES
(1,'T001','Сатин','Натуральная',1,1,1,'https://vidy-tkanej.ru/files/upload/satin-big.jpg'),
(2,'T002','Шифон','Вечерняя',2,2,1,'https://flamencotkani.ru/assets/images/products/13032/img/img_2166-(1).jpg'),
(3,'T003','Джинса','Повседневная',3,3,0,'https://magok.ru/upload/resize_cache/pics/p-fc1/mg-fc16afa0-resized-350x350-1-b392.jpg'),
(4,'T004','Креп','Деловая',4,4,1,'https://www.podushka.net/uploads/blog/1431542896.jpg'),
(5,'T005','Бархат','Вечерняя',5,5,1,'https://romatex.ru/wp-content/uploads/2024/11/barchat_fiolet-1-1024x690.jpg'),
(6,'T006','Лен','Натуральная',6,6,0,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEj1U88uXY5lb-xHmJNn8ZUSyCuIvt6UyrHA&s\nhttps://www.atelier-talisman.ru/img/photos/photo79.jpg'),
(7,'T007','Шерсть','Зимняя',7,7,1,'https://tkani-valentina.ru/image/catalog/cash1.jpg'),
(8,'T008','Кашемир','Премиум',8,8,1,'https://static.insales-cdn.com/images/products/1/920/520020888/62511ACF-B136-4797-A782-93FBCBBE2D51.jpeg'),
(9,'T009','Сетка','Декоративная',9,9,0,'https://domastkani.ru/upload/iblock/c99/rq81t7gwnzj0dchjyg1xa766mbp90b6h.webp'),
(10,'T010','Тафта','Праздничная',10,10,1,'https://domastkani.ru/upload/iblock/c99/rq81t7gwnzj0dchjyg1xa766mbp90b6h.webp');
/*!40000 ALTER TABLE `tkan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tsvetfurnitury`
--

DROP TABLE IF EXISTS `tsvetfurnitury`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tsvetfurnitury` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazvanie` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tsvetfurnitury`
--

LOCK TABLES `tsvetfurnitury` WRITE;
/*!40000 ALTER TABLE `tsvetfurnitury` DISABLE KEYS */;
INSERT INTO `tsvetfurnitury` VALUES
(1,'Золото'),
(2,'Серебро'),
(3,'Бронза'),
(4,'Хром'),
(5,'Никель'),
(6,'Черный матовый'),
(7,'Белый глянцевый'),
(8,'Прозрачный'),
(9,'Коричневый'),
(10,'Бирюзовый');
/*!40000 ALTER TABLE `tsvetfurnitury` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tsvettkani`
--

DROP TABLE IF EXISTS `tsvettkani`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tsvettkani` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nazvanie` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tsvettkani`
--

LOCK TABLES `tsvettkani` WRITE;
/*!40000 ALTER TABLE `tsvettkani` DISABLE KEYS */;
INSERT INTO `tsvettkani` VALUES
(1,'Красный'),
(2,'Синий'),
(3,'Зеленый'),
(4,'Черный'),
(5,'Белый'),
(6,'Желтый'),
(7,'Фиолетовый'),
(8,'Серый'),
(9,'Коричневый'),
(10,'Бежевый');
/*!40000 ALTER TABLE `tsvettkani` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upravlyayushchiy`
--

DROP TABLE IF EXISTS `upravlyayushchiy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `upravlyayushchiy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `polzovatelid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `polzovatelid` (`polzovatelid`),
  CONSTRAINT `upravlyayushchiy_ibfk_1` FOREIGN KEY (`polzovatelid`) REFERENCES `polzovateli` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upravlyayushchiy`
--

LOCK TABLES `upravlyayushchiy` WRITE;
/*!40000 ALTER TABLE `upravlyayushchiy` DISABLE KEYS */;
INSERT INTO `upravlyayushchiy` VALUES
(4,8),
(2,9);
/*!40000 ALTER TABLE `upravlyayushchiy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zakazfurnitura`
--

DROP TABLE IF EXISTS `zakazfurnitura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zakazfurnitura` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idFurnitura` int(11) DEFAULT NULL,
  `idZakaz` int(11) DEFAULT NULL,
  `kolichestvo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ZakazFurnitura_ZakazNaPoshiv_FK` (`idZakaz`),
  KEY `ZakazFurnitura_Furnitura_FK` (`idFurnitura`),
  CONSTRAINT `ZakazFurnitura_Furnitura_FK` FOREIGN KEY (`idFurnitura`) REFERENCES `furnitura` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ZakazFurnitura_ZakazNaPoshiv_FK` FOREIGN KEY (`idZakaz`) REFERENCES `zakaznaposhiv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zakazfurnitura`
--

LOCK TABLES `zakazfurnitura` WRITE;
/*!40000 ALTER TABLE `zakazfurnitura` DISABLE KEYS */;
INSERT INTO `zakazfurnitura` VALUES
(1,1,1,666),
(2,2,2,1),
(3,3,3,213123),
(4,4,4,2),
(5,5,5,3),
(6,2,1,123123),
(7,9,4,22);
/*!40000 ALTER TABLE `zakazfurnitura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zakaznaposhiv`
--

DROP TABLE IF EXISTS `zakaznaposhiv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zakaznaposhiv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idKlient` int(11) DEFAULT NULL,
  `idStatusRaboty` int(11) DEFAULT NULL,
  `Data` date DEFAULT NULL,
  `idShveya` int(11) DEFAULT NULL,
  `idIzdelie` int(11) DEFAULT NULL,
  `TsenaRabotyShvei` decimal(10,2) DEFAULT NULL,
  `idSkidka` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_zakaz_klient` (`idKlient`),
  KEY `fk_zakaz_status` (`idStatusRaboty`),
  KEY `fk_zakaz_shveya` (`idShveya`),
  KEY `fk_zakaz_izdelie` (`idIzdelie`),
  KEY `fk_zakaz_skidka` (`idSkidka`),
  CONSTRAINT `fk_zakaz_izdelie` FOREIGN KEY (`idIzdelie`) REFERENCES `izdelie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_zakaz_klient` FOREIGN KEY (`idKlient`) REFERENCES `klient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_zakaz_shveya` FOREIGN KEY (`idShveya`) REFERENCES `shveya` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_zakaz_skidka` FOREIGN KEY (`idSkidka`) REFERENCES `skidka` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_zakaz_status` FOREIGN KEY (`idStatusRaboty`) REFERENCES `statusraboty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zakaznaposhiv`
--

LOCK TABLES `zakaznaposhiv` WRITE;
/*!40000 ALTER TABLE `zakaznaposhiv` DISABLE KEYS */;
INSERT INTO `zakaznaposhiv` VALUES
(1,1,1,'2025-10-06',1,1,1500.00,1),
(2,2,2,'2024-03-02',2,2,1200.00,2),
(3,3,3,'2025-10-01',3,3,1000.00,3),
(4,4,4,'2025-10-04',1,4,1800.00,4),
(5,5,3,'2024-03-05',2,5,1300.00,5),
(6,1,1,'2025-10-06',1,1,1000.00,1),
(7,3,1,'2025-10-08',1,1,1000.00,1),
(8,3,5,'2025-10-08',1,1,1000.00,1),
(9,3,1,'2025-10-08',2,1,1000.00,1),
(10,3,1,'2025-10-08',3,1,1000.00,1),
(11,3,1,'2025-10-08',1,1,1000.00,1),
(12,3,1,'2025-10-08',1,1,1000.00,1),
(13,1,1,'2025-10-08',1,1,1000.00,1);
/*!40000 ALTER TABLE `zakaznaposhiv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zakaztkan`
--

DROP TABLE IF EXISTS `zakaztkan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zakaztkan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idTkan` int(11) DEFAULT NULL,
  `idZakaz` int(11) DEFAULT NULL,
  `kolichestvoTkaniM` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idTkan` (`idTkan`),
  KEY `tkanIzdelie_ZakazNaPoshiv_FK` (`idZakaz`),
  CONSTRAINT `Zakaztkan_ibfk_1` FOREIGN KEY (`idTkan`) REFERENCES `tkan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tkanIzdelie_ZakazNaPoshiv_FK` FOREIGN KEY (`idZakaz`) REFERENCES `zakaznaposhiv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zakaztkan`
--

LOCK TABLES `zakaztkan` WRITE;
/*!40000 ALTER TABLE `zakaztkan` DISABLE KEYS */;
INSERT INTO `zakaztkan` VALUES
(1,1,1,2.50),
(2,2,2,1.80),
(3,3,3,1.20),
(4,4,4,3.00),
(5,5,5,2.00),
(6,8,2,44.00),
(7,4,13,123.00),
(8,10,1,111111.00),
(9,3,7,324423.00);
/*!40000 ALTER TABLE `zakaztkan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'group01'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-24 15:15:47
