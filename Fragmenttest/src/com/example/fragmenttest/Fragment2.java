package com.example.fragmenttest;

import java.security.PublicKey;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment2 extends Fragment {
	communicate call;
	private Button button;
	public EditText editText;

	public interface communicate {
		public void com(int m);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			call = (communicate) activity;
		} catch (Exception e) {
			Log.e("e", "noimplement");
			// TODO: handle exception
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragement2, container, false);
		button = (Button) view.findViewById(R.id.button1);
		editText = (EditText) view.findViewById(R.id.editText1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				call.com(10);
			}
		});
		return view;
	}
}
