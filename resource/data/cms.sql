-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.26-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 cms 的数据库结构
CREATE DATABASE IF NOT EXISTS `cms` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `cms`;

-- 导出  表 cms.activity 结构
CREATE TABLE IF NOT EXISTS `activity` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `start_time` datetime(3) NOT NULL,
  `end_time` datetime(3) NOT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `remark` varchar(60) DEFAULT NULL,
  `online` tinyint(3) unsigned DEFAULT '1',
  `entrance_img` varchar(255) DEFAULT NULL,
  `internal_top_img` varchar(255) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.activity 的数据：~0 rows (大约)
DELETE FROM `activity`;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` (`id`, `title`, `description`, `start_time`, `end_time`, `create_time`, `update_time`, `delete_time`, `remark`, `online`, `entrance_img`, `internal_top_img`, `name`) VALUES
	(2, '夏日好礼送不停', '长夏村墟风日清', '2019-08-03 18:04:52.000', '2030-08-31 18:05:16.000', '2019-08-03 17:59:01.000', '2021-08-27 11:47:22.215', NULL, '限服装、鞋、文具等商品', 1, 'http://127.0.0.1:8080/cms-pictures/activity/activity.jpg', NULL, 'a-2');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;

-- 导出  表 cms.activity_category 结构
CREATE TABLE IF NOT EXISTS `activity_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int(10) unsigned NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.activity_category 的数据：~11 rows (大约)
DELETE FROM `activity_category`;
/*!40000 ALTER TABLE `activity_category` DISABLE KEYS */;
INSERT INTO `activity_category` (`id`, `category_id`, `activity_id`) VALUES
	(1, 2, 2),
	(2, 7, 2),
	(6, 4, 2),
	(7, 27, 2),
	(8, 32, 2),
	(9, 27, 1),
	(11, 1, 3),
	(12, 2, 3),
	(13, 1, 4),
	(14, 2, 4),
	(15, 3, 4);
/*!40000 ALTER TABLE `activity_category` ENABLE KEYS */;

-- 导出  表 cms.activity_coupon 结构
CREATE TABLE IF NOT EXISTS `activity_coupon` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `coupon_id` int(10) unsigned NOT NULL,
  `activity_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.activity_coupon 的数据：~9 rows (大约)
DELETE FROM `activity_coupon`;
/*!40000 ALTER TABLE `activity_coupon` DISABLE KEYS */;
INSERT INTO `activity_coupon` (`id`, `coupon_id`, `activity_id`) VALUES
	(1, 3, 2),
	(2, 4, 2),
	(3, 5, 2),
	(4, 7, 1),
	(6, 3, 3),
	(7, 4, 3),
	(8, 3, 4),
	(9, 4, 4),
	(10, 5, 4);
/*!40000 ALTER TABLE `activity_coupon` ENABLE KEYS */;

-- 导出  表 cms.activity_spu 结构
CREATE TABLE IF NOT EXISTS `activity_spu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `activity_id` int(10) unsigned NOT NULL,
  `spu_id` int(10) unsigned NOT NULL,
  `participation` tinyint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.activity_spu 的数据：~0 rows (大约)
DELETE FROM `activity_spu`;
/*!40000 ALTER TABLE `activity_spu` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_spu` ENABLE KEYS */;

-- 导出  表 cms.banner 结构
CREATE TABLE IF NOT EXISTS `banner` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL COMMENT '部分banner可能有标题图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.banner 的数据：~2 rows (大约)
DELETE FROM `banner`;
/*!40000 ALTER TABLE `banner` DISABLE KEYS */;
INSERT INTO `banner` (`id`, `name`, `description`, `create_time`, `update_time`, `delete_time`, `title`, `img`) VALUES
	(1, 'b-1', '首页顶部主banner', '2019-07-28 04:47:15.000', '2019-08-04 01:03:16.000', NULL, NULL, NULL),
	(2, 'b-2', '热销商品banner', '2019-08-01 00:37:47.000', '2021-08-27 11:48:02.964', NULL, NULL, 'http://127.0.0.1:8080/cms-pictures/theme/theme4.jpg');
/*!40000 ALTER TABLE `banner` ENABLE KEYS */;

-- 导出  表 cms.banner_item 结构
CREATE TABLE IF NOT EXISTS `banner_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `img` varchar(255) DEFAULT NULL,
  `keyword` varchar(50) DEFAULT NULL,
  `type` smallint(5) unsigned NOT NULL DEFAULT '0',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `banner_id` int(10) unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.banner_item 的数据：~6 rows (大约)
DELETE FROM `banner_item`;
/*!40000 ALTER TABLE `banner_item` DISABLE KEYS */;
INSERT INTO `banner_item` (`id`, `img`, `keyword`, `type`, `create_time`, `update_time`, `delete_time`, `banner_id`, `name`) VALUES
	(5, 'http://127.0.0.1:8080/cms-pictures/banner/banner5.jpg', '28', 1, '2019-08-01 00:41:41.000', '2021-08-27 11:48:14.648', NULL, 2, 'left'),
	(6, 'http://127.0.0.1:8080/cms-pictures/banner/banner6.jpg', '26', 1, '2019-08-01 00:41:41.000', '2021-08-27 11:48:17.337', NULL, 2, 'right-top'),
	(7, 'http://127.0.0.1:8080/cms-pictures/banner/banner7.jpg', '27', 1, '2019-08-01 00:41:41.000', '2021-08-27 11:48:19.801', NULL, 2, 'right-bottom'),
	(12, 'http://127.0.0.1:8080/cms-pictures/banner/banner1.jpg', 't-2', 3, '2019-09-15 17:29:52.000', '2021-08-27 11:48:21.967', NULL, 1, NULL),
	(13, 'http://127.0.0.1:8080/cms-pictures/banner/banner2.jpg', '23', 1, '2019-07-28 04:39:22.000', '2021-08-27 11:48:24.040', NULL, 1, NULL),
	(14, 'http://127.0.0.1:8080/cms-pictures/banner/banner3.jpg', '24', 1, '2019-07-28 04:40:10.000', '2021-08-27 11:48:39.066', NULL, 1, NULL);
/*!40000 ALTER TABLE `banner_item` ENABLE KEYS */;

-- 导出  表 cms.brand 结构
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.brand 的数据：~17 rows (大约)
DELETE FROM `brand`;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` (`id`, `name`, `description`, `create_time`, `update_time`, `delete_time`) VALUES
	(1, 'Ikea', NULL, '2019-07-15 12:42:43', '2019-07-15 12:54:11', NULL),
	(2, 'Theory', NULL, '2019-07-15 12:46:00', '2019-07-15 12:46:00', NULL),
	(3, 'Converse', NULL, '2019-07-15 12:47:00', '2019-07-15 12:47:00', NULL),
	(4, 'Hickies', NULL, '2019-07-15 12:48:13', '2019-07-15 12:48:13', NULL),
	(5, 'Bucketfeet', NULL, '2019-07-15 12:48:55', '2019-07-15 12:48:55', NULL),
	(6, 'ROUJE\nROUJE\n', NULL, '2019-07-15 12:53:21', '2019-07-15 12:53:21', NULL),
	(7, 'Claudie Pierlot', NULL, '2019-07-15 12:53:40', '2019-07-15 12:53:40', NULL),
	(8, 'Lemaire&UNIQLIO', NULL, '2019-07-15 12:53:51', '2019-07-15 12:54:02', NULL),
	(9, 'The Kooples', NULL, '2019-07-15 12:54:39', '2019-07-15 12:54:39', NULL),
	(10, 'Marc Jacobs', NULL, '2019-07-15 12:55:08', '2019-07-15 12:55:08', NULL),
	(11, 'Michael Kors', NULL, '2019-07-15 12:55:17', '2019-07-15 12:55:17', NULL),
	(12, 'Tory Burch', NULL, '2019-07-15 12:55:25', '2019-07-15 12:55:25', NULL),
	(13, 'Kate Spade', NULL, '2019-07-15 12:55:43', '2019-07-15 12:55:43', NULL),
	(14, 'Apple', NULL, '2019-07-17 07:59:59', '2019-07-17 07:59:59', NULL),
	(15, 'Staedtler', NULL, '2019-07-17 08:00:30', '2019-07-17 08:00:30', NULL),
	(16, '林间有风', NULL, '2019-07-17 08:19:36', '2019-07-17 08:19:36', NULL),
	(17, '九州', '请打', '2019-08-26 22:08:20', '2019-08-26 22:08:20', NULL);
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;

-- 导出  表 cms.category 结构
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `is_root` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `parent_id` int(10) unsigned DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `sort` int(10) unsigned DEFAULT NULL,
  `online` int(10) unsigned DEFAULT '1',
  `level` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.category 的数据：~0 rows (大约)
