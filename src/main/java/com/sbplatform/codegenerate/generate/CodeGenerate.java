/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.sbplatform.codegenerate.generate;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sbplatform.codegenerate.database.SbReadTable;
import com.sbplatform.codegenerate.pojo.Columnt;
import com.sbplatform.codegenerate.pojo.CreateFileProperty;
import com.sbplatform.codegenerate.util.CodeDateUtils;
import com.sbplatform.codegenerate.util.CodeResourceUtil;
import com.sbplatform.codegenerate.util.NonceUtils;
import com.sbplatform.codegenerate.util.def.FtlDef;

public class CodeGenerate implements ICallBack {
	private static final Log log = LogFactory.getLog(CodeGenerate.class);

	private static String entityPackage = "test";
	private static String entityName = "Person";
	private static String tableName = "person";
	private static String ftlDescription = "����";
	private static String primaryKeyPolicy = "uuid";
	private static String sequenceCode = "";
	private static String[] foreignKeys;
	private List<Columnt> originalColumns = new ArrayList();
	public static int FIELD_ROW_NUM = 1;
	private static CreateFileProperty createFileProperty = new CreateFileProperty();

	private List<Columnt> columns = new ArrayList();
	private SbReadTable dbFiledUtil = new SbReadTable();

	static {
		createFileProperty.setActionFlag(true);
		createFileProperty.setServiceIFlag(true);
		createFileProperty.setJspFlag(true);
		createFileProperty.setServiceImplFlag(true);
		createFileProperty.setJspMode("01");
		createFileProperty.setPageFlag(true);
		createFileProperty.setEntityFlag(true);
	}

	public CodeGenerate() {
	}

	public CodeGenerate(String entityPackage, String entityName, String tableName, String ftlDescription, CreateFileProperty createFileProperty, int fieldRowNum, String primaryKeyPolicy,
			String sequenceCode) {
		entityName = entityName;
		entityPackage = entityPackage;
		tableName = tableName;
		ftlDescription = ftlDescription;
		createFileProperty = createFileProperty;
		FIELD_ROW_NUM = fieldRowNum;
		primaryKeyPolicy = primaryKeyPolicy;
		sequenceCode = sequenceCode;
	}

	public CodeGenerate(String entityPackage, String entityName, String tableName, String ftlDescription, CreateFileProperty createFileProperty, String primaryKeyPolicy, String sequenceCode) {
		entityName = entityName;
		entityPackage = entityPackage;
		tableName = tableName;
		ftlDescription = ftlDescription;
		createFileProperty = createFileProperty;
		primaryKeyPolicy = primaryKeyPolicy;
		sequenceCode = sequenceCode;
	}

	public CodeGenerate(String entityPackage, String entityName, String tableName, String ftlDescription, CreateFileProperty createFileProperty, String primaryKeyPolicy,
			String sequenceCode, String[] foreignKeys) {
		entityName = entityName;
		entityPackage = entityPackage;
		tableName = tableName;
		ftlDescription = ftlDescription;
		createFileProperty = createFileProperty;
		primaryKeyPolicy = primaryKeyPolicy;
		sequenceCode = sequenceCode;
		foreignKeys = foreignKeys;
	}

	public Map<String, Object> execute() {
		Map data = new HashMap();

		data.put("bussiPackage", CodeResourceUtil.bussiPackage);

		data.put("entityPackage", entityPackage);

		data.put("entityName", entityName);

		data.put("tableName", tableName);

		data.put("ftl_description", ftlDescription);

		data.put(FtlDef.SB_TABLE_ID, CodeResourceUtil.SB_GENERATE_TABLE_ID);

		data.put(FtlDef.SB_PRIMARY_KEY_POLICY, primaryKeyPolicy);
		data.put(FtlDef.SB_SEQUENCE_CODE, sequenceCode);

		data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));

		data.put("foreignKeys", foreignKeys);

		data.put(FtlDef.FIELD_REQUIRED_NAME,
				Integer.valueOf((StringUtils.isNotEmpty(CodeResourceUtil.SB_UI_FIELD_REQUIRED_NUM)) ? Integer.parseInt(CodeResourceUtil.SB_UI_FIELD_REQUIRED_NUM) : -1));

		data.put(FtlDef.SEARCH_FIELD_NUM,
				Integer.valueOf((StringUtils.isNotEmpty(CodeResourceUtil.SB_UI_FIELD_SEARCH_NUM)) ? Integer.parseInt(CodeResourceUtil.SB_UI_FIELD_SEARCH_NUM) : -1));

		data.put(FtlDef.FIELD_ROW_NAME, Integer.valueOf(FIELD_ROW_NUM));
		try {
			this.columns = this.dbFiledUtil.readTableColumn(tableName);
			data.put("columns", this.columns);

			this.originalColumns = this.dbFiledUtil.readOriginalTableColumn(tableName);
			data.put("originalColumns", this.originalColumns);

			for (Columnt c : this.originalColumns) {
				if (c.getFieldName().toLowerCase().equals(CodeResourceUtil.SB_GENERATE_TABLE_ID.toLowerCase()))
					data.put("primary_key_type", c.getFieldType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		long serialVersionUID = NonceUtils.randomLong() + NonceUtils.currentMills();
		data.put("serialVersionUID", String.valueOf(serialVersionUID));
		return data;
	}

	public void generateToFile() {
		log.info("----sb---Code----Generation----[����ģ��:" + tableName + "]------- ����С�����");

		CodeFactory codeFactory = new CodeFactory();
		codeFactory.setCallBack(new CodeGenerate());

		if (createFileProperty.isJspFlag()) {
			if ("03".equals(createFileProperty.getJspMode())) {
				codeFactory.invoke("onetomany/jspSubTemplate.ftl", "jspList");
			} else {
				if ("01".equals(createFileProperty.getJspMode())) {
					codeFactory.invoke("jspTableTemplate.ftl", "jsp");
				}
				if ("02".equals(createFileProperty.getJspMode())) {
					codeFactory.invoke("jspDivTemplate.ftl", "jsp");
				}
				codeFactory.invoke("jspListTemplate.ftl", "jspList");
			}
		}
		if (createFileProperty.isServiceImplFlag()) {
			codeFactory.invoke("serviceImplTemplate.ftl", "serviceImpl");
		}
		if (createFileProperty.isServiceIFlag()) {
			codeFactory.invoke("serviceITemplate.ftl", "service");
		}
		if (createFileProperty.isActionFlag()) {
			codeFactory.invoke("controllerTemplate.ftl", "controller");
		}
		if (createFileProperty.isEntityFlag()) {
			codeFactory.invoke("entityTemplate.ftl", "entity");
		}
		log.info("----sb----Code----Generation-----[����ģ�ͣ�" + tableName + "]------ �����ɡ�����");
	}

	public static void main(String[] args) {
		System.out.println("----sb--------- Code------------- Generation -----[����ģ��]------- ����С�����");
		new CodeGenerate().generateToFile();
		System.out.println("----sb--------- Code------------- Generation -----[����ģ��]------- �����ɡ�����");
	}
}