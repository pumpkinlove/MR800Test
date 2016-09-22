package com.miaxis.mr800test.io;

import android.util.Log;

import com.jni.JNI;
/**
 * HID
 * @author yc.zhang
 *
 */
public class Hid extends AbIO{
	public static final String TAG = "Hid";
	private static final int DATA_LENGTH = 1024;
	private int fd;
	private String deviceName;
	
	public Hid(String deviceName){
		super(deviceName);
		this.deviceName = deviceName;
	}

	@Override
	public void open() {
		fd = JNI.open(deviceName);
		Log.d(TAG, "hid fd:"+fd);
		if(fd > 0){
			startRun();
		}
	}

	@Override
	public void close() {
		stopRun();
		try {
			if(fd > 0)
			JNI.close(fd);
		} catch (Exception e) {
		}
	}

	@Override
	public int write(byte[] buffer, int length) {
		//包大小必须是固定值，可有SO做也可以有App做;当前暂时有APP来做
		byte[] finalData = new byte[DATA_LENGTH];
		int dataLen = (length < DATA_LENGTH ) ? length:DATA_LENGTH;
		System.arraycopy(buffer, 0, finalData, 0, dataLen);
		return JNI.write(fd, finalData, DATA_LENGTH);
	}

	@Override
	public int read(byte[] buffer, int length) {
		return JNI.read(fd, buffer, length);
	}
	

	

}
