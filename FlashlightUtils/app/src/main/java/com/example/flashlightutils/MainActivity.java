package com.example.flashlightutils;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    FlashUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utils = new FlashUtils(this);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch flashControl = findViewById(R.id.flash_control);
        flashControl.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                utils.open();
            } else {
                utils.close();
            }
        });
    }
}