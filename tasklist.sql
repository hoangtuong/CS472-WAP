/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : tasklist

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 21/11/2017 16:45:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dueDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (19, '456', '2017-11-21', 'Personal', 'Lowest', 'Completed', 1);
INSERT INTO `task` VALUES (20, 'aaaaaaaaaaa', '2017-11-21', 'Work', 'Urgent', 'Completed', 1);
INSERT INTO `task` VALUES (21, 'bbbbbbbb', '2017-11-21', 'Personal', 'Normal', 'Completed', 1);
INSERT INTO `task` VALUES (23, 'qqqqqqqqq', '2017-11-21', 'Personal', 'Critical', 'Created', 1);
INSERT INTO `task` VALUES (25, 'qqqqqqqqqqqqqq', '2017-11-21', 'Work', 'Urgent', 'Created', 2);
INSERT INTO `task` VALUES (26, 'rrrrrrrrrr', '2017-11-21', 'Work', 'Low', 'Created', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Pham Dong', 'phvdong@mum.edu');
INSERT INTO `user` VALUES (2, 'Vinh Hoang', 'hthoang@mum.edu');
INSERT INTO `user` VALUES (3, 'Yongzhao Zhang\r\n', NULL);

SET FOREIGN_KEY_CHECKS = 1;
