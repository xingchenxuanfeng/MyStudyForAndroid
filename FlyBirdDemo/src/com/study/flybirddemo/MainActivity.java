package com.study.flybirddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.study.flybirddemo.drawer.Drawer;

public class MainActivity extends Activity {

	SurfaceView surfaceView;
	private Drawer drawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surfaceView = new SurfaceView(this);
		surfaceView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (drawer.gameStatus) {
				case Drawer.ready:
					drawer.gameStatus = Drawer.running;
					break;
				case Drawer.running:
					if (!drawer.isdrop) {
						drawer.bird.setYspeed(-15);
					}
					break;
				case Drawer.stop:
					drawer.initSpirit();
					drawer.initAttr();
					drawer.gameStatus = Drawer.ready;
					break;
				default:
					break;
				}
				return false;
			}
		});
		surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				drawer.setKeep(false);
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				drawer = new Drawer(MainActivity.this, surfaceView.getWidth(),
						surfaceView.getHeight(), holder);
				drawer.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub

			}
		});
		setContentView(surfaceView);
	}

}
