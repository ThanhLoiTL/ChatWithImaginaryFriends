package com.android.chatwithimaginaryfriends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationMenu;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationMenu = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.view_pager);

        setUpViewPager();

        navigationMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_heart:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_character:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_chat:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_setting:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
    }
}