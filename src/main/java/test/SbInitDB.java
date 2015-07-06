package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbplatform.web.cgform.entity.config.CgFormFieldEntity;
import com.sbplatform.web.cgform.entity.config.CgFormHeadEntity;
import com.sbplatform.web.system.pojo.base.Attachment;
import com.sbplatform.web.system.pojo.base.BaseUser;
import com.sbplatform.web.system.pojo.base.Department;
import com.sbplatform.web.system.pojo.base.MenuFunction;
import com.sbplatform.web.system.pojo.base.Log;
import com.sbplatform.web.system.pojo.base.Role;
import com.sbplatform.web.system.pojo.base.Type;
import com.sbplatform.web.system.pojo.base.TypeGroup;
import com.sbplatform.web.system.pojo.base.User;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/** 
 * @Description 
 * @ClassName: SbInitDB
 * @author tanghan
 * @date 2013-7-19 下午04:24:51  
 */

public class SbInitDB {

	private static Connection con = null;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (con == null) {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sb", "root", "root");
		}
		return con;
	}

	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
		String sql1 = "select * from s_t_attachment";
		String sql2 = "select * from s_t_base_user";
		String sql3 = "select * from s_t_department";
		String sql4 = "select * from s_t_role";
		String sql5 = "select * from s_t_user";
		String sql6 = "select * from s_t_type_group";
		String sql7 = "select * from s_t_menu_function";
		String sql8 = "select * from s_t_type";
		String sql9 = "select * from s_t_log limit 100";
		String sql10 = "select * from cgform_t_field where table_id ="; //此处由于需要更具cgform_t_head生成，故只能单个生成
		String sql11 = "select * from cgform_t_head ";
		Statement st = null;
		ResultSet rs = null;
		try {
			cfg.setDirectoryForTemplateLoading(new File("E:/Workspace-sb/sb-v3-simple-new/src/test"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate("init.ftl", "UTF-8");
			con = getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql1);
			int i = 1;
			Map<String, Object> root = new HashMap<String, Object>();
			List att = new ArrayList();
			while (rs.next()) {
				Attachment attachment = new Attachment();
				attachment.setId(i + "");
				attachment.setAttachmentName(rs.getString("ATTACHMENT_NAME"));
				attachment.setRealPath(rs.getString("ATTACHMENT_REAL_PATH"));
				attachment.setSwfPath(rs.getString("SWF_PATH"));
				attachment.setExtendName(rs.getString("EXTEND_NAME"));
				att.add(attachment);
				i++;
			}
			root.put("animals", att);

			rs = st.executeQuery(sql2);
			i = 1;
			List user = new ArrayList();
			while (rs.next()) {
				BaseUser baseUser = new BaseUser();
				baseUser.setId(i + "");
				baseUser.setUserKey(rs.getString("userkey"));
				baseUser.setStatus(rs.getShort("status"));
				baseUser.setRealName(rs.getString("realname"));
				baseUser.setUserName(rs.getString("username"));
				baseUser.setPassword(rs.getString("password"));
				baseUser.setActivitiSync(rs.getShort("activitisync"));
				user.add(baseUser);
				i++;
			}
			root.put("baseuser", user);

			rs = st.executeQuery(sql3);
			List dep = new ArrayList();
			i = 1;
			while (rs.next()) {
				Department department = new Department();
				department.setId(i + "");
				department.setName(rs.getString("name"));
				department.setDescription(rs.getString("description"));
				dep.add(department);
				i++;
			}
			root.put("depart", dep);

			rs = st.executeQuery(sql4);
			List<Role> roles = new ArrayList<Role>();
			i = 1;
			while (rs.next()) {
				com.sbplatform.core.util.LogUtil.info(rs.getString("rolename"));
				Role role = new Role();
				role.setId(i + "");
				role.setRoleName(rs.getString("rolename"));
				role.setRoleCode(rs.getString("rolecode"));
				roles.add(role);
				i++;
			}
			root.put("role", roles);

			rs = st.executeQuery(sql5);
			List<User> susers = new ArrayList<User>();
			i = 1;
			while (rs.next()) {
				User suer = new User();
				suer.setId(i + "");
				suer.setMobilePhone(rs.getString("mobilePhone"));
				suer.setOfficePhone(rs.getString("officePhone"));
				suer.setEmail(rs.getString("email"));
				susers.add(suer);
				i++;
			}
			root.put("suser", susers);

			rs = st.executeQuery(sql6);
			List<TypeGroup> typeGroups = new ArrayList<TypeGroup>();
			i = 1;
			while (rs.next()) {
				TypeGroup typeGroup = new TypeGroup();
				typeGroup.setId(i + "");
				typeGroup.setTypeGroupName(rs.getString("type_group_name"));
				typeGroup.setTypeGroupCode(rs.getString("type_group_code"));
				typeGroups.add(typeGroup);
				i++;
			}
			root.put("typeGroups", typeGroups);

			rs = st.executeQuery(sql7);
			List<MenuFunction> functions = new ArrayList<MenuFunction>();
			i = 1;
			while (rs.next()) {
				MenuFunction menuFunction = new MenuFunction();
				menuFunction.setId(i + "");
				menuFunction.setName(rs.getString("name"));
				menuFunction.setUrl(rs.getString("url"));
				menuFunction.setLevel(rs.getShort("level"));
				menuFunction.setSort(rs.getString("sort"));
				functions.add(menuFunction);
				i++;
			}
			root.put("menu", functions);

			rs = st.executeQuery(sql8);
			List<Type> types = new ArrayList<Type>();
			i = 1;
			while (rs.next()) {
				Type type = new Type();
				type.setId(i + "");
				type.setName(rs.getString("name"));
				type.setCode(rs.getString("code"));
				types.add(type);
				i++;
			}
			root.put("type", types);

			rs = st.executeQuery(sql9);
			List<Log> logs = new ArrayList<Log>();
			i = 1;
			while (rs.next()) {
				Log log = new Log();
				log.setId(i + "");
				log.setId(i + "");
				log.setLogContent(rs.getString("log_content"));
				log.setLogLevel(rs.getShort("log_level"));
				log.setBroswer(rs.getString("broswer"));
				log.setNote(rs.getString("note"));
				log.setOccurTime(rs.getTimestamp("occur_time"));
				log.setOperateType(rs.getShort("operate_type"));
				logs.add(log);
				i++;
			}
			root.put("log", logs);

			rs = st.executeQuery(sql11);
			List cghead = new ArrayList();
			i = 1;
			while (rs.next()) {
				CgFormHeadEntity head = new CgFormHeadEntity();
				head.setId(i + "");
				head.setTableName(rs.getString("table_name"));
				head.setIsTree(rs.getString("is_tree"));
				head.setIsPagination(rs.getString("is_pagination"));
				head.setQuerymode(rs.getString("queryMode"));
				head.setIsCheckbox(rs.getString("is_checkbox"));
				head.setIsDbSynch(rs.getString("is_dbsynch"));
				head.setContent(rs.getString("content"));
				head.setJformVersion(rs.getString("JFORM_VERSION"));
				head.setJformType(rs.getInt("jform_type"));
				head.setColumns(getCgFormItem(sql10, rs.getString("id")));
				cghead.add(head);
				i++;
			}
			root.put("cghead", cghead);
			//            rs=st.executeQuery(sql10);
			//            List cgfield = new ArrayList();
			//            i=1;
			//            while(rs.next())
			//            {
			//            	CgFormFieldEntity filed = new CgFormFieldEntity();
			//                filed.setFieldName(rs.getString("field_name"));
			//                filed.setLength(rs.getInt("length"));
			//                filed.setType(rs.getString("type"));
			//                filed.setPointLength(rs.getInt("point_length"));
			//                filed.setIsNull(rs.getString("is_null"));
			//                filed.setIsKey(rs.getString("is_key"));
			//                filed.setIsQuery(rs.getString("is_query"));
			//                filed.setIsShow(rs.getString("is_show"));
			//                filed.setShowType(rs.getString("show_type"));
			//                filed.setOrderNum(rs.getInt("order_num"));
			//                filed.setFieldHref(rs.getString("field_href"));
			//                filed.setFieldLength(rs.getInt("field_length"));
			//                filed.setFieldValidType(rs.getString("field_valid_type"));
			//                filed.setQueryMode(rs.getString("query_mode"));
			//                filed.setContent(rs.getString("content"));
			//                filed.setDictTable(rs.getString("dict_table"));
			//                filed.setDictField(rs.getString("dict_field"));
			//                filed.setMainField(rs.getString("main_field"));
			//                filed.setMainTable(rs.getString("main_table"));
			//                cgfield.add(filed);
			//                i++;
			//            }
			//            root.put("cgfield", cgfield);

			Writer out = new OutputStreamWriter(new FileOutputStream("RepairServiceImpl.java"), "UTF-8");
			temp.process(root, out);
			out.flush();
			out.close();
			com.sbplatform.core.util.LogUtil.info("Successfull................");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取表单字段方法
	 * @param sql10
	 * @param cgformhead_id
	 * @return
	 * @throws Exception
	 */
	public static List getCgFormItem(String sql10, String cgformhead_id) throws Exception {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql10 + "'" + cgformhead_id.trim() + "'");
		List cgfield = new ArrayList();
		int i = 1;
		while (rs.next()) {
			CgFormFieldEntity filed = new CgFormFieldEntity();
			filed.setFieldName(rs.getString("field_name"));
			filed.setLength(rs.getInt("length"));
			filed.setType(rs.getString("type"));
			filed.setPointLength(rs.getInt("point_length"));
			filed.setIsNull(rs.getString("is_null"));
			filed.setIsKey(rs.getString("is_key"));
			filed.setIsQuery(rs.getString("is_query"));
			filed.setIsShow(rs.getString("is_show"));
			filed.setShowType(rs.getString("show_type"));
			filed.setOrderNum(rs.getInt("order_num"));
			filed.setFieldHref(rs.getString("field_href"));
			filed.setFieldLength(rs.getInt("field_length"));
			filed.setFieldValidType(rs.getString("field_valid_type"));
			filed.setQueryMode(rs.getString("query_mode"));
			filed.setContent(rs.getString("content"));
			filed.setDictTable(rs.getString("dict_table"));
			filed.setDictField(rs.getString("dict_field"));
			filed.setMainField(rs.getString("main_field"));
			filed.setMainTable(rs.getString("main_table"));
			cgfield.add(filed);
			i++;
		}
		return cgfield;
	}

}
