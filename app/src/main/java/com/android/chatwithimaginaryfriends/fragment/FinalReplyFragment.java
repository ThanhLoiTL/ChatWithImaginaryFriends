package com.android.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.FinalReplyAdapter;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.util.ConvertUtil;

import java.util.List;

public class FinalReplyFragment extends ListFragment {
    public List<String> listFinalReply;
    private FinalReplyAdapter finalReplyAdapter;

    private HeartModel heartModel;
    private ImageButton btnAddFinalReply;
    private IHeartDAO heartDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_final_reply, container, false);
        heartDAO = new HeartDAO();
        heartModel = (HeartModel) getArguments().getSerializable("Heart");
        heartModel = heartDAO.findOne(heartModel.getId());
        btnAddFinalReply = view.findViewById(R.id.btn_add_final_reply);
        listFinalReply = ConvertUtil.stringToArray(heartModel.getFinalReply());
        finalReplyAdapter = new FinalReplyAdapter(getActivity(), R.layout.row_trigger_word, listFinalReply);
        setListAdapter(finalReplyAdapter);

        addFinalReply();
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
        title.setText("Edit Final Reply");

        EditText txtUpdateFinalReply = dialog.findViewById(R.id.txt_input);
        txtUpdateFinalReply.setHint("Please input a final reply");
        TextView btnOkFinalReply = dialog.findViewById(R.id.btn_ok);
        TextView btnCancelFinalReply = dialog.findViewById(R.id.btn_cancel);
        TextView btnDeleteFinalReply = dialog.findViewById(R.id.btn_delete);

        txtUpdateFinalReply.setText(listFinalReply.get(position));

        btnCancelFinalReply.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnOkFinalReply.setOnClickListener(v -> {
            if(txtUpdateFinalReply.getText().toString().trim().equals("")){
                Toast.makeText(v.getContext(), "Please Fill All Properties", Toast.LENGTH_LONG).show();
            }else {
                if (listFinalReply.get(position) != txtUpdateFinalReply.getText().toString()) {
                    listFinalReply.set(position, txtUpdateFinalReply.getText().toString().trim());
                    String s = ConvertUtil.arrayToString(listFinalReply);
                    heartModel.setFinalReply(s);
                    int isSuccess = heartDAO.updateHeart(heartModel);
                    if (isSuccess == 1) {
                        Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    heartModel = heartDAO.findOne(heartModel.getId());
                    listFinalReply = finalReplyAdapter.listFinalReply = ConvertUtil.stringToArray(heartModel.getFinalReply());
                    finalReplyAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        btnDeleteFinalReply.setOnClickListener(v -> {
            listFinalReply.remove(position);
            String s = ConvertUtil.arrayToString(listFinalReply);
            heartModel.setFinalReply(s);
            int isSuccess = heartDAO.updateHeart(heartModel);
            if (isSuccess == 1) {
                Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
            heartModel = heartDAO.findOne(heartModel.getId());
            listFinalReply = finalReplyAdapter.listFinalReply = ConvertUtil.stringToArray(heartModel.getFinalReply());
            finalReplyAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void addFinalReply() {
        btnAddFinalReply.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_add);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Add Final Reply");
            EditText txtAddFinalReply = dialog.findViewById(R.id.txt_input);
            txtAddFinalReply.setHint("Please input a final reply");
            TextView btnOkFinalReply = dialog.findViewById(R.id.btn_ok);
            TextView btnCancelFinalReply = dialog.findViewById(R.id.btn_cancel);

            btnCancelFinalReply.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnOkFinalReply.setOnClickListener(v -> {
                if(txtAddFinalReply.getText().toString().trim().equals("")){
                    Toast.makeText(v.getContext(), "Please Fill All Properties", Toast.LENGTH_LONG).show();
                }else {

                    listFinalReply.add(txtAddFinalReply.getText().toString().trim());
                    String s = ConvertUtil.arrayToString(listFinalReply);
                    heartModel.setFinalReply(s);
                    int isSuccess = heartDAO.updateHeart(heartModel);
                    if (isSuccess == 1) {
                        Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    heartModel = heartDAO.findOne(heartModel.getId());
                    listFinalReply = finalReplyAdapter.listFinalReply = ConvertUtil.stringToArray(heartModel.getFinalReply());
                    finalReplyAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }
}
