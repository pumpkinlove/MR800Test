package com.miaxis.mr800test.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.activity.BaseActivity;
import com.miaxis.mr800test.activity.ConfigActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    String PATH_LAI_LIAO    = "MR800_test_lai_liao.txt";
    String PATH_GONG_NENG   = "MR800_test_gong_neng.txt";
    String PATH_FU_CE       = "MR800_test_fu_ce.txt";
    String PATH_WEI_XIU     = "MR800_test_wei_xiu.txt";

    @ViewInject(R.id.tv_version)
    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initData();
        initView();
        initVersion();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Event(R.id.tv_single_test)
    private void testStart(View view) {
        startActivity(new Intent(this, ConfigActivity.class));
    }

    @Event(R.id.tv_test_del)
    private void deleteAll(View view) {

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

    private void initVersion() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            tv_version.append(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