DELETE FROM `category`;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `name`, `description`, `create_time`, `update_time`, `delete_time`, `is_root`, `parent_id`, `img`, `sort`, `online`, `level`) VALUES
	(1, '鞋', NULL, '2019-07-15 08:51:19.000', '2021-09-04 16:19:04.021', NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/category/xieimg.jpg', 3, 1, NULL),
	(2, '服装', NULL, '2019-07-15 08:51:28.000', '2021-09-04 16:26:13.384', NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/category/yifuimg.jpg', 2, 1, NULL),
	(3, '包包', NULL, '2019-07-15 08:51:35.000', '2021-09-04 16:21:36.808', NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/category/baoimg.jpg', 1, 1, NULL),
	(4, '居家', NULL, '2019-07-15 08:51:42.000', '2021-09-04 16:24:04.785', NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/category/jujiaimg.jpg', 5, 1, NULL),
	(5, '饰品', NULL, '2019-07-15 08:51:49.000', '2021-09-04 16:25:33.409', NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/category/shipingimg.jpg', 4, 1, NULL),
	(6, '平底鞋', NULL, '2019-07-15 08:51:55.000', '2021-09-04 16:19:54.543', NULL, 0, 1, 'http://127.0.0.1:8080/cms-pictures/category/xie1.jpg', NULL, 1, NULL),
	(8, '拖鞋', NULL, '2019-07-15 08:53:04.000', '2021-09-04 16:20:04.816', NULL, 0, 1, 'http://127.0.0.1:8080/cms-pictures/category/xie2.jpg', NULL, 1, NULL),
	(9, '运动鞋', NULL, '2019-07-15 08:53:23.000', '2021-09-04 16:20:27.331', NULL, 0, 1, 'http://127.0.0.1:8080/cms-pictures/category/xie3.jpg', NULL, 1, NULL),
	(10, '高跟鞋', NULL, '2019-07-15 08:53:51.000', '2021-09-04 16:20:32.338', NULL, 0, 1, 'http://127.0.0.1:8080/cms-pictures/category/xie4.jpg', NULL, 1, NULL),
	(11, '衬衫', NULL, '2019-07-15 08:54:41.000', '2021-09-04 16:26:28.208', NULL, 0, 2, 'http://127.0.0.1:8080/cms-pictures/category/yifu1.jpg', NULL, 1, NULL),
	(12, 'T恤', NULL, '2019-07-15 08:55:11.000', '2021-09-04 16:26:40.177', NULL, 0, 2, 'http://127.0.0.1:8080/cms-pictures/category/yifu2.jpg', NULL, 1, NULL),
	(13, '牛仔裤', NULL, '2019-07-15 08:55:58.000', '2021-09-04 16:26:42.194', NULL, 0, 2, 'http://127.0.0.1:8080/cms-pictures/category/yifu3.jpg', NULL, 1, NULL),
	(14, '针织衫', NULL, '2019-07-15 08:56:27.000', '2021-09-04 16:26:44.189', NULL, 0, 2, 'http://127.0.0.1:8080/cms-pictures/category/yifu4.jpg', NULL, 1, NULL),
	(15, '连衣裙', NULL, '2019-07-15 08:56:43.000', '2021-09-04 16:26:46.238', NULL, 0, 2, 'http://127.0.0.1:8080/cms-pictures/category/yifu5.jpg', NULL, 1, NULL),
	(16, '风衣', NULL, '2019-07-15 08:57:38.000', '2021-09-04 16:26:48.620', NULL, 0, 2, 'http://127.0.0.1:8080/cms-pictures/category/yifu6.jpg', NULL, 1, NULL),
	(17, '手包', NULL, '2019-07-15 08:58:12.000', '2021-09-04 16:21:58.873', NULL, 0, 3, 'http://127.0.0.1:8080/cms-pictures/category/bao1.jpg', NULL, 1, NULL),
	(18, '旅行包', NULL, '2019-07-15 08:58:38.000', '2021-09-04 16:22:03.142', NULL, 0, 3, 'http://127.0.0.1:8080/cms-pictures/category/bao2.jpg', NULL, 1, NULL),
	(19, '单肩包', NULL, '2019-07-15 08:58:51.000', '2021-09-04 16:22:05.555', NULL, 0, 3, 'http://127.0.0.1:8080/cms-pictures/category/bao3.jpg', NULL, 1, NULL),
	(20, '收纳', NULL, '2019-07-15 09:00:19.000', '2021-09-04 16:24:28.616', NULL, 0, 4, 'http://127.0.0.1:8080/cms-pictures/category/jujia1.jpg', NULL, 1, NULL),
	(21, '毛巾', NULL, '2019-07-15 09:01:38.000', '2021-09-04 16:24:33.985', NULL, 0, 4, 'http://127.0.0.1:8080/cms-pictures/category/jujia2.jpg', NULL, 1, NULL),
	(22, '四件套', NULL, '2019-07-15 09:04:52.000', '2021-09-04 16:24:42.089', NULL, 0, 4, 'http://127.0.0.1:8080/cms-pictures/category/jujia3.jpg', NULL, 1, NULL),
	(23, '台灯', NULL, '2019-07-15 14:18:40.000', '2021-09-04 16:24:45.666', NULL, 0, 4, 'http://127.0.0.1:8080/cms-pictures/category/jujia4.jpg', NULL, 1, NULL),
	(24, '工艺', NULL, '2019-07-15 14:20:05.000', '2021-09-04 16:29:04.883', NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/category/gongyiimg.jpg', 1, 1, NULL),
	(32, '文具', NULL, '2019-07-30 01:29:19.000', '2021-09-04 16:29:19.124', NULL, 0, 24, 'http://127.0.0.1:8080/cms-pictures/category/gongyi1.jpg', NULL, 1, NULL),
	(35, '家具', NULL, '2019-08-02 19:44:24.000', '2021-09-04 16:24:59.246', NULL, 0, 4, 'http://127.0.0.1:8080/cms-pictures/category/jujia5.jpg', 9, 1, NULL),
	(36, '酷玩', NULL, '2019-08-07 22:49:22.000', '2021-09-04 16:25:50.736', NULL, 0, 5, 'http://127.0.0.1:8080/cms-pictures/category/shiping1.jpg', NULL, 1, NULL),
	(37, '测试数据', NULL, '2019-08-25 19:06:24.000', '2021-09-04 16:22:52.483', NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/category/ceshiimg.jpg', NULL, 1, NULL),
	(38, '测试数据', NULL, '2019-08-25 19:07:53.000', '2021-09-04 16:23:04.859', NULL, 0, 37, 'http://127.0.0.1:8080/cms-pictures/category/ceshi1.jpg', NULL, 1, NULL),
	(39, '束带', NULL, '2019-09-07 16:43:29.000', '2021-09-04 16:25:54.572', NULL, 0, 5, 'http://127.0.0.1:8080/cms-pictures/category/shiping2.jpg', NULL, 1, NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- 导出  表 cms.coupon 结构
CREATE TABLE IF NOT EXISTS `coupon` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `full_money` decimal(10,2) DEFAULT NULL COMMENT '满减门槛',
  `minus` decimal(10,2) DEFAULT NULL,
  `rate` decimal(10,2) DEFAULT NULL,
  `type` smallint(6) NOT NULL COMMENT '1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `activity_id` int(10) unsigned DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `whole_store` tinyint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.coupon 的数据：~5 rows (大约)
DELETE FROM `coupon`;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` (`id`, `title`, `start_time`, `end_time`, `description`, `full_money`, `minus`, `rate`, `type`, `create_time`, `update_time`, `delete_time`, `activity_id`, `remark`, `whole_store`) VALUES
	(3, '无门槛减0.1券', '2019-08-05 06:11:42', '2031-08-05 06:11:48', NULL, NULL, 0.10, NULL, 3, '2019-08-03 08:22:06.000', '2019-08-26 14:50:55.000', NULL, 2, '全场无门槛立减', 1),
	(4, '满500减100券', '2019-08-05 06:11:42', '2030-08-05 06:11:48', NULL, 500.00, 100.00, NULL, 1, '2019-08-03 08:19:34.000', '2019-09-15 21:44:53.000', NULL, 2, '限服装、居家、文具等商品', 0),
	(7, '满1000减230券', '2019-08-05 06:10:48', '2030-03-05 06:11:17', NULL, 1000.00, 230.00, NULL, 1, '2019-08-03 08:18:36.000', '2019-09-15 21:44:53.000', NULL, 2, '限服装、家具、文具等商品', 0),
	(10, '满1000打8折', '2019-08-23 09:07:29', '2030-08-23 09:07:36', NULL, NULL, NULL, NULL, 2, '2019-08-23 09:07:57.000', '2019-08-23 09:07:57.000', NULL, NULL, NULL, NULL),
	(11, '满100打9.9折', '2019-08-28 03:49:55', '2030-08-28 03:49:59', NULL, 100.00, NULL, 0.99, 2, '2019-08-28 03:49:11.000', '2019-09-05 19:14:56.000', NULL, 2, '全场通用券', 1);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

-- 导出  表 cms.coupon_category 结构
CREATE TABLE IF NOT EXISTS `coupon_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int(10) unsigned NOT NULL,
  `coupon_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.coupon_category 的数据：~6 rows (大约)
DELETE FROM `coupon_category`;
/*!40000 ALTER TABLE `coupon_category` DISABLE KEYS */;
INSERT INTO `coupon_category` (`id`, `category_id`, `coupon_id`) VALUES
	(1, 15, 4),
	(2, 32, 4),
	(6, 35, 4),
	(7, 15, 7),
	(8, 35, 7),
	(9, 32, 7);
/*!40000 ALTER TABLE `coupon_category` ENABLE KEYS */;

-- 导出  表 cms.coupon_template 结构
CREATE TABLE IF NOT EXISTS `coupon_template` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `full_money` decimal(10,2) DEFAULT NULL,
  `minus` decimal(10,2) DEFAULT NULL,
  `discount` decimal(10,2) DEFAULT NULL COMMENT '国内多是打折，国外多是百分比 off',
  `type` smallint(6) NOT NULL COMMENT '1. 满减券 2.折扣券 3.无门槛券 4.满金额折扣券',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.coupon_template 的数据：~7 rows (大约)
DELETE FROM `coupon_template`;
/*!40000 ALTER TABLE `coupon_template` DISABLE KEYS */;
INSERT INTO `coupon_template` (`id`, `title`, `description`, `full_money`, `minus`, `discount`, `type`, `create_time`, `update_time`, `delete_time`) VALUES
	(3, '满1000-230', NULL, 1000.00, 230.00, NULL, 1, '2019-08-03 08:18:36.000', '2019-08-03 08:20:18.000', NULL),
	(4, '满500-100', NULL, 500.00, 100.00, NULL, 1, '2019-08-03 08:19:34.000', '2019-08-03 08:19:34.000', NULL),
	(5, '满2000-500', NULL, 2000.00, 500.00, NULL, 1, '2019-08-03 08:20:03.000', '2019-08-03 08:20:23.000', NULL),
	(6, '满5000-1500', NULL, 5000.00, 1500.00, NULL, 1, '2019-08-03 08:21:15.000', '2019-08-03 08:21:15.000', NULL),
	(7, '无门槛-20', NULL, NULL, NULL, NULL, 3, '2019-08-03 08:22:06.000', '2019-08-03 08:22:06.000', NULL),
	(8, '满减折扣', NULL, 800.00, NULL, 0.90, 4, '2019-08-03 08:23:54.000', '2019-08-03 08:25:02.000', NULL),
	(9, '无门槛折扣', NULL, NULL, NULL, 0.95, 2, '2019-08-03 08:25:54.000', '2019-08-03 08:25:54.000', NULL);
/*!40000 ALTER TABLE `coupon_template` ENABLE KEYS */;

-- 导出  表 cms.coupon_type 结构
CREATE TABLE IF NOT EXISTS `coupon_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `code` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.coupon_type 的数据：~0 rows (大约)
DELETE FROM `coupon_type`;
/*!40000 ALTER TABLE `coupon_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon_type` ENABLE KEYS */;

-- 导出  表 cms.grid_category 结构
CREATE TABLE IF NOT EXISTS `grid_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `root_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.grid_category 的数据：~6 rows (大约)
DELETE FROM `grid_category`;
/*!40000 ALTER TABLE `grid_category` DISABLE KEYS */;
INSERT INTO `grid_category` (`id`, `title`, `img`, `name`, `create_time`, `update_time`, `delete_time`, `category_id`, `root_category_id`) VALUES
	(1, '服装', 'http://127.0.0.1:8080/cms-pictures/grid/grid1.jpg', NULL, '2019-08-02 19:41:55.000', '2021-08-27 11:55:58.193', NULL, NULL, 2),
	(2, '包包', 'http://127.0.0.1:8080/cms-pictures/grid/grid2.jpg', NULL, '2019-08-02 19:42:11.000', '2021-08-27 11:56:00.323', NULL, NULL, 3),
	(3, '鞋', 'http://127.0.0.1:8080/cms-pictures/grid/grid3.jpg', NULL, '2019-08-02 19:42:57.000', '2021-08-27 11:56:02.659', NULL, NULL, 1),
	(4, '饰品', 'http://127.0.0.1:8080/cms-pictures/grid/grid4.jpg', NULL, '2019-08-02 19:43:34.000', '2021-08-27 11:56:04.726', NULL, NULL, 5),
	(5, '居家', 'http://127.0.0.1:8080/cms-pictures/grid/grid5.jpg', NULL, '2019-08-02 19:44:34.000', '2021-08-27 11:56:06.919', NULL, NULL, 4),
	(6, '工艺', 'http://127.0.0.1:8080/cms-pictures/grid/grid6.jpg', NULL, '2019-08-02 19:46:53.000', '2021-08-27 11:56:07.598', NULL, NULL, 24);
/*!40000 ALTER TABLE `grid_category` ENABLE KEYS */;

-- 导出  表 cms.order 结构
CREATE TABLE IF NOT EXISTS `order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) DEFAULT NULL,
  `user_id` int(10) unsigned DEFAULT NULL COMMENT 'user表外键',
  `total_price` decimal(10,2) DEFAULT '0.00',
  `total_count` int(11) unsigned DEFAULT '0',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `expired_time` datetime(3) DEFAULT NULL,
  `placed_time` datetime(3) DEFAULT NULL,
  `snap_img` varchar(255) DEFAULT NULL,
  `snap_title` varchar(255) DEFAULT NULL,
  `snap_items` json DEFAULT NULL,
  `snap_address` json DEFAULT NULL,
  `prepay_id` varchar(255) DEFAULT NULL,
  `final_total_price` decimal(10,2) DEFAULT NULL,
  `status` tinyint(3) unsigned DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_order_no` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.order 的数据：~1 rows (大约)
DELETE FROM `order`;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `order_no`, `user_id`, `total_price`, `total_count`, `create_time`, `delete_time`, `update_time`, `expired_time`, `placed_time`, `snap_img`, `snap_title`, `snap_items`, `snap_address`, `prepay_id`, `final_total_price`, `status`) VALUES
	(19, 'A932560460445', 34, 1399.00, 1, '2021-09-03 22:42:05.659', NULL, '2021-09-03 22:42:05.659', '2021-09-03 23:42:05.603', '2021-09-03 22:42:05.603', 'http://127.0.0.1:8080/cms-pictures/sku/h1.jpg', '双色百褶裙（鹅暖青）', '[{"id": 31, "img": "http://127.0.0.1:8080/cms-pictures/sku/h1.jpg", "count": 1, "title": "双色百褶裙（鹅暖青）", "spu_id": 23, "final_price": 1399, "spec_values": ["鹅暖青", "中号 M"], "single_price": 1399}]', '{"city": "广州市", "county": "海珠区", "detail": "新港中路397号", "mobile": "020-81167888", "province": "广东省", "user_name": "张三", "postal_code": "510000", "national_code": "510000"}', NULL, 1399.00, 1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- 导出  表 cms.product 结构
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 正在导出表  cms.product 的数据：~0 rows (大约)
DELETE FROM `product`;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;

-- 导出  表 cms.sale_explain 结构
CREATE TABLE IF NOT EXISTS `sale_explain` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fixed` tinyint(3) unsigned DEFAULT '0',
  `text` varchar(100) NOT NULL,
  `spu_id` int(11) DEFAULT NULL,
  `index` int(10) unsigned DEFAULT NULL,
  `replace_id` int(10) unsigned DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.sale_explain 的数据：~4 rows (大约)
DELETE FROM `sale_explain`;
/*!40000 ALTER TABLE `sale_explain` DISABLE KEYS */;
INSERT INTO `sale_explain` (`id`, `fixed`, `text`, `spu_id`, `index`, `replace_id`, `create_time`, `delete_time`, `update_time`) VALUES
	(1, 1, '发货地：上海', NULL, 1, NULL, '2019-08-17 04:59:44.000', NULL, '2019-08-17 04:59:44.000'),
	(2, 1, '物流：顺丰', NULL, 2, NULL, '2019-08-17 04:59:44.000', NULL, '2019-08-17 05:00:27.000'),
	(3, 1, '发货时间：七个工作日', NULL, 3, NULL, '2019-08-17 04:59:44.000', NULL, '2019-08-17 05:00:29.000'),
	(4, 1, '售后：不支持7天无理由退货', NULL, 4, NULL, '2019-08-17 04:59:44.000', NULL, '2019-09-10 12:17:04.000');
/*!40000 ALTER TABLE `sale_explain` ENABLE KEYS */;

-- 导出  表 cms.sku 结构
CREATE TABLE IF NOT EXISTS `sku` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) unsigned NOT NULL,
  `discount_price` decimal(10,2) unsigned DEFAULT NULL,
  `online` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `img` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `spu_id` int(10) unsigned NOT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `specs` json DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `stock` int(10) unsigned NOT NULL DEFAULT '0',
  `category_id` int(10) unsigned DEFAULT NULL,
  `root_category_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.sku 的数据：~50 rows (大约)
DELETE FROM `sku`;
/*!40000 ALTER TABLE `sku` DISABLE KEYS */;
INSERT INTO `sku` (`id`, `price`, `discount_price`, `online`, `img`, `title`, `spu_id`, `create_time`, `update_time`, `delete_time`, `specs`, `code`, `stock`, `category_id`, `root_category_id`) VALUES
	(1, 13.80, 11.10, 1, 'http://127.0.0.1:8080/cms-pictures/spu/wan1.jpg', '青峰·7英寸', 1, '2019-07-16 13:14:26.000', '2021-09-04 15:46:36.887', NULL, '[{"key": "颜色", "value": "青蓝色", "key_id": 1, "value_id": 1}, {"key": "尺寸", "value": "7英寸", "key_id": 2, "value_id": 5}]', '1$1-1#2-5', 99, 28, 27),
	(3, 66.00, 59.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/mao2.jpg', '青芒色·灌篮高手', 2, '2019-07-18 13:11:11.000', '2021-09-04 15:37:43.884', NULL, '[{"key": "颜色", "value": "青芒色", "key_id": 1, "value_id": 42}, {"key": "图案", "value": "灌篮高手", "key_id": 3, "value_id": 10}, {"key": "尺码", "value": "中号 M", "key_id": 4, "value_id": 15}]', '2$1-42#3-10#4-15', 999, 17, 3),
	(4, 88.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/mao1.jpg', '青芒色·圣斗士', 2, '2019-07-18 13:11:13.000', '2021-09-04 15:37:49.166', NULL, '[{"key": "颜色", "value": "青芒色", "key_id": 1, "value_id": 42}, {"key": "图案", "value": "圣斗士", "key_id": 3, "value_id": 11}, {"key": "尺码", "value": "大号  L", "key_id": 4, "value_id": 16}]', '2$1-42#3-11#4-16', 8, 17, 3),
	(5, 77.00, 59.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/mao1.jpg', '橘黄色·七龙珠', 2, '2019-07-18 13:11:16.000', '2021-09-04 15:37:51.469', NULL, '[{"key": "颜色", "value": "橘黄色", "key_id": 1, "value_id": 44}, {"key": "图案", "value": "七龙珠", "key_id": 3, "value_id": 9}, {"key": "尺码", "value": "小号 S", "key_id": 4, "value_id": 14}]', '2$1-44#3-9#4-14', 7, 17, 3),
	(6, 29.99, 19.99, 1, 'http://127.0.0.1:8080/cms-pictures/sku/h1.jpg', '黑色', 3, '2019-07-30 02:26:25.000', '2021-08-27 12:17:34.057', '2019-09-14 06:43:46.000', '[{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}]', '3$1-12', 2, 32, 24),
	(7, 32.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/h1.jpg', '银色', 3, '2019-07-30 02:27:39.000', '2021-08-27 12:17:34.567', '2019-09-14 06:43:49.000', '[{"key": "颜色", "value": "银色", "key_id": 1, "value_id": 18}]', '3$1-18', 3, 32, 24),
	(8, 49.70, 37.70, 1, 'http://127.0.0.1:8080/cms-pictures/sku/h1.jpg', '金色', 3, '2019-07-30 02:27:39.000', '2021-08-27 12:17:35.097', '2019-09-14 06:43:51.000', '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 19}]', '3$1-19', 0, 32, 24),
	(9, 16.60, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/spu/wan2.jpg', '白羽·4.3英寸', 1, '2019-07-30 02:36:26.000', '2021-09-04 15:47:13.196', NULL, '[{"key": "颜色", "value": "深白色", "key_id": 1, "value_id": 3}, {"key": "尺寸", "value": "4.3英寸", "key_id": 2, "value_id": 7}]', '1$1-3#2-7', 0, 28, 27),
	(10, 78.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/bu1.jpg', '桌旗 130 cm', 4, '2019-07-30 06:46:56.000', '2021-09-04 15:43:33.352', NULL, '[{"key": "颜色规格", "value": "桌旗 30x 100 cm", "key_id": 5, "value_id": 20}]', '4$5-20', 665, 26, 27),
	(11, 128.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/bu1.jpg', '桌布 140x 360 cm', 4, '2019-07-30 06:52:40.000', '2021-09-04 15:43:34.412', NULL, '[{"key": "颜色规格", "value": "桌布 140x 360 cm", "key_id": 5, "value_id": 21}]', '4$5-21', 555, 26, 27),
	(12, 72.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/bu1.jpg', '桌旗 30x 220 cm', 4, '2019-07-30 06:52:40.000', '2021-09-04 15:43:35.435', NULL, '[{"key": "颜色规格", "value": "桌旗 30x 220 cm", "key_id": 5, "value_id": 22}]', '4$5-22', 556, 26, 27),
	(13, 188.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/bu1.jpg', '桌布 160x 330 cm', 4, '2019-07-30 06:52:40.000', '2021-09-04 15:43:36.110', NULL, '[{"key": "颜色规格", "value": "桌布 160x 330 cm", "key_id": 5, "value_id": 23}]', '4$5-23', 555, 26, 27),
	(14, 77.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/mojing1.jpg', '飞行员墨镜（阳光橙）', 11, '2019-08-07 22:53:15.000', '2021-09-04 15:32:30.237', NULL, NULL, NULL, 330, 36, 5),
	(15, 0.20, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/ceshi.jpg', '林白的测试数据', 12, '2019-08-26 01:01:35.000', '2021-09-04 16:08:02.293', NULL, '[]', '12$', 933, 38, 37),
	(16, 72.00, 68.00, 1, 'http://127.0.0.1:8080/cms-pictures/spu/spu1.jpg', '碳素墨水', 10, '2019-08-28 01:25:41.000', '2021-09-04 14:47:42.291', NULL, NULL, NULL, 70, 32, 24),
	(17, 297.00, 236.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good8.jpg', '爱丽丝Alisia束带（黑色）', 13, '2019-08-28 01:25:41.000', '2021-09-04 15:58:46.695', NULL, '[{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}]', '13$1-12', 71, 39, 5),
	(18, 297.00, 236.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good8.jpg', '爱丽丝Alisia束带（棕色）', 13, '2019-08-28 01:25:41.000', '2021-09-04 15:58:47.327', NULL, '[{"key": "颜色", "value": "棕色", "key_id": 1, "value_id": 24}]', '13$1-24', 71, 39, 5),
	(19, 297.00, 236.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good8.jpg', '爱丽丝Alisia束带  （咖色）', 13, '2019-08-28 01:25:41.000', '2021-09-04 15:58:47.889', NULL, '[{"key": "颜色", "value": "咖色", "key_id": 1, "value_id": 25}]', '13$1-25', 71, 39, 5),
	(20, 19.90, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/jiazi1.jpg', 'Decend Mini小夹子  （橙色）', 14, '2019-08-28 01:25:41.000', '2021-09-04 15:16:29.707', NULL, '[{"key": "颜色", "value": "橙色", "key_id": 1, "value_id": 27}]', '14$1-27', 71, 20, 4),
	(21, 19.90, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/jiazi2.jpg', 'Decend Mini小夹子  （红色）', 14, '2019-08-28 01:25:41.000', '2021-09-04 15:16:36.593', NULL, '[{"key": "颜色", "value": "红色", "key_id": 1, "value_id": 26}]', '14$1-26', 0, 20, 4),
	(22, 19.90, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/jiazi3.jpg', 'Decend Mini小夹子   （金色）', 14, '2019-08-28 01:25:41.000', '2021-09-04 15:16:42.573', NULL, '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 19}]', '14$1-19', 71, 20, 4),
	(23, 24.00, 19.90, 1, 'http://127.0.0.1:8080/cms-pictures/sku/zhen3.jpg', '多彩别针、回形针 Mini （金色）一盒30个', 15, '2019-09-10 02:05:48.000', '2021-09-04 15:08:20.570', NULL, '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 19}, {"key": "数量", "value": "一盒30个", "key_id": 6, "value_id": 28}]', '15$1-19#6-28', 71, 32, 24),
	(24, 24.00, 19.90, 1, 'http://127.0.0.1:8080/cms-pictures/sku/zhen4.jpg', '多彩别针、回形针 Mini （银色）一盒30个', 15, '2019-09-10 02:05:48.000', '2021-09-04 15:08:25.879', NULL, '[{"key": "颜色", "value": "银色", "key_id": 1, "value_id": 18}, {"key": "数量", "value": "一盒30个", "key_id": 6, "value_id": 28}]', '15$1-18#6-28', 71, 32, 24),
	(25, 24.00, 19.90, 1, 'http://127.0.0.1:8080/cms-pictures/sku/zhen5.jpg', '多彩别针、回形针 Mini （黑色）一盒30个', 15, '2019-09-10 02:05:48.000', '2021-09-04 15:08:34.099', NULL, '[{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}, {"key": "数量", "value": "一盒30个", "key_id": 6, "value_id": 28}]', '15$1-12#6-28', 71, 32, 24),
	(26, 32.00, 24.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/zhen5.jpg', '多彩别针、回形针 Mini （黑色）一盒50个', 15, '2019-09-10 02:05:48.000', '2021-09-04 15:09:20.361', NULL, '[{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}, {"key": "数量", "value": "一盒50个", "key_id": 6, "value_id": 29}]', '15$1-12#6-29', 71, 32, 24),
	(27, 1799.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/sleeve.jpg', '风袖说 Sleeven牛仔 ', 6, '2019-09-10 02:05:48.000', '2021-09-04 16:02:51.853', NULL, '[{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}, {"key": "尺码", "value": "小号 S", "key_id": 4, "value_id": 14}]', '6$1-12#4-14', 70, 14, 2),
	(28, 1799.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/sleeve.jpg', '风袖说 Sleeve牛仔 ', 6, '2019-09-10 02:05:48.000', '2021-09-04 16:02:54.901', NULL, '[{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}, {"key": "尺码", "value": "中号 M", "key_id": 4, "value_id": 15}]', '6$1-12#4-15', 70, 14, 2),
	(29, 1799.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/sleeve.jpg', '风袖说 Sleeve牛仔 ', 6, '2019-09-10 02:05:48.000', '2021-09-04 16:02:55.614', NULL, '[{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}, {"key": "尺码", "value": "中号 L", "key_id": 4, "value_id": 16}]', '6$1-12#4-16', 70, 14, 2),
	(30, 1399.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/spu/qun.jpg', '双色百褶裙（棕色）', 23, '2019-09-12 22:42:33.000', '2021-09-04 16:11:22.322', NULL, '[{"key": "颜色", "value": "棕色", "key_id": 1, "value_id": 24}, {"key": "尺码", "value": "小号 S", "key_id": 4, "value_id": 14}]', '23$1-24#4-14', 69, 15, 2),
	(31, 1399.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/qun.jpg', '双色百褶裙（鹅暖青）', 23, '2019-09-14 01:44:49.000', '2021-09-04 16:11:27.702', NULL, '[{"key": "颜色", "value": "鹅暖青", "key_id": 1, "value_id": 30}, {"key": "尺码", "value": "中号 M", "key_id": 4, "value_id": 15}]', '23$1-30#4-15', 6, 15, 2),
	(32, 1399.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/qun.jpg', '双色百褶裙（鹅暖青 小号）', 23, '2019-09-14 01:46:06.000', '2021-09-04 16:11:30.258', NULL, '[{"key": "颜色", "value": "鹅暖青", "key_id": 1, "value_id": 30}, {"key": "尺码", "value": "小号 S", "key_id": 4, "value_id": 14}]', '23$1-30#4-14', 8, 15, 2),
	(33, 2799.00, 1799.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good7.jpg', '秋冬新款驼色大衣', 24, '2019-09-14 02:17:15.000', '2021-09-04 15:28:01.225', NULL, '[{"key": "颜色", "value": "驼色", "key_id": 1, "value_id": 31}, {"key": "尺码", "value": "小号 S", "key_id": 4, "value_id": 14}]', '24$1-31#4-14', 70, 16, 2),
	(34, 2699.00, 1799.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good7.jpg', '秋冬新款驼色大衣（驼色 M号）', 24, '2019-09-14 02:19:39.000', '2021-09-04 15:28:00.260', NULL, '[{"key": "颜色", "value": "驼色", "key_id": 1, "value_id": 31}, {"key": "尺码", "value": "中号 M", "key_id": 4, "value_id": 15}]', '24$1-31#4-15', 70, 16, 2),
	(35, 2999.00, 1699.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good7.jpg', '秋冬新款驼色大衣 （L号）', 24, '2019-09-14 02:21:02.000', '2021-09-04 15:28:01.873', NULL, '[{"key": "颜色", "value": "驼色", "key_id": 1, "value_id": 31}, {"key": "尺码", "value": "大号  L", "key_id": 4, "value_id": 16}]', '24$1-31#4-16', 47, 16, 2),
	(36, 3999.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/h2.jpg', '复古双色沙发（藏青色）', 25, '2019-09-14 02:42:50.000', '2021-08-27 11:56:51.183', NULL, '[{"key": "颜色", "value": "藏青色", "key_id": 1, "value_id": 2}, {"key": "双色沙发尺寸（非标）", "value": "1.5米 x 1米", "key_id": 7, "value_id": 32}]', '25$1-2#7-32', 87, 35, NULL),
	(37, 3999.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/sku/h1.jpg', '复古双色沙发 (米黄色）', 25, '2019-09-14 02:43:47.000', '2021-08-27 11:56:49.717', NULL, '[{"key": "颜色", "value": "米黄色", "key_id": 1, "value_id": 35}, {"key": "双色沙发尺寸（非标）", "value": "2米 x 1米", "key_id": 7, "value_id": 33}]', '25$1-35#7-33', 56, 35, NULL),
	(38, 4799.00, 4299.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good2.jpg', 'SemiConer柔质沙发（长款）', 26, '2019-09-14 05:44:45.000', '2021-09-04 15:26:38.515', NULL, '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 27}, {"key": "双色沙发尺寸（非标）", "value": "2米 x 1米", "key_id": 7, "value_id": 33}]', '26$1-27#7-33', 7, 35, 4),
	(39, 4799.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good2.jpg', 'SemiConer柔质沙发 （L型）', 26, '2019-09-14 05:45:44.000', '2021-09-04 15:26:39.352', NULL, '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 27}, {"key": "双色沙发尺寸（非标）", "value": "L型 2米 + 0.8米", "key_id": 7, "value_id": 34}]', '26$1-27#7-34', 7, 35, 4),
	(40, 4799.00, 4299.00, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good2.jpg', 'SemiConer柔质沙发（短款）', 26, '2019-09-14 05:46:29.000', '2021-09-04 15:26:40.021', NULL, '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 27}, {"key": "双色沙发尺寸（非标）", "value": "1.5米 x 1米", "key_id": 7, "value_id": 32}]', '26$1-27#7-32', 6, 35, 4),
	(41, 1399.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good4.jpg', 'Mier双色靠椅（海蓝色）', 27, '2019-09-14 06:11:11.000', '2021-09-04 15:29:28.474', NULL, '[{"key": "颜色", "value": "海蓝色", "key_id": 1, "value_id": 36}]', '27$1-36', 67, 35, 4),
	(42, 1399.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good4.jpg', 'Mier双色靠椅 （象牙白）', 27, '2019-09-14 06:11:50.000', '2021-09-04 15:29:29.605', NULL, '[{"key": "颜色", "value": "象牙白", "key_id": 1, "value_id": 37}]', '27$1-37', 13, 35, 4),
	(43, 999.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good3.jpg', 'Ins复古金色落地灯（落地灯）', 28, '2019-09-14 06:25:46.000', '2021-09-04 15:26:00.510', NULL, '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 27}, {"key": "台灯高低", "value": "落地灯", "key_id": 8, "value_id": 38}]', '28$1-27#8-38', 19, 23, 4),
	(44, 999.00, NULL, 1, 'http://127.0.0.1:8080/cms-pictures/goods/good3.jpg', 'Ins复古金色落地灯 （台灯）', 28, '2019-09-14 06:26:25.000', '2021-09-04 15:26:01.342', NULL, '[{"key": "颜色", "value": "金色", "key_id": 1, "value_id": 27}, {"key": "台灯高低", "value": "台灯", "key_id": 8, "value_id": 39}]', '28$1-27#8-39', 19, 23, 4),
	(45, 22.00, 17.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/xiangpi1.jpg', '抹茶小橡皮 （一盒30个）', 3, '2019-09-14 06:36:02.000', '2021-09-04 15:01:44.000', NULL, '[{"key": "颜色", "value": "抹茶绿", "key_id": 1, "value_id": 40}, {"key": "数量", "value": "一盒30个", "key_id": 6, "value_id": 28}]', '3$1-40#6-28', 120, 32, 24),
	(46, 22.00, 20.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/xiangpi2.jpg', '抹茶小橡皮（一盒50个）', 3, '2019-09-14 06:40:00.000', '2021-09-04 15:01:50.369', NULL, '[{"key": "颜色", "value": "抹茶绿", "key_id": 1, "value_id": 40}, {"key": "数量", "value": "一盒50个", "key_id": 6, "value_id": 29}]', '3$1-40#6-29', 8, 32, 24),
	(47, 29.00, 20.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/biji1.jpg', 'ins复古印花NoteBook （一盒30）', 8, '2019-09-14 06:51:47.000', '2021-09-04 15:21:22.910', NULL, '[{"key": "颜色", "value": "青草绿", "key_id": 1, "value_id": 41}, {"key": "数量", "value": "一盒30个", "key_id": 6, "value_id": 28}]', '8$1-41#6-28', 18, 32, 24),
	(48, 29.00, 27.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/biji2.jpg', 'ins复古印花NoteBook （一盒50个）', 8, '2019-09-14 06:55:14.000', '2021-09-04 15:21:29.791', NULL, '[{"key": "颜色", "value": "青草绿", "key_id": 1, "value_id": 41}, {"key": "数量", "value": "一盒50个", "key_id": 6, "value_id": 29}]', '8$1-41#6-29', 89, 32, 24),
	(49, 379.00, 279.00, 1, 'http://127.0.0.1:8080/cms-pictures/spu/shan1.jpg', '七色针织衫（米黄）', 5, '2019-09-14 07:08:06.000', '2021-09-04 15:56:34.476', NULL, '[{"key": "颜色", "value": "米黄色", "key_id": 1, "value_id": 35}, {"key": "尺码", "value": "中号 M", "key_id": 4, "value_id": 15}]', '5$1-35#4-15', 7, 14, 2),
	(50, 349.00, 279.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/shan1.jpg', '七色针织衫（白色）', 5, '2019-09-14 07:09:31.000', '2021-09-04 15:56:41.879', NULL, '[{"key": "颜色", "value": "象牙白", "key_id": 1, "value_id": 37}, {"key": "尺码", "value": "小号 S", "key_id": 4, "value_id": 14}]', '5$1-37#4-14', 60, 14, 2),
	(51, 349.00, 279.00, 1, 'http://127.0.0.1:8080/cms-pictures/sku/shan2.jpg', '七色针织衫（蓝色）', 5, '2019-09-14 07:12:13.000', '2021-09-04 15:56:50.026', NULL, '[{"key": "颜色", "value": "青蓝色", "key_id": 1, "value_id": 1}, {"key": "尺码", "value": "中号 M", "key_id": 4, "value_id": 15}]', '5$1-1#4-15', 60, 14, 2);
/*!40000 ALTER TABLE `sku` ENABLE KEYS */;

-- 导出  表 cms.sku_spec 结构
CREATE TABLE IF NOT EXISTS `sku_spec` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `spu_id` int(10) unsigned NOT NULL,
  `sku_id` int(10) unsigned NOT NULL,
  `key_id` int(10) unsigned NOT NULL,
  `value_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.sku_spec 的数据：~60 rows (大约)
DELETE FROM `sku_spec`;
/*!40000 ALTER TABLE `sku_spec` DISABLE KEYS */;
INSERT INTO `sku_spec` (`id`, `spu_id`, `sku_id`, `key_id`, `value_id`) VALUES
	(5, 23, 2728, 1, 25),
	(6, 23, 2728, 4, 14),
	(7, 23, 2729, 1, 1),
	(8, 23, 2729, 4, 14),
	(9, 23, 2730, 1, 24),
	(10, 23, 2730, 4, 14),
	(11, 23, 30, 1, 24),
	(12, 23, 30, 4, 14),
	(13, 23, 31, 1, 30),
	(14, 23, 31, 4, 15),
	(15, 23, 32, 1, 30),
	(16, 23, 32, 4, 14),
	(23, 25, 36, 1, 2),
	(24, 25, 36, 7, 32),
	(25, 25, 37, 1, 35),
	(26, 25, 37, 7, 33),
	(27, 26, 38, 1, 27),
	(28, 26, 38, 7, 33),
	(29, 26, 39, 1, 27),
	(30, 26, 39, 7, 34),
	(31, 26, 40, 1, 27),
	(32, 26, 40, 7, 32),
	(33, 27, 41, 1, 36),
	(34, 27, 42, 1, 37),
	(35, 28, 43, 1, 27),
	(36, 28, 43, 8, 38),
	(37, 28, 44, 1, 27),
	(38, 28, 44, 8, 39),
	(41, 3, 45, 1, 40),
	(42, 3, 45, 6, 28),
	(43, 3, 46, 1, 40),
	(44, 3, 46, 6, 29),
	(47, 8, 47, 1, 41),
	(48, 8, 47, 6, 28),
	(49, 8, 48, 1, 41),
	(50, 8, 48, 6, 29),
	(51, 5, 49, 1, 35),
	(52, 5, 49, 4, 15),
	(53, 5, 50, 1, 37),
	(54, 5, 50, 4, 14),
	(55, 5, 51, 1, 1),
	(56, 5, 51, 4, 15),
	(57, 24, 34, 1, 31),
	(58, 24, 34, 4, 15),
	(59, 24, 35, 1, 31),
	(60, 24, 35, 4, 16),
	(61, 24, 33, 1, 31),
	(62, 24, 33, 4, 14),
	(78, 2, 5, 1, 43),
	(79, 2, 5, 3, 9),
	(80, 2, 5, 4, 14),
	(81, 2, 3, 1, 42),
	(82, 2, 3, 3, 10),
	(83, 2, 3, 4, 15),
	(84, 2, 2, 1, 3),
	(85, 2, 2, 3, 9),
	(86, 2, 2, 4, 14),
	(87, 2, 4, 1, 42),
	(88, 2, 4, 3, 11),
	(89, 2, 4, 4, 16);
/*!40000 ALTER TABLE `sku_spec` ENABLE KEYS */;

-- 导出  表 cms.spec_key 结构
CREATE TABLE IF NOT EXISTS `spec_key` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `unit` varchar(30) DEFAULT NULL,
  `standard` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.spec_key 的数据：~8 rows (大约)
DELETE FROM `spec_key`;
/*!40000 ALTER TABLE `spec_key` DISABLE KEYS */;
INSERT INTO `spec_key` (`id`, `name`, `unit`, `standard`, `create_time`, `update_time`, `delete_time`, `description`) VALUES
	(1, '颜色', NULL, 1, '2019-07-15 15:33:18', '2019-07-15 15:33:18', NULL, NULL),
	(2, '尺寸', '英寸', 0, '2019-07-15 15:48:52', '2019-07-16 13:02:44', NULL, NULL),
	(3, '图案', NULL, 0, '2019-07-17 08:21:42', '2019-07-17 08:21:42', NULL, NULL),
	(4, '尺码', NULL, 1, '2019-07-17 08:24:40', '2019-07-17 08:24:47', NULL, NULL),
	(5, '颜色与规格', NULL, 0, '2019-07-30 06:39:27', '2019-07-30 06:39:27', NULL, NULL),
	(6, '数量', '个', 0, '2019-09-10 02:13:11', '2019-09-10 02:13:11', NULL, NULL),
	(7, '双色沙发尺寸（非标）', '米', 0, '2019-09-14 02:32:05', '2019-09-14 02:32:05', NULL, ''),
	(8, '台灯高低', '', 0, '2019-09-14 03:28:00', '2019-09-14 03:28:00', NULL, '');
/*!40000 ALTER TABLE `spec_key` ENABLE KEYS */;

-- 导出  表 cms.spec_value 结构
CREATE TABLE IF NOT EXISTS `spec_value` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `value` varchar(255) NOT NULL,
  `spec_id` int(10) unsigned NOT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `extend` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.spec_value 的数据：~45 rows (大约)
DELETE FROM `spec_value`;
/*!40000 ALTER TABLE `spec_value` DISABLE KEYS */;
INSERT INTO `spec_value` (`id`, `value`, `spec_id`, `create_time`, `update_time`, `delete_time`, `extend`) VALUES
	(1, '青蓝色', 1, '2019-07-16 12:52:36.000', '2019-07-16 12:52:36.000', NULL, NULL),
	(2, '藏青色', 1, '2019-07-16 12:55:45.000', '2019-07-16 12:55:45.000', NULL, NULL),
	(3, '深白色', 1, '2019-07-16 12:56:33.000', '2019-07-16 12:56:33.000', NULL, NULL),
	(4, '少女粉', 1, '2019-07-16 12:57:01.000', '2019-07-16 12:57:01.000', NULL, NULL),
	(5, '7英寸', 2, '2019-07-16 12:58:47.000', '2019-07-16 12:58:47.000', NULL, NULL),
	(6, '5英寸', 2, '2019-07-16 12:58:56.000', '2019-07-16 12:58:56.000', NULL, NULL),
	(7, '4.3英寸', 2, '2019-07-16 12:59:12.000', '2019-07-16 12:59:12.000', NULL, NULL),
	(8, '10英寸', 2, '2019-07-16 12:59:23.000', '2019-07-16 12:59:23.000', NULL, NULL),
	(9, '七龙珠', 3, '2019-07-17 08:22:05.000', '2019-07-17 08:22:05.000', NULL, NULL),
	(10, '灌篮高手', 3, '2019-07-17 08:22:15.000', '2019-07-17 08:22:15.000', NULL, NULL),
	(11, '圣斗士', 3, '2019-07-17 08:22:24.000', '2019-07-17 08:22:24.000', NULL, NULL),
	(12, '黑色', 1, '2019-07-17 08:22:42.000', '2019-07-17 08:22:42.000', NULL, NULL),
	(13, '墨绿色', 1, '2019-07-17 08:25:04.000', '2019-07-17 08:25:04.000', NULL, NULL),
	(14, '小号 S', 4, '2019-07-17 08:25:34.000', '2019-09-10 15:47:36.000', NULL, NULL),
	(15, '中号 M', 4, '2019-07-17 08:25:44.000', '2019-09-10 15:47:40.000', NULL, NULL),
	(16, '大号  L', 4, '2019-07-17 08:25:50.000', '2019-09-10 15:47:45.000', NULL, NULL),
	(17, '加大 XL', 4, '2019-07-17 08:26:03.000', '2019-09-10 15:47:50.000', NULL, NULL),
	(18, '银色', 1, '2019-07-30 02:23:10.000', '2019-07-30 02:23:10.000', NULL, NULL),
	(19, '金色', 1, '2019-07-30 02:23:21.000', '2019-07-30 02:23:21.000', NULL, NULL),
	(20, '桌旗 30x 100 cm', 5, '2019-07-30 06:40:26.000', '2019-07-30 06:43:19.000', NULL, NULL),
	(21, '桌旗 30x 220 cm', 5, '2019-07-30 06:42:16.000', '2019-07-30 06:43:22.000', NULL, NULL),
	(22, '桌布 140x 360 cm', 5, '2019-07-30 06:42:48.000', '2019-07-30 06:43:27.000', NULL, NULL),
	(23, '桌布 160x 330 cm', 5, '2019-07-30 06:43:16.000', '2019-07-30 06:43:29.000', NULL, NULL),
	(24, '棕色', 1, '2019-07-30 06:43:16.000', '2019-09-07 19:22:05.000', NULL, NULL),
	(25, '咖色', 1, '2019-07-30 06:43:16.000', '2019-09-07 19:22:08.000', NULL, NULL),
	(26, '红色', 1, '2019-09-07 19:22:01.000', '2019-09-07 19:22:01.000', NULL, NULL),
	(27, '金色', 1, '2019-09-07 19:22:33.000', '2019-09-07 19:22:33.000', NULL, NULL),
	(28, '一盒30个', 6, '2019-09-10 02:13:42.000', '2019-09-10 02:13:42.000', NULL, NULL),
	(29, '一盒50个', 6, '2019-09-10 02:13:42.000', '2019-09-10 02:13:42.000', NULL, NULL),
	(30, '鹅暖青', 1, '2019-09-14 01:42:00.000', '2019-09-14 01:42:00.000', NULL, ''),
	(31, '驼色', 1, '2019-09-14 02:15:59.000', '2019-09-14 02:15:59.000', NULL, ''),
	(32, '1.5米 x 1米', 7, '2019-09-14 02:32:57.000', '2019-09-14 02:32:57.000', NULL, ''),
	(33, '2米 x 1米', 7, '2019-09-14 02:33:39.000', '2019-09-14 02:33:39.000', NULL, ''),
	(34, 'L型 2米 + 0.8米', 7, '2019-09-14 02:34:12.000', '2019-09-14 02:34:12.000', NULL, ''),
	(35, '米黄色', 1, '2019-09-14 02:40:45.000', '2019-09-14 02:40:45.000', NULL, ''),
	(36, '海蓝色', 1, '2019-09-14 05:50:31.000', '2019-09-14 05:50:31.000', NULL, ''),
	(37, '象牙白', 1, '2019-09-14 05:50:42.000', '2019-09-14 05:50:42.000', NULL, ''),
	(38, '落地灯', 8, '2019-09-14 06:24:15.000', '2019-09-14 06:24:15.000', NULL, ''),
	(39, '台灯', 8, '2019-09-14 06:24:27.000', '2019-09-14 06:24:27.000', NULL, ''),
	(40, '抹茶绿', 1, '2019-09-14 06:37:13.000', '2019-09-14 06:37:13.000', NULL, ''),
	(41, '青草绿', 1, '2019-09-14 06:53:08.000', '2019-09-14 06:53:08.000', NULL, ''),
	(42, '青芒色', 1, '2019-09-15 18:02:01.000', '2019-09-15 18:02:01.000', NULL, ''),
	(43, '粉色', 1, '2019-09-15 18:02:16.000', '2019-09-15 18:02:16.000', NULL, ''),
	(44, '橘黄色', 1, '2019-09-18 03:32:43.000', '2019-09-18 03:32:43.000', NULL, ''),
	(45, '金属灰', 1, '2019-09-18 03:32:59.000', '2019-09-18 03:32:59.000', NULL, '');
/*!40000 ALTER TABLE `spec_value` ENABLE KEYS */;

-- 导出  表 cms.spu 结构
CREATE TABLE IF NOT EXISTS `spu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `subtitle` varchar(800) DEFAULT NULL,
  `category_id` int(10) unsigned NOT NULL,
  `root_category_id` int(11) DEFAULT NULL,
  `online` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `price` varchar(20) NOT NULL COMMENT '文本型价格，有时候SPU需要展示的是一个范围，或者自定义平均价格',
  `sketch_spec_id` int(10) unsigned DEFAULT NULL COMMENT '某种规格可以直接附加单品图片',
  `default_sku_id` int(11) DEFAULT NULL COMMENT '默认选中的sku',
  `img` varchar(255) DEFAULT NULL,
  `discount_price` varchar(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tags` varchar(30) DEFAULT NULL,
  `is_test` tinyint(3) unsigned DEFAULT '0',
  `spu_theme_img` json DEFAULT NULL,
  `for_theme_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.spu 的数据：~19 rows (大约)
DELETE FROM `spu`;
/*!40000 ALTER TABLE `spu` DISABLE KEYS */;
INSERT INTO `spu` (`id`, `title`, `subtitle`, `category_id`, `root_category_id`, `online`, `create_time`, `update_time`, `delete_time`, `price`, `sketch_spec_id`, `default_sku_id`, `img`, `discount_price`, `description`, `tags`, `is_test`, `spu_theme_img`, `for_theme_img`) VALUES
	(1, '青锋大碗', '大碗主要用来盛宽面，凡凡倾情推荐', 28, 27, 1, '2019-07-15 14:47:11.000', '2021-09-04 15:46:13.621', NULL, '12.99', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/wan1.jpg', '11.11', NULL, '林白推荐', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/wan1.jpg'),
	(2, '林间有风自营针织衫', '秋日冬款，浪漫满屋', 12, 2, 1, '2019-07-31 08:19:24.000', '2021-09-04 15:36:06.877', NULL, '77.00', 1, 2, 'http://127.0.0.1:8080/cms-pictures/spu/mao1.jpg', '62.00', NULL, '秋日冬款$浪漫满屋', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/mao1.jpg'),
	(3, '抹茶小橡皮', '小作文写错了，用它擦一擦', 32, 24, 1, '2019-09-16 09:55:51.000', '2021-09-04 15:00:44.691', NULL, '29.99', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/spu2.jpg', '17.00', NULL, '一飞推荐', 0, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/spu2.jpg'),
	(4, '印花桌布', '生活需要仪式感，吃饭也一样。桌旗+桌布给你绚烂的生命色彩', 26, 27, 1, '2019-07-30 06:26:33.000', '2021-09-04 15:43:10.914', NULL, '119.00', 5, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/bu1.jpg', '97.00', NULL, '风袖臻选', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/bu1.jpg'),
	(5, '七色针织衫', '女朋友不给你洗衣服？没关系，每天换一件。', 14, 2, 1, '2019-07-16 14:47:11.000', '2021-09-04 15:55:58.466', NULL, '349', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/shan1.jpg', '279', NULL, 'pedro推荐', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/shan1.jpg'),
	(6, 'Sleeve羊绒毛衣', 'Sleeve风袖说当季经典款', 14, 2, 1, '2019-08-01 08:19:24.000', '2021-09-04 16:02:03.040', NULL, '1799', 1, 5, 'http://127.0.0.1:8080/cms-pictures/spu/sleeve.jpg', '', NULL, '包邮$热门', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/sleeve.jpg'),
	(8, 'Ins复古碎花NoteBook', '林白默默的掏出小本本，将她说的话一次不漏的记了下来。', 32, 24, 1, '2019-09-15 05:00:21.000', '2021-09-04 15:21:56.877', NULL, '29.99', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/biji1.jpg', '27.8', NULL, '林白推荐', 0, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/biji1.jpg'),
	(10, '碳素墨水', '虽然我们早已不再使用钢笔书写，但钢笔在纸上划过的笔触永远是键盘无法替代的。一只钢笔+一瓶墨水在一个安静的午后，写写内心的故事。', 32, 24, 1, '2019-09-16 09:57:15.000', '2021-09-04 14:49:44.344', NULL, '80.00', NULL, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/spu1.jpg', '69.00', NULL, '', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/spu1.jpg'),
	(11, '飞行员墨镜', '戴起来像小李子', 36, 5, 1, '2019-08-07 22:47:05.000', '2021-09-04 15:31:57.211', NULL, '77.00', NULL, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/mojing1.jpg', NULL, NULL, NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/mojing1.jpg'),
	(12, '测试商品', '测试商品，可购买体验完整支付和订单流程', 38, 37, 1, '2019-08-25 19:03:03.000', '2021-09-04 16:07:27.076', NULL, '0.2', NULL, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/ceshi.jpg', NULL, NULL, '测试数据$可支付', 0, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/ceshi.jpg'),
	(13, '基克的聚合束带', '三色可选，加攻、加防、还能加血', 39, 5, 1, '2019-09-07 16:06:47.000', '2021-09-04 15:52:06.782', NULL, '279', NULL, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good8.jpg', NULL, NULL, NULL, 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good8.jpg'),
	(14, 'Ins 复古小夹子（Mini)', '静静的，享受时光的流逝', 32, 24, 1, '2019-09-16 09:54:47.000', '2021-09-04 15:15:40.867', NULL, '19.9', NULL, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/jiazi1.jpg', NULL, NULL, '三色可选', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/jiazi1.jpg'),
	(15, '多彩别针、回形针', '每盒70个，可爱多彩', 32, 24, 1, '2019-09-16 09:55:27.000', '2021-09-04 15:05:43.022', NULL, '24', 1, 25, 'http://127.0.0.1:8080/cms-pictures/spu/spu3.jpg', '19.9', NULL, '三色可选', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/spu3.jpg'),
	(23, '双色百褶裙', '秋冬新款，绽放女人心', 15, 2, 1, '2019-09-16 10:26:04.000', '2021-09-04 16:10:50.416', NULL, '1399', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/qun.jpg', NULL, NULL, '', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/spu/qun.jpg'),
	(24, '秋冬新款驼色大衣', '2020新款，暖暖过秋冬', 16, 2, 1, '2019-09-14 02:13:20.000', '2021-09-04 00:42:35.479', NULL, '2999', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good7.jpg', '2699', NULL, '经典单色', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good7.jpg'),
	(25, '复古双色沙发', '双色可选，经典青黄两色', 35, 4, 1, '2019-09-14 02:30:23.000', '2021-09-04 00:42:35.479', NULL, '3999', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good1.jpg', NULL, NULL, '复刻经典$双色可选', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good1.jpg'),
	(26, 'SemiConer柔质沙发', '窝在沙发上，一杯红酒配电影', 35, 4, 1, '2019-09-14 05:43:19.000', '2021-09-04 00:42:35.479', NULL, '4799', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good2.jpg', '4200', NULL, '', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good2.jpg'),
	(27, 'Mier双色靠椅', '安静的午后，一杯清茶，追忆似水年华。看清风浮动，看落日余晖', 35, 4, 1, '2019-09-09 02:26:12.000', '2021-09-04 00:42:35.479', NULL, '1299', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good4.jpg', NULL, NULL, '', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good4.jpg'),
	(28, 'Ins复古金色落地灯', 'Instagram复古台灯，就在此刻回到过去，找寻逝去的记忆', 23, 4, 1, '2019-09-14 06:19:12.000', '2021-09-04 00:42:35.479', NULL, '999', 8, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good3.jpg', NULL, NULL, 'Ins$复古流行', 1, NULL, 'http://127.0.0.1:8080/cms-pictures/goods/good3.jpg');
/*!40000 ALTER TABLE `spu` ENABLE KEYS */;

-- 导出  表 cms.spu_detail_img 结构
CREATE TABLE IF NOT EXISTS `spu_detail_img` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `img` varchar(255) NOT NULL,
  `spu_id` int(10) unsigned DEFAULT NULL,
  `sort` int(10) unsigned NOT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.spu_detail_img 的数据：~3 rows (大约)
DELETE FROM `spu_detail_img`;
/*!40000 ALTER TABLE `spu_detail_img` DISABLE KEYS */;
INSERT INTO `spu_detail_img` (`id`, `img`, `spu_id`, `sort`, `create_time`, `update_time`, `delete_time`) VALUES
	(24, 'http://127.0.0.1:8080/cms-pictures/spu/mao1.jpg', 2, 1, '2019-09-18 04:50:21.313', '2021-09-04 15:41:02.215', NULL),
	(25, 'http://127.0.0.1:8080/cms-pictures/sku/mao1.jpg', 2, 3, '2019-09-18 04:50:21.313', '2021-09-04 15:40:52.178', NULL),
	(26, 'http://127.0.0.1:8080/cms-pictures/sku/mao2.jpg', 2, 4, '2019-09-18 04:50:21.313', '2021-09-04 15:40:57.768', NULL);
/*!40000 ALTER TABLE `spu_detail_img` ENABLE KEYS */;

-- 导出  表 cms.spu_img 结构
CREATE TABLE IF NOT EXISTS `spu_img` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `img` varchar(255) DEFAULT NULL,
  `spu_id` int(10) unsigned DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.spu_img 的数据：~33 rows (大约)
DELETE FROM `spu_img`;
/*!40000 ALTER TABLE `spu_img` DISABLE KEYS */;
INSERT INTO `spu_img` (`id`, `img`, `spu_id`, `delete_time`, `update_time`, `create_time`) VALUES
	(11, 'http://127.0.0.1:8080/cms-pictures/goods/mojing1.jpg', 11, NULL, '2021-09-04 15:32:23.712', '2019-08-08 03:36:56.000'),
	(15, 'http://127.0.0.1:8080/cms-pictures/goods/good8.jpg', 13, NULL, '2021-09-04 15:58:31.574', '2019-09-07 16:33:59.000'),
	(19, 'http://127.0.0.1:8080/cms-pictures/spu/jiazi1.jpg', 14, NULL, '2021-09-04 15:16:03.925', '2019-09-07 16:33:59.000'),
	(20, 'http://127.0.0.1:8080/cms-pictures/spu/jiazi2.jpg', 14, NULL, '2021-09-04 15:16:10.976', '2019-09-07 16:33:59.000'),
	(30, 'http://127.0.0.1:8080/cms-pictures/sku/zhen1.jpg', 15, NULL, '2021-09-04 15:09:53.777', '2019-09-07 16:33:59.000'),
	(31, 'http://127.0.0.1:8080/cms-pictures/sku/zhen2.jpg', 15, NULL, '2021-09-04 15:09:57.880', '2019-09-07 16:33:59.000'),
	(41, NULL, 16, NULL, '2020-02-25 15:07:01.461', '2019-09-11 21:16:57.000'),
	(42, NULL, 16, NULL, '2020-02-25 15:07:01.461', '2019-09-11 21:16:57.000'),
	(43, 'http://127.0.0.1:8080/cms-pictures/spu/sleeve.jpg', 6, NULL, '2021-09-04 16:02:14.342', '2019-09-12 18:06:59.000'),
	(44, 'http://127.0.0.1:8080/cms-pictures/sku/sleeve.jpg', 6, NULL, '2021-09-04 16:02:20.030', '2019-09-12 18:06:59.000'),
	(88, 'http://127.0.0.1:8080/cms-pictures/sku/h1.jpg', 25, NULL, '2021-09-04 15:25:03.416', '2019-09-14 02:37:42.000'),
	(89, 'http://127.0.0.1:8080/cms-pictures/sku/h2.jpg', 25, NULL, '2021-09-04 15:24:58.658', '2019-09-14 02:37:42.000'),
	(107, 'http://127.0.0.1:8080/cms-pictures/sku/xiangpi1.jpg', 3, NULL, '2021-09-04 14:59:39.744', '2019-09-14 06:34:25.000'),
	(108, 'http://127.0.0.1:8080/cms-pictures/sku/xiangpi2.jpg', 3, NULL, '2021-09-04 14:59:47.134', '2019-09-14 06:34:25.000'),
	(124, 'http://127.0.0.1:8080/cms-pictures/goods/ceshi.jpg', 12, NULL, '2021-09-04 16:34:17.468', '2019-09-14 07:24:14.000'),
	(126, 'http://127.0.0.1:8080/cms-pictures/goods/bu1.jpg', 4, NULL, '2021-09-04 15:43:17.954', '2019-09-14 19:15:48.000'),
	(138, 'http://127.0.0.1:8080/cms-pictures/spu/wan1.jpg', 1, NULL, '2021-09-04 15:46:16.946', '2019-09-14 23:01:57.000'),
	(139, 'http://127.0.0.1:8080/cms-pictures/spu/wan2.jpg', 1, NULL, '2021-09-04 15:46:23.816', '2019-09-14 23:01:57.000'),
	(141, 'http://127.0.0.1:8080/cms-pictures/goods/good3.jpg', 28, NULL, '2021-09-04 15:23:51.291', '2019-09-14 23:06:56.000'),
	(150, 'http://127.0.0.1:8080/cms-pictures/goods/good2.jpg', 26, NULL, '2021-09-04 15:23:14.521', '2019-09-14 23:07:16.000'),
	(151, 'http://127.0.0.1:8080/cms-pictures/spu/shan1.jpg', 5, NULL, '2021-09-04 15:56:01.096', '2019-09-15 04:53:29.000'),
	(152, 'http://127.0.0.1:8080/cms-pictures/sku/shan1.jpg', 5, NULL, '2021-09-04 15:56:07.915', '2019-09-15 04:53:29.000'),
	(153, 'http://127.0.0.1:8080/cms-pictures/sku/shan2.jpg', 5, NULL, '2021-09-04 15:57:10.859', '2019-09-15 04:53:29.000'),
	(161, 'http://127.0.0.1:8080/cms-pictures/spu/spu1.jpg', 10, NULL, '2021-09-04 14:53:00.122', '2019-09-15 17:50:36.000'),
	(165, 'http://127.0.0.1:8080/cms-pictures/spu/mao1.jpg', 2, NULL, '2021-09-04 15:36:16.312', '2019-09-15 17:57:23.000'),
	(166, 'http://127.0.0.1:8080/cms-pictures/sku/mao1.jpg', 2, NULL, '2021-09-04 15:36:25.261', '2019-09-15 17:57:23.000'),
	(167, 'http://127.0.0.1:8080/cms-pictures/sku/mao2.jpg', 2, NULL, '2021-09-04 15:36:32.080', '2019-09-15 17:57:23.000'),
	(172, 'http://127.0.0.1:8080/cms-pictures/goods/good7.jpg', 24, NULL, '2021-09-04 15:27:50.577', '2019-09-16 01:02:58.000'),
	(177, 'http://127.0.0.1:8080/cms-pictures/spu/biji1.jpg', 8, NULL, '2021-09-04 15:22:03.800', '2019-09-16 01:03:14.000'),
	(179, 'http://127.0.0.1:8080/cms-pictures/spu/biji2.jpg', 8, NULL, '2021-09-04 15:22:09.879', '2019-09-16 01:03:14.000'),
	(186, 'http://127.0.0.1:8080/cms-pictures/spu/qun.jpg', 23, NULL, '2021-09-04 16:10:59.686', '2019-09-16 01:05:21.000'),
	(187, 'http://127.0.0.1:8080/cms-pictures/sku/qun.jpg', 23, NULL, '2021-09-04 16:11:08.771', '2019-09-16 01:05:21.000'),
	(190, 'http://127.0.0.1:8080/cms-pictures/goods/good4.jpg', 27, NULL, '2021-09-04 15:29:21.648', '2019-09-16 01:05:46.000');
/*!40000 ALTER TABLE `spu_img` ENABLE KEYS */;

-- 导出  表 cms.spu_key 结构
CREATE TABLE IF NOT EXISTS `spu_key` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `spu_id` int(10) unsigned NOT NULL,
  `spec_key_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.spu_key 的数据：~26 rows (大约)
DELETE FROM `spu_key`;
/*!40000 ALTER TABLE `spu_key` DISABLE KEYS */;
INSERT INTO `spu_key` (`id`, `spu_id`, `spec_key_id`) VALUES
	(1, 3, 1),
	(3, 2, 1),
	(7, 1, 1),
	(8, 1, 2),
	(9, 15, 1),
	(10, 15, 2),
	(11, 16, 2),
	(13, 16, 1),
	(14, 23, 1),
	(15, 23, 4),
	(16, 24, 1),
	(17, 24, 4),
	(18, 25, 1),
	(19, 25, 7),
	(20, 26, 1),
	(21, 26, 7),
	(22, 27, 1),
	(23, 28, 1),
	(24, 28, 8),
	(25, 3, 6),
	(26, 8, 1),
	(27, 8, 6),
	(28, 5, 1),
	(29, 5, 4),
	(30, 2, 3),
	(31, 2, 4);
/*!40000 ALTER TABLE `spu_key` ENABLE KEYS */;

-- 导出  表 cms.spu_tag 结构
CREATE TABLE IF NOT EXISTS `spu_tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `spu_id` int(10) unsigned NOT NULL,
  `tag_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.spu_tag 的数据：~5 rows (大约)
DELETE FROM `spu_tag`;
/*!40000 ALTER TABLE `spu_tag` DISABLE KEYS */;
INSERT INTO `spu_tag` (`id`, `spu_id`, `tag_id`) VALUES
	(1, 2, 5),
	(2, 2, 1),
	(3, 2, 12),
	(4, 12, 13),
	(5, 12, 14);
/*!40000 ALTER TABLE `spu_tag` ENABLE KEYS */;

-- 导出  表 cms.tag 结构
CREATE TABLE IF NOT EXISTS `tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(30) NOT NULL COMMENT '中文限制6个，英文限制12个，由逻辑层控制',
  `description` varchar(255) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `highlight` tinyint(4) unsigned DEFAULT '0',
  `type` tinyint(3) unsigned DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.tag 的数据：~18 rows (大约)
DELETE FROM `tag`;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`id`, `title`, `description`, `update_time`, `delete_time`, `create_time`, `highlight`, `type`) VALUES
	(1, 'Sleeve', '', '2019-09-14 07:35:17.000', NULL, '2019-07-30 04:10:10.000', 1, 1),
	(3, '椅', NULL, '2019-09-14 07:37:52.000', NULL, '2019-07-30 04:11:06.000', NULL, 1),
	(4, '非卖品', NULL, '2019-08-01 18:45:52.000', NULL, '2019-08-01 18:45:52.000', NULL, 1),
	(5, '测试数据', NULL, '2019-09-16 02:19:38.000', NULL, '2019-08-01 18:46:01.000', 1, 1),
	(10, '江浙沪不包邮', NULL, '2019-09-16 02:42:58.000', NULL, '2019-08-01 18:49:02.000', NULL, 1),
	(11, '百褶裙', NULL, '2019-09-14 07:37:52.000', NULL, '2019-08-01 18:50:26.000', 1, 1),
	(15, '秋冬', NULL, '2019-09-14 07:36:43.000', NULL, '2019-09-12 18:06:59.000', 1, 1),
	(17, '落地灯', NULL, '2019-09-14 07:35:17.000', NULL, '2019-09-14 02:13:20.000', NULL, 1),
	(18, '复刻', NULL, '2019-09-16 02:43:03.000', NULL, '2019-09-14 02:37:42.000', NULL, 1),
	(19, '双色可选', NULL, '2019-09-14 02:37:42.000', NULL, '2019-09-14 02:37:42.000', NULL, 1),
	(20, 'Ins', NULL, '2019-09-14 06:19:12.000', NULL, '2019-09-14 06:19:12.000', NULL, 1),
	(21, '复古', NULL, '2019-09-14 07:37:52.000', NULL, '2019-09-14 06:19:12.000', 1, 1),
	(22, '林间有风', NULL, '2019-09-16 02:42:44.000', NULL, '2019-09-14 06:34:25.000', 1, 1),
	(23, '灯', NULL, '2019-09-14 07:36:43.000', NULL, '2019-09-14 06:50:11.000', NULL, 1),
	(25, '可支付', NULL, '2019-09-14 07:24:14.000', NULL, '2019-09-14 07:24:14.000', NULL, 1),
	(26, '风袖臻选', NULL, '2019-09-16 02:36:35.000', NULL, '2019-09-14 19:15:48.000', 0, 1),
	(28, '林白', NULL, '2019-09-16 02:44:26.000', NULL, '2019-09-14 23:01:25.000', NULL, 1),
	(30, '包邮', NULL, '2019-09-15 17:55:04.000', NULL, '2019-09-15 17:55:04.000', NULL, 1);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;

-- 导出  表 cms.theme 结构
CREATE TABLE IF NOT EXISTS `theme` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `tpl_name` varchar(30) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  `entrance_img` varchar(255) DEFAULT NULL,
  `extend` varchar(255) DEFAULT NULL,
  `internal_top_img` varchar(255) DEFAULT NULL,
  `title_img` varchar(255) DEFAULT NULL,
  `online` tinyint(3) unsigned DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.theme 的数据：~8 rows (大约)
DELETE FROM `theme`;
/*!40000 ALTER TABLE `theme` DISABLE KEYS */;
INSERT INTO `theme` (`id`, `title`, `description`, `name`, `create_time`, `tpl_name`, `update_time`, `delete_time`, `entrance_img`, `extend`, `internal_top_img`, `title_img`, `online`) VALUES
	(1, '清凉一夏，折扣季', '秋天是金色的。麦穗是金色的，秋阳是金色的。虽然冬快至，但宜人的温度总是让我们心情愉快#我们为您精选了一系列秋冬折扣商品，慢慢挑选吧~', 't-1', '2019-07-18 07:10:59.000', 'janna', '2021-08-27 12:23:20.655', NULL, 'http://127.0.0.1:8080/cms-pictures/theme/theme1.jpg', NULL, NULL, NULL, 1),
	(4, '每周上新', '风袖`每周上新`活动#每周都有一款折扣商品，每周都有适合你的唯一专属#有Ins复古风装饰；金属CD唱片夹；每周来逛逛，找到你所喜爱的东西', 't-2', '2019-07-30 00:00:14.000', NULL, '2021-09-04 00:10:32.001', NULL, NULL, NULL, 'http://127.0.0.1:8080/cms-pictures/banner/banner1.jpg', NULL, 1),
	(5, '风袖甄选', '甄选', 't-3', '2019-07-30 17:20:23.000', 'diana', '2021-09-04 00:37:06.056', NULL, 'http://127.0.0.1:8080/cms-pictures/theme/theme3.jpg', NULL, 'http://127.0.0.1:8080/cms-pictures/theme/theme3.jpg', NULL, 1),
	(6, '时尚穿搭', '帅点才有女朋友', 't-4', '2019-08-01 02:43:18.000', 'irelia', '2021-09-04 00:47:00.725', NULL, 'http://127.0.0.1:8080/cms-pictures/theme/theme5.jpg', NULL, 'http://127.0.0.1:8080/cms-pictures/theme/theme5.jpg', NULL, 1),
	(7, '热卖好评', '林白选的那一定是最好的', 't-5', '2019-08-09 07:19:37.000', 'camille', '2020-02-25 15:07:01.465', NULL, NULL, NULL, NULL, NULL, 1),
	(8, '热门推荐', NULL, 't-6', '2019-09-10 11:43:06.000', 'camille', '2020-02-25 15:07:01.465', NULL, NULL, NULL, NULL, NULL, 1),
	(10, NULL, NULL, NULL, '2019-10-10 16:59:43.363', NULL, '2019-10-10 16:59:43.363', NULL, NULL, NULL, NULL, NULL, 1),
	(11, NULL, NULL, NULL, '2020-02-06 21:49:43.994', NULL, '2020-02-06 21:49:43.994', NULL, NULL, NULL, NULL, NULL, 1);
/*!40000 ALTER TABLE `theme` ENABLE KEYS */;

-- 导出  表 cms.theme_spu 结构
CREATE TABLE IF NOT EXISTS `theme_spu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `theme_id` int(10) unsigned NOT NULL,
  `spu_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.theme_spu 的数据：~47 rows (大约)
DELETE FROM `theme_spu`;
/*!40000 ALTER TABLE `theme_spu` DISABLE KEYS */;
INSERT INTO `theme_spu` (`id`, `theme_id`, `spu_id`) VALUES
	(22, 6, 23),
	(23, 6, 24),
	(24, 6, 5),
	(25, 6, 6),
	(39, 7, 8),
	(41, 7, 23),
	(42, 7, 24),
	(43, 7, 28),
	(44, 7, 27),
	(45, 7, 25),
	(46, 7, 13),
	(47, 7, 14),
	(48, 6, 28),
	(49, 6, 27),
	(50, 6, 26),
	(51, 6, 8),
	(54, 8, 27),
	(55, 8, 23),
	(56, 8, 6),
	(57, 8, 25),
	(58, 8, 27),
	(59, 8, 26),
	(60, 1, 25),
	(61, 1, 28),
	(62, 1, 23),
	(63, 1, 27),
	(64, 1, 8),
	(65, 1, 14),
	(66, 1, 3),
	(67, 1, 26),
	(70, 8, 8),
	(71, 4, 25),
	(75, 4, 26),
	(76, 4, 28),
	(83, 4, 27),
	(84, 4, 23),
	(85, 4, 8),
	(86, 4, 24),
	(87, 4, 13),
	(88, 4, 6),
	(89, 4, 5),
	(90, 4, 3),
	(91, 5, 23),
	(92, 5, 8),
	(93, 5, 27),
	(94, 5, 24),
	(100, 8, 28);
/*!40000 ALTER TABLE `theme_spu` ENABLE KEYS */;

-- 导出  表 cms.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) DEFAULT NULL,
  `nickname` varchar(60) DEFAULT NULL,
  `unify_uid` int(10) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(30) DEFAULT NULL,
  `wx_profile` json DEFAULT NULL,
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_openid` (`openid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.user 的数据：~22 rows (大约)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `openid`, `nickname`, `unify_uid`, `email`, `password`, `mobile`, `wx_profile`, `create_time`, `update_time`, `delete_time`) VALUES
	(1, NULL, NULL, NULL, '123@qq.com', '45678123123', NULL, 'null', '2019-07-13 08:06:35.000', '2019-07-13 08:06:35.000', NULL),
	(2, NULL, NULL, NULL, '1236@qq.com', '45678123123', NULL, 'null', '2019-07-13 08:06:55.000', '2019-07-13 08:06:55.000', NULL),
	(3, NULL, NULL, NULL, '12367@qq.com', '0e40a584386c3f52bee84639fa2608ce', NULL, 'null', '2019-07-13 11:08:20.000', '2019-07-13 11:08:20.000', NULL),
	(4, NULL, NULL, NULL, '123667@qq.com', '0e40a584386c3f52bee84639fa2608ce', NULL, 'null', '2019-07-13 11:20:58.000', '2019-07-13 11:20:58.000', NULL),
	(16, NULL, '7七月', NULL, NULL, NULL, NULL, '{"city": "Haidian", "gender": 1, "country": "China", "language": "zh_CN", "nickName": "7七月", "province": "Beijing", "avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJRgMw7FHDF5Etxb54Qh743fib2ZfO40U7JWyBNcphdDRyaBb1eWXV0NicJtDL59DGGAY8Bf8weqSgg/132"}', '2019-08-19 09:52:31.000', '2020-02-25 15:07:01.469', NULL),
	(17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2019-08-21 15:14:23.000', '2020-02-25 15:07:01.469', NULL),
	(18, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-11 21:46:46.000', '2020-02-25 15:07:01.469', NULL),
	(19, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-11 21:46:47.000', '2020-02-25 15:07:01.469', NULL),
	(20, NULL, 'Jokky💫', NULL, NULL, NULL, NULL, '{"city": "Nantong", "gender": 1, "country": "China", "language": "zh_HK", "nickName": "Jokky💫", "province": "Jiangsu", "avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIW4BKAJ072RS11HnwVDR4OEIlt3fr61JyaucribV8KUXWEmYvJ2VUzyYShdibrT5PPP95Y8DKLK2Lg/132"}', '2019-09-11 22:12:25.000', '2020-02-25 15:07:01.469', NULL),
	(21, NULL, 'Colorful3', NULL, NULL, NULL, NULL, '{"city": "", "gender": 1, "country": "Fiji Islands", "language": "zh_CN", "nickName": "Colorful3", "province": "", "avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobqRnYzViaMmIt9gAKCIBkib7pF9lcsAmaFfzKFU0MCr5XcLfLzKIckyib3PSFyqMP2ksq3ibFibxb05A/132"}', '2019-09-11 22:12:37.000', '2020-02-25 15:07:01.469', NULL),
	(22, NULL, '一飞同学@Evan', NULL, NULL, NULL, NULL, '{"city": "Suzhou", "gender": 1, "country": "China", "language": "zh_CN", "nickName": "一飞同学@Evan", "province": "Jiangsu", "avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83epwgf48jZfAzOz78zxQOqtWD9SuQNgIUdhficfteoMl8MBcTUOJWTfqTDbYdaEheGWjPp9cSD5Uiclw/132"}', '2019-09-11 22:13:01.000', '2020-02-25 15:07:01.469', NULL),
	(23, NULL, '流乔', NULL, NULL, NULL, NULL, '{"city": "", "gender": 0, "country": "", "language": "zh_CN", "nickName": "流乔", "province": "", "avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/aN5d8M5StwcQLOic6FkLYrHcpxdaNR7CLfwfBOl9ThCesTjUAVAnR2WyE1sTBficepZ526KAn98bpRpJ35rnGElQ/132"}', '2019-09-11 22:18:23.000', '2020-02-25 15:07:01.469', NULL),
	(24, NULL, 'pedro', NULL, NULL, NULL, NULL, '{"city": "Wuhan", "gender": 1, "country": "China", "language": "zh_CN", "nickName": "pedro", "province": "Hubei", "avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/So1cw4tZWziadtWHyqALcSSUY2dOjmZ669eARYZoJVMlSSBMzqGFlekHHic0a2MfFCYicURXTghib23mmzjYA2BjLg/132"}', '2019-09-11 22:21:08.000', '2020-02-25 15:07:01.469', NULL),
	(25, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-12 05:31:35.000', '2020-02-25 15:07:01.469', NULL),
	(26, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-12 05:31:48.000', '2020-02-25 15:07:01.469', NULL),
	(27, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-12 05:59:03.000', '2020-02-25 15:07:01.469', NULL),
	(28, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-12 06:00:18.000', '2020-02-25 15:07:01.469', NULL),
	(29, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-12 06:55:27.000', '2020-02-25 15:07:01.469', NULL),
	(30, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-12 17:48:25.000', '2020-02-25 15:07:01.469', NULL),
	(31, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-15 18:10:08.000', '2020-02-25 15:07:01.469', NULL),
	(32, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-16 03:24:38.000', '2020-02-25 15:07:01.469', NULL),
	(33, NULL, NULL, NULL, NULL, NULL, NULL, 'null', '2019-09-16 04:15:19.000', '2020-02-25 15:07:01.469', NULL),
	(34, 'oBfjq4p7h0kuQEV4_mYRtNj1FX8s', NULL, NULL, NULL, NULL, NULL, 'null', '2021-09-02 00:24:01.574', '2021-09-02 00:24:01.574', NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 导出  表 cms.user_coupon 结构
CREATE TABLE IF NOT EXISTS `user_coupon` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `coupon_id` int(10) unsigned NOT NULL,
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '1:未使用，2：已使用， 3：已过期',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
  `order_id` int(10) unsigned DEFAULT NULL,
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_user_coupon` (`user_id`,`coupon_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  cms.user_coupon 的数据：~4 rows (大约)
DELETE FROM `user_coupon`;
/*!40000 ALTER TABLE `user_coupon` DISABLE KEYS */;
INSERT INTO `user_coupon` (`id`, `user_id`, `coupon_id`, `status`, `create_time`, `order_id`, `update_time`) VALUES
	(1, 34, 3, 2, '2021-09-02 16:15:49.236', 7, '2021-09-03 14:38:31.097'),
	(2, 34, 4, 1, '2021-09-02 17:40:38.040', NULL, '2021-09-02 17:40:38.063'),
	(3, 34, 7, 1, '2021-09-02 17:40:40.840', NULL, '2021-09-02 17:40:40.860'),
	(4, 34, 11, 1, '2021-09-02 17:40:41.451', NULL, '2021-09-02 17:40:41.472');
/*!40000 ALTER TABLE `user_coupon` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
