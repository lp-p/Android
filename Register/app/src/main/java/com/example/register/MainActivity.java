package com.example.register;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    //public static final String MESSAGE = "com.example.register.MESSAGE";
    private EditText et_name, et_email, et_pwd;  //EditText 文本输入框  private私有
    private String name, email, pwd, sex, hobbies;

    private MyOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//调用父类Activity的onCreate()方法
        setContentView(R.layout.activity_main);//设置当前活动的页面布局文件  将布局文件转换为View对象，通过设备显示在界面
        openHelper = new MyOpenHelper(this);
        init();
    }

    private void init() {
        //获取界面控件
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        RadioGroup rg_sex = findViewById(R.id.rg_sex);
        CheckBox cb_sing = findViewById(R.id.cb_sing);
        CheckBox cb_dance = findViewById(R.id.cb_dance);
        CheckBox cb_read = findViewById(R.id.cb_read);
        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);//设置提交按钮的点击事件的监听器
        //设置复选框控件的点击事件的监听器
        cb_sing.setOnCheckedChangeListener(this);
        cb_dance.setOnCheckedChangeListener(this);
        cb_read.setOnCheckedChangeListener(this);
        hobbies = new String();
        //设置单选按钮的点击事件
        rg_sex.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) { //判断被点击的RadioButton
                case R.id.rb_boy:
                    sex = "男";
                    break;
                case R.id.rb_girl:
                    sex = "女";
                    break;
            }
        });
    }

    /**
     * 获取界面输入的信息
     */
    private void getData() {
        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
        hobbies = hobbies.trim();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) { //提交按钮的点击事件
            getData();
            if (!Util.checkname(name)) {
                Toast.makeText(MainActivity.this, "请输入2-5位中文名字",//李都
                        Toast.LENGTH_SHORT).show();
            } else if (!Util.checkemail(email)) {
                Toast.makeText(MainActivity.this, "请输入邮箱，例如:123@qq.com",
                        Toast.LENGTH_SHORT).show();
            } else if (!Util.checkPassword(pwd)) {
                Toast.makeText(MainActivity.this, "请输入密码,字母或数字或下划线6-12位",//123456
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(sex)) {
                Toast.makeText(MainActivity.this, "请选择性别",
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(hobbies)) {
                Toast.makeText(MainActivity.this, "请选择兴趣爱好",
                        Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Toast.makeText(MainActivity.this, "注册成功",
                            Toast.LENGTH_SHORT).show();
                    Log.i("MainActivity", "注册的用户信息：" + "名字：" + name + ", 邮箱："
                            + email + ", 性别：" + sex + ", 兴趣爱好：" + hobbies);
                    SQLiteDatabase database = openHelper.getReadableDatabase();//获取可读的对象
                    // 通过sqlitedatabase对象可以直接执行sql语句  new一个数组将数据放到数组里作为注册语句的参数
                    database.execSQL("INSERT INTO info VALUES (NULL, ? , ? , ? , ? , ?)", new Object[]{name, email, pwd, sex, hobbies});
                    database.close();//关闭数据库
                    //注册后跳转到显示页面  创建intent对象，跳转到指定类  显示意图
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
					/*隐式意图 AndroidManifest.xml中设置action(动作)和category(类别)
					<action>标签指定了当前Activity可以响应的动作为android.intent.action.MAIN
					而<category>标签则包含了一些类别信息，只有当这两者中的内容同时匹配时，Activity才会启动。
					Intent intent=new Intent();
					Intent.setAction(“android.intent.action.MAIN”);
					StartActivity(intent);
					*/
                    Bundle bundle = new Bundle();
                    //intent.putExtra(MESSAGE, name);//一次只能传送一个数据，多个取最后一个
                    bundle.putString("name", name);//键值对（key,value）
                    bundle.putString("email", email);
                    bundle.putString("hobbies", hobbies);
                    intent.putExtras(bundle);
                    //启动跳转
                    startActivity(intent);
                    //注册成功后将页面数据置空
                    et_name.setText("");
                    et_email.setText("");
                    et_pwd.setText("");

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "注册失败,用户名重复！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 复选框的点击事件
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String motion = buttonView.getText().toString();//获取复选框中的内容
        if (isChecked) {
            if (!hobbies.contains(motion)) { //判断之前选择的内容是否与此次选择的不一样
                hobbies = hobbies + motion;
            }
        } else {
            if (hobbies.contains(motion)) {
                hobbies = hobbies.replace(motion, "");
            }
        }
    }
}
