package com.example.fragmenttest;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnDrawListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Fragment2.communicate {
	String tag = "activity";
	private Button button;
	private FragmentManager fragmentManager;
	private TextView tv;
	private Fragment2 fragment1;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		Log.i("fragment", "createactivity");
		fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragment1 = new Fragment2();
		fragmentTransaction.add(R.id.li, fragment1);
		fragmentTransaction.commit();
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Fragment2 fragment2=new Fragment2();
//				fragmentManager.beginTransaction()
//						.add(R.id.li, fragment2).addToBackStack(null).commit();
//				fragment1.editText.setText("successful");
				Fragment2 f=(Fragment2) fragmentManager.findFragmentById(R.id.fragment1);
				f.editText.setText("ok");
				

			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("fragment", "destoryactivity");

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(tag, "start");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(tag, "resume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(tag, "pause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(tag, "stop");
	}

	@Override
	public void com(int m) {
		// TODO Auto-generated method stub
		tv.setText(String.valueOf(m));
	}

}
