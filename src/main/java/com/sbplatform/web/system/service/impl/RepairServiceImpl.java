package com.sbplatform.web.system.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import com.sbplatform.core.util.DataUtils;
import com.sbplatform.web.cgform.engine.FreemarkerHelper;
import com.sbplatform.web.cgform.entity.config.CgFormFieldEntity;
import com.sbplatform.web.cgform.entity.config.CgFormHeadEntity;
import com.sbplatform.web.cgform.entity.enhance.CgformEnhanceJsEntity;
import com.sbplatform.web.demo.entity.test.CKEditorEntity;
import com.sbplatform.web.demo.entity.test.CourseEntity;
import com.sbplatform.web.demo.entity.test.SbDemoCkfinderEntity;
import com.sbplatform.web.demo.entity.test.SbJdbcEntity;
import com.sbplatform.web.demo.entity.test.SbMatterBom;
import com.sbplatform.web.demo.entity.test.SbNoteEntity;
import com.sbplatform.web.demo.entity.test.SbOrderCustomEntity;
import com.sbplatform.web.demo.entity.test.SbOrderMainEntity;
import com.sbplatform.web.demo.entity.test.SbOrderProductEntity;
import com.sbplatform.web.demo.entity.test.StudentEntity;
import com.sbplatform.web.demo.entity.test.TSStudent;
import com.sbplatform.web.demo.entity.test.TeacherEntity;
import com.sbplatform.web.system.dao.repair.RepairDao;
import com.sbplatform.web.system.pojo.base.Attachment;
import com.sbplatform.web.system.pojo.base.Demo;
import com.sbplatform.web.system.pojo.base.Department;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Icon;
import com.sbplatform.web.system.pojo.base.Log;
import com.sbplatform.web.system.pojo.base.Operation;
import com.sbplatform.web.system.pojo.base.Role;
import com.sbplatform.web.system.pojo.base.RoleMenuFunction;
import com.sbplatform.web.system.pojo.base.RoleUser;
import com.sbplatform.web.system.pojo.base.TimeTask;
import com.sbplatform.web.system.pojo.base.Type;
import com.sbplatform.web.system.pojo.base.TypeGroup;
import com.sbplatform.web.system.pojo.base.User;
import com.sbplatform.web.system.service.RepairService;

/**
 * @Description 修复数据库Service
 * @ClassName: RepairService
 * @author tanghan
 * @date 2013-7-19 下午01:31:00
 */
@Service("repairService")
@Transactional
public class RepairServiceImpl extends CommonServiceImpl implements RepairService {

	@Autowired
	private RepairDao repairDao;

	/**
	 * @Description 先清空数据库，然后再修复数据库
	 * @author tanghan 2013-7-19
	 */

	public void deleteAndRepair() {
		// 由于表中有主外键关系，清空数据库需注意
		commonDao.executeHql("delete Log");
		commonDao.executeHql("delete CKEditorEntity");
		commonDao.executeHql("delete CgformEnhanceJsEntity");
		commonDao.executeHql("delete CgFormFieldEntity");
		commonDao.executeHql("delete CgFormHeadEntity");
		commonDao.executeHql("delete Attachment");
		commonDao.executeHql("delete Operation");
		commonDao.executeHql("delete RoleMenuFunction");
		commonDao.executeHql("delete RoleUser");
		commonDao.executeHql("delete User");
		commonDao.executeHql("update MenuFunction ts set ts.parent = null");
		commonDao.executeHql("delete MenuFunction");
		commonDao.executeHql("update Department t set t.parent = null");
		commonDao.executeHql("delete Department");
		commonDao.executeHql("delete Icon");
		commonDao.executeHql("delete Role");
		commonDao.executeHql("delete Type");
		commonDao.executeHql("delete TypeGroup");
		commonDao.executeHql("update Demo t set t.parent = null");
		commonDao.executeHql("delete Demo");
		commonDao.executeHql("delete SbDemoCkfinderEntity");
		commonDao.executeHql("delete TimeTask");
		commonDao.executeHql("update Territory t set t.parent = null");
		commonDao.executeHql("delete Territory");
		commonDao.executeHql("delete StudentEntity");
		commonDao.executeHql("delete CourseEntity");
		commonDao.executeHql("delete TeacherEntity");
		commonDao.executeHql("delete SbJdbcEntity ");
		commonDao.executeHql("delete SbOrderMainEntity ");
		commonDao.executeHql("delete SbOrderProductEntity ");
		commonDao.executeHql("delete SbOrderCustomEntity ");
		commonDao.executeHql("delete SbNoteEntity ");
		commonDao.executeHql("update SbMatterBom mb set mb.parent = null");
		commonDao.executeHql("delete SbMatterBom ");
		repair();
	}

	/**
	 * @Description 修复数据库
	 * @author tanghan 2013-7-19
	 */

	synchronized public void repair() {
		//repairCkFinder();// 修复智能表单ck_finder数据库
		repaireIcon(); // 修复图标
		repairAttachment(); // 修改附件
		repairDepart();// 修复部门表
		repairMenu();// 修复菜单权限
		repairRole();// 修复角色
		repairUser(); // 修复基本用户
		repairTypeAndGroup();// 修复字典类型
		repairType();// 修复字典值
		repairOperation(); // 修复操作表
		repairRoleFunction();// 修复角色和权限的关系
		repairUserRole();// 修复用户和角色的关系
		repairDemo(); // 修复Demo表，(页面表单验证功能);
		repairLog();// 修复日志表
		repairCkEditor(); // 修复此表，初始化HTML在线编辑功能
		repairCgFormHead();// 修复智能表单header表
		repairCgFormField(); // 修复在线表单字段
		repairTask();//修复任务管理
		repairExcel();//修复Excel导出导入demo
		repairDao.batchRepairTerritory();//修复地域管理
		repairJdbcEntity();//修复表单例子无tag
		repairSbNoteEntity();//表单模型
		repairOrder();//修复订单一对多
		repairMatterBom();//修复物料Bom
		repairReportEntity();//修复报表统计demo
	}

	/**
	 * 修复物料Bom
	 *@Author fancq
	 *@date   2013-11-14
	 */
	private void repairMatterBom() {
		SbMatterBom entity = new SbMatterBom();
		entity.setCode("001");
		entity.setName("电脑");
		entity.setUnit("台");
		entity.setWeight("100");
		entity.setPrice(new BigDecimal(5000));
		entity.setStock(10);
		entity.setAddress("广东深圳");
		entity.setProductionDate(new Date());
		entity.setQuantity(5);
		commonDao.save(entity);

		SbMatterBom entity2 = new SbMatterBom();
		entity2.setCode("001001");
		entity2.setName("主板");
		entity2.setUnit("个");
		entity2.setWeight("60");
		entity2.setPrice(new BigDecimal(800));
		entity2.setStock(18);
		entity2.setAddress("上海");
		entity2.setProductionDate(new Date());
		entity2.setQuantity(6);
		entity2.setParent(entity);
		commonDao.save(entity2);
	}

	/**
	 * 修复订单一对多
	 *@Author JueYue
	 *@date   2013-11-12
	 */
	private void repairOrder() {
		SbOrderMainEntity main = new SbOrderMainEntity();
		main.setGoAllPrice(new BigDecimal(1111111));
		main.setGoContactName("alex");
		main.setGoContent("别放辣椒");
		main.setGoderType("1");
		main.setGoOrderCode("11111AAA");
		main.setGoOrderCount(1);
		main.setGoReturnPrice(new BigDecimal(100));
		main.setUsertype("1");
		commonDao.save(main);
		SbOrderProductEntity product = new SbOrderProductEntity();
		product.setGoOrderCode(main.getGoOrderCode());
		product.setGopCount(1);
		product.setGopOnePrice(new BigDecimal(100));
		product.setGopProductName("最最美味的地锅鸡");
		product.setGopProductType("1");
		product.setGopSumPrice(new BigDecimal(100));
		commonDao.save(product);
		SbOrderCustomEntity coustom = new SbOrderCustomEntity();
		coustom.setGoOrderCode(main.getGoOrderCode());
		coustom.setGocCusName("小明");
		coustom.setGocSex("1");
		commonDao.save(coustom);
	}

	/**
	 * 修复表单模型
	 *@Author JueYue
	 *@date   2013-11-12
	 */
	private void repairSbNoteEntity() {
		SbNoteEntity entity = new SbNoteEntity();
		entity.setAge(10);
		entity.setBirthday(new Date());
		entity.setCreatedt(new Date());
		entity.setName("小红");
		entity.setSalary(new BigDecimal(1000));
		commonDao.save(entity);
	}

	/**
	 * 修复表单例子无tag
	 * @throws ParseException 
	 *@Author JueYue
	 *@date   2013-11-12
	 */
	private void repairJdbcEntity() {
		SbJdbcEntity entity = new SbJdbcEntity();
		entity.setAge(12);
		//		update-begin--Author:yangyong  Date:20140214 for：[bugfree号]当前时间获取调整--------------------
		entity.setBirthday(DataUtils.str2Date("2014-02-14", new SimpleDateFormat("yyyy-MM-dd")));
		//		update-end--Author:yangyong  Date:20140214 for：[bugfree号]当前时间获取调整--------------------
		entity.setDepId("123");
		entity.setEmail("demo@sb.com");
		entity.setMobilePhone("13111111111");
		entity.setOfficePhone("66666666");
		entity.setSalary(new BigDecimal(111111));
		entity.setSex("1");
		entity.setUserName("小明");
		commonDao.save(entity);
	}

	/**
	 * 修复Excel导出导入数据
	 *@Author JueYue
	 *@date 2013-11-10
	 */
	private void repairExcel() {
		CourseEntity course = new CourseEntity();
		course.setName("海贼王");
		TeacherEntity teacher = new TeacherEntity();
		teacher.setName("路飞");
		teacher.setPic("upload/Teacher/pic3345280233.PNG");
		course.setTeacher(teacher);
		List<StudentEntity> list = new ArrayList<StudentEntity>();
		StudentEntity student = new StudentEntity();
		student.setName("卓洛");
		student.setSex("0");
		list.add(student);
		student = new StudentEntity();
		student.setName("山治 ");
		student.setSex("0");
		list.add(student);
		course.setStudents(list);
		commonDao.save(course.getTeacher());
		commonDao.save(course);
		commonDao.save(course);
		for (StudentEntity s : course.getStudents()) {
			s.setCourse(course);
		}
		commonDao.batchSave(course.getStudents());
	}

	/**
	 * 修复任务管理
	 * @author JueYue
	 * @serialData 2013年11月5日
	 */
	private void repairTask() {
		TimeTask task = new TimeTask();
		task.setTaskId("taskDemoServiceTaskCronTrigger");
		task.setTaskDescribe("测试Demo");
		task.setCronExpression("0 0/1 * * * ?");
		task.setIsEffect("0");
		task.setIsStart("0");
		commonDao.saveOrUpdate(task);
	}

	/**
	 * @Description 修复智能表单ck_finder数据库
	 * @author Alexander 2013-10-13
	 */
	private void repairCkFinder() {
		SbDemoCkfinderEntity ckfinder = new SbDemoCkfinderEntity();
		ckfinder.setImage("/sb/userfiles/images/%E6%9C%AA%E5%91%BD%E5%90%8D.jpg");
		ckfinder.setAttachment("/sb/userfiles/files/SB%20UI%E6%A0%87%E7%AD%BE%E5%BA%93%E5%B8%AE%E5%8A%A9%E6%96%87%E6%A1%A3v3_2.pdf");
		String str = "<img alt=\"\" src=\"/sb/userfiles/images/%E6%9C%AA%E5%91%BD%E5%90%8D.jpg\" style=\"height:434px; width:439px\" /><br />\r\n可爱的小猫<br />\r\n<br />\r\n<strong><span style=\"font-size:14.0pt\">1</span></strong><strong><span style=\"font-family:宋体; font-size:14.0pt; line-height:150%\">．</span></strong><strong><span style=\"font-size:14.0pt\">CRM</span></strong><strong><span style=\"font-family:宋体; font-size:14.0pt; line-height:150%\">概述</span></strong><br />\r\n<strong><span style=\"font-size:12.0pt\">1</span></strong><strong><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">．</span></strong><strong><span style=\"font-size:12.0pt\">1</span></strong><strong><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">概念</span></strong>\r\n\r\n<p style=\"line-height:150%; text-indent:24.0pt\"><span style=\"color:black; font-family:宋体; font-size:12.0pt; line-height:150%\">CRM</span><span style=\"color:black; font-family:宋体; font-size:12.0pt; line-height:150%\">是一项商业战略，它是按照客户细分原则有效的组织企业资源，来培养以客户为中心的</span><span style=\"color:red; font-family:宋体; font-size:12.0pt; line-height:150%\">经营行为以及实施以</span><span style=\"color:black; font-family:宋体; font-size:12.0pt; line-height:150%\">客户为中心的业务流程，以此为手段来提高企业的获利能力、收入及客户满意度。</span></p>\r\n\r\n<p style=\"line-height:150%; text-indent:24.0pt\"><span style=\"color:black; font-family:宋体; font-size:12.0pt; line-height:150%\">U8CRM</span><span style=\"color:black; font-family:宋体; font-size:12.0pt; line-height:150%\">同样是基于客户为中心应用原则，</span><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">把客户作为企业最重要的资源，围绕客户的生命周期，从客户接触开始，到客户交易、客户服务的全程进行跟踪管理、过程监控，并通过对客户多角度分析，识别客户满足度和价值度，从而不断改进产品和服务，使客户价值最大化、终身化。</span></p>\r\n<strong><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">1</span></strong><strong><span style=\"font-family:宋体; font-size:12.0pt; line-height:	150%\">．2应用价值</span></strong>\r\n\r\n<p style=\"line-height:150%; text-indent:24.0pt\"><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">统一企业的客户资源：系统帮助企业建立完整的客户、联系人资源档案，不同的组织、部门可以根据资源权限访问相关的客户资料。</span></p>\r\n<span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">深入挖掘价值客户：系统帮助用户建立价值评估体系，基于客户交易数据，多角度、全方位评估客户价值。通过客户价值的评估，挖掘价值客户，进而更好的服务价值客户。例如，帮助企业发现带来80%销售收入的20%价值客户。</span>\r\n\r\n<p style=\"line-height:150%; text-indent:24.0pt\"><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">跟踪和监控销售机会：系统侧重售前业务管理，从客户向企业表达意向开始，围绕销售线索不同阶段，提供对商机客户购买意向、接触过程、竞争对手、阶段评估等信息的追踪记录，并通过销售漏斗有效监督整个销售过程是否正常。</span></p>\r\n\r\n<p style=\"line-height:150%; text-indent:24.0pt\"><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">科学预测未来销售情况：提供了一种预测模式，系统可以按照销售商机的预期销售收入和预计成交时间，科学的预测未来可能实现的销售收入。</span></p>\r\n\r\n<p style=\"line-height:150%; text-indent:24.0pt\"><span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">提供科学理性的分析决策：系统提供基于客户完整业务（客户关系管理、供应链和财务）的决策分析，并通过报表的形式展现给用户，辅助企业做出数字化决策。</span></p>\r\n<span style=\"font-family:宋体; font-size:12.0pt; line-height:150%\">资源共享：CRM系统与企业销售系统、财务系统等集成应用，实现企业信息化的整合和共享。</span><br />\r\n&nbsp;";
		ckfinder.setRemark(str);
		commonDao.saveOrUpdate(ckfinder);
	}

