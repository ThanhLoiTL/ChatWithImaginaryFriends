package com.android.chatwithimaginaryfriends.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.adapter.ComboBoxHeartAdapter;
import com.android.chatwithimaginaryfriends.dao.ICharacterDAO;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.CharacterDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.model.CharacterModel;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.util.ImageUtil;
import com.android.chatwithimaginaryfriends.util.ImageUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class EditCharacterActivity extends AppCompatActivity {
    private final int REQUEST_CODE_FOLDER = 303;

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
        heartDAO = new HeartDAO();
        characterDAO = new CharacterDAO();

        CharacterModel character = (CharacterModel) intent.getSerializableExtra("CharacterModel");
        characterName.setText(character.getName());
        shortDescription.setText(character.getShortDescription());
        gender.setText(character.getGender());
        birthday.setText(character.getBirthday());
        height.setText(String.valueOf(character.getHeight()));
        weight.setText(String.valueOf(character.getWeight()));
        address.setText(character.getAddress());
        zodiac.setText(character.getZodiac());
        imgAvatar.setImageBitmap(ImageUtil.byteToBitmap(character.getAvatar()));
        if(character.getHeart() != 0) {
            typeCharacter.setText("Heart");
            listHeart = heartDAO.getAll();
            comboBoxHeartAdapter = new ComboBoxHeartAdapter(this, R.layout.item_selected, listHeart);
            HeartModel heartModel = heartDAO.findOne(character.getHeart());
            spnTypeCharacter.setAdapter(comboBoxHeartAdapter);
            selectValue(spnTypeCharacter, heartModel);

            spnTypeCharacter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    character.setHeart(comboBoxHeartAdapter.getItem(i).getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }else {
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
            Intent intentImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                character.setName(name);
                character.setShortDescription(short_description);
                character.setGender(_gender);
                character.setBirthday(_birthday);
                character.setHeight(Double.parseDouble(_height));
                character.setWeight(Double.parseDouble(_weight));
                character.setAddress(_address);
                character.setZodiac(_zodiac);
                character.setAvatar(ImageUtil.bitmapToByteArray(imgAvatar));
                int isSuccess = characterDAO.updateCharacter(character);
                if(isSuccess == 1){
                    Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
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
                imgAvatar.setImageBitmap(bitmap);
                //Bitmap bitmap = ImageUtils.getInstant().getCompressedBitmap(uri.getPath());
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
    private void selectValue(Spinner spinner, HeartModel value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            HeartModel heart = (HeartModel) spinner.getItemAtPosition(i);
            if (value.getId() == heart.getId()) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}