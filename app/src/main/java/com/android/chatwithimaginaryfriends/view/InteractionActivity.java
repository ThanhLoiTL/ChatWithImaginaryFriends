package com.android.chatwithimaginaryfriends.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.constant.SystemConstant;
import com.android.chatwithimaginaryfriends.dao.IHeartDAO;
import com.android.chatwithimaginaryfriends.dao.impl.HeartDAO;
import com.android.chatwithimaginaryfriends.fragment.InteractionFragment;
import com.android.chatwithimaginaryfriends.model.HeartModel;

import java.util.Objects;

public class InteractionActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FINAL_REPLY = 1145;
    private FragmentManager fragmentManager = getFragmentManager();
    private TextView finalReply;
    private ImageButton btnSaveHeart;
    private HeartModel heartModel;
    private IHeartDAO heartDAO;
    private InteractionFragment interactionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int appTheme = app_preferences.getInt("theme", 0);
        if (appTheme == 0){
            setTheme(SystemConstant.DEFAULT_THEME);
        }else{
            setTheme(appTheme);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_interaction);
        mapping();

        Intent intent = getIntent();
        heartModel = (HeartModel) intent.getSerializableExtra("HeartModel");
        Bundle bundle = new Bundle();
        bundle.putSerializable("HeartModel", heartModel);
        interactionFragment.setArguments(bundle);
        addFragment(interactionFragment);
        finalReply.setText(heartModel.getFinalReply());

        btnSaveHeart.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.anim_enter_from_left, R.anim.anim_exit_out_right);
        });

        finalReply.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, EditFinalReplyActivity.class);
            intent1.putExtra("Heart", heartModel);
            startActivityForResult(intent1, REQUEST_CODE_FINAL_REPLY);
            overridePendingTransition(R.anim.anim_enter_from_right, R.anim.anim_exit_out_left);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_FINAL_REPLY){
            heartModel = heartDAO.findOne(heartModel.getId());
            finalReply.setText(heartModel.getFinalReply());
        }
    }

    private void mapping() {
        finalReply = findViewById(R.id.final_reply);
        btnSaveHeart = findViewById(R.id.btn_save_heart);
        heartDAO = new HeartDAO();
        interactionFragment = new InteractionFragment();
    }
    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutInteraction, fragment);
        fragmentTransaction.commit();
    }
}