package com.miaxis.mr800test.activity;

import android.support.v7.app.AppCompatActivity;

import com.miaxis.mr800test.domain.TestItem;
import java.util.List;

/**
 * Created by xu.nan on 2016/9/7.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initData();
    protected abstract void initView();

    protected int step;
    protected String testType;
    protected List<TestItem> allItems;
    protected List<TestItem> selectedItems;

    protected Class getNextStep(int curStep, List<TestItem> itemList) {
        if(itemList == null) {
            return null;
        }
        return getClassByName(itemList.get(curStep).getName());
    }

    protected Class getClassByName(String name) {
        if("硬件信息".equals(name)) {
            return BaseInfoActivity.class;
        }
        if("黑白屏测试".equals(name)) {
            return ScreenTestActivity.class;
        }
        if("密码键盘测试".equals(name)) {
            return KeyBoardActivity.class;
        }
        if("摄像头测试".equals(name)) {
            return CameraActivity.class;
        }
        if("喇叭测试".equals(name)) {
            return VoiceActivity.class;
        }
        if("USB测试".equals(name)) {
            return USBActivity.class;
        }
        if("串口测试".equals(name)) {
            return PortActivity.class;
        }
        if("签名笔测试".equals(name)) {
            return PenActivity.class;
        }
        if("SD卡测试".equals(name)) {
            return SDActivity.class;
        }
        if("点钞机测试".equals(name)) {
            return CountActivity.class;
        }
        if("IC卡测试".equals(name)) {
            return ICActivity.class;
        }
        if("非接触式IC卡测试".equals(name)) {
            return FICActivity.class;
        }
        if("麦克风测试".equals(name)) {
            return MicActivity.class;
        }
        if("WIFI测试".equals(name)) {
            return WifiActivity.class;
        }
        if("二代证测试".equals(name)) {
            return IDActivity.class;
        }
        return null;
    }

}
