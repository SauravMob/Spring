package com.springorg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springorg.dao.StudentDao;
import com.springorg.entities.Student;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        StudentDao studentDao = context.getBean("studentDao", StudentDao.class);
        Student student = new Student(121, "Saurav", "Mumbai");
        int r = studentDao.insert(student);
        System.out.println("Done:" + r);
    }
}
