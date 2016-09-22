package com.miaxis.mr800test.io;

import android.util.Log;

/**
 * 邮储bin文件匹配
 * @author yc.zhang
 *
 */
public class FitBinPSBC extends AbFitBin{

	public static final String TAG = "FitBinPSBC";
	public void fitBin(byte[] buffer, String deviceName,
			OnIOListener onIOListener){
		AbBin abBin =  null;
		byte[] cmd = Util.filterBuffer(2, buffer);
		if(cmd == null || isUnEncrypt(buffer)){
			//主要是串口非加密数字--单数据
			abBin = OtherBin.getInstance();
			abBin.setDeviceName(deviceName);
			abBin.setBuffer(OtherBin.CMD_SINGLE, buffer, buffer.length);
			Log.d(TAG, "single data!");
			return;
		}
		setInputCmd(cmd);
//		if(AndroidTestApp.DEVICE_NAME_HID.equals(deviceName)){
//			Log.d(TAG, "hid");
//			if(isCmd(Command.CMD_AF)){
//				abBin = AddFileBin.getInstance();
//			}else if(isCmd(Command.CMD_WP)){
//				abBin = NumberBin.getInstance();
//			}else if(isCmd(Command.CMD_QP)){
//				abBin = NumberBin.getInstance();
//			}else if(isCmd(Command.CMD_WC)){
//				abBin = NumberBin.getInstance();
//			}else if(isCmd(Command.CMD_LG)){
//				abBin = TellerBin.getInstance();
//			}else{
//				abBin = OtherBin.getInstance();
//			}
//		}else if(AndroidTestApp.DEVICE_NAME_SERIAL.equals(deviceName)){
//			Log.d(TAG, "serial");
//			if(isCmd(Command.CMD_AF)){
//				abBin = AddFileBin.getInstance();
//			}else if(isCmd(Command.CMD_WP)){
//				abBin = NumberBin.getInstance();
//			}else if(isCmd(Command.CMD_QP)){
//				abBin = NumberBin.getInstance();
//			}else if(isCmd(Command.CMD_WC)){
//				abBin = NumberBin.getInstance();
//			}else if(isCmd(Command.CMD_LG)){
//				abBin = TellerBin.getInstance();
//			}else{
//				abBin = OtherBin.getInstance();
//			}
//		}
//		if(abBin != null){
//			abBin.setDeviceName(deviceName);
//			abBin.setOnIOListener(onIOListener);
//			abBin.setBuffer(getCurCmd(),buffer, buffer.length);
//
//		}
	}
	private boolean isUnEncrypt(byte[] buffer){
		if(buffer[0] == -126  || buffer[0] == -127 || buffer[0] == -125){
			Log.d(TAG, "80,81,82");
			return true;
		}
		return false;
	}
}