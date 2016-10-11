package com.miaxis.mr800test.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Point;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
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

    @ViewInject(R.id.tv_cpu_need)
    private TextView tv_cpu_need;
    @ViewInject(R.id.tv_ram_need)
    private TextView tv_ram_need;
    @ViewInject(R.id.tv_sd_need)
    private TextView tv_sd_need;
    @ViewInject(R.id.tv_resolution_need)
    private TextView tv_resolution_need;
    @ViewInject(R.id.tv_size_need)
    private TextView tv_size_need;
    @ViewInject(R.id.tv_edition_need)
    private TextView tv_edition_need;

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
        tv_size.setText(getScreenInch(this)+"英寸");
        autoJudge();
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
        tv_cpu.setText(getCpuInfo()[0].toString());
        tv_cpu.append("\n" + Double.valueOf(getMaxCpuFreq()) / 1000000.00d + "GHz");
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

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        float W =dm.widthPixels*dm.density;
        float H =dm.heightPixels*dm.density;

        tv_resolution.setText(W+" * "+H);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Event(R.id.tv_left)
    private void reTest(View view) {
        fetchCpuInfo();
        fetchEdition();
        fetchStorage();
        fetchWidthHeight();
        tv_size.setText(getScreenInch(this)+"英寸");
        autoJudge();

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

    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = { "/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }
    // 获取CPU最小频率（单位KHZ）
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = { "/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" };
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }
    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public double getScreenInch(Activity context) {
        double mInch = 0.0d;
        try {
            int realWidth = 0, realHeight = 0;
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getRealMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }

            mInch =(Math.sqrt((realWidth/metrics.xdpi) * (realWidth /metrics.xdpi) + (realHeight/metrics.ydpi) * (realHeight / metrics.ydpi)));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return mInch;
    }

    private void autoJudge() {
        if (tv_cpu.getText().equals(tv_cpu_need.getText())) {
           tv_cpu.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tv_cpu.setTextColor(getResources().getColor(R.color.red));
        }

        if (tv_ram.getText().equals(tv_ram_need.getText())) {
            tv_ram.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tv_ram.setTextColor(getResources().getColor(R.color.red));
        }

        if (tv_sd.getText().equals(tv_sd_need.getText())) {
            tv_sd.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tv_sd.setTextColor(getResources().getColor(R.color.red));
        }

        if (tv_resolution.getText().equals(tv_resolution_need.getText())) {
            tv_resolution.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tv_resolution.setTextColor(getResources().getColor(R.color.red));
        }

        if (tv_size.getText().equals(tv_size_need.getText())) {
            tv_size.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tv_size.setTextColor(getResources().getColor(R.color.red));
        }

        if (tv_edition.getText().equals(tv_edition_need.getText())) {
            tv_edition.setTextColor(getResources().getColor(R.color.green_dark));
        } else {
            tv_edition.setTextColor(getResources().getColor(R.color.red));
        }
    }


}
