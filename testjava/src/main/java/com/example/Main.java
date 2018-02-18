package com.example;

import com.example.demo.IDemo;
import com.example.demo.ILove;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Main {
	public static void main(String[] args) throws Exception {
		//ApplicationContext context = new ClassPathXmlApplicationContext("springmvc.xml");
		//D:\demo\Modularization\javaproject\testjava\src\main\java\com\example\springmvc.xml
		ApplicationContext context = new FileSystemXmlApplicationContext("testjava\\src\\main\\java\\com\\example\\springmvc.xml");
        ILove demo = (ILove) context.getBean("demo");
		System.out.println(demo.getClass());
		System.out.println(demo);
        demo.love();
		IDemo demo1 = (IDemo) context.getBean("demo");
		System.out.println(demo1);
		demo1.test();

		String string = demo1.getString();
		System.out.println(string);
	}

	
	
	 public static byte[] objectToByteArray(Object obj) {
	        byte[] bytes = null;
	        ByteArrayOutputStream byteArrayOutputStream = null;
	        ObjectOutputStream objectOutputStream = null;
	        try {
	            byteArrayOutputStream = new ByteArrayOutputStream();
	            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
	            objectOutputStream.writeObject(obj);
	            objectOutputStream.flush();
	            bytes = byteArrayOutputStream.toByteArray();

	        } catch (IOException e) {
	        } finally {
	            if (objectOutputStream != null) {
	                try {
	                    objectOutputStream.close();
	                } catch (IOException e) {
	                }
	            }
	            if (byteArrayOutputStream != null) {
	                try {
	                    byteArrayOutputStream.close();
	                } catch (IOException e) {
	                }
	            }

	        }
	        return bytes;
	    }
	 public static void writeObjectToFile(Object obj)
	    {
	        File file =new File("F:/test/test.class");
	        FileOutputStream out;
	        try {
	            out = new FileOutputStream(file);
	            ObjectOutputStream objOut=new ObjectOutputStream(out);
	            objOut.writeObject(obj);
	            objOut.flush();
	            objOut.close();
	            System.out.println("write object success!");
	        } catch (IOException e) {
	            System.out.println("write object failed");
	            e.printStackTrace();
	        }
	    }

}