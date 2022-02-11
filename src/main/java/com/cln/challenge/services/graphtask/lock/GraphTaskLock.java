package com.cln.challenge.services.graphtask.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface GraphTaskLock {
    /** Proxy annotation
     * @see com.cln.challenge.services.graphtask.lock.GraphTaskLockProxy
     */
}
