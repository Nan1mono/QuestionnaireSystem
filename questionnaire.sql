/*
 Navicat Premium Data Transfer

 Source Server         : MySQL_t1
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : localhost:3306
 Source Schema         : questionnaire

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 17/09/2021 19:32:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `answer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer_text` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `answer_time` datetime NULL DEFAULT NULL,
  `paper_id` bigint(20) NULL DEFAULT NULL,
  `field_id` bigint(20) NULL DEFAULT NULL,
  `group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer` VALUES (1, '选项A', '2021-09-15 18:55:26', 1, 1, '576df7b0d935445aa6083b94e64321a2');
INSERT INTO `answer` VALUES (2, '选项A&选项B&选项C', '2021-09-15 18:55:26', 1, 2, '576df7b0d935445aa6083b94e64321a2');
INSERT INTO `answer` VALUES (5, '选项D', '2021-09-15 20:40:43', 1, 1, '68d97d00e7c3484e9904236cb6019fe1');
INSERT INTO `answer` VALUES (6, '选项C&选项D', '2021-09-15 20:40:43', 1, 2, '68d97d00e7c3484e9904236cb6019fe1');

-- ----------------------------
-- Table structure for field
-- ----------------------------
DROP TABLE IF EXISTS `field`;
CREATE TABLE `field`  (
  `field_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `field_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `field_type` int(11) NOT NULL,
  `field_desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `paper_id` bigint(20) NULL DEFAULT NULL,
  `field_status` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`field_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of field
-- ----------------------------
INSERT INTO `field` VALUES (1, '单选按钮', 4, '', 1, 1);
INSERT INTO `field` VALUES (2, '多选按钮', 5, '', 1, 1);
INSERT INTO `field` VALUES (3, '单选按钮', 4, '', 2, 1);
INSERT INTO `field` VALUES (4, '多选按钮', 5, '', 2, 1);
INSERT INTO `field` VALUES (5, '单选按钮', 4, '', 3, 1);
INSERT INTO `field` VALUES (6, '多选按钮', 5, '', 3, 1);
INSERT INTO `field` VALUES (7, '单选按钮', 4, '', 4, 1);
INSERT INTO `field` VALUES (8, '多选按钮', 5, '', 4, 1);
INSERT INTO `field` VALUES (9, '单选按钮', 4, '', 5, 1);
INSERT INTO `field` VALUES (10, '多选按钮', 5, '', 5, 1);
INSERT INTO `field` VALUES (11, '多行文本框', 3, '', 6, 1);
INSERT INTO `field` VALUES (12, '单选按钮', 4, '', 6, 1);
INSERT INTO `field` VALUES (13, '单行文本框', 2, '', 7, 1);
INSERT INTO `field` VALUES (14, '单选按钮', 4, '', 7, 1);

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `paper_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paper_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `paper_desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `paper_created` datetime NULL DEFAULT NULL,
  `paper_status` int(11) NULL DEFAULT 2,
  `paper_count` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`paper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES (1, '问卷标题', '这里是问卷的说明内容', '2021-09-15 18:42:13', 1, 3);
INSERT INTO `paper` VALUES (2, '问卷标题', '这里是问卷的说明内容', '2021-09-15 18:46:48', 1, 0);
INSERT INTO `paper` VALUES (3, '问卷标题', '这里是问卷的说明内容', '2021-09-15 18:48:48', 2, 0);
INSERT INTO `paper` VALUES (4, '问卷标题', '这里是问卷的说明内容', '2021-09-15 18:52:27', 2, 0);
INSERT INTO `paper` VALUES (5, '问卷标题', '这里是问卷的说明内容', '2021-09-15 18:54:25', 1, 0);
INSERT INTO `paper` VALUES (6, '问卷标题', '这里是问卷的说明内容', '2021-09-16 10:10:56', 2, 0);
INSERT INTO `paper` VALUES (7, '第一个', '这里是问卷的说明内容', '2021-09-16 10:55:14', 2, 0);

-- ----------------------------
-- Table structure for selector
-- ----------------------------
DROP TABLE IF EXISTS `selector`;
CREATE TABLE `selector`  (
  `selector_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `selector_text` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `field_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`selector_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of selector
-- ----------------------------
INSERT INTO `selector` VALUES (1, '选项A', 1);
INSERT INTO `selector` VALUES (2, '选项B', 1);
INSERT INTO `selector` VALUES (3, '选项C', 1);
INSERT INTO `selector` VALUES (4, '选项D', 1);
INSERT INTO `selector` VALUES (5, '选项A', 2);
INSERT INTO `selector` VALUES (6, '选项B', 2);
INSERT INTO `selector` VALUES (7, '选项C', 2);
INSERT INTO `selector` VALUES (8, '选项D', 2);
INSERT INTO `selector` VALUES (9, '选项A', 3);
INSERT INTO `selector` VALUES (10, '选项B', 3);
INSERT INTO `selector` VALUES (11, '选项C', 3);
INSERT INTO `selector` VALUES (12, '选项D', 3);
INSERT INTO `selector` VALUES (13, '选项A', 4);
INSERT INTO `selector` VALUES (14, '选项B', 4);
INSERT INTO `selector` VALUES (15, '选项C', 4);
INSERT INTO `selector` VALUES (16, '选项D', 4);
INSERT INTO `selector` VALUES (17, '选项A', 5);
INSERT INTO `selector` VALUES (18, '选项B', 5);
INSERT INTO `selector` VALUES (19, '选项C', 5);
INSERT INTO `selector` VALUES (20, '选项D', 5);
INSERT INTO `selector` VALUES (21, '选项A', 6);
INSERT INTO `selector` VALUES (22, '选项B', 6);
INSERT INTO `selector` VALUES (23, '选项C', 6);
INSERT INTO `selector` VALUES (24, '选项D', 6);
INSERT INTO `selector` VALUES (25, '选项A', 7);
INSERT INTO `selector` VALUES (26, '选项B', 7);
INSERT INTO `selector` VALUES (27, '选项C', 7);
INSERT INTO `selector` VALUES (28, '选项D', 7);
INSERT INTO `selector` VALUES (29, '选项A', 8);
INSERT INTO `selector` VALUES (30, '选项B', 8);
INSERT INTO `selector` VALUES (31, '选项C', 8);
INSERT INTO `selector` VALUES (32, '选项D', 8);
INSERT INTO `selector` VALUES (33, '选项A', 9);
INSERT INTO `selector` VALUES (34, '选项B', 9);
INSERT INTO `selector` VALUES (35, '选项C', 9);
INSERT INTO `selector` VALUES (36, '选项D', 9);
INSERT INTO `selector` VALUES (37, '选项A', 10);
INSERT INTO `selector` VALUES (38, '选项B', 10);
INSERT INTO `selector` VALUES (39, '选项C', 10);
INSERT INTO `selector` VALUES (40, '选项D', 10);
INSERT INTO `selector` VALUES (41, '选项A', 12);
INSERT INTO `selector` VALUES (42, '选项B', 12);
INSERT INTO `selector` VALUES (43, '选项C', 12);
INSERT INTO `selector` VALUES (44, '选项D', 12);
INSERT INTO `selector` VALUES (45, '选项A', 14);
INSERT INTO `selector` VALUES (46, '选项B', 14);
INSERT INTO `selector` VALUES (47, '选项C', 14);
INSERT INTO `selector` VALUES (48, '选项D', 14);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编号',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_pass` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ljc', '123');
INSERT INTO `user` VALUES ('2', 'zhangsan', '123');
INSERT INTO `user` VALUES ('3', 'lihaohao', '123');
INSERT INTO `user` VALUES ('4', 'maxwell', '123');

SET FOREIGN_KEY_CHECKS = 1;
