package com.miaxis.mr800test.io;

import android.util.Log;
/**
 * IO口:串口、U口<br>
 * 串口或者U口发送数据机制会有个最大限制数据长度,程序上可以根据实际发送的数据长度然后二次发送剩下的数据
 * 
 * @author yc.zhang TODO byte数组创建接收能有待很大优化
 */
public abstract class AbIO {
	public static final String TAG = "AbIO";
	public static final int LENGTH_REVEIVE = 1024;
	public OnIOListener ioReadListener;
	public boolean isRunRead;
	public boolean isRunWrite;
	public byte[] receiveBuffer = new byte[LENGTH_REVEIVE];

	/* 每次发送接收是4000*2左右,建立buffer缓存池,如果是每次初始化浪费时间与资源* */
	public byte[] sendBuffer = new byte[4000 * 3];
	private int sendLength;
	private int entitySendLength;
	private String deviceName;

	public abstract void open();

	public abstract void close();

	public abstract int write(byte[] buffer, int length);

	public abstract int read(byte[] buffer, int length);

	public AbIO(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setOnIOReadListener(OnIOListener ioReadListener) {
		this.ioReadListener = ioReadListener;
	}

	public void setSendBuffer(byte[] buffer, int length) {
		sendLength = length;
		entitySendLength = 0;
		System.arraycopy(buffer, 0, sendBuffer, 0, length);
	}

	public void startRun() {
		new Thread(runRead).start();
		new Thread(runWrite).start(); // TODO 多线程导致数据不安全
	}

	public void stopRun() {
		isRunRead = false;
		isRunWrite = false;
	}

	private Runnable runRead = new Runnable() {

		@Override
		public void run() {
			isRunRead = true;
			while (isRunRead) {
				// read
				// 阻塞----------------------------------------------------------------
				int entityLength = read(receiveBuffer, LENGTH_REVEIVE);
				if (entityLength > 0 && ioReadListener != null) {
					byte[] entityBuffer = new byte[entityLength];
					System.arraycopy(receiveBuffer, 0, entityBuffer, 0,
							entityLength);
					ioReadListener.onIoRead(entityBuffer, entityLength);
				}

			}

		}
	};
	private Runnable runWrite = new Runnable() {

		@Override
		public void run() {
			isRunWrite = true;
			while (isRunWrite) {
				if (entitySendLength < sendLength) {
					// 未发送的数据
					byte[] remainBuffer = new byte[sendLength
							- entitySendLength];
					System.arraycopy(sendBuffer, entitySendLength,
							remainBuffer, 0, remainBuffer.length);
					int entityLength = write(remainBuffer, remainBuffer.length);
					// 实际发送的数据长度
					entitySendLength += entityLength;

				}
			}
		}
	};

}

