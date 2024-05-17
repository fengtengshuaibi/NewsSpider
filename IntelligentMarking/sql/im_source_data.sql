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

 Date: 13/03/2024 14:43:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_source_data
-- ----------------------------
DROP TABLE IF EXISTS `im_source_data`;
CREATE TABLE `im_source_data`  (
  `autoincre` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `from` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `link` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '链接',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类标签',
  `readingamount` int UNSIGNED NULL DEFAULT 0 COMMENT '阅读量',
  `commentamount` int UNSIGNED NULL DEFAULT 0 COMMENT '评论数',
  `likes` int UNSIGNED NULL DEFAULT 0 COMMENT '点赞数',
  `belongs` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属栏目',
  `articleid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章id',
  `submitTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '入库时间yyyy-MM-dd HH:mm:ss',
  `keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关键词',
  `rank` int UNSIGNED NULL DEFAULT 0 COMMENT '序列',
  `recordTime` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '新闻录入时间yyyy-MM-dd HH:mm:ss',
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '新闻正文（如果有）',
  PRIMARY KEY (`autoincre`) USING BTREE,
  UNIQUE INDEX `priindex`(`autoincre` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
