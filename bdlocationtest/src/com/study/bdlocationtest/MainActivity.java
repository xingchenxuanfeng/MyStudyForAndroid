package com.study.bdlocationtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.bdlocationtest.R;

public class MainActivity extends Activity {
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="bd09ll";
	private LocationClient mLocationClient;
	private TextView textView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
		textView = (TextView) findViewById(R.id.localinfo);
		((LocationApplication)getApplication()).mLocationResult=textView;
        InitLocation();
        mLocationClient.start();
        textView.setText("��λ����");
        
    }
    private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//���ö�λģʽ
		option.setCoorType(tempcoor);//���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		int span=5000;
		option.setScanSpan(span);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
