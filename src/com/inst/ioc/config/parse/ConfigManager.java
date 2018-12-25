package com.inst.ioc.config.parse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.inst.ioc.config.Bean;
import com.inst.ioc.config.Property;

public class ConfigManager {
	private static Map<String,Bean> map = new HashMap<String,Bean>();
	
	/**
	 * 读取配置文件，返回结果集合
	 * 返回Map集合便于注入,key是每个Bean的name属性,value是对应的那个Bean对象
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Bean> getConfig(String path){
		 /*dom4j实现
         *  1.创建解析器
         *  2.加载配置文件,得到document对象
         *  3.定义xpath表达式,取出所有Bean元素
         *  4.对Bean元素继续遍历
         *      4.1将Bean元素的name/class属性封装到bean类属性中
         *      4.2获得bean下的所有property子元素
         *      4.3将属性name/value/ref分装到类Property类中
         *  5.将property对象封装到bean对象中
         *  6.将bean对象封装到Map集合中,返回map 
         */
		// 创建解析器
		SAXReader reader = new SAXReader();
		InputStream is = ConfigManager.class.getResourceAsStream(path);
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("请检查您的xml配置是否正确");
		}
		
		String xPath = "//bean";
		List<Element> list = doc.selectNodes(xPath);
		if(null != list) {
			// 将元素的name/class属性封装到bean类属性中
			for (Element bean : list) {
				Bean b = new Bean();
				String name = bean.attributeValue("name");
				String clazz = bean.attributeValue("class");
				String scope = bean.attributeValue("scope");
				b.setName(name);
				b.setClassName(clazz);
				if(null != scope) {
					b.setScope(scope);
				}
				
				// 获取bean下的所有property子元素
				List<Element> children = bean.elements("property");
				if(null != children) {
					for (Element child : children) {
						Property prop = new Property();
						String pName = child.attributeValue("name");
						String pValue = child.attributeValue("value");
						String pRef = child.attributeValue("ref");
						prop.setName(pName);
						prop.setValue(pValue);
						prop.setRef(pRef);
						b.getProperties().add(prop);
					}
				}
				// 将bean对象封装到Map集合中，返回map
				map.put(name, b);
			}
		}
		return map;
	}
}
