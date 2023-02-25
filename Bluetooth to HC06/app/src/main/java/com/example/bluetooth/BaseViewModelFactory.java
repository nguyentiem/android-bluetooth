package com.example.bluetooth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BaseViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public BaseViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(context);
        } else{
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }

}