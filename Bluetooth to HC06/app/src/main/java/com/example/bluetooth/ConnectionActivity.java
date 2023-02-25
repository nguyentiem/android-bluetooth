package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat.Action;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectionActivity extends AppCompatActivity {
    InputStream taInput;
    OutputStream taOut;
    int st = 0;
    BluetoothDevice pairedBluetoothDevice = null;
    BluetoothSocket blsocket = null;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public final String TAG = "ConnectionActivity";
    EditText mess;
    ImageView send;
    Button connect;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        mess = findViewById(R.id.mess);
        send = findViewById(R.id.send);
        connect = findViewById(R.id.connect);
        status = findViewById(R.id.status);
        String s = getIntent().getStringExtra("device");
        Log.d(TAG, "onCreate: " + s);

        pairedBluetoothDevice = (BluetoothDevice) getIntent().getParcelableExtra("device");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        Log.d(TAG, "onCreate: " + pairedBluetoothDevice.getName());

        if (st == 0) {
           connect(pairedBluetoothDevice);
        }
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (st == 0) {
                    connect(pairedBluetoothDevice);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mess.getText().length()>0 ){
                    if(st==1){
                        send2Bluetooth(mess.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(),"not connet to blue, reconnect ",Toast.LENGTH_LONG).show();
                        connect(pairedBluetoothDevice);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "empty mess",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void connect(BluetoothDevice device) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "no permission", Toast.LENGTH_LONG).show();
            }
            blsocket = device.createInsecureRfcommSocketToServiceRecord(uuid);

            blsocket.connect();
            pairedBluetoothDevice = device;
            progress.dismiss();
            st = 1;
            status.setText("connected");
            Toast.makeText(getApplicationContext(), "Device paired successfully!", Toast.LENGTH_LONG).show();
        } catch (IOException ioe) {
            progress.dismiss();
            st = 0;
            status.setText("connect fail");
            Log.e("taha>", "cannot connect to device :( " + ioe);
            Toast.makeText(getApplicationContext(), "Could not connect", Toast.LENGTH_LONG).show();
            pairedBluetoothDevice = null;
        }
    }

    void send2Bluetooth(String s) {
        //make sure there is a paired device
        if (pairedBluetoothDevice != null && blsocket != null) {
            try {  taOut = blsocket.getOutputStream();
                for(char l :s.toCharArray()){

                    taOut.write(l);
                }


                taOut.flush();
            } catch (IOException ioe) {
                Log.e("app>", "Could not open a output stream " + ioe);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onStop() {
        super.onStop();
    }

}