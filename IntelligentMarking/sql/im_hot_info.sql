/*
 Navicat Premium Data Transfer

 Source Server         : IntelligentMarking
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : im

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 13/03/2024 14:42:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_hot_info
-- ----------------------------
DROP TABLE IF EXISTS `im_hot_info`;
CREATE TABLE `im_hot_info`  (
  `autoincre` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关键词',
  `articleid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章id',
  `submitTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '入库时间yyyy-MM-dd HH:mm:ss',
  `recordTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '新闻录入时间yyyy-MM-dd HH:mm:ss',
  `score` int UNSIGNED NULL DEFAULT 0 COMMENT '热点得分',
  PRIMARY KEY (`autoincre`) USING BTREE,
  UNIQUE INDEX `priindex`(`autoincre` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
