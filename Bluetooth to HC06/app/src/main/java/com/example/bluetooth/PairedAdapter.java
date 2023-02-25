package com.example.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PairedAdapter extends RecyclerView.Adapter<PairedAdapter.ViewHolder> {
    private List<BluetoothDevice> list = new ArrayList<>();

    ItemListener listener;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int postion;

        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.lstContent);

        }

        public TextView getTextView() {
            return textView;
        }
    }


    public void setList(List<BluetoothDevice> list) {
        this.list = list;
        Log.d("TAG", "setList: "+this.list);
        notifyItemRangeChanged(0,list.size());
    }

    public PairedAdapter(Context context, List<BluetoothDevice> devices, ItemListener listener) {
        list = devices;
        this.context = context;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PairedAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        ViewHolder resultView = new PairedAdapter.ViewHolder(view);
        resultView.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listener.pairedItemClick(list.get(resultView.getLayoutPosition()));
            }
        });
        return resultView;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PairedAdapter.ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "onBindViewHolder: no permission");

        }
        Log.d("TAG", "onBindViewHolder: "+list.get(position).getName());
        viewHolder.getTextView().setText(list.get(position).getName() + "\n" + list.get(position).getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