	/**
	 * @Description 修复智能表单Head数据库
	 * @author tanghan 2013-7-28
	 */
	private void repairCgFormHead() {
		CgFormHeadEntity order_main = new CgFormHeadEntity();
		order_main.setTableName("jform_order_main");
		order_main.setIsTree("N");
		order_main.setIsPagination("Y");
		order_main.setIsCheckbox("N");
		order_main.setQuerymode("group");
		order_main.setIsDbSynch("N");
		order_main.setContent("订单主信息");
		order_main.setCreateBy("admin");
		order_main.setCreateDate(new Date());
		order_main.setJformPkType("UUID");
		// order_main.setJsPlugIn("0");
		// order_main.setSqlPlugIn("0");
		order_main.setCreateName("管理员");
		order_main.setJformVersion("57");
		order_main.setJformType(2);
		order_main.setRelationType(0);
		order_main.setSubTableStr("jform_order_ticket,jform_order_customer");
		commonDao.saveOrUpdate(order_main);

		CgFormHeadEntity leave = new CgFormHeadEntity();
		leave.setTableName("jform_leave");
		leave.setIsTree("N");
		leave.setIsPagination("Y");
		leave.setIsCheckbox("N");
		leave.setJformPkType("UUID");
		leave.setQuerymode("group");
		leave.setIsDbSynch("N");
		leave.setContent("请假单");
		leave.setCreateBy("admin");
		leave.setCreateDate(new Date());
		// leave.setJsPlugIn("0");
		// leave.setSqlPlugIn("0");
		leave.setCreateName("管理员");
		leave.setJformVersion("51");
		leave.setJformType(1);
		leave.setRelationType(0);
		commonDao.saveOrUpdate(leave);
		//		CgFormHeadEntity cgreport_head = new CgFormHeadEntity();
		//		cgreport_head.setTableName("cgreport_t_head");
		//		cgreport_head.setIsTree("N");
		//		cgreport_head.setIsPagination("Y");
		//		cgreport_head.setIsCheckbox("N");
		//		cgreport_head.setQuerymode("single");
		//		cgreport_head.setIsDbSynch("N");
		//		cgreport_head.setContent("动态报表配置抬头");
		//		cgreport_head.setCreateBy("admin");
		//		cgreport_head.setCreateDate(new Date());
		/*
		 * cgreport_head.setJsPlugIn("$(function(){"+"\r\n" +
		 * "$(\"body\").append(\"<link href=\\\"plug-in/lhgDialog/skins/default.css\\\" rel=\\\"stylesheet\\\" id=\\\"lhgdialoglink\\\">\");"
		 * +"\r\n" +
		 * "var $btn = $(\"<div class=\\\"ui_buttons\\\"  style=\\\"display:inline-block;\\\"><input style=\\\"position: relative;top: -8px;\\\" class=\\\"ui_state_highlight\\\" type=\\\"button\\\" value=\\\"sql解析\\\"        id=\\\"sqlAnalyze\\\" /></div>\");"
		 * +"\r\n" +"$(\"#cgr_sql\").after($btn);"+"\r\n"
		 * +"$btn.click(function(){"+"\r\n" +" $.ajax({"+"\r\n"
		 * +"     url:\"cgReportController.do?getFields\","+"\r\n"
		 * +"    data:{sql:$(\"#cgr_sql\").val()},"+"\r\n"
		 * +"   type:\"Post\","+"\r\n" +"    dataType:\"json\","+"\r\n"
		 * +"    success:function(data){"+"\r\n"
		 * +"      if(data.status==0){"+"\r\n"
		 * +"         $(\"#add_cgreport_t_item_table\").empty();"+"\r\n"
		 * +"      $.each(data.datas,function(index,e){"+"\r\n" +
		 * "        var $tr = $(\"#add_cgreport_t_item_table_template tr\").clone();"
		 * +"\r\n" +"      $tr.find(\"td:eq(1) :text\").val(e);"+"\r\n"
		 * +"       $tr.find(\"td:eq(2) :text\").val(index);"+"\r\n"
		 * +"       $tr.find(\"td:eq(3) :text\").val(e);"+"\r\n"
		 * +"       $(\"#add_cgreport_t_item_table\").append($tr);"+"\r\n"
		 * +"      }); "+"\r\n"
		 * +"    resetTrNum(\"add_cgreport_t_item_table\");"+"\r\n"
		 * +"    }"+"\r\n" +"  }"+"\r\n" +"  });"+"\r\n" +" });"+"\r\n" +"});");
		 * cgreport_head.setSqlPlugIn("0");
		 */
		//		cgreport_head.setCreateName("管理员");
		//		cgreport_head.setJformVersion("87");
		//		cgreport_head.setJformType(2);
		//		cgreport_head.setRelationType(0);
		//		cgreport_head.setSubTableStr("cgreport_t_item");
		//		/* cgreport_head.setSqlPlugIn("select * from s_t_user"); */
		//
		//		commonDao.saveOrUpdate(cgreport_head);
		//
		//		CgFormHeadEntity cgreport_item = new CgFormHeadEntity();
		//		cgreport_item.setTableName("cgreport_t_item");
		//		cgreport_item.setIsTree("N");
		//		cgreport_item.setIsPagination("Y");
		//		cgreport_item.setIsCheckbox("N");
		//		cgreport_item.setQuerymode("single");
		//		cgreport_item.setIsDbSynch("N");
		//		cgreport_item.setContent("动态报表配置明细");
		//		cgreport_item.setCreateBy("admin");
		//		cgreport_item.setCreateDate(new Date());
		//		/*
		//		 * cgreport_item.setJsPlugIn("0"); cgreport_item.setSqlPlugIn("0");
		//		 */
		//		cgreport_item.setCreateName("管理员");
		//		cgreport_item.setJformVersion("12");
		//		cgreport_item.setJformType(3);
		//		cgreport_item.setRelationType(0);
		//		commonDao.saveOrUpdate(cgreport_item);

		CgFormHeadEntity customer = new CgFormHeadEntity();
		customer.setTableName("jform_order_customer");
		customer.setIsTree("N");
		customer.setIsPagination("Y");
		customer.setIsCheckbox("Y");
		customer.setQuerymode("single");
		customer.setIsDbSynch("N");
		customer.setContent("订单客户信息");
		customer.setCreateBy("admin");
		customer.setJformPkType("UUID");
		customer.setCreateDate(new Date());
		/*
		 * customer.setJsPlugIn("alert(' hello world ');");
		 * customer.setSqlPlugIn("0");
		 */
		customer.setCreateName("管理员");
		customer.setJformVersion("16");
		customer.setJformType(3);
		customer.setRelationType(0);
		commonDao.saveOrUpdate(customer);

		CgFormHeadEntity ticket = new CgFormHeadEntity();
		ticket.setTableName("jform_order_ticket");
		ticket.setIsTree("N");
		ticket.setIsPagination("Y");
		ticket.setIsCheckbox("N");
		ticket.setQuerymode("single");
		ticket.setIsDbSynch("N");
		ticket.setJformPkType("UUID");
		ticket.setContent("订单机票信息");
		ticket.setCreateBy("admin");
		ticket.setCreateDate(new Date());
		/*
		 * ticket.setJsPlugIn(
		 * "$(\"input/[name=\'name\']\").change( function() { \r\n 这里可以写些验证代码 \r\n  var name = $(\"#name\").val();  \r\nalert(name);});"
		 * ); ticket.setSqlPlugIn("0");
		 */
		ticket.setCreateName("管理员");
		ticket.setJformVersion("20");
		ticket.setJformType(3);
		ticket.setRelationType(0);
		commonDao.saveOrUpdate(ticket);

		CgFormHeadEntity price1 = new CgFormHeadEntity();
		price1.setTableName("jform_price1");
		price1.setIsTree("N");
		price1.setIsPagination("Y");
		price1.setIsCheckbox("N");
		price1.setQuerymode("group");
		price1.setIsDbSynch("N");
		price1.setJformPkType("UUID");
		price1.setContent("价格认证机构统计表");
		price1.setCreateBy("admin");
		price1.setCreateDate(new Date());
		/*
		 * price1.setJsPlugIn("0"); price1.setSqlPlugIn("0");
		 */
		price1.setCreateName("管理员");
		price1.setJformVersion("3");
		price1.setJformType(1);
		price1.setRelationType(0);
		commonDao.saveOrUpdate(price1);

	}

