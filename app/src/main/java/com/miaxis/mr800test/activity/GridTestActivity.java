package com.miaxis.mr800test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaxis.mr800test.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_grid_test)
public class GridTestActivity extends BaseActivity {

    @ViewInject(R.id.tv_middle)
    private TextView tvMiddle;

    @ViewInject(R.id.tv_left)
    private TextView tvLeft;

//    @ViewInject(R.id.tv_io_test)
//    private TextView tvIoTest;
//
//    @ViewInject(R.id.tv_base_info)
//    private TextView tvBaseInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvMiddle.setText("单功能测试");
        tvLeft.setText("返回");
        tvLeft.setVisibility(View.VISIBLE);
    }

    @Event(R.id.tv_left)
    private void leftClick(View view) {
        finish();
    }

    @Event(R.id.tv_base_info)
    private void toBaseInfo(View view) {
        startActivityForResult(new Intent(this, BaseInfoActivity.class), 1);
    }

    @Event(R.id.tv_io_test)
    private void toIoTest(View view) {
        startActivityForResult(new Intent(this, IOActivity.class), 2);
    }

    @Event(R.id.tv_io_test)
    private void toFingerTest(View view) {
        startActivityForResult(new Intent(this, IOActivity.class), 2);
    }
}
