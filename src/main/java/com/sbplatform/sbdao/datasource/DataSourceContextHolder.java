package com.sbplatform.sbdao.datasource;
/**
 *类名：DataSourceContextHolder.java
 *功能：获得和设置上下文环境的类，主要负责改变上下文数据源的名称
 * DataSourceContextHolder
 * @description:DataSourceContextHolder
 * @author 黄世民
 * @mail zhangdaiscott@163.com
 * @category www.sb.org
 * @date 20130817
 * @version V1.0
 */
public class DataSourceContextHolder {

	private static final ThreadLocal contextHolder=new ThreadLocal();
	
	public static void setDataSourceType(DataSourceType dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
	public static DataSourceType getDataSourceType(){
		return (DataSourceType) contextHolder.get();
	}
	
	public static void clearDataSourceType(){
		contextHolder.remove();
	}
	
}
