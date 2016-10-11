package com.miaxis.mr800test.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.miaxis.mr800test.utils.CommonUtil;
import com.miaxis.mr800test.utils.DateUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;

@ContentView(R.layout.activity_touch_test)
public class TouchTestActivity extends BaseActivity {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.iv_touch)
    private ImageView iv_touch;

    @ViewInject(R.id.rl_edge_test)
    private RelativeLayout rl_edge_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
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
        step = 3;
        testType = getIntent().getStringExtra("testType");

    }

    @Override
    protected void initView() {
        tv_title.setText("触摸屏测试");
        reTest(null);
        iv_touch.setOnTouchListener(new TouchListener());
        rl_edge_test.setVisibility(View.VISIBLE);
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

    @Event(R.id.tv_left)
    private void reTest(View view) {
        rl_edge_test.setVisibility(View.VISIBLE);
    }

    private class TouchListener implements View.OnTouchListener {

        private PointF startPoint = new PointF();
        private Matrix matrix = new Matrix();
        private Matrix currentMaritx = new Matrix();

        private int mode = 0; // 用于标记模式
        private static final int DRAG = 1; // 拖动
        private static final int ZOOM = 2; // 放大
        private float startDis = 0;
        private PointF midPoint; // 中心点

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = DRAG; // 拖拽
                    currentMaritx.set(iv_touch.getImageMatrix()); // 记录ImageView当前移动位置
                    startPoint.set(event.getX(), event.getY()); // 开始点
                    break;
                case MotionEvent.ACTION_MOVE:// 移动事件
                    if (mode == DRAG) { // 图片拖动事件
                        float dx = event.getX() - startPoint.x; // x轴移动距离
                        float dy = event.getY() - startPoint.y;
                        matrix.set(currentMaritx); // 在当前的位置基础上移动
                        matrix.postTranslate(dx, dy);
                    } else if (mode == ZOOM) { // 图片放大事件
                        float endDis = distance(event); // 结束距离
                        if (endDis > 10f) {
                            float scale = endDis / startDis; // 放大倍数
                            matrix.set(currentMaritx);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mode = 0;
                    break;
                // 有手指离开屏幕，但屏幕还有触点（手指）
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                // 当屏幕上已经有触点（手指），再有一个手指压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = ZOOM;
                    startDis = distance(event);
                    if (startDis > 10f) { // 避免手指上有两个
                        midPoint = mid(event);
                        currentMaritx.set(iv_touch.getImageMatrix()); // 记录当前的缩放倍数
                    }
                    break;
            }
            // 显示缩放后的图片
            iv_touch.setImageMatrix(matrix);
            return true;
        }

    }

    /**
     * 计算两点之间的距离
     *
     * @param event
     * @return
     */
    public static float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 计算两点之间的中间点
     *
     * @param event
     * @return
     */
    public static PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }

    @Event(value = {R.id.btn_touch1,R.id.btn_touch2,R.id.btn_touch3,R.id.btn_touch4,R.id.btn_touch5,R.id.btn_touch6,R.id.btn_touch7,R.id.btn_touch8,})
    private void edgeTouch(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.green_dark));
    }

    @Event(R.id.btn_edge_pass)
    private void edgePass(View view) {
        rl_edge_test.setVisibility(View.GONE);
    }

    @Event(R.id.btn_edge_re)
    private void edgeRe(View view) {
        findViewById(R.id.btn_touch1).setBackgroundColor(getResources().getColor(R.color.red));
        findViewById(R.id.btn_touch2).setBackgroundColor(getResources().getColor(R.color.red));
        findViewById(R.id.btn_touch3).setBackgroundColor(getResources().getColor(R.color.red));
        findViewById(R.id.btn_touch4).setBackgroundColor(getResources().getColor(R.color.red));
        findViewById(R.id.btn_touch5).setBackgroundColor(getResources().getColor(R.color.red));
        findViewById(R.id.btn_touch6).setBackgroundColor(getResources().getColor(R.color.red));
        findViewById(R.id.btn_touch7).setBackgroundColor(getResources().getColor(R.color.red));
        findViewById(R.id.btn_touch8).setBackgroundColor(getResources().getColor(R.color.red));

    }

}
