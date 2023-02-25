package com.example.bluetooth;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;

import android.content.Context;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

public class MainViewModel extends BaseViewModel {

    MainViewModel(Context context) {
        super(context);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
