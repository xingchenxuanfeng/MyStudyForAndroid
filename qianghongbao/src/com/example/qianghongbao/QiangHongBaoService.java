package com.example.qianghongbao;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QiangHongBaoService extends AccessibilityService {

	static final String TAG = "QiangHongBao";

	/** 微信的包名 */
	static final String WECHAT_PACKAGENAME = "com.tencent.mm";
	/** 红包消息的关键字 */
	static final String HONGBAO_TEXT_KEY = "[微信红包]";
	public Boolean StartPick = false;

	private String tag = "QiangHongBao";

	private changestartpick receiver = new changestartpick();

	private KeyguardManager.KeyguardLock keyguardLock;

	@Override
	public void onCreate() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.qhb");
		registerReceiver(receiver, intentFilter);
		Log.e(tag, "register");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		Log.e(tag, "unregister");
		super.onDestroy();
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		final int eventType = event.getEventType();
		// StartPick = getSharedPreferences("start", 0).getBoolean("start",
		// false);

		Log.d(TAG, "事件---->" + event);

		// 通知栏事件
		if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
			List<CharSequence> texts = event.getText();
			if (!texts.isEmpty()) {
				for (CharSequence t : texts) {
					String text = String.valueOf(t);
					if (text.contains(HONGBAO_TEXT_KEY)) {
						// ring();
						unlockScreen();
						// test();
						StartPick = true;
						openNotify(event);
						break;
					}
				}
			}
		} else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			Log.e(tag, "run startpick=" + StartPick);
			if (StartPick) {
				if (new Date().before(new Date(115, 9, 10))) {
					openHongBao(event);
					// } else {
					// Toast.makeText(getApplicationContext(),
					// R.string.testverion, Toast.LENGTH_LONG).show();
				}
			}
		} else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
			// if ("com.tencent.mm.ui.LauncherUI".equals(event.getClassName()))
			// {
			Log.e(tag, "run content startpick=" + StartPick);
			if (StartPick) {
				if (new Date().before(new Date(115, 9, 10))) {
					checkKey1();
					// } else {
					// Toast.makeText(getApplicationContext(),
					// R.string.testverion, Toast.LENGTH_LONG).show();
				}
			}
			// }
		}
	}

	@Override
	protected boolean onKeyEvent(KeyEvent event) {
		Log.e(tag, "keyevent");
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			StartPick = false;
			Log.e(tag, "startpick change to false");
		}
		return super.onKeyEvent(event);
	}

	private void ring() {
		// 使用来电铃声的铃声路径
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		MediaPlayer mMediaPlayer = null;
		// 如果为空，才构造，不为空，说明之前有构造过
		if (mMediaPlayer == null)
			mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(getApplication(), uri);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mMediaPlayer.setLooping(true); // 循环播放
		try {
			mMediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mMediaPlayer.start();
	}

	private void unlockScreen() {
		KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext()
				.getSystemService(Context.KEYGUARD_SERVICE);
		keyguardLock = keyguardManager.newKeyguardLock("MyKeyguardLock");
		keyguardLock.disableKeyguard();

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wakeLock = pm.newWakeLock(
				PowerManager.FULL_WAKE_LOCK
						| PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");

		wakeLock.acquire();
		// keyguardLock.reenableKeyguard();
		wakeLock.release();
	}

	/*
	 * @Override protected boolean onKeyEvent(KeyEvent event) { //return
	 * super.onKeyEvent(event); return true; }
	 */

	@Override
	public void onInterrupt() {
		// Toast.makeText(this, "中断抢红包服务", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		// Toast.makeText(this, "连接抢红包服务", Toast.LENGTH_SHORT).show();
	}

	private void sendNotifyEvent() {
		AccessibilityManager manager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
		if (!manager.isEnabled()) {
			return;
		}
		AccessibilityEvent event = AccessibilityEvent
				.obtain(AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED);
		event.setPackageName(WECHAT_PACKAGENAME);
		event.setClassName(Notification.class.getName());
		CharSequence tickerText = HONGBAO_TEXT_KEY;
		event.getText().add(tickerText);
		manager.sendAccessibilityEvent(event);
	}

	/** 打开通知栏消息 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openNotify(AccessibilityEvent event) {
		if (event.getParcelableData() == null
				|| !(event.getParcelableData() instanceof Notification)) {
			return;
		}
		// 以下是精华，将微信的通知栏消息打开
		Notification notification = (Notification) event.getParcelableData();
		PendingIntent pendingIntent = notification.contentIntent;
		try {
			pendingIntent.send();
		} catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openNotify(Notification notification) {
		PendingIntent pendingIntent = notification.contentIntent;
		try {
			pendingIntent.send();
		} catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openHongBao(AccessibilityEvent event) {
		if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI"
				.equals(event.getClassName())) {
			// 点中了红包，下一步就是去拆红包
			checkKey1();
			Log.e(tag, "拆");
		} else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI"
				.equals(event.getClassName())) {
			// 拆完红包后看详细的纪录界面
			// nonething
			Log.e(tag, "看");
			StartPick = false;
			keyguardLock.reenableKeyguard();
			Log.e(tag, "startpick=false");
			// setstart(StartPick);
		} else if ("com.tencent.mm.ui.LauncherUI".equals(event.getClassName())) {
			// 在聊天界面,去点中红包
			checkKey2();
			Log.e(tag, "抢");
		}
	}

	private void setstart(Boolean i) {
		SharedPreferences start = getSharedPreferences("start", 0);
		SharedPreferences.Editor editor = start.edit();
		editor.putBoolean("start", i);
		editor.commit();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void checkKey1() {
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null) {
			Log.w(TAG, "rootWindow为空");
			return;
		}
		List<AccessibilityNodeInfo> list = nodeInfo
				.findAccessibilityNodeInfosByText("拆红包");
		for (AccessibilityNodeInfo n : list) {
			n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
			Log.w(TAG, "-->拆红包performAction:" + n);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void checkKey2() {
		Log.e(tag, "1");
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null) {
			Log.w(TAG, "rootWindow为空");
			return;
		}
		List<AccessibilityNodeInfo> list = nodeInfo
				.findAccessibilityNodeInfosByText("领取红包");
		for (int i = 0; i < 10; i++) {
			if (list.isEmpty()) {
				try {
					Thread.sleep(1000);
					list = nodeInfo.findAccessibilityNodeInfosByText("领取红包");
					getTime("领取红包");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
		if (list.isEmpty()) {
			list = nodeInfo.findAccessibilityNodeInfosByText(HONGBAO_TEXT_KEY);
			for (int i = 0; i < 10; i++) {
				if (list.isEmpty()) {
					try {
						Thread.sleep(1000);
						list = nodeInfo
								.findAccessibilityNodeInfosByText(HONGBAO_TEXT_KEY);
						getTime(HONGBAO_TEXT_KEY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					break;
				}
			}
			if (list.isEmpty()) {
				Log.i(TAG, "找到null -->微信红包:" + list);
				return;
			}
			for (AccessibilityNodeInfo n : list) {
				Log.i(TAG, "-->微信红包:" + n);
				n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				Log.w(TAG, "-->抢红包微信红包performAction:" + n + "&&isClickable="
						+ n.isClickable());
				break;
			}
		} else {
			// 最新的红包领起
			for (int i = list.size() - 1; i >= 0; i--) {
				AccessibilityNodeInfo parent = list.get(i).getParent();
				Log.i(TAG, "-->领取红包:" + parent);
				if (parent != null) {
					parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					Log.w(TAG, "-->抢红包领取红包performAction:" + parent
							+ "&&isClickable=" + parent.isClickable());
					break;
				}
			}
		}
	}

	void getTime(String str) {
		long time = System.currentTimeMillis();// long now =
												// android.os.SystemClock.uptimeMillis();
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SSSS");
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		Log.w(TAG, str + t1);
	}

	class changestartpick extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// Boolean sp = intent.getBooleanExtra("start", false);
			Notification notification = (Notification) intent
					.getParcelableExtra("notification");
			StartPick = true;
			unlockScreen();
			openNotify(notification);
			Log.e(tag, "broadcastreciver: startpick=" + StartPick);
		}
	}

}
