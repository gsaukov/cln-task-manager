package com.cln.challenge.services.graphtask.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order
@Component
public class GraphTaskLockProxy {

    @Around("@annotation(com.cln.challenge.services.graphtask.lock.GraphTaskLock)")
    public Object lockTask(ProceedingJoinPoint pjp) throws Throwable {
        return null;
    }

}
