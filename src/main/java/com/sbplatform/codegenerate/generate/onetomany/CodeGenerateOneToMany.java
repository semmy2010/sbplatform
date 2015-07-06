/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.sbplatform.codegenerate.generate.onetomany;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sbplatform.codegenerate.database.SbReadTable;
import com.sbplatform.codegenerate.generate.CodeGenerate;
import com.sbplatform.codegenerate.generate.ICallBack;
import com.sbplatform.codegenerate.pojo.Columnt;
import com.sbplatform.codegenerate.pojo.CreateFileProperty;
import com.sbplatform.codegenerate.pojo.onetomany.CodeParamEntity;
import com.sbplatform.codegenerate.pojo.onetomany.SubTableEntity;
import com.sbplatform.codegenerate.util.CodeDateUtils;
import com.sbplatform.codegenerate.util.CodeResourceUtil;
import com.sbplatform.codegenerate.util.NonceUtils;
import com.sbplatform.codegenerate.util.def.FtlDef;
import com.sbplatform.codegenerate.util.def.SbKey;

public class CodeGenerateOneToMany implements ICallBack {
	private static final Log log = LogFactory.getLog(CodeGenerateOneToMany.class);

	private static String entityPackage = "test";
	private static String entityName = "Person";
	private static String tableName = "person";
	private static String ftlDescription = "�û�";
	private static String primaryKeyPolicy = "uuid";
	private static String sequenceCode = "";
	private static String ftl_mode;
	public static String FTL_MODE_A = "A";
	public static String FTL_MODE_B = "B";

	private static List<SubTableEntity> subTabParam = new ArrayList();
	private static CreateFileProperty createFileProperty = new CreateFileProperty();
	public static int FIELD_ROW_NUM = 4;

	private List<Columnt> mainColums = new ArrayList();

	private List<Columnt> originalColumns = new ArrayList();

	private List<SubTableEntity> subTabFtl = new ArrayList();

	private static SbReadTable dbFiledUtil = new SbReadTable();

	static {
		createFileProperty.setActionFlag(true);
		createFileProperty.setServiceIFlag(true);
		createFileProperty.setJspFlag(true);
		createFileProperty.setServiceImplFlag(true);
		createFileProperty.setPageFlag(true);
		createFileProperty.setEntityFlag(true);
	}

	public CodeGenerateOneToMany() {
	}

	public CodeGenerateOneToMany(String entityPackage, String entityName, String tableName, List<SubTableEntity> subTabParam, String ftlDescription, CreateFileProperty createFileProperty,
			String primaryKeyPolicy, String sequenceCode) {
		entityName = entityName;
		entityPackage = entityPackage;
		tableName = tableName;
		ftlDescription = ftlDescription;
		createFileProperty = createFileProperty;
		subTabParam = subTabParam;
		primaryKeyPolicy = (StringUtils.isNotBlank(primaryKeyPolicy)) ? primaryKeyPolicy : "uuid";
		sequenceCode = sequenceCode;
	}

	public CodeGenerateOneToMany(CodeParamEntity codeParamEntity) {
		entityName = codeParamEntity.getEntityName();
		entityPackage = codeParamEntity.getEntityPackage();
		tableName = codeParamEntity.getTableName();
		ftlDescription = codeParamEntity.getFtlDescription();
		subTabParam = codeParamEntity.getSubTabParam();
		ftl_mode = codeParamEntity.getFtl_mode();
		primaryKeyPolicy = (StringUtils.isNotBlank(codeParamEntity.getPrimaryKeyPolicy())) ? codeParamEntity.getPrimaryKeyPolicy() : "uuid";
		sequenceCode = codeParamEntity.getSequenceCode();
	}

