package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import java.util.List;

public class HeartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HeartModel> listHeartModel;

    public HeartAdapter(Context context, int layout, List<HeartModel> listHeartModel) {
        this.context = context;
        this.layout = layout;
        this.listHeartModel = listHeartModel;
    }

    @Override
    public int getCount() {
        return listHeartModel.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        TextView heartName, heartDescription;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.heartName = view.findViewById(R.id.heart_name);
            viewHolder.heartDescription = view.findViewById(R.id.heart_description);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HeartModel heartModel = listHeartModel.get(i);
        viewHolder.heartName.setText(heartModel.getHeartName());
        viewHolder.heartDescription.setText(heartModel.getDescription());

        return view;
    }
}
