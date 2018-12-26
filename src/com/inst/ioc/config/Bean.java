package com.inst.ioc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * bean实例信息
 * 
 * @author xxl
 * @version 1.0
 * @createDate 2018年12月25日 下午2:42:02
 *
 */
public class Bean {
	private String name; // 名称
	private String className; // 类名
	private String scope = "singleton"; // 作用域默认采用singleton
										// (Singleton: Spring容器只存在一个共享的bean实例，默认的配置。
										// Prototype: 每次对bean的请求都会创建一个新的bean实例)
	private List<Property> properties = new ArrayList<Property>(); // 所有属性信息

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}
