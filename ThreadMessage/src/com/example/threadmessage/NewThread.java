package com.example.threadmessage;

import java.io.PipedReader;

import android.util.Log;

public class NewThread extends Thread {
	PipedReader pr;
	String TAG = "Threadtest";

	public NewThread(PipedReader pipedReader) {
		// TODO Auto-generated constructor stub
		pr = pipedReader;
	}
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.i(TAG,Thread.currentThread().toString());
		while (!Thread.currentThread().interrupted()) {

			try {
				int i;
				if ((i= pr.read())!=-1) {
					String c=String.valueOf((char)i);
					Log.i(TAG,c);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
