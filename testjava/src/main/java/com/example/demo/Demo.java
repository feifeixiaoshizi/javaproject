package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Demo implements IDemo{

	@Override
	public void test() {
	  System.out.println("wo shi a demo");
		
	}

	@Override
	public String getString() {
		return "lijiansheng is a woner ";
	}


}
