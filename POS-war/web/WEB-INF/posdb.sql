CREATE DATABASE  IF NOT EXISTS `posdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `posdb`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: posdb
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `fk_employee_no` int(11) NOT NULL,
  `admin_password` varchar(20) NOT NULL,
  PRIMARY KEY (`fk_employee_no`),
  CONSTRAINT `fk_employee_no` FOREIGN KEY (`fk_employee_no`) REFERENCES `employee` (`employee_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1244,'pass'),(1245,'pass'),(1246,'pass');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `employee_no` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phone_number` varchar(12) NOT NULL,
  `SIN` int(11) NOT NULL,
  `position` enum('admin','bartender','server') NOT NULL,
  `isActive` int(11) NOT NULL,
  `hire_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`employee_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1244,'Bruce','Lee','123 Apple St.','403-111-1111',111222333,'admin',1,'2015-01-01',NULL),(1245,'John','Smith','11 Banana Apt.','403-222-2222',222333444,'bartender',1,'2015-01-01',NULL),(1246,'Darth','Vader','333 Melon Drive','403-333-3333',333444555,'server',1,'2015-01-27',NULL),(1247,'Luke','Skywalker','2001 Berry St.','403-444-3333',333444556,'bartender',1,'2015-01-30',NULL),(1248,'Jabba','Hutt','999 Star Dr.','583-132-3322',444111666,'server',1,'2015-01-30',NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `item_no` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `item_description` varchar(200) DEFAULT NULL,
  `base_price` double NOT NULL,
  `cost` double NOT NULL,
  `itemType` varchar(45) NOT NULL,
  `numberOfSides` int(11) NOT NULL DEFAULT '0',
  `numberOfExtras` int(11) NOT NULL DEFAULT '0',
  `isItemActive` int(11) NOT NULL,
  PRIMARY KEY (`item_no`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'Fish and Chips','Mill St. Tankhouse Ale-Battered Haddock',13,13,'main',0,0,1),(2,'Chicken Strips','Chicken strips with choice of dipping sauce',13,13,'main',0,1,1),(3,'All Day Breakfast','2 Eggs any style, tomato, bacon, hash brown patties and toast',11,11,'main',0,0,1),(4,'Fried Egg Sandwich','2 Eggs, bacon, pickles, lettuce, tomato and chipotle aioli on a brioche bun',11,11,'main',0,0,1),(5,'Steak Sandwich','6 oz hanger steak, topped with herb butter and cripsy onions. Served on toasted focaccia',15,15,'main',2,0,1),(6,'Chicken Ceasar Wrap','Grilled chicken, romaine lettuce, asiago cheese, house-moude dressing',13,13,'main',1,1,1),(7,'Ham and Cheese Wrap','Thin-sliced ham, lettuce, tomato, crispy onions and honey mustard',13,13,'main',1,0,1),(8,'Quesadilla','Sauteed zucchini, red pepper, onions and cheese blend. Choice of chicken, beef, ham, or double veg.',13,13,'main',0,2,1),(9,'Ham and Onion Grilled Cheese','Thin-sliced ham, caramelized cajun onions and monterey jack cheese on rhye bread ',13,13,'main',1,0,1),(10,'Inferno Burger','Guacamole, crispy onions, jalapenos, hot sauce and mozzarella',13,13,'main',1,1,1),(11,'Poipu Burger','Vegan patty, grilled pineapple, guacamole, lettuce and tomato, served on a vegan bun',13,13,'main',1,1,1),(12,'Chicken Bruschetta','Grilled chicken breast, house-made bruschetta, lettuce, asiago and garlic aioli',13,13,'main',1,1,1),(14,'Nachos','Blended cheese, olives, pickled jalapenos, tomato, caramelized cajun onions, topped with green onions, with salsa and sour cream',7,14,'appetizer',0,3,1),(15,'Wings','One pound of buttermilk and peppercorn marinated wings tossed in your choice of sauce. Served with a side of ranch.',12,12,'appetizer',0,1,1),(16,'Cauliflower Wings','Breaded cauliflower florets tossed in your choice of sauce',10,10,'appetizer',0,0,1),(17,'Chickpea Fries','House made with your choice of dipping sauce',9,9,'appetizer',0,1,1),(18,'Hummus Plate','Pita and seasonal grilled vegetables',10,10,'appetizer',0,0,1),(19,'Poutine','House-cut fries, cheese curds and gravy',5,10,'appetizer',0,0,1),(20,'Onion Rings','Battered with Mill St. Tankhouse ale, handmade to order',7,7,'appetizer',0,0,1),(21,'Bruschetta','Served on toasted baguette',7,7,'appetizer',0,0,1),(22,'Zucchini Corn Fritters','House-Made, vegan friendly. Choice of sauce to dip.',8,8,'appetizer',0,1,1),(23,'Focaccia and Marinara','Toasted to perfection',7,7,'appetizer',0,2,1),(24,'Broken Salad','House-mixed greens, fresh veggies, lemon oil and balsamic vinegar',9,9,'salad',0,0,1),(25,'Lentil Quinoa Salad','Sprouted Lentils, quinoa, roasted pumpking seeds, dried cranberries, lemon oil and balsamic vinegar',11,11,'salad',0,0,1),(26,'Vegan Chili','Served with garlic focaccia',8,8,'soup',0,0,1),(27,'Ceasar Salad','Romaine, croutons, asiago cheese, bacon bits, and house-made dressing',9,9,'salad',0,0,1),(28,'Soup of the Day','Served with toasted garlic focaccia',8,8,'soup',0,0,1),(29,'Grilled Steak Salad','Grilled 6 oz. hanger steak with tossed greens, pickled beets, cancied almonds and apple cider vinaigrette',15,15,'salad',0,0,1),(30,'Grilled Veggie Salad','Grilled Zucchini, red peppers, mushrooms and pickled beets. Tossed in apple cider vinaigrette',11,11,'salad',0,0,1),(31,'Strawberry Salad','Romain lettuce, strawberries, cucumbers, green onions, and toasted sunflower seeds tossed in a honey balsamic dressing',11,11,'salad',0,0,1),(32,'Eggs Benedict','House-made tea biscuits, bacon and brown butter hollandaise, served with hashbrowns',12,12,'brunch',1,1,1),(33,'Breakfast Poutine','Choice of hash browns or fries, cheese curds, gravy and 2 eggs any style',12,12,'brunch',0,1,1),(34,'Steak and Eggs','6 oz. hanger steak, 2 eggs, hash browns, topped with bechamel cheese sauce',15,15,'brunch',1,1,1),(35,'Fried Egg Sandwich','2 eggs, bacon, pickles, lettuce, tomato and chipotle aioli on a brioche bun. Served with hashbrown patties',11,11,'brunch',1,1,1),(36,'Cornflake and Coconut Crusted French Toast','With cinnamon and brown sugar whipped sour cream',10,10,'brunch',1,1,1),(37,'Apple Cinnamon Pancakes','Topped with fresh fruit',10,10,'brunch',1,1,1),(38,'Breakfast Bowl','Hash browns, vegan chili, 2 scrambled eggs, cheese and fresh salsa',11,11,'brunch',0,1,1),(39,'Beau\'s Breakfast','Bacon, sausage, hash browns, toast, 2 eggs with house-made jam and cashew butter',13,13,'brunch',0,1,1),(40,'Green Eggs and Ham','2 eggs any style, thick slice of ham and salsa verde. Served with hash browns',12,12,'brunch',0,1,1),(41,'Ham and Onion Grilled Cheese','Thin-sliced ham, caramelized cajun onions and monterey jack cheese on rhye bread served with hash browns',13,13,'brunch',0,1,1),(42,'Fruit and Ice Cream','Fresh fruit and vanilla ice cream',5,5,'dessert',0,0,1),(43,'Cookies and Ice Cream','House-made cookies served with vanilla ice cream',5,5,'dessert',0,0,1),(44,'Fruit Parfait','Fresh fruit, yogurt and granola',5,5,'dessert',0,0,1),(45,'Broken City Burger','Garlic mushrooms, bacon, lettuce, tomato, chipotle aioli and monterey jack cheese',13,13,'main',1,1,1),(46,'Coke','Coca Cola Drink',1.5,1,'drink',0,0,1),(47,'Mountain Dew','Lemon Citrus Soda',1.5,3,'drink',0,0,1),(50,'Domestic Beer','$5.50',5.5,4,'speed bar',0,0,1),(52,'Import Beer','$7.50',7.5,5,'speed bar',0,0,1),(53,'Single Shot Regular','$5.50',5.5,4,'speed bar',0,0,1),(54,'Double Shot Regular','$11.00',11,8,'speed bar',0,0,1),(55,'Single Shot Premium','$6.50',6.5,5.5,'speed bar',0,0,1),(56,'Double Shot Premium','$13.00',13,11,'speed bar',0,0,1);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_on_order`
