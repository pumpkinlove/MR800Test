package com.jni;
/**
 * HID_JNI<br>
 * JNI无需初始化,static System.loadLibrary("jni") 编译期间就已经加载完毕
 * @author yc.zhang
 *
 */
public class JNI {
	static {
		System.loadLibrary("jni");
	}

//	byte[] buffer = new byte[64];
//	int fd_hidg0 = JNI.open("/dev/hidg0");
//	int sndLength = JNI.write(fd_hidg0, buffer, 64);
//	int recvLength = JNI.read(fd_hidg0, buffer, 64);
	
	public static native int open(String device);
	public static native int close(int fd);
	public static native int read(int fd, byte[] buf, int len);
	public static native int write(int fd, byte[] buf, int len);
}
