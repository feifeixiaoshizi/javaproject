package com.example.genric;


public class GenericTest {
	public static void main(String[] args) throws Exception {
		//带范型类型的向下转型
		Generic<String> generic =(Generic<String>) get();
		String str = generic.convert("convert");
		System.out.println("str:"+str);
	}

	public static Generic<?> get(){
		return  new Generic<Object>() {
			@Override
			public String convert(Object o) {
				return "hello";
			}
		};
	}


}