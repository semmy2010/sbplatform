package com.sbplatform.core.util;

import java.util.Comparator;

import com.sbplatform.web.system.pojo.base.MenuFunction;


/**
* @ClassName: SetListSort 
* @Description: TODO(int比较器) 
* @author  黄世民 
* @date 2013-1-31 下午06:19:03 
*
 */
public class SetListSort implements Comparator {
	/**
	 * 菜单排序比较器
	 */
	public int compare(Object o1, Object o2) {
		MenuFunction c1 = (MenuFunction) o1;
		MenuFunction c2 = (MenuFunction) o2;
		if (c1.getSort() != null && c2.getSort() != null) {
			int c1order = oConvertUtils.getInt(c1.getSort().substring(c1.getSort().indexOf("fun")+3));
			int c2order = oConvertUtils.getInt(c2.getSort().substring(c2.getSort().indexOf("fun"))+3);
			if (c1order > c2order) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return 1;
		}

	}
}