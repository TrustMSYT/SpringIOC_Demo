package com.inst.ioc.main;

import com.inst.ioc.entity.Person;
import com.inst.ioc.entity.Student;

public class TestBean {
	public static void main(String[] args) {
		BeanFactory bf = new ClassPathXmlApplicationContext("/application.xml");
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
