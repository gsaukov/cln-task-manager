package com.cln.challenge.services.plantgraphtask.lock;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PlantGraphTaskLock {
    /** Proxy annotation
     * @see com.cln.challenge.services.plantgraphtask.lock.PlantGraphTaskLockProxy
     */

    @AliasFor("name")
    String[] value() default {};

    @AliasFor("value")
    String[] name() default {};

}
