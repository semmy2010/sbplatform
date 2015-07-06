package com.sbplatform.core.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sbplatform.core.jackson.FilterPropertyHandler;
import com.sbplatform.core.jackson.impl.JavassistFilterPropertyHandler;

@Aspect
@Component
public class IgnorePropertyAspect {
	public static final Logger LOGGER = Logger.getLogger(IgnorePropertyAspect.class);

	@Pointcut("execution(* com.sbplatform.web.*.controller..*.*(..))")
	private void anyMethod() {

	}

	@Around("anyMethod()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();// 参数
		Object returnVal = pjp.proceed(); // 返回源结果
		try {
			FilterPropertyHandler filterPropertyHandler = new JavassistFilterPropertyHandler(true);
			Method method = ((MethodSignature) pjp.getSignature()).getMethod();
			returnVal = filterPropertyHandler.filterProperties(method, returnVal);
		} catch (Exception e) {
			LOGGER.error(e);
			e.printStackTrace();
		}

		return returnVal;
	}

	@AfterThrowing(pointcut = "anyMethod()", throwing = "e")
	public void doAfterThrowing(Exception e) {
		System.out.println(" -------- AfterThrowing -------- ");
	}
}
