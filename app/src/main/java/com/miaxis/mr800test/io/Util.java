package com.miaxis.mr800test.io;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Util {

	public static final String TAG = "Util";
	public static final String UTF_8 = "utf-8";
	public static final String GB2312 = "gb2312";
	public static final String GBK = "GBK";
	public static final String COMMA_EN = ",";
	public static final String SPLITE = "\\|";
	
	public static byte[] getCount4N(int value) {
		byte[] data = new byte[4];
		data[0] = getAscall((value / 1000) % 10);
		data[1] = getAscall((value / 100) % 10);
		data[2] = getAscall((value / 10) % 10);
		data[3] = getAscall(value % 10);
		return data;
	}
	public static byte[] getCount4F(int value) {
		byte[] data = new byte[4];
		int ff = 0xff;
		data[0] = (byte) ((value / ff / ff / ff) % ff);
		data[1] = (byte) ((value / ff / ff) % ff);
		data[2] = (byte) ((value / ff) % ff);
		data[3] = (byte) (value % ff);
		return data;
	}
	
	public static byte getAscall(int value) {
		return (byte) (0x30 ^ value);
	}
	
	/**
	 * @category	获取文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName){
		if(fileName == null){
			return null;
		}
		int lastPiont = fileName.lastIndexOf(".");
		if(lastPiont == -1){
			return null;
		}
		return fileName.substring(lastPiont, fileName.length());
	}
	
	/**
	 * @category	解析字符串中的标志符号与分割符号拆分后存入map列表
	 * @param src	需要拆分的字符串
	 * @param symbol	标志符号
	 * @param splite	分割符号
	 * @return	map
	 */
	public static Map<String, String> getSpliteCharSequence(String src,String symbol,String splite){
		if(src == null || symbol == null || splite == null){
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		//如果字符串中没有该分割符则直接返回字符
		String[] spliteStrs = src.split(splite);
		
		for(String spliteStr :spliteStrs){
			String[] symbolStrs = spliteStr.split(symbol);
			String value = null;
			if(symbolStrs.length == 2){
				value = symbolStrs[1];
			}
			map.put(symbolStrs[0], value);
		}
		return map;
	}


	/**
	 * @category	将byte数组按照"charset"格式编码
	 * @param data	需要编码的数据
	 * @param charset	编码格式{@link #GBK}
	 * @return
	 */
	public static String getString(byte[] data, String charset) {
		if (data == null || data.length == 0) {
			return null;
		}
		String str = null;
		try {
			str = new String(data, charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public static byte[] filterBuffer(int length, byte[] buffer) {
		if (buffer == null || length > buffer.length) {
			return null;
		}
		if (length <= 0) {
			return null;
		}
		return filterBuffer(0, length, buffer);
	}

	/**
	 * @category 拷贝buffer
	 * @param srcPos
	 *            buffer起始位置
	 * @param length
	 *            拷贝buffer长度
	 * @param buffer
	 *            原
	 * @return
	 */
	public static byte[] filterBuffer(int srcPos, int length, byte[] buffer) {
		if (buffer == null || length > buffer.length) {
			return null;
		}
		if (length <= 0 || (srcPos + length) > buffer.length) {
			return null;
		}
		byte[] filterArray = new byte[length];
		System.arraycopy(buffer, srcPos, filterArray, 0, length);
		return filterArray;
	}

	/**
	 * 
	 * @param length
	 *            of buffer copy array byte[]
	 * @param buffer
	 * @param cmd
	 * @return
	 */
	public static boolean isArrayEquals(int length, byte[] buffer, byte[] cmd) {
		byte[] saxCmd = filterBuffer(length, buffer);
		if (saxCmd == null || cmd == null) {
			return false;
		}
		if (Arrays.equals(saxCmd, cmd)) {
			return true;
		}

		return false;
	}

	public static boolean isArrayEquals(byte[] buffer, byte[] cmd) {
		byte[] saxCmd = filterBuffer(2, buffer);
		if (saxCmd == null || cmd == null) {
			return false;
		}
		if (Arrays.equals(saxCmd, cmd)) {
			return true;
		}

		return false;
	}

	public static int arrayToInteger(byte[] buffer) {
		int hight = ((buffer[0] << 8) & 0xff00);
		int low = (buffer[1] & 0x00ff);

		return hight | low;
	}

	/**
	 * 由于长度是2个字节，所以最高位与次高位用不到
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] integerToArray(int value) {
		byte[] array = new byte[2];
		array[0] = (byte) ((value & 0xff00) >> 8);
		array[1] = (byte) (value & 0x00ff);
		return array;
	}

	/**
	 * 255进制
	 * 
	 * @param buffer
	 * @return
	 */
	public static int arrayFFToDecimalism(byte[] buffer) {
		int num = 0;
		if (buffer == null) {
			Log.e(TAG, "arrayFFToDecimalism buffer is null");
			return -1;
		}
		int ff = 0;
		for (int i = 0; i < buffer.length; i++) {
			if (i == buffer.length - 1) {
				ff = 1;
			} else {
				ff = (int) Math.pow(255, (buffer.length - i - 1));
			}
			num += (buffer[i] & 0xff) * ff;
		}
		return num;
	}

	/**
	 * 十进制数组数据转换成十进制数据
	 * 
	 * @param buffer
	 * @return
	 */
	public static int arrayNToDecimalism(byte[] buffer) {
		int num = 0;
		if (buffer == null) {
			Log.e(TAG, "getArrayCount buffer is null");
			return -1;
		}
		for (int i = 0; i < buffer.length; i++) {
			int n = (char) buffer[i] - 48;
			Log.d(TAG, "n:" + n);
			if (n <= -1 || n >= 10) {
				Log.e(TAG, "getArrayCount num is illegal");
				return -1;
			}

			num += (i == (buffer.length - 1) ? n : n
					* Math.pow(10, buffer.length - i - 1));
		}
		return num;
	}

	/**
	 * 拆分数据
	 * 
	 * @param buffer
	 * @return
	 */
	public static byte[] splitBuffer(byte[] buffer, int tag) {
		if (buffer == null || buffer.length <= 0) {
			return null;
		}
		byte[] splitBuf = new byte[buffer.length * 2];
		for (int i = 0; i < buffer.length; i++) {

			byte hight = (byte) ((buffer[i] & 0xf0) >> 4);
			byte low = (byte) (buffer[i] & 0x0f);

			splitBuf[i * 2] = (byte) (hight + tag);
			splitBuf[(i * 2) + 1] = (byte) (low + tag);
		}
		return splitBuf;
	}

	/**
	 * 合并拆分数据
	 * 
	 * @param buffer
	 * @return
	 */
	public static byte[] merge(byte[] buffer, int tag) {
		if (buffer == null || buffer.length <= 0) {
			return null;
		}

		byte[] mergeBuf = new byte[buffer.length / 2];
		for (int i = 0; i < mergeBuf.length; i++) {
			int hight = (buffer[i * 2] - tag);
			int low = (buffer[(i * 2) + 1] - tag);
			// 高低位合并
			mergeBuf[i] = (byte) (((hight << 4) & 0xf0) | low & 0x0f);
		}
		return mergeBuf;
	}

}
