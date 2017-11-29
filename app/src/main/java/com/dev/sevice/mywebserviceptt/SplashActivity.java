package com.dev.sevice.mywebserviceptt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 11/29/2017 AD.
 */

public class SplashActivity extends AppCompatActivity{
    private Handler handler;
    private TextView textView;
    private long startTime, currentTime, finishedTime = 0L;
    private int duration = 10000 / 4;
    private int endTime = 0;
    private Activity _activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(R.string.app_name);
        _activity = SplashActivity.this;
        handler = new Handler();
        startTime = Long.valueOf(System.currentTimeMillis());
        currentTime = startTime;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentTime = Long.valueOf(System.currentTimeMillis());
                finishedTime = Long.valueOf(currentTime)
                        - Long.valueOf(startTime);

                if (finishedTime >= duration + 30) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    endTime = (int) (finishedTime / 250);
                    Spannable spannableString = new SpannableString(textView
                            .getText());
                    spannableString.setSpan(new ForegroundColorSpan(
                                    Color.BLACK), 0, endTime,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    textView.setText(spannableString);
                    handler.postDelayed(this, 10);
                }
            }
        }, 10);
    }
}
