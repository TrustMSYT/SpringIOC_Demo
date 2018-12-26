package com.inst.ioc.main;

import com.inst.ioc.entity.Person;
import com.inst.ioc.entity.Student;
 
/**
 * 测试类
 * @author xxl
 * @version 1.0
 * @createDate 2018年12月25日 下午2:39:19
 *
 */
public class TestBean {
	public static void main(String[] args) {
		// 根据配置文件获取实例工厂
		BeanFactory bf = new ClassPathXmlApplicationContext("/application.xml");
		// 根据实例名称获取实例对象
		Person s = (Person) bf.getBean("person");
		Person s1 = (Person) bf.getBean("person");
		System.out.println(s==s1);
		System.out.println(s1);
		System.out.println(s);
        Student stu1=(Student) bf.getBean("student");
        Student stu2=(Student) bf.getBean("student");
        String name=stu1.getName();
        System.out.println(name);
        System.out.println(stu1==stu2);
        System.out.println(stu1);
        System.out.println(stu2);
	}
}
