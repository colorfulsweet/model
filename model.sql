/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50168
Source Host           : localhost:3306
Source Database       : model

Target Server Type    : MYSQL
Target Server Version : 50168
File Encoding         : 65001

Date: 2016-02-15 23:38:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_dict`
-- ----------------------------
DROP TABLE IF EXISTS `s_dict`;
CREATE TABLE `s_dict` (
  `ID` varchar(32) NOT NULL,
  `DICT_CODE` varchar(50) DEFAULT NULL COMMENT '字典编码',
  `DICT_NAME` varchar(255) DEFAULT NULL COMMENT '字典名称',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_dict
-- ----------------------------
INSERT INTO `s_dict` VALUES ('40289ff152b6c0870152b6ca76940001', 'd_userStatus', '用户状态', '表示该用户当前是否可用');

-- ----------------------------
-- Table structure for `s_dict_clause`
-- ----------------------------
DROP TABLE IF EXISTS `s_dict_clause`;
CREATE TABLE `s_dict_clause` (
  `ID` varchar(32) NOT NULL,
  `CLAUSE_CODE` varchar(50) DEFAULT NULL COMMENT '字典条目编码',
  `CLAUSE_NAME` varchar(255) DEFAULT NULL COMMENT '字典条目名称',
  `DICT_ID` varchar(32) DEFAULT NULL,
  `INDEX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK60F67C2C86674166` (`DICT_ID`),
  CONSTRAINT `FK60F67C2C86674166` FOREIGN KEY (`DICT_ID`) REFERENCES `s_dict` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_dict_clause
-- ----------------------------
INSERT INTO `s_dict_clause` VALUES ('40289ff152b6c0870152b6cab1030002', 'true', '可用', '40289ff152b6c0870152b6ca76940001', '0');
INSERT INTO `s_dict_clause` VALUES ('40289ff152b6c0870152b6cad6c70003', 'false', '禁用', '40289ff152b6c0870152b6ca76940001', '1');

-- ----------------------------
-- Table structure for `s_menu`
-- ----------------------------
DROP TABLE IF EXISTS `s_menu`;
CREATE TABLE `s_menu` (
  `ID` varchar(32) NOT NULL,
  `MENU_NAME` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `URL` varchar(255) DEFAULT NULL COMMENT 'URL地址',
  `ICON` varchar(50) DEFAULT NULL COMMENT '图标编码',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_menu
-- ----------------------------
INSERT INTO `s_menu` VALUES ('1001', '字典管理', null, 'book', null);
INSERT INTO `s_menu` VALUES ('1002', '系统管理', null, 'cog', null);
INSERT INTO `s_menu` VALUES ('40289ff152e58f990152e59020870001', 'RUOK', 'cmwo/lll.html', 'adjust', '噜啦啦');

-- ----------------------------
-- Table structure for `s_role`
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `ID` varchar(32) NOT NULL,
  `ROLE_NAME` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `CREATE_TIME` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES ('100', '系统管理员', '2016-01-07');

-- ----------------------------
-- Table structure for `s_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `s_role_menu`;
CREATE TABLE `s_role_menu` (
  `ROLE_ID` varchar(32) NOT NULL,
  `MENU_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`MENU_ID`,`ROLE_ID`),
  KEY `FKA946169C5C4B74C6` (`MENU_ID`),
  KEY `FKA946169C75BA0966` (`ROLE_ID`),
  CONSTRAINT `FKA946169C5C4B74C6` FOREIGN KEY (`MENU_ID`) REFERENCES `s_menu` (`ID`),
  CONSTRAINT `FKA946169C75BA0966` FOREIGN KEY (`ROLE_ID`) REFERENCES `s_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_role_menu
-- ----------------------------
INSERT INTO `s_role_menu` VALUES ('100', '1001');
INSERT INTO `s_role_menu` VALUES ('100', '1002');

-- ----------------------------
-- Table structure for `s_submenu`
-- ----------------------------
DROP TABLE IF EXISTS `s_submenu`;
CREATE TABLE `s_submenu` (
  `ID` varchar(32) NOT NULL,
  `SUBMENU_NAME` varchar(255) DEFAULT NULL COMMENT '子菜单名称',
  `URL` varchar(255) DEFAULT NULL COMMENT 'URL地址',
  `REMARK` text COMMENT '备注',
  `PARENT_MENU_ID` varchar(32) DEFAULT NULL,
  `INDEX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK5A36E3936B6FDA31` (`PARENT_MENU_ID`),
  CONSTRAINT `FK5A36E3936B6FDA31` FOREIGN KEY (`PARENT_MENU_ID`) REFERENCES `s_menu` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_submenu
-- ----------------------------
INSERT INTO `s_submenu` VALUES ('1', '数据字典', 'admin/dictManage.html', null, '1001', '0');
INSERT INTO `s_submenu` VALUES ('2', '菜单管理', 'admin/menuManage.html', null, '1002', '0');
INSERT INTO `s_submenu` VALUES ('3', '角色管理', 'admin/roleManage.html', null, '1002', '1');
INSERT INTO `s_submenu` VALUES ('4', '用户管理', 'admin/userManage.html', null, '1002', '2');

-- ----------------------------
-- Table structure for `s_user`
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `ID` varchar(32) NOT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL COMMENT '用户名',
  `REAL_NAME` varchar(255) DEFAULT NULL COMMENT '昵称',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT '密码(SHA-256加密)',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `E_MAIL` varchar(255) DEFAULT NULL COMMENT '电子邮箱',
  `TEL` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `STATUS` bit(1) DEFAULT NULL COMMENT '状态(0禁用 , 1可用)',
  `ROLE_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9304D83775BA0966` (`ROLE_ID`),
  CONSTRAINT `FK9304D83775BA0966` FOREIGN KEY (`ROLE_ID`) REFERENCES `s_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES ('402880ea52271d6c0152271d6da80001', 'test', '测试', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '2016-01-07 18:06:21', null, null, '', '100');