--

DROP TABLE IF EXISTS `item_on_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_on_order` (
  `pk_item_order` int(11) NOT NULL AUTO_INCREMENT,
  `fk_order` int(11) NOT NULL,
  `fk_item` int(11) NOT NULL,
  `side1` int(11) DEFAULT NULL,
  `side2` int(11) DEFAULT NULL,
  `side3` int(11) DEFAULT NULL,
  `extra1` int(11) DEFAULT NULL,
  `extra2` int(11) DEFAULT NULL,
  `extra3` int(11) DEFAULT NULL,
  `item_comment` varchar(300) DEFAULT NULL,
  `item_total` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`pk_item_order`),
  KEY `fk_item_no_idx` (`fk_item`),
  KEY `fk_order_idx` (`fk_order`),
  CONSTRAINT `fk_item` FOREIGN KEY (`fk_item`) REFERENCES `item` (`item_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order` FOREIGN KEY (`fk_order`) REFERENCES `order` (`order_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_on_order`
--

LOCK TABLES `item_on_order` WRITE;
/*!40000 ALTER TABLE `item_on_order` DISABLE KEYS */;
INSERT INTO `item_on_order` VALUES (1,1,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `item_on_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_on_order_log`
--

DROP TABLE IF EXISTS `item_on_order_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_on_order_log` (
  `item_on_order_log` int(11) NOT NULL,
  `order_no_log` int(11) NOT NULL,
  `item_no_log` int(11) NOT NULL,
  `side1_log` int(11) DEFAULT NULL,
  `side2_log` int(11) DEFAULT NULL,
  `side3_log` int(11) DEFAULT NULL,
  `extra1_log` int(11) DEFAULT NULL,
  `extra2_log` int(11) DEFAULT NULL,
  `extra3_log` int(11) DEFAULT NULL,
  `item_comment_log` varchar(300) DEFAULT NULL,
  `item_total_log` double DEFAULT '0',
  `item_date` datetime DEFAULT NULL,
  PRIMARY KEY (`item_on_order_log`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_on_order_log`
--

LOCK TABLES `item_on_order_log` WRITE;
/*!40000 ALTER TABLE `item_on_order_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_on_order_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_option`
--

DROP TABLE IF EXISTS `item_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_option` (
  `pk_item_option` int(11) NOT NULL AUTO_INCREMENT,
  `fk_item_no` int(11) NOT NULL,
  `fk_option_no` int(11) NOT NULL,
  PRIMARY KEY (`pk_item_option`),
  KEY `fk_item_no_idx` (`fk_item_no`),
  KEY `fk_option_num_idx` (`fk_option_no`),
  CONSTRAINT `fk_item_num` FOREIGN KEY (`fk_item_no`) REFERENCES `item` (`item_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_option_num` FOREIGN KEY (`fk_option_no`) REFERENCES `option` (`option_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_option`
--

LOCK TABLES `item_option` WRITE;
/*!40000 ALTER TABLE `item_option` DISABLE KEYS */;
INSERT INTO `item_option` VALUES (1,2,30),(2,2,31),(3,2,32),(4,2,33),(5,2,34),(6,2,35),(7,2,36),(8,2,37),(9,2,38),(10,2,39),(11,5,5),(12,5,6),(13,5,7),(14,5,8),(15,5,9),(16,5,10),(17,5,11),(18,5,12),(19,5,13),(20,5,14),(21,6,5),(22,6,6),(23,6,7),(24,6,8),(25,6,9),(26,6,10),(27,6,11),(28,6,12),(29,6,13),(30,6,14),(31,6,63),(32,7,5),(33,7,6),(34,7,7),(35,7,8),(36,7,9),(37,7,10),(38,7,11),(39,7,12),(40,7,13),(41,7,14),(42,8,56),(43,8,57),(44,8,58),(45,8,59),(46,8,3),(47,9,5),(48,9,6),(49,9,7),(50,9,8),(51,9,9),(52,9,10),(53,9,11),(54,9,12),(55,9,13),(56,9,14),(57,10,60),(58,10,61),(59,10,62),(60,10,5),(61,10,6),(62,10,7),(63,10,8),(64,10,9),(65,10,10),(66,10,11),(67,10,12),(68,10,13),(69,10,14),(70,11,60),(71,11,61),(72,11,62),(73,11,5),(74,11,6),(75,11,7),(76,11,8),(77,11,9),(78,11,10),(79,11,11),(80,11,12),(81,11,13),(82,11,14),(83,12,60),(84,12,61),(85,12,62),(86,12,5),(87,12,6),(88,12,7),(89,12,8),(90,12,9),(91,12,10),(92,12,11),(93,12,12),(94,12,13),(95,12,14),(96,45,60),(97,45,61),(98,45,62),(99,45,5),(100,45,6),(101,45,7),(102,45,8),(103,45,9),(104,45,10),(105,45,11),(106,45,12),(107,45,13),(108,45,14),(109,14,16),(110,14,17),(111,14,18),(112,14,19),(113,15,30),(114,15,31),(115,15,32),(116,15,33),(117,15,34),(118,15,35),(119,15,36),(120,15,37),(121,15,38),(122,15,39),(123,17,30),(124,17,31),(125,17,32),(126,22,30),(127,22,31),(128,22,32),(129,22,33),(130,22,34),(131,22,35),(132,22,36),(133,22,37),(134,22,38),(135,22,39),(136,23,40),(137,23,41),(138,32,42),(139,32,43),(140,32,44),(141,32,45),(142,32,46),(143,32,47),(144,32,48),(145,32,49),(146,32,50),(147,32,51),(148,33,42),(149,33,43),(150,33,44),(151,33,45),(152,33,46),(153,33,47),(154,33,48),(155,33,49),(156,33,50),(157,33,51),(158,34,42),(159,34,43),(160,34,44),(161,34,45),(162,34,46),(163,34,47),(164,34,48),(165,34,49),(166,34,50),(167,34,51),(168,35,42),(169,35,43),(170,35,44),(171,35,45),(172,35,46),(173,35,47),(174,35,48),(175,35,49),(176,35,50),(177,35,51),(178,36,42),(179,36,43),(180,36,44),(181,36,45),(182,36,46),(183,36,47),(184,36,48),(185,36,49),(186,36,50),(187,36,51),(188,37,42),(189,37,43),(190,37,44),(191,37,45),(192,37,46),(193,37,47),(194,37,48),(195,37,49),(196,37,50),(197,37,51),(198,33,52),(199,33,53),(200,38,54),(201,38,55),(202,38,45),(203,38,46),(204,38,47),(205,38,48),(206,38,49),(207,38,50),(208,38,51),(209,39,45),(210,39,46),(211,39,47),(212,39,48),(213,39,49),(214,39,50),(215,39,51),(216,40,45),(217,40,46),(218,40,47),(219,40,48),(220,40,49),(221,40,50),(222,40,51),(223,41,45),(224,41,46),(225,41,47),(226,41,48),(227,41,49),(228,41,50),(229,41,51);
/*!40000 ALTER TABLE `item_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `option`
--

DROP TABLE IF EXISTS `option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `option` (
  `option_no` int(11) NOT NULL AUTO_INCREMENT,
  `option_name` varchar(100) NOT NULL,
  `option_description` varchar(200) DEFAULT NULL,
  `option_price` double NOT NULL,
  `option_cost` double NOT NULL,
  `optionType` varchar(45) NOT NULL,
  `isOptionActive` int(11) NOT NULL,
  PRIMARY KEY (`option_no`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option`
--

LOCK TABLES `option` WRITE;
/*!40000 ALTER TABLE `option` DISABLE KEYS */;
INSERT INTO `option` VALUES (3,'Quesadilla Gucacamole','Quesadilla guacamole',1.5,1,'extra',1),(5,'Ceasar Salad','Main sides',0,0,'side',1),(6,'House Salad','Main sides',0,0,'side',1),(7,'Lentil Quinoa Salad','Main sides',0,0,'side',1),(8,'Vegan Chili','Main sides',0,0,'side',1),(9,'Soup','Main sides',0,0,'side',1),(10,'Hash Brown Patties','Main sides',0,0,'side',1),(11,'Fries','Main sides',0,0,'side',1),(12,'Sub Chickpea Fies','Main sides',1,1,'side',1),(13,'Onion Rings','Main sides',1,1,'side',1),(14,'Poutine','Main sides',2,2,'side',1),(15,'Burger Bacon','Burger add bacon',3,3,'extra',1),(16,'Nachos Chicken','Nachos meat',4,4,'extra',1),(17,'Nachos Beef','Nachos meat',4,4,'extra',1),(18,'Nachos Cheese','Nachos cheese',1.5,1.5,'extra',1),(19,'Nachos Guacamole','Nachos guacamole',3,3,'extra',1),(30,'Garlic Aioli Sauce','Dipping Sauce',0,0,'extra',1),(31,'Chipotle Aioli','Dipping Sauce',0,0,'extra',1),(32,'Ranch Sauce','Dipping Sauce',0,0,'extra',1),(33,'Hot Sauce','Dipping Sauce',0,0,'extra',1),(34,'Teriyaki Sauce','Dipping Sauce',0,0,'extra',1),(35,'Honey Garlic Sauce','Dipping Sauce',0,0,'extra',1),(36,'BBQ Sauce','Dipping Sauce',0,0,'extra',1),(37,'Suicide Sauce','Dipping Sauce',0,0,'extra',1),(38,'Apple Bourbon BBQ Sauce','Dipping Sauce',0,0,'extra',1),(39,'Honey Mustard Sauce','Dipping Sauce',0,0,'extra',1),(40,'Focaccia Cheese','Focaccia add cheese',1.5,1.5,'extra',1),(41,'Focaccia Bacon Bits','Focaccia add bacon bits',1.5,1.5,'extra',1),(42,'Brunch Hash Browns','Brunch Side',0,0,'side',1),(43,'Brunch Hash Brown Patties','Brunch Side',0,0,'side',1),(44,'Brunch Fruit Cup','Brunch Side',0,0,'side',1),(45,'Extra Bacon','Brunch Sides',3,3,'side',1),(46,'Extra Sausage','Brunch Sides',3,3,'side',1),(47,'Extra Toast','Brunch Sides',1,1,'side',1),(48,'Extra Strawberry Jam','Brunch Sides',1,1,'side',1),(49,'Extra Cashew Butter','Brunch Sides',1,1,'side',1),(50,'Extra Fruit Cup','Brunch Sides',3,3,'side',1),(51,'Extra Hash or Patties','Brunch Sides',3,3,'side',1),(52,'Breakfast Poutine Hash','Breakfast poutine side',0,0,'side',1),(53,'Breakfast Poutine Fries','Breakfast poutine side',0,0,'side',1),(54,'Breakfast Bowl Chicken','Extra Chicken',4,4,'extra',1),(55,'Breakfast Bowl Beef','Extra Beef',4,4,'extra',1),(56,'Quesadilla Chicken','Quesadilla Meat',0,0,'extra',1),(57,'Quesadilla Beef','Quesadilla Meat',0,0,'extra',1),(58,'Quesadilla Ham','Quesadilla Meat',0,0,'extra',1),(59,'Quesadilla Double Veg','Quesadilla Meat',0,0,'extra',1),(60,'Chicken Patty','Burger Patty',0,0,'extra',1),(61,'Beef Patty','Burger Patty',0,0,'extra',1),(62,'Vegan Patty','Burger Patty',0,0,'extra',1),(63,'Bacon Bits','Ceasar Salad Extra',1.5,1.5,'extra',1);
/*!40000 ALTER TABLE `option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_no` int(11) NOT NULL AUTO_INCREMENT,
  `fk_table_no` int(11) NOT NULL,
  `seat_no` int(11) NOT NULL,
  `order_total` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_no`),
  KEY `fk_table_no_idx` (`fk_table_no`),
  CONSTRAINT `fk_table_no` FOREIGN KEY (`fk_table_no`) REFERENCES `table` (`table_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,1,1,0);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_log`
--

DROP TABLE IF EXISTS `order_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_log` (
  `order_no_log` int(11) NOT NULL,
  `table_no_log` int(11) NOT NULL,
  `seat_no_log` int(11) NOT NULL,
  `order_total_log` double NOT NULL DEFAULT '0',
  `payment_type` varchar(45) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_no_log`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_log`
--

LOCK TABLES `order_log` WRITE;
/*!40000 ALTER TABLE `order_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_log`
--

DROP TABLE IF EXISTS `receipt_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `receipt_log` (
  `receipt_no` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`receipt_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_log`
--

LOCK TABLES `receipt_log` WRITE;
/*!40000 ALTER TABLE `receipt_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `receipt_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shift`
--

DROP TABLE IF EXISTS `shift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shift` (
  `shift_no` int(11) NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `clocked_in` char(1) NOT NULL,
  `employee_no` int(11) NOT NULL,
  PRIMARY KEY (`shift_no`),
  KEY `employee_no_idx` (`employee_no`),
  CONSTRAINT `employee_no` FOREIGN KEY (`employee_no`) REFERENCES `employee` (`employee_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shift`
--

LOCK TABLES `shift` WRITE;
/*!40000 ALTER TABLE `shift` DISABLE KEYS */;
INSERT INTO `shift` VALUES (19,'2015-01-23 22:58:53','2015-01-25 12:02:14','N',1244),(20,'2015-01-23 23:00:56','2015-01-24 13:15:31','N',1245),(21,'2015-02-03 12:02:11','2015-02-03 15:02:14','N',1244),(22,'2015-02-03 13:15:29','2015-02-03 15:15:31','N',1245),(23,'2015-02-19 12:16:30','2015-02-19 13:07:04','N',1246),(24,'2015-02-19 13:07:07',NULL,'Y',1246),(25,'2015-02-24 12:02:53',NULL,'Y',1248),(26,'2015-03-02 11:18:04',NULL,'Y',1244),(27,'2015-03-24 12:36:44',NULL,'Y',1245);
/*!40000 ALTER TABLE `shift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `special`
--

DROP TABLE IF EXISTS `special`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `special` (
  `special_no` int(11) NOT NULL AUTO_INCREMENT,
  `special_name` varchar(100) DEFAULT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  PRIMARY KEY (`special_no`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `special`
--

LOCK TABLES `special` WRITE;
/*!40000 ALTER TABLE `special` DISABLE KEYS */;
INSERT INTO `special` VALUES (1,'Happy Hour','14:00:00','20:00:00'),(8,'Nacho Tuesday','14:00:00','23:00:00'),(9,'Poutine Day','13:00:00','18:00:00'),(11,'Nacho Wednesday','14:00:00','23:00:00');
/*!40000 ALTER TABLE `special` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `special_day`
--

DROP TABLE IF EXISTS `special_day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `special_day` (
  `pk_special_day` int(11) NOT NULL AUTO_INCREMENT,
  `fk_special` int(11) NOT NULL,
  `day_of_week` int(11) NOT NULL,
  PRIMARY KEY (`pk_special_day`),
  KEY `fk_special_idx` (`fk_special`),
  CONSTRAINT `fk_special` FOREIGN KEY (`fk_special`) REFERENCES `special` (`special_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `special_day`
--

LOCK TABLES `special_day` WRITE;
/*!40000 ALTER TABLE `special_day` DISABLE KEYS */;
INSERT INTO `special_day` VALUES (1,1,0),(8,8,1),(9,9,2),(10,11,2);
/*!40000 ALTER TABLE `special_day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `special_item`
--

DROP TABLE IF EXISTS `special_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `special_item` (
  `fk_special_item` int(11) NOT NULL,
  `item_on_special` int(11) NOT NULL,
  `discounted` int(11) NOT NULL,
  `original` int(11) NOT NULL,
  KEY `fk_special_item_idx` (`fk_special_item`),
  KEY `fk_item_idx` (`item_on_special`),
  CONSTRAINT `fk_special_item` FOREIGN KEY (`fk_special_item`) REFERENCES `special` (`special_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `special_item`
--

LOCK TABLES `special_item` WRITE;
/*!40000 ALTER TABLE `special_item` DISABLE KEYS */;
INSERT INTO `special_item` VALUES (1,15,6,12),(8,14,7,14),(9,19,5,10),(11,14,7,14);
/*!40000 ALTER TABLE `special_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table`
--

DROP TABLE IF EXISTS `table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table` (
  `table_no` int(11) NOT NULL,
  `table_active` int(11) NOT NULL DEFAULT '0',
  `server_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`table_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table`
--

LOCK TABLES `table` WRITE;
/*!40000 ALTER TABLE `table` DISABLE KEYS */;
INSERT INTO `table` VALUES (0,1,NULL),(1,0,NULL),(2,0,NULL),(3,0,NULL),(4,0,NULL),(5,0,NULL),(6,0,NULL),(7,0,NULL),(8,0,NULL),(9,0,NULL),(10,0,NULL),(11,1,NULL),(12,0,NULL),(13,0,NULL),(14,0,NULL),(15,0,NULL),(16,0,NULL),(17,0,NULL),(18,0,NULL),(19,0,NULL),(20,0,NULL),(21,0,NULL),(22,0,NULL),(23,0,NULL),(24,0,NULL),(25,0,NULL),(26,0,NULL),(27,0,NULL),(28,0,NULL),(29,0,NULL),(30,0,NULL),(31,0,NULL),(32,0,NULL),(33,0,NULL),(34,0,NULL),(35,0,NULL),(36,0,NULL),(37,0,NULL),(38,0,NULL),(39,0,NULL),(40,0,NULL),(41,0,NULL),(42,0,NULL),(43,0,NULL),(44,0,NULL),(45,0,NULL),(46,0,NULL),(47,0,NULL),(48,0,NULL),(49,0,NULL),(50,0,NULL),(51,0,NULL),(52,0,NULL),(53,0,NULL),(54,0,NULL),(55,0,NULL),(56,0,NULL),(57,0,NULL),(58,0,NULL),(59,0,NULL),(60,0,NULL),(61,0,NULL),(62,0,NULL),(63,0,NULL),(64,0,NULL),(65,0,NULL),(66,0,NULL),(67,0,NULL),(68,0,NULL),(69,0,NULL),(70,0,NULL),(71,0,NULL),(72,0,NULL),(73,0,NULL),(74,0,NULL),(75,0,NULL),(76,0,NULL),(77,0,NULL),(78,0,NULL),(79,0,NULL),(80,0,NULL),(81,0,NULL),(82,0,NULL),(83,0,NULL),(84,0,NULL),(85,0,NULL),(86,0,NULL),(87,0,NULL),(88,0,NULL),(89,0,NULL),(90,0,NULL),(91,0,NULL),(92,0,NULL),(93,0,NULL),(94,0,NULL),(95,0,NULL),(96,0,NULL),(97,0,NULL),(98,0,NULL),(99,0,NULL),(100,1,NULL);
/*!40000 ALTER TABLE `table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'posdb'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `myEvent` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `myEvent` ON SCHEDULE EVERY 15 MINUTE STARTS '2015-03-25 10:29:19' ON COMPLETION NOT PRESERVE ENABLE DO begin
            CALL updateSpecial();
        end */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'posdb'
--
/*!50003 DROP FUNCTION IF EXISTS `addNewItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `addNewItem`(itemname VARCHAR(45), 
	itemdesc VARCHAR(200), base DOUBLE, cost DOUBLE,
    itemtype ENUM('main','burger','appetizer','soup',
    'salad','brunch','extra','dessert','side','others','drink','speed bar'),
    numsides INT(11), numoptions INT(11), isactive INT(1)) RETURNS int(11)
BEGIN
	INSERT INTO item (item_name,item_description,
    base_price,cost,itemType,numberOfSides,numberOfExtras,isItemActive)
    VALUES (itemname,itemdesc,base,cost,
    itemtype,numsides,numoptions,isactive);
RETURN LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `addNewItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `addNewItemOnOrder`(orderNumber int, itemNumber int) RETURNS int(11)
BEGIN
	INSERT INTO `posdb`.`item_on_order`(fk_order,fk_item,item_total) VALUES(orderNumber,itemNumber,0);
    
RETURN LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `addNewOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `addNewOrder`(tableNumber int, seatNumber int) RETURNS int(11)
BEGIN
	INSERT INTO `posdb`.`order` VALUES(0, tableNumber, seatNumber,0);

RETURN LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addEmployee`(IN empNo INT(11),
		IN fName varchar(45),IN lName varchar(45),IN adrs varchar(50),
		IN phone VARCHAR(12),IN sin INT(11),
        IN position VARCHAR(9),IN hiredate DATETIME)
BEGIN
	INSERT INTO employee (employee_no,first_name,last_name,address,phone_number,
		SIN,position,isActive,hire_date) VALUES (empNo,fName,lName,adrs,phone,
       sin,position,1,hiredate);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addItem`(IN itemname VARCHAR(45), 
	IN itemdesc VARCHAR(100), IN base DOUBLE, IN cost DOUBLE,
    IN itemtype ENUM('main','burger','appetizer','soup',
    'salad','brunch','extra','dessert','side','others','drink','speed bar'),
    IN numsides INT(11), IN numoptions INT(11), IN isactive INT(1))
BEGIN
	INSERT INTO item (item_name,item_description,
    base_price,cost,itemType,numberOfSides,numberOfExtras,isItemActive)
    VALUES (itemname,itemdesc,base,cost,
    itemtype,numsides,numoptions,isactive);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addItemOnOrder`(IN fkorder INT,IN seatnum INT,IN fkitem INT,IN sid1 INT,IN sid2 INT,
								IN sid3 INT,IN ext1 INT,IN ext2 INT,IN ext3 INT,IN itemcomment VARCHAR(300))
BEGIN
	INSERT INTO item_on_order (fk_order,seat_no,fk_item,side1,side2,side3,extra1,extra2,extra3,item_comment)
    VALUES (fkorder,seatnum,fkitem,sid1,sid2,sid3,ext1,ext2,ext3,itemcomment);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addItemOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addItemOption`(IN inum INT, IN onum INT)
BEGIN
	INSERT INTO item_option (fk_item_no,fk_option_no) VALUES (inum,onum);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addOption`(IN optionname VARCHAR(100), IN optiondesc VARCHAR(200),
	IN optprice DOUBLE, IN optcost DOUBLE, IN opttype ENUM('extra','side'), IN optstatus INT(11), IN inum INT(11))
BEGIN
	INSERT INTO `posdb`.`option` (option_name,
    option_description,option_price,option_cost,optionType,isOptionActive) 
    VALUES (optionname,optiondesc,optprice,optcost,opttype,optstatus);
    
    CALL addItemOption(inum,LAST_INSERT_ID());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addOrder`(IN tabnum INT,IN seatnum INT)
BEGIN
	INSERT INTO `order` (fk_table_no,seat_no)
    VALUES (tabnum,seatnum);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addPassword` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addPassword`(IN empno INT,IN pw VARCHAR(20))
BEGIN
	INSERT INTO admin (fk_employee_no,admin_password) VALUES (empno,pw);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addSpecial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSpecial`(IN specname VARCHAR(100),IN starttime TIME,
				IN endtime TIME)
BEGIN
	INSERT INTO special (special_name,start_time,end_time)
    VALUES (specname,starttime,endtime);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addSpecialDay` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSpecialDay`(IN specno INT, IN dow INT)
BEGIN
	INSERT INTO special_day (fk_special,day_of_week) VALUES (specno,dow);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addSpecialItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSpecialItem`(IN specnum INT, IN inum INT, IN disc DOUBLE)
BEGIN
	DECLARE original   DOUBLE;
    
    SELECT base_price INTO original FROM item WHERE item_no=inum;
    
    INSERT INTO special_item VALUES (specnum,inum,disc,original);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addTable`(IN tabnum INT,IN isactive INT,IN servernum INT)
BEGIN
	INSERT INTO `table` VALUES (tabnum,isactive,servernum);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `closeOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `closeOrder`(IN ordernum INT)
BEGIN
	DELETE FROM `item_on_order` WHERE pk_item_order=ordernum;
    
    DELETE FROM `order` WHERE order_no=ordernum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `closeTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `closeTable`(IN tablenum INT)
BEGIN
	DECLARE tcount  INT;
    
    SELECT COUNT(*) INTO tcount FROM `order` WHERE fk_table_no=tablenum;
    
    IF(tcount=0) THEN
		CALL disableTable(tablenum);
        
        UPDATE `table` SET server_no=null WHERE table_no=tablenum;
    END IF;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `disableTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `disableTable`(IN tablenum INT)
BEGIN
	UPDATE `table` SET table_active=0 WHERE table_no=tablenum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `enableTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `enableTable`(IN tablenum INT)
BEGIN
	UPDATE `table` SET table_active=1 WHERE table_no=tablenum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `endShift` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `endShift`(empno INT)
BEGIN
	IF(select MAX(shift_no) from shift where employee_no=empno AND end_time IS NULL) IS NOT NULL THEN
		UPDATE `posdb`.`shift` SET end_time=SYSDATE(), clocked_in='N' 
		WHERE employee_no=empno AND 
        shift_no=(select shift_no from
        (select MAX(shift_no) from shift) as x);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getActiveEmployees` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getActiveEmployees`()
BEGIN
	SELECT * FROM
    employee WHERE isActive=1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getActiveExtras` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getActiveExtras`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE optionType='extra' AND isOptionActive=1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getActiveItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getActiveItems`()
BEGIN
	SELECT * FROM item where isItemActive=1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getActiveOptions` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getActiveOptions`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE isOptionActive=1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getActiveSides` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getActiveSides`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE optionType='side' AND isOptionActive=1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getActiveSpecials` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getActiveSpecials`()
BEGIN
    CREATE TEMPORARY TABLE IF NOT EXISTS my_temp_table
	SELECT fk_item_no FROM special WHERE (day_of_week=weekday(curdate()) AND start_time<=curtime() AND
    end_time>=curtime());
    
    SELECT * FROM my_temp_table;
    
    DROP TABLE my_temp_table;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllEmployees` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllEmployees`()
BEGIN
	SELECT * FROM employee
	ORDER BY isActive DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllExtras` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllExtras`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE optionType='extra';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllItemOnOrder`()
BEGIN
	SELECT * FROM item_on_order;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllItems`()
BEGIN
	SELECT * FROM item;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllOption`()
BEGIN
	SELECT * FROM `posdb`.`option`;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllOrders` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllOrders`()
BEGIN
	SELECT * FROM `order`;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllSides` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllSides`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE optionType='side';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllSpecials` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllSpecials`()
BEGIN
	SELECT * FROM special;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getAllTables` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllTables`()
BEGIN
	SELECT * FROM `table`;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getDaysForSpecial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getDaysForSpecial`(IN sno INT)
BEGIN
	SELECT day_of_week FROM special_day WHERE fk_special=sno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getEmployee`(IN empno INT)
BEGIN
	SELECT * FROM employee
    WHERE employee_no=empno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getExportData` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getExportData`(IN fromdate DATETIME, IN todate DATETIME)
BEGIN 
    SELECT item_no,item_name,base_price,item_total_log FROM item_on_order_log 
    JOIN item ON item_on_order_log.item_no_log=item.item_no
    WHERE item_date BETWEEN fromdate and todate;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getExtraInfo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getExtraInfo`(IN optionnum INT)
BEGIN
	SELECT option_name,option_price FROM `option`
    WHERE option_no=optionnum AND optionType='extra';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getInactiveEmployees` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getInactiveEmployees`()
BEGIN
	SELECT * FROM
    employee WHERE isActive=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getInactiveExtras` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getInactiveExtras`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE optionType='extra' AND isOptionActive=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getInactiveItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getInactiveItems`()
BEGIN
	SELECT * FROM item where isItemActive=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getInactiveOptions` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getInactiveOptions`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE isOptionActive=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getInactiveSides` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getInactiveSides`()
BEGIN
	SELECT * FROM `posdb`.`option` WHERE optionType='side' AND isOptionActive=0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItem`(IN num INT(11))
BEGIN
	SELECT * FROM item where item_no=num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getItemOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemOption`(IN itemnum INT)
BEGIN
	SELECT fk_option_no FROM item_option WHERE fk_item_no=itemnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getItemsForOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsForOrder`(IN orderNo INT)
BEGIN
SELECT pk_item_order, fk_item, side1, side2, side3, extra1, extra2, extra3,
item_comment, item_total
   FROM item_on_order 
   WHERE fk_order = orderNo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getItemsForSeat` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsForSeat`(IN ordernum INT)
BEGIN
	SELECT item_no, item_name, base_price, side1, side2, side3, extra1, extra2, extra3 
    FROM item_on_order JOIN item ON item_on_order.fk_item=item.item_no 

    WHERE fk_order=ordernum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getItemsForSpecial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getItemsForSpecial`(IN specialno INT)
BEGIN
	SELECT item_on_special FROM special_item WHERE fk_special_item=specialno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getNumberOfExtras` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getNumberOfExtras`(IN num INT(11))
BEGIN
	SELECT numberOfExtras FROM `posdb`.`item` WHERE item_no=num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getNumberOfSeats` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getNumberOfSeats`(IN tablenum INT)
BEGIN
	SELECT COUNT(seat_no) FROM `order` WHERE fk_table_no=tablenum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getNumberOfSides` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getNumberOfSides`(IN num INT(11))
BEGIN
	SELECT numberOfSides FROM `posdb`.`item` WHERE item_no=num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOption`(IN optnum INT(11))
BEGIN
	SELECT * FROM `posdb`.`option` WHERE option_no=optnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOrder`(IN ordernum INT)
BEGIN
	SELECT * FROM `order` WHERE order_no=ordernum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getOrderNumber` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOrderNumber`(IN tabnum INT, IN seatnum INT)
BEGIN
	SELECT order_no FROM `order` WHERE fk_table_no=tabnum AND seat_no=seatnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getServer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getServer`(IN tablenum INT)
BEGIN
	SELECT server_no FROM `table` WHERE table_no=tablenum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getSideInfo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getSideInfo`(IN optionnum INT)
BEGIN
	SELECT option_name,option_price FROM `option`
    WHERE option_no=optionnum AND optionType='side';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getSpecials` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getSpecials`(IN num INT)
BEGIN
	SELECT * FROM special where fk_item_no=num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getTable`(IN tabnum INT)
BEGIN
	SELECT * FROM `table` WHERE table_no=tabnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getUserType` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserType`(empno INT)
BEGIN
	SELECT position
    FROM employee
    WHERE employee_no=empno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `isActive` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `isActive`(IN empno INT)
BEGIN
	SELECT isActive from Employee where employee_no=empno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `isClockedIn` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `isClockedIn`(empno INT)
BEGIN
	SELECT MAX(clocked_in)
    FROM shift 
    WHERE employee_no=empno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `isValidEmp` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `isValidEmp`(empno INT)
BEGIN
	SELECT COUNT(*) FROM employee
		WHERE employee_no = empno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `logItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `logItemOnOrder`(IN ordernum INT(11))
BEGIN
	DECLARE p1 INT;
    DECLARE onum INT;
	DECLARE inum INT;
    DECLARE s1 INT;
    DECLARE s2 INT;
    DECLARE s3 INT;
    DECLARE e1 INT;
    DECLARE e2 INT;
    DECLARE e3 INT;
	DECLARE icomment VARCHAR(300);
    DECLARE itotal DOUBLE;

	DECLARE exit_loop BOOLEAN;         
   
    DECLARE io_cursor CURSOR FOR
     SELECT * FROM item_on_order WHERE fk_order=ordernum;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET exit_loop = TRUE;
	
    OPEN io_cursor;
	
    io_loop: LOOP
	
    FETCH  io_cursor INTO p1,onum,inum,s1,s2,s3,e1,e2,e3,icomment,itotal;
	
    IF exit_loop THEN
        CLOSE io_cursor;
        LEAVE io_loop;
    END IF;
    
    INSERT INTO item_on_order_log VALUES(p1,onum,inum,s1,s2,s3,e1,e2,e3,icomment,itotal,SYSDATE());
    DELETE FROM item_on_order WHERE fk_order=onum;
    
    
    END LOOP io_loop;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `logOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `logOrder`(IN ordernum INT(11), IN paytype varchar(45))
BEGIN
	DECLARE v1 INT;
    DECLARE v2 INT;
    DECLARE v3 INT;
    DECLARE v4 INT;

	SELECT order_no INTO v1 FROM `order` WHERE order_no=ordernum;
    SELECT fk_table_no INTO v2 FROM `order` WHERE order_no=ordernum;
    SELECT seat_no INTO v3 FROM `order` WHERE order_no=ordernum;
    SELECT order_total INTO v4 FROM `order` WHERE order_no=ordernum;
	
    INSERT INTO order_log VALUES (v1,v2,v3,v4,paytype,SYSDATE());
    DELETE FROM `order` WHERE order_no=ordernum;
    
	
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `maxItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `maxItemOnOrder`()
BEGIN
	SELECT max(pk_item_order) FROM item_on_order;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifyEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifyEmployee`(IN empNo INT(11),
		IN fName varchar(45),IN lName varchar(45),IN adrs varchar(50),
		IN phone VARCHAR(12),IN sin INT(11),
        IN position VARCHAR(9),
        IN isActive INT(11),IN hireDate DATE,IN endDate DATE)
BEGIN
	UPDATE employee 
    SET first_name=fName, last_name=lName, 
    phone_number=phone, SIN=sin, address=adrs, position=position,
    isActive=isActive, hire_date=hireDate, end_date=endDate
    WHERE employee_no=empNo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifyItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifyItem`(IN num INT, IN itemname VARCHAR(45), 
	IN itemdesc VARCHAR(200), IN base DOUBLE, IN cost DOUBLE,
    IN itemtype ENUM('main','burger','appetizer','soup',
    'salad','brunch','extra','dessert','side','others','drink','speed bar'),
    IN numsides INT(11), IN numoptions INT(11), IN isactive INT(1))
BEGIN
	UPDATE item SET item_name=itemname,
    item_description=itemdesc,base_price=base,cost=cost,
    itemType=itemtype,numberOfSides=numsides,numberOfExtras=numoptions,isItemActive=isactive   
    WHERE item_no=num;
    
    UPDATE special SET original_price=base WHERE fk_item_no=num;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifyItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifyItemOnOrder`(IN pkitem INT,IN sid1 INT,IN sid2 INT,
								IN sid3 INT,IN ext1 INT,IN ext2 INT,IN ext3 INT,IN itemcomment VARCHAR(300),IN itotal DOUBLE)
BEGIN
	UPDATE item_on_order SET
    side1=sid1,
    side2=sid2,
    side3=sid3,
    extra1=ext1,
    extra2=ext2,
    extra3=ext3,
    item_comment=itemcomment,
    item_total=itotal WHERE pk_item_order=pkitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifyOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifyOption`(IN optnum INT(11), IN optionname VARCHAR(100), IN optiondesc VARCHAR(200),
	IN optprice DOUBLE, IN optcost DOUBLE, IN opttype ENUM('extra','side'), IN optstatus INT(11))
BEGIN
	UPDATE `posdb`.`option` 
    SET option_no=optnum,
    option_name=optionname,
    option_description=optiondesc,
    option_price=optprice,
    option_cost=optcost,
    optionType=opttype,
    isOptionActive=opttype
    WHERE option_no=optnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifyOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifyOrder`(IN ordernum INT,IN price DOUBLE)
BEGIN
	UPDATE `order` SET order_total=price
    WHERE order_no=ordernum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifyPassword` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifyPassword`(IN empno INT, pass VARCHAR(20))
BEGIN
	UPDATE admin SET admin_password=pass where fk_employee_no=empno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifySpecial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifySpecial`(IN specno INT,IN specname VARCHAR(100),IN starttime TIME,
				IN endtime TIME)
BEGIN
	UPDATE special SET special_name=specname, start_time=starttime, end_time=end_time WHERE
    special_no=specno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifySpecialDay` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifySpecialDay`(IN specno INT, IN dow INT)
BEGIN
    UPDATE special_day 
    SET day_of_week=dow
    WHERE fk_special=specno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifySpecialItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifySpecialItem`(IN specnum INT, IN inum INT, IN disc DOUBLE)
BEGIN
	UPDATE special_item SET
    item_on_special=inum,
    discounted=disc
    WHERE fk_special_item=specnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `modifyTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `modifyTable`(IN tabnum INT, IN isactive INT, IN servernum INT)
BEGIN
	UPDATE `table` SET table_active=isactive, server_no=servernum WHERE table_no=tabnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `moveItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `moveItem`(IN pkitemorder INT, IN ordernum INT)
BEGIN
	UPDATE item_on_order SET fk_order=ordernum WHERE pk_item_order=pkitemorder;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `moveSeat` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `moveSeat`(IN tablenum INT,IN curval INT, IN newval INT)
BEGIN
	UPDATE `order` SET seat_no=newval WHERE fk_table_no=tablenum AND seat_no=curval;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `moveTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `moveTable`(IN curval INT, IN newval INT)
BEGIN
	UPDATE `order` SET fk_table_no=newval WHERE fk_table_no=curval;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `nextItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `nextItemOnOrder`()
BEGIN
	SELECT max(pk_item_order)+1 FROM item_on_order;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `pay` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pay`(IN ordernum INT(11), IN paytype varchar(45))
BEGIN
	CALL logItemOnOrder(ordernum);
    CALL logOrder(ordernum,paytype);
    
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeEmployee`(IN empno INT)
BEGIN
	DELETE FROM employee where
    employee_no=empno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeItem`(IN itemno INT(11))
BEGIN
	CALL removeOption(itemno);

	DELETE FROM item WHERE item_no=itemno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeItemOnOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeItemOnOrder`(IN pkitemorder INT)
BEGIN
	DELETE FROM item_on_order WHERE pk_item_order=pkitemorder;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeItemOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeItemOption`(IN compositenum INT)
BEGIN
	DELETE FROM item_option WHERE pk_item_option=compositenum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeOption` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeOption`(IN itemnum INT(11))
BEGIN
	DECLARE inum  INT;
    DECLARE onum  INT;

	DECLARE exit_loop BOOLEAN;         
   
    DECLARE io_cursor CURSOR FOR
     SELECT fk_option_no FROM item_option WHERE fk_item_no=itemnum;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET exit_loop = TRUE;

    OPEN io_cursor;

    io_loop: LOOP
	
    FETCH  io_cursor INTO onum;

    DELETE FROM `posdb`.`option` WHERE option_no=onum;
    DELETE FROM item_option WHERE fk_item_no=itemnum;
     
    IF exit_loop THEN
        CLOSE io_cursor;
        LEAVE io_loop;
    END IF;
    END LOOP io_loop;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeOrder`(IN ordernum INT)
BEGIN
	DELETE FROM `order` WHERE order_no=ordernum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeSpecial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeSpecial`(IN specno INT)
BEGIN
	DELETE FROM special_item WHERE fk_special_item=specno;
    DELETE FROM special_day WHERE fk_special=specno;
    DELETE FROM special WHERE special_no=specno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeSpecialDay` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeSpecialDay`(IN specno INT)
BEGIN
	DELETE FROM special_day WHERE fk_special=specno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeSpecialItem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeSpecialItem`(IN specno INT)
BEGIN
	DELETE FROM special_item WHERE fk_special_item=specno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `removeTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeTable`(IN tabnum INT)
BEGIN
	DELETE FROM `table` WHERE table_no=tabnum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reportAllEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reportAllEmployee`(IN fromdate DATETIME, IN todate DATETIME)
BEGIN    
	SELECT employee_no,CAST(SUM(TIMEDIFF(end_time,start_time)) as time) FROM shift where
        clocked_in='N' AND start_time>=fromdate and end_time<=todate GROUP BY employee_no;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reportItemCount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reportItemCount`(IN fromdate DATETIME, IN todate DATETIME)
BEGIN
	SELECT item_no_log, COUNT(*),item_date FROM item_on_order_log  
    WHERE item_date BETWEEN fromdate and todate GROUP BY item_no_log; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reportOneEmployee` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reportOneEmployee`(IN empnum INT,IN fromdate DATETIME, IN todate DATETIME)
BEGIN    
	SELECT employee_no,CAST(CAST(SUM(TIMEDIFF(end_time,start_time)) as time) as CHAR) FROM shift where
        clocked_in='N' AND employee_no=empnum AND start_time>=fromdate and end_time<=todate GROUP BY employee_no;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reportOrderByDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reportOrderByDate`(IN fromdate DATETIME, IN todate DATETIME)
BEGIN
	SELECT order_no_log,order_total_log,payment_type,order_date FROM order_log
    WHERE order_date BETWEEN fromdate and todate; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reportOrderByPayment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reportOrderByPayment`(IN paytype VARCHAR(45),IN fromdate DATETIME, IN todate DATETIME)
BEGIN
	SELECT order_no_log,order_total_log,order_date FROM order_log
    WHERE payment_type=paytype AND order_date BETWEEN fromdate and todate; 
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `reportOrderItems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `reportOrderItems`(IN ordernum INT)
BEGIN    
    SELECT item_name,base_price,item_total_log FROM item_on_order_log 
    JOIN item ON item_on_order_log.item_no_log=item.item_no
    WHERE order_no_log=ordernum;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `startShift` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `startShift`(empno INT)
BEGIN
	IF(select MAX(shift_no) from shift where employee_no=empno AND end_time IS NULL) IS NOT NULL THEN
		INSERT INTO `posdb`.`shift` (start_time,clocked_in,employee_no)
        VALUES(SYSDATE(),'Y',empno);
	ELSEIF(select COUNT(shift_no) from shift where employee_no=empno) > 0 THEN
		INSERT INTO `posdb`.`shift` (start_time,clocked_in,employee_no)
        VALUES(SYSDATE(),'Y',empno);
	ELSEIF(select shift_no from shift where employee_no=empno) IS NULL THEN
		INSERT INTO `posdb`.`shift` (start_time,clocked_in,employee_no)
        VALUES(SYSDATE(),'Y',empno);
	ELSEIF(select * from shift where employee_no=empno) IS NULL THEN
		INSERT INTO `posdb`.`shift` (start_time,clocked_in,employee_no)
        VALUES(SYSDATE(),'Y',empno);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateSpecial` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSpecial`()
BEGIN
    
    
    DECLARE inum  INT;
    DECLARE sno INT;
    DECLARE dow   	 INT;
    DECLARE stime    TIME;
    DECLARE etime    TIME;
    DECLARE bprice   DOUBLE;
    DECLARE org DOUBLE;
    
    DECLARE exit_loop BOOLEAN;         
       
    DECLARE special_cursor CURSOR FOR
		SELECT special_no,start_time,end_time,day_of_week,item_on_special,discounted,original 
        FROM special JOIN special_day ON special.special_no=special_day.fk_special
		JOIN special_item ON special.special_no=special_item.fk_special_item;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET exit_loop = TRUE;

    OPEN special_cursor;

    special_loop: LOOP
	
	FETCH special_cursor INTO sno,stime,etime,dow,inum,bprice,org;
    
    IF exit_loop THEN
        CLOSE special_cursor;
        LEAVE special_loop;
    END IF;
    
    IF(weekday(curdate())=dow) THEN
		IF(stime<=curtime() AND etime>=curtime()) THEN
			UPDATE item SET base_price=bprice WHERE item_no=inum;
		ELSE
			UPDATE item SET base_price=org WHERE item_no=inum;
		END IF;
    END IF;
     
    END LOOP special_loop;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `validateAdmin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `validateAdmin`(IN empno INT, IN pass VARCHAR(20))
BEGIN
	SELECT * FROM admin WHERE fk_employee_no=empno AND admin_password=pass;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-13 13:43:06
