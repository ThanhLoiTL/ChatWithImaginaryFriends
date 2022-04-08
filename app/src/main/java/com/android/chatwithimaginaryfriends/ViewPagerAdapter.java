package com.android.chatwithimaginaryfriends;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter  {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HeartFragment();
            case 1:
                return new CharacterFragment();
            case 3:
                return new SettingFragment();
            case 2:
            default:
                return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
