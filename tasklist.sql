/*
 Navicat MySQL Data Transfer

 Source Server         : TaskList
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost
 Source Database       : TaskList

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : utf-8

 Date: 11/21/2017 20:47:41 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task` varchar(255) DEFAULT NULL,
  `dueDate` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `task`
-- ----------------------------
BEGIN;
INSERT INTO `task` VALUES ('19', 'Setup/configure MySQL', '2017-11-28', 'Work', 'High', 'Created', '1'), ('20', 'Database MySQL: create a table Task', '2017-11-28', 'Work', 'Urgent', 'Created', '1'), ('21', 'Server side - Implement Database connection', '2017-11-27', 'Work', 'Normal', 'Created', '1'), ('23', 'Server side - Implement Database APIs: get task list, update task, insert task', '2017-11-26', 'Work', 'Critical', 'Created', '1'), ('25', 'Support to delete a task in both client and server	', '2017-11-29', 'Work', 'Urgent', 'Created', '1'), ('26', 'Support to mark a Task as Complete	', '2017-11-30', 'Work', 'Low', 'Created', '1'), ('27', 'Setup Github repository, code base', '2017-11-27', 'Work', 'High', 'Created', '2'), ('28', 'Add \'priority\' field (both front end and backend sides)', '2017-11-26', 'Work', 'Critical', 'Created', '2'), ('30', 'Sort task list by due date and priority	', '2017-11-25', 'Work', 'Critical', 'Created', '2'), ('31', 'Add userId to form', '2017-11-26', 'Work', 'Critical', 'Created', '3'), ('32', 'Config mysql on my own local machine', '2017-11-25', 'Work', 'Normal', 'Created', '3'), ('33', 'Sleep well', '2017-11-20', 'Personal', 'Normal', 'Completed', '2'), ('34', 'test', '2017-11-11', 'Personal', 'Critical', 'Created', '2');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'Pham Dong', 'phvdong@mum.edu'), ('2', 'Vinh Hoang', 'hthoang@mum.edu'), ('3', 'Yongzhao Zhang\r\n', 'yzhang@mum.edu');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
