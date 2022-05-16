package com.android.chatwithimaginaryfriends.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.android.chatwithimaginaryfriends.adapter.ComboBoxBotAdapter;
import com.android.chatwithimaginaryfriends.adapter.ComboBoxHeartAdapter;
import com.android.chatwithimaginaryfriends.dao.IBotDAO;
import com.android.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.BotDAO;
import com.android.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.model.BotModel;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.util.ImageUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AddEditCharacterActivity extends AppCompatActivity {
    private final int REQUEST_CODE_FOLDER = 666;
    private final int REQUEST_CODE_EXTERNAL_STO = 345;

    private Spinner spnTypeCharacter;
    private ComboBoxHeartAdapter comboBoxHeartAdapter;
    private ComboBoxBotAdapter comboBoxBotAdapter;
    private TextView typeCharacter;
    private ImageButton btnSaveCharacter;
    private ImageView imgAvatar;
    private EditText characterName, shortDescription, gender, birthday, height, weight, zodiac, address;
    private List<HeartModel> listHeart;
    private List<BotModel> listBot;
    private IHeartDAO heartDAO;
    private IBotDAO botDAO;
    private ICharacterDAO characterDAO;
    private HeartModel heartModel;
    private BotModel botModel;
    private CharacterModel character;


    @SuppressLint("IntentReset")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_character);

        heartDAO = new HeartDAO();
        characterDAO = new CharacterDAO();
        botDAO = new BotDAO();

        init();

        Intent intent = getIntent();
        String characterType = intent.getStringExtra("CHARACTER_TYPE");
        characterType = (characterType==null) ? "EMPTY" :intent.getStringExtra("CHARACTER_TYPE");
        character = (CharacterModel) intent.getSerializableExtra("CharacterModel");
        character = (character == null) ? new CharacterModel() : (CharacterModel) intent.getSerializableExtra("CharacterModel");

        setView(character);

        if(characterType.compareToIgnoreCase("HEART") == 0 || character.getHeart() != 0){
            listHeart = heartDAO.getAll();
            typeCharacter.setText("Heart");

            //set ComboBox
            comboBoxHeartAdapter = new ComboBoxHeartAdapter(this, R.layout.item_selected, listHeart);
            spnTypeCharacter.setAdapter(comboBoxHeartAdapter);

            //auto fill type when edit
            if(character.getId() != null){
                heartModel = heartDAO.findOne(character.getHeart());
                selectValue(spnTypeCharacter, heartModel);
            }

            spnTypeCharacter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    character.setHeart(comboBoxHeartAdapter.getItem(i).getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        } else if(character.getBot() != 0 || characterType.compareToIgnoreCase("BOT")==0) {
            listBot = botDAO.getAll();
            typeCharacter.setText("AI");
            comboBoxBotAdapter = new ComboBoxBotAdapter(this, R.layout.item_selected, listBot);
            spnTypeCharacter.setAdapter(comboBoxBotAdapter);

            if(character.getId() != null){
                botModel = botDAO.findOne(character.getBot());
                selectValue(spnTypeCharacter, botModel);
            }
            spnTypeCharacter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    character.setBot(comboBoxBotAdapter.getItem(i).getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        imgAvatar.setOnClickListener(view -> {
            Intent intentImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intentImage.setType("image/*");
            startActivityForResult(intentImage, REQUEST_CODE_FOLDER);
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(AddEditCharacterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_STO);
            }
        });

        btnSaveCharacter.setOnClickListener(v -> {
            String _name = characterName.getText().toString();
            String _short_description = shortDescription.getText().toString();
            String _gender = gender.getText().toString();
            String _birthday = birthday.getText().toString();
            String _height = height.getText().toString();
            String _weight = weight.getText().toString();
            String _address = address.getText().toString();
            String _zodiac = zodiac.getText().toString();

            if(_name.isEmpty() || _short_description.isEmpty() || _gender.isEmpty() || _birthday.isEmpty() || _height.isEmpty() ||
                    _weight.isEmpty() || _address.isEmpty() || _zodiac.isEmpty() || (character.getBot() == 0 && character.getHeart() == 0)){
                Toast.makeText(v.getContext(), "Fill All Properties", Toast.LENGTH_LONG).show();
            }else{
                character.setName(_name);
                character.setShortDescription(_short_description);
                character.setGender(_gender);
                character.setBirthday(_birthday);
                character.setHeight(Double.parseDouble(_height));
                character.setWeight(Double.parseDouble(_weight));
                character.setAddress(_address);
                character.setZodiac(_zodiac);
                character.setAvatar(ImageUtil.bitmapToByteArray(imgAvatar));

                if(character.getId() == null){
                    Long id = characterDAO.addCharacter(character);
                    if(id == null) {
                        Toast.makeText(v.getContext(), "Add failed", Toast.LENGTH_LONG).show();
                    }
                }else {
                    int isSuccess = characterDAO.updateCharacter(character);
                    if(isSuccess == 1){
                        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //imgAvatar.setImageBitmap(ImageUtil.resizeImage(bitmap, AddEditCharacterActivity.this));
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setView(CharacterModel character) {
        if(character.getId() != null){
            characterName.setText(character.getName());
            shortDescription.setText(character.getShortDescription());
            gender.setText(character.getGender());
            birthday.setText(character.getBirthday());
            height.setText(String.valueOf(character.getHeight()));
            weight.setText(String.valueOf(character.getWeight()));
            address.setText(character.getAddress());
            zodiac.setText(character.getZodiac());
            imgAvatar.setImageBitmap(ImageUtil.byteToBitmap(character.getAvatar()));
        }else{
            imgAvatar.setImageBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_person_round)).getBitmap());
        }
    }

    private void selectValue(Spinner spinner, HeartModel value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            HeartModel heart = (HeartModel) spinner.getItemAtPosition(i);
            if (value.getId() == heart.getId()) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    private void selectValue(Spinner spinner, BotModel value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            BotModel bot = (BotModel) spinner.getItemAtPosition(i);
            if (value.getId() == bot.getId()) {
                spinner.setSelection(i);
                break;
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