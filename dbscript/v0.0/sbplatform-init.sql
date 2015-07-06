
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cgform_t_button`
-- ----------------------------
DROP TABLE IF EXISTS `cgform_t_button`;
CREATE TABLE `cgform_t_button` (
  `ID` varchar(32) NOT NULL,
  `BUTTON_CODE` varchar(50) DEFAULT NULL,
  `button_icon` varchar(20) DEFAULT NULL,
  `BUTTON_NAME` varchar(50) DEFAULT NULL,
  `BUTTON_STATUS` varchar(2) DEFAULT NULL,
  `BUTTON_STYLE` varchar(20) DEFAULT NULL,
  `EXP` varchar(255) DEFAULT NULL,
  `FORM_ID` varchar(32) DEFAULT NULL,
  `OPT_TYPE` varchar(20) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgform_t_button
-- ----------------------------

-- ----------------------------
-- Table structure for `cgform_t_button_sql`
-- ----------------------------
DROP TABLE IF EXISTS `cgform_t_button_sql`;
CREATE TABLE `cgform_t_button_sql` (
  `ID` varchar(32) NOT NULL,
  `BUTTON_CODE` varchar(50) DEFAULT NULL,
  `CGB_SQL` tinyblob,
  `CGB_SQL_NAME` varchar(50) DEFAULT NULL,
  `CONTENT` longtext,
  `FORM_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgform_t_button_sql
-- ----------------------------

