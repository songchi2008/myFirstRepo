/**
 * DataSourceAspect.java by Ckex.zha
 */

package com.mljr.spider.dao.dynamic.datasource;

import java.lang.reflect.Method;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Ckex zha </br>
 *         2016年11月7日,下午1:37:24
 *
 */
@Component
@Aspect
public class DataSourceAspect {

	protected static transient Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

	@Pointcut("execution(* com.mljr.spider.dao.impl.*.*(..))")
	public void aspect() {
	}

	@Before("aspect()")
	public void before(JoinPoint point) {
		Object target = point.getTarget();
		String method = point.getSignature().getName();

		Class<?>[] classz = target.getClass().getInterfaces();

		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
		try {
			Method mthd = classz[0].getMethod(method, parameterTypes);
			if (mthd != null && mthd.isAnnotationPresent(DataSource.class)) {
				DataSource data = mthd.getAnnotation(DataSource.class);
				DynamicDataSourceHolder.putDataSource(data.value());
				if (logger.isDebugEnabled()) {
					logger.debug("----------------------" + data.value());
				}
			}
		} catch (Exception ex) {
			logger.error(ExceptionUtils.getStackTrace(ex));
		}
	}

}
