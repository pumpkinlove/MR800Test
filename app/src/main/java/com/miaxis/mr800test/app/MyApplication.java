package com.miaxis.mr800test.app;

import android.app.Application;
import android.view.View;

import com.miaxis.mr800test.domain.SendPwdEvent;
import com.miaxis.mr800test.io.AbFitBin;
import com.miaxis.mr800test.io.FitBinPSBC;
import com.miaxis.mr800test.io.FitPactManager;
import com.miaxis.mr800test.io.Hid;
import com.miaxis.mr800test.io.OnIOListener;
import com.miaxis.mr800test.io.PactCCB;
import com.miaxis.mr800test.io.Serial;

import org.xutils.x;

/**
 * Created by xu.nan on 2016/9/20.
 */
public class MyApplication extends Application implements FitPactManager.OnFitPactListener {

    public static final String DEVICE_NAME_HID = "/dev/hidg0";
    public static final String DEVICE_NAME_SERIAL = "/dev/ttyS1";
    public static final String DEVICE_PWD_SERIAL = "/dev/ttySWK3";
    public static final int DEVICE_BAUD = 9600;

    private static FitPactManager fitPactManagerHid;
    private static FitPactManager fitPactManagerSerial;
    private static FitPactManager pwdSerial;

    private static AbFitBin abFitBin;

    @Override
    public void onCreate() {
        x.Ext.init(this);
        super.onCreate();

        initConfiguration();
    }

    private void initConfiguration() {
        //通道协议
//        fitPactManagerHid = new FitPactManager(
//                new Hid(DEVICE_NAME_HID), new PactCCB(), this);
//        fitPactManagerSerial = new FitPactManager(new Serial(
//                DEVICE_NAME_SERIAL, DEVICE_BAUD), new PactCCB(),
//                this);

        pwdSerial = new FitPactManager(new Serial(
                DEVICE_PWD_SERIAL, DEVICE_BAUD), new PactCCB(),
                this);
        //匹配Bin文件
        abFitBin = new FitBinPSBC();
//        abFitView = new FitViewTest();
//        fitPactManagerSerial.start();
        pwdSerial.start();
//        fitPactManagerHid.start();
    }

    @Override
    public void onFitData(byte[] buffer, String deviceName, OnIOListener onIOListener) {
        if(abFitBin != null){
            abFitBin.fitBin(buffer, deviceName, onIOListener);
        }
    }

    public static FitPactManager getFitPactManagerHid() {
        return fitPactManagerHid;
    }

    public static FitPactManager getFitPactManagerSerial() {
        return fitPactManagerSerial;
    }

    public static FitPactManager getPwdSerial() {
        return pwdSerial;
    }

    public static AbFitBin getAbFitBin() {
        return abFitBin;
    }
}