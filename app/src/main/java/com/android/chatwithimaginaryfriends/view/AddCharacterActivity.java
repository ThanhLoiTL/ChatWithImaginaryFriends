package com.android.chatwithimaginaryfriends.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.ComboBoxHeartAdapter;
import com.android.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.util.ImageUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AddCharacterActivity extends AppCompatActivity {
    private final int REQUEST_CODE_FOLDER = 666;

    private Spinner spnTypeCharacter;
    private ComboBoxHeartAdapter comboBoxHeartAdapter;
    private TextView typeCharacter;
    private ImageButton btnSaveCharacter;
    private ImageView imgAvatar;
    private EditText characterName, shortDescription, gender, birthday, height, weight, zodiac, address;
    List<HeartModel> listHeart;
    IHeartDAO heartDAO;
    ICharacterDAO characterDAO;
    Long idHeart, idAi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_character);
        init();
        Intent intent = getIntent();
        String characterType = intent.getStringExtra("CHARACTER_TYPE");
        heartDAO = new HeartDAO();
        characterDAO = new CharacterDAO();
        if(characterType.equals("HEART")){
            listHeart = heartDAO.getAll();
            typeCharacter.setText("Heart");
            comboBoxHeartAdapter = new ComboBoxHeartAdapter(this, R.layout.item_selected, listHeart);
            spnTypeCharacter.setAdapter(comboBoxHeartAdapter);
            spnTypeCharacter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    idHeart = comboBoxHeartAdapter.getItem(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if(characterType.equals("AI")) {
            listHeart = heartDAO.getAll();
            typeCharacter.setText("AI");
            comboBoxHeartAdapter = new ComboBoxHeartAdapter(this, R.layout.item_selected, listHeart);
            spnTypeCharacter.setAdapter(comboBoxHeartAdapter);
            spnTypeCharacter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    idAi = comboBoxHeartAdapter.getItem(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        imgAvatar.setOnClickListener(view -> {
            Intent intentImage = new Intent(Intent.ACTION_PICK);
            intentImage.setType("image/*");
            startActivityForResult(intentImage, REQUEST_CODE_FOLDER);
        });

        btnSaveCharacter.setOnClickListener(v -> {
            String name = characterName.getText().toString();
            String short_description = shortDescription.getText().toString();
            String _gender = gender.getText().toString();
            String _birthday = birthday.getText().toString();
            String _height = height.getText().toString();
            String _weight = weight.getText().toString();
            String _address = address.getText().toString();
            String _zodiac = zodiac.getText().toString();

            if(name.isEmpty() || short_description.isEmpty() || _gender.isEmpty() || _birthday.isEmpty() || _height.isEmpty() ||
            _weight.isEmpty() || _address.isEmpty() || _zodiac.isEmpty()){
                Toast.makeText(v.getContext(), "Fill ALl Properties", Toast.LENGTH_LONG).show();
            }else{
                CharacterModel characterModel = new CharacterModel();
                characterModel.setName(name);
                characterModel.setShortDescription(short_description);
                characterModel.setGender(_gender);
                characterModel.setBirthday(_birthday);
                characterModel.setHeight(Double.parseDouble(_height));
                characterModel.setWeight(Double.parseDouble(_weight));
                characterModel.setAddress(_address);
                characterModel.setZodiac(_zodiac);
                characterModel.setAvatar(ImageUtil.toByteArray(imgAvatar));
                if(characterType.equals("AI")){
                    characterModel.setBot(idAi);
                }else if(characterType.equals("HEART")){
                    characterModel.setHeart(idHeart);
                }
                Long id = characterDAO.addCharacter(characterModel);
                if(id == null) {
                    Toast.makeText(v.getContext(), "False", Toast.LENGTH_LONG).show();
                }else{
                    finish();
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        spnTypeCharacter = findViewById(R.id.spn_type_character);
        typeCharacter = findViewById(R.id.type_character);
        btnSaveCharacter = findViewById(R.id.btn_save_character);
        imgAvatar = findViewById(R.id.img_avatar);
        characterName = findViewById(R.id.character_name);
        shortDescription = findViewById(R.id.short_description);
        gender = findViewById(R.id.gender);
        birthday = findViewById(R.id.birthday);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        address = findViewById(R.id.address);
        zodiac = findViewById(R.id.zodiac);
    }
}