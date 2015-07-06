package com.sbplatform.core.jackson;

import java.lang.reflect.Method;

/**
 * 过滤属性处理器
 *
 */
public interface FilterPropertyHandler {
    /**
     * 通过传入调用方法和返回值过滤属性 <br>
     * 2013-10-21 上午10:16:27
     *
     * @param method 调用方法
     * @param object 方法返回值
     * @return 过滤属性后的值
     */
    public Object filterProperties(Method method, Object object);
}
