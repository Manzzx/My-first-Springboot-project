/*
 Navicat Premium Data Transfer

 Source Server         : mydatabase01
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3307
 Source Schema         : reggie

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 05/02/2023 11:42:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `sex` tinyint(4) NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '地址管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (1417414526093082626, 1417012167126876162, '小明', 1, '13812345678', NULL, NULL, NULL, NULL, NULL, NULL, '昌平区金燕龙办公楼', '公司', 1, '2021-07-20 17:22:12', '2021-07-20 17:26:33', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1417414926166769666, 1417012167126876162, '小李', 1, '13512345678', NULL, NULL, NULL, NULL, NULL, NULL, '测试', '家', 0, '2021-07-20 17:23:47', '2021-07-20 17:23:47', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1519220416315400193, 1518853017015697409, 'kitty', 1, '15222222222', NULL, NULL, NULL, NULL, NULL, NULL, '河北保定', '公司', 0, '2022-04-27 15:42:27', '2022-04-27 17:53:20', 1518853017015697409, 1518853017015697409, 0);
INSERT INTO `address_book` VALUES (1519231376832884737, 1518853017015697409, 'kitty', 1, '15222222222', NULL, NULL, NULL, NULL, NULL, NULL, '北京海淀区', '公司', 1, '2022-04-27 16:26:00', '2022-04-27 17:40:15', 1518853017015697409, 1518853017015697409, 0);
INSERT INTO `address_book` VALUES (1621170687399706626, 1621166431154319362, 'zzx', 1, '17866798847', NULL, NULL, NULL, NULL, NULL, NULL, '广州市花都区', '家', 0, '2023-02-02 23:36:26', '2023-02-04 20:33:54', 1621166431154319362, 1621166431154319362, 0);
INSERT INTO `address_book` VALUES (1621172612820230145, 1621166431154319362, 'zzx', 1, '13266089538', NULL, NULL, NULL, NULL, NULL, NULL, '东莞市松山湖', '学校', 1, '2023-02-02 23:44:05', '2023-02-05 11:13:57', 1621166431154319362, 1621166431154319362, 0);
INSERT INTO `address_book` VALUES (1621335145330040834, 1621166431154319362, '钟xx', 1, '15345678988', NULL, NULL, NULL, NULL, NULL, NULL, '广东省云浮市', '家', 0, '2023-02-03 10:29:56', '2023-02-04 19:46:32', 1621166431154319362, 1621166431154319362, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1397844263642378242, 1, '湘菜', 1, '2021-05-27 09:16:58', '2023-01-30 00:03:16', 1, 1, 0);
INSERT INTO `category` VALUES (1397844303408574465, 1, '川菜', 2, '2021-05-27 09:17:07', '2021-06-02 14:27:22', 1, 1, 0);
INSERT INTO `category` VALUES (1397844391040167938, 1, '粤菜', 3, '2021-05-27 09:17:28', '2021-07-09 14:37:13', 1, 1, 0);
INSERT INTO `category` VALUES (1413341197421846529, 1, '饮品', 11, '2021-07-09 11:36:15', '2021-07-09 14:39:15', 1, 1, 0);
INSERT INTO `category` VALUES (1413342269393674242, 2, '商务套餐', 5, '2021-07-09 11:40:30', '2021-07-09 14:43:45', 1, 1, 0);
INSERT INTO `category` VALUES (1413384954989060097, 1, '主食', 12, '2021-07-09 14:30:07', '2021-07-09 14:39:19', 1, 1, 0);
INSERT INTO `category` VALUES (1413386191767674881, 2, '儿童套餐', 6, '2021-07-09 14:35:02', '2021-07-09 14:39:05', 1, 1, 0);
INSERT INTO `category` VALUES (1514518234500497409, 2, '减肥套餐', 2, '2022-04-14 16:17:40', '2022-04-16 21:18:13', 1, 1, 0);
INSERT INTO `category` VALUES (1619716634790940673, 2, '穷B人套餐', 9, '2023-01-29 23:18:33', '2023-01-30 00:05:22', 1, 1, 0);
INSERT INTO `category` VALUES (1619718531547484162, 1, '好菜', 8, '2023-01-29 23:26:06', '2023-01-29 23:26:06', 1, 1, 0);
INSERT INTO `category` VALUES (1619718681430937601, 2, '简餐', 9, '2023-01-29 23:26:41', '2023-01-30 16:55:28', 1, 1619206012562931714, 0);
INSERT INTO `category` VALUES (1619983138291511298, 1, 'bb菜', 3, '2023-01-30 16:57:33', '2023-01-30 21:35:17', 1619206012562931714, 1, 0);
INSERT INTO `category` VALUES (1619983919782621186, 1, 'aa菜', 30, '2023-01-30 17:00:39', '2023-01-30 21:35:23', 1619206012562931714, 1, 0);
INSERT INTO `category` VALUES (1619984483618713602, 1, 'cc菜', 7, '2023-01-30 17:02:53', '2023-01-30 21:35:29', 1619206012562931714, 1, 0);
INSERT INTO `category` VALUES (1619984657028018177, 1, 'a菜', 2, '2023-01-30 17:03:35', '2023-01-31 09:07:05', 1619206012562931714, 1, 0);
INSERT INTO `category` VALUES (1620602668483465218, 2, '精品套餐', 8, '2023-02-01 09:59:20', '2023-02-01 09:59:20', 1, 1, 0);
INSERT INTO `category` VALUES (1620602753283903489, 2, '高级套餐', 2, '2023-02-01 09:59:40', '2023-02-01 09:59:40', 1, 1, 0);
INSERT INTO `category` VALUES (1620602815858724865, 2, '平民套餐', 13, '2023-02-01 09:59:55', '2023-02-01 09:59:55', 1, 1, 0);
INSERT INTO `category` VALUES (1620602897362440193, 2, '每日套餐', 7, '2023-02-01 10:00:15', '2023-02-01 10:00:15', 1, 1, 0);
INSERT INTO `category` VALUES (1620603162362761218, 2, '单人套餐', 10, '2023-02-01 10:01:18', '2023-02-01 10:01:43', 1, 1, 0);
INSERT INTO `category` VALUES (1620603211595501569, 2, '双人套餐', 6, '2023-02-01 10:01:30', '2023-02-01 10:01:30', 1, 1, 0);
INSERT INTO `category` VALUES (1620603367548112897, 2, '特价套餐', 16, '2023-02-01 10:02:07', '2023-02-01 10:02:07', 1, 1, 0);
INSERT INTO `category` VALUES (1620603497865138178, 2, '尊贵套餐', 8, '2023-02-01 10:02:38', '2023-02-01 10:02:38', 1, 1, 0);
INSERT INTO `category` VALUES (1620603620305260545, 2, 'SVIP尊贵套餐', 88, '2023-02-01 10:03:07', '2023-02-01 10:03:07', 1, 1, 0);
INSERT INTO `category` VALUES (1620662200148258818, 1, '美国菜', 15, '2023-02-01 13:55:54', '2023-02-01 13:55:54', 1, 1, 0);
INSERT INTO `category` VALUES (1620662229097345026, 1, '法国菜', 16, '2023-02-01 13:56:01', '2023-02-01 13:56:01', 1, 1, 0);
INSERT INTO `category` VALUES (1620662275582816257, 1, '英国菜', 17, '2023-02-01 13:56:12', '2023-02-01 13:56:12', 1, 1, 0);
INSERT INTO `category` VALUES (1620662306218012673, 1, '德国菜', 18, '2023-02-01 13:56:19', '2023-02-01 13:56:19', 1, 1, 0);
INSERT INTO `category` VALUES (1620662330888908801, 1, '日本菜', 19, '2023-02-01 13:56:25', '2023-02-01 13:56:25', 1, 1, 0);
INSERT INTO `category` VALUES (1620662379085656066, 1, '意大利菜', 20, '2023-02-01 13:56:36', '2023-02-01 13:56:36', 1, 1, 0);

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dish_name_uindex`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish
-- ----------------------------
INSERT INTO `dish` VALUES (1397849739276890114, '辣子鸡鸡', 1397844303408574465, 3800.00, '222222222', 'e5bd4c00-b4c3-47b8-879b-a8bf465efc4b.jpg', '棒棒棒棒棒辣子鸡敲好吃', 1, 0, '2021-05-27 09:38:43', '2023-02-03 13:55:34', 1, 1, 0);
INSERT INTO `dish` VALUES (1397850140982161409, '毛氏红烧肉', 1397844263642378242, 6800.00, '123412341234', '4aeb7935-9a3a-4500-9eff-5cecac7840fe.jpg', '毛氏红烧肉毛氏红烧肉，确定不来一份？', 1, 0, '2021-05-27 09:40:19', '2023-02-03 13:53:58', 1, 1, 0);
INSERT INTO `dish` VALUES (1397850392090947585, '组庵鱼翅', 1397844263642378242, 4800.00, '123412341234', 'ea7c73a7-c86b-40cd-b702-f94e716c019a.jpg', '组庵鱼翅，看图足以表明好吃程度', 1, 0, '2021-05-27 09:41:19', '2023-02-03 13:52:53', 1, 1, 0);
INSERT INTO `dish` VALUES (1397850851245600769, '霸王别姬', 1397844263642378242, 12800.00, '123412341234', '019413f0-b0b8-4404-abe2-14c671be30da.jpg', '还有什么比霸王别姬更美味的呢？', 1, 0, '2021-05-27 09:43:08', '2023-02-03 13:52:23', 1, 1, 0);
INSERT INTO `dish` VALUES (1397851099502260226, '全家福', 1397844263642378242, 11800.00, '23412341234', '9eee2f27-5c83-4168-bc45-f63eae432ed2.jpeg', '别光吃肉啦，来份全家福吧，让你长寿又美味', 1, 0, '2021-05-27 09:44:08', '2023-02-03 13:53:10', 1, 1, 0);
INSERT INTO `dish` VALUES (1397851370462687234, '邵阳猪血丸子', 1397844263642378242, 13800.00, '1246812345678', '224681bc-34b4-467b-9243-5ed7719cbd1d.jpg', '看，美味不？来嘛来嘛，这才是最爱吖', 1, 0, '2021-05-27 09:45:12', '2023-02-01 21:33:33', 1, 1, 0);
INSERT INTO `dish` VALUES (1397851668262465537, '口味蛇', 1397844263642378242, 16800.00, '1234567812345678', '354e7218-32ad-4559-82d4-92472f4cdfdf.jpg', '爬行界的扛把子，东兴-口味蛇，让你欲罢不能', 1, 0, '2021-05-27 09:46:23', '2023-02-01 21:33:33', 1, 1, 0);
INSERT INTO `dish` VALUES (1397852391150759938, '辣子鸡丁', 1397844303408574465, 8800.00, '2346812468', '56c5df7e-33f2-44c1-90dc-882d8d0b9629.jpg', '辣子鸡丁，辣子鸡丁，永远的魂', 1, 0, '2021-05-27 09:49:16', '2023-02-01 21:33:33', 1, 1, 0);
INSERT INTO `dish` VALUES (1397853183287013378, '麻辣兔头', 1397844303408574465, 19800.00, '123456787654321', '9d36ee77-2f90-407d-8d0c-9ed47c2a7c2a.jpg', '麻辣兔头的详细制作，麻辣鲜香，色泽红润，回味悠长', 1, 0, '2021-05-27 09:52:24', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397853709101740034, '蒜泥白肉', 1397844303408574465, 9800.00, '1234321234321', '889cc848-716c-4c2e-b3f6-e8fca9b0b125.jpg', '多么的有食欲啊', 1, 0, '2021-05-27 09:54:30', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397853890262118402, '鱼香肉丝', 1397844303408574465, 3800.00, '1234212321234', '2a156d3a-eb68-498c-ad97-0c78c32bc009.jpg', '鱼香肉丝简直就是我们童年回忆的一道经典菜，上学的时候点个鱼香肉丝盖饭坐在宿舍床上看着肥皂剧，绝了！现在完美复刻一下上学的时候感觉', 1, 0, '2021-05-27 09:55:13', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397854652581064706, '麻辣水煮鱼', 1397844303408574465, 14800.00, '2345312·345321', '276f1b88-1149-4498-b066-c5ff3fcef265.jpg', '鱼片是买的切好的鱼片，放几个虾，增加味道', 1, 0, '2021-05-27 09:58:15', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397854865672679425, '鱼香炒鸡蛋', 1397844303408574465, 2000.00, '23456431·23456', '9f051a45-c744-47c0-bf8b-f1feded46521.jpg', '鱼香菜也是川味的特色。里面没有鱼却鱼香味', 1, 0, '2021-05-27 09:59:06', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397860242057375745, '脆皮烧鹅', 1397844391040167938, 12800.00, '123456786543213456', 'e46dd7c9-8471-46b2-a029-9ff53cfecd89.jpg', '“广东烤鸭美而香，却胜烧鹅说古冈（今新会），燕瘦环肥各佳妙，君休偏重便宜坊”，可见烧鹅与烧鸭在粤菜之中已早负盛名。作为广州最普遍和最受欢迎的烧烤肉食，以它的“色泽金红，皮脆肉嫩，味香可口”的特色，在省城各大街小巷的烧卤店随处可见。', 1, 0, '2021-05-27 10:20:27', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397860963880316929, '脆皮乳鸽', 1397844391040167938, 10800.00, '1234563212345', 'fa6c538d-3107-4808-ba7b-5e6a89ccb69c.jpg', '“脆皮乳鸽”是广东菜中的一道传统名菜，属于粤菜系，具有皮脆肉嫩、色泽红亮、鲜香味美的特点，常吃可使身体强健，清肺顺气。随着菜品制作工艺的不断发展，逐渐形成了熟炸法、生炸法和烤制法三种制作方法。无论那种制作方法，都是在鸽子经过一系列的加工，挂脆皮水后再加工而成，正宗的“脆皮乳鸽皮脆肉嫩、色泽红亮、鲜香味美、香气馥郁。这三种方法的制作过程都不算复杂，但想达到理想的效果并不容易。', 1, 0, '2021-05-27 10:23:19', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397861683434139649, '清蒸河鲜海鲜', 1397844391040167938, 38800.00, '1234567876543213456', '1405081e-f545-42e1-86a2-f7559ae2e276.jpeg', '新鲜的海鲜，清蒸是最好的处理方式。鲜，体会为什么叫海鲜。清蒸是广州最经典的烹饪手法，过去岭南地区由于峻山大岭阻隔，交通不便，经济发展起步慢，自家打的鱼放在锅里煮了就吃，没有太多的讲究，但却发现这清淡的煮法能使鱼的鲜甜跃然舌尖。', 1, 0, '2021-05-27 10:26:11', '2023-01-31 23:50:59', 1, 1, 0);
INSERT INTO `dish` VALUES (1397862198033297410, '老火靓汤', 1397844391040167938, 49800.00, '123456786532455', '583df4b7-a159-4cfc-9543-4f666120b25f.jpeg', '老火靓汤又称广府汤，是广府人传承数千年的食补养生秘方，慢火煲煮的中华老火靓汤，火候足，时间长，既取药补之效，又取入口之甘甜。 广府老火汤种类繁多，可以用各种汤料和烹调方法，烹制出各种不同口味、不同功效的汤来。', 1, 0, '2021-05-27 10:28:14', '2023-01-31 23:51:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1397862477831122945, '上汤焗龙虾', 1397844391040167938, 108800.00, '1234567865432', '5b8d2da3-3744-4bb3-acdc-329056b8259d.jpeg', '上汤焗龙虾是一道色香味俱全的传统名菜，属于粤菜系。此菜以龙虾为主料，配以高汤制成的一道海鲜美食。本品肉质洁白细嫩，味道鲜美，蛋白质含量高，脂肪含量低，营养丰富。是色香味俱全的传统名菜。', 1, 0, '2021-05-27 10:29:20', '2023-01-31 23:51:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1413342036832100354, '北冰洋', 1413341197421846529, 500.00, '', 'c99e0aab-3cb7-4eaa-80fd-f47d4ffea694.png', '', 1, 0, '2021-07-09 11:39:35', '2023-01-31 23:51:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1413384757047271425, '王老吉', 1413341197421846529, 500.00, '', '00874a5e-0df2-446b-8f69-a30eb7d88ee8.png', '啊啊啊啊啊', 1, 0, '2021-07-09 14:29:20', '2023-01-31 23:51:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1413385247889891330, '米饭', 1413384954989060097, 200.00, '', '71cd3c81-d89a-4745-8961-88e9bafb6e53.png', '香喷喷的东北大米', 1, 0, '2023-02-01 16:21:31', '2023-02-02 16:53:38', 1, 1619206012562931714, 0);
INSERT INTO `dish` VALUES (1518377248351068161, '珍珠奶茶', 1413341197421846529, 1700.00, '', '11650844315241100.png', '珍珠奶茶', 1, 0, '2022-04-25 07:52:00', '2023-01-31 23:51:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1518377248351068162, '好菜菜', 1619718531547484162, 1700.00, '', '11650844315241100.png', '珍珠奶茶', 1, 0, '2022-04-25 07:52:00', '2023-01-31 23:51:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1620043404035522562, 'aaa', 1397844303408574465, 50000.00, '', '1487aa14-ab87-4a2a-8057-ffea2710ca9b.jpg', 'bbbb', 1, 0, '2023-01-30 21:03:09', '2023-01-31 23:51:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1620081985277767681, 'bbb', 1397844263642378242, 8800.00, '', 'null', 'sadad', 1, 0, '2023-01-30 23:32:29', '2023-02-03 13:53:37', 1, 1, 0);
INSERT INTO `dish` VALUES (1620084954421362690, '巴氏鲜奶', 1413384954989060097, 800.00, '', 'null', '巴巴爸爸不', 1, 0, '2023-01-30 23:44:37', '2023-02-02 10:26:28', 1, 1, 0);
INSERT INTO `dish` VALUES (1620289478452850689, '酸奶', 1413341197421846529, 500.00, '', 'null', '好好喝', 1, 0, '2023-01-31 13:14:50', '2023-02-02 10:26:28', 1, 1, 0);
INSERT INTO `dish` VALUES (1620411056780054530, 'abb', 1619984657028018177, 55500.00, '', '13e6a03e-dc45-47b0-8b68-ca3c10291e6b.jpg', 'asdasdasd', 1, 0, '2023-01-31 22:42:39', '2023-02-02 10:26:28', 1, 1, 0);
INSERT INTO `dish` VALUES (1620446567938748418, 'a', 1619984657028018177, 2200.00, '', 'null', 'asasd', 1, 0, '2023-01-31 23:39:03', '2023-02-02 10:26:28', 1, 1, 0);
INSERT INTO `dish` VALUES (1620446632119988225, 'aa', 1619984657028018177, 2200.00, '', 'null', 'aadasd', 1, 0, '2023-01-31 23:39:18', '2023-02-02 10:26:28', 1, 1, 0);
INSERT INTO `dish` VALUES (1620447129463779330, 'aaaa', 1619984657028018177, 33200.00, '', 'null', 'asdasdasd', 1, 0, '2023-01-31 23:41:17', '2023-02-02 10:26:28', 1, 1, 0);
INSERT INTO `dish` VALUES (1620447337853583361, 'aaaaaa', 1619984657028018177, 21300.00, '', 'null', 'asdasdasd', 1, 0, '2023-01-31 23:42:06', '2023-02-02 10:26:28', 1, 1, 0);
INSERT INTO `dish` VALUES (1620447397450448897, 'bbbb', 1413341197421846529, 32400.00, '', 'null', 'asdasdasd', 1, 0, '2023-01-31 23:42:21', '2023-02-02 10:26:32', 1, 1, 0);
INSERT INTO `dish` VALUES (1620622395678986241, '冒菜', 1619983138291511298, 4500.00, '', '23298a56-a41a-45de-b0e7-ee3c74e79796.jpg', '冒菜好好吃', 1, 0, '2023-02-01 11:17:43', '2023-02-01 19:11:05', 1, 1, 0);
INSERT INTO `dish` VALUES (1621697931683680258, '硬菜', 1397844263642378242, 88600.00, '', '094096bf-157e-4e90-bbef-4750b7439383.jpeg', '很好吃的菜', 1, 0, '2023-02-04 10:31:31', '2023-02-04 10:31:31', 1, 1, 0);
INSERT INTO `dish` VALUES (1621896459890585601, '面包', 1413384954989060097, 700.00, '', '46407ec4-cdc5-42d8-b34f-b193a9753d67.jpg', '新鲜出炉的面包', 1, 0, '2023-02-04 23:40:24', '2023-02-04 23:42:12', 1, 1, 0);

-- ----------------------------
-- Table structure for dish_flavor
-- ----------------------------
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '口味名称',
  `value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味数据list',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dish_flavor_pk`(`dish_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品口味关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dish_flavor
-- ----------------------------
INSERT INTO `dish_flavor` VALUES (1397851668283437058, 1397851668262465537, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2023-01-31 22:53:14', '2023-01-31 22:53:14', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397854133632413697, 1397854133603053569, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-27 09:56:11', '2021-05-27 09:56:11', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397859757061615618, 1397859757036449794, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:18:32', '2021-05-27 10:18:32', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1397861135754506242, 1397861135733534722, '甜味', '[\"无糖\",\"少糖\",\"半躺\",\"多糖\",\"全糖\"]', '2021-05-27 10:24:00', '2021-05-27 10:24:00', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398089545865015297, 1398089545676271617, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:31:38', '2021-05-28 01:31:38', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1398090685449023490, 1398090685419663362, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2021-05-28 01:36:09', '2021-05-28 01:36:09', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1413389540592263169, 1413384757047271425, '温度', '[\"常温\",\"冷藏\"]', '2022-04-25 09:57:38', '2022-04-25 15:18:12', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1413389684020682754, 1413342036832100354, '温度', '[\"常温\",\"冷藏\"]', '2021-07-09 15:12:18', '2022-04-25 15:17:46', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1518375945893965826, 1518375945768136706, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]', '2022-04-25 07:46:50', '2022-04-25 07:46:50', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1518375945898160130, 1518375945768136706, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2022-04-25 07:46:50', '2022-04-25 07:46:50', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1518376699895607297, 1518376600306053121, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]', '2022-04-25 07:49:49', '2022-04-25 07:49:49', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1518376699975299074, 1518376600306053121, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2022-04-25 07:49:50', '2022-04-25 07:49:50', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1518377248351068162, 1518377248351068161, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]', '2022-04-25 07:52:00', '2022-04-25 07:52:00', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1518377248351068163, 1518377248351068161, '温度', '[\"热饮\",\"常温\",\"去冰\",\"少冰\",\"多冰\"]', '2022-04-25 07:52:00', '2022-04-25 07:52:00', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620394307967897601, 1397849739276890114, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-02-03 13:55:34', '2023-02-03 13:55:34', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620395052897259522, 1397849739276890114, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-02-03 13:55:34', '2023-02-03 13:55:34', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620434741498851330, 1397852391150759938, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-01-31 22:52:03', '2023-01-31 22:52:03', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620434741498851331, 1397852391150759938, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-01-31 22:52:03', '2023-01-31 22:52:03', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620434895886987265, 1397851370462687234, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-01-31 22:52:40', '2023-01-31 22:52:40', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620434895886987266, 1397851370462687234, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-01-31 22:52:40', '2023-01-31 22:52:40', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620622395678986242, 1620622395678986241, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-02-01 19:11:05', '2023-02-01 19:11:05', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1620622395737706498, 1620622395678986241, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-02-01 19:11:05', '2023-02-01 19:11:05', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386092965896194, 1397850851245600769, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-02-03 13:52:23', '2023-02-03 13:52:23', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386092965896195, 1397850851245600769, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-02-03 13:52:23', '2023-02-03 13:52:23', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386216421040130, 1397850392090947585, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-02-03 13:52:53', '2023-02-03 13:52:53', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386216421040131, 1397850392090947585, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-02-03 13:52:53', '2023-02-03 13:52:53', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386287589990402, 1397851099502260226, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-02-03 13:53:10', '2023-02-03 13:53:10', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386287589990403, 1397851099502260226, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-02-03 13:53:10', '2023-02-03 13:53:10', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386403617021954, 1620081985277767681, '辣度', '[\"不辣\",\"微辣\",\"中辣\",\"重辣\"]', '2023-02-03 13:53:37', '2023-02-03 13:53:37', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621386489516367873, 1397850140982161409, '忌口', '[\"不要葱\",\"不要蒜\",\"不要香菜\",\"不要辣\"]', '2023-02-03 13:53:58', '2023-02-03 13:53:58', 1, 1, 0);
INSERT INTO `dish_flavor` VALUES (1621896460003831809, 1621896459890585601, '甜味', '[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]', '2023-02-04 23:42:12', '2023-02-04 23:42:12', 1, 1, 0);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2023-01-28 15:22:27', 1, 1);
INSERT INTO `employee` VALUES (2, 'demo02', 'demo02', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '130621202204141888', 1, '2021-05-06 17:20:07', '2023-01-29 16:36:34', 1, 1);
INSERT INTO `employee` VALUES (3, 'demo03', 'demo03', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2023-01-28 21:15:14', 1, 1);
INSERT INTO `employee` VALUES (4, 'demo04', 'demo04', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 0, '2021-05-06 17:20:07', '2023-01-28 17:55:03', 1, 1);
INSERT INTO `employee` VALUES (5, 'demo05', 'demo05', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:05', 1, 1);
INSERT INTO `employee` VALUES (6, 'demo06', 'demo06', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);
INSERT INTO `employee` VALUES (7, 'demo07', 'demo07', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);
INSERT INTO `employee` VALUES (8, 'demo08', 'demo08', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);
INSERT INTO `employee` VALUES (9, 'demo09', 'demo09', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);
INSERT INTO `employee` VALUES (10, 'demo10', 'demo10', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2023-01-28 19:13:06', 1, 1);
INSERT INTO `employee` VALUES (11, 'demo11', 'demo11', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);
INSERT INTO `employee` VALUES (1514172439561543682, '张三', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '15222222222', '1', '130621202204146666', 1, '2022-04-13 17:23:36', '2022-04-14 14:23:34', 1, 1);
INSERT INTO `employee` VALUES (1514497404844498945, '李四', 'lisi', 'e10adc3949ba59abbe56e057f20f883e', '13233333333', '1', '130621202204141454', 1, '2022-04-14 14:54:53', '2022-04-14 14:54:53', 1, 1);
INSERT INTO `employee` VALUES (1514504286447452161, '王五', 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '13233333333', '1', '130621202204141522', 1, '2022-04-14 15:22:14', '2022-04-14 15:22:14', 1, 1);
INSERT INTO `employee` VALUES (1619205878080962562, 'dsfsf', 'demo12', 'e10adc3949ba59abbe56e057f20f883e', '15832197898', '1', '556446545466665', 1, '2023-01-28 13:28:59', '2023-01-28 13:28:59', 1, 1);
INSERT INTO `employee` VALUES (1619206012562931714, 'zzx', 'zzx7888', 'e10adc3949ba59abbe56e057f20f883e', '15832197885', '1', '564656456465556', 1, '2023-01-28 13:29:31', '2023-02-02 16:52:38', 1, 1619206012562931714);
INSERT INTO `employee` VALUES (1619569929407758337, 'zzx123', '7888zzx', 'e10adc3949ba59abbe56e057f20f883e', '15832197885', '1', '546544656464651533', 1, '2023-01-29 13:35:36', '2023-01-29 15:57:12', 1, 1619569929407758337);
INSERT INTO `employee` VALUES (1619570457625784322, 'fdsfsdf', 'adsasd7880', 'e10adc3949ba59abbe56e057f20f883e', '15832197885', '1', '424534756534542345', 1, '2023-01-29 13:37:46', '2023-01-29 13:39:10', 1, 1);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `dish_id` bigint(20) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1519658010962567171, '组庵鱼翅', '740c79ce-af29-41b8-b78d-5f49c96e38c4.jpg', 11651149677613100, 1397850392090947585, NULL, '微辣,微辣', 1, 48.00);
INSERT INTO `order_detail` VALUES (1519658011025481730, '霸王别姬', '057dd338-e487-4bbc-a74c-0384c44a9ca3.jpg', 11651149677613100, 1397850851245600769, NULL, '不要香菜,微辣', 1, 128.00);
INSERT INTO `order_detail` VALUES (1519658011025481731, '鱼香炒鸡蛋', '0f252364-a561-4e8d-8065-9a6797a6b1d3.jpg', 11651149677613100, 1397854865672679425, NULL, '重辣', 1, 20.00);
INSERT INTO `order_detail` VALUES (1519840789042089985, '口味蛇', '0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg', 11651193255341100, 1397851668262465537, NULL, '常温', 1, 168.00);
INSERT INTO `order_detail` VALUES (1621837620994449412, '口味蛇', '354e7218-32ad-4559-82d4-92472f4cdfdf.jpg', 1621837620994449410, 1397851668262465537, NULL, '常温', 2, 168.00);
INSERT INTO `order_detail` VALUES (1621837620994449413, '邵阳猪血丸子', '224681bc-34b4-467b-9243-5ed7719cbd1d.jpg', 1621837620994449410, 1397851370462687234, NULL, '不要蒜,微辣', 2, 138.00);
INSERT INTO `order_detail` VALUES (1621837621057363969, '霸王别姬', '019413f0-b0b8-4404-abe2-14c671be30da.jpg', 1621837620994449410, 1397850851245600769, NULL, '不要蒜,微辣', 2, 128.00);
INSERT INTO `order_detail` VALUES (1621849633988665347, '口味蛇', '354e7218-32ad-4559-82d4-92472f4cdfdf.jpg', 1621849633988665345, 1397851668262465537, NULL, '常温', 2, 168.00);
INSERT INTO `order_detail` VALUES (1621849633988665348, '组庵鱼翅', 'ea7c73a7-c86b-40cd-b702-f94e716c019a.jpg', 1621849633988665345, 1397850392090947585, NULL, '不要蒜,不辣', 1, 48.00);
INSERT INTO `order_detail` VALUES (1621849633988665349, '毛氏红烧肉', '4aeb7935-9a3a-4500-9eff-5cecac7840fe.jpg', 1621849633988665345, 1397850140982161409, NULL, '不要蒜', 2, 68.00);
INSERT INTO `order_detail` VALUES (1621849634055774209, '巴氏鲜奶', 'null', 1621849633988665345, 1620084954421362690, NULL, '默认口味', 2, 8.00);
INSERT INTO `order_detail` VALUES (1621849634055774210, '米饭', '71cd3c81-d89a-4745-8961-88e9bafb6e53.png', 1621849633988665345, 1413385247889891330, NULL, '默认口味', 2, 2.00);
INSERT INTO `order_detail` VALUES (1621855032221495298, '口味蛇', '354e7218-32ad-4559-82d4-92472f4cdfdf.jpg', 1621855031969837057, 1397851668262465537, NULL, '常温', 1, 168.00);
INSERT INTO `order_detail` VALUES (1621855032221495299, '邵阳猪血丸子', '224681bc-34b4-467b-9243-5ed7719cbd1d.jpg', 1621855031969837057, 1397851370462687234, NULL, '不要葱,不辣', 1, 138.00);
INSERT INTO `order_detail` VALUES (1621865669303562243, '口味蛇', '354e7218-32ad-4559-82d4-92472f4cdfdf.jpg', 1621865669219676161, 1397851668262465537, NULL, '常温', 1, 168.00);
INSERT INTO `order_detail` VALUES (1621865669370671105, '邵阳猪血丸子', '224681bc-34b4-467b-9243-5ed7719cbd1d.jpg', 1621865669219676161, 1397851370462687234, NULL, '不要蒜,微辣', 2, 138.00);
INSERT INTO `order_detail` VALUES (1621865669370671106, '霸王别姬', '019413f0-b0b8-4404-abe2-14c671be30da.jpg', 1621865669219676161, 1397850851245600769, NULL, '不要香菜,中辣', 1, 128.00);
INSERT INTO `order_detail` VALUES (1621894432280145921, '白米饭套餐', 'ce094396-ce87-48c3-885a-438a1603db03.jpg', 1621894432028487682, NULL, 1518510426785161219, NULL, 2, 34.00);
INSERT INTO `order_detail` VALUES (1621894432280145922, '鱼翅套餐2', '3be3e986-c026-4a91-ae1d-8c3024c65941.jpg', 1621894432028487682, NULL, 1620661858023075841, NULL, 1, 87.00);
INSERT INTO `order_detail` VALUES (1622071006006931459, '全家福', '9eee2f27-5c83-4168-bc45-f63eae432ed2.jpeg', 1622071006006931457, 1397851099502260226, NULL, '不要蒜,中辣', 1, 118.00);
INSERT INTO `order_detail` VALUES (1622071006006931460, '邵阳猪血丸子', '224681bc-34b4-467b-9243-5ed7719cbd1d.jpg', 1622071006006931457, 1397851370462687234, NULL, '不要蒜,中辣', 1, 138.00);
INSERT INTO `order_detail` VALUES (1622071595881263108, '毛氏红烧肉', '4aeb7935-9a3a-4500-9eff-5cecac7840fe.jpg', 1622071595881263106, 1397850140982161409, NULL, '不要蒜', 1, 68.00);
INSERT INTO `order_detail` VALUES (1622071595944177665, '硬菜', '094096bf-157e-4e90-bbef-4750b7439383.jpeg', 1622071595881263106, 1621697931683680258, NULL, '默认口味', 1, 886.00);
INSERT INTO `order_detail` VALUES (1622071595944177666, '面包', '46407ec4-cdc5-42d8-b34f-b193a9753d67.jpg', 1622071595881263106, 1621896459890585601, NULL, '半糖', 1, 7.00);
INSERT INTO `order_detail` VALUES (1622071595944177667, '米饭', '71cd3c81-d89a-4745-8961-88e9bafb6e53.png', 1622071595881263106, 1413385247889891330, NULL, '默认口味', 2, 2.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户',
  `address_book_id` bigint(20) NOT NULL COMMENT '地址id',
  `order_time` datetime(0) NOT NULL COMMENT '下单时间',
  `checkout_time` datetime(0) NOT NULL COMMENT '结账时间',
  `pay_method` int(11) NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `orders_number_uindex`(`number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (11651195746672100, '11651149677613100', 4, 1518853017015697409, 1519231376832884737, '2022-04-28 20:41:17', '2022-04-28 20:41:17', 1, 196.00, '备注', '15222222222', '北京海淀区', 'zzx', 'kitty');
INSERT INTO `orders` VALUES (1519840789000146945, '11651193255341100', 4, 1518853017015697409, 1519231376832884737, '2022-04-29 08:47:35', '2022-04-29 08:47:35', 1, 168.00, '', '15222222222', '北京海淀区', 'zzx', 'kitty');
INSERT INTO `orders` VALUES (1621837620994449411, '1621837620994449410', 3, 1621166431154319362, 1621335145330040834, '2023-02-04 19:46:36', '2023-02-04 19:46:36', 1, 868.00, '加饭', '15345678988', '广东省云浮市', '伶铝疽可病', '钟xx');
INSERT INTO `orders` VALUES (1621849633988665346, '1621849633988665345', 2, 1621166431154319362, 1621170687399706626, '2023-02-04 20:34:20', '2023-02-04 20:34:20', 1, 540.00, '分开装', '15812398888', '广州市花都区', '伶铝疽可病', 'zzx');
INSERT INTO `orders` VALUES (1621855031969837058, '1621855031969837057', 2, 1621166431154319362, 1621170687399706626, '2023-02-04 20:55:47', '2023-02-04 20:55:47', 1, 306.00, '加饭谢谢老板', '15812398888', '广州市花都区', '伶铝疽可病', 'zzx');
INSERT INTO `orders` VALUES (1621865669303562242, '1621865669219676161', 2, 1621166431154319362, 1621170687399706626, '2023-02-04 21:38:03', '2023-02-04 21:38:03', 1, 572.00, '加饭', '15812398888', '广州市花都区', '伶铝疽可病', 'zzx');
INSERT INTO `orders` VALUES (1621894432112373762, '1621894432028487682', 3, 1621166431154319362, 1621170687399706626, '2023-02-04 23:32:21', '2023-02-04 23:32:21', 1, 155.00, '加多点饭，肉给多点谢谢老板', '15812398888', '广州市花都区', '伶铝疽可病', 'zzx');
INSERT INTO `orders` VALUES (1622071006006931458, '1622071006006931457', 2, 1621166431154319362, 1621172612820230145, '2023-02-05 11:13:59', '2023-02-05 11:13:59', 1, 256.00, '', '13266029438', '东莞市松山湖', '伶铝疽可病', 'zzx');
INSERT INTO `orders` VALUES (1622071595881263107, '1622071595881263106', 2, 1621166431154319362, 1621172612820230145, '2023-02-05 11:16:20', '2023-02-05 11:16:20', 1, 965.00, '', '13266029438', '东莞市松山湖', '伶铝疽可病', 'zzx');

-- ----------------------------
-- Table structure for setmeal
-- ----------------------------
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10, 2) NOT NULL COMMENT '套餐价格',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_setmeal_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal
-- ----------------------------
INSERT INTO `setmeal` VALUES (1415580119015145474, 1413386191767674881, '儿童套餐A计划', 18800.00, 1, '', '儿童套餐喵喵喵~', '2144ef04-7b87-4977-90b3-682e9f968513.jpg', '2021-07-15 15:52:55', '2023-02-03 12:40:54', 1415576781934608386, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1518510426785161219, 1619716634790940673, '白米饭套餐', 3400.00, 1, '', '', 'ce094396-ce87-48c3-885a-438a1603db03.jpg', '2022-04-25 16:41:12', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620650895899213825, 1413342269393674242, '鱼翅套餐', 8800.00, 1, '', '好好吃鱼翅', '58a1d096-599f-417b-bab7-09860c1533e0.jpg', '2023-02-01 21:10:16', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620661858023075841, 1620602668483465218, '鱼翅套餐2', 8700.00, 1, '', '的是发胜多负少', '3be3e986-c026-4a91-ae1d-8c3024c65941.jpg', '2023-02-01 13:54:32', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620662832976457729, 1620602668483465218, '畅饮套餐', 30000.00, 1, '', '好好吃', 'b6ec83a6-1002-4a16-b0f7-0197b3faa34f.png', '2023-02-01 13:58:25', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620771730676793346, 1620603620305260545, '鱼翅套餐3', 13800.00, 1, '', '呵呵哈哈哈太好吃了', '94ae3e03-bef1-4d29-901f-d08183ba5307.jpg', '2023-02-01 21:11:08', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620772434703302657, 1620602753283903489, 'a', 1100.00, 1, '', 'sadasdsa', '77aeb550-cc8c-4b76-bea6-93b468cefa4d.jpg', '2023-02-01 21:13:56', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620772505490571265, 1514518234500497409, 'bb', 2200.00, 1, '', 'asasfafaf', 'ce8cb888-6148-4f31-9ca7-c5d765325c9c.jpg', '2023-02-01 21:14:12', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620772600718049282, 1413342269393674242, 'cc', 3300.00, 1, '', 'adasdasdasd', '9464099a-5a5b-4c03-9452-d558950d524f.jpg', '2023-02-01 21:14:35', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620772704627736577, 1620603211595501569, 'dd', 4400.00, 1, '', 'asfsdgsd', 'd3faf204-6e31-43a3-9ca9-c26956e5b726.jpg', '2023-02-01 21:15:00', '2023-02-03 12:40:54', 1, 1621166431154319362, 0);
INSERT INTO `setmeal` VALUES (1620772808717778946, 1620603211595501569, 'ee', 5500.00, 1, '', 'egfsdfsdfsdf', 'b05cde41-cf8e-4722-ad8d-7cac7c12e719.jpg', '2023-02-01 21:15:25', '2023-02-02 10:26:48', 1, 1, 0);
INSERT INTO `setmeal` VALUES (1620772900703059970, 1413386191767674881, 'ff', 6600.00, 1, '', 'adasdasdasd', '891b393c-e664-4dae-b1b5-d9666bb33fdc.jpg', '2023-02-01 21:15:47', '2023-02-02 10:26:48', 1, 1, 0);
INSERT INTO `setmeal` VALUES (1620773021352214529, 1620602668483465218, 'gg', 7700.00, 1, '', '发水电费水电费水电费', '7e9e17eb-8b47-4a33-b7bd-5e6a48e302d3.jpg', '2023-02-01 21:16:15', '2023-02-02 10:26:48', 1, 1, 0);
INSERT INTO `setmeal` VALUES (1620773097306865666, 1620602668483465218, 'hh', 8800.00, 1, '', '暗示法是鬼斧神工', '542a4168-9cdb-4b2a-9d34-61ca0a927aad.jpg', '2023-02-01 21:16:34', '2023-02-02 10:26:48', 1, 1, 0);
INSERT INTO `setmeal` VALUES (1620773585930698753, 1620603162362761218, 'ii', 9900.00, 1, '', 'sdfsdfsdf', 'e18754fb-7d2b-41eb-a813-3e4f4bde7774.jpg', '2023-02-01 21:18:30', '2023-02-02 10:26:48', 1, 1, 0);

-- ----------------------------
-- Table structure for setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '菜品原价（冗余字段）',
  `copies` int(11) NOT NULL COMMENT '份数',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐菜品关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setmeal_dish
-- ----------------------------
INSERT INTO `setmeal_dish` VALUES (1620772434900434945, '1620772434703302657', '1397850851245600769', '霸王别姬', 12800.00, 1, 0, '2023-02-01 21:13:56', '2023-02-01 21:13:56', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772434900434946, '1620772434703302657', '1397851668262465537', '口味蛇', 16800.00, 1, 0, '2023-02-01 21:13:56', '2023-02-01 21:13:56', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772505754812417, '1620772505490571265', '1397851370462687234', '邵阳猪血丸子', 13800.00, 1, 0, '2023-02-01 21:14:13', '2023-02-01 21:14:13', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772505754812418, '1620772505490571265', '1397851099502260226', '全家福', 11800.00, 1, 0, '2023-02-01 21:14:13', '2023-02-01 21:14:13', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772505754812419, '1620772505490571265', '1620081985277767681', 'bbb', 8800.00, 1, 0, '2023-02-01 21:14:13', '2023-02-01 21:14:13', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772600718049283, '1620772600718049282', '1397851099502260226', '全家福', 11800.00, 1, 0, '2023-02-01 21:14:35', '2023-02-01 21:14:35', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772600718049284, '1620772600718049282', '1620081985277767681', 'bbb', 8800.00, 2, 0, '2023-02-01 21:14:35', '2023-02-01 21:14:35', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772600785158146, '1620772600718049282', '1518377248351068162', '好菜菜', 1700.00, 2, 0, '2023-02-01 21:14:35', '2023-02-01 21:14:35', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772704694845441, '1620772704627736577', '1413385247889891330', '米饭', 200.00, 1, 0, '2023-02-01 21:15:00', '2023-02-01 21:15:00', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772704694845442, '1620772704627736577', '1620084954421362690', '巴氏鲜奶', 800.00, 1, 0, '2023-02-01 21:15:00', '2023-02-01 21:15:00', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772704694845443, '1620772704627736577', '1518377248351068162', '好菜菜', 1700.00, 1, 0, '2023-02-01 21:15:00', '2023-02-01 21:15:00', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772704694845444, '1620772704627736577', '1397849739276890114', '辣子鸡鸡', 3800.00, 2, 0, '2023-02-01 21:15:00', '2023-02-01 21:15:00', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772704694845445, '1620772704627736577', '1397852391150759938', '辣子鸡丁', 8800.00, 1, 0, '2023-02-01 21:15:00', '2023-02-01 21:15:00', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620772704694845446, '1620772704627736577', '1397853890262118402', '鱼香肉丝', 3800.00, 1, 0, '2023-02-01 21:15:00', '2023-02-01 21:15:00', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804457275125761, '1620661858023075841', '1397850392090947585', '组庵鱼翅', 4800.00, 1, 0, '2023-02-01 23:21:10', '2023-02-01 23:21:10', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804457275125762, '1620661858023075841', '1397851668262465537', '口味蛇', 16800.00, 1, 0, '2023-02-01 23:21:10', '2023-02-01 23:21:10', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804457275125763, '1620661858023075841', '1413385247889891330', '米饭', 200.00, 1, 0, '2023-02-01 23:21:10', '2023-02-01 23:21:10', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804671138492417, '1620662832976457729', '1620289478452850689', '酸奶', 500.00, 1, 0, '2023-02-01 23:22:01', '2023-02-01 23:22:01', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804671138492418, '1620662832976457729', '1518377248351068161', '珍珠奶茶', 1700.00, 1, 0, '2023-02-01 23:22:01', '2023-02-01 23:22:01', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804671138492419, '1620662832976457729', '1413384757047271425', '王老吉', 500.00, 1, 0, '2023-02-01 23:22:01', '2023-02-01 23:22:01', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804671138492420, '1620662832976457729', '1413342036832100354', '北冰洋', 500.00, 1, 0, '2023-02-01 23:22:01', '2023-02-01 23:22:01', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804671138492421, '1620662832976457729', '1413385247889891330', '米饭', 200.00, 1, 0, '2023-02-01 23:22:01', '2023-02-01 23:22:01', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804671138492422, '1620662832976457729', '1397850140982161409', '毛氏红烧肉', 6800.00, 1, 0, '2023-02-01 23:22:01', '2023-02-01 23:22:01', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804765493555202, '1620771730676793346', '1397850140982161409', '毛氏红烧肉', 6800.00, 1, 0, '2023-02-01 23:22:24', '2023-02-01 23:22:24', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804765556469762, '1620771730676793346', '1397850392090947585', '组庵鱼翅', 4800.00, 1, 0, '2023-02-01 23:22:24', '2023-02-01 23:22:24', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804765556469763, '1620771730676793346', '1397851099502260226', '全家福', 11800.00, 1, 0, '2023-02-01 23:22:24', '2023-02-01 23:22:24', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804765556469764, '1620771730676793346', '1413385247889891330', '米饭', 200.00, 2, 0, '2023-02-01 23:22:24', '2023-02-01 23:22:24', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620804765556469765, '1620771730676793346', '1620084954421362690', '巴氏鲜奶', 800.00, 1, 0, '2023-02-01 23:22:24', '2023-02-01 23:22:24', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620971308193775618, '1518510426785161219', '1413385247889891330', '米饭', 200.00, 2, 0, '2023-02-02 10:24:11', '2023-02-02 10:24:11', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1620971308193775619, '1518510426785161219', '1397853890262118402', '鱼香肉丝', 3800.00, 1, 0, '2023-02-02 10:24:11', '2023-02-02 10:24:11', 1, 1, 0);
INSERT INTO `setmeal_dish` VALUES (1621068827120730114, '1415580119015145474', '1397850140982161409', '毛氏红烧肉', 6800.00, 1, 0, '2023-02-02 16:51:41', '2023-02-02 16:51:41', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621068827120730115, '1415580119015145474', '1397850851245600769', '霸王别姬', 12800.00, 1, 0, '2023-02-02 16:51:41', '2023-02-02 16:51:41', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621068827183644674, '1415580119015145474', '1397851668262465537', '口味蛇', 16800.00, 1, 0, '2023-02-02 16:51:41', '2023-02-02 16:51:41', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621068827183644675, '1415580119015145474', '1518377248351068161', '珍珠奶茶', 1700.00, 1, 0, '2023-02-02 16:51:41', '2023-02-02 16:51:41', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621068827183644676, '1415580119015145474', '1620289478452850689', '酸奶', 500.00, 1, 0, '2023-02-02 16:51:41', '2023-02-02 16:51:41', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621068827183644677, '1415580119015145474', '1413385247889891330', '米饭', 200.00, 1, 0, '2023-02-02 16:51:41', '2023-02-02 16:51:41', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621069935725608962, '1620650895899213825', '1397850140982161409', '毛氏红烧肉', 6800.00, 1, 0, '2023-02-02 16:56:05', '2023-02-02 16:56:05', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621069935792717826, '1620650895899213825', '1413385247889891330', '米饭', 200.00, 1, 0, '2023-02-02 16:56:05', '2023-02-02 16:56:05', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621069935792717827, '1620650895899213825', '1620084954421362690', '巴氏鲜奶', 800.00, 1, 0, '2023-02-02 16:56:05', '2023-02-02 16:56:05', 1619206012562931714, 1619206012562931714, 0);
INSERT INTO `setmeal_dish` VALUES (1621069935792717828, '1620650895899213825', '1620622395678986241', '冒菜', 4500.00, 1, 0, '2023-02-02 16:56:05', '2023-02-02 16:56:05', 1619206012562931714, 1619206012562931714, 0);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint(20) NOT NULL COMMENT '主键',
  `dish_id` bigint(20) NULL DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) NULL DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` int(11) NOT NULL DEFAULT 1 COMMENT '是否删除',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES (1621788605296975873, '鱼翅套餐', '58a1d096-599f-417b-bab7-09860c1533e0.jpg', 1621173196398907394, NULL, 1620650895899213825, NULL, 4, 88.00, '2023-02-04 16:31:50', 0, '2023-02-04 16:31:59');
INSERT INTO `shopping_cart` VALUES (1621788667657887745, '邵阳猪血丸子', '224681bc-34b4-467b-9243-5ed7719cbd1d.jpg', 1621173196398907394, 1397851370462687234, NULL, '不要香菜,重辣', 2, 138.00, '2023-02-04 16:32:04', 0, '2023-02-04 16:32:20');
INSERT INTO `shopping_cart` VALUES (1621788711962320898, '邵阳猪血丸子', '224681bc-34b4-467b-9243-5ed7719cbd1d.jpg', 1621173196398907394, 1397851370462687234, NULL, '不要香菜,不辣', 1, 138.00, '2023-02-04 16:32:15', 0, '2023-02-04 16:32:15');
INSERT INTO `shopping_cart` VALUES (1621863480359251969, '口味蛇', '354e7218-32ad-4559-82d4-92472f4cdfdf.jpg', 1, 1397851668262465537, NULL, '去冰', 3, 168.00, '2023-02-04 21:29:21', 1, '2023-02-04 21:29:30');
INSERT INTO `shopping_cart` VALUES (1621897071789207553, '面包', '46407ec4-cdc5-42d8-b34f-b193a9753d67.jpg', 1, 1621896459890585601, NULL, '全糖', 1, 7.00, '2023-02-04 23:42:50', 1, '2023-02-04 23:42:50');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
  `status` int(11) NULL DEFAULT 0 COMMENT '状态 0:禁用，1:正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_phone_uindex`(`phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1518853017015697409, 'zzx', '13231901277', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1519299126825664514, '蜘蛛侠', '15222222222', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1621166431154319362, '伶铝疽可病', '15832197788', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1621173196398907394, '指靛跺钟恃', '13866029378', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1621859331202740226, '堡规蠢洛凶', '13417989752', NULL, NULL, NULL, 1);
INSERT INTO `user` VALUES (1621862261851025409, '膜颤黔哆竿', '15189577623', NULL, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
