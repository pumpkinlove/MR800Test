package com.signway.uart;

public class Uart {
	static {
		System.loadLibrary("uart_jni");
	}

	public synchronized native int openUart(String device, int baud);

	public synchronized native int closeUart(int fd);

	public synchronized native int readUart(int fd, byte[] buf, int len);

	public synchronized native int writeUart(int fd, byte[] buf, int len);
}
