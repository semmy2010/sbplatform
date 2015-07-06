package sb.system.service.impl;

import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Icon;
import com.sbplatform.web.system.service.RepairService;

import com.sbplatform.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 
 * @Description 修复数据库Service
 * @ClassName: RepairService
 * @author tanghan
 * @date 2013-7-19 下午01:31:00  
 */ 
@Service("repairService")
@Transactional
public class RepairServiceImpl extends CommonServiceImpl implements RepairService{
        
	/** 
	 * @Description  先清空数据库，然后再修复数据库
	 * @author tanghan 2013-7-19  
	 */
	
	public void deleteAndRepair() {
		
	}

	/** 
	 * @Description  修复数据库
	 * @author tanghan 2013-7-19  
	 */
	
		synchronized public void repair() {
		repaireIcon(); //修复图标
		repairAttachment();  //修改附件
		repairDepart();
		repairMenu();// 修复菜单权限【权限控制到菜单级别】
		repairRole();// 修复角色
		repairUser();// 修复用户
//		repairRoleAuth();// 修复角色和权限的关系
//		repairUserRole();// 修复用户和角色的关系
//		repairDict();// 修复字典
//		repairOrg();// 修复组织机构
	}

/** 
	 * @Description 
	 * @author tanghan 2013-7-28  
	 */
	private void repairCgFormField() {
		<#list cghead as hd>
			//表单[${hd.content}] - 字段清单
			CgFormHeadEntity ${hd.tableName} = commonDao.findByProperty(CgFormHeadEntity.class, "tableName", "${hd.tableName}").get(0);
		<#list hd.columns as f>
	        CgFormFieldEntity ${hd.tableName}_${f.fieldName} = new CgFormFieldEntity();
	        ${hd.tableName}_${f.fieldName}.setFieldName("${f.fieldName}");
	        ${hd.tableName}_${f.fieldName}.setTable(${hd.tableName});
	        ${hd.tableName}_${f.fieldName}.setFieldLength(${f.fieldLength});
	        ${hd.tableName}_${f.fieldName}.setIsKey("${f.isKey}");
	        ${hd.tableName}_${f.fieldName}.setIsNull("${f.isNull}");
	        ${hd.tableName}_${f.fieldName}.setIsQuery("${f.isQuery}");
	        ${hd.tableName}_${f.fieldName}.setIsShow("${f.isShow}");
	        ${hd.tableName}_${f.fieldName}.setShowType("${f.showType}");
	        ${hd.tableName}_${f.fieldName}.setLength(${f.length});
	        ${hd.tableName}_${f.fieldName}.setType("${f.type}");
	        ${hd.tableName}_${f.fieldName}.setOrderNum(${f.orderNum});
	        ${hd.tableName}_${f.fieldName}.setPointLength(${f.pointLength});
	        ${hd.tableName}_${f.fieldName}.setQueryMode("${f.queryMode}");
	        ${hd.tableName}_${f.fieldName}.setContent("${f.content}");
	        ${hd.tableName}_${f.fieldName}.setCreateBy("admin");
	        ${hd.tableName}_${f.fieldName}.setCreateDate(new Date());
	        ${hd.tableName}_${f.fieldName}.setCreateName("管理员");
	        ${hd.tableName}_${f.fieldName}.setDictField("${f.dictField}");
	        ${hd.tableName}_${f.fieldName}.setDictTable("${f.dictTable}");
	        ${hd.tableName}_${f.fieldName}.setMainTable("${f.mainTable}");
	        ${hd.tableName}_${f.fieldName}.setMainField("${f.mainField}");
	        commonDao.saveOrUpdate(${hd.tableName}_${f.fieldName});
	        
        </#list>
	  	</#list>
	}


