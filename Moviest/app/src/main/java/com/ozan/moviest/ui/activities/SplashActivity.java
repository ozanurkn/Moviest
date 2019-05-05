package com.ozan.moviest.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

import com.ozan.moviest.R;
import com.ozan.moviest.helper.BaseActivity;
import com.ozan.moviest.helper.Constant;

public class SplashActivity extends BaseActivity {
    @Override
    public Context getContext() {
        return SplashActivity.this;
    }

    @Override
    public void initView() {

        new CountDownTimer(Constant.SPLASH_SCREEN_DELAY,1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getContext(),MainActivity.class));
                finish();
            }
        }.start();

    }

    @Override
    public int getLayoutId() {
        return R.layout.splash_activity_layout;
    }

    @Override
    public void onBackPressed() {

    }
}
