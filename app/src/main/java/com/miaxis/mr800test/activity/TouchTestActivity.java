package com.miaxis.mr800test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.utils.CommonUtil;
import com.miaxis.mr800test.utils.DateUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

@ContentView(R.layout.activity_touch_test)
public class TouchTestActivity extends BaseActivity {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.touch1)
    private TextView touch1;

    @ViewInject(R.id.touch2)
    private TextView touch2;

    @ViewInject(R.id.touch3)
    private TextView touch3;

    @ViewInject(R.id.touch4)
    private TextView touch4;

    @ViewInject(R.id.touch5)
    private TextView touch5;

    @ViewInject(R.id.touch6)
    private TextView touch6;

    @ViewInject(R.id.touch7)
    private TextView touch7;

    @ViewInject(R.id.touch8)
    private TextView touch8;

    @ViewInject(R.id.touch9)
    private TextView touch9;

    @ViewInject(R.id.touch10)
    private TextView touch10;

    @ViewInject(R.id.touch11)
    private TextView touch11;

    @ViewInject(R.id.touch12)
    private TextView touch12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        String selected = getIntent().getStringExtra("selected");
        selectedItems = CommonUtil.parseItem(selected);
        String all = getIntent().getStringExtra("allItems");
        allItems = CommonUtil.parseItem(all);
        step = 15;
        testType = getIntent().getStringExtra("testType");
    }

    @Override
    protected void initView() {
        tv_title.setText("触摸屏测试");
        reTest(null);
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
        allItems.get(step).setStatus("NG");
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

    @Event(value = {
            R.id.touch1, R.id.touch2, R.id.touch3, R.id.touch4,
            R.id.touch5, R.id.touch6, R.id.touch7, R.id.touch8,
            R.id.touch9, R.id.touch10, R.id.touch11, R.id.touch12,
    })
    private void onTouch(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.green_dark));
    }

    @Event(R.id.tv_left)
    private void reTest(View view) {
        touch1.setBackgroundColor(getResources().getColor(R.color.white));
        touch2.setBackgroundColor(getResources().getColor(R.color.white));
        touch3.setBackgroundColor(getResources().getColor(R.color.white));
        touch4.setBackgroundColor(getResources().getColor(R.color.white));
        touch5.setBackgroundColor(getResources().getColor(R.color.white));
        touch6.setBackgroundColor(getResources().getColor(R.color.white));
        touch7.setBackgroundColor(getResources().getColor(R.color.white));
        touch8.setBackgroundColor(getResources().getColor(R.color.white));
        touch9.setBackgroundColor(getResources().getColor(R.color.white));
        touch10.setBackgroundColor(getResources().getColor(R.color.white));
        touch11.setBackgroundColor(getResources().getColor(R.color.white));
        touch12.setBackgroundColor(getResources().getColor(R.color.white));
    }
}
