package com.supr.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declerations
	
	// For Controller Package
	@Pointcut("execution(* com.supr.spring.controller.*.*(..))")
	private void forControllerPackage() {}
	// for Dao Package
	@Pointcut("execution(* com.supr.dao.*.*(..))")
	private void forDaoPackage() {}
	// For Service Package
	@Pointcut("execution(* com.supr.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
	private void forAppFlow() {}
	
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before (JoinPoint theJoinpoint) {
		
		// display the methods being called
		String theMethod = theJoinpoint.getSignature().toShortString();
		System.out.println("--> @Before: calling Method: "+ theMethod);
		
		// display the arguments being passed to the method
			// get the arguments
			Object[] args = theJoinpoint.getArgs();
			
			// loop through and display the arguments
			for (Object tempArg: args) {
				System.out.println("==>> Arguments being passed: "+ tempArg);
			}
		
	}
	
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult")
	public void afterReturning(JoinPoint theJoinpoint, Object theResult) {
		
		// display method that we are returning from
		String theMethod = theJoinpoint.getSignature().toShortString();
		System.out.println("===>> @AfterReturning: from Method: "+ theMethod);
		
		// display the returned data
		System.out.println("===>> Returning Result: "+ theResult);
		
	}
	
	
	// add after returning aspect
}
