package com.example.qianghongbao;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjishi on 15/2/13.
 */
@SuppressWarnings("NewApi")
public class NotificationService extends NotificationListenerService {

	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		Notification notification = sbn.getNotification();
		if (null != notification) {
			Bundle extras = notification.extras;
			if (null != extras) {
				List<String> textList = new ArrayList<String>();
				String title = extras.getString("android.title");
				if (!TextUtils.isEmpty(title))
					textList.add(title);

				String detailText = extras.getString("android.text");
				if (!TextUtils.isEmpty(detailText))
					textList.add(detailText);

				if (textList.size() > 0) {
					for (String text : textList) {
						if (!TextUtils.isEmpty(text) && text.contains("[微信红包]")) {
							PendingIntent pendingIntent = notification.contentIntent;
							try {
								// setstart(true);
								// startpick=true;
								Intent intent = new Intent("com.example.qhb");
								intent.putExtra("notification",notification);
								sendBroadcast(intent);
								pendingIntent.send();
								unlockScreen();

							} catch (PendingIntent.CanceledException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		}
	}

	private void setstart(Boolean i) {
		SharedPreferences start = getSharedPreferences("start", 0);
		SharedPreferences.Editor editor = start.edit();
		editor.putBoolean("start", i);
		editor.commit();
	}

	private void unlockScreen() {
		KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext()
				.getSystemService(Context.KEYGUARD_SERVICE);
		@SuppressWarnings("deprecation")
		final KeyguardManager.KeyguardLock keyguardLock = keyguardManager
				.newKeyguardLock("MyKeyguardLock");
		keyguardLock.disableKeyguard();

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = pm.newWakeLock(
				PowerManager.FULL_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");

		wakeLock.acquire();
		// keyguardLock.reenableKeyguard();
		// wakeLock.release();
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {

	}
}
