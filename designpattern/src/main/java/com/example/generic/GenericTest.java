package com.example.generic;


import com.example.generic.clazz.GenericClassField;
import com.example.generic.clazz.GenericMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

public class GenericTest {
	public static void main(String[] args) throws Exception {
		test();
	}


	private static void test(){
		GenericMethod<?> genericMethod = GenericMethod.get();
		GenericMethod g = genericMethod;

		TypeVariable[] types = genericMethod.getClass().getTypeParameters();
		for(TypeVariable type : types){
			System.out.println("type:"+type.getName());
		}
		Object object = genericMethod.getGenericWildcard();
		System.out.println("object:"+object.getClass());

	}


}