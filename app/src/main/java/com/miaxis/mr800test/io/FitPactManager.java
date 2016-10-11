package com.miaxis.mr800test.io;

import android.util.Log;

/**
 * @category 匹配IO与帧格式协议
 * 
 * @author yc.zhang
 * 
 */
public class FitPactManager implements OnIOListener {

	public static final String TAG = "FitPactManager";
	private AbIO abIO;
	private AbPact abPact;
	private OnFitPactListener fitPactListener;

	public FitPactManager(AbIO abIO, AbPact abPact,
			OnFitPactListener fitPactListener) {
		this.abIO = abIO;
		this.abPact = abPact;
		this.fitPactListener = fitPactListener;

	}

	public void start() {
		if (this.abIO != null) {
			this.abIO.setOnIOReadListener(this);
			abIO.open(); // 打开IO工作
		}
	}

	public void stop() {
		if (this.abIO != null) {
			abIO.close();
		}
	}

	@Override
	public void onIoRead(byte[] buffer, int length) {
		byte[] filterPackage = abPact.filterPackage(buffer, length);
		if (filterPackage != null) {
			try {
				byte[] peelData = abPact.peelPackage(filterPackage,
						filterPackage.length);
				if (peelData != null && fitPactListener != null) {
					fitPactListener.onFitData(peelData, abIO.getDeviceName(), this);
//				ALog.Log(TAG, "FitPactManager is OK!");
				}
			} catch (Exception e) {
				Log.e("peelDataException", e.getMessage());
			}
		} else {
			if (abPact.isFindStx) { // 有协议包的数据不单个发送要收集整包在发送
				return;
			}
			// U口的81 82;串口不做处理---TODO 但是串口也可单个密码传输--!(等串口应该不是一个)
			fitPactListener.onFitData(buffer, abIO.getDeviceName(), this);
//			ALog.Log(TAG, "FitPactManager(single) is OK!");

		}

	}

	public void sendFitBin(String cmd) {
		if (fitPactListener != null)
		fitPactListener.onFitData(cmd.getBytes(), abIO.getDeviceName(), this);
	}

	@Override
	public void onIoWrite(byte[] buffer, int length) {
		if (abIO != null) {
			// 组包
			byte[] packData = abPact.makePackage(buffer, length);
			if (packData != null)
				abIO.setSendBuffer(packData, packData.length);
			Log.d(TAG, "onIoWrite");
		}

	}

	public AbIO getAbIO() {
		return abIO;
	}

	public AbPact getAbPact() {
		return abPact;
	}

	public OnFitPactListener getFitPactListener() {
		return fitPactListener;
	}

	public static interface OnFitPactListener {
		/**
		 * @category IO适配协议后获取数据的接口
		 * @param buffer
		 *            IO数据
		 * @param deviceName
		 *            IO名称
		 * @param onIOListener
		 *            IO读写接口
		 */
		public void onFitData(byte[] buffer, String deviceName,
							  OnIOListener onIOListener);
	}

}