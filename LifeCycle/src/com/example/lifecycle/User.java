package com.example.lifecycle;

import java.io.Serializable;

public class User implements Serializable {
	public User(String name, int age) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.age = age;
	}

	String name;
	 int age;
}