-- ----------------------------
-- Table structure for `cgform_t_enhance_js`
-- ----------------------------
DROP TABLE IF EXISTS `cgform_t_enhance_js`;
CREATE TABLE `cgform_t_enhance_js` (
  `ID` varchar(32) NOT NULL,
  `CG_JS` blob,
  `CG_JS_TYPE` varchar(20) DEFAULT NULL,
  `CONTENT` longtext,
  `FORM_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgform_t_enhance_js
-- ----------------------------

-- ----------------------------
-- Table structure for `cgform_t_field`
-- ----------------------------
DROP TABLE IF EXISTS `cgform_t_field`;
CREATE TABLE `cgform_t_field` (
  `id` varchar(32) NOT NULL,
  `content` varchar(200) NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `dict_field` varchar(100) DEFAULT NULL,
  `dict_table` varchar(100) DEFAULT NULL,
  `dict_text` varchar(100) DEFAULT NULL,
  `field_default` varchar(20) DEFAULT NULL,
  `field_href` varchar(200) DEFAULT NULL,
  `field_length` int(11) DEFAULT NULL,
  `field_name` varchar(32) NOT NULL,
  `field_valid_type` varchar(10) DEFAULT NULL,
  `is_key` varchar(2) DEFAULT NULL,
  `is_null` varchar(5) DEFAULT NULL,
  `is_query` varchar(5) DEFAULT NULL,
  `is_show` varchar(5) DEFAULT NULL,
  `is_show_list` varchar(5) DEFAULT NULL,
  `length` int(11) NOT NULL,
  `main_field` varchar(100) DEFAULT NULL,
  `main_table` varchar(100) DEFAULT NULL,
  `old_field_name` varchar(32) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `point_length` int(11) DEFAULT NULL,
  `query_mode` varchar(10) DEFAULT NULL,
  `show_type` varchar(10) DEFAULT NULL,
  `type` varchar(32) NOT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  `table_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgform_t_field
-- ----------------------------

-- ----------------------------
-- Table structure for `cgform_t_ftl`
-- ----------------------------
DROP TABLE IF EXISTS `cgform_t_ftl`;
CREATE TABLE `cgform_t_ftl` (
  `ID` varchar(32) NOT NULL,
  `CGFORM_ID` varchar(36) NOT NULL,
  `CGFORM_NAME` varchar(100) NOT NULL,
  `CREATE_BY` varchar(36) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_NAME` varchar(32) DEFAULT NULL,
  `FTL_CONTENT` longtext,
  `FTL_STATUS` varchar(50) DEFAULT NULL,
  `FTL_VERSION` int(11) NOT NULL,
  `FTL_WORD_URL` varchar(200) DEFAULT NULL,
  `UPDATE_BY` varchar(36) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `UPDATE_NAME` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgform_t_ftl
-- ----------------------------

-- ----------------------------
-- Table structure for `cgform_t_head`
-- ----------------------------
DROP TABLE IF EXISTS `cgform_t_head`;
CREATE TABLE `cgform_t_head` (
  `id` varchar(32) NOT NULL,
  `content` varchar(200) NOT NULL,
  `create_by` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `create_name` varchar(32) DEFAULT NULL,
  `is_checkbox` varchar(5) NOT NULL,
  `is_dbsynch` varchar(20) NOT NULL,
  `is_pagination` varchar(5) NOT NULL,
  `is_tree` varchar(5) NOT NULL,
  `jform_pk_sequence` varchar(200) DEFAULT NULL,
  `jform_pk_type` varchar(100) DEFAULT NULL,
  `jform_type` int(11) NOT NULL,
  `jform_version` varchar(10) NOT NULL,
  `querymode` varchar(10) NOT NULL,
  `relation_type` int(11) DEFAULT NULL,
  `sub_table_str` longtext,
  `tab_order` int(11) DEFAULT NULL,
  `table_name` varchar(20) NOT NULL,
  `update_by` varchar(32) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgform_t_head
-- ----------------------------

-- ----------------------------
-- Table structure for `cgform_t_uploadfiles`
-- ----------------------------
DROP TABLE IF EXISTS `cgform_t_uploadfiles`;
CREATE TABLE `cgform_t_uploadfiles` (
  `CGFORM_FIELD` varchar(100) NOT NULL,
  `CGFORM_ID` varchar(36) NOT NULL,
  `CGFORM_NAME` varchar(100) NOT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_on950rx4uy79hlqtuv7ityatf` (`id`),
  CONSTRAINT `FK_on950rx4uy79hlqtuv7ityatf` FOREIGN KEY (`id`) REFERENCES `s_t_attachment` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgform_t_uploadfiles
-- ----------------------------

-- ----------------------------
-- Table structure for `cgreport_t_head`
-- ----------------------------
DROP TABLE IF EXISTS `cgreport_t_head`;
CREATE TABLE `cgreport_t_head` (
  `ID` varchar(36) NOT NULL,
  `CGR_SQL` longtext NOT NULL,
  `CODE` varchar(36) NOT NULL,
  `CONTENT` longtext NOT NULL,
  `NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgreport_t_head
-- ----------------------------

-- ----------------------------
-- Table structure for `cgreport_t_item`
-- ----------------------------
DROP TABLE IF EXISTS `cgreport_t_item`;
CREATE TABLE `cgreport_t_item` (
  `ID` varchar(36) NOT NULL,
  `CGRHEAD_ID` varchar(36) DEFAULT NULL,
  `DICT_CODE` varchar(36) DEFAULT NULL,
  `FIELD_HREF` varchar(120) DEFAULT NULL,
  `FIELD_NAME` varchar(36) DEFAULT NULL,
  `FIELD_TXT` longtext,
  `FIELD_TYPE` varchar(10) DEFAULT NULL,
  `IS_SHOW` varchar(5) DEFAULT NULL,
  `ORDER_NUM` int(11) DEFAULT NULL,
  `REPLACE_VA` varchar(36) DEFAULT NULL,
  `S_FLAG` varchar(2) DEFAULT NULL,
  `S_MODE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cgreport_t_item
-- ----------------------------

-- ----------------------------
-- Table structure for `s_t_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_attachment`;
CREATE TABLE `s_t_attachment` (
  `ID` varchar(32) NOT NULL,
  `ATTACHMENT_CONTENT` longblob,
  `ATTACHMENT_NAME` varchar(100) DEFAULT NULL,
  `BUSINESS_KEY` varchar(32) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `EXTEND_NAME` varchar(32) DEFAULT NULL,
  `NOTE` longtext,
  `REAL_PATH` varchar(100) DEFAULT NULL,
  `SUB_CLASS_NAME` longtext,
  `SWF_PATH` longtext,
  `ATTACHMENTCONTENT` longblob,
  `ATTACHMENTTITLE` varchar(100) DEFAULT NULL,
  `BUSENTITYNAME` varchar(100) DEFAULT NULL,
  `BUSINESSKEY` varchar(32) DEFAULT NULL,
  `CREATEDATE` datetime DEFAULT NULL,
  `EXTEND` varchar(32) DEFAULT NULL,
  `INFOTYPEID` varchar(32) DEFAULT NULL,
  `REALPATH` varchar(100) DEFAULT NULL,
  `SUBCLASSNAME` longtext,
  `SWFPATH` longtext,
  `USERID` varchar(32) DEFAULT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_wybi1c3jcunl3q3kettl0kul` (`USER_ID`),
  CONSTRAINT `FK_wybi1c3jcunl3q3kettl0kul` FOREIGN KEY (`USER_ID`) REFERENCES `s_t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_attachment
-- ----------------------------
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a846b0008', null, 'JR079839867R90000001000', null, null, 'doc', null, 'JR079839867R90000001000', null, 'upload/files/20130719201109hDr31jP1.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a846e0009', null, 'SB平台协议', null, null, 'docx', null, 'SB平台协议', null, 'upload/files/20130719201156sYHjSFJj.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a846e000a', null, '分析SB与其他的开源项目的不足和优势', null, null, 'docx', null, '分析SB与其他的开源项目的不足和优势', null, 'upload/files/20130719201727ZLEX1OSf.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a846f000b', null, 'DMS-T3第三方租赁业务接口开发说明', null, null, 'docx', null, 'DMS-T3第三方租赁业务接口开发说明', null, 'upload/files/20130719201841LzcgqUek.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a8470000c', null, 'SAP-需求说明书-金融服务公司-第三方租赁业务需求V1.7-研发', null, null, 'doc', null, 'SAP-需求说明书-金融服务公司-第三方租赁业务需求V1.7-研发', null, 'upload/files/20130719201925mkCrU47P.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a8472000d', null, 'SB团队开发规范', null, null, 'txt', null, 'SB团队开发规范', null, 'upload/files/20130724103633fvOTwNSV.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a8473000e', null, '第一模板', null, null, 'doc', null, '第一模板', null, 'upload/files/20130724104603pHDw4QUT.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a8474000f', null, 'github入门使用教程', null, null, 'doc', null, 'github入门使用教程', null, 'upload/files/20130704200345EakUH3WB.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a84750010', null, 'github入门使用教程', null, null, 'doc', null, 'github入门使用教程', null, 'upload/files/20130704200651IE8wPdZ4.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a84760011', null, '（黄世民）-金融服务公司机构岗位职责与任职资格设置表(根据模板填写）', null, null, 'xlsx', null, '（黄世民）-金融服务公司机构岗位职责与任职资格设置表(根据模板填写）', null, 'upload/files/20130704201022KhdRW1Gd.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a84770012', null, 'EIM201_CN', null, null, 'pdf', null, 'EIM201_CN', null, 'upload/files/20130704201046JVAkvvOt.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a84790013', null, 'github入门使用教程', null, null, 'doc', null, 'github入门使用教程', null, 'upload/files/20130704201116Z8NhEK57.swf', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `s_t_attachment` VALUES ('2c99cac94ade0a33014ade0a847b0014', null, 'SBUI标签库帮助文档v3.2', null, null, 'pdf', null, 'SBUI标签库帮助文档v3.2', null, 'upload/files/20130704201125DQg8hi2x.swf', null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `s_t_config`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_config`;
CREATE TABLE `s_t_config` (
  `ID` varchar(32) NOT NULL,
  `CODE` varchar(100) DEFAULT NULL,
  `CONTENT` longtext,
  `NAME` varchar(100) NOT NULL,
  `NOTE` longtext,
  `USER_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_rymm7rh8htsj99jy7gh58lgt6` (`USER_ID`),
  CONSTRAINT `FK_rymm7rh8htsj99jy7gh58lgt6` FOREIGN KEY (`USER_ID`) REFERENCES `s_t_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_config
-- ----------------------------

-- ----------------------------
-- Table structure for `s_t_demo`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_demo`;
CREATE TABLE `s_t_demo` (
  `ID` varchar(32) NOT NULL,
  `DEMO_CODE` longtext,
  `DEMO_ORDER` smallint(6) DEFAULT NULL,
  `DEMO_TITLE` varchar(200) DEFAULT NULL,
  `DEMO_URL` varchar(200) DEFAULT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_hal3w31eephauwwr7esdi70vu` (`PARENT_ID`),
  CONSTRAINT `FK_hal3w31eephauwwr7esdi70vu` FOREIGN KEY (`PARENT_ID`) REFERENCES `s_t_demo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_demo
-- ----------------------------
INSERT INTO `s_t_demo` VALUES ('2c99cac94ade0a33014ade0a861f00c7', '<div class=\"form\">\r\n   <label class=\"Validform_label\">\r\n     非空验证：\r\n    </label>\r\n    <input name=\"demotitle\" id=\"demotitle\" datatype=\"*\" errormsg=\"该字段不为空\">\r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>\r\n   <div class=\"form\">\r\n     <label class=\"Validform_label\">\r\n     URL验证：\r\n    </label>\r\n    <input name=\"demourl\" id=\"demourl\" datatype=\"url\" errormsg=\"必须是URL\">\r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>\r\n   <div class=\"form\">\r\n     <label class=\"Validform_label\">\r\n     至少选择2项：\r\n    </label>\r\n    <input name=\"shoppingsite1\" class=\"rt2\" id=\"shoppingsite21\" type=\"checkbox\" value=\"1\" datatype=\"need2\" nullmsg=\"请选择您的爱好！\" />\r\n  \r\n     阅读\r\n  \r\n    <input name=\"shoppingsite1\" class=\"rt2\" id=\"shoppingsite22\" type=\"checkbox\" value=\"2\" />\r\n    \r\n     音乐\r\n  \r\n    <input name=\"shoppingsite1\" class=\"rt2\" id=\"shoppingsite23\" type=\"checkbox\" value=\"3\" />\r\n  \r\n     运动\r\n   \r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>\r\n   <div class=\"form\">\r\n     <label class=\"Validform_label\">\r\n     邮箱：\r\n    </label>\r\n    <input name=\"demoorder\" id=\"demoorder\" datatype=\"e\" errormsg=\"邮箱非法\">\r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>\r\n   <div class=\"form\">\r\n     <label class=\"Validform_label\">\r\n     手机号：\r\n    </label>\r\n    <input name=\"phone\" id=\"phone\" datatype=\"m\" errormsg=\"手机号非法\">\r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>\r\n   <div class=\"form\">\r\n     <label class=\"Validform_label\">\r\n     金额：\r\n    </label>\r\n    <input name=\"money\" id=\"money\" datatype=\"d\" errormsg=\"金额非法\">\r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>\r\n   <div class=\"form\">\r\n     <label class=\"Validform_label\">\r\n     日期：\r\n    </label>\r\n    <input name=\"date\" id=\"date\" class=\"easyui-datebox\">\r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>\r\n   <div class=\"form\">\r\n     <label class=\"Validform_label\">\r\n     时间：\r\n    </label>\r\n    <input name=\"time\" id=\"time\" class=\"easyui-datetimebox\">\r\n    <span class=\"Validform_checktip\"></span>\r\n   </div>', null, '表单验证', null, null);

-- ----------------------------
-- Table structure for `s_t_department`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_department`;
CREATE TABLE `s_t_department` (
  `ID` varchar(32) NOT NULL,
  `DESCRIPTION` longtext,
  `NAME` varchar(100) NOT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_mt2r7ggq9d5qxnatujsxftp4c` (`PARENT_ID`),
  CONSTRAINT `FK_mt2r7ggq9d5qxnatujsxftp4c` FOREIGN KEY (`PARENT_ID`) REFERENCES `s_t_department` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_department
-- ----------------------------
INSERT INTO `s_t_department` VALUES ('2c99cac94ade0a33014ade0a847c0015', '12', '信息部', null);
INSERT INTO `s_t_department` VALUES ('2c99cac94ade0a33014ade0a84800016', null, '设计部', null);
INSERT INTO `s_t_department` VALUES ('2c99cac94ade0a33014ade0a84820017', '研发技术难题', '研发室', '2c99cac94ade0a33014ade0a84800016');

-- ----------------------------
-- Table structure for `s_t_document`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_document`;
CREATE TABLE `s_t_document` (
  `DOCUMENT_STATE` smallint(6) DEFAULT NULL,
  `DOCUMENT_TITLE` varchar(100) DEFAULT NULL,
  `PICTURE_INDEX` blob,
  `SHOW_HOME` smallint(6) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  `TYPE_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p21vt59plcv7eilerrec19icx` (`TYPE_ID`),
  KEY `FK_4narx5cmd332tyluk37ntbkvo` (`id`),
  CONSTRAINT `FK_4narx5cmd332tyluk37ntbkvo` FOREIGN KEY (`id`) REFERENCES `s_t_attachment` (`ID`),
  CONSTRAINT `FK_p21vt59plcv7eilerrec19icx` FOREIGN KEY (`TYPE_ID`) REFERENCES `s_t_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_document
-- ----------------------------

-- ----------------------------
-- Table structure for `s_t_fileno`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_fileno`;
CREATE TABLE `s_t_fileno` (
  `ID` varchar(32) NOT NULL,
  `FILENO_BEFORE` varchar(32) DEFAULT NULL,
  `FILENO_NUM` int(11) DEFAULT NULL,
  `filenotype` varchar(32) DEFAULT NULL,
  `fileno_Year` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_fileno
-- ----------------------------

-- ----------------------------
-- Table structure for `s_t_icon`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_icon`;
CREATE TABLE `s_t_icon` (
  `ID` varchar(32) NOT NULL,
  `EXTEND_NAME` varchar(255) DEFAULT NULL,
  `ICON_CLASS` varchar(200) DEFAULT NULL,
  `ICON_CONTENT` blob,
  `ICON_NAME` varchar(100) NOT NULL,
  `ICON_PATH` longtext,
  `ICON_TYPE` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_icon
-- ----------------------------
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a84510000', 'png', 'back', null, '返回', 'plug-in/accordion/images/back.png', '1');
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a84660001', 'png', 'pie', null, '饼图', 'plug-in/accordion/images/pie.png', '1');
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a84670002', 'png', 'pictures', null, '图片', 'plug-in/accordion/images/pictures.png', '1');
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a84670003', 'png', 'pencil', null, '笔', 'plug-in/accordion/images/pencil.png', '1');
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a84680004', 'png', 'map', null, '地图', 'plug-in/accordion/images/map.png', '1');
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a84690005', 'png', 'group_add', null, '组', 'plug-in/accordion/images/group_add.png', '1');
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a84690006', 'png', 'calculator', null, '计算器', 'plug-in/accordion/images/calculator.png', '1');
INSERT INTO `s_t_icon` VALUES ('2c99cac94ade0a33014ade0a846a0007', 'png', 'folder', null, '文件夹', 'plug-in/accordion/images/folder.png', '1');

-- ----------------------------
-- Table structure for `s_t_log`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_log`;
CREATE TABLE `s_t_log` (
  `ID` varchar(32) NOT NULL,
  `BROSWER` varchar(100) DEFAULT NULL,
  `LOG_CONTENT` longtext NOT NULL,
  `LOG_LEVEL` smallint(6) DEFAULT NULL,
  `NOTE` longtext,
  `OCCUR_TIME` datetime NOT NULL,
  `OPERATE_TYPE` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `s_t_menu_function`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_menu_function`;
CREATE TABLE `s_t_menu_function` (
  `ID` varchar(32) NOT NULL,
  `IFRAME` smallint(6) DEFAULT NULL,
  `LEVEL` smallint(6) DEFAULT NULL,
  `NAME` varchar(50) NOT NULL,
  `SORT` varchar(255) DEFAULT NULL,
  `URL` varchar(100) DEFAULT NULL,
  `ICON_ID` varchar(32) DEFAULT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_clkrv3yfgemgxs5v6e4v9dpk2` (`ICON_ID`),
  KEY `FK_602hf8lvfasdj2d1moke4e82h` (`PARENT_ID`),
  CONSTRAINT `FK_602hf8lvfasdj2d1moke4e82h` FOREIGN KEY (`PARENT_ID`) REFERENCES `s_t_menu_function` (`ID`),
  CONSTRAINT `FK_clkrv3yfgemgxs5v6e4v9dpk2` FOREIGN KEY (`ICON_ID`) REFERENCES `s_t_icon` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_menu_function
-- ----------------------------
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a848e0019', null, '0', '系统管理', '5', '', '2c99cac94ade0a33014ade0a84690005', null);
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a8492001c', null, '0', '系统监控', '11', '', '2c99cac94ade0a33014ade0a84670002', null);
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a8493001d', null, '1', '用户管理', '5', 'userController.do?user', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a848e0019');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a8495001e', null, '1', '角色管理', '6', 'roleController.do?role', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a848e0019');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a8497001f', null, '1', '菜单管理', '7', 'functionController.do?function', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a848e0019');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a84980020', null, '1', '数据字典', '6', 'systemController.do?typeGroupList', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a848e0019');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a849a0021', null, '1', '图标管理', '18', 'iconController.do?icon', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a848e0019');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a849c0022', null, '1', '部门管理', '22', 'departController.do?depart', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a848e0019');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a849e0023', null, '1', '地域管理', '22', 'territoryController.do?territory', '2c99cac94ade0a33014ade0a84660001', '2c99cac94ade0a33014ade0a848e0019');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a84a70027', null, '1', '数据监控', '11', 'dataSourceController.do?goDruid&isIframe', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a8492001c');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a84aa0028', null, '1', '系统日志', '21', 'logController.do?log', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a8492001c');
INSERT INTO `s_t_menu_function` VALUES ('2c99cac94ade0a33014ade0a84ac0029', null, '1', '定时任务', '21', 'timeTaskController.do?timeTask', '2c99cac94ade0a33014ade0a84670002', '2c99cac94ade0a33014ade0a8492001c');

-- ----------------------------
-- Table structure for `s_t_operation`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_operation`;
CREATE TABLE `s_t_operation` (
  `ID` varchar(32) NOT NULL,
  `CODE` varchar(50) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `STATUS` smallint(6) DEFAULT NULL,
  `ICON_ID` varchar(32) DEFAULT NULL,
  `MENU_FUNCTION_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_s3ku912lkyvny7e68yu6dhfpb` (`ICON_ID`),
  KEY `FK_ej5mej0d0ko3txfbb4knk4a1d` (`MENU_FUNCTION_ID`),
  CONSTRAINT `FK_ej5mej0d0ko3txfbb4knk4a1d` FOREIGN KEY (`MENU_FUNCTION_ID`) REFERENCES `s_t_menu_function` (`ID`),
  CONSTRAINT `FK_s3ku912lkyvny7e68yu6dhfpb` FOREIGN KEY (`ICON_ID`) REFERENCES `s_t_icon` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_operation
-- ----------------------------

-- ----------------------------
-- Table structure for `s_t_opin_template`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_opin_template`;
CREATE TABLE `s_t_opin_template` (
  `ID` varchar(32) NOT NULL,
  `DESCRIPT` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_opin_template
-- ----------------------------

-- ----------------------------
-- Table structure for `s_t_role`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_role`;
CREATE TABLE `s_t_role` (
  `ID` varchar(32) NOT NULL,
  `ROLE_CODE` varchar(10) DEFAULT NULL,
  `ROLE_NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_role
-- ----------------------------
INSERT INTO `s_t_role` VALUES ('2c99cac94ade0a33014ade0a84e90042', 'admin', '管理员');
INSERT INTO `s_t_role` VALUES ('2c99cac94ade0a33014ade0a84ec0043', 'manager', '普通用户');

-- ----------------------------
-- Table structure for `s_t_role_menu_function`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_role_menu_function`;
CREATE TABLE `s_t_role_menu_function` (
  `ID` varchar(32) NOT NULL,
  `OPERATION` varchar(100) DEFAULT NULL,
  `MENU_FUNCTION_ID` varchar(32) DEFAULT NULL,
  `ROLE_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_79m4yl65v0dkbkgg4bqtv6fp6` (`MENU_FUNCTION_ID`),
  KEY `FK_lamr29a4r8drx7b8ifhwd775p` (`ROLE_ID`),
  CONSTRAINT `FK_lamr29a4r8drx7b8ifhwd775p` FOREIGN KEY (`ROLE_ID`) REFERENCES `s_t_role` (`ID`),
  CONSTRAINT `FK_79m4yl65v0dkbkgg4bqtv6fp6` FOREIGN KEY (`MENU_FUNCTION_ID`) REFERENCES `s_t_menu_function` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_role_menu_function
-- ----------------------------
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85690070', null, '2c99cac94ade0a33014ade0a848e0019', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a856a0071', null, '2c99cac94ade0a33014ade0a848e0019', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85750076', null, '2c99cac94ade0a33014ade0a8492001c', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85760077', null, '2c99cac94ade0a33014ade0a8492001c', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85780078', null, '2c99cac94ade0a33014ade0a8493001d', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85790079', null, '2c99cac94ade0a33014ade0a8493001d', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a857b007a', null, '2c99cac94ade0a33014ade0a8495001e', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a857d007b', null, '2c99cac94ade0a33014ade0a8495001e', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a857e007c', null, '2c99cac94ade0a33014ade0a8497001f', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a8580007d', null, '2c99cac94ade0a33014ade0a8497001f', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a8581007e', null, '2c99cac94ade0a33014ade0a84980020', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a8583007f', null, '2c99cac94ade0a33014ade0a84980020', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85850080', null, '2c99cac94ade0a33014ade0a849a0021', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85860081', null, '2c99cac94ade0a33014ade0a849a0021', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85880082', null, '2c99cac94ade0a33014ade0a849c0022', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a858a0083', null, '2c99cac94ade0a33014ade0a849c0022', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a858b0084', null, '2c99cac94ade0a33014ade0a849e0023', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a858c0085', null, '2c99cac94ade0a33014ade0a849e0023', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a859a008c', null, '2c99cac94ade0a33014ade0a84a70027', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a859b008d', null, '2c99cac94ade0a33014ade0a84a70027', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a859d008e', null, '2c99cac94ade0a33014ade0a84aa0028', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a859f008f', null, '2c99cac94ade0a33014ade0a84aa0028', '2c99cac94ade0a33014ade0a84ec0043');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85a00090', null, '2c99cac94ade0a33014ade0a84ac0029', '2c99cac94ade0a33014ade0a84e90042');
INSERT INTO `s_t_role_menu_function` VALUES ('2c99cac94ade0a33014ade0a85a10091', null, '2c99cac94ade0a33014ade0a84ac0029', '2c99cac94ade0a33014ade0a84ec0043');

-- ----------------------------
-- Table structure for `s_t_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_role_user`;
CREATE TABLE `s_t_role_user` (
  `ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) DEFAULT NULL,
  `USER_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_n4e6mtg4whv9plvfkgxuln5pj` (`ROLE_ID`),
  KEY `FK_ho6csgplsyo8vn42iygxtesg1` (`USER_ID`),
  CONSTRAINT `FK_ho6csgplsyo8vn42iygxtesg1` FOREIGN KEY (`USER_ID`) REFERENCES `s_t_user` (`ID`),
  CONSTRAINT `FK_n4e6mtg4whv9plvfkgxuln5pj` FOREIGN KEY (`ROLE_ID`) REFERENCES `s_t_role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_role_user
-- ----------------------------
INSERT INTO `s_t_role_user` VALUES ('2c99cac94ade0a33014ade0a85e400c2', '2c99cac94ade0a33014ade0a84e90042', '2c99cac94ade0a33014ade0a84f20044');
INSERT INTO `s_t_role_user` VALUES ('2c99cac94ade0a33014ade0a85e700c3', '2c99cac94ade0a33014ade0a84e90042', '2c99cac94ade0a33014ade0a84f20044');

-- ----------------------------
-- Table structure for `s_t_territory`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_territory`;
CREATE TABLE `s_t_territory` (
  `ID` varchar(32) NOT NULL,
  `code` varchar(10) NOT NULL,
  `LEVEL` smallint(6) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `pinyin` varchar(40) DEFAULT NULL,
  `SORT` varchar(3) NOT NULL,
  `X_WGS84` double NOT NULL,
  `Y_WGS84` double NOT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_territory
-- ----------------------------
INSERT INTO `s_t_territory` VALUES ('1', '100', '1', '全国', 'qg', '0', '106.258754', '38.471318', '0');
INSERT INTO `s_t_territory` VALUES ('10', '00000016', '2', '河南省', 'HNS', '0', '113.687228', '34.76819', '1');
INSERT INTO `s_t_territory` VALUES ('100', '371300', '3', '临沂市', 'LYS', '0', '118.356448', '35.104672', '18');
INSERT INTO `s_t_territory` VALUES ('1000', '370903', '4', '岱岳区', 'DYQ', '0', '117.041582', '36.18799', '96');
INSERT INTO `s_t_territory` VALUES ('1001', '370921', '4', '宁阳县', 'NYX', '0', '116.805797', '35.758787', '96');
INSERT INTO `s_t_territory` VALUES ('1002', '370923', '4', '东平县', 'DPX', '0', '116.470304', '35.937102', '96');
INSERT INTO `s_t_territory` VALUES ('1003', '370982', '4', '新泰市', 'XTS', '0', '117.767953', '35.909032', '96');
INSERT INTO `s_t_territory` VALUES ('1004', '370983', '4', '肥城市', 'FCS', '0', '116.768358', '36.182571', '96');
INSERT INTO `s_t_territory` VALUES ('1005', '371002', '4', '环翠区', 'HCQ', '0', '122.123444', '37.501991', '97');
INSERT INTO `s_t_territory` VALUES ('1006', '371081', '4', '文登市', 'WDS', '0', '122.058128', '37.1939129', '97');
INSERT INTO `s_t_territory` VALUES ('1007', '371082', '4', '荣成市', 'RCS', '0', '122.486676', '37.165249', '97');
INSERT INTO `s_t_territory` VALUES ('1008', '371083', '4', '乳山市', 'RSS', '0', '121.539764', '36.919816', '97');
INSERT INTO `s_t_territory` VALUES ('1009', '371102', '4', '东港区', 'DGQ', '0', '119.462228', '35.425475', '98');
INSERT INTO `s_t_territory` VALUES ('101', '371400', '3', '德州市', 'DZS', '0', '116.357465', '37.434093', '18');
INSERT INTO `s_t_territory` VALUES ('1010', '371103', '4', '岚山区', 'LSQ', '0', '119.318813', '35.121816', '98');
INSERT INTO `s_t_territory` VALUES ('1011', '371121', '4', '五莲县', 'WLX', '0', '119.208744', '35.750095', '98');
INSERT INTO `s_t_territory` VALUES ('1012', '371122', '4', '莒县', 'JX', '0', '118.837131', '35.579868', '98');
INSERT INTO `s_t_territory` VALUES ('1013', '371202', '4', '莱城区', 'LCQ', '0', '117.659864', '36.203208', '99');
INSERT INTO `s_t_territory` VALUES ('1014', '371203', '4', '钢城区', 'GCQ', '0', '117.811355', '36.058572', '99');
INSERT INTO `s_t_territory` VALUES ('1015', '371302', '4', '兰山区', 'LSQ', '0', '118.347707', '35.051729', '100');
INSERT INTO `s_t_territory` VALUES ('1016', '371311', '4', '罗庄区', 'LZQ', '0', '118.284786', '34.996741', '100');
INSERT INTO `s_t_territory` VALUES ('1017', '371312', '4', '河东区', 'HDQ', '0', '118.402893', '35.089917', '100');
INSERT INTO `s_t_territory` VALUES ('1018', '371321', '4', '沂南县', 'YNX', '0', '118.465213', '35.549976', '100');
INSERT INTO `s_t_territory` VALUES ('1019', '371322', '4', '郯城县', 'TCX', '0', '118.367263', '34.613557', '100');
INSERT INTO `s_t_territory` VALUES ('102', '371500', '3', '聊城市', 'LCS', '0', '115.985371', '36.4567039', '18');
INSERT INTO `s_t_territory` VALUES ('1020', '371323', '4', '沂水县', 'YSX', '0', '118.627918', '35.79045', '100');
INSERT INTO `s_t_territory` VALUES ('1021', '371324', '4', '苍山县', 'CSX', '0', '118.07065', '34.857149', '100');
INSERT INTO `s_t_territory` VALUES ('1022', '371325', '4', '费县', 'FX', '0', '117.977868', '35.26634', '100');
INSERT INTO `s_t_territory` VALUES ('1023', '371326', '4', '平邑县', 'PYX', '0', '117.640352', '35.5059429', '100');
INSERT INTO `s_t_territory` VALUES ('1024', '371327', '4', '莒南县', 'JNX', '0', '118.835163', '35.174846', '100');
INSERT INTO `s_t_territory` VALUES ('1025', '371328', '4', '蒙阴县', 'MYX', '0', '117.945085', '35.710032', '100');
INSERT INTO `s_t_territory` VALUES ('1026', '371329', '4', '临沭县', 'LSX', '0', '118.650782', '34.919852', '100');
INSERT INTO `s_t_territory` VALUES ('1027', '371402', '4', '德城区', 'DCQ', '0', '116.299434', '37.451272', '101');
INSERT INTO `s_t_territory` VALUES ('1028', '371421', '4', '陵县', 'LX', '0', '116.576176', '37.33579', '101');
INSERT INTO `s_t_territory` VALUES ('1029', '371422', '4', '宁津县', 'NJX', '0', '116.800324', '37.652329', '101');
INSERT INTO `s_t_territory` VALUES ('103', '371600', '3', '滨州市', 'BZS', '0', '117.970703', '37.38199', '18');
INSERT INTO `s_t_territory` VALUES ('1030', '371423', '4', '庆云县', 'QYX', '0', '117.385123', '37.77539', '101');
INSERT INTO `s_t_territory` VALUES ('1031', '371424', '4', '临邑县', 'LYX', '0', '116.86665', '37.189864', '101');
INSERT INTO `s_t_territory` VALUES ('1032', '371425', '4', '齐河县', 'QHX', '0', '116.758917', '36.795011', '101');
INSERT INTO `s_t_territory` VALUES ('1033', '371426', '4', '平原县', 'PYX', '0', '116.434187', '37.165419', '101');
INSERT INTO `s_t_territory` VALUES ('1034', '371427', '4', '夏津县', 'XJX', '0', '116.001726', '36.948371', '101');
INSERT INTO `s_t_territory` VALUES ('1035', '371428', '4', '武城县', 'WCX', '0', '116.069302', '37.213311', '101');
INSERT INTO `s_t_territory` VALUES ('1036', '371481', '4', '乐陵市', 'LLS', '0', '117.231935', '37.729907', '101');
INSERT INTO `s_t_territory` VALUES ('1037', '371482', '4', '禹城市', 'YCS', '0', '116.638387', '36.934259', '101');
INSERT INTO `s_t_territory` VALUES ('1038', '371502', '4', '东昌府区', 'DCFQ', '0', '115.988484', '36.434697', '102');
INSERT INTO `s_t_territory` VALUES ('1039', '371521', '4', '阳谷县', 'YGX', '0', '115.79182', '36.114392', '102');
INSERT INTO `s_t_territory` VALUES ('104', '371700', '3', '菏泽市', 'HZS', '0', '115.480656', '35.23375', '18');
INSERT INTO `s_t_territory` VALUES ('1040', '371522', '4', '莘县', 'SX', '0', '115.671191', '36.233599', '102');
INSERT INTO `s_t_territory` VALUES ('1041', '371523', '4', '茌平县', 'CPX', '0', '116.255282', '36.5807639', '102');
INSERT INTO `s_t_territory` VALUES ('1042', '371524', '4', '东阿县', 'DAX', '0', '116.24758', '36.334917', '102');
INSERT INTO `s_t_territory` VALUES ('1043', '371525', '4', '冠县', 'GX', '0', '115.44274', '36.484009', '102');
INSERT INTO `s_t_territory` VALUES ('1044', '371526', '4', '高唐县', 'GTX', '0', '116.231478', '36.866062', '102');
INSERT INTO `s_t_territory` VALUES ('1045', '371581', '4', '临清市', 'LQS', '0', '115.704881', '36.838277', '102');
INSERT INTO `s_t_territory` VALUES ('1046', '371602', '4', '滨城区', 'BCQ', '0', '118.019146', '37.43206', '103');
INSERT INTO `s_t_territory` VALUES ('1047', '371621', '4', '惠民县', 'HMX', '0', '117.510451', '37.489769', '103');
INSERT INTO `s_t_territory` VALUES ('1048', '371622', '4', '阳信县', 'YXX', '0', '117.578262', '37.641106', '103');
INSERT INTO `s_t_territory` VALUES ('1049', '371623', '4', '无棣县', 'WDX', '0', '117.625696', '37.770261', '103');
INSERT INTO `s_t_territory` VALUES ('1050', '371624', '4', '沾化县', 'ZHX', '0', '118.132199', '37.698281', '103');
INSERT INTO `s_t_territory` VALUES ('1051', '371625', '4', '博兴县', 'BXX', '0', '118.131815', '37.150226', '103');
INSERT INTO `s_t_territory` VALUES ('1052', '371626', '4', '邹平县', 'ZPX', '0', '117.743109', '36.862989', '103');
INSERT INTO `s_t_territory` VALUES ('1053', '371702', '4', '牡丹区', 'MDQ', '0', '115.417827', '35.252512', '104');
INSERT INTO `s_t_territory` VALUES ('1054', '371721', '4', '曹县', 'CX', '0', '115.542328', '34.825508', '104');
INSERT INTO `s_t_territory` VALUES ('1055', '371722', '4', '单县', 'DX', '0', '116.107428', '34.778808', '104');
INSERT INTO `s_t_territory` VALUES ('1056', '371723', '4', '成武县', 'CWX', '0', '115.889765', '34.952459', '104');
INSERT INTO `s_t_territory` VALUES ('1057', '371724', '4', '巨野县', 'JYX', '0', '116.094674', '35.396261', '104');
INSERT INTO `s_t_territory` VALUES ('1058', '371725', '4', '郓城县', 'YCX', '0', '115.943613', '35.599758', '104');
INSERT INTO `s_t_territory` VALUES ('1059', '371726', '4', '鄄城县', 'JCX', '0', '115.510193', '35.563408', '104');
INSERT INTO `s_t_territory` VALUES ('1060', '371727', '4', '定陶县', 'DTX', '0', '115.573094', '35.071', '104');
INSERT INTO `s_t_territory` VALUES ('1061', '371728', '4', '东明县', 'DMX', '0', '115.089905', '35.289368', '104');
INSERT INTO `s_t_territory` VALUES ('11', '00000008', '2', '黑龙江省', 'HLJS', '0', '126.662507', '45.7421699', '1');
INSERT INTO `s_t_territory` VALUES ('12', '20000000', '2', '上海市', 'SHS', '0', '121.473704', '31.230393', '1');
INSERT INTO `s_t_territory` VALUES ('13', '00000010', '2', '江苏省', 'JSS', '0', '118.763232', '32.061707', '1');
INSERT INTO `s_t_territory` VALUES ('14', '00000011', '2', '浙江省', 'ZJS', '0', '120.153676', '30.26586', '1');
INSERT INTO `s_t_territory` VALUES ('15', '00000012', '2', '安徽省', 'AHS', '0', '117.284923', '31.861184', '1');
INSERT INTO `s_t_territory` VALUES ('16', '00000013', '2', '福建省', 'FJS', '0', '119.296506', '26.099933', '1');
INSERT INTO `s_t_territory` VALUES ('17', '00000014', '2', '江西省', 'JXS', '0', '115.909175', '28.674424', '1');
INSERT INTO `s_t_territory` VALUES ('1739', '360102', '4', '东湖区', 'DHQ', '0', '115.89901', '28.6849159', '184');
INSERT INTO `s_t_territory` VALUES ('1740', '360103', '4', '西湖区', 'XHQ', '0', '115.877287', '28.656887', '184');
INSERT INTO `s_t_territory` VALUES ('1741', '360104', '4', '青云谱区', 'QYPQ', '0', '115.925749', '28.621169', '184');
INSERT INTO `s_t_territory` VALUES ('1742', '360105', '4', '湾里区', 'WLQ', '0', '115.730994', '28.714869', '184');
INSERT INTO `s_t_territory` VALUES ('1743', '360111', '4', '青山湖区', 'QSHQ', '0', '115.962144', '28.682985', '184');
INSERT INTO `s_t_territory` VALUES ('1744', '360121', '4', '南昌县', 'NCX', '0', '115.944162', '28.545459', '184');
INSERT INTO `s_t_territory` VALUES ('1745', '360122', '4', '新建县', 'XJX', '0', '115.815233', '28.692437', '184');
INSERT INTO `s_t_territory` VALUES ('1746', '360123', '4', '安义县', 'AYX', '0', '115.549199', '28.844429', '184');
INSERT INTO `s_t_territory` VALUES ('1747', '360124', '4', '进贤县', 'JXX', '0', '116.240924', '28.376918', '184');
INSERT INTO `s_t_territory` VALUES ('1748', '360202', '4', '昌江区', 'CJQ', '0', '117.183688', '29.27342', '185');
INSERT INTO `s_t_territory` VALUES ('1749', '360203', '4', '珠山区', 'ZSQ', '0', '117.202336', '29.301272', '185');
INSERT INTO `s_t_territory` VALUES ('1750', '360222', '4', '浮梁县', 'FLX', '0', '117.215061', '29.351739', '185');
INSERT INTO `s_t_territory` VALUES ('1751', '360281', '4', '乐平市', 'LPS', '0', '117.129169', '28.961902', '185');
INSERT INTO `s_t_territory` VALUES ('1752', '360302', '4', '安源区', 'AYQ', '0', '113.87073', '27.615202', '186');
INSERT INTO `s_t_territory` VALUES ('1753', '360313', '4', '湘东区', 'XDQ', '0', '113.733059', '27.640075', '186');
INSERT INTO `s_t_territory` VALUES ('1754', '360321', '4', '莲花县', 'LHX', '0', '113.961465', '27.127669', '186');
INSERT INTO `s_t_territory` VALUES ('1755', '360322', '4', '上栗县', 'SLX', '0', '113.795219', '27.880567', '186');
INSERT INTO `s_t_territory` VALUES ('1756', '360323', '4', '芦溪县', 'LXX', '0', '114.029595', '27.631145', '186');
INSERT INTO `s_t_territory` VALUES ('1757', '360402', '4', '庐山区', 'LSQ', '0', '115.989212', '29.671775', '187');
INSERT INTO `s_t_territory` VALUES ('1758', '360403', '4', '浔阳区', 'XYQ', '0', '115.990399', '29.72746', '187');
INSERT INTO `s_t_territory` VALUES ('1759', '360421', '4', '九江县', 'JJX', '0', '115.911314', '29.608456', '187');
INSERT INTO `s_t_territory` VALUES ('1760', '360423', '4', '武宁县', 'WNX', '0', '115.100579', '29.256323', '187');
INSERT INTO `s_t_territory` VALUES ('1761', '360424', '4', '修水县', 'XSX', '0', '114.547356', '29.025707', '187');
INSERT INTO `s_t_territory` VALUES ('1762', '360425', '4', '永修县', 'YXX', '0', '115.809111', '29.021824', '187');
INSERT INTO `s_t_territory` VALUES ('1763', '360426', '4', '德安县', 'DAX', '0', '115.756883', '29.314348', '187');
INSERT INTO `s_t_territory` VALUES ('1764', '360427', '4', '星子县', 'XZX', '0', '116.044893', '29.448037', '187');
INSERT INTO `s_t_territory` VALUES ('1765', '360428', '4', '都昌县', 'DCX', '0', '116.204099', '29.273194', '187');
INSERT INTO `s_t_territory` VALUES ('1766', '360429', '4', '湖口县', 'HKX', '0', '116.220266', '29.73944', '187');
INSERT INTO `s_t_territory` VALUES ('1767', '360430', '4', '彭泽县', 'PZX', '0', '116.549359', '29.896061', '187');
INSERT INTO `s_t_territory` VALUES ('1768', '360481', '4', '瑞昌市', 'RCS', '0', '115.681504', '29.673795', '187');
INSERT INTO `s_t_territory` VALUES ('1769', '360499', '4', '共青城', 'GQC', '0', '115.774', '29.2417', '187');
INSERT INTO `s_t_territory` VALUES ('1770', '360502', '4', '渝水区', 'YSQ', '0', '114.944574', '27.80038', '188');
INSERT INTO `s_t_territory` VALUES ('1771', '360521', '4', '分宜县', 'FYX', '0', '114.692039', '27.814628', '188');
INSERT INTO `s_t_territory` VALUES ('1772', '360602', '4', '月湖区', 'YHQ', '0', '117.036676', '28.238797', '189');
INSERT INTO `s_t_territory` VALUES ('1773', '360622', '4', '余江县', 'YJX', '0', '116.818871', '28.204174', '189');
INSERT INTO `s_t_territory` VALUES ('1774', '360681', '4', '贵溪市', 'GXS', '0', '117.245497', '28.292519', '189');
INSERT INTO `s_t_territory` VALUES ('1775', '360702', '4', '章贡区', 'ZGQ', '0', '114.941826', '25.862827', '190');
INSERT INTO `s_t_territory` VALUES ('1776', '360721', '4', '赣县', 'GX', '0', '115.011561', '25.860691', '190');
INSERT INTO `s_t_territory` VALUES ('1777', '360722', '4', '信丰县', 'XFX', '0', '114.922963', '25.386278', '190');
INSERT INTO `s_t_territory` VALUES ('1778', '360723', '4', '大余县', 'DYX', '0', '114.362094', '25.401283', '190');
INSERT INTO `s_t_territory` VALUES ('1779', '360724', '4', '上犹县', 'SYX', '0', '114.551371', '25.784978', '190');
INSERT INTO `s_t_territory` VALUES ('1780', '360725', '4', '崇义县', 'CYX', '0', '114.308273', '25.681879', '190');
INSERT INTO `s_t_territory` VALUES ('1781', '360726', '4', '安远县', 'AYX', '0', '115.393922', '25.136925', '190');
INSERT INTO `s_t_territory` VALUES ('1782', '360727', '4', '龙南县', 'LNX', '0', '114.789811', '24.911107', '190');
INSERT INTO `s_t_territory` VALUES ('1783', '360728', '4', '定南县', 'DNX', '0', '115.027845', '24.78441', '190');
INSERT INTO `s_t_territory` VALUES ('1784', '360729', '4', '全南县', 'QNX', '0', '114.530125', '24.742401', '190');
INSERT INTO `s_t_territory` VALUES ('1785', '360730', '4', '宁都县', 'NDX', '0', '116.009472', '26.470116', '190');
INSERT INTO `s_t_territory` VALUES ('1786', '360731', '4', '于都县', 'YDX', '0', '115.41551', '25.952066', '190');
INSERT INTO `s_t_territory` VALUES ('1787', '360732', '4', '兴国县', 'XGX', '0', '115.36319', '26.337937', '190');
INSERT INTO `s_t_territory` VALUES ('1788', '360733', '4', '会昌县', 'HCX', '0', '115.786057', '25.600272', '190');
INSERT INTO `s_t_territory` VALUES ('1789', '360734', '4', '寻乌县', 'YWX', '0', '115.646525', '24.963322', '190');
INSERT INTO `s_t_territory` VALUES ('1790', '360735', '4', '石城县', 'SCX', '0', '116.354201', '26.32686', '190');
INSERT INTO `s_t_territory` VALUES ('1791', '360781', '4', '瑞金市', 'RJS', '0', '116.02713', '25.885561', '190');
INSERT INTO `s_t_territory` VALUES ('1792', '360782', '4', '南康市', 'NKS', '0', '114.765238', '25.661356', '190');
INSERT INTO `s_t_territory` VALUES ('1793', '360802', '4', '吉州区', 'JZQ', '0', '114.994307', '27.129975', '191');
INSERT INTO `s_t_territory` VALUES ('1794', '360803', '4', '青原区', 'QYQ', '0', '115.01424', '27.081719', '191');
INSERT INTO `s_t_territory` VALUES ('1795', '360821', '4', '吉安县', 'JAX', '0', '114.907659', '27.040142', '191');
INSERT INTO `s_t_territory` VALUES ('1796', '360822', '4', '吉水县', 'JSX', '0', '115.135507', '27.229632', '191');
INSERT INTO `s_t_territory` VALUES ('1797', '360823', '4', '峡江县', 'XJX', '0', '115.316566', '27.582901', '191');
INSERT INTO `s_t_territory` VALUES ('1798', '360824', '4', '新干县', 'XGX', '0', '115.393043', '27.740809', '191');
INSERT INTO `s_t_territory` VALUES ('1799', '360825', '4', '永丰县', 'YFX', '0', '115.441477', '27.317869', '191');
INSERT INTO `s_t_territory` VALUES ('18', '00000015', '2', '山东省', 'SDS', '0', '117.020411', '36.668627', '1');
INSERT INTO `s_t_territory` VALUES ('1800', '360826', '4', '泰和县', 'THX', '0', '114.908861', '26.790231', '191');
INSERT INTO `s_t_territory` VALUES ('1801', '360827', '4', '遂川县', 'SCX', '0', '114.52098', '26.311894', '191');
INSERT INTO `s_t_territory` VALUES ('1802', '360828', '4', '万安县', 'WAX', '0', '114.786256', '26.458257', '191');
INSERT INTO `s_t_territory` VALUES ('1803', '360829', '4', '安福县', 'AFX', '0', '114.619893', '27.392874', '191');
INSERT INTO `s_t_territory` VALUES ('1804', '360830', '4', '永新县', 'YXX', '0', '114.242675', '26.945233', '191');
INSERT INTO `s_t_territory` VALUES ('1805', '360881', '4', '井冈山市', 'JGSS', '0', '114.289182', '26.748186', '191');
INSERT INTO `s_t_territory` VALUES ('1806', '360902', '4', '袁州区', 'YZQ', '0', '114.424657', '27.798846', '192');
INSERT INTO `s_t_territory` VALUES ('1807', '360921', '4', '奉新县', 'FXX', '0', '115.384904', '28.700806', '192');
INSERT INTO `s_t_territory` VALUES ('1808', '360922', '4', '万载县', 'WZX', '0', '114.447551', '28.10455', '192');
INSERT INTO `s_t_territory` VALUES ('1809', '360923', '4', '上高县', 'SGX', '0', '114.924494', '28.232827', '192');
INSERT INTO `s_t_territory` VALUES ('1810', '360924', '4', '宜丰县', 'YFX', '0', '114.803542', '28.393613', '192');
INSERT INTO `s_t_territory` VALUES ('1811', '360925', '4', '靖安县', 'JAX', '0', '115.362629', '28.861475', '192');
INSERT INTO `s_t_territory` VALUES ('1812', '360926', '4', '铜鼓县', 'TGX', '0', '114.37098', '28.520747', '192');
INSERT INTO `s_t_territory` VALUES ('1813', '360981', '4', '丰城市', 'FCS', '0', '115.771195', '28.159325', '192');
INSERT INTO `s_t_territory` VALUES ('1814', '360982', '4', '樟树市', 'ZSS', '0', '115.546063', '28.055796', '192');
INSERT INTO `s_t_territory` VALUES ('1815', '360983', '4', '高安市', 'GAS', '0', '115.375618', '28.417261', '192');
INSERT INTO `s_t_territory` VALUES ('1816', '361002', '4', '临川区', 'LCQ', '0', '116.31136', '27.934529', '193');
INSERT INTO `s_t_territory` VALUES ('1817', '361021', '4', '南城县', 'NCX', '0', '116.644658', '27.552748', '193');
INSERT INTO `s_t_territory` VALUES ('1818', '361022', '4', '黎川县', 'LCX', '0', '116.907508', '27.282382', '193');
INSERT INTO `s_t_territory` VALUES ('1819', '361023', '4', '南丰县', 'NFX', '0', '116.525725', '27.218445', '193');
INSERT INTO `s_t_territory` VALUES ('1820', '361024', '4', '崇仁县', 'CRX', '0', '116.061164', '27.764681', '193');
INSERT INTO `s_t_territory` VALUES ('1821', '361025', '4', '乐安县', 'LAX', '0', '115.837895', '27.420441', '193');
INSERT INTO `s_t_territory` VALUES ('1822', '361026', '4', '宜黄县', 'YHX', '0', '116.222128', '27.546146', '193');
INSERT INTO `s_t_territory` VALUES ('1823', '361027', '4', '金溪县', 'JXX', '0', '116.775435', '27.908337', '193');
INSERT INTO `s_t_territory` VALUES ('1824', '361028', '4', '资溪县', 'ZXX', '0', '117.060264', '27.706102', '193');
INSERT INTO `s_t_territory` VALUES ('1825', '361029', '4', '东乡县', 'DXX', '0', '116.590465', '28.236118', '193');
INSERT INTO `s_t_territory` VALUES ('1826', '361030', '4', '广昌县', 'GCX', '0', '116.325757', '26.837267', '193');
INSERT INTO `s_t_territory` VALUES ('1827', '361102', '4', '信州区', 'XZQ', '0', '117.966823', '28.43121', '194');
INSERT INTO `s_t_territory` VALUES ('1828', '361121', '4', '上饶县', 'SRX', '0', '117.90785', '28.448983', '194');
INSERT INTO `s_t_territory` VALUES ('1829', '361122', '4', '广丰县', 'GFX', '0', '118.19124', '28.436286', '194');
INSERT INTO `s_t_territory` VALUES ('1830', '361123', '4', '玉山县', 'YSX', '0', '118.245124', '28.682055', '194');
INSERT INTO `s_t_territory` VALUES ('1831', '361124', '4', '铅山县', 'QSX', '0', '117.709451', '28.315217', '194');
INSERT INTO `s_t_territory` VALUES ('1832', '361125', '4', '横峰县', 'HFX', '0', '117.596452', '28.407118', '194');
INSERT INTO `s_t_territory` VALUES ('1833', '361126', '4', '弋阳县', 'YYX', '0', '117.449588', '28.378044', '194');
INSERT INTO `s_t_territory` VALUES ('1834', '361127', '4', '余干县', 'YGX', '0', '116.695647', '28.702302', '194');
INSERT INTO `s_t_territory` VALUES ('1835', '361128', '4', '鄱阳县', 'PYX', '0', '116.699746', '29.011699', '194');
INSERT INTO `s_t_territory` VALUES ('1836', '361129', '4', '万年县', 'WNX', '0', '117.058445', '28.694582', '194');
INSERT INTO `s_t_territory` VALUES ('1837', '361130', '4', '婺源县', 'WYX', '0', '117.861911', '29.2480249', '194');
INSERT INTO `s_t_territory` VALUES ('1838', '361181', '4', '德兴市', 'DXS', '0', '117.578713', '28.946464', '194');
INSERT INTO `s_t_territory` VALUES ('1839', '370102', '4', '历下区', 'LXQ', '0', '117.07653', '36.666344', '195');
INSERT INTO `s_t_territory` VALUES ('184', '360100', '3', '南昌市', 'NCS', '0', '115.858089', '28.68316', '17');
INSERT INTO `s_t_territory` VALUES ('1840', '370103', '4', '市中区', 'SZQ', '0', '116.997475', '36.6511749', '195');
INSERT INTO `s_t_territory` VALUES ('1841', '370104', '4', '槐荫区', 'HYQ', '0', '116.90113', '36.651301', '195');
INSERT INTO `s_t_territory` VALUES ('1842', '370105', '4', '天桥区', 'TQQ', '0', '116.987492', '36.678016', '195');
INSERT INTO `s_t_territory` VALUES ('1843', '370112', '4', '历城区', 'LCQ', '0', '117.065222', '36.680171', '195');
INSERT INTO `s_t_territory` VALUES ('185', '360200', '3', '景德镇市', 'JDZS', '0', '117.17842', '29.268836', '17');
INSERT INTO `s_t_territory` VALUES ('186', '360300', '3', '萍乡市', 'PXS', '0', '113.854676', '27.622865', '17');
INSERT INTO `s_t_territory` VALUES ('187', '360400', '3', '九江市', 'JJS', '0', '116.001951', '29.705103', '17');
INSERT INTO `s_t_territory` VALUES ('188', '360500', '3', '新余市', 'XYS', '0', '114.91741', '27.817819', '17');
INSERT INTO `s_t_territory` VALUES ('189', '360600', '3', '鹰潭市', 'YTS', '0', '117.069202', '28.260189', '17');
INSERT INTO `s_t_territory` VALUES ('19', '00000017', '2', '湖北省', 'HBS', '0', '114.341921', '30.545861', '1');
INSERT INTO `s_t_territory` VALUES ('190', '360700', '3', '赣州市', 'GZS', '0', '114.935025', '25.831925', '17');
INSERT INTO `s_t_territory` VALUES ('191', '360800', '3', '吉安市', 'JAS', '0', '114.992912', '27.113039', '17');
INSERT INTO `s_t_territory` VALUES ('192', '360900', '3', '宜春市', 'YCS', '0', '114.416778', '27.815619', '17');
INSERT INTO `s_t_territory` VALUES ('193', '361000', '3', '抚州市', 'FZS', '0', '116.358176', '27.9492', '17');
INSERT INTO `s_t_territory` VALUES ('194', '361100', '3', '上饶市', 'SRS', '0', '117.943433', '28.454863', '17');
INSERT INTO `s_t_territory` VALUES ('195', '370100', '3', '济南市', 'JNS', '0', '116.994917', '36.665282', '18');
INSERT INTO `s_t_territory` VALUES ('20', '00000018', '2', '湖南省', 'HNS', '0', '112.98381', '28.112444', '1');
INSERT INTO `s_t_territory` VALUES ('21', '40000000', '2', '重庆市', 'ZQS', '0', '106.551557', '29.56301', '1');
INSERT INTO `s_t_territory` VALUES ('22', '00000022', '2', '四川省', 'SCS', '0', '104.075931', '30.651652', '1');
INSERT INTO `s_t_territory` VALUES ('23', '00000019', '2', '广东省', 'GDS', '0', '113.266531', '23.132191', '1');
INSERT INTO `s_t_territory` VALUES ('24', '00000020', '2', '广西壮族自治区', 'GXZZZZQ', '0', '108.327546', '22.815478', '1');
INSERT INTO `s_t_territory` VALUES ('25', '00000021', '2', '海南省', 'HNS', '0', '110.349229', '20.017378', '1');
INSERT INTO `s_t_territory` VALUES ('26', '810000', '2', '香港特别行政区', 'XGTBXZQ', '0', '114.109497', '22.396428', '1');
INSERT INTO `s_t_territory` VALUES ('27', '820000', '2', '澳门特别行政区', 'AMTBXZQ', '0', '113.543873', '22.198745', '1');
INSERT INTO `s_t_territory` VALUES ('28', '00000023', '2', '贵州省', 'GZS', '0', '106.707116', '26.598026', '1');
INSERT INTO `s_t_territory` VALUES ('29', '00000024', '2', '云南省', 'YNS', '0', '102.709812', '25.045359', '1');
INSERT INTO `s_t_territory` VALUES ('3', '00000006', '2', '辽宁省', 'LNS', '0', '123.42944', '41.835441', '1');
INSERT INTO `s_t_territory` VALUES ('30', '00000025', '2', '西藏自治区', 'XCZZQ', '0', '91.1170059', '29.647951', '1');
INSERT INTO `s_t_territory` VALUES ('31', '00000026', '2', '陕西省', 'SXS', '0', '108.954239', '34.265472', '1');
INSERT INTO `s_t_territory` VALUES ('32', '00000027', '2', '甘肃省', 'GSS', '0', '103.826308', '36.059421', '1');
INSERT INTO `s_t_territory` VALUES ('33', '00000028', '2', '青海省', 'QHS', '0', '101.780199', '36.620901', '1');
INSERT INTO `s_t_territory` VALUES ('34', '00000029', '2', '宁夏回族自治区', 'NXHZZZQ', '0', '106.258754', '38.471318', '1');
INSERT INTO `s_t_territory` VALUES ('35', '00000030', '2', '新疆维吾尔自治区', 'XJWWEZZQ', '0', '87.6278119', '43.793028', '1');
INSERT INTO `s_t_territory` VALUES ('4', '00000007', '2', '吉林省', 'JLS', '0', '125.326065', '43.896082', '1');
INSERT INTO `s_t_territory` VALUES ('5', '10000000', '2', '北京市', 'BJS', '0', '116.407413', '39.904214', '1');
INSERT INTO `s_t_territory` VALUES ('6', '30000000', '2', '天津市', 'TJS', '0', '117.200983', '39.084158', '1');
INSERT INTO `s_t_territory` VALUES ('7', '00000003', '2', '河北省', 'HBS', '0', '114.468665', '38.037057', '1');
INSERT INTO `s_t_territory` VALUES ('8', '00000004', '2', '山西省', 'SXS', '0', '112.562569', '37.873376', '1');
INSERT INTO `s_t_territory` VALUES ('89', '370200', '3', '青岛市', 'QDS', '0', '120.382504', '36.06722', '18');
INSERT INTO `s_t_territory` VALUES ('9', '00000005', '2', '内蒙古自治区', 'NMGZZQ', '0', '111.765618', '40.817498', '1');
INSERT INTO `s_t_territory` VALUES ('90', '370300', '3', '淄博市', 'ZBS', '0', '118.055007', '36.813497', '18');
INSERT INTO `s_t_territory` VALUES ('91', '370400', '3', '枣庄市', 'ZZS', '0', '117.323725', '34.810488', '18');
INSERT INTO `s_t_territory` VALUES ('92', '370500', '3', '东营市', 'DYS', '0', '118.674767', '37.434751', '18');
INSERT INTO `s_t_territory` VALUES ('926', '370113', '4', '长清区', 'CQQ', '0', '116.751959', '36.553691', '195');
INSERT INTO `s_t_territory` VALUES ('927', '370124', '4', '平阴县', 'PYX', '0', '116.456187', '36.289265', '195');
INSERT INTO `s_t_territory` VALUES ('928', '370125', '4', '济阳县', 'JYX', '0', '117.173529', '36.978547', '195');
INSERT INTO `s_t_territory` VALUES ('929', '370126', '4', '商河县', 'SHX', '0', '117.157183', '37.309045', '195');
INSERT INTO `s_t_territory` VALUES ('93', '370600', '3', '烟台市', 'YTS', '0', '121.447926', '37.463819', '18');
INSERT INTO `s_t_territory` VALUES ('930', '370181', '4', '章丘市', 'ZQS', '0', '117.534326', '36.714015', '195');
INSERT INTO `s_t_territory` VALUES ('931', '370202', '4', '市南区', 'SNQ', '0', '120.412392', '36.075651', '89');
INSERT INTO `s_t_territory` VALUES ('932', '370203', '4', '市北区', 'SBQ', '0', '120.374801', '36.087661', '89');
INSERT INTO `s_t_territory` VALUES ('933', '370205', '4', '四方区', 'SFQ', '0', '120.366454', '36.103993', '89');
INSERT INTO `s_t_territory` VALUES ('934', '370211', '4', '黄岛区', 'HDQ', '0', '120.198054', '35.960935', '89');
INSERT INTO `s_t_territory` VALUES ('935', '370212', '4', '崂山区', 'LSQ', '0', '120.468956', '36.107538', '89');
INSERT INTO `s_t_territory` VALUES ('936', '370213', '4', '李沧区', 'LCQ', '0', '120.432864', '36.145476', '89');
INSERT INTO `s_t_territory` VALUES ('937', '370214', '4', '城阳区', 'CYQ', '0', '120.396529', '36.307061', '89');
INSERT INTO `s_t_territory` VALUES ('938', '370281', '4', '胶州市', 'JZS', '0', '120.033345', '36.264664', '89');
INSERT INTO `s_t_territory` VALUES ('939', '370282', '4', '即墨市', 'JMS', '0', '120.447162', '36.389401', '89');
INSERT INTO `s_t_territory` VALUES ('94', '370700', '3', '潍坊市', 'WFS', '0', '119.16193', '36.706691', '18');
INSERT INTO `s_t_territory` VALUES ('940', '370283', '4', '平度市', 'PDS', '0', '119.960014', '36.7867', '89');
INSERT INTO `s_t_territory` VALUES ('941', '370284', '4', '胶南市', 'JNS', '0', '120.04643', '35.8725', '89');
INSERT INTO `s_t_territory` VALUES ('942', '370285', '4', '莱西市', 'LXS', '0', '120.51769', '36.889084', '89');
INSERT INTO `s_t_territory` VALUES ('943', '370302', '4', '淄川区', 'ZCQ', '0', '117.966842', '36.643449', '90');
INSERT INTO `s_t_territory` VALUES ('944', '370303', '4', '张店区', 'ZDQ', '0', '118.017656', '36.806773', '90');
INSERT INTO `s_t_territory` VALUES ('945', '370304', '4', '博山区', 'BSQ', '0', '117.861698', '36.494752', '90');
INSERT INTO `s_t_territory` VALUES ('946', '370305', '4', '临淄区', 'LZQ', '0', '118.308977', '36.827343', '90');
INSERT INTO `s_t_territory` VALUES ('947', '370306', '4', '周村区', 'ZCQ', '0', '117.869877', '36.803109', '90');
INSERT INTO `s_t_territory` VALUES ('948', '370321', '4', '桓台县', 'HTX', '0', '118.097955', '36.959623', '90');
INSERT INTO `s_t_territory` VALUES ('949', '370322', '4', '高青县', 'GQX', '0', '117.826916', '37.171063', '90');
INSERT INTO `s_t_territory` VALUES ('95', '370800', '3', '济宁市', 'JNS', '0', '116.587099', '35.414921', '18');
INSERT INTO `s_t_territory` VALUES ('950', '370323', '4', '沂源县', 'YYX', '0', '118.170979', '36.184827', '90');
INSERT INTO `s_t_territory` VALUES ('951', '370402', '4', '市中区', 'SZQ', '0', '117.556124', '34.864114', '91');
INSERT INTO `s_t_territory` VALUES ('952', '370403', '4', '薛城区', 'YCQ', '0', '117.263157', '34.795206', '91');
INSERT INTO `s_t_territory` VALUES ('953', '370404', '4', '峄城区', 'YCQ', '0', '117.590819', '34.772236', '91');
INSERT INTO `s_t_territory` VALUES ('954', '370405', '4', '台儿庄区', 'TEZQ', '0', '117.733832', '34.562528', '91');
INSERT INTO `s_t_territory` VALUES ('955', '370406', '4', '山亭区', 'STQ', '0', '117.461343', '35.099549', '91');
INSERT INTO `s_t_territory` VALUES ('956', '370481', '4', '滕州市', 'TZS', '0', '117.164388', '35.084021', '91');
INSERT INTO `s_t_territory` VALUES ('957', '370502', '4', '东营区', 'DYQ', '0', '118.582184', '37.448964', '92');
INSERT INTO `s_t_territory` VALUES ('958', '370503', '4', '河口区', 'HKQ', '0', '118.525579', '37.886138', '92');
INSERT INTO `s_t_territory` VALUES ('959', '370521', '4', '垦利县', 'KLX', '0', '118.547627', '37.58754', '92');
INSERT INTO `s_t_territory` VALUES ('960', '370522', '4', '利津县', 'LJX', '0', '118.255273', '37.49026', '92');
INSERT INTO `s_t_territory` VALUES ('961', '370523', '4', '广饶县', 'GRX', '0', '118.407045', '37.0537', '92');
INSERT INTO `s_t_territory` VALUES ('962', '370602', '4', '芝罘区', 'ZFQ', '0', '121.400031', '37.540687', '93');
INSERT INTO `s_t_territory` VALUES ('963', '370611', '4', '福山区', 'FSQ', '0', '121.267697', '37.498051', '93');
INSERT INTO `s_t_territory` VALUES ('964', '370612', '4', '牟平区', 'MPQ', '0', '121.600512', '37.386901', '93');
INSERT INTO `s_t_territory` VALUES ('965', '370613', '4', '莱山区', 'LSQ', '0', '121.445304', '37.511305', '93');
INSERT INTO `s_t_territory` VALUES ('966', '370614', '4', '开发区', 'KFQ', '0', '121.251001', '37.554683', '93');
INSERT INTO `s_t_territory` VALUES ('967', '370634', '4', '长岛县', 'CDX', '0', '120.736584', '37.921417', '93');
INSERT INTO `s_t_territory` VALUES ('968', '370681', '4', '龙口市', 'LKS', '0', '120.477836', '37.646064', '93');
INSERT INTO `s_t_territory` VALUES ('969', '370682', '4', '莱阳市', 'LYS', '0', '120.711607', '36.97891', '93');
INSERT INTO `s_t_territory` VALUES ('970', '370683', '4', '莱州市', 'LZS', '0', '119.942327', '37.177017', '93');
INSERT INTO `s_t_territory` VALUES ('971', '370684', '4', '蓬莱市', 'PLS', '0', '120.758848', '37.810661', '93');
INSERT INTO `s_t_territory` VALUES ('972', '370685', '4', '招远市', 'ZYS', '0', '120.434072', '37.355469', '93');
INSERT INTO `s_t_territory` VALUES ('973', '370686', '4', '栖霞市', 'QXS', '0', '120.849675', '37.335123', '93');
INSERT INTO `s_t_territory` VALUES ('974', '370687', '4', '海阳市', 'HYS', '0', '121.158477', '36.776425', '93');
INSERT INTO `s_t_territory` VALUES ('975', '370702', '4', '潍城区', 'WCQ', '0', '119.024836', '36.7281', '94');
INSERT INTO `s_t_territory` VALUES ('976', '370703', '4', '寒亭区', 'HTQ', '0', '119.219734', '36.775491', '94');
INSERT INTO `s_t_territory` VALUES ('977', '370704', '4', '坊子区', 'FZQ', '0', '119.166485', '36.654448', '94');
INSERT INTO `s_t_territory` VALUES ('978', '370705', '4', '奎文区', 'KWQ', '0', '119.132486', '36.707676', '94');
INSERT INTO `s_t_territory` VALUES ('979', '370724', '4', '临朐县', 'LQX', '0', '118.542982', '36.5125059', '94');
INSERT INTO `s_t_territory` VALUES ('980', '370725', '4', '昌乐县', 'CLX', '0', '118.829914', '36.706945', '94');
INSERT INTO `s_t_territory` VALUES ('981', '370781', '4', '青州市', 'QZS', '0', '118.479622', '36.684528', '94');
INSERT INTO `s_t_territory` VALUES ('982', '370782', '4', '诸城市', 'ZCS', '0', '119.410103', '35.995654', '94');
INSERT INTO `s_t_territory` VALUES ('983', '370783', '4', '寿光市', 'SGS', '0', '118.790652', '36.85548', '94');
INSERT INTO `s_t_territory` VALUES ('984', '370784', '4', '安丘市', 'AQS', '0', '119.218978', '36.478494', '94');
INSERT INTO `s_t_territory` VALUES ('985', '370785', '4', '高密市', 'GMS', '0', '119.755597', '36.3825949', '94');
INSERT INTO `s_t_territory` VALUES ('986', '370786', '4', '昌邑市', 'CYS', '0', '119.398525', '36.85882', '94');
INSERT INTO `s_t_territory` VALUES ('987', '370802', '4', '市中区', 'SZQ', '0', '116.596614', '35.40819', '95');
INSERT INTO `s_t_territory` VALUES ('988', '370811', '4', '任城区', 'RCQ', '0', '116.628562', '35.433727', '95');
INSERT INTO `s_t_territory` VALUES ('989', '370826', '4', '微山县', 'WSX', '0', '117.128946', '34.8071', '95');
INSERT INTO `s_t_territory` VALUES ('990', '370827', '4', '鱼台县', 'YTX', '0', '116.650608', '35.012749', '95');
INSERT INTO `s_t_territory` VALUES ('991', '370828', '4', '金乡县', 'JXX', '0', '116.311532', '35.06662', '95');
INSERT INTO `s_t_territory` VALUES ('992', '370829', '4', '嘉祥县', 'JXX', '0', '116.342442', '35.407829', '95');
INSERT INTO `s_t_territory` VALUES ('993', '370830', '4', '汶上县', 'WSX', '0', '116.489043', '35.732799', '95');
INSERT INTO `s_t_territory` VALUES ('994', '370831', '4', '泗水县', 'SSX', '0', '117.251195', '35.664323', '95');
INSERT INTO `s_t_territory` VALUES ('995', '370832', '4', '梁山县', 'LSX', '0', '116.096044', '35.802306', '95');
INSERT INTO `s_t_territory` VALUES ('996', '370881', '4', '曲阜市', 'QFS', '0', '116.986532', '35.581137', '95');
INSERT INTO `s_t_territory` VALUES ('997', '370882', '4', '兖州市', 'YZS', '0', '116.783834', '35.553144', '95');
INSERT INTO `s_t_territory` VALUES ('998', '370883', '4', '邹城市', 'ZCS', '0', '117.003743', '35.405185', '95');
INSERT INTO `s_t_territory` VALUES ('999', '370902', '4', '泰山区', 'TSQ', '0', '117.135354', '36.192084', '96');

-- ----------------------------
-- Table structure for `s_t_time_task`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_time_task`;
CREATE TABLE `s_t_time_task` (
  `ID` varchar(32) NOT NULL,
  `CREATE_BY` varchar(32) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATE_NAME` varchar(32) DEFAULT NULL,
  `CRON_EXPRESSION` varchar(100) NOT NULL,
  `IS_EFFECT` varchar(1) NOT NULL,
  `IS_START` varchar(1) NOT NULL,
  `TASK_DESCRIBE` varchar(50) NOT NULL,
  `TASK_ID` varchar(100) NOT NULL,
  `UPDATE_BY` varchar(32) DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `UPDATE_NAME` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_time_task
-- ----------------------------
INSERT INTO `s_t_time_task` VALUES ('2c99cac94ade0a33014ade0a866e00f8', null, null, null, '0 0/1 * * * ?', '0', '0', '测试Demo', 'taskDemoServiceTaskCronTrigger', null, null, null);

-- ----------------------------
-- Table structure for `s_t_type`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_type`;
CREATE TABLE `s_t_type` (
  `ID` varchar(32) NOT NULL,
  `CODE` varchar(50) DEFAULT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL,
  `TYPE_GROUP_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_e986angq0jovn8oclmlhaaegx` (`PARENT_ID`),
  KEY `FK_5yc5rw1tgsiepfetjd1qxfiyv` (`TYPE_GROUP_ID`),
  CONSTRAINT `FK_5yc5rw1tgsiepfetjd1qxfiyv` FOREIGN KEY (`TYPE_GROUP_ID`) REFERENCES `s_t_type_group` (`ID`),
  CONSTRAINT `FK_e986angq0jovn8oclmlhaaegx` FOREIGN KEY (`PARENT_ID`) REFERENCES `s_t_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_type
-- ----------------------------
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85210052', '2', '菜单图标', null, '2c99cac94ade0a33014ade0a84fb0048');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85240053', '1', '系统图标', null, '2c99cac94ade0a33014ade0a84fb0048');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85260054', 'files', '附件', null, '2c99cac94ade0a33014ade0a85090050');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85280055', '1', '优质订单', null, '2c99cac94ade0a33014ade0a84fd0049');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a852a0056', '2', '普通订单', null, '2c99cac94ade0a33014ade0a84fd0049');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a852c0057', '1', '签约客户', null, '2c99cac94ade0a33014ade0a84ff004a');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a852e0058', '2', '普通客户', null, '2c99cac94ade0a33014ade0a84ff004a');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85300059', '1', '特殊服务', null, '2c99cac94ade0a33014ade0a8501004b');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a8532005a', '2', '普通服务', null, '2c99cac94ade0a33014ade0a8501004b');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a8534005b', 'single', '单条件查询', null, '2c99cac94ade0a33014ade0a8503004c');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a8536005c', 'group', '范围查询', null, '2c99cac94ade0a33014ade0a8503004c');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a8538005d', 'Y', '是', null, '2c99cac94ade0a33014ade0a8504004d');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a853a005e', 'N', '否', null, '2c99cac94ade0a33014ade0a8504004d');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a853c005f', 'Integer', 'Integer', null, '2c99cac94ade0a33014ade0a8506004e');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a853e0060', 'Date', 'Date', null, '2c99cac94ade0a33014ade0a8506004e');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85400061', 'String', 'String', null, '2c99cac94ade0a33014ade0a8506004e');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85420062', 'Long', 'Long', null, '2c99cac94ade0a33014ade0a8506004e');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85430063', 'act', '工作流引擎表', null, '2c99cac94ade0a33014ade0a8508004f');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85450064', 't_s', '系统基础表', null, '2c99cac94ade0a33014ade0a8508004f');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85470065', 't_b', '业务表', null, '2c99cac94ade0a33014ade0a8508004f');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a85490066', 't_p', '自定义引擎表', null, '2c99cac94ade0a33014ade0a8508004f');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a854b0067', 'news', '新闻', null, '2c99cac94ade0a33014ade0a85090050');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a854c0068', '0', '男性', null, '2c99cac94ade0a33014ade0a850b0051');
INSERT INTO `s_t_type` VALUES ('2c99cac94ade0a33014ade0a854e0069', '1', '女性', null, '2c99cac94ade0a33014ade0a850b0051');

-- ----------------------------
-- Table structure for `s_t_type_group`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_type_group`;
CREATE TABLE `s_t_type_group` (
  `ID` varchar(32) NOT NULL,
  `TYPE_GROUP_CODE` varchar(50) DEFAULT NULL,
  `TYPE_GROUP_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_type_group
-- ----------------------------
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a84fb0048', 'icontype', '图标类型');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a84fd0049', 'order', '订单类型');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a84ff004a', 'custom', '客户类型');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a8501004b', 'service', '服务项目类型');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a8503004c', 'searchmode', '查询模式');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a8504004d', 'yesorno', '逻辑条件');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a8506004e', 'fieldtype', '字段类型');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a8508004f', 'database', '数据表');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a85090050', 'fieltype', '文档分类');
INSERT INTO `s_t_type_group` VALUES ('2c99cac94ade0a33014ade0a850b0051', 'sex', '性别类');

-- ----------------------------
-- Table structure for `s_t_user`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_user`;
CREATE TABLE `s_t_user` (
  `ID` varchar(32) NOT NULL,
  `ACTIVITI_SYNC` smallint(6) DEFAULT NULL,
  `BROWSER` varchar(20) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `REAL_NAME` varchar(50) DEFAULT NULL,
  `SIGNATURE` blob,
  `STATUS` smallint(6) DEFAULT NULL,
  `USER_KEY` varchar(200) DEFAULT NULL,
  `USER_NAME` varchar(10) NOT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `MOBILE_PHONE` varchar(30) DEFAULT NULL,
  `OFFICE_PHONE` varchar(20) DEFAULT NULL,
  `SIGNATURE_FILE` varchar(100) DEFAULT NULL,
  `DEPARTMENT_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_8v21eqrrwn5b9pp2lm6v2nhd0` (`DEPARTMENT_ID`),
  CONSTRAINT `FK_8v21eqrrwn5b9pp2lm6v2nhd0` FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES `s_t_department` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_user
-- ----------------------------
INSERT INTO `s_t_user` VALUES ('2c99cac94ade0a33014ade0a84f20044', '1', null, 'c44b01947c9e6e3f', '管理员', null, '1', null, 'admin', null, null, null, 'images/renfang/qm/licf.gif', '2c99cac94ade0a33014ade0a847c0015');

-- ----------------------------
-- Table structure for `s_t_version`
-- ----------------------------
DROP TABLE IF EXISTS `s_t_version`;
CREATE TABLE `s_t_version` (
  `ID` varchar(32) NOT NULL,
  `LOGIN_PAGE` varchar(100) DEFAULT NULL,
  `VERSION_CODE` varchar(50) DEFAULT NULL,
  `VERSION_NAME` varchar(30) DEFAULT NULL,
  `VERSION_NUM` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_t_version
-- ----------------------------