    	/** 
	 * @Description 
	 * @author tanghan 2013-7-28  
	 */
	private void repairFormHead() {
	  <#list cghead as hd>
	 	CgFormHeadEntity cgHead${hd.id}  = new CgFormHeadEntity();
		cgHead${hd.id}.setTableName("${hd.tableName}");
		cgHead${hd.id}.setIsTree("${hd.isTree}");
		cgHead${hd.id}.setIsPagination("${hd.isPagination}");
		cgHead${hd.id}.setIsCheckbox("${hd.isCheckbox}");
		cgHead${hd.id}.setQuerymode("${hd.querymode}");
		cgHead${hd.id}.setIsDbSynch("N");
		cgHead${hd.id}.setContent("${hd.content}");
		cgHead${hd.id}.setCreateBy("admin");
		cgHead${hd.id}.setCreateDate(new Date());
		cgHead${hd.id}.setJsPlugIn("0");
		cgHead${hd.id}.setSqlPlugIn("0");
		cgHead${hd.id}.setCreateName("管理员");
		cgHead${hd.id}.setJformVersion("${hd.jformVersion}");
		cgHead${hd.id}.setJformType(${hd.jformType});
		commonDao.saveOrUpdate(cgHead${hd.id});
		
	   </#list>
	}

		/** 
		 * @Description 
		 * @author tanghan 2013-7-28  
		 */
		private void repairCkEditor() {
		}
 

    /** 
	 * @Description 
	 * @author tanghan 2013-7-28  
	 */
	private void repairLog() {
		User admin = commonDao.findByProperty(User.class, "signatureFile", "images/renfang/qm/licf.gif").get(0);
		try {
        <#list log as ll>
          Log log${ll.id} = new Log();
          log${ll.id}.setLogContent("${ll.logContent}");
          log${ll.id}.setBroswer("${ll.broswer}");
          log${ll.id}.setNote("${ll.note}");
          log${ll.id}.setOccurTime(DataUtils.parseTimestamp("${ll.operatetime}", "yyyy-MM-dd HH:mm"));
          log${ll.id}.setOperateType((short)${ll.operateType});
          log${ll.id}.setLoglevel((short)${ll.logLevel});
          commonDao.saveOrUpdate(log${ll.id});
          
        </#list>
        } catch (ParseException e) {
			e.printStackTrace();
		}
	}

    /** 
	 * @Description 
	 * @author tanghan 2013-7-22  
	 */
	private void repairBaseUser() {
		Department eiu = commonDao.findByProperty(Department.class, "name", "信息部").get(0);
		Department RAndD = commonDao.findByProperty(Department.class, "name", "信息部").get(0);
	 <#list baseuser as bb>
        BaseUser baseUser${bb.id} = new BaseUser();
        baseUser${bb.id}.setStatus((short)${bb.status});
        baseUser${bb.id}.setRealName("${bb.realName}");
        baseUser${bb.id}.setUserName("${bb.userName}");
        baseUser${bb.id}.setPassword("${bb.password}");
        baseUser${bb.id}.setDepartment(eiu);
        baseUser${bb.id}.setActivitiSync((short)${bb.activitiSync});
        commonDao.saveOrUpdate(baseUser${bb.id});
        
      </#list>
	}
 

    /** 
	 * @Description 
	 * @author tanghan 2013-7-22  
	 */
	private void repairType() {
	  <#list type as t>
         Type type${t.id} = new Type();
         type${t.id}.setName("${t.name}");
         type${t.id}.setCode("${t.code}");
         commonDao.saveOrUpdate(type${t.id});
         
      </#list>
	}

	private void repairTypeAndGroup() {
	  <#list typeGroup as p>
		TypeGroup typeGroup${p.id} = new TypeGroup();
		typeGroup${p.id}.setTypeGroupName("${p.typeGroupName}");
		typeGroup${p.id}.setTypeGroupCode("${p.typeGroupCode}");
		commonDao.saveOrUpdate(typeGroup${p.id});
	   </#list>
	}
	 
	/** 
	 * @Description 
	 * @author tanghan 2013-7-20  
	 */
	private void repairUser() {
	 <#list suser as pp>
       User suser${pp.id} = new User();
       suser${pp.id}.setMobilePhone("${pp.mobilePhone}");
       suser${pp.id}.setOfficePhone("${pp.officePhone}");
       suser${pp.id}.setEmail("${pp.email}");
       commonDao.saveOrUpdate(suser${pp.id});
      </#list>
	}
	 
