package com.miaxis.mr800test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.miaxis.mr800test.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;
@ContentView(R.layout.activity_mic)
public class MicActivity extends BaseActivity {


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

    }
}
