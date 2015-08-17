package com.study.flybirddemo.spirit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Spirit implements Cloneable {
	Bitmap bitmap;
	Bitmap[] bms;
	int width, height;
	float x, y;
	float xspeed, yspeed;
	long curtime = 0, lasttime = 0;
	long span = 100;

	public Spirit(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}

	public Spirit(Bitmap bitmap, Bitmap[] bms, float x, float y, float xspeed,
			float yspeed) {
		super();
		this.bitmap = bitmap;
		this.bms = bms;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
	}

	public Spirit(Bitmap[] bitmap) {
		super();
		this.bms = bitmap;
		this.width = bms[0].getWidth();
		this.height = bms[0].getHeight();
	}

	public void changebmp() {
		if (bms == null || bms.length == 0) {
			return;
		}
		bitmap = bms[(int) (System.currentTimeMillis() % bms.length)];
	}

	public void drawTheBird(Canvas canvas) {
		curtime = System.currentTimeMillis();
		if (curtime - lasttime > span) {
			changebmp();
			lasttime = curtime;
		}
		canvas.drawBitmap(bitmap, x, y, null);

	}

	public RectF getRectF() {
		return new RectF(x, y, x + width, y + height);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public Bitmap[] getBms() {
		return bms;
	}

	public Spirit getClone() {
		try {
			return (Spirit) this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public float getX() {
		return x;
	}

	public float getXspeed() {
		return xspeed;
	}

	public float getY() {
		return y;
	}

	public float getYspeed() {
		return yspeed;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}

	public void setBms(Bitmap[] bms) {
		this.bms = bms;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setXspeed(float xspeed) {
		this.xspeed = xspeed;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setYspeed(float yspeed) {
		this.yspeed = yspeed;
	}

	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub

		canvas.drawBitmap(bitmap, x, y, null);
	}
}
