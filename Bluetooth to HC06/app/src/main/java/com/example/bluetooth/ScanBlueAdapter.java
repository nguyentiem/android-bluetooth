package com.example.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScanBlueAdapter extends RecyclerView.Adapter<ScanBlueAdapter.ViewHolder> {

    private List <BluetoothDevice> list = new ArrayList<>();

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
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

    private Context context;
    ItemListener listener;

    public ScanBlueAdapter(Context context, List<BluetoothDevice> devices, ItemListener listener) {
        list = devices;
        this.context = context;
        this.listener = listener;
    }

    public void setAdapter() {

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        viewHolder.getTextView().setText(list.get(position).getName() + "\n" + list.get(position).getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}