	/**
	 * @Description 同步智能表单字段表
	 * @author tanghan 2013-7-28
	 */
	private void repairCgFormField() {
		// 表单[订单主信息] - 字段清单
		CgFormHeadEntity jform_order_main = commonDao.findByProperty(CgFormHeadEntity.class, "tableName", "jform_order_main").get(0);
		CgFormFieldEntity jform_order_main_id = new CgFormFieldEntity();
		jform_order_main_id.setFieldName("id");
		jform_order_main_id.setTable(jform_order_main);
		jform_order_main_id.setFieldLength(0);
		jform_order_main_id.setIsKey("Y");
		jform_order_main_id.setIsNull("N");
		jform_order_main_id.setIsQuery("N");
		jform_order_main_id.setIsShow("N");
		jform_order_main_id.setIsShowList("N");
		jform_order_main_id.setShowType("text");
		jform_order_main_id.setLength(36);
		jform_order_main_id.setType("string");
		jform_order_main_id.setOrderNum(0);
		jform_order_main_id.setPointLength(0);
		jform_order_main_id.setQueryMode("single");
		jform_order_main_id.setContent("主键");
		jform_order_main_id.setCreateBy("admin");
		jform_order_main_id.setCreateDate(new Date());
		jform_order_main_id.setCreateName("管理员");
		jform_order_main_id.setDictField("");
		jform_order_main_id.setDictTable("");
		jform_order_main_id.setMainTable("");
		jform_order_main_id.setMainField("");
		commonDao.saveOrUpdate(jform_order_main_id);

		CgFormFieldEntity jform_order_main_order_code = new CgFormFieldEntity();
		jform_order_main_order_code.setFieldName("order_code");
		jform_order_main_order_code.setTable(jform_order_main);
		jform_order_main_order_code.setFieldLength(0);
		jform_order_main_order_code.setIsKey("N");
		jform_order_main_order_code.setIsNull("Y");
		jform_order_main_order_code.setIsQuery("Y");
		jform_order_main_order_code.setIsShow("Y");
		jform_order_main_order_code.setIsShowList("Y");
		jform_order_main_order_code.setShowType("text");
		jform_order_main_order_code.setLength(50);
		jform_order_main_order_code.setType("string");
		jform_order_main_order_code.setOrderNum(1);
		jform_order_main_order_code.setPointLength(0);
		jform_order_main_order_code.setQueryMode("single");
		jform_order_main_order_code.setContent("订单号");
		jform_order_main_order_code.setCreateBy("admin");
		jform_order_main_order_code.setCreateDate(new Date());
		jform_order_main_order_code.setCreateName("管理员");
		jform_order_main_order_code.setDictField("");
		jform_order_main_order_code.setDictTable("");
		jform_order_main_order_code.setMainTable("");
		jform_order_main_order_code.setMainField("");
		commonDao.saveOrUpdate(jform_order_main_order_code);

		CgFormFieldEntity jform_order_main_order_date = new CgFormFieldEntity();
		jform_order_main_order_date.setFieldName("order_date");
		jform_order_main_order_date.setTable(jform_order_main);
		jform_order_main_order_date.setFieldLength(0);
		jform_order_main_order_date.setIsKey("N");
		jform_order_main_order_date.setIsNull("Y");
		jform_order_main_order_date.setIsQuery("Y");
		jform_order_main_order_date.setIsShow("Y");
		jform_order_main_order_date.setIsShowList("Y");
		jform_order_main_order_date.setShowType("date");
		jform_order_main_order_date.setLength(20);
		jform_order_main_order_date.setType("Date");
		jform_order_main_order_date.setOrderNum(2);
		jform_order_main_order_date.setPointLength(0);
		jform_order_main_order_date.setQueryMode("single");
		jform_order_main_order_date.setContent("订单日期");
		jform_order_main_order_date.setCreateBy("admin");
		jform_order_main_order_date.setCreateDate(new Date());
		jform_order_main_order_date.setCreateName("管理员");
		jform_order_main_order_date.setDictField("");
		jform_order_main_order_date.setDictTable("");
		jform_order_main_order_date.setMainTable("");
		jform_order_main_order_date.setMainField("");
		commonDao.saveOrUpdate(jform_order_main_order_date);

		CgFormFieldEntity jform_order_main_order_money = new CgFormFieldEntity();
		jform_order_main_order_money.setFieldName("order_money");
		jform_order_main_order_money.setTable(jform_order_main);
		jform_order_main_order_money.setFieldLength(0);
		jform_order_main_order_money.setIsKey("N");
		jform_order_main_order_money.setIsNull("Y");
		jform_order_main_order_money.setIsQuery("Y");
		jform_order_main_order_money.setIsShow("Y");
		jform_order_main_order_money.setIsShowList("Y");
		jform_order_main_order_money.setShowType("text");
		jform_order_main_order_money.setLength(10);
		jform_order_main_order_money.setType("double");
		jform_order_main_order_money.setOrderNum(3);
		jform_order_main_order_money.setPointLength(3);
		jform_order_main_order_money.setQueryMode("single");
		jform_order_main_order_money.setContent("订单金额");
		jform_order_main_order_money.setCreateBy("admin");
		jform_order_main_order_money.setCreateDate(new Date());
		jform_order_main_order_money.setCreateName("管理员");
		jform_order_main_order_money.setDictField("");
		jform_order_main_order_money.setDictTable("");
		jform_order_main_order_money.setMainTable("");
		jform_order_main_order_money.setMainField("");
		commonDao.saveOrUpdate(jform_order_main_order_money);

		CgFormFieldEntity jform_order_main_content = new CgFormFieldEntity();
		jform_order_main_content.setFieldName("content");
		jform_order_main_content.setTable(jform_order_main);
		jform_order_main_content.setFieldLength(0);
		jform_order_main_content.setIsKey("N");
		jform_order_main_content.setIsNull("Y");
		jform_order_main_content.setIsQuery("Y");
		jform_order_main_content.setIsShow("Y");
		jform_order_main_content.setIsShowList("Y");
		jform_order_main_content.setShowType("text");
		jform_order_main_content.setLength(255);
		jform_order_main_content.setType("string");
		jform_order_main_content.setOrderNum(4);
		jform_order_main_content.setPointLength(0);
		jform_order_main_content.setQueryMode("single");
		jform_order_main_content.setContent("备注");
		jform_order_main_content.setCreateBy("admin");
		jform_order_main_content.setCreateDate(new Date());
		jform_order_main_content.setCreateName("管理员");
		jform_order_main_content.setDictField("");
		jform_order_main_content.setDictTable("");
		jform_order_main_content.setMainTable("");
		jform_order_main_content.setMainField("");
		commonDao.saveOrUpdate(jform_order_main_content);

		// 表单[请假单] - 字段清单
		CgFormHeadEntity jform_leave = commonDao.findByProperty(CgFormHeadEntity.class, "tableName", "jform_leave").get(0);
		CgFormFieldEntity jform_leave_id = new CgFormFieldEntity();
		jform_leave_id.setFieldName("id");
		jform_leave_id.setTable(jform_leave);
		jform_leave_id.setFieldLength(0);
		jform_leave_id.setIsKey("Y");
		jform_leave_id.setIsNull("N");
		jform_leave_id.setIsQuery("N");
		jform_leave_id.setIsShow("N");
		jform_leave_id.setIsShowList("N");
		jform_leave_id.setShowType("text");
		jform_leave_id.setLength(36);
		jform_leave_id.setType("string");
		jform_leave_id.setOrderNum(0);
		jform_leave_id.setPointLength(0);
		jform_leave_id.setQueryMode("single");
		jform_leave_id.setContent("主键");
		jform_leave_id.setCreateBy("admin");
		jform_leave_id.setCreateDate(new Date());
		jform_leave_id.setCreateName("管理员");
		jform_leave_id.setDictField("");
		jform_leave_id.setDictTable("");
		jform_leave_id.setMainTable("");
		jform_leave_id.setMainField("");
		commonDao.saveOrUpdate(jform_leave_id);

		CgFormFieldEntity jform_leave_title = new CgFormFieldEntity();
		jform_leave_title.setFieldName("title");
		jform_leave_title.setTable(jform_leave);
		jform_leave_title.setFieldLength(0);
		jform_leave_title.setIsKey("N");
		jform_leave_title.setIsNull("N");
		jform_leave_title.setIsQuery("N");
		jform_leave_title.setIsShow("Y");
		jform_leave_title.setIsShowList("Y");
		jform_leave_title.setShowType("text");
		jform_leave_title.setLength(50);
		jform_leave_title.setType("string");
		jform_leave_title.setOrderNum(1);
		jform_leave_title.setPointLength(0);
		jform_leave_title.setQueryMode("single");
		jform_leave_title.setContent("请假标题");
		jform_leave_title.setCreateBy("admin");
		jform_leave_title.setCreateDate(new Date());
		jform_leave_title.setCreateName("管理员");
		jform_leave_title.setDictField("");
		jform_leave_title.setDictTable("");
		jform_leave_title.setMainTable("");
		jform_leave_title.setMainField("");
		commonDao.saveOrUpdate(jform_leave_title);

		CgFormFieldEntity jform_leave_people = new CgFormFieldEntity();
		jform_leave_people.setFieldName("people");
		jform_leave_people.setTable(jform_leave);
		jform_leave_people.setFieldLength(0);
		jform_leave_people.setIsKey("N");
		jform_leave_people.setIsNull("N");
		jform_leave_people.setIsQuery("Y");
		jform_leave_people.setIsShow("Y");
		jform_leave_people.setIsShowList("Y");
		jform_leave_people.setShowType("text");
		jform_leave_people.setLength(20);
		jform_leave_people.setType("string");
		jform_leave_people.setOrderNum(2);
		jform_leave_people.setPointLength(0);
		jform_leave_people.setQueryMode("single");
		jform_leave_people.setContent("请假人");
		jform_leave_people.setCreateBy("admin");
		jform_leave_people.setCreateDate(new Date());
		jform_leave_people.setCreateName("管理员");
		jform_leave_people.setDictField("");
		jform_leave_people.setDictTable("");
		jform_leave_people.setMainTable("");
		jform_leave_people.setMainField("");
		commonDao.saveOrUpdate(jform_leave_people);

		CgFormFieldEntity jform_leave_sex = new CgFormFieldEntity();
		jform_leave_sex.setFieldName("sex");
		jform_leave_sex.setTable(jform_leave);
		jform_leave_sex.setFieldLength(0);
		jform_leave_sex.setIsKey("N");
		jform_leave_sex.setIsNull("N");
		jform_leave_sex.setIsQuery("Y");
		jform_leave_sex.setIsShow("Y");
		jform_leave_sex.setIsShowList("Y");
		jform_leave_sex.setShowType("list");
		jform_leave_sex.setLength(10);
		jform_leave_sex.setType("string");
		jform_leave_sex.setOrderNum(3);
		jform_leave_sex.setPointLength(0);
		jform_leave_sex.setQueryMode("single");
		jform_leave_sex.setContent("性别");
		jform_leave_sex.setCreateBy("admin");
		jform_leave_sex.setCreateDate(new Date());
		jform_leave_sex.setCreateName("管理员");
		jform_leave_sex.setDictField("sex");
		jform_leave_sex.setDictTable("");
		jform_leave_sex.setMainTable("");
		jform_leave_sex.setMainField("");
		commonDao.saveOrUpdate(jform_leave_sex);

		CgFormFieldEntity jform_leave_begindate = new CgFormFieldEntity();
		jform_leave_begindate.setFieldName("begindate");
		jform_leave_begindate.setTable(jform_leave);
		jform_leave_begindate.setFieldLength(0);
		jform_leave_begindate.setIsKey("N");
		jform_leave_begindate.setIsNull("N");
		jform_leave_begindate.setIsQuery("N");
		jform_leave_begindate.setIsShow("Y");
		jform_leave_begindate.setIsShowList("Y");
		jform_leave_begindate.setShowType("date");
		jform_leave_begindate.setLength(0);
		jform_leave_begindate.setType("Date");
		jform_leave_begindate.setOrderNum(4);
		jform_leave_begindate.setPointLength(0);
		jform_leave_begindate.setQueryMode("group");
		jform_leave_begindate.setContent("请假开始时间");
		jform_leave_begindate.setCreateBy("admin");
		jform_leave_begindate.setCreateDate(new Date());
		jform_leave_begindate.setCreateName("管理员");
		jform_leave_begindate.setDictField("");
		jform_leave_begindate.setDictTable("");
		jform_leave_begindate.setMainTable("");
		jform_leave_begindate.setMainField("");
		commonDao.saveOrUpdate(jform_leave_begindate);

		CgFormFieldEntity jform_leave_enddate = new CgFormFieldEntity();
		jform_leave_enddate.setFieldName("enddate");
		jform_leave_enddate.setTable(jform_leave);
		jform_leave_enddate.setFieldLength(0);
		jform_leave_enddate.setIsKey("N");
		jform_leave_enddate.setIsNull("N");
		jform_leave_enddate.setIsQuery("N");
		jform_leave_enddate.setIsShow("Y");
		jform_leave_enddate.setIsShowList("Y");
		jform_leave_enddate.setShowType("datetime");
		jform_leave_enddate.setLength(0);
		jform_leave_enddate.setType("Date");
		jform_leave_enddate.setOrderNum(5);
		jform_leave_enddate.setPointLength(0);
		jform_leave_enddate.setQueryMode("group");
		jform_leave_enddate.setContent("请假结束时间");
		jform_leave_enddate.setCreateBy("admin");
		jform_leave_enddate.setCreateDate(new Date());
		jform_leave_enddate.setCreateName("管理员");
		jform_leave_enddate.setDictField("");
		jform_leave_enddate.setDictTable("");
		jform_leave_enddate.setMainTable("");
		jform_leave_enddate.setMainField("");
		commonDao.saveOrUpdate(jform_leave_enddate);

		CgFormFieldEntity jform_leave_hol_dept = new CgFormFieldEntity();
		jform_leave_hol_dept.setFieldName("hol_dept");
		jform_leave_hol_dept.setTable(jform_leave);
		jform_leave_hol_dept.setFieldLength(0);
		jform_leave_hol_dept.setIsKey("N");
		jform_leave_hol_dept.setIsNull("N");
		jform_leave_hol_dept.setIsQuery("N");
		jform_leave_hol_dept.setIsShow("Y");
		jform_leave_hol_dept.setIsShowList("Y");
		jform_leave_hol_dept.setShowType("list");
		jform_leave_hol_dept.setLength(32);
		jform_leave_hol_dept.setType("string");
		jform_leave_hol_dept.setOrderNum(7);
		jform_leave_hol_dept.setPointLength(0);
		jform_leave_hol_dept.setQueryMode("single");
		jform_leave_hol_dept.setContent("所属部门");
		jform_leave_hol_dept.setCreateBy("admin");
		jform_leave_hol_dept.setCreateDate(new Date());
		jform_leave_hol_dept.setCreateName("管理员");
		jform_leave_hol_dept.setDictField("id");
		jform_leave_hol_dept.setDictTable("s_t_department");
		jform_leave_hol_dept.setDictText("name");
		jform_leave_hol_dept.setMainTable("");
		jform_leave_hol_dept.setMainField("");
		commonDao.saveOrUpdate(jform_leave_hol_dept);

		CgFormFieldEntity jform_leave_hol_reson = new CgFormFieldEntity();
		jform_leave_hol_reson.setFieldName("hol_reson");
		jform_leave_hol_reson.setTable(jform_leave);
		jform_leave_hol_reson.setFieldLength(0);
		jform_leave_hol_reson.setIsKey("N");
		jform_leave_hol_reson.setIsNull("N");
		jform_leave_hol_reson.setIsQuery("N");
		jform_leave_hol_reson.setIsShow("Y");
		jform_leave_hol_reson.setIsShowList("Y");
		jform_leave_hol_reson.setShowType("text");
		jform_leave_hol_reson.setLength(255);
		jform_leave_hol_reson.setType("string");
		jform_leave_hol_reson.setOrderNum(8);
		jform_leave_hol_reson.setPointLength(0);
		jform_leave_hol_reson.setQueryMode("single");
		jform_leave_hol_reson.setContent("请假原因");
		jform_leave_hol_reson.setCreateBy("admin");
		jform_leave_hol_reson.setCreateDate(new Date());
		jform_leave_hol_reson.setCreateName("管理员");
		jform_leave_hol_reson.setDictField("");
		jform_leave_hol_reson.setDictTable("");
		jform_leave_hol_reson.setMainTable("");
		jform_leave_hol_reson.setMainField("");
		commonDao.saveOrUpdate(jform_leave_hol_reson);

		CgFormFieldEntity jform_leave_dep_leader = new CgFormFieldEntity();
		jform_leave_dep_leader.setFieldName("dep_leader");
		jform_leave_dep_leader.setTable(jform_leave);
		jform_leave_dep_leader.setFieldLength(0);
		jform_leave_dep_leader.setIsKey("N");
		jform_leave_dep_leader.setIsNull("N");
		jform_leave_dep_leader.setIsQuery("N");
		jform_leave_dep_leader.setIsShow("Y");
		jform_leave_dep_leader.setIsShowList("Y");
		jform_leave_dep_leader.setShowType("text");
		jform_leave_dep_leader.setLength(20);
		jform_leave_dep_leader.setType("string");
		jform_leave_dep_leader.setOrderNum(9);
		jform_leave_dep_leader.setPointLength(0);
		jform_leave_dep_leader.setQueryMode("single");
		jform_leave_dep_leader.setContent("部门审批人");
		jform_leave_dep_leader.setCreateBy("admin");
		jform_leave_dep_leader.setCreateDate(new Date());
		jform_leave_dep_leader.setCreateName("管理员");
		jform_leave_dep_leader.setDictField("");
		jform_leave_dep_leader.setDictTable("");
		jform_leave_dep_leader.setMainTable("");
		jform_leave_dep_leader.setMainField("");
		commonDao.saveOrUpdate(jform_leave_dep_leader);

		CgFormFieldEntity jform_leave_content = new CgFormFieldEntity();
		jform_leave_content.setFieldName("content");
		jform_leave_content.setTable(jform_leave);
		jform_leave_content.setFieldLength(0);
		jform_leave_content.setIsKey("N");
		jform_leave_content.setIsNull("N");
		jform_leave_content.setIsQuery("N");
		jform_leave_content.setIsShow("Y");
		jform_leave_content.setIsShowList("Y");
		jform_leave_content.setShowType("text");
		jform_leave_content.setLength(255);
		jform_leave_content.setType("string");
		jform_leave_content.setOrderNum(10);
		jform_leave_content.setPointLength(0);
		jform_leave_content.setQueryMode("single");
		jform_leave_content.setContent("部门审批意见");
		jform_leave_content.setCreateBy("admin");
		jform_leave_content.setCreateDate(new Date());
		jform_leave_content.setCreateName("管理员");
		jform_leave_content.setDictField("");
		jform_leave_content.setDictTable("");
		jform_leave_content.setMainTable("");
		jform_leave_content.setMainField("");
		commonDao.saveOrUpdate(jform_leave_content);

		CgFormFieldEntity jform_leave_day_num = new CgFormFieldEntity();
		jform_leave_day_num.setFieldName("day_num");
		jform_leave_day_num.setTable(jform_leave);
		jform_leave_day_num.setFieldLength(120);
		jform_leave_day_num.setIsKey("N");
		jform_leave_day_num.setIsNull("Y");
		jform_leave_day_num.setIsQuery("N");
		jform_leave_day_num.setIsShow("Y");
		jform_leave_day_num.setIsShowList("Y");
		jform_leave_day_num.setShowType("text");
		jform_leave_day_num.setLength(10);
		jform_leave_day_num.setType("int");
		jform_leave_day_num.setOrderNum(6);
		jform_leave_day_num.setPointLength(0);
		jform_leave_day_num.setQueryMode("single");
		jform_leave_day_num.setContent("请假天数");
		jform_leave_day_num.setCreateBy("admin");
		jform_leave_day_num.setCreateDate(new Date());
		jform_leave_day_num.setCreateName("管理员");
		jform_leave_day_num.setDictField("");
		jform_leave_day_num.setDictTable("");
		jform_leave_day_num.setMainTable("");
		jform_leave_day_num.setMainField("");
		commonDao.saveOrUpdate(jform_leave_day_num);
		// 表单[动态报表配置抬头] - 字段清单
		//		CgFormHeadEntity cgreport_t_head = commonDao.findByProperty(
		//				CgFormHeadEntity.class, "tableName", "cgreport_t_head")
		//				.get(0);
		//		CgFormFieldEntity cgreport_t_head_id = new CgFormFieldEntity();
		//		// begin - 动态报表配置的js增强
		//		String cgReportFormId = cgreport_t_head.getId();
		//		CgformEnhanceJsEntity cgReportJs = new CgformEnhanceJsEntity();
		//		cgReportJs.setCgJsType("form");
		//		cgReportJs
		//				.setCgJs(new BigInteger(
		//						"242866756E6374696F6E28297B0D0A242822626F647922292E617070656E6428223C6C696E6B20687265663D5C22706C75672D696E2F6C68674469616C6F672F736B696E732F64656661756C742E6373735C222072656C3D5C227374796C6573686565745C222069643D5C226C68676469616C6F676C696E6B5C223E22293B0D0A766172202462746E203D202428223C64697620636C6173733D5C2275695F627574746F6E735C2220207374796C653D5C22646973706C61793A696E6C696E652D626C6F636B3B5C223E3C696E707574207374796C653D5C22706F736974696F6E3A2072656C61746976653B746F703A202D3870783B5C2220636C6173733D5C2275695F73746174655F686967686C696768745C2220747970653D5C22627574746F6E5C222076616C75653D5C2273716CBDE2CEF65C22202020202020202069643D5C2273716C416E616C797A655C22202F3E3C2F6469763E22293B0D0A242822236367725F73716C22292E6166746572282462746E293B0D0A2462746E2E636C69636B2866756E6374696F6E28297B0D0A20242E616A6178287B0D0A202020202075726C3A2263675265706F7274436F6E74726F6C6C65722E646F3F6765744669656C6473222C0D0A20202020646174613A7B73716C3A242822236367725F73716C22292E76616C28297D2C0D0A09747970653A22506F7374222C0D0A2020202064617461547970653A226A736F6E222C0D0A20202020737563636573733A66756E6374696F6E2864617461297B0D0A202020202020696628646174612E7374617475733D3D227375636365737322297B0D0A202020202020202020242822236164645F6A666F726D5F63677265706F72745F6974656D5F7461626C6522292E656D70747928293B0D0A202020202020242E6561636828646174612E64617461732C66756E6374696F6E28696E6465782C65297B0D0A202020202020202076617220247472203D20242822236164645F6A666F726D5F63677265706F72745F6974656D5F7461626C655F74656D706C61746520747222292E636C6F6E6528293B0D0A2020202020202474722E66696E64282274643A6571283129203A7465787422292E76616C2865293B0D0A202020202020202474722E66696E64282274643A6571283229203A7465787422292E76616C28696E646578293B0D0A202020202020202474722E66696E64282274643A6571283329203A7465787422292E76616C2865293B0D0A20202020202020242822236164645F6A666F726D5F63677265706F72745F6974656D5F7461626C6522292E617070656E6428247472293B0D0A2020202020207D293B200D0A20202020726573657454724E756D28226164645F6A666F726D5F63677265706F72745F6974656D5F7461626C6522293B0D0A202020207D656C73657B0D0A0909242E6D657373616765722E616C6572742827B4EDCEF3272C646174612E6461746173293B0D0A097D0D0A20207D0D0A20207D293B0D0A207D293B0D0A7D293B",
		//						16).toByteArray());
		//		cgReportJs.setFormId(cgReportFormId);
		//		commonDao.saveOrUpdate(cgReportJs);
		//		// end - 动态报表配置的js增强
		//		cgreport_t_head_id.setFieldName("id");
		//		cgreport_t_head_id.setTable(cgreport_t_head);
		//		cgreport_t_head_id.setFieldLength(120);
		//		cgreport_t_head_id.setIsKey("Y");
		//		cgreport_t_head_id.setIsNull("N");
		//		cgreport_t_head_id.setIsQuery("N");
		//		cgreport_t_head_id.setIsShow("N");
		//		cgreport_t_head_id.setIsShowList("N");
		//		cgreport_t_head_id.setShowType("checkbox");
		//		cgreport_t_head_id.setLength(36);
		//		cgreport_t_head_id.setType("string");
		//		cgreport_t_head_id.setOrderNum(0);
		//		cgreport_t_head_id.setPointLength(0);
		//		cgreport_t_head_id.setQueryMode("single");
		//		cgreport_t_head_id.setContent("主键");
		//		cgreport_t_head_id.setCreateBy("admin");
		//		cgreport_t_head_id.setCreateDate(new Date());
		//		cgreport_t_head_id.setCreateName("管理员");
		//		cgreport_t_head_id.setDictField("");
		//		cgreport_t_head_id.setDictTable("");
		//		cgreport_t_head_id.setMainTable("");
		//		cgreport_t_head_id.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_head_id);
		//
		//		CgFormFieldEntity cgreport_t_head_code = new CgFormFieldEntity();
		//		cgreport_t_head_code.setFieldName("code");
		//		cgreport_t_head_code.setTable(cgreport_t_head);
		//		cgreport_t_head_code.setFieldLength(120);
		//		cgreport_t_head_code.setIsKey("N");
		//		cgreport_t_head_code.setIsNull("N");
		//		cgreport_t_head_code.setIsQuery("Y");
		//		cgreport_t_head_code.setIsShow("Y");
		//		cgreport_t_head_code.setIsShowList("Y");
		//		cgreport_t_head_code.setShowType("text");
		//		cgreport_t_head_code.setLength(36);
		//		cgreport_t_head_code.setType("string");
		//		cgreport_t_head_code.setOrderNum(1);
		//		cgreport_t_head_code.setPointLength(0);
		//		cgreport_t_head_code.setQueryMode("single");
		//		cgreport_t_head_code.setContent("编码");
		//		cgreport_t_head_code.setCreateBy("admin");
		//		cgreport_t_head_code.setCreateDate(new Date());
		//		cgreport_t_head_code.setCreateName("管理员");
		//		cgreport_t_head_code.setDictField("");
		//		cgreport_t_head_code.setDictTable("");
		//		cgreport_t_head_code.setMainTable("");
		//		cgreport_t_head_code.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_head_code);
		//
		//		CgFormFieldEntity cgreport_t_head_name = new CgFormFieldEntity();
		//		cgreport_t_head_name.setFieldName("name");
		//		cgreport_t_head_name.setTable(cgreport_t_head);
		//		cgreport_t_head_name.setFieldLength(120);
		//		cgreport_t_head_name.setIsKey("N");
		//		cgreport_t_head_name.setIsNull("N");
		//		cgreport_t_head_name.setIsQuery("Y");
		//		cgreport_t_head_name.setIsShow("Y");
		//		cgreport_t_head_name.setIsShowList("Y");
		//		cgreport_t_head_name.setShowType("text");
		//		cgreport_t_head_name.setLength(100);
		//		cgreport_t_head_name.setType("string");
		//		cgreport_t_head_name.setOrderNum(2);
		//		cgreport_t_head_name.setPointLength(0);
		//		cgreport_t_head_name.setQueryMode("single");
		//		cgreport_t_head_name.setContent("名称");
		//		cgreport_t_head_name.setCreateBy("admin");
		//		cgreport_t_head_name.setCreateDate(new Date());
		//		cgreport_t_head_name.setCreateName("管理员");
		//		cgreport_t_head_name.setDictField("");
		//		cgreport_t_head_name.setDictTable("");
		//		cgreport_t_head_name.setMainTable("");
		//		cgreport_t_head_name.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_head_name);
		//
		//		CgFormFieldEntity cgreport_t_head_cgr_sql = new CgFormFieldEntity();
		//		cgreport_t_head_cgr_sql.setFieldName("cgr_sql");
		//		cgreport_t_head_cgr_sql.setTable(cgreport_t_head);
		//		cgreport_t_head_cgr_sql.setFieldLength(120);
		//		cgreport_t_head_cgr_sql.setIsKey("N");
		//		cgreport_t_head_cgr_sql.setIsNull("N");
		//		cgreport_t_head_cgr_sql.setIsQuery("Y");
		//		cgreport_t_head_cgr_sql.setIsShow("Y");
		//		cgreport_t_head_cgr_sql.setIsShowList("Y");
		//		cgreport_t_head_cgr_sql.setShowType("textarea");
		//		cgreport_t_head_cgr_sql.setLength(2000);
		//		cgreport_t_head_cgr_sql.setType("string");
		//		cgreport_t_head_cgr_sql.setOrderNum(3);
		//		cgreport_t_head_cgr_sql.setPointLength(0);
		//		cgreport_t_head_cgr_sql.setQueryMode("single");
		//		cgreport_t_head_cgr_sql.setContent("查询数据SQL");
		//		cgreport_t_head_cgr_sql.setCreateBy("admin");
		//		cgreport_t_head_cgr_sql.setCreateDate(new Date());
		//		cgreport_t_head_cgr_sql.setCreateName("管理员");
		//		cgreport_t_head_cgr_sql.setDictField("");
		//		cgreport_t_head_cgr_sql.setDictTable("");
		//		cgreport_t_head_cgr_sql.setMainTable("");
		//		cgreport_t_head_cgr_sql.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_head_cgr_sql);
		//
		//		CgFormFieldEntity cgreport_t_head_content = new CgFormFieldEntity();
		//		cgreport_t_head_content.setFieldName("content");
		//		cgreport_t_head_content.setTable(cgreport_t_head);
		//		cgreport_t_head_content.setFieldLength(120);
		//		cgreport_t_head_content.setIsKey("N");
		//		cgreport_t_head_content.setIsNull("N");
		//		cgreport_t_head_content.setIsQuery("Y");
		//		cgreport_t_head_content.setIsShow("Y");
		//		cgreport_t_head_content.setIsShowList("Y");
		//		cgreport_t_head_content.setShowType("textarea");
		//		cgreport_t_head_content.setLength(1000);
		//		cgreport_t_head_content.setType("string");
		//		cgreport_t_head_content.setOrderNum(4);
		//		cgreport_t_head_content.setPointLength(0);
		//		cgreport_t_head_content.setQueryMode("single");
		//		cgreport_t_head_content.setContent("描述");
		//		cgreport_t_head_content.setCreateBy("admin");
		//		cgreport_t_head_content.setCreateDate(new Date());
		//		cgreport_t_head_content.setCreateName("管理员");
		//		cgreport_t_head_content.setDictField("");
		//		cgreport_t_head_content.setDictTable("");
		//		cgreport_t_head_content.setMainTable("");
		//		cgreport_t_head_content.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_head_content);
		//
		//		// 表单[动态报表配置明细] - 字段清单
		//		CgFormHeadEntity cgreport_t_item = commonDao.findByProperty(
		//				CgFormHeadEntity.class, "tableName", "cgreport_t_item")
		//				.get(0);
		//		CgFormFieldEntity cgreport_t_item_id = new CgFormFieldEntity();
		//		cgreport_t_item_id.setFieldName("id");
		//		cgreport_t_item_id.setTable(cgreport_t_item);
		//		cgreport_t_item_id.setFieldLength(120);
		//		cgreport_t_item_id.setIsKey("Y");
		//		cgreport_t_item_id.setIsNull("N");
		//		cgreport_t_item_id.setIsQuery("N");
		//		cgreport_t_item_id.setIsShow("N");
		//		cgreport_t_item_id.setIsShowList("N");
		//		cgreport_t_item_id.setShowType("checkbox");
		//		cgreport_t_item_id.setLength(36);
		//		cgreport_t_item_id.setType("string");
		//		cgreport_t_item_id.setOrderNum(0);
		//		cgreport_t_item_id.setPointLength(0);
		//		cgreport_t_item_id.setQueryMode("single");
		//		cgreport_t_item_id.setContent("主键");
		//		cgreport_t_item_id.setCreateBy("admin");
		//		cgreport_t_item_id.setCreateDate(new Date());
		//		cgreport_t_item_id.setCreateName("管理员");
		//		cgreport_t_item_id.setDictField("");
		//		cgreport_t_item_id.setDictTable("");
		//		cgreport_t_item_id.setMainTable("");
		//		cgreport_t_item_id.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_id);
		//
		//		CgFormFieldEntity cgreport_t_item_field_name = new CgFormFieldEntity();
		//		cgreport_t_item_field_name.setFieldName("field_name");
		//		cgreport_t_item_field_name.setTable(cgreport_t_item);
		//		cgreport_t_item_field_name.setFieldLength(120);
		//		cgreport_t_item_field_name.setIsKey("N");
		//		cgreport_t_item_field_name.setIsNull("Y");
		//		cgreport_t_item_field_name.setIsQuery("N");
		//		cgreport_t_item_field_name.setIsShow("Y");
		//		cgreport_t_item_field_name.setIsShowList("Y");
		//		cgreport_t_item_field_name.setShowType("text");
		//		cgreport_t_item_field_name.setLength(36);
		//		cgreport_t_item_field_name.setType("string");
		//		cgreport_t_item_field_name.setOrderNum(1);
		//		cgreport_t_item_field_name.setPointLength(0);
		//		cgreport_t_item_field_name.setQueryMode("single");
		//		cgreport_t_item_field_name.setContent("字段名");
		//		cgreport_t_item_field_name.setCreateBy("admin");
		//		cgreport_t_item_field_name.setCreateDate(new Date());
		//		cgreport_t_item_field_name.setCreateName("管理员");
		//		cgreport_t_item_field_name.setDictField("");
		//		cgreport_t_item_field_name.setDictTable("");
		//		cgreport_t_item_field_name.setMainTable("");
		//		cgreport_t_item_field_name.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_field_name);
		//
		//		CgFormFieldEntity cgreport_t_item_order_num = new CgFormFieldEntity();
		//		cgreport_t_item_order_num.setFieldName("order_num");
		//		cgreport_t_item_order_num.setTable(cgreport_t_item);
		//		cgreport_t_item_order_num.setFieldLength(120);
		//		cgreport_t_item_order_num.setIsKey("N");
		//		cgreport_t_item_order_num.setIsNull("Y");
		//		cgreport_t_item_order_num.setIsQuery("N");
		//		cgreport_t_item_order_num.setIsShow("Y");
		//		cgreport_t_item_order_num.setIsShowList("Y");
		//		cgreport_t_item_order_num.setShowType("text");
		//		cgreport_t_item_order_num.setLength(10);
		//		cgreport_t_item_order_num.setType("int");
		//		cgreport_t_item_order_num.setOrderNum(2);
		//		cgreport_t_item_order_num.setPointLength(0);
		//		cgreport_t_item_order_num.setQueryMode("single");
		//		cgreport_t_item_order_num.setContent("字段序号");
		//		cgreport_t_item_order_num.setCreateBy("admin");
		//		cgreport_t_item_order_num.setCreateDate(new Date());
		//		cgreport_t_item_order_num.setCreateName("管理员");
		//		cgreport_t_item_order_num.setDictField("");
		//		cgreport_t_item_order_num.setDictTable("");
		//		cgreport_t_item_order_num.setMainTable("");
		//		cgreport_t_item_order_num.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_order_num);
		//
		//		CgFormFieldEntity cgreport_t_item_s_mode = new CgFormFieldEntity();
		//		cgreport_t_item_s_mode.setFieldName("s_mode");
		//		cgreport_t_item_s_mode.setTable(cgreport_t_item);
		//		cgreport_t_item_s_mode.setFieldLength(120);
		//		cgreport_t_item_s_mode.setIsKey("N");
		//		cgreport_t_item_s_mode.setIsNull("Y");
		//		cgreport_t_item_s_mode.setIsQuery("N");
		//		cgreport_t_item_s_mode.setIsShow("Y");
		//		cgreport_t_item_s_mode.setIsShowList("Y");
		//		cgreport_t_item_s_mode.setShowType("list");
		//		cgreport_t_item_s_mode.setLength(10);
		//		cgreport_t_item_s_mode.setType("string");
		//		cgreport_t_item_s_mode.setOrderNum(5);
		//		cgreport_t_item_s_mode.setPointLength(0);
		//		cgreport_t_item_s_mode.setQueryMode("single");
		//		cgreport_t_item_s_mode.setContent("查询模式");
		//		cgreport_t_item_s_mode.setCreateBy("admin");
		//		cgreport_t_item_s_mode.setCreateDate(new Date());
		//		cgreport_t_item_s_mode.setCreateName("管理员");
		//		cgreport_t_item_s_mode.setDictField("searchmode");
		//		cgreport_t_item_s_mode.setDictTable("");
		//		cgreport_t_item_s_mode.setMainTable("");
		//		cgreport_t_item_s_mode.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_s_mode);
		//
		//		CgFormFieldEntity cgreport_t_item_replace_va = new CgFormFieldEntity();
		//		cgreport_t_item_replace_va.setFieldName("replace_va");
		//		cgreport_t_item_replace_va.setTable(cgreport_t_item);
		//		cgreport_t_item_replace_va.setFieldLength(120);
		//		cgreport_t_item_replace_va.setIsKey("N");
		//		cgreport_t_item_replace_va.setIsNull("Y");
		//		cgreport_t_item_replace_va.setIsQuery("N");
		//		cgreport_t_item_replace_va.setIsShow("Y");
		//		cgreport_t_item_replace_va.setIsShowList("Y");
		//		cgreport_t_item_replace_va.setShowType("text");
		//		cgreport_t_item_replace_va.setLength(36);
		//		cgreport_t_item_replace_va.setType("string");
		//		cgreport_t_item_replace_va.setOrderNum(6);
		//		cgreport_t_item_replace_va.setPointLength(0);
		//		cgreport_t_item_replace_va.setQueryMode("single");
		//		cgreport_t_item_replace_va.setContent("取值表达式");
		//		cgreport_t_item_replace_va.setCreateBy("admin");
		//		cgreport_t_item_replace_va.setCreateDate(new Date());
		//		cgreport_t_item_replace_va.setCreateName("管理员");
		//		cgreport_t_item_replace_va.setDictField("");
		//		cgreport_t_item_replace_va.setDictTable("");
		//		cgreport_t_item_replace_va.setMainTable("");
		//		cgreport_t_item_replace_va.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_replace_va);
		//
		//		CgFormFieldEntity cgreport_t_item_dict_code = new CgFormFieldEntity();
		//		cgreport_t_item_dict_code.setFieldName("dict_code");
		//		cgreport_t_item_dict_code.setTable(cgreport_t_item);
		//		cgreport_t_item_dict_code.setFieldLength(120);
		//		cgreport_t_item_dict_code.setIsKey("N");
		//		cgreport_t_item_dict_code.setIsNull("Y");
		//		cgreport_t_item_dict_code.setIsQuery("N");
		//		cgreport_t_item_dict_code.setIsShow("Y");
		//		cgreport_t_item_dict_code.setIsShowList("Y");
		//		cgreport_t_item_dict_code.setShowType("text");
		//		cgreport_t_item_dict_code.setLength(36);
		//		cgreport_t_item_dict_code.setType("string");
		//		cgreport_t_item_dict_code.setOrderNum(7);
		//		cgreport_t_item_dict_code.setPointLength(0);
		//		cgreport_t_item_dict_code.setQueryMode("single");
		//		cgreport_t_item_dict_code.setContent("字典Code");
		//		cgreport_t_item_dict_code.setCreateBy("admin");
		//		cgreport_t_item_dict_code.setCreateDate(new Date());
		//		cgreport_t_item_dict_code.setCreateName("管理员");
		//		cgreport_t_item_dict_code.setDictField("");
		//		cgreport_t_item_dict_code.setDictTable("");
		//		cgreport_t_item_dict_code.setMainTable("");
		//		cgreport_t_item_dict_code.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_dict_code);
		//
		//		CgFormFieldEntity cgreport_t_item_s_flag = new CgFormFieldEntity();
		//		cgreport_t_item_s_flag.setFieldName("s_flag");
		//		cgreport_t_item_s_flag.setTable(cgreport_t_item);
		//		cgreport_t_item_s_flag.setFieldLength(120);
		//		cgreport_t_item_s_flag.setIsKey("N");
		//		cgreport_t_item_s_flag.setIsNull("Y");
		//		cgreport_t_item_s_flag.setIsQuery("N");
		//		cgreport_t_item_s_flag.setIsShow("Y");
		//		cgreport_t_item_s_flag.setIsShowList("Y");
		//		cgreport_t_item_s_flag.setShowType("list");
		//		cgreport_t_item_s_flag.setLength(2);
		//		cgreport_t_item_s_flag.setType("string");
		//		cgreport_t_item_s_flag.setOrderNum(8);
		//		cgreport_t_item_s_flag.setPointLength(0);
		//		cgreport_t_item_s_flag.setQueryMode("single");
		//		cgreport_t_item_s_flag.setContent("是否查询");
		//		cgreport_t_item_s_flag.setCreateBy("admin");
		//		cgreport_t_item_s_flag.setCreateDate(new Date());
		//		cgreport_t_item_s_flag.setCreateName("管理员");
		//		cgreport_t_item_s_flag.setDictField("yesorno");
		//		cgreport_t_item_s_flag.setDictTable("");
		//		cgreport_t_item_s_flag.setMainTable("");
		//		cgreport_t_item_s_flag.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_s_flag);
		//
		//		CgFormFieldEntity cgreport_t_item_cgrhead_id = new CgFormFieldEntity();
		//		cgreport_t_item_cgrhead_id.setFieldName("cgrhead_id");
		//		cgreport_t_item_cgrhead_id.setTable(cgreport_t_item);
		//		cgreport_t_item_cgrhead_id.setFieldLength(120);
		//		cgreport_t_item_cgrhead_id.setIsKey("N");
		//		cgreport_t_item_cgrhead_id.setIsNull("Y");
		//		cgreport_t_item_cgrhead_id.setIsQuery("N");
		//		cgreport_t_item_cgrhead_id.setIsShow("N");
		//		cgreport_t_item_cgrhead_id.setIsShowList("N");
		//		cgreport_t_item_cgrhead_id.setShowType("text");
		//		cgreport_t_item_cgrhead_id.setLength(36);
		//		cgreport_t_item_cgrhead_id.setType("string");
		//		cgreport_t_item_cgrhead_id.setOrderNum(9);
		//		cgreport_t_item_cgrhead_id.setPointLength(0);
		//		cgreport_t_item_cgrhead_id.setQueryMode("single");
		//		cgreport_t_item_cgrhead_id.setContent("外键");
		//		cgreport_t_item_cgrhead_id.setCreateBy("admin");
		//		cgreport_t_item_cgrhead_id.setCreateDate(new Date());
		//		cgreport_t_item_cgrhead_id.setCreateName("管理员");
		//		cgreport_t_item_cgrhead_id.setDictField("");
		//		cgreport_t_item_cgrhead_id.setDictTable("");
		//		cgreport_t_item_cgrhead_id.setMainTable("cgreport_t_head");
		//		cgreport_t_item_cgrhead_id.setMainField("id");
		//		commonDao.saveOrUpdate(cgreport_t_item_cgrhead_id);
		//
		//		CgFormFieldEntity cgreport_t_item_field_txt = new CgFormFieldEntity();
		//		cgreport_t_item_field_txt.setFieldName("field_txt");
		//		cgreport_t_item_field_txt.setTable(cgreport_t_item);
		//		cgreport_t_item_field_txt.setFieldLength(120);
		//		cgreport_t_item_field_txt.setIsKey("N");
		//		cgreport_t_item_field_txt.setIsNull("Y");
		//		cgreport_t_item_field_txt.setIsQuery("N");
		//		cgreport_t_item_field_txt.setIsShow("Y");
		//		cgreport_t_item_field_txt.setIsShowList("Y");
		//		cgreport_t_item_field_txt.setShowType("text");
		//		cgreport_t_item_field_txt.setLength(1000);
		//		cgreport_t_item_field_txt.setType("string");
		//		cgreport_t_item_field_txt.setOrderNum(3);
		//		cgreport_t_item_field_txt.setPointLength(0);
		//		cgreport_t_item_field_txt.setQueryMode("single");
		//		cgreport_t_item_field_txt.setContent("字段文本");
		//		cgreport_t_item_field_txt.setCreateBy("admin");
		//		cgreport_t_item_field_txt.setCreateDate(new Date());
		//		cgreport_t_item_field_txt.setCreateName("管理员");
		//		cgreport_t_item_field_txt.setDictField("");
		//		cgreport_t_item_field_txt.setDictTable("");
		//		cgreport_t_item_field_txt.setMainTable("");
		//		cgreport_t_item_field_txt.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_field_txt);
		//
		//		CgFormFieldEntity cgreport_t_item_field_type = new CgFormFieldEntity();
		//		cgreport_t_item_field_type.setFieldName("field_type");
		//		cgreport_t_item_field_type.setTable(cgreport_t_item);
		//		cgreport_t_item_field_type.setFieldLength(120);
		//		cgreport_t_item_field_type.setIsKey("N");
		//		cgreport_t_item_field_type.setIsNull("Y");
		//		cgreport_t_item_field_type.setIsQuery("N");
		//		cgreport_t_item_field_type.setIsShow("Y");
		//		cgreport_t_item_field_type.setIsShowList("Y");
		//		cgreport_t_item_field_type.setShowType("list");
		//		cgreport_t_item_field_type.setLength(10);
		//		cgreport_t_item_field_type.setType("string");
		//		cgreport_t_item_field_type.setOrderNum(4);
		//		cgreport_t_item_field_type.setPointLength(0);
		//		cgreport_t_item_field_type.setQueryMode("single");
		//		cgreport_t_item_field_type.setContent("字段类型");
		//		cgreport_t_item_field_type.setCreateBy("admin");
		//		cgreport_t_item_field_type.setCreateDate(new Date());
		//		cgreport_t_item_field_type.setCreateName("管理员");
		//		cgreport_t_item_field_type.setDictField("fieldtype");
		//		cgreport_t_item_field_type.setDictTable("");
		//		cgreport_t_item_field_type.setMainTable("");
		//		cgreport_t_item_field_type.setMainField("");
		//		commonDao.saveOrUpdate(cgreport_t_item_field_type);

		// 表单[订单客户信息] - 字段清单
		CgFormHeadEntity jform_order_customer = commonDao.findByProperty(CgFormHeadEntity.class, "tableName", "jform_order_customer").get(0);
		CgFormFieldEntity jform_order_customer_id = new CgFormFieldEntity();
		jform_order_customer_id.setFieldName("id");
		jform_order_customer_id.setTable(jform_order_customer);
		jform_order_customer_id.setFieldLength(0);
		jform_order_customer_id.setIsKey("Y");
		jform_order_customer_id.setIsNull("N");
		jform_order_customer_id.setIsQuery("N");
		jform_order_customer_id.setIsShow("N");
		jform_order_customer_id.setIsShowList("N");
		jform_order_customer_id.setShowType("text");
		jform_order_customer_id.setLength(36);
		jform_order_customer_id.setType("string");
		jform_order_customer_id.setOrderNum(0);
		jform_order_customer_id.setPointLength(0);
		jform_order_customer_id.setQueryMode("single");
		jform_order_customer_id.setContent("主键");
		jform_order_customer_id.setCreateBy("admin");
		jform_order_customer_id.setCreateDate(new Date());
		jform_order_customer_id.setCreateName("管理员");
		jform_order_customer_id.setDictField("");
		jform_order_customer_id.setDictTable("");
		jform_order_customer_id.setMainTable("");
		jform_order_customer_id.setMainField("");
		commonDao.saveOrUpdate(jform_order_customer_id);

		CgFormFieldEntity jform_order_customer_name = new CgFormFieldEntity();
		jform_order_customer_name.setFieldName("name");
		jform_order_customer_name.setTable(jform_order_customer);
		jform_order_customer_name.setFieldLength(0);
		jform_order_customer_name.setIsKey("N");
		jform_order_customer_name.setIsNull("Y");
		jform_order_customer_name.setIsQuery("Y");
		jform_order_customer_name.setIsShow("Y");
		jform_order_customer_name.setIsShowList("Y");
		jform_order_customer_name.setShowType("text");
		jform_order_customer_name.setLength(32);
		jform_order_customer_name.setType("string");
		jform_order_customer_name.setOrderNum(1);
		jform_order_customer_name.setPointLength(0);
		jform_order_customer_name.setQueryMode("single");
		jform_order_customer_name.setContent("客户名");
		jform_order_customer_name.setCreateBy("admin");
		jform_order_customer_name.setCreateDate(new Date());
		jform_order_customer_name.setCreateName("管理员");
		jform_order_customer_name.setDictField("");
		jform_order_customer_name.setDictTable("");
		jform_order_customer_name.setMainTable("");
		jform_order_customer_name.setMainField("");
		commonDao.saveOrUpdate(jform_order_customer_name);

		CgFormFieldEntity jform_order_customer_money = new CgFormFieldEntity();
		jform_order_customer_money.setFieldName("money");
		jform_order_customer_money.setTable(jform_order_customer);
		jform_order_customer_money.setFieldLength(0);
		jform_order_customer_money.setIsKey("N");
		jform_order_customer_money.setIsNull("Y");
		jform_order_customer_money.setIsQuery("Y");
		jform_order_customer_money.setIsShow("Y");
		jform_order_customer_money.setIsShowList("Y");
		jform_order_customer_money.setShowType("text");
		jform_order_customer_money.setLength(10);
		jform_order_customer_money.setType("double");
		jform_order_customer_money.setOrderNum(2);
		jform_order_customer_money.setPointLength(2);
		jform_order_customer_money.setQueryMode("group");
		jform_order_customer_money.setContent("单价");
		jform_order_customer_money.setCreateBy("admin");
		jform_order_customer_money.setCreateDate(new Date());
		jform_order_customer_money.setCreateName("管理员");
		jform_order_customer_money.setDictField("");
		jform_order_customer_money.setDictTable("");
		jform_order_customer_money.setMainTable("");
		jform_order_customer_money.setMainField("");
		commonDao.saveOrUpdate(jform_order_customer_money);

		CgFormFieldEntity jform_order_customer_fk_id = new CgFormFieldEntity();
		jform_order_customer_fk_id.setFieldName("fk_id");
		jform_order_customer_fk_id.setTable(jform_order_customer);
		jform_order_customer_fk_id.setFieldLength(120);
		jform_order_customer_fk_id.setIsKey("N");
		jform_order_customer_fk_id.setIsNull("N");
		jform_order_customer_fk_id.setIsQuery("Y");
		jform_order_customer_fk_id.setIsShow("N");
		jform_order_customer_fk_id.setIsShowList("N");
		jform_order_customer_fk_id.setShowType("text");
		jform_order_customer_fk_id.setLength(36);
		jform_order_customer_fk_id.setType("string");
		jform_order_customer_fk_id.setOrderNum(5);
		jform_order_customer_fk_id.setPointLength(0);
		jform_order_customer_fk_id.setQueryMode("single");
		jform_order_customer_fk_id.setContent("外键");
		jform_order_customer_fk_id.setCreateBy("admin");
		jform_order_customer_fk_id.setCreateDate(new Date());
		jform_order_customer_fk_id.setCreateName("管理员");
		jform_order_customer_fk_id.setDictField("");
		jform_order_customer_fk_id.setDictTable("");
		jform_order_customer_fk_id.setMainTable("jform_order_main");
		jform_order_customer_fk_id.setMainField("id");
		commonDao.saveOrUpdate(jform_order_customer_fk_id);

		CgFormFieldEntity jform_order_customer_telphone = new CgFormFieldEntity();
		jform_order_customer_telphone.setFieldName("telphone");
		jform_order_customer_telphone.setTable(jform_order_customer);
		jform_order_customer_telphone.setFieldLength(120);
		jform_order_customer_telphone.setIsKey("N");
		jform_order_customer_telphone.setIsNull("Y");
		jform_order_customer_telphone.setIsQuery("Y");
		jform_order_customer_telphone.setIsShow("Y");
		jform_order_customer_telphone.setIsShowList("Y");
		jform_order_customer_telphone.setShowType("text");
		jform_order_customer_telphone.setLength(32);
		jform_order_customer_telphone.setType("string");
		jform_order_customer_telphone.setOrderNum(4);
		jform_order_customer_telphone.setPointLength(0);
		jform_order_customer_telphone.setQueryMode("single");
		jform_order_customer_telphone.setContent("电话");
		jform_order_customer_telphone.setCreateBy("admin");
		jform_order_customer_telphone.setCreateDate(new Date());
		jform_order_customer_telphone.setCreateName("管理员");
		jform_order_customer_telphone.setDictField("");
		jform_order_customer_telphone.setDictTable("");
		jform_order_customer_telphone.setMainTable("");
		jform_order_customer_telphone.setMainField("");
		commonDao.saveOrUpdate(jform_order_customer_telphone);

		CgFormFieldEntity jform_order_customer_sex = new CgFormFieldEntity();
		jform_order_customer_sex.setFieldName("sex");
		jform_order_customer_sex.setTable(jform_order_customer);
		jform_order_customer_sex.setFieldLength(120);
		jform_order_customer_sex.setIsKey("N");
		jform_order_customer_sex.setIsNull("Y");
		jform_order_customer_sex.setIsQuery("Y");
		jform_order_customer_sex.setIsShow("Y");
		jform_order_customer_sex.setIsShowList("Y");
		jform_order_customer_sex.setShowType("radio");
		jform_order_customer_sex.setLength(4);
		jform_order_customer_sex.setType("string");
		jform_order_customer_sex.setOrderNum(3);
		jform_order_customer_sex.setPointLength(0);
		jform_order_customer_sex.setQueryMode("single");
		jform_order_customer_sex.setContent("性别");
		jform_order_customer_sex.setCreateBy("admin");
		jform_order_customer_sex.setCreateDate(new Date());
		jform_order_customer_sex.setCreateName("管理员");
		jform_order_customer_sex.setDictField("sex");
		jform_order_customer_sex.setDictTable("");
		jform_order_customer_sex.setMainTable("");
		jform_order_customer_sex.setMainField("");
		commonDao.saveOrUpdate(jform_order_customer_sex);

		// 表单[订单机票信息] - 字段清单
		CgFormHeadEntity jform_order_ticket = commonDao.findByProperty(CgFormHeadEntity.class, "tableName", "jform_order_ticket").get(0);
		CgFormFieldEntity jform_order_ticket_id = new CgFormFieldEntity();
		jform_order_ticket_id.setFieldName("id");
		jform_order_ticket_id.setTable(jform_order_ticket);
		jform_order_ticket_id.setFieldLength(120);
		jform_order_ticket_id.setIsKey("Y");
		jform_order_ticket_id.setIsNull("N");
		jform_order_ticket_id.setIsQuery("N");
		jform_order_ticket_id.setIsShow("N");
		jform_order_ticket_id.setIsShowList("N");
		jform_order_ticket_id.setShowType("checkbox");
		jform_order_ticket_id.setLength(36);
		jform_order_ticket_id.setType("string");
		jform_order_ticket_id.setOrderNum(0);
		jform_order_ticket_id.setPointLength(0);
		jform_order_ticket_id.setQueryMode("single");
		jform_order_ticket_id.setContent("主键");
		jform_order_ticket_id.setCreateBy("admin");
		jform_order_ticket_id.setCreateDate(new Date());
		jform_order_ticket_id.setCreateName("管理员");
		jform_order_ticket_id.setDictField("");
		jform_order_ticket_id.setDictTable("");
		jform_order_ticket_id.setMainTable("");
		jform_order_ticket_id.setMainField("");
		commonDao.saveOrUpdate(jform_order_ticket_id);

		CgFormFieldEntity jform_order_ticket_ticket_code = new CgFormFieldEntity();
		jform_order_ticket_ticket_code.setFieldName("ticket_code");
		jform_order_ticket_ticket_code.setTable(jform_order_ticket);
		jform_order_ticket_ticket_code.setFieldLength(120);
		jform_order_ticket_ticket_code.setIsKey("N");
		jform_order_ticket_ticket_code.setIsNull("N");
		jform_order_ticket_ticket_code.setIsQuery("Y");
		jform_order_ticket_ticket_code.setIsShow("Y");
		jform_order_ticket_ticket_code.setIsShowList("Y");
		jform_order_ticket_ticket_code.setShowType("text");
		jform_order_ticket_ticket_code.setLength(100);
		jform_order_ticket_ticket_code.setType("string");
		jform_order_ticket_ticket_code.setOrderNum(1);
		jform_order_ticket_ticket_code.setPointLength(0);
		jform_order_ticket_ticket_code.setQueryMode("single");
		jform_order_ticket_ticket_code.setContent("航班号");
		jform_order_ticket_ticket_code.setCreateBy("admin");
		jform_order_ticket_ticket_code.setCreateDate(new Date());
		jform_order_ticket_ticket_code.setCreateName("管理员");
		jform_order_ticket_ticket_code.setDictField("");
		jform_order_ticket_ticket_code.setDictTable("");
		jform_order_ticket_ticket_code.setMainTable("");
		jform_order_ticket_ticket_code.setMainField("");
		commonDao.saveOrUpdate(jform_order_ticket_ticket_code);

		CgFormFieldEntity jform_order_ticket_tickect_date = new CgFormFieldEntity();
		jform_order_ticket_tickect_date.setFieldName("tickect_date");
		jform_order_ticket_tickect_date.setTable(jform_order_ticket);
		jform_order_ticket_tickect_date.setFieldLength(120);
		jform_order_ticket_tickect_date.setIsKey("N");
		jform_order_ticket_tickect_date.setIsNull("N");
		jform_order_ticket_tickect_date.setIsQuery("Y");
		jform_order_ticket_tickect_date.setIsShow("Y");
		jform_order_ticket_tickect_date.setIsShowList("Y");
		jform_order_ticket_tickect_date.setShowType("datetime");
		jform_order_ticket_tickect_date.setLength(10);
		jform_order_ticket_tickect_date.setType("Date");
		jform_order_ticket_tickect_date.setOrderNum(2);
		jform_order_ticket_tickect_date.setPointLength(0);
		jform_order_ticket_tickect_date.setQueryMode("single");
		jform_order_ticket_tickect_date.setContent("航班时间");
		jform_order_ticket_tickect_date.setCreateBy("admin");
		jform_order_ticket_tickect_date.setCreateDate(new Date());
		jform_order_ticket_tickect_date.setCreateName("管理员");
		jform_order_ticket_tickect_date.setDictField("");
		jform_order_ticket_tickect_date.setDictTable("");
		jform_order_ticket_tickect_date.setMainTable("");
		jform_order_ticket_tickect_date.setMainField("");
		commonDao.saveOrUpdate(jform_order_ticket_tickect_date);

		CgFormFieldEntity jform_order_ticket_fck_id = new CgFormFieldEntity();
		jform_order_ticket_fck_id.setFieldName("fck_id");
		jform_order_ticket_fck_id.setTable(jform_order_ticket);
		jform_order_ticket_fck_id.setFieldLength(120);
		jform_order_ticket_fck_id.setIsKey("N");
		jform_order_ticket_fck_id.setIsNull("N");
		jform_order_ticket_fck_id.setIsQuery("N");
		jform_order_ticket_fck_id.setIsShow("N");
		jform_order_ticket_fck_id.setIsShowList("N");
		jform_order_ticket_fck_id.setShowType("text");
		jform_order_ticket_fck_id.setLength(36);
		jform_order_ticket_fck_id.setType("string");
		jform_order_ticket_fck_id.setOrderNum(3);
		jform_order_ticket_fck_id.setPointLength(0);
		jform_order_ticket_fck_id.setQueryMode("single");
		jform_order_ticket_fck_id.setContent("外键");
		jform_order_ticket_fck_id.setCreateBy("admin");
		jform_order_ticket_fck_id.setCreateDate(new Date());
		jform_order_ticket_fck_id.setCreateName("管理员");
		jform_order_ticket_fck_id.setDictField("");
		jform_order_ticket_fck_id.setDictTable("");
		jform_order_ticket_fck_id.setMainTable("jform_order_main");
		jform_order_ticket_fck_id.setMainField("id");
		commonDao.saveOrUpdate(jform_order_ticket_fck_id);

		// 表单[价格认证机构统计表] - 字段清单
		CgFormHeadEntity jform_price1 = commonDao.findByProperty(CgFormHeadEntity.class, "tableName", "jform_price1").get(0);
		CgFormFieldEntity jform_price1_id = new CgFormFieldEntity();
		jform_price1_id.setFieldName("id");
		jform_price1_id.setTable(jform_price1);
		jform_price1_id.setFieldLength(0);
		jform_price1_id.setIsKey("Y");
		jform_price1_id.setIsNull("N");
		jform_price1_id.setIsQuery("N");
		jform_price1_id.setIsShow("N");
		jform_price1_id.setIsShowList("N");
		jform_price1_id.setShowType("text");
		jform_price1_id.setLength(36);
		jform_price1_id.setType("string");
		jform_price1_id.setOrderNum(0);
		jform_price1_id.setPointLength(0);
		jform_price1_id.setQueryMode("single");
		jform_price1_id.setContent("主键");
		jform_price1_id.setCreateBy("admin");
		jform_price1_id.setCreateDate(new Date());
		jform_price1_id.setCreateName("管理员");
		jform_price1_id.setDictField("");
		jform_price1_id.setDictTable("");
		jform_price1_id.setMainTable("");
		jform_price1_id.setMainField("");
		commonDao.saveOrUpdate(jform_price1_id);

		CgFormFieldEntity jform_price1_a = new CgFormFieldEntity();
		jform_price1_a.setFieldName("a");
		jform_price1_a.setTable(jform_price1);
		jform_price1_a.setFieldLength(0);
		jform_price1_a.setIsKey("N");
		jform_price1_a.setIsNull("N");
		jform_price1_a.setIsQuery("Y");
		jform_price1_a.setIsShow("Y");
		jform_price1_a.setIsShowList("Y");
		jform_price1_a.setShowType("text");
		jform_price1_a.setLength(10);
		jform_price1_a.setType("double");
		jform_price1_a.setOrderNum(1);
		jform_price1_a.setPointLength(2);
		jform_price1_a.setQueryMode("group");
		jform_price1_a.setContent("机构合计");
		jform_price1_a.setCreateBy("admin");
		jform_price1_a.setCreateDate(new Date());
		jform_price1_a.setCreateName("管理员");
		jform_price1_a.setDictField("");
		jform_price1_a.setDictTable("");
		jform_price1_a.setMainTable("");
		jform_price1_a.setMainField("");
		commonDao.saveOrUpdate(jform_price1_a);

		CgFormFieldEntity jform_price1_b1 = new CgFormFieldEntity();
		jform_price1_b1.setFieldName("b1");
		jform_price1_b1.setTable(jform_price1);
		jform_price1_b1.setFieldLength(0);
		jform_price1_b1.setIsKey("N");
		jform_price1_b1.setIsNull("N");
		jform_price1_b1.setIsQuery("N");
		jform_price1_b1.setIsShow("Y");
		jform_price1_b1.setIsShowList("Y");
		jform_price1_b1.setShowType("text");
		jform_price1_b1.setLength(10);
		jform_price1_b1.setType("double");
		jform_price1_b1.setOrderNum(2);
		jform_price1_b1.setPointLength(2);
		jform_price1_b1.setQueryMode("group");
		jform_price1_b1.setContent("行政小计");
		jform_price1_b1.setCreateBy("admin");
		jform_price1_b1.setCreateDate(new Date());
		jform_price1_b1.setCreateName("管理员");
		jform_price1_b1.setDictField("");
		jform_price1_b1.setDictTable("");
		jform_price1_b1.setMainTable("");
		jform_price1_b1.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b1);

