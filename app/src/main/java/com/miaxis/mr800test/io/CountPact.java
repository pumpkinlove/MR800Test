package com.miaxis.mr800test.io;

/**
 * Created by xu.nan on 2016/10/18.
 */

public class CountPact extends AbPact {

    public static final String TAG = "CountPact";
    public static final int SPLIT = 0x30;
    public static final int STX = 0x02;
    public static final int ETX = 0x03;
    public static final int PACKAGE_MIN_LENGHT = 8;

    @Override
    public byte[] filterPackage(byte[] buffer, int length) {
        return new byte[0];
    }

    @Override
    public byte[] makePackage(byte[] buffer, int length) {
        return new byte[0];
    }

    @Override
    public byte[] peelPackage(byte[] buffer, int length) {
        return new byte[0];
    }
}
