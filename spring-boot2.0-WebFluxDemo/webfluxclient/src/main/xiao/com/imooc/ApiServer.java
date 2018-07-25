package com.imooc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务器相关信息
 * 
 * @author 晓风轻
 *
 */
@Target(ElementType.TYPE)//告诉是什么类型
@Retention(RetentionPolicy.RUNTIME)//告诉类如何保存
public @interface ApiServer {
	//默认类型这是一个方法不是属性
	String value() default "";
}
