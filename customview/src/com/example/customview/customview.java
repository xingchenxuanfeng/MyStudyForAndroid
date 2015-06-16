package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class customview extends LinearLayout {

	private ImageView iv;
	private TextView tv;
	Paint paint;

	public customview(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		LayoutInflater.from(context).inflate(R.layout.customview, this,true);
		iv = (ImageView) findViewById(R.id.iv);
		tv = (TextView) findViewById(R.id.tv);
		// TODO Auto-generated constructor stub
	}

	public customview(Context context) {
		this(context, null);
		paint = new Paint();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	public void setImageResource(int resId) {
		if (iv == null)
			Log.i("null", "iv");
		else
			iv.setImageResource(resId);
	}

	public void setTextViewText(String text) {
		if (tv == null)
			Log.i("null", "tv");
		else
			tv.setText(text);
	}
}
