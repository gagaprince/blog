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

/*Table structure for table `touch_novel` */

DROP TABLE IF EXISTS `touch_novel`;

CREATE TABLE `touch_novel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '书名',
  `author` varchar(50) DEFAULT NULL COMMENT '作者名',
  `cate` varchar(20) DEFAULT NULL COMMENT '分类',
  `source` varchar(50) DEFAULT NULL COMMENT '源',
  `source_url` varchar(100) DEFAULT NULL COMMENT '源主页',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `descripe` varchar(1000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sourceUrl` (`source_url`)
) ENGINE=InnoDB AUTO_INCREMENT=6559 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `touch_novel_chapter` */

DROP TABLE IF EXISTS `touch_novel_chapter`;

CREATE TABLE `touch_novel_chapter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `novel_id` bigint(20) DEFAULT NULL COMMENT '对应的小说id',
  `chapter` bigint(20) DEFAULT NULL COMMENT '第几章节',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `source_url` varchar(100) DEFAULT NULL COMMENT '对应章节的地址',
  `name` varchar(100) DEFAULT NULL COMMENT '章节名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `novelc` (`novel_id`,`chapter`)
) ENGINE=InnoDB AUTO_INCREMENT=4510700 DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
