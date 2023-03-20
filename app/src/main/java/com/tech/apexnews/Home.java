package com.tech.apexnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();

        fragmentTransaction(new HomeFragment());

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_menu:
                        fragmentTransaction( new HomeFragment());
                         break;
                    case R.id.video_menu:
                        fragmentTransaction(new VideoFragment());
                        break;
                    case R.id.user_menu:
                        fragmentTransaction(new UserFragment());
                        break;
                    default:
                        break;

                }

                return true;
            }
        });

    }

    public void fragmentTransaction(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    private void initViews(){
        bottom_navigation = findViewById(R.id.bottom_navigation);
    }
}