package com.paci.training.android.truongnv92.clock;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.paci.training.android.truongnv92.clock.model.ServiceRepository;
import com.paci.training.android.truongnv92.clock.viewmodel.ClockViewModel;
import com.paci.training.android.truongnv92.clock.viewmodel.ClockViewModelFactory;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private ClockViewModel mClockViewModel;
    private boolean isTimerRunningVNam = false;
    private boolean isTimerRunningUSA = false;
    Button btnVietNam, btnUSA;
    TextView tvTime;
    // SharedPreferences
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "MySharedPref";
    private static final String KEY_TIME = "time";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        ClockViewModelFactory factory = new ClockViewModelFactory(getApplication());
        mClockViewModel = new ViewModelProvider(this, factory).get(ClockViewModel.class);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Đọc thời gian từ SharedPreferences khi khởi động ứng dụng
        String savedTime = sharedPreferences.getString(KEY_TIME, "");
        tvTime.setText(savedTime);

        btnVietNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunningUSA) {
                    mClockViewModel.stopTimers();
                    isTimerRunningUSA = false;
                }
                mClockViewModel.startTimerVietNam();
                tvTime.setText(mClockViewModel.getCurrentTimeVietNamLiveData().getValue());
                mClockViewModel.getCurrentTimeVietNamLiveData().observe(MainActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String time) {
                        if (isTimerRunningVNam) {
                            tvTime.setText(time);
                            saveTimeToSharedPreferences(time);
                        }
                    }
                });
                isTimerRunningVNam = true;
            }
        });

        btnUSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunningVNam) {
                    mClockViewModel.stopTimers();
                    isTimerRunningVNam = false;
                }
                mClockViewModel.startTimerUSA();
                tvTime.setText(mClockViewModel.getCurrentTimeUSALiveData().getValue());
                mClockViewModel.getCurrentTimeUSALiveData().observe(MainActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String time) {
                        if (isTimerRunningUSA) {
                            tvTime.setText(time);
                            saveTimeToSharedPreferences(time);
                        }
                    }
                });
                isTimerRunningUSA = true;
            }
        });
    }

    // Phương thức lưu thời gian vào SharedPreferences
    private void saveTimeToSharedPreferences(String time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TIME, time);
        editor.apply();
    }

    private void initView() {
        btnVietNam = findViewById(R.id.btnVietNam);
        btnUSA = findViewById(R.id.btnUSA);
        tvTime = findViewById(R.id.tvTime);
    }
}


