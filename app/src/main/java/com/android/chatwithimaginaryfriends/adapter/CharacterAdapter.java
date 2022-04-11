package com.android.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.CharacterModel;

import java.util.List;

public class CharacterAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CharacterModel> listCharacterModel;

    public CharacterAdapter(Context context, int layout, List<CharacterModel> listCharacterModel) {
        this.context = context;
        this.layout = layout;
        this.listCharacterModel = listCharacterModel;
    }

    @Override
    public int getCount() {
        return listCharacterModel.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        TextView txtCharacterName, txtHeart;
        ImageView imgAvatar;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.txtCharacterName = view.findViewById(R.id.txtCharacterName);
            viewHolder.txtHeart = view.findViewById(R.id.txtHeart);
            viewHolder.imgAvatar = view.findViewById(R.id.imgAvatar);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CharacterModel characterModel = listCharacterModel.get(i);
        viewHolder.txtCharacterName.setText(characterModel.getCharacterName());
        viewHolder.txtHeart.setText(characterModel.getHeart());

        return view;
    }
}
