package com.miaxis.mr800test.io;

import android.content.Intent;
import android.util.Log;
/**
 * 按照协议逐字节获取值并存入缓存
 * @author yc.zhang
 *
 */
public abstract class AbBin  implements OnResponeseListener{
	public String TAG = "AbBin";
	
	public static final String UTF_8 = "utf-8";
	public static final String GB2312 = "gb2312";
	public static final String GBK = "GBK";
	private String curCmd;
	private String returnCmd;
	private String operationCmd;
	public int timeOut;
	public int pos = 2;
	public int filterLen;
	public byte[] buffer;
	public int bufferLen;
	public String deviceName;
	private OnIOListener onIOListener;
	public AbBin(String tag){
		TAG = tag;
	}
	
	public void setDeviceName(String deviceName){
		this.deviceName =  deviceName;
	}
	public void setCurCmd(String curCmd) {
		this.curCmd = curCmd;
		setReturnCmd(Command.getReturnCmd(curCmd));
		setOperationCmd(Command.getOperationCmd(curCmd));
	}
	public void setBuffer(String curCmd,byte[] buffer,int length){
		initData();
		setCurCmd(curCmd);
		this.buffer = buffer;
		this.bufferLen = length;
		analysis();
	}

	
	public String getCurCmd() {
		return curCmd;
	}

	public String getReturnCmd() {
		return returnCmd;
	}


	public void setReturnCmd(String returnCmd) {
		this.returnCmd = returnCmd;
	}
	
	public String getOperationCmd() {
		return operationCmd;
	}

	public void setOperationCmd(String operationCmd) {
		this.operationCmd = operationCmd;
	}

	public byte[] filterBuffer(int length){
		pos += filterLen;
		filterLen = length;
		return Util.filterBuffer(pos, length, buffer);
	}
	public int NtoInteger(byte[] buffer) {
		return Util.arrayNToDecimalism(buffer);
	}
	/**
	 * @category	转换十进制整数,下标与buffer已经内部计算
	 * @param length	buffer的长度
	 * @return
	 */
	public int NtoInteger(int length){
		return NtoInteger(filterBuffer(length));
	}
	public int FtoInteger(byte[] buffer){
		return Util.arrayFFToDecimalism(buffer);
	}
	public int FtoInteger(int length){
		return FtoInteger(filterBuffer(length));
	}
	public String getString(byte[] data, String charset) {
		return Util.getString(data, charset);
	}
	public String getString(int length,String charset) {
		return Util.getString(filterBuffer(length), charset);
	}
//	public void launchView(){
//		AndroidTestApp.toDisplay();
//	}
//	public void sendMessage(String action){
//		AndroidTestApp.sendMessage(action);
//	}
//	public void sendMessage(String action, int type) {
//		AndroidTestApp.sendMessage(action, type);
//	}
//	public void sendMessage(String action,long delayMillis){
//		AndroidTestApp.sendMessage(action, delayMillis);
//	}
//	public void sendIntent(Intent intent){
//		AndroidTestApp.sendIntent(intent);
//	}
	
	public void setOnIOListener(OnIOListener ioListener){
		this.onIOListener = ioListener;
	}
	
	public void write(byte[] buffer,int length){
		if(onIOListener != null){
			onIOListener.onIoWrite(buffer, length);
		}
	}
	
	public void write(String buffer){
		if(buffer != null){
			Log.d(TAG, "write buffer:"+buffer);
			write(buffer.getBytes(), buffer.length());
		}
	}	


	public void responseErro() {
		write(returnCmd + Command.CODE_12);
		Log.e(TAG, "--responseErro--");
	}
	public void resposeConfirm(){
		write(returnCmd + Command.CODE_00);
		Log.d(TAG, "--resposeConfirm--");
	}
	
	public void responseErro(String response) {
		write(returnCmd + Command.CODE_12+response);
		Log.e(TAG, "--responseErro--");
	}
	public void resposeConfirm(String response){
		write(returnCmd + Command.CODE_00+response);
		Log.d(TAG, "--resposeConfirm--");
	}
	public void operactionErro(String erroCod){
		write(operationCmd + erroCod);
		Log.e(TAG, "--operactionErro--");
	}
	public void operactionConfirm(String confirmCode){
		write(operationCmd + Command.CODE_00+ confirmCode);
		Log.e(TAG, "--operactionConfirm--");
	}
	public void operactionConfirmInf(String confirmCode,String inf){
		write(operationCmd + Command.CODE_00+ confirmCode + inf);
		Log.e(TAG, "--operactionConfirmInf--");
	}
	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	
	public abstract void analysis();
	
	public void initData(){
		pos = 2;
		filterLen = 0;
	}

}
