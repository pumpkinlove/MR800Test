package com.miaxis.mr800test.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import com.miaxis.mr800test.R;
import com.miaxis.mr800test.utils.CommonUtil;
import com.miaxis.mr800test.utils.DateUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

@ContentView(R.layout.activity_base_info)
public class BaseInfoActivity extends BaseActivity {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_cpu)
    private TextView tv_cpu;
    @ViewInject(R.id.tv_ram)
    private TextView tv_ram;
    @ViewInject(R.id.tv_sd)
    private TextView tv_sd;
    @ViewInject(R.id.tv_resolution)
    private TextView tv_resolution;
    @ViewInject(R.id.tv_size)
    private TextView tv_size;
    @ViewInject(R.id.tv_edition)
    private TextView tv_edition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initData();
        initView();
        fetchCpuInfo();
        fetchEdition();
        fetchStorage();
        fetchWidthHeight();
    }

    @Override
    protected void initData() {
        String selected = getIntent().getStringExtra("selected");
        selectedItems = CommonUtil.parseItem(selected);
        String all = getIntent().getStringExtra("allItems");
        allItems = CommonUtil.parseItem(all);
        step = 0;
        testType = getIntent().getStringExtra("testType");
    }

    @Override
    protected void initView() {
        tv_title.setText("硬件信息");
    }

    private void fetchCpuInfo() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<getCpuInfo().length; i++) {
            sb.append(getCpuInfo()[i]+"\n");
        }
        tv_cpu.setText(sb.toString());
    }

    private void fetchEdition() {
        tv_edition.setText(android.os.Build.VERSION.RELEASE);
    }

    public String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"",""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return cpuInfo;
    }

    public String formatSize(long size) {
        String suffix = null;
        float fSize=0;

        if (size >= 1024) {
            suffix = "KB";
            fSize=size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public void fetchStorage(){
        File romPath = Environment.getDataDirectory();
        StatFs stat = new StatFs(romPath.getPath());
        long blockCount = stat.getBlockCount();
        long blockSize = stat.getBlockSize();
        long romSize = blockCount * blockSize;
        tv_ram.append(formatSize(romSize));
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdPath = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdPath.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long sdSize = bSize * bCount;
            tv_sd.append(formatSize(sdSize));
        }

    }

    private void fetchWidthHeight() {

        Display mDisplay = getWindowManager().getDefaultDisplay();
        int W = mDisplay.getWidth();
        int H = mDisplay.getHeight();
        Log.i("Main", "Width = " + W);
        Log.i("Main", "Height = " + H);
        tv_resolution.setText(W+" * "+H);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;// 密度值
        float xdpi = metrics.xdpi;
//        float ydpi = metrics.ydpi;
//        double zdpi = Math.sqrt(Math.pow(xdpi, 2) + Math.pow(ydpi, 2));
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        double z = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));

        // 根据实践，我个人觉得xdpi这个值也可以这么理解，真正的dpi / xdpi = density ,
        // 所以要获取真正的dpi就成了，xdpi*density,所以最后，根据勾股定理算对角线像素，除以dpi，就算出屏幕尺寸了
        double f = (z / (xdpi * density));
        tv_size.setText(f+"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Event(R.id.tv_left)
    private void reTest(View view) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings","com.android.settings.DisplaySettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        startActivityForResult( intent , 0);

//        getStorage();
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

}
