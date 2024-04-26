/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : easy_test

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2024-04-26 13:24:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(10) DEFAULT NULL COMMENT '用户名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `birthday` date DEFAULT NULL COMMENT '出生年月',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别 0:女 1:男',
  `position` tinyint(1) DEFAULT NULL COMMENT '职位0:程序员 1:测试  2:产品经理',
  `roles` varchar(20) DEFAULT NULL COMMENT '角色 0:普通用户 1:组长 2:经理 3:管理员 可多选',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10026 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('10000', '张三', '18666666666', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', '1', '1', '0,1', '2023-07-11 09:11:47');
INSERT INTO `user_info` VALUES ('10008', '李四', '18666666662', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', '0', '1', '0,1,2', '2023-07-11 09:11:47');
INSERT INTO `user_info` VALUES ('10009', '王五', '18666666663', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', '1', '0', '0,1', '2023-07-11 09:11:47');
INSERT INTO `user_info` VALUES ('10010', '赵六', '18666666664', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', '0', '1', '0,1', '2023-07-11 09:11:47');
INSERT INTO `user_info` VALUES ('10011', '老刘', '18666666665', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', '1', '2', '0,1,2,3', '2023-07-11 09:11:47');
INSERT INTO `user_info` VALUES ('10012', '老罗', '18666668888', 'test123456', '2023-08-15', '1', '1', '2,1', '2023-08-31 22:30:24');
INSERT INTO `user_info` VALUES ('10014', 'test005', '18202222222', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10015', 'test004', '18202222223', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10017', 'test006', '18202222224', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10018', 'test007', '18202222225', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10019', 'test008', '18202222226', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10020', 'test009', '18202222227', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10021', 'test010', '18202222228', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10022', 'test011', '18202222229', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10023', 'test012', '18202222230', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10024', 'test013', '18202222231', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
INSERT INTO `user_info` VALUES ('10025', 'test014', '18202222232', 'test654321', '2023-08-14', '1', '0', '0', '2023-08-31 22:40:51');
