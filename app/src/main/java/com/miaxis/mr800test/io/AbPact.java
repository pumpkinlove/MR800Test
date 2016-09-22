package com.miaxis.mr800test.io;

import android.util.Log;

/**
 * 协议类
 * @author yc.zhang
 *
 */
public abstract class AbPact {
	
	public static final String TAG = "AbPact";
	public static final int MAX_LEN = 1024 * 10;
	public boolean isFindStx, isFindEtx;
	public byte[] baseBuffer = new byte[MAX_LEN];
	public int index;

	/**
	 * 过滤数据提取符合协议的数据
	 * @param buffer
	 * @param length
	 * @return
	 */
	public abstract byte[] filterPackage(byte[] buffer,int length);
	/**
	 * 按照协议制作帧包
	 * @param buffer
	 * @param length
	 * @return
	 */
	public abstract byte[] makePackage(byte[] buffer, int length);
	/**
	 * 去掉帧包壳,获取数据
	 * @param buffer
	 * @param length
	 * @return
	 */
	public abstract byte[] peelPackage(byte[] buffer,int length);
	

	public boolean addBuffer(byte[] src, int srcPos, int length) {

		if (index + length > MAX_LEN) { 
			isFindStx = false;
			isFindEtx = false;
			clearBuffer();
			Log.i(TAG, "--<< Array Index OutOfBounds ReFind STX >>--");
			return false;
		}
		try {
			System.arraycopy(src, srcPos, baseBuffer, index, length);
		} catch (Exception e) {
		}
		index += length;
		Log.i(TAG, "--<< !continue find ETX >>--");
		return true;
	}
	public byte[] copyNeedBuffer(int needLen){
		byte[] needBuf = new byte[needLen];
		System.arraycopy(baseBuffer, 0, needBuf, 0, needBuf.length);
		clearBuffer();
		isFindStx = false;
		isFindEtx = false;
		Log.i(TAG, "--<< extract success! >>--");
		return needBuf;
	}

	public void clearBuffer() {
		for (int i = 0; i < baseBuffer.length; i++) {
			baseBuffer[i] = 0;
		}
		index = 0;
	}

	public int findTag(byte[] buffer, int tag) {
		if (buffer == null) {
			return -1;
		}
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] == tag) {
				return i;
			}
		}
		return -1;
	}
}