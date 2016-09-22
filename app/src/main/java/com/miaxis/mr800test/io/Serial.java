package com.miaxis.mr800test.io;

import android.util.Log;

import com.signway.uart.Uart;

/**
 * 串口
 * @author yc.zhang
 *
 */
public class Serial extends AbIO{

	private Uart uart;
	private int fd;
	private String deviceName;
	private int deviceBaud;
	public Serial(String deviceName,int deviceBaud){
		super(deviceName);
		uart = new Uart();
		this.deviceName = deviceName;
		this.deviceBaud = deviceBaud;
	}
	@Override
	public void open() {
		fd = uart.openUart(deviceName, deviceBaud);
		Log.d(TAG, "serial fd:"+fd);
		if(fd > 0){
			startRun();
		}
	}

	@Override
	public void close() {
		stopRun();
		uart.closeUart(fd);
		
	}

	@Override
	public int write(byte[] buffer, int length) {
		Log.d(TAG, "write" + fd + deviceName);
		return uart.writeUart(fd, buffer, length);
	}

	@Override
	public int read(byte[] buffer, int length) {
		return uart.readUart(fd, buffer, length);
	}

	

}