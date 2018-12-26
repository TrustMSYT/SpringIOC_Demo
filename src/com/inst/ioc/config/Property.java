package com.inst.ioc.config;

/**
 * 属性信息
 * @author xxl
 * @version 1.0
 * @createDate 2018年12月25日 下午2:41:30
 *
 */
public class Property {
	private String name; // 属性名称
	private String value; // 属性值
	private String ref; // 属性连接
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
}
