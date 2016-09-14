package com.miaxis.mr800test.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miaxis.mr800test.R;
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

@ContentView(R.layout.activity_io)
public class IOActivity extends BaseActivity {

    private String testPath = "test.txt";

    @ViewInject(R.id.et_write)
    private EditText etWrite;

    @ViewInject(R.id.tv_read)
    private TextView tvRead;

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

    @Event(R.id.tv_io_read)
    private void readTest(View view) {
        if(testPath == null) {
            Toast.makeText(this, "文件缺失", Toast.LENGTH_LONG).show();
            return;
        }
        BufferedReader br = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), testPath);
            br = new BufferedReader(new FileReader(file));
            String readline = "";
            StringBuffer sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                sb.append(readline);
            }
            br.close();
            tvRead.setText(sb.toString());
            Toast.makeText(this, "读取成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null){
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getDefaultFilePath() {
        if(!CommonUtil.isSdCardExist()){
            return null;
        }
        String filepath = "";
        File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        if (file.exists()) {
            filepath = file.getAbsolutePath();
        } else {
            filepath = "文件不存在";
        }
        return filepath;
    }

    @Event(R.id.tv_io_write)
    private void writeTest(View view) {
        if(testPath == null) {
            Toast.makeText(this, "SD卡缺失", Toast.LENGTH_LONG).show();
            return;
        }
        if("文件不存在".equals(testPath)) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
            testPath = "test.txt";
        }
        BufferedWriter bw = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), testPath);
            //第二个参数意义是说是否以append方式添加内容
            bw = new BufferedWriter(new FileWriter(file, false));
            String info = etWrite.getText().toString();
            bw.write(info);
            bw.flush();
            Toast.makeText(this, "写入成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
