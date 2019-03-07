package com.zhongchuang.canting.allive.pusher.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.zhongchuang.canting.R;

//import com.aliyun.alivcsolution.R;

public class LicenseActivity extends Activity {

    private ImageView mBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        mBack = (ImageView) findViewById(R.id.iv_back);
        mBack.setOnClickListener(mListener);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.iv_back) {
                finish();

            }
        }
    };
}