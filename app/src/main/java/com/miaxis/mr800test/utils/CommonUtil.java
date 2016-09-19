package com.miaxis.mr800test.utils;

import android.os.Environment;
import android.widget.Toast;

import com.miaxis.mr800test.domain.TestItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu.nan on 2016/9/12.
 */
public class CommonUtil {

    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static List<TestItem> parseItem(String str) {
        List<TestItem> items = new ArrayList<>();
        String[] itemArray = str.split("=");
        for(int i=0; i<itemArray.length; i++) {
            String[] array = itemArray[i].split("_");
            TestItem t = new TestItem();
            t.setStep(Integer.valueOf(array[0]));
            t.setCheck(array[1]);
            t.setName(array[2]);
            t.setStatus(array[3]);
            t.setMessage(array[4]);
            t.setOpdate(array[5]);
            t.setOptime(array[6]);
            items.add(t);
        }
        return items;
    }

    public static String parseString(List<TestItem> items) {
        StringBuilder sb = new StringBuilder("");
        for(int i=0; i<items.size(); i++) {
            sb.append(items.get(i).getStep() + "_");
            sb.append(items.get(i).getCheck() + "_");
            sb.append(items.get(i).getName() + "_");
            sb.append(items.get(i).getStatus() + "_");
            sb.append(items.get(i).getMessage() + "_");
            sb.append(items.get(i).getOpdate() + "_");
            if(i == (items.size() - 1)) {
                sb.append(items.get(i).getOptime());
            }else {
                sb.append(items.get(i).getOptime()+"=");
            }
        }
        return sb.toString();
    }

    public static void writeFile(String path, String content) {
        BufferedWriter bw = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), path);
            //第二个参数意义是说是否以append方式添加内容
            bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readFile(File file, StringBuilder sb) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String readline = "";
            while ((readline = br.readLine()) != null) {
                sb.append(readline);
            }
            br.close();
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

}
