package com.android.chatwithimaginaryfriends.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.model.HeartModel;

import java.util.List;

public class CharacterListAdapter extends ArrayAdapter<CharacterModel> {
    private Context context;
    private int layout;
    public List<CharacterModel> listCharacter;

    public CharacterListAdapter(@NonNull Context context, int layout, @NonNull List<CharacterModel> objects) {
        super(context, layout, objects);
        this.listCharacter = objects;
        this.layout = layout;
        this.context = context;
    }

    private class ViewHolder {
        TextView characterName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.characterName = view.findViewById(R.id.tv_item);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CharacterModel characterModel = listCharacter.get(i);
        viewHolder.characterName.setText(characterModel.getName());
        return view;
    }
}
