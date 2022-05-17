package com.android.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.Intent;
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
import com.android.chatwithimaginaryfriends.adapter.InteractionAdapter;
import com.android.chatwithimaginaryfriends.dao.IInteractionDAO;
import com.android.chatwithimaginaryfriends.dao.impl.InteractionDAO;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.model.InteractionModel;
import com.android.chatwithimaginaryfriends.view.EditInteractionActivity;

import java.util.List;

public class InteractionFragment extends ListFragment {
    private static final int CODE_INTERACTION = 222;
    public static List<InteractionModel> listInteractionModel;
    InteractionAdapter interactionAdapter;

    private ImageButton btnAddInteraction;
    private IInteractionDAO interactionDAO;
    private HeartModel heartModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interaction, container, false);
        interactionDAO = new InteractionDAO();
        //get heart from InteractionActivity to get id heart
        heartModel = (HeartModel) getArguments().getSerializable("HeartModel");
        listInteractionModel = interactionDAO.findByHeart(heartModel.getId());
        interactionAdapter = new InteractionAdapter(getActivity(), R.layout.row_interaction, listInteractionModel);
        setListAdapter(interactionAdapter);
        btnAddInteraction = view.findViewById(R.id.btn_add_interaction);
        addInteraction();

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        InteractionModel interaction = listInteractionModel.get(position);
        Intent intent = new Intent(getActivity(), EditInteractionActivity.class);
        intent.putExtra("InteractionModel", interaction);
        startActivityForResult(intent, CODE_INTERACTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_INTERACTION){
            listInteractionModel = interactionAdapter.listInteractionModel = interactionDAO.findByHeart(heartModel.getId());
            interactionAdapter.notifyDataSetChanged();
        }
    }

    private void addInteraction() {
        btnAddInteraction.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_add);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            TextView title = dialog.findViewById(R.id.title);
            title.setText("Add Interaction");
            EditText triggerWord = dialog.findViewById(R.id.txt_input);
            triggerWord.setHint("Please input a trigger word");
            TextView btnOkInteraction = dialog.findViewById(R.id.btn_ok);
            TextView btnCancelInteraction = dialog.findViewById(R.id.btn_cancel);

            btnCancelInteraction.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnOkInteraction.setOnClickListener(v -> {
                InteractionModel interaction = new InteractionModel();
                interaction.setIdHeart(heartModel.getId());
                interaction.setTriggerWord(triggerWord.getText().toString().trim());
                Long id = interactionDAO.addInteraction(interaction);
                if(id == null) {
                    Toast.makeText(v.getContext(), "False", Toast.LENGTH_LONG).show();
                }
                listInteractionModel = interactionAdapter.listInteractionModel = interactionDAO.findByHeart(heartModel.getId());
                interactionAdapter.notifyDataSetChanged();
                dialog.dismiss();

            });
            dialog.show();
        });
    }
}
