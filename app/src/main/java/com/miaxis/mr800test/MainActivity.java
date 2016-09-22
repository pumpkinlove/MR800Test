package com.miaxis.mr800test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.miaxis.mr800test.activity.BaseActivity;
import com.miaxis.mr800test.activity.ConfigActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

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

    @Event(R.id.tv_single_test)
    private void testStart(View view){
        startActivity(new Intent(this, ConfigActivity.class));
    }

    @Event(R.id.tv_test_del)
    private void deleteAll(View view) {
        String PATH_LAI_LIAO = "MR800_test_lai_liao.txt";
        String PATH_GONG_NENG = "MR800_test_gong_neng.txt";
        String PATH_FU_CE = "MR800_test_fu_ce.txt";
        String PATH_WEI_XIU = "MR800_test_wei_xiu.txt";

        File file = new File(Environment.getExternalStorageDirectory(), PATH_LAI_LIAO);
        if (file == null || !file.exists() || file.isDirectory()) {
            return;
        }
        file.delete();

        file = new File(Environment.getExternalStorageDirectory(), PATH_GONG_NENG);
        if (file == null || !file.exists() || file.isDirectory()) {
            return;
        }
        file.delete();

        file = new File(Environment.getExternalStorageDirectory(), PATH_FU_CE);
        if (file == null || !file.exists() || file.isDirectory()) {
            return;
        }
        file.delete();

        file = new File(Environment.getExternalStorageDirectory(), PATH_WEI_XIU);
        if (file == null || !file.exists() || file.isDirectory()) {
            return;
        }
        file.delete();
    }

}
