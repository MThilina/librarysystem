package com.collebera.librarysystem.aspect;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceLoginAspect {
    private static final Logger logger = LogManager.getLogger(ServiceLoginAspect.class);

    @AfterThrowing(pointcut = "execution(*  com.collebera.librarysystem.service..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Exception error) {
        // Get the class name
        String className = joinPoint.getTarget().getClass().getSimpleName();
        // Log the exception with class name, method name, and exception message
        logger.error("Exception in in class: {} method: {} with cause: {} and exception message: {}. Method arguments: {}",
                className,
                joinPoint.getSignature().getName(),
                error.getCause() != null ? error.getCause() : "NULL",
                error.getMessage(),
                Arrays.toString(joinPoint.getArgs()));
    }
}
