package com.miaxis.mr800test.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.miaxis.mr800test.R;
import com.platform.DisplayPassword;
import com.platform.OnEchoListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_password)
public class PasswordActivity extends BaseActivity {

    @ViewInject(R.id.tv_password)
    private TextView tvPassword;

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
        tvPassword.setVisibility(View.GONE);
        DisplayPassword.create(echoListener);
    }

    OnEchoListener echoListener = new OnEchoListener() {

        @Override
        public void openTextBox() {
            // TODO Auto-generated method stub
            tvPassword.setText("");
            tvPassword.setVisibility(View.VISIBLE);
        }

        @Override
        public void echoPassword(String password) {
            // TODO Auto-generated method stub
            tvPassword.setText(password);
        }

        @Override
        public void closeTextBox() {
            // TODO Auto-generated method stub
            tvPassword.setVisibility(View.GONE);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Event(R.id.b1)
    private void b1(View view) {
        echoListener.openTextBox();
    }

    @Event(R.id.b2)
    private void b2(View view) {
        echoListener.echoPassword("78987");
    }

    @Event(R.id.b3)
    private void b3(View view) {
        echoListener.closeTextBox();
    }
}
