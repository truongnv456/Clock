package com.paci.training.android.truongnv92.clock.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ClockViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public ClockViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClockViewModel.class)) {
            return (T) new ClockViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

