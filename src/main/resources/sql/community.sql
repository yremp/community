/*
 Navicat Premium Data Transfer

 Source Server         : wp
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 04/08/2019 19:29:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comm
-- ----------------------------
DROP TABLE IF EXISTS `comm`;
CREATE TABLE `comm`  (
  `comm_id` int(128) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `comm_parent_id` int(128) NULL DEFAULT NULL COMMENT '父类评论id',
  `comm_type` int(10) NOT NULL COMMENT '评论级别',
  `comm_user_id` int(128) NULL DEFAULT NULL COMMENT '评论者id',
  `comm_info` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `comm_count` int(255) NULL DEFAULT NULL COMMENT '评论回复统计',
  `comm_like` int(16) UNSIGNED NULL DEFAULT NULL COMMENT '点赞数',
  `gmt_create` bigint(255) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(255) NULL DEFAULT NULL COMMENT '更新后时间',
  PRIMARY KEY (`comm_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `notice_id` int(128) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `notice_senderid` int(128) NOT NULL DEFAULT 0 COMMENT '发送者id',
  `notice_receiverid` int(128) NOT NULL DEFAULT 0 COMMENT '接收者id',
  `notice_sendername` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送者名字',
  `gmt_create` bigint(255) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `status` int(16) NOT NULL DEFAULT 0 COMMENT '阅读状态(0/1)',
  `type` int(16) NOT NULL COMMENT '回复类型(1/2)',
  `notice_qcparentid` int(128) NOT NULL COMMENT 'notice_qcparentid',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ques
-- ----------------------------
DROP TABLE IF EXISTS `ques`;
CREATE TABLE `ques`  (
  `ques_id` int(128) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ques_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `ques_desc` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '正文描述',
  `ques_userid` int(128) NULL DEFAULT NULL COMMENT '问题发起者',
  `ques_comment` int(128) NULL DEFAULT NULL COMMENT '问题回复数',
  `ques_read` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题阅读数',
  `ques_like` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题点赞数(暂时没有使用）',
  `gmt_create` bigint(255) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(255) NULL DEFAULT NULL COMMENT '更新时间',
  `ques_tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问题标签',
  PRIMARY KEY (`ques_id`) USING BTREE,
  INDEX `ques_userid`(`ques_userid`) USING BTREE,
  CONSTRAINT `ques_ibfk_1` FOREIGN KEY (`ques_userid`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(128) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'githubid',
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '令牌',
  `user_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `gmt_create` bigint(255) NULL DEFAULT NULL COMMENT '账号创建时间',
  `gmt_modified` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号更新时间',
  `user_bio` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'bio',
  `user_blog` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客地址',
  `user_github` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github地址',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
