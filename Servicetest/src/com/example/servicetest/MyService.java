package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
	String tag = "servicetest";
	MediaPlayer mediaPlayer;

	public class Bind extends Binder {
		String a = "testbind";

		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,
				int flags) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i(tag, data.readString());
			return super.onTransact(code, data, reply, flags);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(tag, "onbind");
		mediaPlayer.start();

		Bind bind = new Bind();
		return bind;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(tag, "onCreate");
		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
		mediaPlayer.setLooping(true);
		// while (true) {
		// try {
		// Thread.sleep(2000);
		// Log.i(tag, "hold");
		//
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(tag, "onDestroy");
		mediaPlayer.stop();

	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(tag, "onUnbind");
		mediaPlayer.stop();

		return super.onUnbind(intent);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(tag, "onStartCommand");
		mediaPlayer.start();
		return super.onStartCommand(intent, flags, startId);

	}
}
