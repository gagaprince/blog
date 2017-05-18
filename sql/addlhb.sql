/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.24 : Database - blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
USE `blog`;

/*Table structure for table `division` */

CREATE TABLE `division` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '营业部名称',
  `code` int(11) DEFAULT NULL COMMENT '营业部编码',
  `province` varchar(50) DEFAULT NULL COMMENT '所在省份',
  `company` varchar(50) DEFAULT NULL COMMENT '所属公司',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6495 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `dragon_tiger` */

CREATE TABLE `dragon_tiger` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `share_code` varchar(10) DEFAULT NULL COMMENT '代码',
  `buy1_division` int(11) DEFAULT NULL COMMENT 'buy1席位',
  `buy1_val` float DEFAULT NULL COMMENT 'buy1值',
  `buy2_division` int(11) DEFAULT NULL COMMENT 'buy2席位',
  `buy2_val` float DEFAULT NULL COMMENT 'buy2值',
  `buy3_division` int(11) DEFAULT NULL,
  `buy3_val` float DEFAULT NULL,
  `buy4_division` int(11) DEFAULT NULL,
  `buy4_val` float DEFAULT NULL,
  `buy5_division` int(11) DEFAULT NULL,
  `buy5_val` float DEFAULT NULL,
  `sell1_division` int(11) DEFAULT NULL,
  `sell1_val` float DEFAULT NULL,
  `sell2_division` int(11) DEFAULT NULL,
  `sell2_val` float DEFAULT NULL,
  `sell3_division` int(11) DEFAULT NULL,
  `sell3_val` float DEFAULT NULL,
  `sell4_division` int(11) DEFAULT NULL,
  `sell4_val` float DEFAULT NULL,
  `sell5_division` int(11) DEFAULT NULL,
  `sell5_val` float DEFAULT NULL,
  `current_date` date DEFAULT NULL COMMENT '日期',
  `reason` varchar(100) DEFAULT NULL COMMENT '入榜原因',
  `jd` varchar(300) DEFAULT NULL COMMENT '解读',
  `share_name` varchar(20) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_date` (`share_code`,`current_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
