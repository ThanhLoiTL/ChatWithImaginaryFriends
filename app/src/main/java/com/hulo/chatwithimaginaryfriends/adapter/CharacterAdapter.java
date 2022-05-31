package com.hulo.chatwithimaginaryfriends.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulo.chatwithimaginaryfriends.R;
import com.hulo.chatwithimaginaryfriends.dao.IBotDAO;
import com.hulo.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.IHeartDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.BotDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.hulo.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.hulo.chatwithimaginaryfriends.model.BotModel;
import com.hulo.chatwithimaginaryfriends.model.CharacterModel;
import com.hulo.chatwithimaginaryfriends.model.HeartModel;
import com.hulo.chatwithimaginaryfriends.util.ImageUtil;

import java.util.List;

public class CharacterAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    public List<CharacterModel> listCharacterModel;

    private IHeartDAO heartDAO;
    private ICharacterDAO characterDAO;
    private IBotDAO botDAO;

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
        TextView characterName, heartOrBot, characterDescription;
        ImageView imgAvatar;
        ImageButton characterDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        heartDAO = new HeartDAO();
        botDAO = new BotDAO();
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.characterName = view.findViewById(R.id.character_name);
            viewHolder.heartOrBot = view.findViewById(R.id.heart_or_bot);
            viewHolder.imgAvatar = view.findViewById(R.id.img_avatar);
            viewHolder.characterDescription = view.findViewById(R.id.character_description);
            viewHolder.characterDelete = view.findViewById(R.id.character_delete);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CharacterModel characterModel = listCharacterModel.get(i);
        viewHolder.characterName.setText(characterModel.getName());
        if(characterModel.getHeart() != 0) {
            HeartModel heart = heartDAO.findOne(characterModel.getHeart());
            viewHolder.heartOrBot.setText(heart.getHeartName());
        }else{
            BotModel botModel = botDAO.findOne(characterModel.getBot());
            viewHolder.heartOrBot.setText(botModel.getName());
        }
        viewHolder.characterDescription.setText(characterModel.getShortDescription());
        viewHolder.imgAvatar.setImageBitmap(ImageUtil.byteToBitmap(characterModel.getAvatar()));

        viewHolder.characterDelete.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Notification!");
            alertDialog.setIcon(null);
            alertDialog.setMessage("Do you want to delete?");
            alertDialog.setPositiveButton("Yes", (dialogInterface, i1) -> {
                characterDAO = new CharacterDAO();
                characterDAO.deleteCharacter(characterModel.getId());
                listCharacterModel = characterDAO.getAll();
                this.notifyDataSetChanged();
            });
            alertDialog.setNegativeButton("No", (dialogInterface, i2) -> {

            });
            alertDialog.show();
        });
        return view;
    }
}
