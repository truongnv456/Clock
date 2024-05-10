package com.paci.training.android.truongnv92.clock.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.paci.training.android.truongnv92.clock.model.ServiceRepository;

public class ClockViewModel extends ViewModel {
    private ServiceRepository mClockRepository;

    public ClockViewModel() {
        mClockRepository = new ServiceRepository();
    }

    private MutableLiveData<String> currentTimeVietNamLiveData = new MutableLiveData<>();
    private MutableLiveData<String> currentTimeUSALiveData = new MutableLiveData<>();

    // Getter cho LiveData
    public LiveData<String> getCurrentTimeVietNamLiveData() {
        return currentTimeVietNamLiveData;
    }

    public LiveData<String> getCurrentTimeUSALiveData() {
        return currentTimeUSALiveData;
    }

    public void connectToRepository(Context context) {
        mClockRepository.bindService(context);
        // Lắng nghe thay đổi thời gian và cập nhật LiveData
        mClockRepository.getTimeRunnableVietNam(new ServiceRepository.TimeCallback() {
            @Override
            public void onTimeReceived(String time) {
                currentTimeVietNamLiveData.postValue(time);
            }
        });

        mClockRepository.getTimeRunnableUsa(new ServiceRepository.TimeCallback() {
            @Override
            public void onTimeReceived(String time) {
                currentTimeUSALiveData.postValue(time);
            }
        });
    }

    public void disConnectToRepository(Context context) {
        mClockRepository.unbindService(context);
    }

    public String getCurrentTimeVietNam() {
        return mClockRepository.getCurrentTimeVietNam();
    }

    public String getCurrentTimeUsa() {
        return mClockRepository.getCurrentTimeUsa();
    }
}

