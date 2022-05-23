package com.example.intadd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText et_count1,et_count2;
    //   private Button bt_add;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_count1=findViewById(R.id.et_1);
        et_count2=findViewById(R.id.et_2);
        //  bt_add=findViewById(R.id.bt_add);
        tv_result=findViewById(R.id.tv_result);
    }
    public void add(View v){
        //获取输入的两个整数
        String et1=et_count1.getText().toString().trim();
        String et2=et_count2.getText().toString().trim();
        //转换String为int，进行相加
        int result= Integer.parseInt(et1)+Integer.parseInt(et2);
        //结果输出
        tv_result.setText(""+result);
    }
}
