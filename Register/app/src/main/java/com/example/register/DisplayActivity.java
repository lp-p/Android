package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        //获取intent
        Intent intent = getIntent();
        //获取界面控件
        TextView textName = findViewById(R.id.name);
        TextView textEmail = findViewById(R.id.email);
        TextView textHobbies = findViewById(R.id.hobbies);
        //通过key获取值  数据在intent中
        String name =intent.getStringExtra("name");
        String email=intent.getStringExtra("email");
        String hobbies=intent.getStringExtra("hobbies");

        System.out.println("name="+name+"   email="+email+"   hobbies="+hobbies);
        //设置控件显示的内容
        textName.setText(name);
        textEmail.setText(email);
        textHobbies.setText(hobbies);
    }
}
