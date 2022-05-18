package com.android.chatwithimaginaryfriends.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.chatwithimaginaryfriends.R;
import com.android.chatwithimaginaryfriends.model.HeartModel;
import com.android.chatwithimaginaryfriends.view.MainActivity;

public class SettingFragment extends Fragment {
    private RelativeLayout theme;
    SharedPreferences sharedPreferences, appPreferences;
    SharedPreferences.Editor editor;
    int appTheme;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        theme = view.findViewById(R.id.theme);
        editThem();
        return view;
    }

    private void editThem() {
        appPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        appTheme = appPreferences.getInt("theme", 0);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        theme.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_set_theme);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            TextView btn_cancel_theme = dialog.findViewById(R.id.btn_cancel_theme);
            setColor(dialog);

            btn_cancel_theme.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();
        });
    }

    private void setColor(Dialog dialog){
        ImageButton colorViolet = dialog.findViewById(R.id.color_violet);
        ImageButton colorGreen = dialog.findViewById(R.id.color_green);
        ImageButton colorBlue = dialog.findViewById(R.id.color_blue);
        ImageButton colorRed = dialog.findViewById(R.id.color_red);
        ImageButton colorPink = dialog.findViewById(R.id.color_pink);
        ImageButton colorBlack = dialog.findViewById(R.id.color_black);

        colorViolet.setOnClickListener(view -> {
            setTheme(view, R.style.Theme_ChatWithImaginaryFriends);
        });
        colorGreen.setOnClickListener(view -> {
            setTheme(view, R.style.Theme_Green);
        });
        colorBlue.setOnClickListener(view -> {
            setTheme(view, R.style.Theme_Blue);
        });
        colorRed.setOnClickListener(view -> {
            setTheme(view, R.style.Theme_Red);
        });
        colorPink.setOnClickListener(view -> {
            setTheme(view, R.style.Theme_Pink);
        });
        colorBlack.setOnClickListener(view -> {
            setTheme(view, R.style.Theme_Black);
        });
    }
    private void setTheme(View view, int theme) {
        editor.putInt("theme",theme);
        editor.commit();
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
