package com.paci.training.android.truongnv92.clock.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.paci.training.android.truongnv92.clock.model.ServiceRepository;

import java.util.Timer;
import java.util.TimerTask;

public class ClockViewModel extends AndroidViewModel {
    private ServiceRepository mClockRepository;
    private MutableLiveData<String> currentTimeVietNamLiveData = new MutableLiveData<>();
    private MutableLiveData<String> currentTimeUSALiveData = new MutableLiveData<>();
    private static Timer timerVietNam, timerUSA;

    public ClockViewModel(Application application) {
        super(application);
        mClockRepository = new ServiceRepository();
        mClockRepository.bindService(application);
    }

    public LiveData<String> getCurrentTimeVietNamLiveData() {
        return currentTimeVietNamLiveData;
    }

    public LiveData<String> getCurrentTimeUSALiveData() {
        return currentTimeUSALiveData;
    }

    public void requestCurrentTimeVietNam() {
        currentTimeVietNamLiveData.postValue(mClockRepository.getCurrentTimeVietNam());
    }

    public void requestCurrentTimeUSA() {
        currentTimeUSALiveData.postValue(mClockRepository.getCurrentTimeUsa());
    }

    public void startTimerVietNam() {
        if (timerVietNam == null) {
            timerVietNam = new Timer();
            timerVietNam.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            currentTimeVietNamLiveData.postValue(mClockRepository.getCurrentTimeVietNam());
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    public void startTimerUSA() {
        if (timerUSA == null) {
            timerUSA = new Timer();
            timerUSA.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            currentTimeUSALiveData.postValue(mClockRepository.getCurrentTimeUsa());
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    public void stopTimers() {
        if (timerVietNam != null) {
            timerVietNam.cancel();
            timerVietNam = null;
        }
        if (timerUSA != null) {
            timerUSA.cancel();
            timerUSA = null;
        }
    }

    public void disConnectToRepository(Context context) {
        mClockRepository.unbindService(context);
    }
}


