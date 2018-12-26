package com.inst.ioc.main;

/**
 * bean工厂类
 * 
 * @author xxl
 * @version 1.0
 * @createDate 2018年12月25日 下午2:27:40
 *
 */
public interface BeanFactory {
	Object getBean(String beanName);
}
