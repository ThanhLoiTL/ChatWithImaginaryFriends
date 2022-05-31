package com.hulo.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.adapter.ReplyAdapter;
import com.hulo.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.InteractionDAO;
import com.hulo.chatwithimaginaryfriends.model.InteractionModel;
import com.hulo.chatwithimaginaryfriends.util.ConvertUtil;

import java.util.List;

public class ReplyFragment extends ListFragment {
    public List<String> listReply;
    ReplyAdapter replyAdapter;

    ImageButton btnAddReplyPattern;
    InteractionModel interactionModel;
    IInteractionDAO interactionDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reply_pattern, container, false);
        interactionDAO = new InteractionDAO();
        btnAddReplyPattern = view.findViewById(R.id.btn_add_reply_pattern);
        interactionModel = (InteractionModel) getArguments().getSerializable("InteractionModel");
        listReply = ConvertUtil.stringToArray(interactionModel.getReplyWord());
        replyAdapter = new ReplyAdapter(getActivity(), R.layout.row_trigger_word, listReply);
        setListAdapter(replyAdapter);

        addReplyPattern();
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_update);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        TextView title = dialog.findViewById(R.id.title);
        title.setText("Edit Reply Pattern");

        EditText txtUpdateReplyPattern = dialog.findViewById(R.id.txt_input);
        txtUpdateReplyPattern.setHint("Please input a reply pattern");
        TextView btnOkReplyPattern = dialog.findViewById(R.id.btn_ok);
        TextView btnCancelReplyPattern = dialog.findViewById(R.id.btn_cancel);
        TextView btnDeleteReplyPattern = dialog.findViewById(R.id.btn_delete);

        txtUpdateReplyPattern.setText(listReply.get(position));

        btnCancelReplyPattern.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnOkReplyPattern.setOnClickListener(v -> {
            if(txtUpdateReplyPattern.getText().toString().trim().equals("")){
                Toast.makeText(v.getContext(), "Please Fill All Properties", Toast.LENGTH_LONG).show();
            }else {
                if (listReply.get(position) != txtUpdateReplyPattern.getText().toString().trim()) {
                    listReply.set(position, txtUpdateReplyPattern.getText().toString().trim());
                    String s = ConvertUtil.arrayToString(listReply);
                    interactionModel.setReplyWord(s);
                    int isSuccess = interactionDAO.updateInteraction(interactionModel);
                    if (isSuccess == 1) {
                        Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    interactionModel = interactionDAO.findOne(interactionModel.getId());
                    listReply = replyAdapter.listReply = ConvertUtil.stringToArray(interactionModel.getReplyWord());
                    replyAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        btnDeleteReplyPattern.setOnClickListener(v -> {
            listReply.remove(position);
            String s = ConvertUtil.arrayToString(listReply);
            interactionModel.setReplyWord(s);
            int isSuccess = interactionDAO.updateInteraction(interactionModel);
            if (isSuccess == 1) {
                Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
            interactionModel = interactionDAO.findOne(interactionModel.getId());
            listReply = replyAdapter.listReply = ConvertUtil.stringToArray(interactionModel.getReplyWord());
            replyAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }
    private void addReplyPattern() {
        btnAddReplyPattern.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_add);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Add Reply Pattern");
            EditText txtAddReplyPattern = dialog.findViewById(R.id.txt_input);
            txtAddReplyPattern.setHint("Please input a reply pattern");
            TextView btnOkReplyPattern = dialog.findViewById(R.id.btn_ok);
            TextView btnCancelReplyPattern = dialog.findViewById(R.id.btn_cancel);

            btnCancelReplyPattern.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnOkReplyPattern.setOnClickListener(v -> {
                if(txtAddReplyPattern.getText().toString().trim().equals("")){
                    Toast.makeText(v.getContext(), "Please Fill All Properties", Toast.LENGTH_LONG).show();
                }else {
                    listReply.add(txtAddReplyPattern.getText().toString().trim());
                    String s = ConvertUtil.arrayToString(listReply);
                    interactionModel.setReplyWord(s);
                    int isSuccess = interactionDAO.updateInteraction(interactionModel);
                    if (isSuccess == 1) {
                        Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    interactionModel = interactionDAO.findOne(interactionModel.getId());
                    listReply = replyAdapter.listReply = ConvertUtil.stringToArray(interactionModel.getReplyWord());
                    replyAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }
}
