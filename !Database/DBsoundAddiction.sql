CREATE DATABASE  IF NOT EXISTS `sound_addiction` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sound_addiction`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sound_addiction
-- ------------------------------------------------------
-- Server version	8.0.11

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
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genres` (
  `genre_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary key - GENRE ID',
  `value` varchar(45) NOT NULL COMMENT 'Genre''s name',
  PRIMARY KEY (`genre_id`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (4,'Blues'),(28,'Breakbeat'),(29,'Classic rock'),(6,'Classical music'),(8,'Country'),(23,'Dance'),(16,'Disco'),(24,'Drum and bass'),(21,'Dubstep'),(17,'Electronic'),(27,'Filk rock'),(5,'Folk music'),(13,'Funk'),(22,'Heavy metal'),(7,'Hip hop'),(15,'House'),(18,'Instrumental'),(3,'Jazz'),(11,'Opera'),(20,'Orchestra'),(1,'Pop music'),(10,'Punk rock'),(9,'Reggae'),(2,'Rock'),(25,'Rock and roll'),(12,'Soul music'),(26,'Soundtrack'),(14,'Techno'),(19,'Trance');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song_has_comments`
--

DROP TABLE IF EXISTS `song_has_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `song_has_comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `song_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `fk_song_has_comments_users` (`user_id`),
  KEY `fk_song_has_comments_songs` (`song_id`),
  CONSTRAINT `fk_song_has_comments_songs` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_song_has_comments_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song_has_comments`
--

LOCK TABLES `song_has_comments` WRITE;
/*!40000 ALTER TABLE `song_has_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `song_has_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song_has_raters`
--

DROP TABLE IF EXISTS `song_has_raters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `song_has_raters` (
  `song_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `rating` decimal(3,1) NOT NULL,
  PRIMARY KEY (`song_id`,`user_id`),
  KEY `fk_song_has_raters_users` (`user_id`),
  CONSTRAINT `fk_song_has_raters_songs` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_song_has_raters_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song_has_raters`
--

LOCK TABLES `song_has_raters` WRITE;
/*!40000 ALTER TABLE `song_has_raters` DISABLE KEYS */;
/*!40000 ALTER TABLE `song_has_raters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs`
--

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `songs` (
  `song_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary key for the songs',
  `name` varchar(150) NOT NULL COMMENT 'Song name',
  `singer` varchar(150) NOT NULL,
  `album` varchar(150) DEFAULT NULL,
  `published_date` date NOT NULL,
  `rating` decimal(10,0) DEFAULT '0',
  `genre_id` int(11) NOT NULL,
  `resource_path` varchar(100) NOT NULL DEFAULT 'url_path',
  `price` double NOT NULL DEFAULT '0',
  `image_path` varchar(500) DEFAULT 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/450px-No_image_available.svg.png',
  PRIMARY KEY (`song_id`),
  UNIQUE KEY `singer` (`singer`,`name`),
  KEY `fk_songs_genres` (`genre_id`),
  CONSTRAINT `fk_songs_genres` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`genre_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs`
--

LOCK TABLES `songs` WRITE;
/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
INSERT INTO `songs` VALUES (1,'The Unforgiven','Metallica','Metallica','1991-09-27',0,22,'uri_path',5.5,'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/450px-No_image_available.svg.png'),(2,'Send Me An Angel','Scorpions','Crazy World','1990-05-20',0,29,'uri_path',5.5,'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/450px-No_image_available.svg.png'),(3,'My Immortal','Evanescence','Origin','2000-02-17',0,2,'uri_path',5.5,'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/450px-No_image_available.svg.png'),(4,'Shape of you','Ed Sheeran',NULL,'2017-04-07',0,1,'uri_path',5.5,'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/450px-No_image_available.svg.png'),(5,'Vse na men','Dara',NULL,'2018-06-20',0,1,'uri_path',5.5,'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/450px-No_image_available.svg.png'),(6,'Havana','Pavell & Venci Venc','Summer hits','2018-04-11',0,7,'uri_path',5.5,'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/450px-No_image_available.svg.png');
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_songs`
--

DROP TABLE IF EXISTS `user_has_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_songs` (
  `song_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`song_id`,`user_id`),
  KEY `fk_user_has_songs_users` (`user_id`),
  CONSTRAINT `fk_user_has_songs_songs` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_songs_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_songs`
--

LOCK TABLES `user_has_songs` WRITE;
/*!40000 ALTER TABLE `user_has_songs` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary key - arbitraty user ID',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL COMMENT 'User''s password',
  `first_name` varchar(45) NOT NULL COMMENT 'User''s first name',
  `last_name` varchar(45) NOT NULL,
  `money` decimal(7,2) NOT NULL DEFAULT '100.00' COMMENT 'User''s money',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (13,0,'mario0.bg@abv.bg','$2a$10$r/pNUNs2P47p0oIOz2AywOtofqCxvTIjRgujXvfmmHPtdUhXfNMOm','Mario','Dimitrov',100.00),(14,0,'peconipetko@gmail.com','$2a$10$ny5xb7VF5OvMk2uoloRaDeizTyiQubZ.kutpjKnyEi2YIf960.e7q','Petko','Dechev',100.00),(15,1,'soundadmin@soundadd.com','$2a$10$dmaIYci6q0CLAWCm89i3EO/4ByECLn4lhkWz/5vyydBb0nBImOAVy','Admin','Adminov',500.00),(16,0,'test@soundadd.com','$2a$10$LRdd627FZCFrAkoAeCvvxO2Z.6jmrzCIzBRxfBbNWRt6WGszaU5ve','TestFName','TestLName',100.00);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-10 14:49:40

#Creating trigger after adding rating of a song to update its own rating
DELIMITER $$
CREATE TRIGGER after_rating_added 
    AFTER UPDATE ON song_has_raters
    FOR EACH ROW 
BEGIN
    UPDATE songs SET rating  = (SELECT AVG(rating) FROM song_has_raters WHERE song_id = NEW.song_id) 
	WHERE song_id = NEW.song_id; 
END$$
DELIMITER ;
