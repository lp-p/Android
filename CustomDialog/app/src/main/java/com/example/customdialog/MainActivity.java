package com.example.customdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed(){
        final CommonDialog dialog = new CommonDialog(MainActivity.this);
        dialog.setTitle("自定义对话框");
        dialog.setMaeeage("是否退出应用？");
        dialog.setNegtive("取消");
        dialog.setPositive("确定");
        dialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
                MainActivity.this.finish();
            }

            @Override
            public void onNegtiveClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}