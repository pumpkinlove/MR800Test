package com.miaxis.mr800test.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.app.MyApplication;
import com.miaxis.mr800test.domain.SendPwdEvent;
import com.miaxis.mr800test.io.AbFitBin;
import com.miaxis.mr800test.io.Action;
import com.miaxis.mr800test.io.Command;
import com.miaxis.mr800test.io.FitBinPSBC;
import com.miaxis.mr800test.io.FitPactManager;
import com.miaxis.mr800test.io.Hid;
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

@ContentView(R.layout.activity_key_board)
public class KeyBoardActivity extends BaseActivity {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_pwd)
    private TextView tv_pwd;

    @ViewInject(R.id.tv_pwd_flag)
    private TextView tv_pwd_flag;

    private boolean isPwdOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

        isPwdOn = true;
        tv_pwd_flag.setText("密码键盘已开启,点击关闭");
        tv_pwd_flag.setTextColor(getResources().getColor(R.color.green_dark));
        byte[] buffer = new byte[1];
        buffer[0] = (byte)(0x82);
        FitPactManager fitPactManager = MyApplication.getPwdSerial();
        fitPactManager.getAbIO().write(buffer, 1);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        String selected = getIntent().getStringExtra("selected");
        selectedItems = CommonUtil.parseItem(selected);
        String all = getIntent().getStringExtra("allItems");
        allItems = CommonUtil.parseItem(all);
        step = 4;
        testType = getIntent().getStringExtra("testType");
    }

    @Override
    protected void initView() {
        tv_title.setText("密码键盘测试");
    }

    @Event(R.id.tv_middle)
    private void pass(View view) {
        allItems.get(step).setStatus("通过");
        allItems.get(step).setOpdate(DateUtil.toMonthDay(new Date()));
        allItems.get(step).setOptime(DateUtil.toHourMinString(new Date()));

        CommonUtil.writeFile(testType, CommonUtil.parseString(allItems));

        selectedItems.clear();
        for(int i=0; i<allItems.size(); i++) {
            if("1".equals(allItems.get(i).getCheck())) {
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
        for(int i=0; i<allItems.size(); i++) {
            if("1".equals(allItems.get(i).getCheck())) {
                selectedItems.add(allItems.get(i));
            }
        }

        Intent i = new Intent(this, getNextStep(step, selectedItems));
        i.putExtra("allItems",CommonUtil.parseString(allItems));
        i.putExtra("selected", CommonUtil.parseString(selectedItems));
        i.putExtra("testType", testType);
        startActivity(i);

    }

    @Event(R.id.tv_left)
    private void reTest(View view) {
        tv_pwd.setText("");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendPwd(SendPwdEvent event) {
        tv_pwd.append(""+event.getIndex());
        if ("1234567890-2-1".equals(tv_pwd.getText().toString())) {
            tv_pwd.setTextColor(getResources().getColor(R.color.green_dark));
            tv_pwd.append("\n通过");
        } else {
            tv_pwd.setTextColor(getResources().getColor(R.color.dark));
        }
        if ("-1".equals(event.getIndex()+"")) {
            tv_pwd_flag.setText("密码键盘已关闭,点击开启");
            tv_pwd_flag.setTextColor(getResources().getColor(R.color.red));
            isPwdOn = false;
            byte[] buffer = new byte[1];
            buffer[0] = (byte)(0x83);
            FitPactManager fitPactManager = MyApplication.getPwdSerial();
            fitPactManager.getAbIO().write(buffer, 1);
        }
    }

    @Event(R.id.tv_pwd_flag)
    private void openPwd(View view) {
        if(!isPwdOn){
            isPwdOn = true;
            tv_pwd_flag.setText("密码键盘已开启,点击关闭");
            tv_pwd_flag.setTextColor(getResources().getColor(R.color.green_dark));
            tv_pwd.setText("");
            byte[] buffer = new byte[1];
            buffer[0] = (byte)(0x82);
            FitPactManager fitPactManager = MyApplication.getPwdSerial();
            fitPactManager.getAbIO().write(buffer, 1);
        }else{
            tv_pwd_flag.setText("密码键盘已关闭,点击开启");
            tv_pwd_flag.setTextColor(getResources().getColor(R.color.red));
            isPwdOn = false;
            byte[] buffer = new byte[1];
            buffer[0] = (byte)(0x83);
            FitPactManager fitPactManager = MyApplication.getPwdSerial();
            fitPactManager.getAbIO().write(buffer, 1);
        }

    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}