	public Map<String, Object> execute() {
		Map data = new HashMap();

		data.put("bussiPackage", CodeResourceUtil.bussiPackage);

		data.put("entityPackage", entityPackage);

		data.put("entityName", entityName);

		data.put("tableName", tableName);

		data.put("ftl_description", ftlDescription);

		data.put("sb_table_id", CodeResourceUtil.SB_GENERATE_TABLE_ID);

		data.put(FtlDef.SB_PRIMARY_KEY_POLICY, primaryKeyPolicy);
		data.put(FtlDef.SB_SEQUENCE_CODE, sequenceCode);
		data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));

		data.put(FtlDef.FIELD_REQUIRED_NAME,
				Integer.valueOf((StringUtils.isNotEmpty(CodeResourceUtil.SB_UI_FIELD_REQUIRED_NUM)) ? Integer.parseInt(CodeResourceUtil.SB_UI_FIELD_REQUIRED_NUM) : -1));

		data.put(FtlDef.SEARCH_FIELD_NUM,
				Integer.valueOf((StringUtils.isNotEmpty(CodeResourceUtil.SB_UI_FIELD_SEARCH_NUM)) ? Integer.parseInt(CodeResourceUtil.SB_UI_FIELD_SEARCH_NUM) : -1));

		data.put(FtlDef.FIELD_ROW_NAME, Integer.valueOf(FIELD_ROW_NUM));
		try {
			this.mainColums = dbFiledUtil.readTableColumn(tableName);
			data.put("mainColums", this.mainColums);
			data.put("columns", this.mainColums);

			this.originalColumns = dbFiledUtil.readOriginalTableColumn(tableName);
			data.put("originalColumns", this.originalColumns);

			for (Columnt c : this.originalColumns) {
				if (c.getFieldName().toLowerCase().equals(CodeResourceUtil.SB_GENERATE_TABLE_ID.toLowerCase())) {
					data.put("primary_key_type", c.getFieldType());
				}
			}

			this.subTabFtl.clear();
			for (SubTableEntity subTableEntity : subTabParam) {
				SubTableEntity po = new SubTableEntity();
				List subColum = dbFiledUtil.readTableColumn(subTableEntity.getTableName());
				po.setSubColums(subColum);
				po.setEntityName(subTableEntity.getEntityName());
				po.setFtlDescription(subTableEntity.getFtlDescription());
				po.setTableName(subTableEntity.getTableName());
				po.setEntityPackage(subTableEntity.getEntityPackage());

				String[] fkeys = subTableEntity.getForeignKeys();
				List foreignKeys = new ArrayList();
				for (String key : fkeys) {
					if (CodeResourceUtil.SB_FILED_CONVERT) {
						foreignKeys.add(SbReadTable.formatFieldCapital(key));
					} else {
						String keyStr = key.toLowerCase();
						String field = keyStr.substring(0, 1).toUpperCase() + keyStr.substring(1);
						foreignKeys.add(field);
					}
				}

				po.setForeignKeys((String[]) foreignKeys.toArray(new String[0]));
				this.subTabFtl.add(po);
			}

			data.put("subTab", this.subTabFtl);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		long serialVersionUID = NonceUtils.randomLong() + NonceUtils.currentMills();
		data.put("serialVersionUID", String.valueOf(serialVersionUID));
		return data;
	}

	public void generateToFile() {
		CodeFactoryOneToMany codeFactoryOneToMany = new CodeFactoryOneToMany();
		codeFactoryOneToMany.setCallBack(new CodeGenerateOneToMany());

		if (createFileProperty.isJspFlag()) {
			codeFactoryOneToMany.invoke("onetomany/jspListTemplate.ftl", "jspList");
			codeFactoryOneToMany.invoke("onetomany/jspTemplate.ftl", "jsp");
		}

		if (createFileProperty.isServiceImplFlag()) {
			codeFactoryOneToMany.invoke("onetomany/serviceImplTemplate.ftl", "serviceImpl");
		}
		if (createFileProperty.isServiceIFlag()) {
			codeFactoryOneToMany.invoke("onetomany/serviceITemplate.ftl", "service");
		}
		if (createFileProperty.isActionFlag()) {
			codeFactoryOneToMany.invoke("onetomany/controllerTemplate.ftl", "controller");
		}
		if (createFileProperty.isEntityFlag()) {
			codeFactoryOneToMany.invoke("onetomany/entityTemplate.ftl", "entity");
		}
		if (!(createFileProperty.isPageFlag()))
			return;
		codeFactoryOneToMany.invoke("onetomany/pageTemplate.ftl", "page");
	}

	public static void main(String[] args) {
		List subTabParamIn = new ArrayList();

		SubTableEntity po = new SubTableEntity();

		po.setTableName("sb_order_custom");

		po.setEntityName("OrderCustom");

		po.setEntityPackage("order");

		po.setFtlDescription("�����ͻ���ϸ");

		po.setPrimaryKeyPolicy(SbKey.UUID);
		po.setSequenceCode(null);

		po.setForeignKeys(new String[] { "GORDER_OBID", "GO_ORDER_CODE" });
		subTabParamIn.add(po);

		SubTableEntity po2 = new SubTableEntity();
		po2.setTableName("sb_order_product");
		po2.setEntityName("OrderProduct");
		po2.setEntityPackage("order");
		po2.setFtlDescription("������Ʒ��ϸ");
		po2.setForeignKeys(new String[] { "GORDER_OBID", "GO_ORDER_CODE" });

		po2.setPrimaryKeyPolicy(SbKey.UUID);
		po2.setSequenceCode(null);
		subTabParamIn.add(po2);

		CodeParamEntity codeParamEntityIn = new CodeParamEntity();
		codeParamEntityIn.setTableName("sb_order_main");
		codeParamEntityIn.setEntityName("OrderMain");
		codeParamEntityIn.setEntityPackage("order");
		codeParamEntityIn.setFtlDescription("����̧ͷ");
		codeParamEntityIn.setFtl_mode(FTL_MODE_B);

		codeParamEntityIn.setPrimaryKeyPolicy(SbKey.UUID);

		codeParamEntityIn.setSequenceCode(null);
		codeParamEntityIn.setSubTabParam(subTabParamIn);

		oneToManyCreate(subTabParamIn, codeParamEntityIn);
	}

	public static void oneToManyCreate(List<SubTableEntity> subTabParamIn, CodeParamEntity codeParamEntityIn) {
		log.info("----sb----Code-----Generation-----[һ�Զ����ģ�ͣ�" + codeParamEntityIn.getTableName() + "]------- ����С�����");

		CreateFileProperty subFileProperty = new CreateFileProperty();
		subFileProperty.setActionFlag(false);
		subFileProperty.setServiceIFlag(false);
		subFileProperty.setJspFlag(true);
		subFileProperty.setServiceImplFlag(false);
		subFileProperty.setPageFlag(false);
		subFileProperty.setEntityFlag(true);
		subFileProperty.setJspMode("03");

		for (SubTableEntity sub : subTabParamIn) {
			String[] fkeys = sub.getForeignKeys();
			List foreignKeys = new ArrayList();
			for (String key : fkeys) {
				if (CodeResourceUtil.SB_FILED_CONVERT) {
					foreignKeys.add(SbReadTable.formatFieldCapital(key));
				} else {
					String keyStr = key.toLowerCase();
					String field = keyStr.substring(0, 1).toUpperCase() + keyStr.substring(1);
					foreignKeys.add(field);
				}

			}

			new CodeGenerate(sub.getEntityPackage(), sub.getEntityName(), sub.getTableName(), sub.getFtlDescription(), subFileProperty,
					(StringUtils.isNotBlank(sub.getPrimaryKeyPolicy())) ? sub.getPrimaryKeyPolicy() : "uuid", sub.getSequenceCode(), (String[]) foreignKeys.toArray(new String[0]))
					.generateToFile();
		}

		new CodeGenerateOneToMany(codeParamEntityIn).generateToFile();
		log.info("----sb----Code----Generation------[һ�Զ����ģ�ͣ�" + codeParamEntityIn.getTableName() + "]------ �����ɡ�����");
	}
}