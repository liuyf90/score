/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : score

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-05-30 09:34:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  KEY `FK9ydhxbq67a3m0ek560r2fq38g` (`owner_id`),
  CONSTRAINT `FK9ydhxbq67a3m0ek560r2fq38g` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '漠河智慧城市', '3');
INSERT INTO `project` VALUES ('2', '呼玛智慧城市', '4');
INSERT INTO `project` VALUES ('3', '数字城管', '2');
INSERT INTO `project` VALUES ('4', '智慧社区', '5');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '用户', 'ROLE_USER');
INSERT INTO `role` VALUES ('2', '组长', 'ROLE_OWNER');
INSERT INTO `role` VALUES ('3', '管理员', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `task_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `b_date` datetime DEFAULT NULL,
  `c_date` datetime DEFAULT NULL,
  `e_date` datetime DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `task_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `finish` int(1) NOT NULL DEFAULT '0',
  `f_date` datetime DEFAULT NULL,
  `check_date` datetime DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `FKk8qrwowg31kx7hp93sru1pdqa` (`project_id`),
  KEY `FKtng16j7iinb49dqvfbfe0wccm` (`order_id`),
  CONSTRAINT `FKk8qrwowg31kx7hp93sru1pdqa` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `FKtng16j7iinb49dqvfbfe0wccm` FOREIGN KEY (`order_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of task
-- ----------------------------

-- ----------------------------
-- Table structure for task_user
-- ----------------------------
DROP TABLE IF EXISTS `task_user`;
CREATE TABLE `task_user` (
  `task_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  KEY `FKs46ejm4kitq56yd498a3fnr19` (`user_id`),
  KEY `FKd1fn28rqhh1ku21jx7hcksvh9` (`task_id`),
  CONSTRAINT `FKd1fn28rqhh1ku21jx7hcksvh9` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FKs46ejm4kitq56yd498a3fnr19` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of task_user
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sts` int(11) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '528726', '1', 'liuyf');
INSERT INTO `user` VALUES ('2', '123456', '1', 'wangyt');
INSERT INTO `user` VALUES ('3', '123456', '1', 'zhaocz');
INSERT INTO `user` VALUES ('4', '123456', '1', 'wangs');
INSERT INTO `user` VALUES ('5', '123456', '1', 'jiangw');
INSERT INTO `user` VALUES ('6', '123456', '1', 'hanh');
INSERT INTO `user` VALUES ('7', '123456', '1', 'guoxm');
INSERT INTO `user` VALUES ('8', '123456', '1', 'wangc');
INSERT INTO `user` VALUES ('9', '123456', '1', 'liuk');
INSERT INTO `user` VALUES ('10', '123456', '1', 'guzj');
INSERT INTO `user` VALUES ('11', '123456', '1', 'guott');
INSERT INTO `user` VALUES ('12', '123456', '1', 'lix');
INSERT INTO `user` VALUES ('13', '123456', '1', 'lib');
INSERT INTO `user` VALUES ('14', '123456', '1', 'zhangzp');
INSERT INTO `user` VALUES ('15', '123456', '1', 'yangyx');
INSERT INTO `user` VALUES ('16', '123456', '1', 'mengx');
INSERT INTO `user` VALUES ('17', '123456', '1', 'lius');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '1');
INSERT INTO `user_role` VALUES ('1', '2');
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('3', '1');
INSERT INTO `user_role` VALUES ('3', '2');
INSERT INTO `user_role` VALUES ('4', '1');
INSERT INTO `user_role` VALUES ('4', '2');
INSERT INTO `user_role` VALUES ('5', '1');
INSERT INTO `user_role` VALUES ('6', '1');
INSERT INTO `user_role` VALUES ('7', '1');
INSERT INTO `user_role` VALUES ('8', '1');
INSERT INTO `user_role` VALUES ('9', '1');
INSERT INTO `user_role` VALUES ('10', '1');
INSERT INTO `user_role` VALUES ('11', '1');
INSERT INTO `user_role` VALUES ('12', '1');
INSERT INTO `user_role` VALUES ('13', '1');
INSERT INTO `user_role` VALUES ('14', '1');
INSERT INTO `user_role` VALUES ('15', '1');
INSERT INTO `user_role` VALUES ('15', '2');
INSERT INTO `user_role` VALUES ('16', '1');
INSERT INTO `user_role` VALUES ('16', '2');
INSERT INTO `user_role` VALUES ('17', '1');
INSERT INTO `user_role` VALUES ('17', '2');
