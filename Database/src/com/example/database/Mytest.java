package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

@SuppressLint("NewApi")
public class Mytest extends AndroidTestCase {

	public void test1() {
		DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.execSQL("insert into person(name) values('abc')");
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", "bcd");
		db.insert("person", "id", contentValues);
		contentValues.clear();
		contentValues.put("name", "bcd");
		db.update("person", contentValues, "name=?", new String[] { "abc" });
		db.close();
	}

	public void test2() {
		DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Cursor cursor = db.query(true, "person", new String[] { "id", "name" },
				"id>?", new String[] { "0" }, null, null, null, null, null);
		int i1 = cursor.getColumnIndex("id");
		int i2 = cursor.getColumnIndex("name");
		String r = "";
		if (cursor.moveToFirst()) {
			do {
				r += cursor.getString(i1) + "  ";
				r += cursor.getString(i2);
				r += "\n";
			} while (cursor.moveToNext());
		}
		Log.i("test", r);
	}

	public void test3() {
		DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
	}

}
