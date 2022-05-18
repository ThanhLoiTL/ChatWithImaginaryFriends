package com.android.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.WordAdapter;
import com.android.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.android.chatwithimaginaryfriends.dao.impl.InteractionDAO;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.util.ConvertUtil;
import java.util.List;

public class WordFragment extends ListFragment {
    public List<String> listWord;
    private WordAdapter wordAdapter;

    private ImageButton btnAddTriggerWord;
    private IInteractionDAO interactionDAO;
    private InteractionModel interactionModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trigger_word, container, false);
        interactionDAO = new InteractionDAO();
        btnAddTriggerWord = view.findViewById(R.id.btn_add_trigger_word);
        interactionModel = (InteractionModel) getArguments().getSerializable("InteractionModel");
        interactionModel = interactionDAO.findOne(interactionModel.getId());
        listWord = ConvertUtil.stringToArray(interactionModel.getTriggerWord());
        wordAdapter = new WordAdapter(getActivity(), R.layout.row_trigger_word, listWord);
        setListAdapter(wordAdapter);

        addTriggerWord();
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
        title.setText("Update Trigger Word");

        EditText txtUpdateTriggerWord = dialog.findViewById(R.id.txt_input);
        txtUpdateTriggerWord.setHint("Please input a trigger word");
        TextView btnOkTriggerWord = dialog.findViewById(R.id.btn_ok);
        TextView btnCancelTriggerWord = dialog.findViewById(R.id.btn_cancel);
        TextView btnDeleteTriggerWord = dialog.findViewById(R.id.btn_delete);

        txtUpdateTriggerWord.setText(listWord.get(position));

        btnCancelTriggerWord.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnOkTriggerWord.setOnClickListener(v -> {
            if(txtUpdateTriggerWord.getText().toString().trim().equals("")){
                Toast.makeText(v.getContext(), "Please Fill All Properties", Toast.LENGTH_LONG).show();
            }else {
                if (listWord.get(position) != txtUpdateTriggerWord.getText().toString().trim()) {
                    listWord.set(position, txtUpdateTriggerWord.getText().toString().trim());
                    String s = ConvertUtil.arrayToString(listWord);
                    interactionModel.setTriggerWord(s);
                    int isSuccess = interactionDAO.updateInteraction(interactionModel);
                    if (isSuccess == 1) {
                        Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    interactionModel = interactionDAO.findOne(interactionModel.getId());
                    listWord = wordAdapter.listWord = ConvertUtil.stringToArray(interactionModel.getTriggerWord());
                    wordAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        btnDeleteTriggerWord.setOnClickListener(v -> {
            listWord.remove(position);
            String s = ConvertUtil.arrayToString(listWord);
            interactionModel.setTriggerWord(s);
            int isSuccess = interactionDAO.updateInteraction(interactionModel);
            if (isSuccess == 1) {
                Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
            interactionModel = interactionDAO.findOne(interactionModel.getId());
            listWord = wordAdapter.listWord = ConvertUtil.stringToArray(interactionModel.getTriggerWord());
            wordAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void addTriggerWord() {
        btnAddTriggerWord.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_add);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Add Trigger Word");
            EditText txtAddTriggerWord = dialog.findViewById(R.id.txt_input);
            txtAddTriggerWord.setHint("Please input a trigger word");
            TextView btnOkTriggerWord = dialog.findViewById(R.id.btn_ok);
            TextView btnCancelTriggerWord = dialog.findViewById(R.id.btn_cancel);

            btnCancelTriggerWord.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnOkTriggerWord.setOnClickListener(v -> {
                if(txtAddTriggerWord.getText().toString().trim().equals("")){
                    Toast.makeText(v.getContext(), "Please Fill All Properties", Toast.LENGTH_LONG).show();
                }else {
                    listWord.add(txtAddTriggerWord.getText().toString().trim());
                    String s = ConvertUtil.arrayToString(listWord);
                    interactionModel.setTriggerWord(s);
                    int isSuccess = interactionDAO.updateInteraction(interactionModel);
                    if (isSuccess == 1) {
                        Toast.makeText(v.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                    interactionModel = interactionDAO.findOne(interactionModel.getId());
                    listWord = wordAdapter.listWord = ConvertUtil.stringToArray(interactionModel.getTriggerWord());
                    wordAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }
}
