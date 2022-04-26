package com.android.chatwithimaginaryfriends.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.HeartModel;

import java.util.List;

public class ComboBoxHeartAdapter extends ArrayAdapter<HeartModel> {
    public ComboBoxHeartAdapter(@NonNull Context context, int resource, @NonNull List<HeartModel> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView itemSelected = convertView.findViewById(R.id.item_selected);
        HeartModel heart = this.getItem(position);
        if(heart != null) {
            itemSelected.setText(heart.getHeartName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_combobox, parent, false);
        TextView tvItem = convertView.findViewById(R.id.tv_item);
        HeartModel heart = this.getItem(position);
        if(heart != null) {
            tvItem.setText(heart.getHeartName());
        }
        return convertView;
    }
}
