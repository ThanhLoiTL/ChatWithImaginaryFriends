package com.hulo.chatwithimaginaryfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.model.CharacterModel;
import com.hulo.chatwithimaginaryfriends.util.ImageUtil;

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
        ImageView imgAvatar;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.characterName = view.findViewById(R.id.character_name);
            viewHolder.imgAvatar = view.findViewById(R.id.img_avatar);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CharacterModel characterModel = listCharacter.get(i);
        viewHolder.characterName.setText(characterModel.getName());
        viewHolder.imgAvatar.setImageBitmap(ImageUtil.byteToBitmap(characterModel.getAvatar()));
        return view;
    }
}
