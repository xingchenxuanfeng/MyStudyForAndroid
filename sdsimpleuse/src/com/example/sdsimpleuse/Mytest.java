package com.example.sdsimpleuse;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

public class Mytest extends AndroidTestCase {

	public Mytest() {
		// TODO Auto-generated constructor stub
	}

	public void testfile() {
		Context context = getContext();
		// TODO Auto-generated method stub
		fileuse fileuse = new fileuse(context);
		boolean b = fileuse.savetosd("abcd.txt", "≤‚ ‘1234");
		Log.i("test", b + "");

	}

	public void testfile2() {
		Context context = getContext();
		// TODO Auto-generated method stub
		fileuse fileuse = new fileuse(context);
		String b = fileuse.getfromsd("abcd.txt");
		Log.i("test", b + "");

	}
}
