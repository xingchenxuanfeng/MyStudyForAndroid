package com.example.sdsimpleuse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;
import android.os.Environment;

public class fileuse {
	Context context;

	public fileuse(Context context) {
		super();
		this.context = context;
	}

	public String getfromsd(String filename) {
		FileInputStream in = null;
		String s = "";
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			try {
				in = new FileInputStream(file);
				int len = 0;
				byte[] date = new byte[1024];
				while ((len = in.read(date)) != -1) {
					out.write(date, 0, len);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		s = out.toString();
		return s;
	}

	public boolean savetosd(String filename, String content) {
		boolean flag = false;
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		FileOutputStream fileOutputStream = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			try {
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(content.getBytes());
				flag = true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return flag;
	}

}
