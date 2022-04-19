package com.android.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.HeartAdapter;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.view.EditHeartActivity;
import java.util.List;

public class HeartFragment extends ListFragment {
    private final int CODE_HEART = 123;
    public static List<HeartModel> listHeartModel;
    HeartAdapter heartAdapter;

    ImageButton btnAddHeart;
    IHeartDAO heartDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        heartDAO = new HeartDAO();
        listHeartModel = heartDAO.getAll();
        heartAdapter = new HeartAdapter(getActivity(), R.layout.row_heart, listHeartModel);
        setListAdapter(heartAdapter);
        View view = inflater.inflate(R.layout.fragment_heart, container, false);
        btnAddHeart = view.findViewById(R.id.btn_add_heart);
        addHeart();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_HEART){
            listHeartModel = heartAdapter.listHeartModel = heartDAO.getAll();
            heartAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        HeartModel heart = listHeartModel.get(position);
        Intent intent = new Intent(getActivity(), EditHeartActivity.class);
        intent.putExtra("HeartModel", heart);
        startActivityForResult(intent, CODE_HEART);
    }

    private void addHeart() {
        btnAddHeart.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_add_heart);

            EditText heartName = dialog.findViewById(R.id.heart_name);
            EditText heartDescription = dialog.findViewById(R.id.heart_description);
            TextView btnOkHeart = dialog.findViewById(R.id.btn_ok_heart);
            TextView btnCancelHeart = dialog.findViewById(R.id.btn_cancel_heart);

            btnCancelHeart.setOnClickListener(v -> {
                dialog.dismiss();
            });

            btnOkHeart.setOnClickListener(v -> {
                HeartModel heart = new HeartModel();
                heart.setHeartName(heartName.getText().toString().trim());
                heart.setDescription(heartDescription.getText().toString().trim());
                Long id = heartDAO.addHeart(heart);
                if(id == null) {
                    Toast.makeText(v.getContext(), "False", Toast.LENGTH_LONG).show();
                }
                listHeartModel = heartAdapter.listHeartModel = heartDAO.getAll();
                heartAdapter.notifyDataSetChanged();
                dialog.dismiss();

            });
            dialog.show();
        });
    }
}