package com.miaxis.mr800test.io;

import java.util.Arrays;


/**
 * 根据命令匹配Bin数据文件
 * @author yc.zhang
 *
 */
public abstract class AbFitBin {
	private byte[] inputCmd;
	private String CurCmd;
	public abstract void fitBin(byte[] buffer, String deviceName,
			OnIOListener onIOListener);
	
	public boolean isCmd(String command){
		if(command == null || inputCmd == null){
			return false;
		}
		if(Arrays.equals(command.getBytes(), inputCmd)){
			this.CurCmd = command;
			return true;
		}
		return false;
	}

	public void setInputCmd(byte[] curCmd) {
		this.inputCmd = curCmd;
	}

	public String getCurCmd() {
		return CurCmd;
	} 
	
	
	
}