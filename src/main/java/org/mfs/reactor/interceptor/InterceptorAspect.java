package org.mfs.reactor.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class InterceptorAspect {

	@Before("execution(* org.mfs.reactor.InterceptorReact.*(..))")
	public Object auditMethod(ProceedingJoinPoint jp) throws Throwable {
		jp.getSignature().getName();
		Object obj = jp.proceed();
		return obj;
	}
}