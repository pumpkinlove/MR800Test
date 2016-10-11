package com.miaxis.mr800test.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.adapter.TestItemAdapter;
import com.miaxis.mr800test.domain.TestItem;
import com.miaxis.mr800test.utils.CommonUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_config)
public class ConfigActivity extends BaseActivity {

    @ViewInject(R.id.tv_left)
    private TextView tv_left;
    @ViewInject(R.id.tv_middle)
    private TextView tv_middle;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;

    @ViewInject(R.id.lai_liao)
    private TextView tv_lai_liao;
    @ViewInject(R.id.gong_neng)
    private TextView tv_gong_neng;
    @ViewInject(R.id.fu_ce)
    private TextView tv_fu_ce;
    @ViewInject(R.id.wei_xiu)
    private TextView tv_wei_xiu;

    @ViewInject(R.id.cb_all)
    private CheckBox cb_all;

    @ViewInject(R.id.rv_items)
    private RecyclerView rv_items;

    private List<TestItem> allItems;
    private List<TestItem> selectItems;

    private TestItemAdapter adapter;

    private final static String PATH_LAI_LIAO = "MR800_test_lai_liao.txt";
    private final static String PATH_GONG_NENG = "MR800_test_gong_neng.txt";
    private final static String PATH_FU_CE = "MR800_test_fu_ce.txt";
    private final static String PATH_WEI_XIU = "MR800_test_wei_xiu.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();
        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (int i=0; i<allItems.size(); i++) {
                    if (b) {
                        allItems.get(i).setCheck("1");
                    } else {
                        allItems.get(i).setCheck("0");
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void initData() {
        allItems = new ArrayList<>();
        selectItems = new ArrayList<>();
        adapter = new TestItemAdapter(allItems, this);
    }

    @Override
    protected void initView() {
        cb_all.setVisibility(View.INVISIBLE);
        tv_left.setVisibility(View.INVISIBLE);
        tv_right.setVisibility(View.INVISIBLE);
        tv_middle.setText("开始测试");
        rv_items.setLayoutManager(new LinearLayoutManager(this));
        rv_items.setAdapter(adapter);
    }

    @Event(R.id.lai_liao)
    private void testLaiLiao(View view) {
        cb_all.setVisibility(View.VISIBLE);
        cb_all.setChecked(false);
        view.setBackgroundColor(getResources().getColor(R.color.blue_band_dark3));
        tv_gong_neng.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fu_ce.setBackgroundColor(getResources().getColor(R.color.white));
        tv_wei_xiu.setBackgroundColor(getResources().getColor(R.color.white));
        loadFile(PATH_LAI_LIAO);
        testType = PATH_LAI_LIAO;
    }

    @Event(R.id.gong_neng)
    private void testGongneng(View view) {
        cb_all.setVisibility(View.VISIBLE);
        cb_all.setChecked(false);
        view.setBackgroundColor(getResources().getColor(R.color.blue_band_dark3));
        tv_lai_liao.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fu_ce.setBackgroundColor(getResources().getColor(R.color.white));
        tv_wei_xiu.setBackgroundColor(getResources().getColor(R.color.white));
        loadFile(PATH_GONG_NENG);
        testType = PATH_GONG_NENG;
    }

    @Event(R.id.fu_ce)
    private void testFuCe(View view) {
        cb_all.setVisibility(View.VISIBLE);
        cb_all.setChecked(false);
        view.setBackgroundColor(getResources().getColor(R.color.blue_band_dark3));
        tv_gong_neng.setBackgroundColor(getResources().getColor(R.color.white));
        tv_lai_liao.setBackgroundColor(getResources().getColor(R.color.white));
        tv_wei_xiu.setBackgroundColor(getResources().getColor(R.color.white));
        loadFile(PATH_FU_CE);
        testType = PATH_FU_CE;
    }

    @Event(R.id.wei_xiu)
    private void testWeiXiu(View view) {
        cb_all.setVisibility(View.VISIBLE);
        cb_all.setChecked(false);
        view.setBackgroundColor(getResources().getColor(R.color.blue_band_dark3));
        tv_gong_neng.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fu_ce.setBackgroundColor(getResources().getColor(R.color.white));
        tv_lai_liao.setBackgroundColor(getResources().getColor(R.color.white));
        loadFile(PATH_WEI_XIU);
        testType = PATH_WEI_XIU;
    }

    private void loadFile(String path) {
        allItems.clear();
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if(!file.exists()) {
            Toast.makeText(this, path + " 文件不存在, 即将写入新", Toast.LENGTH_LONG).show();
            allItems = fetchNewList();
            CommonUtil.writeFile(path, CommonUtil.parseString(allItems));
            adapter.setItems(allItems);
            adapter.notifyDataSetChanged();
        }else{
            StringBuilder sb = new StringBuilder();
            CommonUtil.readFile(file, sb);
            if(sb.toString().length() == 0) {
                return;
            }
            allItems = CommonUtil.parseItem(sb.toString());
            adapter.setItems(allItems);
            adapter.notifyDataSetChanged();
        }
    }

    private List<TestItem> fetchNewList() {
        List<TestItem> items = new ArrayList<>();

        TestItem item = new TestItem();

        item.setStep(0);
        item.setCheck("0");
        item.setName("硬件信息");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(1);
        item.setCheck("0");
        item.setName("签名笔测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(2);
        item.setCheck("0");
        item.setName("黑白屏测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(3);
        item.setCheck("0");
        item.setName("触摸屏测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(4);
        item.setCheck("0");
        item.setName("密码键盘测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(5);
        item.setCheck("0");
        item.setName("串口测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(6);
        item.setCheck("0");
        item.setName("点钞机测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(7);
        item.setCheck("0");
        item.setName("摄像头测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(8);
        item.setCheck("0");
        item.setName("喇叭测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(9);
        item.setCheck("0");
        item.setName("USB测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(10);
        item.setCheck("0");
        item.setName("SD卡测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(11);
        item.setCheck("0");
        item.setName("IC卡测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(12);
        item.setCheck("0");
        item.setName("非接触式IC卡测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(13);
        item.setCheck("0");
        item.setName("麦克风测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(14);
        item.setCheck("0");
        item.setName("WIFI测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setStep(15);
        item.setCheck("0");
        item.setName("二代证测试");
        item.setStatus("未测试");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        return items;
    }

    @Event(R.id.tv_middle)
    private void startTest(View view) {
        if(allItems == null || allItems.size() == 0) {
            Toast.makeText(this, "请选择测试步骤",Toast.LENGTH_LONG).show();
            return;
        }
        CommonUtil.writeFile(testType, CommonUtil.parseString(allItems));
        selectItems.clear();
        for(int i=0; i<allItems.size(); i++) {
            if("1".equals(allItems.get(i).getCheck())) {
                selectItems.add(allItems.get(i));
            }
        }
        if(selectItems == null || selectItems.size() == 0) {
            Toast.makeText(this, "请勾选需要测试的项目",Toast.LENGTH_LONG).show();
            return;
        }
        Class nextClass = getNextStep(-1, selectItems);
        if(nextClass == null) {
            return;
        }
        Intent i = new Intent(this, nextClass);
        i.putExtra("allItems",CommonUtil.parseString(allItems));
        i.putExtra("selected", CommonUtil.parseString(selectItems));
        i.putExtra("testType", testType);
        i.putExtra("step", step + 1);
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
