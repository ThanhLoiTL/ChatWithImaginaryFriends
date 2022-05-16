package com.android.chatwithimaginaryfriends.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.dao.IChatDAO;
import com.android.chatwithimaginaryfriends.dao.impl.ChatDAO;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.model.ChatModel;
import com.android.chatwithimaginaryfriends.util.ConvertUtil;
import com.android.chatwithimaginaryfriends.view.ChatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class BotAPIAdapter extends BaseAdapter {
    private Context context;
    public List<ChatModel> listChat;

    private IChatDAO chatDAO;

    public BotAPIAdapter(Context context, List<ChatModel> listChat) {
        this.context = context;
        this.listChat = listChat;
    }

    @Override
    public int getCount() {
        return listChat.size();
    }

    @Override
    public Object getItem(int i) {
        return listChat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView message, time;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        chatDAO = new ChatDAO();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null) {
            viewHolder = new ViewHolder();
            if(listChat.get(i).getIsSend()){
                view = inflater.inflate(R.layout.user_msg_rv_item, null);
            }else{
                view = inflater.inflate(R.layout.bot_msg_rv_item, null);
            }
            viewHolder.message = view.findViewById(R.id.message);
            viewHolder.time = view.findViewById(R.id.time);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ChatModel chatModel = listChat.get(i);
        viewHolder.message.setText(chatModel.getMessage());
        viewHolder.time.setText(chatModel.getTime());
        viewHolder.message.setOnClickListener(v -> {
            boolean visible;
            if(viewHolder.time.getVisibility() == View.GONE){
                visible = false;
            }else {
                visible = true;
            }
            viewHolder.time.setVisibility(visible? v.GONE : v.VISIBLE);
        });

        viewHolder.message.setOnLongClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext(), R.style.BottomSheetDialogTheme);
            View bottomSheetView = inflater.inflate(R.layout.bottom_sheet, null);

            bottomSheetView.findViewById(R.id.cancel).setOnClickListener(v1 -> {
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.remove).setOnClickListener(v1 -> {
                chatDAO.deleteChat(chatModel.getId());
                listChat = ChatActivity.listChat = chatDAO.findByCharacter(chatModel.getCharterId());
                ChatActivity.setAdapterChat();
                notifyDataSetChanged();
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.edit_content).setOnClickListener(v1 -> {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_add);
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                TextView title = dialog.findViewById(R.id.title);
                title.setText("Edit message");
                EditText txtEdit = dialog.findViewById(R.id.txt_input);
                txtEdit.setText(chatModel.getMessage());
                txtEdit.setHint("Please input message");
                TextView btnOk = dialog.findViewById(R.id.btn_ok);
                TextView btnCancel = dialog.findViewById(R.id.btn_cancel);

                btnOk.setOnClickListener(v2 -> {
                    String text = txtEdit.getText().toString().trim();
                    chatModel.setMessage(text);
                    int isSuccess = chatDAO.updateChat(chatModel);
                    if (isSuccess == 1) {
                        Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    listChat = ChatActivity.listChat = chatDAO.findByCharacter(chatModel.getCharterId());
                    ChatActivity.setAdapterChat();
                    notifyDataSetChanged();
                    dialog.dismiss();
                    bottomSheetDialog.dismiss();
                });
                btnCancel.setOnClickListener(v2 -> {
                    dialog.dismiss();
                    bottomSheetDialog.dismiss();
                });

                dialog.show();
            });

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
            return false;
        });
        return view;
    }


}
