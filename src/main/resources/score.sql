-- MySQL dump 10.13  Distrib 8.0.32, for macos13.0 (arm64)
--
-- Host: 127.0.0.1    Database: score
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `permission_id` bigint NOT NULL AUTO_INCREMENT,
  `descritpion` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `pid` int DEFAULT NULL,
  `seq` int DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'任务池','任务池','/',1,1,'/root'),(2,'我的任务','我的任务','/mytasks/',1,2,'/mytasks/'),(3,'任务审核','任务审核','/check/',1,3,'/check/'),(4,'数据分析','数据分析','/analyze/',1,4,'/analyze/'),(5,'任务测试','任务测试','/test/',1,5,'/test/'),(6,'项目维护','项目维护','/project/',1,6,'/project/'),(7,'任务分配','任务分配','/assigning/',1,7,'/assigning/');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;

--
-- Table structure for table `permission_role`
--

DROP TABLE IF EXISTS `permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission_role` (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `FK50sfdcvbvdaclpn7wp4uop4ml` (`role_id`),
  CONSTRAINT `FK3tuvkbyi6wcytyg21hvpd6txw` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`),
  CONSTRAINT `FK50sfdcvbvdaclpn7wp4uop4ml` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_role`
--

/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */;
INSERT INTO `permission_role` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(2,1),(2,2),(2,4),(3,1),(3,2),(3,4),(3,5);
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `project_id` bigint NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FK9ydhxbq67a3m0ek560r2fq38g` (`owner_id`),
  CONSTRAINT `FK9ydhxbq67a3m0ek560r2fq38g` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'漠河智慧城市',3),(2,'呼玛智慧城市',4),(3,'数字城管',2),(4,'智慧社区',1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `c_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'用户','ROLE_USER'),(2,'组长','ROLE_OWNER'),(3,'管理员','ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `score_id` bigint NOT NULL AUTO_INCREMENT,
  `c_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `rule` varchar(255) NOT NULL,
  `socre` decimal(19,2) NOT NULL,
  `task_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`score_id`),
  KEY `FKmxvrryhwud4n5isfwkqjtgl01` (`task_id`),
  KEY `FKq0fd09h1462r0pfum9249o3o7` (`user_id`),
  CONSTRAINT `FKmxvrryhwud4n5isfwkqjtgl01` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FKq0fd09h1462r0pfum9249o3o7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

/*!40000 ALTER TABLE `score` DISABLE KEYS */;
/*!40000 ALTER TABLE `score` ENABLE KEYS */;

--
-- Table structure for table `server`
--

DROP TABLE IF EXISTS `server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `server` (
  `server_id` bigint NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `port` int NOT NULL,
  `sys` int DEFAULT NULL,
  `used` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `project_id` bigint DEFAULT NULL,
  PRIMARY KEY (`server_id`),
  KEY `FKc2q2chwi3c5gqpckpsts7nppi` (`project_id`),
  CONSTRAINT `FKc2q2chwi3c5gqpckpsts7nppi` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `server`
--

/*!40000 ALTER TABLE `server` DISABLE KEYS */;
/*!40000 ALTER TABLE `server` ENABLE KEYS */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` bigint NOT NULL AUTO_INCREMENT,
  `b_date` datetime DEFAULT NULL,
  `c_date` datetime DEFAULT NULL,
  `e_date` datetime DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `task_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `project_id` bigint DEFAULT NULL,
  `finish` int NOT NULL DEFAULT '0',
  `f_date` datetime DEFAULT NULL,
  `check_date` datetime DEFAULT NULL,
  `type` int NOT NULL DEFAULT '2' COMMENT '软件编码',
  PRIMARY KEY (`task_id`),
  KEY `FKk8qrwowg31kx7hp93sru1pdqa` (`project_id`),
  KEY `FKtng16j7iinb49dqvfbfe0wccm` (`order_id`),
  CONSTRAINT `FKk8qrwowg31kx7hp93sru1pdqa` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `FKtng16j7iinb49dqvfbfe0wccm` FOREIGN KEY (`order_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;

--
-- Table structure for table `task_user`
--

DROP TABLE IF EXISTS `task_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_user` (
  `task_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `id` bigint NOT NULL,
  `role_type` int NOT NULL DEFAULT '0' COMMENT '任务处理者',
  KEY `FKs46ejm4kitq56yd498a3fnr19` (`user_id`),
  KEY `FKd1fn28rqhh1ku21jx7hcksvh9` (`task_id`),
  CONSTRAINT `FKd1fn28rqhh1ku21jx7hcksvh9` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FKs46ejm4kitq56yd498a3fnr19` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_user`
--

/*!40000 ALTER TABLE `task_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_user` ENABLE KEYS */;

--
-- Table structure for table `test_report`
--

DROP TABLE IF EXISTS `test_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_report` (
  `test_report_id` bigint NOT NULL AUTO_INCREMENT,
  `c_date` datetime DEFAULT NULL,
  `report` varchar(255) DEFAULT NULL,
  `type` int NOT NULL DEFAULT '0' COMMENT '通过',
  `task_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`test_report_id`),
  KEY `FKntu9aqxmfct0q9xy9e7p4wsvx` (`task_id`),
  KEY `FK2yieig1ojx9ojgds4cr31dp4j` (`user_id`),
  CONSTRAINT `FK2yieig1ojx9ojgds4cr31dp4j` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKntu9aqxmfct0q9xy9e7p4wsvx` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_report`
--

/*!40000 ALTER TABLE `test_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_report` ENABLE KEYS */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `sts` int DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `cname` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `task_count` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'528726',1,'liuyf','刘宇飞',0),(2,'123456',1,'wangyt',NULL,0),(3,'123456',1,'zhaocz',NULL,0),(4,'123456',1,'wangs',NULL,0),(5,'123456',1,'jiangw',NULL,0),(6,'123456',1,'hanh',NULL,0),(7,'123456',1,'guoxm',NULL,0),(8,'123456',1,'wangc',NULL,0),(9,'123456',1,'liuk',NULL,0),(10,'123456',1,'guzj',NULL,0),(11,'123456',1,'guott',NULL,0),(12,'123456',1,'lix',NULL,0),(13,'123456',1,'lib',NULL,0),(14,'123456',1,'zhangzp',NULL,0),(15,'123456',1,'yangyx',NULL,0),(16,'123456',1,'mengx',NULL,0),(17,'123456',1,'lius',NULL,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,1),(1,2),(2,2),(3,1),(3,2),(4,1),(4,2),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(15,2),(16,1),(16,2),(17,1),(17,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

--
-- Dumping routines for database 'score'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-21 15:22:11
