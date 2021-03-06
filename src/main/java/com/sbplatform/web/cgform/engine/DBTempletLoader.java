package com.sbplatform.web.cgform.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.sbplatform.web.cgform.common.CgAutoListConstant;
import com.sbplatform.web.cgform.common.FormHtmlUtil;
import com.sbplatform.web.cgform.entity.config.CgFormFieldEntity;
import com.sbplatform.web.cgform.entity.config.CgFormHeadEntity;
import com.sbplatform.web.cgform.service.cgformftl.CgformFtlServiceI;
import com.sbplatform.web.cgform.service.config.CgFormFieldServiceI;

import freemarker.cache.TemplateLoader;

/**
 * @ClassName: DBTempletLoader
 * @Description: (模板加载处理类)
 * @author zhoujunfeng
 */
@Component("templetLoader")
public class DBTempletLoader implements TemplateLoader {
	
	public static final String TEMPLET = "com/sbplatform/web/cgform/engine/jform.ftl";
	public static final String TEMPLET_ONE_MANY = "com/sbplatform/web/cgform/engine/jformunion.ftl";
	//生成输入框匹配内容
	private final static String regEx_attr = "\\#\\{([a-zA-Z_0-9]+)\\}";
	
	@Autowired
	private CgformFtlServiceI cgformFtlService;
	
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	
	
    public Object findTemplateSource(String name) throws IOException {
    	name = name.toLowerCase();
		name = name.replace("_zh_cn", "");
		name = name.replace("_en_us", "");
    	com.sbplatform.core.util.LogUtil.info("table name----------->"+name);
        Object obj = getObject(name);
        return obj;
    }

    
    public long getLastModified(Object templateSource) {
        return 0;
    }

    
    public Reader getReader(Object templateSource, String encoding) throws IOException {
    	Reader br = new StringReader("");
    	if (templateSource instanceof InputStreamReader) {
			br = new BufferedReader((InputStreamReader)templateSource);
		} else {
			StringBuilder str = (StringBuilder)templateSource;
			br = new StringReader(str.toString()); 
		}
    	return br;
    }
    
    private Object getObject(String name) throws IOException {
    	PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
    	if(name.lastIndexOf(".ftl")==-1){//判断是否为include的模板
	    	//如果是主表直接走一对多模板
	    	CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByTableName(name);
	    	if(head==null)return null;
			if(head.getJformType()==CgAutoListConstant.JFORM_TYPE_MAIN_TALBE){
				Resource[] resources = patternResolver.getResources(TEMPLET_ONE_MANY);
	    		InputStreamReader inputStreamReader =null;
	    		if (resources != null && resources.length > 0) {  
	    			 inputStreamReader = new InputStreamReader(resources[0].getInputStream(),"UTF-8");
	    		}
	    		return inputStreamReader;
			}
	    	//1、根据table name 查询cgformftl 有则获取模板内容
	    	//2、没有cgformftl 则查询cgformfield 根据cgformfield生成模板
	    	Map<String,Object> cgformFtlEntity = cgformFtlService.getCgformFtlByTableName(name);
	    	if(cgformFtlEntity!=null){
	    		String content = (String) (cgformFtlEntity.get("ftl_content")==null?"":cgformFtlEntity.get("ftl_content"));
	    		content = initFormHtml( content, name);
	    		com.sbplatform.core.util.LogUtil.info(content);
	    		return new StringBuilder(content);
	    	}else{
	    		Resource[] resources = patternResolver.getResources(TEMPLET);
	    		InputStreamReader inputStreamReader =null;
	    		if (resources != null && resources.length > 0) {  
	    			 inputStreamReader = new InputStreamReader(resources[0].getInputStream(),"UTF-8");
	    		}
	    		return inputStreamReader;
	    	}
    	}else{
    		Resource[] resources = patternResolver.getResources(name);
    		InputStreamReader inputStreamReader =null;
    		if (resources != null && resources.length > 0) {  
    			 inputStreamReader = new InputStreamReader(resources[0].getInputStream(),"UTF-8");
    		}
    		return inputStreamReader;
    	}
    }
    
    
    public void closeTemplateSource(Object templateSource) throws IOException {
      
    }

    /**
     * 获取jform.ftl的路径
     */
//    private  String getFilePath(String fileName){
//		String path = DBTempletLoader.class.getResource("").toString();
//		if (path != null)
//		{
//			String systemType = System.getProperty("os.name");
//			if(systemType.toLowerCase().indexOf("window")!=-1){
//				path = path.substring(6, path.indexOf("WEB-INF") + 8)+"classes/sb/cgform/engine/";//window 
//			}else{
//				path = path.substring(5, path.indexOf("WEB-INF") + 8)+"classes/sb/cgform/engine/";//linux
//			}
//			try {
//				path = java.net.URLDecoder.decode(path,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			com.sbplatform.core.util.LogUtil.info("current path :" + path);
//		}
//		return (path + fileName);
//	}
    
    
    private String initFormHtml(String htmlStr,String tableName){
    	Pattern pattern;   
	    Matcher matcher;   
    	try {
    		//根据formid获取所有的CgFormFieldEntity
			Map<String,CgFormFieldEntity> fieldMap = cgFormFieldService.getAllCgFormFieldByTableName(tableName);
			//根据formid获取所有的CgFormFieldEntity
			List<CgFormFieldEntity> hiddenFielList = cgFormFieldService.getHiddenCgFormFieldByTableName(tableName);
			
			//添加input语句
			pattern = Pattern.compile(regEx_attr,Pattern.CASE_INSENSITIVE);   
			matcher = pattern.matcher(htmlStr);   

			StringBuffer sb = new StringBuffer(); 
			String thStr = "";
			String inputStr = "";

			boolean result = matcher.find(); 
			while(result) {
				thStr = matcher.group(1);
				inputStr = "";
				if("jform_hidden_field".equals(thStr)){
					inputStr = getHiddenForm(hiddenFielList);
				}else{
					if(fieldMap.get(thStr)!=null){
						CgFormFieldEntity cgFormFieldEntity = fieldMap.get(thStr);
						if("Y".equals(cgFormFieldEntity.getIsShow())){
							inputStr = FormHtmlUtil.getFormHTML(cgFormFieldEntity);
							inputStr +="<span class=\"Validform_checktip\">&nbsp;</span>";
						}
					}
				}
				matcher.appendReplacement(sb, inputStr); 
				result = matcher.find(); 
			} 
			matcher.appendTail(sb); 
			htmlStr = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return htmlStr;
    }
    
    private String getHiddenForm(List<CgFormFieldEntity> hiddenFielList){
    	StringBuffer html = new StringBuffer(""); 
    	if(hiddenFielList!=null&&hiddenFielList.size()>0){
    		for(CgFormFieldEntity cgFormFieldEntity:hiddenFielList){
    		      html.append("<input type=\"hidden\" ");
    		      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
    		      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
    		      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
    		      html.append("\\/>\r\n");
    		}
    	}
    	return html.toString();
    }

}
