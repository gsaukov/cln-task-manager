package com.cln.challenge.services.plantgraphtask.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order
@Component
public class PlantGraphTaskLockProxy {

    @Around("@annotation(com.cln.challenge.services.plantgraphtask.lock.PlantGraphTaskLock)")
    public Object lockTask(ProceedingJoinPoint pjp) throws Throwable {
        return null;
    }

}
