SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
                               `categoryId` varchar(10) NOT NULL,
                               `categoryName` varchar(10) NOT NULL,
                               `description` varchar(100) DEFAULT NULL,
                               PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
BEGIN;
INSERT INTO `tb_category` VALUES ('c0001', '日用品', '日用品');
INSERT INTO `tb_category` VALUES ('c0002', '食物', '食物');
INSERT INTO `tb_category` VALUES ('c0003', '饮料', '饮料');
COMMIT;

-- ----------------------------
-- Table structure for tb_document
-- ----------------------------
DROP TABLE IF EXISTS `tb_document`;
CREATE TABLE `tb_document` (
                               `documentId` varchar(14) NOT NULL,
                               `price` decimal(10,2) NOT NULL DEFAULT 0.00,
                               `orderTime` datetime NOT NULL DEFAULT current_timestamp(),
                               `userAccount` varchar(11) NOT NULL,
                               `type` enum('SALE','PURCHASE','IMPORT','EXPORT') NOT NULL,
                               `note` varchar(100) DEFAULT NULL,
                               PRIMARY KEY (`documentId`),
                               KEY `tb_document_tb_user_userAccount_fk` (`userAccount`),
                               CONSTRAINT `tb_document_tb_user_userAccount_fk` FOREIGN KEY (`userAccount`) REFERENCES `tb_user` (`userAccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_document
-- ----------------------------
BEGIN;
INSERT INTO `tb_document` VALUES ('P1608488763870', 2115.00, '2020-12-20 18:26:03', '18383132610', 'PURCHASE', '');
INSERT INTO `tb_document` VALUES ('P1608528273026', 111.50, '2020-12-21 05:24:33', '18383132610', 'PURCHASE', '');
INSERT INTO `tb_document` VALUES ('S1608487732173', 1400.00, '2020-12-20 18:08:52', '18383132610', 'SALE', 'test');
INSERT INTO `tb_document` VALUES ('S1608488675330', 800.00, '2020-12-20 18:24:35', '18383132610', 'SALE', 'test');
INSERT INTO `tb_document` VALUES ('S1608488905312', 1084.00, '2020-12-20 18:28:25', '18383132610', 'SALE', '');
INSERT INTO `tb_document` VALUES ('S1608528256062', 90.00, '2020-12-21 05:24:16', '18383132610', 'SALE', '');
INSERT INTO `tb_document` VALUES ('S1608530151704', 74.00, '2020-12-21 05:55:51', '18383132610', 'SALE', 'es');
COMMIT;

-- ----------------------------
-- Table structure for tb_document_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_document_detail`;
CREATE TABLE `tb_document_detail` (
                                      `documentId` varchar(14) NOT NULL,
                                      `goodId` varchar(10) NOT NULL,
                                      `amount` int(11) NOT NULL,
                                      `price` decimal(10,2) NOT NULL DEFAULT 0.00,
                                      PRIMARY KEY (`documentId`,`goodId`),
                                      KEY `tb_document_detail_tb_good_goodId_fk` (`goodId`),
                                      CONSTRAINT `tb_document_detail_tb_document_documentId_fk` FOREIGN KEY (`documentId`) REFERENCES `tb_document` (`documentId`),
                                      CONSTRAINT `tb_document_detail_tb_good_goodId_fk` FOREIGN KEY (`goodId`) REFERENCES `tb_good` (`goodId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_document_detail
-- ----------------------------
BEGIN;
INSERT INTO `tb_document_detail` VALUES ('P1608488763870', '20200001', 5, 500.00);
INSERT INTO `tb_document_detail` VALUES ('P1608488763870', '20200002', 5, 1615.00);
INSERT INTO `tb_document_detail` VALUES ('P1608528273026', '20200001', 14, 14.00);
INSERT INTO `tb_document_detail` VALUES ('P1608528273026', '20200002', 5, 25.00);
INSERT INTO `tb_document_detail` VALUES ('P1608528273026', '20200003', 5, 10.00);
INSERT INTO `tb_document_detail` VALUES ('P1608528273026', '20200004', 5, 60.00);
INSERT INTO `tb_document_detail` VALUES ('P1608528273026', '20200005', 5, 2.50);
INSERT INTO `tb_document_detail` VALUES ('S1608487732173', '20200001', 7, 1400.00);
INSERT INTO `tb_document_detail` VALUES ('S1608488675330', '20200001', 4, 800.00);
INSERT INTO `tb_document_detail` VALUES ('S1608488905312', '20200001', 2, 400.00);
INSERT INTO `tb_document_detail` VALUES ('S1608488905312', '20200002', 2, 684.00);
INSERT INTO `tb_document_detail` VALUES ('S1608528256062', '20200001', 5, 15.00);
INSERT INTO `tb_document_detail` VALUES ('S1608528256062', '20200002', 1, 15.00);
INSERT INTO `tb_document_detail` VALUES ('S1608528256062', '20200003', 1, 10.00);
INSERT INTO `tb_document_detail` VALUES ('S1608528256062', '20200004', 1, 50.00);
INSERT INTO `tb_document_detail` VALUES ('S1608530151704', '20200001', 3, 9.00);
INSERT INTO `tb_document_detail` VALUES ('S1608530151704', '20200002', 1, 15.00);
INSERT INTO `tb_document_detail` VALUES ('S1608530151704', '20200004', 1, 50.00);
COMMIT;

-- ----------------------------
-- Table structure for tb_good
-- ----------------------------
DROP TABLE IF EXISTS `tb_good`;
CREATE TABLE `tb_good` (
                           `goodId` varchar(10) NOT NULL,
                           `goodName` varchar(20) NOT NULL,
                           `categoryId` varchar(10) DEFAULT NULL,
                           `retailPrice` decimal(10,2) NOT NULL DEFAULT 0.00,
                           `purchasePrice` decimal(10,2) NOT NULL DEFAULT 0.00,
                           `inventory` int(11) NOT NULL DEFAULT 0,
                           `supplierId` varchar(10) DEFAULT NULL,
                           `status` enum('ENABLE','DISABLE') NOT NULL DEFAULT 'ENABLE',
                           `description` varchar(100) DEFAULT NULL,
                           PRIMARY KEY (`goodId`),
                           KEY `tb_good_tb_category_categoryId_fk` (`categoryId`),
                           KEY `tb_good_tb_supplier_supplierId_fk` (`supplierId`),
                           CONSTRAINT `tb_good_tb_category_categoryId_fk` FOREIGN KEY (`categoryId`) REFERENCES `tb_category` (`categoryId`),
                           CONSTRAINT `tb_good_tb_supplier_supplierId_fk` FOREIGN KEY (`supplierId`) REFERENCES `tb_supplier` (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_good
-- ----------------------------
BEGIN;
INSERT INTO `tb_good` VALUES ('20200001', '辣条', 'c0002', 3.00, 1.00, 9, 's0001', 'ENABLE', '');
INSERT INTO `tb_good` VALUES ('20200002', '洗衣粉', 'c0001', 15.00, 5.00, 6, 's0002', 'ENABLE', 'test');
INSERT INTO `tb_good` VALUES ('20200003', '牙刷', 'c0001', 10.00, 2.00, 4, 's0002', 'ENABLE', '');
INSERT INTO `tb_good` VALUES ('20200004', '沐浴露', 'c0001', 50.00, 12.00, 3, 's0002', 'ENABLE', '');
INSERT INTO `tb_good` VALUES ('20200005', '啤酒', 'c0003', 6.80, 0.50, 5, 's0001', 'ENABLE', '');
INSERT INTO `tb_good` VALUES ('20200006', '可乐', 'c0002', 3.00, 1.00, 0, 's0001', 'ENABLE', '');
INSERT INTO `tb_good` VALUES ('20200007', '方便面', 'c0002', 5.00, 2.00, 0, 's0001', 'ENABLE', '');
COMMIT;

-- ----------------------------
-- Table structure for tb_supplier
-- ----------------------------
DROP TABLE IF EXISTS `tb_supplier`;
CREATE TABLE `tb_supplier` (
                               `supplierId` varchar(10) NOT NULL,
                               `supplierName` varchar(20) NOT NULL,
                               `address` varchar(100) DEFAULT NULL,
                               `phone` char(11) DEFAULT NULL,
                               PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_supplier
-- ----------------------------
BEGIN;
INSERT INTO `tb_supplier` VALUES ('s0001', '供应商1', '上海海事大学', '88888888888');
INSERT INTO `tb_supplier` VALUES ('s0002', '供应商2', '上海海事大学', '88888888888');
INSERT INTO `tb_supplier` VALUES ('s0003', '供应商3', '上海海事大学', '88888888888');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
                           `userAccount` varchar(11) NOT NULL,
                           `userName` varchar(20) NOT NULL,
                           `passwd` char(32) NOT NULL,
                           `age` int(11) NOT NULL DEFAULT 0,
                           `gender` enum('UNKNOWN','MALE','FEMALE','FEMALE_TO_MALE','MALE_TO_FEMALE','OTHER') NOT NULL DEFAULT 'UNKNOWN',
                           `userRole` enum('ADMINISTRATOR','EMPLOYEE') NOT NULL DEFAULT 'EMPLOYEE',
                           `status` enum('ENABLE','DISABLE','UNACTIVATED') NOT NULL DEFAULT 'UNACTIVATED',
                           `email` varchar(30) NOT NULL,
                           `activeCode` varchar(50) DEFAULT NULL,
                           PRIMARY KEY (`userAccount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES ('13419084421', 'liyang', 'c1d909dd260c1a926a3089389dca4626', 0, 'MALE', 'EMPLOYEE', 'ENABLE', '18383132610@163.com', '3ac8789d1e67443489285604f2cfbd0a');
INSERT INTO `tb_user` VALUES ('17855784275', 'huxinzhou', '8feacdf96c3153feb2842ff06a775d12', 0, 'MALE', 'EMPLOYEE', 'ENABLE', '18383132610@163.com', '14de55329c864bc896b617733e3d0a3b');
INSERT INTO `tb_user` VALUES ('18383132610', 'admin', '250bfb3714e9a56043822d7efe0dced2', 20, 'MALE', 'ADMINISTRATOR', 'ENABLE', '1353011752@qq.com', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;