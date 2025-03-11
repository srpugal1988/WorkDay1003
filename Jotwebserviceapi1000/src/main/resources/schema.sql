/*
SQLyog Trial v13.1.9 (64 bit)
MySQL - 8.0.31 : Database - jcms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jcms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `jcms`;

/*Table structure for table `sm_logindetails` */

DROP TABLE IF EXISTS `sm_logindetails`;

CREATE TABLE `sm_logindetails` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `logintime` timestamp NULL DEFAULT NULL,
  `logouttime` timestamp NULL DEFAULT NULL,
  `dateadded` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

/*Table structure for table `sm_userdetails` */

DROP TABLE IF EXISTS `sm_userdetails`;

CREATE TABLE `sm_userdetails` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userrefno` varchar(100) DEFAULT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `contactnumber` varchar(100) DEFAULT NULL,
  `address1` varchar(100) DEFAULT NULL,
  `address2` varchar(100) DEFAULT NULL,
  `address3` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `zip` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `roleid` int DEFAULT NULL,
  `rolename` varchar(100) DEFAULT NULL,
  `jobtitle` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `online` int DEFAULT '1' COMMENT '0-->Offline;1-->Online',
  `dateadded` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdby` varchar(100) DEFAULT NULL,
  `isactiveuser` int DEFAULT '1' COMMENT '0-->Disabled;1-->ActiveUser',
  `dbname` varchar(100) DEFAULT NULL,
  `clientname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
