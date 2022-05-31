package com.hulo.chatwithimaginaryfriends.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.InteractionDAO;
import com.hulo.chatwithimaginaryfriends.model.InteractionModel;

import java.util.List;

public class InteractionAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public List<InteractionModel> listInteractionModel;

    IInteractionDAO interactionDAO;

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
        ImageButton btnDeleteInteraction;
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
            viewHolder.btnDeleteInteraction = view.findViewById(R.id.btn_delete_interaction);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }


        InteractionModel interactionModel = listInteractionModel.get(i);
        viewHolder.triggerWords.setText(interactionModel.getTriggerWord());
        if(interactionModel.getReplyWord() == null || interactionModel.getReplyWord() == ""){
            viewHolder.replyPattern.setText("No reply words set");
        }else{
            viewHolder.replyPattern.setText(interactionModel.getReplyWord());
        }

        viewHolder.btnDeleteInteraction.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Notification!");
            alertDialog.setIcon(null);
            alertDialog.setMessage("Do you want to delete?");
            alertDialog.setPositiveButton("Yes", (dialogInterface, i1) -> {
                interactionDAO = new InteractionDAO();
                interactionDAO.deleteInteraction(interactionModel.getId());
                listInteractionModel = interactionDAO.getAll();
                this.notifyDataSetChanged();
            });
            alertDialog.setNegativeButton("No", (dialogInterface, i2) -> {

            });
            alertDialog.show();
        });

        return view;
    }
}
