package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.model.InteractionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InteractionAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<InteractionModel> listInteractionModel;

    public InteractionAdapter(Context context, int layout, List<InteractionModel> listInteractionModel) {
        this.context = context;
        this.layout = layout;
        this.listInteractionModel = listInteractionModel;
    }

    @Override
    public int getCount() {
        return listInteractionModel.size();
    }

    @Override
    public Object getItem(int i) {
        return listInteractionModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class ViewHolder {
        TextView triggerWords, replyPattern;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.triggerWords = view.findViewById(R.id.trigger_words);
            viewHolder.replyPattern = view.findViewById(R.id.reply_pattern);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
//        List<String> arrWords = new ArrayList<String>();
//        arrWords.add("hello");
//        arrWords.add("Hi");
//        arrWords.add("Xin chao");
//        String triggerWords = String.join(", ", arrWords);
//        List<String> words = stringToArray(triggerWords);

        InteractionModel interactionModel = listInteractionModel.get(i);
        viewHolder.triggerWords.setText(interactionModel.getTriggerWord());
        viewHolder.replyPattern.setText(interactionModel.getReplyWord());
        return view;
    }

//    private List<String> stringToArray(String triggerWords) {
//        List<String> arrString = Arrays.asList(triggerWords.split(","));
//        return arrString;
//    }
}