		CgFormFieldEntity jform_price1_b11 = new CgFormFieldEntity();
		jform_price1_b11.setFieldName("b11");
		jform_price1_b11.setTable(jform_price1);
		jform_price1_b11.setFieldLength(0);
		jform_price1_b11.setIsKey("N");
		jform_price1_b11.setIsNull("N");
		jform_price1_b11.setIsQuery("N");
		jform_price1_b11.setIsShow("Y");
		jform_price1_b11.setIsShowList("Y");
		jform_price1_b11.setShowType("text");
		jform_price1_b11.setLength(100);
		jform_price1_b11.setType("string");
		jform_price1_b11.setOrderNum(3);
		jform_price1_b11.setPointLength(0);
		jform_price1_b11.setQueryMode("group");
		jform_price1_b11.setContent("行政省");
		jform_price1_b11.setCreateBy("admin");
		jform_price1_b11.setCreateDate(new Date());
		jform_price1_b11.setCreateName("管理员");
		jform_price1_b11.setDictField("");
		jform_price1_b11.setDictTable("");
		jform_price1_b11.setMainTable("");
		jform_price1_b11.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b11);

		CgFormFieldEntity jform_price1_b12 = new CgFormFieldEntity();
		jform_price1_b12.setFieldName("b12");
		jform_price1_b12.setTable(jform_price1);
		jform_price1_b12.setFieldLength(0);
		jform_price1_b12.setIsKey("N");
		jform_price1_b12.setIsNull("N");
		jform_price1_b12.setIsQuery("N");
		jform_price1_b12.setIsShow("Y");
		jform_price1_b12.setIsShowList("Y");
		jform_price1_b12.setShowType("text");
		jform_price1_b12.setLength(100);
		jform_price1_b12.setType("string");
		jform_price1_b12.setOrderNum(4);
		jform_price1_b12.setPointLength(0);
		jform_price1_b12.setQueryMode("group");
		jform_price1_b12.setContent("行政市");
		jform_price1_b12.setCreateBy("admin");
		jform_price1_b12.setCreateDate(new Date());
		jform_price1_b12.setCreateName("管理员");
		jform_price1_b12.setDictField("");
		jform_price1_b12.setDictTable("");
		jform_price1_b12.setMainTable("");
		jform_price1_b12.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b12);

		CgFormFieldEntity jform_price1_b13 = new CgFormFieldEntity();
		jform_price1_b13.setFieldName("b13");
		jform_price1_b13.setTable(jform_price1);
		jform_price1_b13.setFieldLength(0);
		jform_price1_b13.setIsKey("N");
		jform_price1_b13.setIsNull("N");
		jform_price1_b13.setIsQuery("N");
		jform_price1_b13.setIsShow("Y");
		jform_price1_b13.setIsShowList("Y");
		jform_price1_b13.setShowType("text");
		jform_price1_b13.setLength(100);
		jform_price1_b13.setType("string");
		jform_price1_b13.setOrderNum(5);
		jform_price1_b13.setPointLength(0);
		jform_price1_b13.setQueryMode("single");
		jform_price1_b13.setContent("行政县");
		jform_price1_b13.setCreateBy("admin");
		jform_price1_b13.setCreateDate(new Date());
		jform_price1_b13.setCreateName("管理员");
		jform_price1_b13.setDictField("");
		jform_price1_b13.setDictTable("");
		jform_price1_b13.setMainTable("");
		jform_price1_b13.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b13);

		CgFormFieldEntity jform_price1_b2 = new CgFormFieldEntity();
		jform_price1_b2.setFieldName("b2");
		jform_price1_b2.setTable(jform_price1);
		jform_price1_b2.setFieldLength(0);
		jform_price1_b2.setIsKey("N");
		jform_price1_b2.setIsNull("N");
		jform_price1_b2.setIsQuery("N");
		jform_price1_b2.setIsShow("Y");
		jform_price1_b2.setIsShowList("Y");
		jform_price1_b2.setShowType("text");
		jform_price1_b2.setLength(10);
		jform_price1_b2.setType("double");
		jform_price1_b2.setOrderNum(6);
		jform_price1_b2.setPointLength(2);
		jform_price1_b2.setQueryMode("single");
		jform_price1_b2.setContent("事业合计");
		jform_price1_b2.setCreateBy("admin");
		jform_price1_b2.setCreateDate(new Date());
		jform_price1_b2.setCreateName("管理员");
		jform_price1_b2.setDictField("");
		jform_price1_b2.setDictTable("");
		jform_price1_b2.setMainTable("");
		jform_price1_b2.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b2);

		CgFormFieldEntity jform_price1_b3 = new CgFormFieldEntity();
		jform_price1_b3.setFieldName("b3");
		jform_price1_b3.setTable(jform_price1);
		jform_price1_b3.setFieldLength(0);
		jform_price1_b3.setIsKey("N");
		jform_price1_b3.setIsNull("N");
		jform_price1_b3.setIsQuery("N");
		jform_price1_b3.setIsShow("Y");
		jform_price1_b3.setIsShowList("Y");
		jform_price1_b3.setShowType("text");
		jform_price1_b3.setLength(10);
		jform_price1_b3.setType("double");
		jform_price1_b3.setOrderNum(7);
		jform_price1_b3.setPointLength(2);
		jform_price1_b3.setQueryMode("single");
		jform_price1_b3.setContent("参公小计");
		jform_price1_b3.setCreateBy("admin");
		jform_price1_b3.setCreateDate(new Date());
		jform_price1_b3.setCreateName("管理员");
		jform_price1_b3.setDictField("");
		jform_price1_b3.setDictTable("");
		jform_price1_b3.setMainTable("");
		jform_price1_b3.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b3);

		CgFormFieldEntity jform_price1_b31 = new CgFormFieldEntity();
		jform_price1_b31.setFieldName("b31");
		jform_price1_b31.setTable(jform_price1);
		jform_price1_b31.setFieldLength(0);
		jform_price1_b31.setIsKey("N");
		jform_price1_b31.setIsNull("N");
		jform_price1_b31.setIsQuery("N");
		jform_price1_b31.setIsShow("Y");
		jform_price1_b31.setIsShowList("Y");
		jform_price1_b31.setShowType("text");
		jform_price1_b31.setLength(100);
		jform_price1_b31.setType("string");
		jform_price1_b31.setOrderNum(8);
		jform_price1_b31.setPointLength(0);
		jform_price1_b31.setQueryMode("single");
		jform_price1_b31.setContent("参公省");
		jform_price1_b31.setCreateBy("admin");
		jform_price1_b31.setCreateDate(new Date());
		jform_price1_b31.setCreateName("管理员");
		jform_price1_b31.setDictField("");
		jform_price1_b31.setDictTable("");
		jform_price1_b31.setMainTable("");
		jform_price1_b31.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b31);

		CgFormFieldEntity jform_price1_b32 = new CgFormFieldEntity();
		jform_price1_b32.setFieldName("b32");
		jform_price1_b32.setTable(jform_price1);
		jform_price1_b32.setFieldLength(0);
		jform_price1_b32.setIsKey("N");
		jform_price1_b32.setIsNull("N");
		jform_price1_b32.setIsQuery("N");
		jform_price1_b32.setIsShow("Y");
		jform_price1_b32.setIsShowList("Y");
		jform_price1_b32.setShowType("text");
		jform_price1_b32.setLength(100);
		jform_price1_b32.setType("string");
		jform_price1_b32.setOrderNum(9);
		jform_price1_b32.setPointLength(0);
		jform_price1_b32.setQueryMode("single");
		jform_price1_b32.setContent("参公市");
		jform_price1_b32.setCreateBy("admin");
		jform_price1_b32.setCreateDate(new Date());
		jform_price1_b32.setCreateName("管理员");
		jform_price1_b32.setDictField("");
		jform_price1_b32.setDictTable("");
		jform_price1_b32.setMainTable("");
		jform_price1_b32.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b32);

		CgFormFieldEntity jform_price1_b33 = new CgFormFieldEntity();
		jform_price1_b33.setFieldName("b33");
		jform_price1_b33.setTable(jform_price1);
		jform_price1_b33.setFieldLength(0);
		jform_price1_b33.setIsKey("N");
		jform_price1_b33.setIsNull("N");
		jform_price1_b33.setIsQuery("N");
		jform_price1_b33.setIsShow("Y");
		jform_price1_b33.setIsShowList("Y");
		jform_price1_b33.setShowType("text");
		jform_price1_b33.setLength(100);
		jform_price1_b33.setType("string");
		jform_price1_b33.setOrderNum(10);
		jform_price1_b33.setPointLength(0);
		jform_price1_b33.setQueryMode("single");
		jform_price1_b33.setContent("参公县");
		jform_price1_b33.setCreateBy("admin");
		jform_price1_b33.setCreateDate(new Date());
		jform_price1_b33.setCreateName("管理员");
		jform_price1_b33.setDictField("");
		jform_price1_b33.setDictTable("");
		jform_price1_b33.setMainTable("");
		jform_price1_b33.setMainField("");
		commonDao.saveOrUpdate(jform_price1_b33);

		CgFormFieldEntity jform_price1_c1 = new CgFormFieldEntity();
		jform_price1_c1.setFieldName("c1");
		jform_price1_c1.setTable(jform_price1);
		jform_price1_c1.setFieldLength(0);
		jform_price1_c1.setIsKey("N");
		jform_price1_c1.setIsNull("N");
		jform_price1_c1.setIsQuery("N");
		jform_price1_c1.setIsShow("Y");
		jform_price1_c1.setIsShowList("Y");
		jform_price1_c1.setShowType("text");
		jform_price1_c1.setLength(10);
		jform_price1_c1.setType("double");
		jform_price1_c1.setOrderNum(11);
		jform_price1_c1.setPointLength(2);
		jform_price1_c1.setQueryMode("single");
		jform_price1_c1.setContent("全额拨款");
		jform_price1_c1.setCreateBy("admin");
		jform_price1_c1.setCreateDate(new Date());
		jform_price1_c1.setCreateName("管理员");
		jform_price1_c1.setDictField("");
		jform_price1_c1.setDictTable("");
		jform_price1_c1.setMainTable("");
		jform_price1_c1.setMainField("");
		commonDao.saveOrUpdate(jform_price1_c1);

		CgFormFieldEntity jform_price1_c2 = new CgFormFieldEntity();
		jform_price1_c2.setFieldName("c2");
		jform_price1_c2.setTable(jform_price1);
		jform_price1_c2.setFieldLength(0);
		jform_price1_c2.setIsKey("N");
		jform_price1_c2.setIsNull("N");
		jform_price1_c2.setIsQuery("N");
		jform_price1_c2.setIsShow("Y");
		jform_price1_c2.setIsShowList("Y");
		jform_price1_c2.setShowType("text");
		jform_price1_c2.setLength(10);
		jform_price1_c2.setType("double");
		jform_price1_c2.setOrderNum(12);
		jform_price1_c2.setPointLength(2);
		jform_price1_c2.setQueryMode("single");
		jform_price1_c2.setContent("差额拨款");
		jform_price1_c2.setCreateBy("admin");
		jform_price1_c2.setCreateDate(new Date());
		jform_price1_c2.setCreateName("管理员");
		jform_price1_c2.setDictField("");
		jform_price1_c2.setDictTable("");
		jform_price1_c2.setMainTable("");
		jform_price1_c2.setMainField("");
		commonDao.saveOrUpdate(jform_price1_c2);

		CgFormFieldEntity jform_price1_c3 = new CgFormFieldEntity();
		jform_price1_c3.setFieldName("c3");
		jform_price1_c3.setTable(jform_price1);
		jform_price1_c3.setFieldLength(0);
		jform_price1_c3.setIsKey("N");
		jform_price1_c3.setIsNull("N");
		jform_price1_c3.setIsQuery("N");
		jform_price1_c3.setIsShow("Y");
		jform_price1_c3.setIsShowList("Y");
		jform_price1_c3.setShowType("text");
		jform_price1_c3.setLength(10);
		jform_price1_c3.setType("double");
		jform_price1_c3.setOrderNum(13);
		jform_price1_c3.setPointLength(2);
		jform_price1_c3.setQueryMode("single");
		jform_price1_c3.setContent("自收自支");
		jform_price1_c3.setCreateBy("admin");
		jform_price1_c3.setCreateDate(new Date());
		jform_price1_c3.setCreateName("管理员");
		jform_price1_c3.setDictField("");
		jform_price1_c3.setDictTable("");
		jform_price1_c3.setMainTable("");
		jform_price1_c3.setMainField("");
		commonDao.saveOrUpdate(jform_price1_c3);

		CgFormFieldEntity jform_price1_d = new CgFormFieldEntity();
		jform_price1_d.setFieldName("d");
		jform_price1_d.setTable(jform_price1);
		jform_price1_d.setFieldLength(0);
		jform_price1_d.setIsKey("N");
		jform_price1_d.setIsNull("N");
		jform_price1_d.setIsQuery("Y");
		jform_price1_d.setIsShow("Y");
		jform_price1_d.setIsShowList("Y");
		jform_price1_d.setShowType("text");
		jform_price1_d.setLength(10);
		jform_price1_d.setType("int");
		jform_price1_d.setOrderNum(14);
		jform_price1_d.setPointLength(2);
		jform_price1_d.setQueryMode("single");
		jform_price1_d.setContent("经费合计");
		jform_price1_d.setCreateBy("admin");
		jform_price1_d.setCreateDate(new Date());
		jform_price1_d.setCreateName("管理员");
		jform_price1_d.setDictField("");
		jform_price1_d.setDictTable("");
		jform_price1_d.setMainTable("");
		jform_price1_d.setMainField("");
		commonDao.saveOrUpdate(jform_price1_d);

		CgFormFieldEntity jform_price1_d1 = new CgFormFieldEntity();
		jform_price1_d1.setFieldName("d1");
		jform_price1_d1.setTable(jform_price1);
		jform_price1_d1.setFieldLength(0);
		jform_price1_d1.setIsKey("N");
		jform_price1_d1.setIsNull("N");
		jform_price1_d1.setIsQuery("N");
		jform_price1_d1.setIsShow("Y");
		jform_price1_d1.setIsShowList("Y");
		jform_price1_d1.setShowType("text");
		jform_price1_d1.setLength(1000);
		jform_price1_d1.setType("string");
		jform_price1_d1.setOrderNum(15);
		jform_price1_d1.setPointLength(0);
		jform_price1_d1.setQueryMode("single");
		jform_price1_d1.setContent("机构资质");
		jform_price1_d1.setCreateBy("admin");
		jform_price1_d1.setCreateDate(new Date());
		jform_price1_d1.setCreateName("管理员");
		jform_price1_d1.setDictField("");
		jform_price1_d1.setDictTable("");
		jform_price1_d1.setMainTable("");
		jform_price1_d1.setMainField("");
		commonDao.saveOrUpdate(jform_price1_d1);

	}

	/**
	 * @Description 修复HTML在线编辑功能
	 * @author tanghan 2013-7-28
	 */
	private void repairCkEditor() {
		CKEditorEntity ckEditor = new CKEditorEntity();
		String str = "<html><head><title></title><link href='plug-in/easyui/themes/default/easyui.css' id='easyuiTheme' rel='stylesheet' type='text/css' />"
				+ "<link href='plug-in/easyui/themes/icon.css' rel='stylesheet' type='text/css' />" + "<link href='plug-in/accordion/css/accordion.css' rel='stylesheet' type='text/css' />"
				+ "<link href='plug-in/Validform/css/style.css' rel='stylesheet' type='text/css' />" + "<link href='plug-in/Validform/css/tablefrom.css' rel='stylesheet' type='text/css' />"
				+ "<style type='text/css'>body{font-size:12px;}table {border:1px solid #000000;border-collapse: collapse;}"
				+ "td {border:1px solid #000000;background:white;font-size:12px;font-family: 新宋体;color: #333;</style></head>" + "<body><div><p>附件2：</p><h1 style='text-align:center'>"
				+ "<span style='font-size:24px'><strong>价格认证人员统计表</strong></span>" + "</h1><p>填报单位（盖章）：<input name='org_name' type='text' value='${org_name?if_exists?html}' /></p>"
				+ "<p>单位代码号：<input name='num' type='text' value='${num?if_exists?html}' /> "
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "&nbsp;&nbsp;单位：人填&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "&nbsp;&nbsp;&nbsp; 报日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 年&nbsp;&nbsp; 月&nbsp;&nbsp; 日</p><"
				+ "form action='cgFormBuildController.do?saveOrUpdate' id='formobj' method='post' name='formobj'>"
				+ "<input name='tableName' type='hidden' value='${tableName?if_exists?html}' /> "
				+ "<input name='id' type='hidden' value='${id?if_exists?html}' />#{jform_hidden_field}<input type='hidden' />"
				+ "<p>&nbsp;</p><table border='1' cellpadding='0' cellspacing='0' style='width:1016px'>" + "<tbody><tr><td rowspan='4'><p>&nbsp;</p><p>&nbsp;</p><p>合计</p><p>&nbsp;</p></td>"
				+ "<td colspan='6' rowspan='2'><p>&nbsp;</p><p>人数</p></td><td colspan='5' rowspan='2'>"
				+ "<p>&nbsp;</p><p>学历</p></td><td colspan='4' rowspan='2'><p>&nbsp;</p><p>取得的（上岗）执业资格</p>"
				+ "</td><td colspan='4'><p>专业技术职称</p></td></tr><tr><td colspan='4'><p>（经济系列、工程系列）</p>"
				+ "</td></tr><tr><td colspan='3'><p>在编人员</p></td><td colspan='2'><p>聘用人员</p></td><td rowspan='2'>"
				+ "<p>临时(借用)人员</p></td><td rowspan='2'><p>高中</p></td><td rowspan='2'><p>大专</p></td><td rowspan='2'>"
				+ "<p>本科</p></td><td rowspan='2'><p>研究生</p></td><td rowspan='2'><p>其它</p></td><td rowspan='2'><p>价格</p>"
				+ "<p>鉴证员</p></td><td rowspan='2'><p>价格</p><p>鉴证师</p></td><td rowspan='2'><p>复核</p><p>裁定员</p></td>"
				+ "<td rowspan='2'><p>其它</p></td><td rowspan='2'><p>初级</p></td><td rowspan='2'><p>中级</p></td><td rowspan='2'>"
				+ "<p>高级</p></td><td rowspan='2'><p>其它</p></td></tr><tr><td><p>本单位</p></td><td colspan='2'><p>其它</p></td>"
				+ "<td><p>长期</p></td><td><p>短期</p></td></tr><tr><td><p>A1</p></td><td><p>B1</p></td><td><p>B2</p></td>"
				+ "<td colspan='2'><p>B3</p></td><td><p>B4</p></td><td><p>B5</p></td><td><p>C1</p></td><td><p>C2</p></td>"
				+ "<td><p>C3</p></td><td><p>C4</p></td><td><p>C5</p></td><td><p>D1</p></td><td><p>D2</p></td><td><p>D3</p>"
				+ "</td><td><p>D4</p></td><td><p>E1</p></td><td><p>E2</p></td><td><p>E3</p></td><td><p>E4</p></td></tr>"
				+ "<tr><td><p><input name='a1' size='4' type='text' value='${a1?if_exists?html}' /></p></td><td>"
				+ "<p><input name='b1' size='4' type='text' value='${b1?if_exists?html}' /></p></td><td><p>"
				+ "<input name='b2' size='4' type='text' value='${b2?if_exists?html}' /></p></td><td colspan='2'><p>"
				+ "<input name='b3' size='4' type='text' value='${b3?if_exists?html}' /></p></td><td><p>"
				+ "<input name='b4' size='4' type='text' value='${b4?if_exists?html}' /></p></td><td><p>"
				+ "<input name='b5' size='4' type='text' value='${b5?if_exists?html}' /></p></td><td><p>"
				+ "<input name='c1' size='4' type='text' value='${c1?if_exists?html}' /></p></td><td><p>"
				+ "<input name='c2' size='4' type='text' value='${c2?if_exists?html}' /></p></td><td><p>"
				+ "<input name='c3' size='4' type='text' value='${c3?if_exists?html}' /></p></td><td><p>"
				+ "<input name='c4' size='4' type='text' value='${c4?if_exists?html}' /></p></td><td><p>"
				+ "<input name='c5' size='4' type='text' value='${c5?if_exists?html}' /></p></td><td><p>"
				+ "<input name='d1' size='4' type='text' value='${d1?if_exists?html}' /></p></td><td><p>"
				+ "<input name='d2' size='4' type='text' value='${d2?if_exists?html}' /></p></td><td><p>"
				+ "<input name='d3' size='4' type='text' value='${d3?if_exists?html}' /></p></td><td><p>"
				+ "<input name='d4' size='4' type='text' value='${d4?if_exists?html}' /></p></td><td><p>"
				+ "<input name='e1' size='4' type='text' value='${e1?if_exists?html}' /></p></td><td><p>"
				+ "<input name='e2' size='4' type='text' value='${e2?if_exists?html}' /></p></td><td><p>"
				+ "<input name='e3' size='4' type='text' value='${e3?if_exists?html}' /></p></td><td><p>"
				+ "<input name='e4' size='4' type='text' value='${e4?if_exists?html}' /></p></td></tr>" + "<tr><td colspan='20'><p>&nbsp;</p><p>填报说明：</p><p>一、合计（A）：填报至统计截止期的本机构的人员总数。</p>"
				+ "<p>二、人数：</p><p>在编人员：分别按照价格认证机构编制内及其它具有价格主管部门编制的实有人数填报在B1、B2栏内。" + "</p><p>聘用人员：按照经价格主管部门或价格认证机构人事部门认可的并签订三年以上的工作合同的人员（B3）；以及没有经过价"
				+ "格主管部门或价格认证机构人事部门认可的签订合同少于三年的人员（B4）分别来进行统计。</p><p>临时（借用）人员（B5）：特指" + "外聘的临时工，或者工作关系不在本单位且无长期聘用合同的借调人员等。</p><p>三、表内各栏目关系</p><p>A=B1+B2+B3+B4+B5=C1+"
				+ "C2+C3+C4+C5=D1+D2+D3+D4=E1+E2+E3+E4</p></td></tr></tbody></table></form></div></body></html>";
		ckEditor.setContents(str.getBytes());
		commonDao.saveOrUpdate(ckEditor);
	}

	/**
	 * @Description 修复日志表
	 * @author tanghan 2013-7-28
	 */
	private void repairLog() {
		//do nothing
	}

	/**
	 * @Description 修复Demo表，（界面表单验证功能）
	 * @author tanghan 2013-7-23
	 */
	private void repairDemo() {
		Demo demo = new Demo();
		// -------------------------------------------------
		// 复杂字符串文本读取，采用文件方式存储
		String html = new FreemarkerHelper().parseTemplate("/com/sbplatform/web/system/txt/valid-code-demo.ftl", null);
		// -------------------------------------------------
		demo.setDemoCode(html);
		demo.setDemoTitle("表单验证");
		commonDao.saveOrUpdate(demo);

	}

	/**
	 * @Description 修复User表
	 * @author tanghan 2013-7-23
	 */
	private void repairUser() {
		Department eiu = commonDao.findByProperty(Department.class, "name", "信息部").get(0);
		Department RAndD = commonDao.findByProperty(Department.class, "name", "研发室").get(0);

		User admin = new User();
		admin.setSignatureFile("images/renfang/qm/licf.gif");
		admin.setStatus((short) 1);
		admin.setRealName("管理员");
		admin.setUserName("admin");
		admin.setPassword("c44b01947c9e6e3f");
		admin.setDepartment(eiu);
		admin.setActivitiSync((short) 1);
		commonDao.saveOrUpdate(admin);

		User scott = new User();
		scott.setMobilePhone("15605948888");
		scott.setOfficePhone("7496661");
		scott.setEmail("semmy2009@163.com");
		scott.setStatus((short) 1);
		scott.setRealName("黄世民");
		scott.setUserName("password");
		scott.setPassword("97c07a884bf272b5");
		scott.setDepartment(RAndD);
		scott.setActivitiSync((short) 0);
		commonDao.saveOrUpdate(scott);

		User buyer = new User();
		buyer.setStatus((short) 1);
		buyer.setRealName("采购员");
		buyer.setUserName("hsm");
		buyer.setPassword("f2322ec2fb9f40d1");
		buyer.setDepartment(eiu);
		buyer.setActivitiSync((short) 0);
		commonDao.saveOrUpdate(buyer);

		User approver = new User();
		approver.setStatus((short) 1);
		approver.setRealName("采购审批员");
		approver.setUserName("semmy");
		approver.setPassword("a324509dc1a3089a");
		approver.setDepartment(eiu);
		approver.setActivitiSync((short) 1);
		commonDao.saveOrUpdate(approver);

	}

	/**
	 * @Description 修复用户角色表
	 * @author tanghan 2013-7-23
	 */
	private void repairUserRole() {
		Role admin = commonDao.findByProperty(Role.class, "roleCode", "admin").get(0);
		Role manager = commonDao.findByProperty(Role.class, "roleCode", "manager").get(0);
		List<User> user = commonDao.loadAll(User.class);
		for (int i = 0; i < user.size(); i++) {
			if (user.get(i).getEmail() != null) {
				RoleUser roleuser = new RoleUser();
				roleuser.setUser(user.get(i));
				roleuser.setRole(manager);
				commonDao.saveOrUpdate(roleuser);
			} else {
				RoleUser roleuser = new RoleUser();
				roleuser.setUser(user.get(i));
				roleuser.setRole(admin);
				commonDao.saveOrUpdate(roleuser);
			}
			if (user.get(i).getSignatureFile() != null) {
				RoleUser roleuser = new RoleUser();
				roleuser.setUser(user.get(i));
				roleuser.setRole(admin);
				commonDao.saveOrUpdate(roleuser);
			}
		}

	}

	/**
	 * @Description 修复角色权限表
	 * @author tanghan 2013-7-23
	 */
	private void repairRoleFunction() {
		Role admin = commonDao.findByProperty(Role.class, "roleCode", "admin").get(0);
		Role manager = commonDao.findByProperty(Role.class, "roleCode", "manager").get(0);
		List<MenuFunction> list = commonDao.loadAll(MenuFunction.class);
		for (int i = 0; i < list.size(); i++) {
			RoleMenuFunction adminroleFunction = new RoleMenuFunction();
			RoleMenuFunction managerFunction = new RoleMenuFunction();
			adminroleFunction.setMenuFunction(list.get(i));
			managerFunction.setMenuFunction(list.get(i));
			adminroleFunction.setRole(admin);
			managerFunction.setRole(manager);
			if (list.get(i).getName().equals("Demo示例")) {
				adminroleFunction.setOperation("add,szqm,");
			}
			commonDao.saveOrUpdate(adminroleFunction);
			commonDao.saveOrUpdate(managerFunction);
		}
	}

	/**
	 * @Description 修复操作按钮表
	 * @author tanghan 2013-7-23
	 */
	private void repairOperation() {
		Icon back = commonDao.findByProperty(Icon.class, "iconName", "返回").get(0);
		MenuFunction function = commonDao.findByProperty(MenuFunction.class, "name", "Demo示例").get(0);

		Operation add = new Operation();
		add.setName("录入");
		add.setCode("add");
		add.setIcon(back);
		add.setMenuFunction(function);
		commonDao.saveOrUpdate(add);

		Operation edit = new Operation();
		edit.setName("编辑");
		edit.setCode("edit");
		edit.setIcon(back);
		edit.setMenuFunction(function);
		commonDao.saveOrUpdate(edit);

		Operation del = new Operation();
		del.setName("删除");
		del.setCode("del");
		del.setIcon(back);
		del.setMenuFunction(function);
		commonDao.saveOrUpdate(del);

		Operation szqm = new Operation();
		szqm.setName("审核");
		szqm.setCode("szqm");
		szqm.setIcon(back);
		szqm.setMenuFunction(function);
		commonDao.saveOrUpdate(szqm);
	}

	/**
	 * @Description 修复类型分组表
	 * @author tanghan 2013-7-20
	 */
	private void repairTypeAndGroup() {
		TypeGroup icontype = new TypeGroup();
		icontype.setTypeGroupName("图标类型");
		icontype.setTypeGroupCode("icontype");
		commonDao.saveOrUpdate(icontype);

		TypeGroup ordertype = new TypeGroup();
		ordertype.setTypeGroupName("订单类型");
		ordertype.setTypeGroupCode("order");
		commonDao.saveOrUpdate(ordertype);

		TypeGroup custom = new TypeGroup();
		custom.setTypeGroupName("客户类型");
		custom.setTypeGroupCode("custom");
		commonDao.saveOrUpdate(custom);

		TypeGroup servicetype = new TypeGroup();
		servicetype.setTypeGroupName("服务项目类型");
		servicetype.setTypeGroupCode("service");
		commonDao.saveOrUpdate(servicetype);

		TypeGroup searchMode = new TypeGroup();
		searchMode.setTypeGroupName("查询模式");
		searchMode.setTypeGroupCode("searchmode");
		commonDao.saveOrUpdate(searchMode);

		TypeGroup yesOrno = new TypeGroup();
		yesOrno.setTypeGroupName("逻辑条件");
		yesOrno.setTypeGroupCode("yesorno");
		commonDao.saveOrUpdate(yesOrno);

		TypeGroup fieldtype = new TypeGroup();
		fieldtype.setTypeGroupName("字段类型");
		fieldtype.setTypeGroupCode("fieldtype");
		commonDao.saveOrUpdate(fieldtype);

		TypeGroup datatable = new TypeGroup();
		datatable.setTypeGroupName("数据表");
		datatable.setTypeGroupCode("database");
		commonDao.saveOrUpdate(datatable);

		TypeGroup filetype = new TypeGroup();
		filetype.setTypeGroupName("文档分类");
		filetype.setTypeGroupCode("fieltype");
		commonDao.saveOrUpdate(filetype);

		TypeGroup sex = new TypeGroup();
		sex.setTypeGroupName("性别类");
		sex.setTypeGroupCode("sex");
		commonDao.saveOrUpdate(sex);
	}

	/**
	 * @Description 修复类型表
	 * @author tanghan 2013-7-22
	 */
	private void repairType() {
		TypeGroup icontype = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "图标类型").get(0);
		TypeGroup ordertype = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "订单类型").get(0);
		TypeGroup custom = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "客户类型").get(0);
		TypeGroup servicetype = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "服务项目类型").get(0);
		TypeGroup datatable = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "数据表").get(0);
		TypeGroup filetype = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "文档分类").get(0);
		TypeGroup sex = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "性别类").get(0);
		TypeGroup searchmode = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "查询模式").get(0);
		TypeGroup yesorno = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "逻辑条件").get(0);
		TypeGroup fieldtype = commonDao.findByProperty(TypeGroup.class, "typeGroupName", "字段类型").get(0);

		Type menu = new Type();
		menu.setName("菜单图标");
		menu.setCode("2");
		menu.setTypeGroup(icontype);
		commonDao.saveOrUpdate(menu);

		Type systemicon = new Type();
		systemicon.setName("系统图标");
		systemicon.setCode("1");
		systemicon.setTypeGroup(icontype);
		commonDao.saveOrUpdate(systemicon);

		Type file = new Type();
		file.setName("附件");
		file.setCode("files");
		file.setTypeGroup(filetype);
		commonDao.saveOrUpdate(file);

		Type goodorder = new Type();
		goodorder.setName("优质订单");
		goodorder.setCode("1");
		goodorder.setTypeGroup(ordertype);
		commonDao.saveOrUpdate(goodorder);

		Type general = new Type();
		general.setName("普通订单");
		general.setCode("2");
		general.setTypeGroup(ordertype);
		commonDao.saveOrUpdate(general);

		Type sign = new Type();
		sign.setName("签约客户");
		sign.setCode("1");
		sign.setTypeGroup(custom);
		commonDao.saveOrUpdate(sign);

		Type commoncustom = new Type();
		commoncustom.setName("普通客户");
		commoncustom.setCode("2");
		commoncustom.setTypeGroup(custom);
		commonDao.saveOrUpdate(commoncustom);

		Type vipservice = new Type();
		vipservice.setName("特殊服务");
		vipservice.setCode("1");
		vipservice.setTypeGroup(servicetype);
		commonDao.saveOrUpdate(vipservice);

		Type commonservice = new Type();
		commonservice.setName("普通服务");
		commonservice.setCode("2");
		commonservice.setTypeGroup(servicetype);
		commonDao.saveOrUpdate(commonservice);

		Type single = new Type();
		single.setName("单条件查询");
		single.setCode("single");
		single.setTypeGroup(searchmode);
		commonDao.saveOrUpdate(single);

		Type group = new Type();
		group.setName("范围查询");
		group.setCode("group");
		group.setTypeGroup(searchmode);
		commonDao.saveOrUpdate(group);

		Type yes = new Type();
		yes.setName("是");
		yes.setCode("Y");
		yes.setTypeGroup(yesorno);
		commonDao.saveOrUpdate(yes);

		Type no = new Type();
		no.setName("否");
		no.setCode("N");
		no.setTypeGroup(yesorno);
		commonDao.saveOrUpdate(no);

		Type type_integer = new Type();
		type_integer.setName("Integer");
		type_integer.setCode("Integer");
		type_integer.setTypeGroup(fieldtype);
		commonDao.saveOrUpdate(type_integer);

		Type type_date = new Type();
		type_date.setName("Date");
		type_date.setCode("Date");
		type_date.setTypeGroup(fieldtype);
		commonDao.saveOrUpdate(type_date);

		Type type_string = new Type();
		type_string.setName("String");
		type_string.setCode("String");
		type_string.setTypeGroup(fieldtype);
		commonDao.saveOrUpdate(type_string);

		Type type_long = new Type();
		type_long.setName("Long");
		type_long.setCode("Long");
		type_long.setTypeGroup(fieldtype);
		commonDao.saveOrUpdate(type_long);

		Type workflow = new Type();
		workflow.setName("工作流引擎表");
		workflow.setCode("act");
		workflow.setTypeGroup(datatable);
		commonDao.saveOrUpdate(workflow);

		Type systable = new Type();
		systable.setName("系统基础表");
		systable.setCode("t_s");
		systable.setTypeGroup(datatable);
		commonDao.saveOrUpdate(systable);

		Type business = new Type();
		business.setName("业务表");
		business.setCode("t_b");
		business.setTypeGroup(datatable);
		commonDao.saveOrUpdate(business);

		Type customwork = new Type();
		customwork.setName("自定义引擎表");
		customwork.setCode("t_p");
		customwork.setTypeGroup(datatable);
		commonDao.saveOrUpdate(customwork);

		Type news = new Type();
		news.setName("新闻");
		news.setCode("news");
		news.setTypeGroup(filetype);
		commonDao.saveOrUpdate(news);

		Type man = new Type();
		man.setName("男性");
		man.setCode("0");
		man.setTypeGroup(sex);
		commonDao.saveOrUpdate(man);

		Type woman = new Type();
		woman.setName("女性");
		woman.setCode("1");
		woman.setTypeGroup(sex);
		commonDao.saveOrUpdate(woman);
	}

	/**
	 * @Description 修复角色表
	 * @author tanghan 2013-7-20
	 */
	private void repairRole() {
		Role admin = new Role();
		admin.setRoleName("管理员");
		admin.setRoleCode("admin");
		commonDao.saveOrUpdate(admin);

		Role manager = new Role();
		manager.setRoleName("普通用户");
		manager.setRoleCode("manager");
		commonDao.saveOrUpdate(manager);

	}

	/**
	 * @Description 修复部门表
	 * @author tanghan 2013-7-20
	 */
	private void repairDepart() {
		Department eiu = new Department();
		eiu.setName("信息部");
		eiu.setDescription("12");
		commonDao.saveOrUpdate(eiu);

		Department desgin = new Department();
		desgin.setName("设计部");
		commonDao.saveOrUpdate(desgin);

		Department RAndD = new Department();
		RAndD.setName("研发室");
		RAndD.setDescription("研发技术难题");
		RAndD.setParent(desgin);
		commonDao.saveOrUpdate(RAndD);
	}

	/**
	 * @Description 修复附件表
	 * @author tanghan 2013-7-20
	 */
	private void repairAttachment() {
		Attachment jro = new Attachment();
		jro.setAttachmentName("JR079839867R90000001000");
		jro.setRealPath("JR079839867R90000001000");
		jro.setSwfPath("upload/files/20130719201109hDr31jP1.swf");
		jro.setExtendName("doc");
		commonDao.saveOrUpdate(jro);

		Attachment treaty = new Attachment();
		treaty.setAttachmentName("SB平台协议");
		treaty.setRealPath("SB平台协议");
		treaty.setSwfPath("upload/files/20130719201156sYHjSFJj.swf");
		treaty.setExtendName("docx");
		commonDao.saveOrUpdate(treaty);

		Attachment analyse = new Attachment();
		analyse.setAttachmentName("分析SB与其他的开源项目的不足和优势");
		analyse.setRealPath("分析SB与其他的开源项目的不足和优势");
		analyse.setSwfPath("upload/files/20130719201727ZLEX1OSf.swf");
		analyse.setExtendName("docx");
		commonDao.saveOrUpdate(analyse);

		Attachment DMS = new Attachment();
		DMS.setAttachmentName("DMS-T3第三方租赁业务接口开发说明");
		DMS.setRealPath("DMS-T3第三方租赁业务接口开发说明");
		DMS.setSwfPath("upload/files/20130719201841LzcgqUek.swf");
		DMS.setExtendName("docx");
		commonDao.saveOrUpdate(DMS);

		Attachment sap = new Attachment();
		sap.setAttachmentName("SAP-需求说明书-金融服务公司-第三方租赁业务需求V1.7-研发");
		sap.setRealPath("SAP-需求说明书-金融服务公司-第三方租赁业务需求V1.7-研发");
		sap.setSwfPath("upload/files/20130719201925mkCrU47P.swf");
		sap.setExtendName("doc");
		commonDao.saveOrUpdate(sap);

		Attachment standard = new Attachment();
		standard.setAttachmentName("SB团队开发规范");
		standard.setRealPath("SB团队开发规范");
		standard.setSwfPath("upload/files/20130724103633fvOTwNSV.swf");
		standard.setExtendName("txt");
		commonDao.saveOrUpdate(standard);

		Attachment temple = new Attachment();
		temple.setAttachmentName("第一模板");
		temple.setRealPath("第一模板");
		temple.setSwfPath("upload/files/20130724104603pHDw4QUT.swf");
		temple.setExtendName("doc");
		commonDao.saveOrUpdate(temple);

		Attachment githubhelp = new Attachment();
		githubhelp.setAttachmentName("github入门使用教程");
		githubhelp.setRealPath("github入门使用教程");
		githubhelp.setSwfPath("upload/files/20130704200345EakUH3WB.swf");
		githubhelp.setExtendName("doc");
		commonDao.saveOrUpdate(githubhelp);

		Attachment githelp = new Attachment();
		githelp.setAttachmentName("github入门使用教程");
		githelp.setRealPath("github入门使用教程");
		githelp.setSwfPath("upload/files/20130704200651IE8wPdZ4.swf");
		githelp.setExtendName("doc");
		commonDao.saveOrUpdate(githelp);

		Attachment settable = new Attachment();
		settable.setAttachmentName("（黄世民）-金融服务公司机构岗位职责与任职资格设置表(根据模板填写）");
		settable.setRealPath("（黄世民）-金融服务公司机构岗位职责与任职资格设置表(根据模板填写）");
		settable.setSwfPath("upload/files/20130704201022KhdRW1Gd.swf");
		settable.setExtendName("xlsx");
		commonDao.saveOrUpdate(settable);

		Attachment eim = new Attachment();
		eim.setAttachmentName("EIM201_CN");
		eim.setRealPath("EIM201_CN");
		eim.setSwfPath("upload/files/20130704201046JVAkvvOt.swf");
		eim.setExtendName("pdf");
		commonDao.saveOrUpdate(eim);

		Attachment github = new Attachment();
		github.setAttachmentName("github入门使用教程");
		github.setRealPath("github入门使用教程");
		github.setSwfPath("upload/files/20130704201116Z8NhEK57.swf");
		github.setExtendName("doc");
		commonDao.saveOrUpdate(github);

		Attachment taghelp = new Attachment();
		taghelp.setAttachmentName("SBUI标签库帮助文档v3.2");
		taghelp.setRealPath("SBUI标签库帮助文档v3.2");
		taghelp.setSwfPath("upload/files/20130704201125DQg8hi2x.swf");
		taghelp.setExtendName("pdf");
		commonDao.saveOrUpdate(taghelp);
	}

	/**
	 * @Description 修复图标表
	 * @author tanghan 2013-7-19
	 */
	private void repaireIcon() {
		com.sbplatform.core.util.LogUtil.info("修复图标中");
		Icon back = new Icon();
		back.setIconName("返回");
		back.setIconType((short) 1);
		back.setIconPath("plug-in/accordion/images/back.png");
		back.setIconClass("back");
		back.setExtendName("png");
		commonDao.saveOrUpdate(back);

		Icon pie = new Icon();

		pie.setIconName("饼图");
		pie.setIconType((short) 1);
		pie.setIconPath("plug-in/accordion/images/pie.png");
		pie.setIconClass("pie");
		pie.setExtendName("png");
		commonDao.saveOrUpdate(pie);

		Icon pictures = new Icon();
		pictures.setIconName("图片");
		pictures.setIconType((short) 1);
		pictures.setIconPath("plug-in/accordion/images/pictures.png");
		pictures.setIconClass("pictures");
		pictures.setExtendName("png");
		commonDao.saveOrUpdate(pictures);

		Icon pencil = new Icon();
		pencil.setIconName("笔");
		pencil.setIconType((short) 1);
		pencil.setIconPath("plug-in/accordion/images/pencil.png");
		pencil.setIconClass("pencil");
		pencil.setExtendName("png");
		commonDao.saveOrUpdate(pencil);

		Icon map = new Icon();
		map.setIconName("地图");
		map.setIconType((short) 1);
		map.setIconPath("plug-in/accordion/images/map.png");
		map.setIconClass("map");
		map.setExtendName("png");
		commonDao.saveOrUpdate(map);

		Icon group_add = new Icon();
		group_add.setIconName("组");
		group_add.setIconType((short) 1);
		group_add.setIconPath("plug-in/accordion/images/group_add.png");
		group_add.setIconClass("group_add");
		group_add.setExtendName("png");
		commonDao.saveOrUpdate(group_add);

		Icon calculator = new Icon();
		calculator.setIconName("计算器");
		calculator.setIconType((short) 1);
		calculator.setIconPath("plug-in/accordion/images/calculator.png");
		calculator.setIconClass("calculator");
		calculator.setExtendName("png");
		commonDao.saveOrUpdate(calculator);

		Icon folder = new Icon();
		folder.setIconName("文件夹");
		folder.setIconType((short) 1);
		folder.setIconPath("plug-in/accordion/images/folder.png");
		folder.setIconClass("folder");
		folder.setExtendName("png");
		commonDao.saveOrUpdate(folder);
	}

	/**
	 * @Description 修复菜单权限
	 * @author tanghan 2013-7-19
	 */
	private void repairMenu() {
		Icon pic = commonDao.findByProperty(Icon.class, "iconName", "图片").get(0);
		Icon group_add = commonDao.findByProperty(Icon.class, "iconName", "组").get(0);
		Icon pie = commonDao.findByProperty(Icon.class, "iconName", "饼图").get(0);
		Icon folder = commonDao.findByProperty(Icon.class, "iconName", "文件夹").get(0);
		com.sbplatform.core.util.LogUtil.info(pic.getIconPath());
		MenuFunction autoinput = new MenuFunction();
		autoinput.setName("Online 开发");
		autoinput.setUrl("");
		autoinput.setLevel((short) 0);
		autoinput.setSort("1");
		autoinput.setIcon(folder);
		commonDao.saveOrUpdate(autoinput);

		MenuFunction sys = new MenuFunction();
		sys.setName("系统管理");
		sys.setUrl("");
		sys.setLevel((short) 0);
		sys.setSort("5");
		sys.setIcon(group_add);
		commonDao.saveOrUpdate(sys);

		MenuFunction state = new MenuFunction();
		state.setName("统计查询");
		state.setUrl("");
		state.setLevel((short) 0);
		state.setSort("3");
		state.setIcon(folder);
		commonDao.saveOrUpdate(state);

		MenuFunction commondemo = new MenuFunction();
		commondemo.setName("常用示例");
		commondemo.setUrl("");
		commondemo.setLevel((short) 0);
		commondemo.setSort("8");
		commondemo.setIcon(pic);
		commonDao.saveOrUpdate(commondemo);

		MenuFunction syscontrol = new MenuFunction();
		syscontrol.setName("系统监控");
		syscontrol.setUrl("");
		syscontrol.setLevel((short) 0);
		syscontrol.setSort("11");
		syscontrol.setIcon(pic);
		commonDao.saveOrUpdate(syscontrol);
		MenuFunction user = new MenuFunction();
		user.setName("用户管理");
		user.setUrl("userController.do?user");
		user.setLevel((short) 1);
		user.setSort("5");
		user.setParent(sys);
		user.setIcon(pic);
		commonDao.saveOrUpdate(user);

		MenuFunction role = new MenuFunction();
		role.setName("角色管理");
		role.setUrl("roleController.do?role");
		role.setLevel((short) 1);
		role.setSort("6");
		role.setParent(sys);
		role.setIcon(pic);
		commonDao.saveOrUpdate(role);

		MenuFunction menu = new MenuFunction();
		menu.setName("菜单管理");
		menu.setUrl("functionController.do?function");
		menu.setLevel((short) 1);
		menu.setSort("7");
		menu.setParent(sys);
		menu.setIcon(pic);
		commonDao.saveOrUpdate(menu);

		MenuFunction typeGroup = new MenuFunction();
		typeGroup.setName("数据字典");
		typeGroup.setUrl("systemController.do?typeGroupList");
		typeGroup.setLevel((short) 1);
		typeGroup.setSort("6");
		typeGroup.setParent(sys);
		typeGroup.setIcon(pic);
		commonDao.saveOrUpdate(typeGroup);

		MenuFunction icon = new MenuFunction();
		icon.setName("图标管理");
		icon.setUrl("iconController.do?icon");
		icon.setLevel((short) 1);
		icon.setSort("18");
		icon.setParent(sys);
		icon.setIcon(pic);
		commonDao.saveOrUpdate(icon);

		MenuFunction depart = new MenuFunction();
		depart.setName("部门管理");
		depart.setUrl("departController.do?depart");
		depart.setLevel((short) 1);
		depart.setSort("22");
		depart.setParent(sys);
		depart.setIcon(pic);
		commonDao.saveOrUpdate(depart);

		MenuFunction territory = new MenuFunction();
		territory.setName("地域管理");
		territory.setUrl("territoryController.do?territory");
		territory.setLevel((short) 1);
		territory.setSort("22");
		territory.setParent(sys);
		territory.setIcon(pie);
		commonDao.saveOrUpdate(territory);
		MenuFunction useranalyse = new MenuFunction();
		useranalyse.setName("用户分析");
		useranalyse.setUrl("logController.do?statisticTabs&isIframe");
		useranalyse.setLevel((short) 1);
		useranalyse.setSort("17");
		useranalyse.setParent(state);
		useranalyse.setIcon(pie);
		commonDao.saveOrUpdate(useranalyse);
		MenuFunction formconfig = new MenuFunction();
		formconfig.setName("表单配置");
		formconfig.setUrl("cgFormHeadController.do?cgFormHeadList");
		formconfig.setLevel((short) 1);
		formconfig.setSort("1");
		formconfig.setParent(autoinput);
		formconfig.setIcon(pic);
		commonDao.saveOrUpdate(formconfig);
		MenuFunction formconfig1 = new MenuFunction();
		formconfig1.setName("动态报表配置");
		formconfig1.setUrl("cgreportConfigHeadController.do?cgreportConfigHead");
		formconfig1.setLevel((short) 1);
		formconfig1.setSort("2");
		formconfig1.setParent(autoinput);
		formconfig1.setIcon(pic);
		commonDao.saveOrUpdate(formconfig1);
		MenuFunction druid = new MenuFunction();
		druid.setName("数据监控");
		druid.setUrl("dataSourceController.do?goDruid&isIframe");
		druid.setLevel((short) 1);
		druid.setSort("11");
		druid.setParent(syscontrol);
		druid.setIcon(pic);
		commonDao.saveOrUpdate(druid);

		MenuFunction log = new MenuFunction();
		log.setName("系统日志");
		log.setUrl("logController.do?log");
		log.setLevel((short) 1);
		log.setSort("21");
		log.setParent(syscontrol);
		log.setIcon(pic);
		commonDao.saveOrUpdate(log);

		MenuFunction timeTask = new MenuFunction();
		timeTask.setName("定时任务");
		timeTask.setUrl("timeTaskController.do?timeTask");
		timeTask.setLevel((short) 1);
		timeTask.setSort("21");
		timeTask.setParent(syscontrol);
		timeTask.setIcon(pic);
		commonDao.saveOrUpdate(timeTask);
		MenuFunction formcheck = new MenuFunction();
		formcheck.setName("表单验证");
		formcheck.setUrl("demoController.do?formTabs");
		formcheck.setLevel((short) 1);
		formcheck.setSort("1");
		formcheck.setParent(commondemo);
		formcheck.setIcon(pic);
		commonDao.saveOrUpdate(formcheck);

		MenuFunction demo = new MenuFunction();
		demo.setName("Demo示例");
		demo.setUrl("sbDemoController.do?sbDemo");
		demo.setLevel((short) 1);
		demo.setSort("2");
		demo.setParent(commondemo);
		demo.setIcon(pic);
		commonDao.saveOrUpdate(demo);

		MenuFunction minidao = new MenuFunction();
		minidao.setName("Minidao例子");
		minidao.setUrl("sbMinidaoController.do?sbMinidao");
		minidao.setLevel((short) 1);
		minidao.setSort("2");
		minidao.setParent(commondemo);
		minidao.setIcon(pic);
		commonDao.saveOrUpdate(minidao);

		MenuFunction onetable = new MenuFunction();
		onetable.setName("单表模型");
		onetable.setUrl("sbNoteController.do?sbNote");
		onetable.setLevel((short) 1);
		onetable.setSort("3");
		onetable.setParent(commondemo);
		onetable.setIcon(pic);
		commonDao.saveOrUpdate(onetable);

		MenuFunction onetoMany = new MenuFunction();
		onetoMany.setName("一对多模型");
		onetoMany.setUrl("sbOrderMainController.do?sbOrderMain");
		onetoMany.setLevel((short) 1);
		onetoMany.setSort("4");
		onetoMany.setParent(commondemo);
		onetoMany.setIcon(pic);
		commonDao.saveOrUpdate(onetoMany);

		MenuFunction excel = new MenuFunction();
		excel.setName("Excel导入导出");
		excel.setUrl("courseController.do?course");
		excel.setLevel((short) 1);
		excel.setSort("5");
		excel.setParent(commondemo);
		excel.setIcon(pic);
		commonDao.saveOrUpdate(excel);

		MenuFunction uploadownload = new MenuFunction();
		uploadownload.setName("上传下载");
		uploadownload.setUrl("commonController.do?listTurn&turn=system/document/filesList");
		uploadownload.setLevel((short) 1);
		uploadownload.setSort("6");
		uploadownload.setParent(commondemo);
		uploadownload.setIcon(pic);
		commonDao.saveOrUpdate(uploadownload);

		MenuFunction jqueryFileUpload = new MenuFunction();
		jqueryFileUpload.setName("JqueryFileUpload示例");
		jqueryFileUpload.setUrl("fileUploadController.do?fileUploadSample&isIframe");
		jqueryFileUpload.setLevel((short) 1);
		jqueryFileUpload.setSort("6");
		jqueryFileUpload.setParent(commondemo);
		jqueryFileUpload.setIcon(pic);
		commonDao.saveOrUpdate(jqueryFileUpload);

		MenuFunction nopaging = new MenuFunction();
		nopaging.setName("无分页列表");
		nopaging.setUrl("userNoPageController.do?user");
		nopaging.setLevel((short) 1);
		nopaging.setSort("7");
		nopaging.setIcon(pic);
		nopaging.setParent(commondemo);
		commonDao.saveOrUpdate(nopaging);

		MenuFunction jdbcdemo = new MenuFunction();
		jdbcdemo.setName("jdbc示例");
		jdbcdemo.setUrl("sbJdbcController.do?sbJdbc");
		jdbcdemo.setLevel((short) 1);
		jdbcdemo.setSort("8");
		jdbcdemo.setParent(commondemo);
		jdbcdemo.setIcon(pic);
		commonDao.saveOrUpdate(jdbcdemo);

		MenuFunction sqlsep = new MenuFunction();
		sqlsep.setName("SQL分离");
		sqlsep.setUrl("sbJdbcController.do?dictParameter");
		sqlsep.setLevel((short) 1);
		sqlsep.setIcon(pic);
		sqlsep.setSort("9");
		sqlsep.setParent(commondemo);
		commonDao.saveOrUpdate(sqlsep);

		MenuFunction dicttag = new MenuFunction();
		dicttag.setName("字典标签");
		dicttag.setUrl("demoController.do?dictSelect");
		dicttag.setLevel((short) 1);
		dicttag.setSort("10");
		dicttag.setParent(commondemo);
		dicttag.setIcon(pic);
		commonDao.saveOrUpdate(dicttag);

		MenuFunction demomaintain = new MenuFunction();
		demomaintain.setName("表单弹出风格");
		demomaintain.setUrl("demoController.do?demoList");
		demomaintain.setLevel((short) 1);
		demomaintain.setSort("11");
		demomaintain.setParent(commondemo);
		demomaintain.setIcon(pic);
		commonDao.saveOrUpdate(demomaintain);

		MenuFunction democlassify = new MenuFunction();
		democlassify.setName("特殊布局");
		democlassify.setUrl("demoController.do?demoIframe");
		democlassify.setLevel((short) 1);
		democlassify.setSort("12");
		democlassify.setParent(commondemo);
		democlassify.setIcon(pic);
		commonDao.saveOrUpdate(democlassify);

		MenuFunction notag1 = new MenuFunction();
		notag1.setName("单表例子(无Tag)");
		notag1.setUrl("sbEasyUIController.do?sbEasyUI");
		notag1.setLevel((short) 1);
		notag1.setSort("13");
		notag1.setParent(commondemo);
		notag1.setIcon(pic);
		commonDao.saveOrUpdate(notag1);

		MenuFunction notag2 = new MenuFunction();
		notag2.setName("一对多例子(无Tag)");
		notag2.setUrl("sbOrderMainNoTagController.do?sbOrderMainNoTag");
		notag2.setLevel((short) 1);
		notag2.setSort("14");
		notag2.setParent(commondemo);
		notag2.setIcon(pic);
		commonDao.saveOrUpdate(notag2);

		MenuFunction htmledit = new MenuFunction();
		htmledit.setName("HTML编辑器");
		htmledit.setUrl("sbDemoController.do?ckeditor&isIframe");
		htmledit.setLevel((short) 1);
		htmledit.setSort("15");
		htmledit.setParent(commondemo);
		htmledit.setIcon(pic);
		commonDao.saveOrUpdate(htmledit);

		MenuFunction weboffice = new MenuFunction();
		weboffice.setName("在线word(IE)");
		weboffice.setUrl("webOfficeController.do?webOffice");
		weboffice.setLevel((short) 1);
		weboffice.setSort("16");
		weboffice.setParent(commondemo);
		weboffice.setIcon(pic);
		commonDao.saveOrUpdate(weboffice);

		MenuFunction Office = new MenuFunction();
		Office.setName("WebOffice官方例子");
		Office.setUrl("webOfficeController.do?webOfficeSample&isIframe");
		Office.setLevel((short) 1);
		Office.setSort("17");
		Office.setIcon(pic);
		Office.setParent(commondemo);
		commonDao.saveOrUpdate(Office);

		MenuFunction finance = new MenuFunction();
		finance.setName("多附件管理");
		finance.setUrl("tFinanceController.do?tFinance");
		finance.setLevel((short) 1);
		finance.setSort("18");
		finance.setParent(commondemo);
		finance.setIcon(pic);
		commonDao.saveOrUpdate(finance);

		MenuFunction userdemo = new MenuFunction();
		userdemo.setName("Datagrid手工Html");
		userdemo.setUrl("userController.do?userDemo");
		userdemo.setLevel((short) 1);
		userdemo.setSort("19");
		userdemo.setParent(commondemo);
		userdemo.setIcon(pic);
		commonDao.saveOrUpdate(userdemo);
		MenuFunction matterBom = new MenuFunction();
		matterBom.setName("物料Bom");
		matterBom.setUrl("sbMatterBomController.do?goList");
		matterBom.setLevel((short) 1);
		matterBom.setSort("20");
		matterBom.setParent(commondemo);
		matterBom.setIcon(pic);
		commonDao.saveOrUpdate(matterBom);
		MenuFunction reportdemo = new MenuFunction();
		reportdemo.setName("报表示例");
		reportdemo.setUrl("reportDemoController.do?studentStatisticTabs&isIframe");
		reportdemo.setLevel((short) 1);
		reportdemo.setSort("21");
		reportdemo.setParent(state);
		reportdemo.setIcon(pie);
		commonDao.saveOrUpdate(reportdemo);

		MenuFunction ckfinder = new MenuFunction();
		ckfinder.setName("ckfinder例子");
		ckfinder.setUrl("sbDemoCkfinderController.do?sbDemoCkfinder");
		ckfinder.setLevel((short) 1);
		ckfinder.setSort("100");
		ckfinder.setParent(commondemo);
		ckfinder.setIcon(pic);
		commonDao.saveOrUpdate(ckfinder);
	}

	/**
	 * 修复报表统计demo
	 *@Author fancq
	 *@date   2013-11-14
	 */
	private void repairReportEntity() {
		TSStudent entity = null;
		entity = new TSStudent();
		entity.setName("张三");
		entity.setSex("f");
		entity.setClassName("1班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("李四");
		entity.setSex("f");
		entity.setClassName("1班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("王五");
		entity.setSex("m");
		entity.setClassName("1班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("f");
		entity.setClassName("1班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("张三");
		entity.setSex("f");
		entity.setClassName("2班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("李四");
		entity.setSex("f");
		entity.setClassName("2班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("王五");
		entity.setSex("m");
		entity.setClassName("2班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("f");
		entity.setClassName("2班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("张三");
		entity.setSex("f");
		entity.setClassName("3班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("李四");
		entity.setSex("f");
		entity.setClassName("3班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("王五");
		entity.setSex("m");
		entity.setClassName("3班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("李四");
		entity.setSex("f");
		entity.setClassName("3班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("王五");
		entity.setSex("m");
		entity.setClassName("3班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("f");
		entity.setClassName("3班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("张三");
		entity.setSex("f");
		entity.setClassName("4班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("李四");
		entity.setSex("f");
		entity.setClassName("4班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("王五");
		entity.setSex("m");
		entity.setClassName("4班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("f");
		entity.setClassName("4班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("张三");
		entity.setSex("m");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("李四");
		entity.setSex("f");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("王五");
		entity.setSex("m");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("m");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("m");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("李四");
		entity.setSex("f");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("王五");
		entity.setSex("m");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("m");
		entity.setClassName("5班");
		commonDao.save(entity);

		entity = new TSStudent();
		entity.setName("赵六");
		entity.setSex("m");
		entity.setClassName("5班");
		commonDao.save(entity);

	}
}
