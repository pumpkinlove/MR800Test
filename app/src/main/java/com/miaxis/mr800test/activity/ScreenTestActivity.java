package com.miaxis.mr800test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.utils.CommonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_screen_test)
public class ScreenTestActivity extends BaseActivity {

    private boolean flag = true;

    @ViewInject(R.id.ll_screen)
    private LinearLayout ll_screen;

    @ViewInject(R.id.ll_tilte)
    private LinearLayout ll_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        String selected = getIntent().getStringExtra("selected");
        selectedItems = CommonUtil.parseItem(selected);
        String all = getIntent().getStringExtra("all");
        allItems = CommonUtil.parseItem(all);
        step = getIntent().getIntExtra("step", -1);
        testType = getIntent().getStringExtra("testType");
    }

    @Override
    protected void initView() {
        ll_title.setVisibility(View.GONE);
    }

    @Event(R.id.ll_screen)
    private void changeColor(View view) {
        if(flag) {
            view.setBackgroundColor(getResources().getColor(R.color.white));
            flag = false;
        }else{
            ll_title.setVisibility(View.VISIBLE);
        }

    }

    @Event(R.id.tv_left)
    private void reTest(View view) {
        flag = true;
        ll_screen.setBackgroundColor(getResources().getColor(R.color.black));
        ll_title.setVisibility(View.GONE);
    }

    @Event(value = {R.id.tv_middle, R.id.tv_right})
    private void goNext(View view) {
        CommonUtil.writeFile(testType, CommonUtil.parseString(allItems));
        for(int i=0; i<allItems.size(); i++) {
            if("1".equals(allItems.get(i).getCheck())) {
                selectedItems.add(allItems.get(i));
            }
        }

        Intent i = new Intent(this, getNextStep(step, selectedItems));
        i.putExtra("allItems",CommonUtil.parseString(allItems));
        i.putExtra("selected", CommonUtil.parseString(selectedItems));
        i.putExtra("step", step + 1);
        startActivity(i);

    }
}
