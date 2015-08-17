package com.example.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.animation.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Loader.ForceLoadContentObserver;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private ImageView iv;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView) findViewById(R.id.lv);
		ArrayList<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 20; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", "test" + i);
			map.put("value", "test" + i);
			maps.add(map);

		}
		SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, maps,
				R.layout.listitem, new String[] { "key", "value" },
				new int[] { R.id.t1, R.id.t2 });
		listView.setAdapter(adapter);
		iv = (ImageView) findViewById(R.id.iv);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// AnimationSet animationSet = new AnimationSet(true);
				// TranslateAnimation animation = new TranslateAnimation(1,
				// 0.5f,1,2,1, 2.5f,1,2.5f);;
				// animationSet.setDuration(2000);
				// animationSet.setFillAfter(true);
				// animationSet.addAnimation(animation);
				// iv.startAnimation(animationSet);
				// Animation animation = AnimationUtils.loadAnimation(
				// MainActivity.this, R.anim.ani);
				// iv.startAnimation(animation);
				iv.setBackgroundResource(R.drawable.ani);
				AnimationDrawable animationDrawable = (AnimationDrawable) iv
						.getBackground();
				animationDrawable.start();
			}
		});

	}
}
