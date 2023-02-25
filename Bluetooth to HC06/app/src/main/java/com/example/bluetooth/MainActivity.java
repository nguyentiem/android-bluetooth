package com.example.bluetooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ItemListener {
    private static final String[] BLE_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final String[] ANDROID_12_BLE_PERMISSIONS = new String[]{
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private Button search;
    private static final int BT_ENABLE_REQUEST = 10; // This is the code we use for BT Enable
    private static final int SETTINGS = 20;
    private UUID mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private int mBufferSize = 50000; //Default
    public static final String DEVICE_EXTRA = "com.example.anysensormonitoring.SOCKET";
    public static final String DEVICE_UUID = "com.example.anysensormonitoring.uuid";
    private static final String DEVICE_LIST = "com.example.anysensormonitoring.devicelist";
    private static final String DEVICE_LIST_SELECTED = "com.example.anysensormonitoring.devicelistselected";
    public static final String BUFFER_SIZE = "com.example.anysensormonitoring.buffersize";
    private static final String TAG = "MainActivity";
    BluetoothAdapter mBluetoothAdapter;
    RecyclerView scanDevive, pairedDevice;
    PairedAdapter pairAdapter;
    ScanBlueAdapter scanAdapter;

    public static void requestBlePermissions(Activity activity, int requestCode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            ActivityCompat.requestPermissions(activity, ANDROID_12_BLE_PERMISSIONS, requestCode);
        else
            ActivityCompat.requestPermissions(activity, BLE_PERMISSIONS, requestCode);
    }

    MainViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.scan);
        scanDevive = findViewById(R.id.listBlue);
        pairedDevice = findViewById(R.id.listPaired);
        model = new ViewModelProvider(this, new BaseViewModelFactory(this)).get(MainViewModel.class);
        pairAdapter = new PairedAdapter(this, new ArrayList<BluetoothDevice>(), this);
        scanAdapter = new ScanBlueAdapter(this, new ArrayList<>(), this);
        scanDevive.setLayoutManager(new LinearLayoutManager(this));
        scanDevive.setAdapter(scanAdapter);
        pairedDevice.setLayoutManager(new LinearLayoutManager(this));
        pairedDevice.setAdapter(pairAdapter);

        requestBlePermissions(this, 110);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 110);
            }
        }


        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        List<BluetoothDevice> s = new ArrayList<BluetoothDevice>();
        for (BluetoothDevice bt : pairedDevices) {
            s.add(bt);
        }
        Log.d(TAG, "onCreate: " + s.size());
        pairAdapter.setList(s);


        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 110) {

        }
    }

    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onStop() {
// TODO Auto-generated method stub
        super.onStop();
    }


    @Override
    public void scanItemClick(BluetoothDevice device) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        Log.d(TAG, "scanItemClick: " + device.getName() + " " + device.getAddress());
        Gson gson = new Gson();
        String json = gson.toJson(device);
        Intent myIntent = new Intent(this, ConnectionActivity.class);
        myIntent.putExtra("device", json); //Optional parameters
        this.startActivity(myIntent);
    }

    @Override
    public void pairedItemClick(BluetoothDevice device) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        Log.d(TAG, "scanItemClick: " + device.getName() + " " + device.getAddress());

        Intent myIntent = new Intent(this, ConnectionActivity.class);
        myIntent.putExtra("device", device); //Optional parameters
        this.startActivity(myIntent);
    }
}