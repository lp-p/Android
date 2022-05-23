package com.example.intadd1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et1;
    EditText et2;
    TextView tv;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        tv = (TextView)findViewById(R.id.tv);
        bt = (Button)findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener(){
            String str = null;
            @Override
            public void onClick(View view){
                String str1 = et1.getText().toString();
                String str2 = et2.getText().toString();

                int num1 = Integer.valueOf(str1).intValue();
                int num2 = Integer.valueOf(str2).intValue();
                int sum = num1 + num2;

                str = String.valueOf(sum);
                tv.setText(str);
            }
        });
    }

}