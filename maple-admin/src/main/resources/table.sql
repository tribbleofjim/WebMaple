SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for maple_source
-- ----------------------------
DROP TABLE IF EXISTS `maple_source`;
CREATE TABLE `maple_source` (
  `source_name` varchar(255) NOT NULL,
  `source_type` enum('0','1','2','3') NOT NULL DEFAULT '0' COMMENT '枚举值，0-服务器，1-mysql数据库，2-mongodb数据库，3-redis数据库',
  `ip` varchar(255) NOT NULL COMMENT '资源ip',
  `account` varchar(255) NOT NULL COMMENT '访问资源的账户',
  `pass` varchar(255) NOT NULL COMMENT '访问资源的账户密码',
  PRIMARY KEY (`source_name`,`source_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for maple_template
-- ----------------------------
DROP TABLE IF EXISTS `maple_template`;
CREATE TABLE `maple_template` (
  `html` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `template_name` varchar(255) NOT NULL,
  `form_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`template_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for maple_user_source
-- ----------------------------
DROP TABLE IF EXISTS `maple_user_source`;
CREATE TABLE `maple_user_source` (
  `phone` varchar(255) NOT NULL,
  `source_name` varchar(255) NOT NULL,
  `source_type` enum('0','1','2','3') NOT NULL DEFAULT '0' COMMENT '0-服务器，1-mysql，2-mongodb，3-redis',
  PRIMARY KEY (`phone`,`source_name`,`source_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for maple_spider
-- ----------------------------
DROP TABLE IF EXISTS `maple_spider`;
CREATE TABLE `maple_spider` (
  `worker` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `urls` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `thread_num` int(11) DEFAULT NULL,
  `processor` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `downloader` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `pipeline` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`worker`,`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for maple_message
-- ----------------------------
DROP TABLE IF EXISTS `maple_message`;
CREATE TABLE `maple_message` (
  `from_user` varchar(255) NOT NULL,
  `to_user` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `valid` binary(0) DEFAULT NULL,
  PRIMARY KEY (`from_user`,`to_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for maple_user
-- ----------------------------
DROP TABLE IF EXISTS `maple_user`;
CREATE TABLE `maple_user` (
  `phone` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `auth` enum('0','1') DEFAULT '1' COMMENT '0-管理员账号，1-普通用户账号',
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Records of maple_user
-- ----------------------------
BEGIN;
INSERT INTO `maple_user` VALUES ('123456', 'admin', 'admin', '0');
COMMIT;
