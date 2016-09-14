package com.miaxis.mr800test.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

    }

    @Override
    protected void initData() {
        step = 0;
        allItems = new ArrayList<>();
        selectItems = new ArrayList<>();
        adapter = new TestItemAdapter(allItems, this);
    }

    @Override
    protected void initView() {
        tv_left.setVisibility(View.INVISIBLE);
        tv_right.setVisibility(View.INVISIBLE);
        tv_middle.setText("开始测试");
        rv_items.setLayoutManager(new LinearLayoutManager(this));
        rv_items.setAdapter(adapter);

    }

    @Event(R.id.lai_liao)
    private void testLaiLiao(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.blue_band_dark3));
        tv_gong_neng.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fu_ce.setBackgroundColor(getResources().getColor(R.color.white));
        tv_wei_xiu.setBackgroundColor(getResources().getColor(R.color.white));
        loadFile(PATH_LAI_LIAO);
        testType = PATH_LAI_LIAO;
    }

    @Event(R.id.gong_neng)
    private void testGongneng(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.blue_band_dark3));
        tv_lai_liao.setBackgroundColor(getResources().getColor(R.color.white));
        tv_fu_ce.setBackgroundColor(getResources().getColor(R.color.white));
        tv_wei_xiu.setBackgroundColor(getResources().getColor(R.color.white));
        loadFile(PATH_GONG_NENG);
        testType = PATH_GONG_NENG;
    }

    @Event(R.id.fu_ce)
    private void testFuCe(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.blue_band_dark3));
        tv_gong_neng.setBackgroundColor(getResources().getColor(R.color.white));
        tv_lai_liao.setBackgroundColor(getResources().getColor(R.color.white));
        tv_wei_xiu.setBackgroundColor(getResources().getColor(R.color.white));
        loadFile(PATH_FU_CE);
        testType = PATH_FU_CE;
    }

    @Event(R.id.wei_xiu)
    private void testWeiXiu(View view) {
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

        item.setCheck("0");
        item.setName("硬件信息");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("黑白屏测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("密码键盘测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("摄像头测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("喇叭测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("USB测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("主串口测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("签名笔测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("SD卡测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("点钞机测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("IC卡测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("非接触式IC卡测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("麦克风测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("WIFI测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        item = new TestItem();
        item.setCheck("0");
        item.setName("二代证测试");
        item.setStatus("未通过");
        item.setMessage("无");
        item.setOpdate("无");
        item.setOptime("无");
        items.add(item);

        return items;
    }

    @Event(R.id.tv_middle)
    private void startTest(View view) {
        CommonUtil.writeFile(testType, CommonUtil.parseString(allItems));
        for(int i=0; i<allItems.size(); i++) {
            if("1".equals(allItems.get(i).getCheck())) {
                selectItems.add(allItems.get(i));
            }
        }

        Intent i = new Intent(this, getNextStep(step, selectItems));
        i.putExtra("allItems",CommonUtil.parseString(allItems));
        i.putExtra("selected", CommonUtil.parseString(selectItems));
        i.putExtra("step", step + 1);
        startActivity(i);

    }


}
