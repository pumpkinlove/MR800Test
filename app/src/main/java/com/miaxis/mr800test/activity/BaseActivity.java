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
        if(curStep == -1) {
            return getClassByStep(itemList.get(0).getStep());
        }
        if(itemList == null || itemList.size() == 0) {
            return null;
        }
        int next = 0;
        for(int i=0; i<itemList.size(); i++) {
            if(itemList.get(i).getStep() == curStep) {
                if (( i + 1 ) == itemList.size()) {
                   return ConfigActivity.class;
                }
                next = i + 1;
                break;
            }
        }

        return getClassByStep(itemList.get(next).getStep());
    }

    protected Class getClassByStep(int step) {
        switch (step) {
            case 0:
                return BaseInfoActivity.class;
            case 1:
                return ScreenTestActivity.class;
            case 2:
                return KeyBoardActivity.class;
            case 3:
                return CameraActivity.class;
            case 4:
                return VoiceActivity.class;
            case 5:
                return USBActivity.class;
            case 6:
                return PortActivity.class;
            case 7:
                return PenActivity.class;
            case 8:
                return SDActivity.class;
            case 9:
                return CountActivity.class;
            case 10:
                return ICActivity.class;
            case 11:
                return FICActivity.class;
            case 12:
                return MicActivity.class;
            case 13:
                return WifiActivity.class;
            case 14:
                return IDActivity.class;
            case 15:
                return TouchTestActivity.class;
            default:
                return null;
        }
    }

}
