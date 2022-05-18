package com.android.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.CharacterAdapter;
import com.android.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.android.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.view.AddEditCharacterActivity;

import java.util.ArrayList;
import java.util.List;

public class CharacterFragment extends ListFragment {
    private final int CODE_CHARACTER = 1101;
    public List<CharacterModel> listCharacterModel;
    private CharacterAdapter characterAdapter;

    private ICharacterDAO characterDAO;
    private ImageButton btnAddCharacter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        characterDAO = new CharacterDAO();
        listCharacterModel = new ArrayList<>();
        listCharacterModel = characterDAO.getAll();
        characterAdapter = new CharacterAdapter(getActivity(), R.layout.row_character, listCharacterModel);
        setListAdapter(characterAdapter);
        View view = inflater.inflate(R.layout.fragment_character, container, false);
        btnAddCharacter = view.findViewById(R.id.btn_add_character);
        addCharacter();
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try{
            Intent intent = new Intent(v.getContext(), AddEditCharacterActivity.class);
            CharacterModel character = listCharacterModel.get(position);
            intent.putExtra("CharacterModel", character);
            startActivityForResult(intent, CODE_CHARACTER);
            getActivity().overridePendingTransition(R.anim.anim_enter_from_right, R.anim.anim_exit_out_left);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_CHARACTER){
            listCharacterModel = characterAdapter.listCharacterModel = characterDAO.getAll();
            characterAdapter.notifyDataSetChanged();
        }
    }

    private void addCharacter() {
        btnAddCharacter.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_select_type_character);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;

            dialog.getWindow().setAttributes(lp);
            TextView chooseHeart = dialog.findViewById(R.id.choose_heart);
            TextView chooseAi = dialog.findViewById(R.id.choose_ai);
            TextView btnCancelCharacter = dialog.findViewById(R.id.btn_cancel_character);

            chooseHeart.setOnClickListener(v -> {
                Intent intent = new Intent(view.getContext(), AddEditCharacterActivity.class);
                intent.putExtra("CHARACTER_TYPE", "HEART");
                dialog.dismiss();
                startActivityForResult(intent, CODE_CHARACTER);
            });

            chooseAi.setOnClickListener(v-> {
                Intent intent = new Intent(view.getContext(), AddEditCharacterActivity.class);
                intent.putExtra("CHARACTER_TYPE", "BOT");
                dialog.dismiss();
                startActivityForResult(intent, CODE_CHARACTER);
            });

            btnCancelCharacter.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }
}
