package com.paci.training.android.truongnv92.clock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.paci.training.android.truongnv92.clock.viewmodel.ClockViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private ClockViewModel mClockViewModel;
    Button btnVietNam, btnUSA;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mClockViewModel = new ViewModelProvider(this).get(ClockViewModel.class);
        mClockViewModel.connectToRepository(this);

        btnVietNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Observer cho thời gian Việt Nam
                mClockViewModel.getCurrentTimeVietNamLiveData().observe(MainActivity.this
                        , new Observer<String>() {
                            @Override
                            public void onChanged(String time) {
                                tvTime.setText(time);
                            }
                        });
            }
        });

        btnUSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Observer cho thời gian Hoa Kỳ
                mClockViewModel.getCurrentTimeUSALiveData().observe(MainActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String time) {
                        tvTime.setText(time);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClockViewModel.disConnectToRepository(this);
    }

    private void initView() {
        btnVietNam = findViewById(R.id.btnVietNam);
        btnUSA = findViewById(R.id.btnUSA);
        tvTime = findViewById(R.id.tvTime);
    }
}