	/** 
	 * @Description 
	 * @author tanghan 2013-7-20  
	 */
	  	
	private void repairRole() {
	  <#list role as p>
		Role role${p.id} = new Role();
        role${p.id}.setRoleName("${p.roleName}");
		role${p.id}.setRoleCode("${p.roleCode}");
		commonDao.saveOrUpdate(role${p.id});
      </#list>
	}

	 
	/** 
	 * @Description 
	 * @author tanghan 2013-7-20  
	 */
	private void repairDepart() {
	  <#list depart as po>
		Department department${po.id} = new Department();
	    department${po.id}.setName("${po.name}");
	    department${po.id}.setDescription("${po.description}");
        commonDao.saveOrUpdate(department${po.id});	
      </#list>	
	}

	 
	/** 
	 * @Description 
	 * @author tanghan 2013-7-20  
	 */
	  	
	private void repareAttachment() {
       <#list animals as being>
         Attachment attachment${being.id}  = new Attachment();
         attachment${being.id}.setAttachmentName("${being.attachmentName}");
         attachment${being.id}.setRealPath("${being.realPath}");
         attachment${being.id}.setSwfPath("${being.swfPath}");
         attachment${being.id}.setExtendName("${being.extendName}");
         commonDao.saveOrUpdate(attachment${being.id});
        </#list>
	}


	/** 
	 * @Description 
	 * @author tanghan 2013-7-19  
	 */
	private void repaireIcon() {
		System.out.println("修复图标中");
		Icon back = new Icon();
		back.setIconName("返回");
		back.setIconType((short)1);
		back.setIconPath("plug-in/accordion/images/back.png");
        back.setIconClass("back");	
        back.setExtendName("png");
        commonDao.saveOrUpdate(back);
        
        Icon pie = new Icon();
        
        pie.setIconName("饼图");
        pie.setIconType((short)1);
        pie.setIconPath("plug-in/accordion/images/pie.png");
        pie.setIconClass("pie");	
        pie.setExtendName("png");
        commonDao.saveOrUpdate(pie);
        
        Icon pictures = new Icon();
        pictures.setIconName("图片");
        pictures.setIconType((short)1);
        pictures.setIconPath("plug-in/accordion/images/pictures.png");
        pictures.setIconClass("pictures");	
        pictures.setExtendName("png");
        commonDao.saveOrUpdate(pictures);
        
        Icon pencil = new Icon();
        pencil.setIconName("笔");
        pencil.setIconType((short)1);
        pencil.setIconPath("plug-in/accordion/images/pencil.png");
        pencil.setIconClass("pencil");	
        pencil.setExtendName("png");
        commonDao.saveOrUpdate(pencil);
        
        Icon map = new Icon();
        map.setIconName("地图");
        map.setIconType((short)1);
        map.setIconPath("plug-in/accordion/images/map.png");
        map.setIconClass("map");	
        map.setExtendName("png");
        commonDao.saveOrUpdate(map);
        
        Icon group_add = new Icon();
        group_add.setIconName("组");
        group_add.setIconType((short)1);
        group_add.setIconPath("plug-in/accordion/images/group_add.png");
        group_add.setIconClass("group_add");	
        group_add.setExtendName("png");
        commonDao.saveOrUpdate(group_add);
        
        Icon calculator = new Icon();
        calculator.setIconName("计算器");
        calculator.setIconType((short)1);
        calculator.setIconPath("plug-in/accordion/images/calculator.png");
        calculator.setIconClass("calculator");	
        calculator.setExtendName("png");
        commonDao.saveOrUpdate(calculator);
        
        Icon folder = new Icon();
        folder.setIconName("文件夹");
        folder.setIconType((short)1);
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
	  <#list menu as mm>
 		MenuFunction menuFunction${mm.id} = new MenuFunction();
		menuFunction${mm.id}.setName("${mm.name}");
		menuFunction${mm.id}.setUrl("${mm.url}");
		menuFunction${mm.id}.setLevel((short)${mm.level});
		menuFunction${mm.id}.setSort("${mm.sort}");
        commonDao.saveOrUpdate(menuFunction${mm.id});	
      </#list>	
		
	}
	 

}
