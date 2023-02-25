package com.example.bluetooth;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private Context context;
    BaseViewModel(Context context){
        this.context =context;
    }
}
