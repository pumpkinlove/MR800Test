package com.miaxis.mr800test.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.utils.CommonUtil;
import com.miaxis.mr800test.utils.DateUtil;
import com.pensoon.android.signature.view.SignatureView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

@ContentView(R.layout.activity_pen)
public class PenActivity extends BaseActivity {

    @ViewInject(R.id.signature_frame_view)
    private SignatureView signatureView;

    @ViewInject(R.id.iv_pen_preview)
    private ImageView ivPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        String selected = getIntent().getStringExtra("selected");
        selectedItems = CommonUtil.parseItem(selected);
        String all = getIntent().getStringExtra("allItems");
        allItems = CommonUtil.parseItem(all);
        step = 7;
        testType = getIntent().getStringExtra("testType");
    }

    @Override
    protected void initView() {
        signatureView.setOnSignedListener(onSignedListener);
        signatureView.setPenColor(Color.BLACK/*SharedpreferencesData.getInstance().getPenColorValue()*/);
        signatureView.setMaxWidth(4/*SharedpreferencesData.getInstance().getPenWidth()*/);
    }

    SignatureView.OnSignedListener onSignedListener = new SignatureView.OnSignedListener(){

        @Override
        public void onSigned() {
            // TODO Auto-generated method stub
        }

        @Override
        public void onClear() {
            // TODO Auto-generated method stub
        }

    };

    @Event(R.id.tv_pen_preview)
    private void preview(View view) {
        Bitmap bitmap = Bitmap.createBitmap(signatureView.getTransparentSignatureBitmap());
        ivPreview.setImageBitmap(bitmap);
    }




    @Event(R.id.tv_left)
    private void reTest(View view) {
        signatureView.clear();
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
        allItems.get(step).setStatus("NG");
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
