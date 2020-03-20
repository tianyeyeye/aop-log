package com.example.logdemo.log;

import java.lang.annotation.*;

/**
 * @author pengZheng
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ControllerLog {

    /**
     * 操作描述 业务名称business
     *
     * @return
     */
    String description() default "";

    /**
     * 功能页面
     *
     * @return
     */
    OperateModule module();

    /**
     * 操作类型
     *
     * @return
     */
    OperateType opType();

}
