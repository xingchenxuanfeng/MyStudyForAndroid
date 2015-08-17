package com.example.simpletest;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent =new Intent();
        
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
      //  intent.setComponent(new ComponentName("com.uc.browser", "com.uc.browser.ActivityUpdate"));
        intent.setClassName("com.UCMobile", "com.UCMobile.main.UCMobile");
//        intent.addCategory(Intent.CATEGORY_DEFAULT);  
        startActivity(intent);
//        Intent intent;  
//        intent = PackageManager.getLaunchIntentForPackage(packageName);  
//        intent.setAction(Intent.ACTION_VIEW);  
//        intent.addCategory(Intent.CATEGORY_DEFAULT);  
//        intent.setData(Uri.parse("http://www.baidu.com"));  
//        startActivity(intent);  
        
    }

    
    
}
