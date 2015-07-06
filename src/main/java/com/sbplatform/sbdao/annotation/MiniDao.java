package com.sbplatform.sbdao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @description:MiniDao   
 * @author 黄世民
 * @mail zhangdaiscott@163.com
 * @category www.sb.org
 * @date 20130817
 * @version V1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MiniDao{
}
