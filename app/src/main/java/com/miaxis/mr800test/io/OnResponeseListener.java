package com.miaxis.mr800test.io;

public interface OnResponeseListener {

	public void onResponese(byte[] buffer, int length);
	public void onResponese(int index);
	public void onResponese(String value);
}
