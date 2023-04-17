package com.cln.challenge.services.plantgraphtask.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order
@Component
public class PlantGraphTaskLockProxy {
    @Around("target(bean) && @annotation(lockConfiguration)")
    public Object lockTask(ProceedingJoinPoint pjp, Object bean, PlantGraphTaskLock lockConfiguration) throws Throwable {
        MethodSignature methodSig = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        String[] parametersName = methodSig.getParameterNames();
        lockConfiguration.value();
        return null;
    }

}
