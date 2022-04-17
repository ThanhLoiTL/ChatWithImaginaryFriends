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
import com.android.chatwithimaginaryfriends.adapter.WordAdapter;
import com.android.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.android.chatwithimaginaryfriends.dao.impl.InteractionDAO;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.util.ConvertUtil;
import java.util.List;

public class WordFragment extends ListFragment {
    public List<String> listWord;
    WordAdapter wordAdapter;

    ImageButton btnAddTriggerWord;
    IInteractionDAO interactionDAO;
    InteractionModel interactionModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trigger_word, container, false);
        interactionDAO = new InteractionDAO();
        btnAddTriggerWord = view.findViewById(R.id.btn_add_trigger_word);
        interactionModel = (InteractionModel) getArguments().getSerializable("InteractionModel");
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_trigger_word);

        EditText txtUpdateTriggerWord = dialog.findViewById(R.id.txt_update_trigger_word);
        Button btnOkTriggerWord = dialog.findViewById(R.id.btn_ok_trigger_word);
        Button btnCancelTriggerWord = dialog.findViewById(R.id.btn_cancel_trigger_word);
        Button btnDeleteTriggerWord = dialog.findViewById(R.id.btn_delete_trigger_word);

        txtUpdateTriggerWord.setText(listWord.get(position));

        btnCancelTriggerWord.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnOkTriggerWord.setOnClickListener(v -> {
            if(listWord.get(position) != txtUpdateTriggerWord.getText().toString().trim()){
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
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_add_trigger_word);

            EditText txtAddTriggerWord = dialog.findViewById(R.id.txt_add_trigger_word);
            Button btnOkTriggerWord = dialog.findViewById(R.id.btn_ok_trigger_word);
            Button btnCancelTriggerWord = dialog.findViewById(R.id.btn_cancel_trigger_word);

            btnCancelTriggerWord.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnOkTriggerWord.setOnClickListener(v -> {
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

            });
            dialog.show();
        });
    }
}
