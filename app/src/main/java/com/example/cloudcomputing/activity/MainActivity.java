package com.example.cloudcomputing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cloudcomputing.R;
import com.example.cloudcomputing.adapter.ViewPagerMainAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPagerMain;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPagerMain = findViewById(R.id.viewpager_main);
        bottomNav = findViewById(R.id.bottom_nav);

        ViewPagerMainAdapter viewPagerMainAdapter = new ViewPagerMainAdapter(this);
        viewPagerMain.setAdapter(viewPagerMainAdapter);

        viewPagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNav.getMenu().getItem(position).setChecked(true);
            }
        });

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_home) viewPagerMain.setCurrentItem(0);
                else if(item.getItemId()==R.id.menu_like) viewPagerMain.setCurrentItem(1);
                else if(item.getItemId()==R.id.menu_shared) viewPagerMain.setCurrentItem(2);
                else if(item.getItemId()==R.id.menu_profile) viewPagerMain.setCurrentItem(3);
                return true;
            }

        });

        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if(!isLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }



}