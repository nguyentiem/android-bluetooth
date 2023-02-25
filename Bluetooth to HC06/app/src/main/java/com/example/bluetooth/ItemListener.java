package com.example.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface ItemListener {
    void scanItemClick(BluetoothDevice device);
    void pairedItemClick(BluetoothDevice device);
}
