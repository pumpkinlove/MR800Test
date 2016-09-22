package com.miaxis.mr800test.io;

import android.util.Log;

import com.miaxis.mr800test.domain.SendPwdEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * 
 * @category U口不支持的命令直接走串口发出给M3，串口不支持的密令直接走U口给Demo
 * @author yc.zhang
 * 2016-4-25
 */
public class OtherBin extends AbBin{

	public static final String DEVICE_NAME_HID = "/dev/hidg0";
	public static final String DEVICE_NAME_SERIAL = "/dev/ttyS1";
	public static final String DEVICE_PWD_SERIAL = "/dev/ttySWK3";
	public static final int DEVICE_BAUD = 9600;

	/**单个字节命令**/
	private int index = -3;
	public static final String CMD_SINGLE = "SINGLE";
	private static OtherBin otherBin;
	public static OtherBin getInstance(){
		if(otherBin == null){
			otherBin = new OtherBin();
		}
		return otherBin;
	}
	public OtherBin() {
		super("OtherBin");
	}

	@Override
	public void onResponese(byte[] buffer, int length) {
		
	}

	@Override
	public void onResponese(int index) {
		
	}

	@Override
	public void onResponese(String value) {
		
	}

	@Override
	public void analysis() {
		FitPactManager fitPactManager = null;
		Log.d(TAG, "deviceName:"+deviceName);
//		if(DEVICE_NAME_HID.equals(deviceName)){
//			fitPactManager = getFitPactManagerSerial();
//		}else if(DEVICE_NAME_SERIAL.equals(deviceName)){
//			fitPactManager = getFitPactManagerHid();
//		}else
		if(DEVICE_PWD_SERIAL.equals(deviceName)){
			Log.d(TAG, "Single Data:"+buffer[0]);
			if(buffer[0] >= '0' && buffer[0] <= '9'){
				index = buffer[0] - '0';
			}else if(buffer[0] == 0x0d){
				index = -1;
			}else if(buffer[0] == 0x08){
				index = -2;
			}
			EventBus.getDefault().post(new SendPwdEvent(index));
			return;
		}
		if(CMD_SINGLE.equals(getCurCmd())){
			Log.d(TAG, "Single Data:"+buffer[0]);
			fitPactManager.getAbIO().write(new byte[]{buffer[0]}, 1);
			return;
		}
		Log.d(TAG, "double Data");
		fitPactManager.onIoWrite(buffer,buffer.length);
	}
	
	public int getPwd() {
		return index;
	}

	public void resetPwd() {
		this.index = -3;
	}

}
