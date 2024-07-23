package ru.tacticm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
todo & settings
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        com.google.android.material.bottomnavigation.BottomNavigationView bmv = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(bmv, navController);
    }

}
