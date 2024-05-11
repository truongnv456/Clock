package com.paci.training.android.truongnv92.clock.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.paci.training.android.truongnv92.clock.model.ServiceRepository;

public class ClockViewModel extends AndroidViewModel {
    private ServiceRepository mClockRepository;

    private MutableLiveData<String> currentTimeVietNamLiveData = new MutableLiveData<>();
    private MutableLiveData<String> currentTimeUSALiveData = new MutableLiveData<>();

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

    public void getTimeRunnableVietNam() {
        mClockRepository.getTimeRunnableVietNam(new ServiceRepository.TimeCallback() {
            @Override
            public void onTimeReceived(String time) {
                currentTimeVietNamLiveData.postValue(time);
            }
        });
    }

    public void getTimeRunnableUSA() {
        mClockRepository.getTimeRunnableUsa(new ServiceRepository.TimeCallback() {
            @Override
            public void onTimeReceived(String time) {
                currentTimeVietNamLiveData.postValue(time);
            }
        });
    }

    public void disConnectToRepository(Context context) {
        mClockRepository.unbindService(context);
    }
}


