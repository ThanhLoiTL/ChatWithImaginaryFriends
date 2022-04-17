package com.android.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.ReplyAdapter;
import com.android.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.android.chatwithimaginaryfriends.dao.impl.InteractionDAO;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.util.ConvertUtil;

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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_reply_pattern);

        EditText txtUpdateReplyPattern = dialog.findViewById(R.id.txt_update_reply_pattern);
        Button btnOkReplyPattern = dialog.findViewById(R.id.btn_ok_reply_pattern);
        Button btnCancelReplyPattern = dialog.findViewById(R.id.btn_cancel_reply_pattern);
        Button btnDeleteReplyPattern = dialog.findViewById(R.id.btn_delete_reply_pattern);

        txtUpdateReplyPattern.setText(listReply.get(position));

        btnCancelReplyPattern.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnOkReplyPattern.setOnClickListener(v -> {
            if(listReply.get(position) != txtUpdateReplyPattern.getText().toString().trim()){
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
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_add_reply_pattern);

            EditText txtAddReplyPattern = dialog.findViewById(R.id.txt_add_reply_pattern);
            Button btnOkReplyPattern = dialog.findViewById(R.id.btn_ok_reply_pattern);
            Button btnCancelReplyPattern = dialog.findViewById(R.id.btn_cancel_reply_pattern);

            btnCancelReplyPattern.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnOkReplyPattern.setOnClickListener(v -> {
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

            });
            dialog.show();
        });
    }
}
