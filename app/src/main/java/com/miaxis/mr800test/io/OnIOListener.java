package com.miaxis.mr800test.io;

public interface OnIOListener {
	public void onIoRead(byte[] buffer, int length);
	public void onIoWrite(byte[] buffer, int length);

}
