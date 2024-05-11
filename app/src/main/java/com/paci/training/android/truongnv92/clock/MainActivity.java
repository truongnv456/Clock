package com.paci.training.android.truongnv92.clock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.paci.training.android.truongnv92.clock.model.ServiceRepository;
import com.paci.training.android.truongnv92.clock.viewmodel.ClockViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private ClockViewModel mClockViewModel;
    private boolean isTimerRunningVNam = false;
    private boolean isTimerRunningUSA = false;
    private Timer timerVNam, timerUSA;

    Button btnVietNam, btnUSA;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mClockViewModel = new ViewModelProvider(this).get(ClockViewModel.class);

        mClockViewModel.getCurrentTimeVietNamLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String time) {
                if (isTimerRunningVNam) {
                    tvTime.setText(time);
                }
            }
        });

        mClockViewModel.getCurrentTimeUSALiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String time) {
                if (isTimerRunningUSA) {
                    tvTime.setText(time);
                }
            }
        });

        btnVietNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunningUSA) {
                    stopTimerUSA();
                    isTimerRunningUSA = false;
                }
                startTimerVNam();
                isTimerRunningVNam = true;
            }
        });

        btnUSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunningVNam) {
                    stopTimerVNam();
                    isTimerRunningVNam = false;
                }
                startTimerUSA();
                isTimerRunningUSA = true;
            }
        });
    }

    private void initView() {
        btnVietNam = findViewById(R.id.btnVietNam);
        btnUSA = findViewById(R.id.btnUSA);
        tvTime = findViewById(R.id.tvTime);
    }

    private void startTimerVNam() {
        timerVNam = new Timer();
        timerVNam.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mClockViewModel.requestCurrentTimeVietNam();
            }
        }, 0, 1000); // Cập nhật thời gian mỗi giây
    }

    private void stopTimerVNam() {
        if (timerVNam != null) {
            timerVNam.cancel();
        }
    }

    private void stopTimerUSA() {
        if (timerUSA != null) {
            timerUSA.cancel();
        }
    }

    private void startTimerUSA() {
        timerUSA = new Timer();
        timerUSA.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mClockViewModel.requestCurrentTimeUSA();
            }
        }, 0, 1000); // Cập nhật thời gian mỗi giây
    }
}

