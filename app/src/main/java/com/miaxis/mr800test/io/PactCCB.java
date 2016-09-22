package com.miaxis.mr800test.io;

import android.util.Log;


/**
 * 建行协议
 * @author yc.zhang
 *
 */
public class PactCCB extends AbPact {
	public static final String TAG = "PactCCB";
	public static final int SPLIT = 0x30;
	public static final int STX = 0x02;
	public static final int ETX = 0x03;
	public static final int PACKAGE_MIN_LENGHT = 8;

	@Override
	public byte[] filterPackage(byte[] readBuffer, int readLen) {

		int indexSTX = 0;
		int indexETX = 0;

		if (readBuffer == null) {
			Log.d(TAG, "--<< readBuffer is null >>--");
			return null;
		}
		for (int i = 0; i < readLen; i++) {
			//Log.d("readBuffer", "readBuffer[" + i + "]:" + readBuffer[i]);
		}

		// find STX
		if (!isFindStx) {
			indexSTX = findTag(readBuffer, STX);
			if (indexSTX != -1) {
				clearBuffer();
				isFindStx = true;
				Log.d(TAG, "--<< find STX >>--");
			}
		}

		// find ETX
		if (isFindStx) {
			indexETX = findTag(readBuffer, ETX);
			if (indexETX != -1) {
				int length = indexETX - indexSTX + 1;
				if (!addBuffer(readBuffer, indexSTX, length)) {
					return null;
				}
				Log.d(TAG, "--<< find ETX >>--");
				return copyNeedBuffer(index);
			}
		}

		int length = readLen - indexSTX;
		addBuffer(readBuffer, indexSTX, length);

		return null;

	}

	@Override
	public byte[] makePackage(byte[] message, int length) {
		if(message == null){
			Log.e(TAG, "makePackage buffer is null");
			return null;
		}
		byte lrc = 0;
		for (int i = 0; i < message.length; i++) {
			lrc ^= message[i];
		}
		Log.d(TAG, "make lrc：" + lrc);
		byte[] mergeData = new byte[2 + length + 1]; // 数据包长+数据包+lrc
		System.arraycopy(Util.integerToArray(length), 0, mergeData, 0, 2);
		System.arraycopy(message, 0, mergeData, 2, message.length);
		mergeData[mergeData.length - 1] = lrc;
		byte[] splitData = Util.splitBuffer(mergeData, SPLIT);

		byte[] packData = new byte[splitData.length + 2];
		packData[0] = STX;
		System.arraycopy(splitData, 0, packData, 1, splitData.length);
		packData[packData.length - 1] = ETX;
		return packData;
	}

	@Override
	public byte[] peelPackage(byte[] buffer, int length) {
		if (buffer == null || length < PACKAGE_MIN_LENGHT) {
			Log.e(TAG, "buffer is null or buffer length < minLength");
			return null;
		}

		/* 合并数据单元数据-不包含包头与包尾 */
		byte[] needBuffer = new byte[length - 2]; // 去除包头包尾的数据
		System.arraycopy(buffer, 1, needBuffer, 0, needBuffer.length);
		byte[] mergeBuffer = Util.merge(needBuffer, SPLIT);

		/* 数据长度 */
		int msgLen = Util.arrayToInteger(new byte[] { mergeBuffer[0],
				mergeBuffer[1] });
		Log.d(TAG, "msgLen:" + msgLen);

		if (mergeBuffer.length < msgLen) {
			Log.d(TAG, "mergeBuffer.length:" + mergeBuffer.length);
			return null;
		}
		/* 数据 */
		byte[] msgData = new byte[msgLen];
		System.arraycopy(mergeBuffer, 2, msgData, 0, msgLen);

		/* LRC校验-拆分数据单元 */
		byte lrc = mergeBuffer[mergeBuffer.length - 1];
		Log.d(TAG, "lrc：" + lrc);
		byte testLrc = 0;
		for (int i = 0; i < msgData.length; i++) {
			testLrc ^= msgData[i];
		}
		Log.d(TAG, "testLrc" + testLrc);
		if (lrc != testLrc) {
			Log.e(TAG, "fail to test lrc");
		//return null;
		}
		Log.i(TAG, "--<< saxPackage success! >>--");
		return msgData;
	}

}
