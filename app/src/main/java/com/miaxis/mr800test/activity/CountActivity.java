package com.miaxis.mr800test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.domain.SendPwdEvent;
import com.miaxis.mr800test.domain.SerialReadEvent;
import com.miaxis.mr800test.io.AbFitBin;
import com.miaxis.mr800test.io.FitBinPSBC;
import com.miaxis.mr800test.io.FitPactManager;
import com.miaxis.mr800test.io.OnIOListener;
import com.miaxis.mr800test.io.PactCCB;
import com.miaxis.mr800test.io.Serial;
import com.miaxis.mr800test.utils.CommonUtil;
import com.miaxis.mr800test.utils.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

@ContentView(R.layout.activity_count)
public class CountActivity extends BaseActivity implements FitPactManager.OnFitPactListener{

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_left)
    private TextView tv_left;

    @ViewInject(R.id.et_write)
    private EditText et_write;

    @ViewInject(R.id.tv_read)
    private TextView tv_read;

    private static FitPactManager testSerial;
    private static AbFitBin abFitBin;

    public static final String DEVICE_MAIN_SERIAL = "/dev/ttySWK1";
    public static final int DEVICE_BAUD = 9600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

//        testSerial.start();
        EventBus.getDefault().register(this);

        byte[] buffer = new byte[1];
        buffer[0] = (byte)(0x82);
        testSerial.getAbIO().write(buffer, 1);

    }

    @Override
    protected void initData() {
        String selected = getIntent().getStringExtra("selected");
        selectedItems = CommonUtil.parseItem(selected);
        String all = getIntent().getStringExtra("allItems");
        allItems = CommonUtil.parseItem(all);
        step = 6;
        testType = getIntent().getStringExtra("testType");
        testSerial = new FitPactManager(new Serial(DEVICE_MAIN_SERIAL, DEVICE_BAUD), new PactCCB(), this);
        abFitBin = new FitBinPSBC();
    }

    @Override
    protected void initView() {
        tv_title.setText("点钞机串口测试");
        tv_left.setText("写入数据");
    }

    @Event(R.id.tv_middle)
    private void pass(View view) {
        allItems.get(step).setStatus("通过");
        allItems.get(step).setOpdate(DateUtil.toMonthDay(new Date()));
        allItems.get(step).setOptime(DateUtil.toHourMinString(new Date()));

        CommonUtil.writeFile(testType, CommonUtil.parseString(allItems));

        selectedItems.clear();
        for (int i=0; i<allItems.size(); i++) {
            if ("1".equals(allItems.get(i).getCheck())) {
                selectedItems.add(allItems.get(i));
            }
        }

        Intent i = new Intent(this, getNextStep(step, selectedItems));
        i.putExtra("allItems",CommonUtil.parseString(allItems));
        i.putExtra("selected", CommonUtil.parseString(selectedItems));
        i.putExtra("testType", testType);
        startActivity(i);

    }

    @Event(R.id.tv_right)
    private void ng(View view) {

        allItems.get(step).setStatus("失败");
        allItems.get(step).setOpdate(DateUtil.toMonthDay(new Date()));
        allItems.get(step).setOptime(DateUtil.toHourMinString(new Date()));

        CommonUtil.writeFile(testType, CommonUtil.parseString(allItems));

        selectedItems.clear();

        for (int i=0; i<allItems.size(); i++) {
            if ("1".equals(allItems.get(i).getCheck())) {
                selectedItems.add(allItems.get(i));
            }
        }

        Intent i = new Intent(this, getNextStep(step, selectedItems));
        i.putExtra("allItems",CommonUtil.parseString(allItems));
        i.putExtra("selected", CommonUtil.parseString(selectedItems));
        i.putExtra("testType", testType);
        startActivity(i);

    }

    @Override
    public void onFitData(byte[] buffer, String deviceName, OnIOListener onIOListener) {
        String read = new String(buffer);
        SerialReadEvent event = new SerialReadEvent();
        event.setMessage(read);
        EventBus.getDefault().post(event);
        Log.e("-----",deviceName + "_____" + buffer.length);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendPwd(SerialReadEvent event) {
        tv_read.setText(event.getMessage());
        if (event.getMessage().equals(et_write.getText().toString())) {
            tv_read.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tv_read.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Event(R.id.tv_left)
    private void write(View view) {

        tv_read.setText("");
        String write = et_write.getText().toString();
        if (write == null || write.length() < 1) {
            return;
        }
        byte[] buffer = new byte[2];
        buffer[0] = 0x49;
        buffer[1] = 0x54;
        testSerial.getAbIO().write(buffer,  2);

    }

    @Override
    public void finish() {
        EventBus.getDefault().unregister(this);
        testSerial.stop();
        super.finish();
    }

    @Override
    protected void onPause() {
        testSerial.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        testSerial.start();
        super.onResume();
    }
}
