package com.inst.ioc.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import com.inst.ioc.config.Bean;
import com.inst.ioc.config.Property;
import com.inst.ioc.config.parse.ConfigManager;

/**
 * IOC容器加载类
 * 
 * @author xxl
 * @version 1.0
 * @createDate 2018年12月25日 下午2:27:03
 *
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

	// 获得读取的配置文件中的map信息
	private Map<String, Bean> map;

	// 作为IOC容器使用，放spring的对象
	private Map<String, Object> context = new HashMap<String, Object>();

	/**
	 * 读取配置文件得到需要初始化的Bean信息
	 * 
	 * @author xxl
	 * @version 1.0
	 * @createDate 2018年12月25日 下午2:32:10
	 *
	 * @param path
	 */
	public ClassPathXmlApplicationContext(String path) {
		// 读取配置文件得到需要初始化的Bean信息
		map = ConfigManager.getConfig(path);
		for (Entry<String, Bean> en : map.entrySet()) {
			String beanName = en.getKey();
			Bean bean = en.getValue();

			Object existBean = context.get(beanName);
			// 当容器中为空且bean的scope属性为singleton时（Spring容器只存在一个某个bean的实例）
			if (null == existBean && bean.getScope().equals("singleton")) {
				// 根据字符创建bean对象
				Object beanObj = createBean(bean);
				// 把创建好的bean对象放到map中去
				context.put(beanName, beanObj);
			}
		}
	}

	/**
	 * 通过反射创建bean对象
	 * 
	 * @param bean
	 * @return
	 */
	private Object createBean(Bean bean) {
		Object beanObj = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(bean.getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("没有找到该类" + bean.getClassName());
		}
		try {
			beanObj = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(bean.getClassName() + "没有提供无参构造器");
		}

		if (null != bean.getProperties()) {
			for (Property property : bean.getProperties()) {
				// 注入分两种情况
				// 获取要注入的属性名称
				String name = property.getName();
				String value = property.getValue();
				String ref = property.getRef();
				// 使用BeanUtils工具类完成属性注入，可以自动完成类型转换
				// 如果value不为null，说明有值
				if (null != value) {
					Map<String, String[]> paraMap = new HashMap<String, String[]>();
					paraMap.put(name, new String[] { value });
					try {
						BeanUtils.populate(beanObj, paraMap);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("请检查你的" + name + "属性");
					}
				}

				if (null != ref) {
					// 看一看当前IOC容器是否已存在该bean，有的话直接设置，没有的话使用递归，创建该bean对象
					Object existBean = context.get(property.getRef());
					if (null == existBean) {
						// 递归创建一个bean
						existBean = createBean(map.get(property.getRef()));
						// 放置到context容器中
						// 只有当scope="singleton"时才往容器中放
						if (map.get(property.getRef()).getScope().equals("singleton")) {
							context.put(property.getRef(), existBean);
						}
					}
					try {
						BeanUtils.setProperty(beanObj, name, existBean);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException("您的bean属性" + name + "没有对应的set方法");
					}
				}
			}
		}
		return beanObj;
	}

	/**
	 * 根据bean名称获取bean对象
	 * 
	 * @author xxl
	 * @version 1.0
	 * @createDate 2018年12月25日 下午2:36:41
	 *
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName) {
		Object bean = context.get(beanName);
		// 如果为空，则什么scope不是singleton，那么容器中是没有的，这个线程创建
		if (null == bean) {
			bean = createBean(map.get(beanName));
		}
		return bean;
	}

}
