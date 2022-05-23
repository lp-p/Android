package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lv_list;
    private TextView tv_name;
    private TextView tv_phone;
    private TextView tv_money;
    private MyOpenHelper openHelper;
    private ArrayList<UserBean> userBeans=new ArrayList<UserBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//设置一个活动的显示界面
        openHelper = new MyOpenHelper(this);

        lv_list = findViewById(R.id.lv_list);//初始化查找控件

    }
    private class MyAdapter extends BaseAdapter{//数据适配器

        @Override
        public int getCount() {
            //这个方法的返回值决定了当前listview究竟要展示多少条数据
            return userBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return userBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view=null;
            if(convertView==null){
                //说明没有可以重用的View对象 需要创建新的view对象

                view = View.inflate(MainActivity.this, R.layout.item, null);//加载一个布局文件
            }else {
                //当前的converView可以被重新使用
                view = convertView;
            }
            //给textView设置数据
            tv_name = view.findViewById(R.id.tv_name);
            tv_phone = view.findViewById(R.id.tv_phone);
            tv_money = view.findViewById(R.id.tv_money);
            //获取显示数据
            UserBean userBean = userBeans.get(position);
            tv_name.setText(userBean.name);
            tv_phone.setText(userBean.phone);
            tv_money.setText(userBean.money);
            return view;
        }
    }
    public void transact(View v){
        SQLiteDatabase db = openHelper.getReadableDatabase();//获取可读的对象
        //开启事务
        db.beginTransaction();
        try {
            //把所有跟事务相关的操作都放到try代码块中
            db.execSQL("update info set money= money-200 where name=?",new String[]{"张三"});
            //int i = 100/0;当执行产生异常，执行回滚操作，显示服务器忙
            db.execSQL("update info set money= money+200 where name=?",new String[]{"李四"});
            db.setTransactionSuccessful();//设置事务成功的标记
        } catch (Exception e) {
            Toast.makeText(this, "服务器忙,异常码(0000)", Toast.LENGTH_SHORT).show();
        }finally {
            //事务结束 , 首先检查 是否设置事务成功的标记 如果有成功标记 就提交所有的修改
            //如果没发现成功的标记 回滚到初始状态
            db.endTransaction();
            db.close();
        }
        Toast.makeText(this, "张三给李四转账200", Toast.LENGTH_SHORT).show();
    }

    public void select(View v){//查询余额
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from info", null);//Cursor游标接口，调用SQLiteDatabase的rawQuery方法
        if (cursor.getCount()==0){

            Toast.makeText(this,"没有数据",Toast.LENGTH_SHORT).show();
        }else {

            cursor.moveToFirst();//指向查询结果的第一个位置
            for (int i = 0; i < cursor.getCount(); i++) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                @SuppressLint("Range") String money = cursor.getString(cursor.getColumnIndex("money"));

                UserBean userBean = new UserBean();
                userBean.setName(name);
                userBean.setPhone(phone);
                userBean.setMoney(money);

                userBeans.add(userBean);
                //移动到下一位
                cursor.moveToNext();
            }
            cursor.close();//不关闭导致内存泄露
            db.close();

            for (UserBean userBean:userBeans){
                System.out.println(userBean);
            }

        }

        lv_list.setAdapter(new MyAdapter());
    }

    public void insert(View v){//添加用户
        SQLiteDatabase db = openHelper.getReadableDatabase();
        db.execSQL("insert into info ('name','phone','money') values ('王五','123456','1000')");
        db.close();
    }
}
/*模拟25以上可以
* adb root
* adb shell
* su
* cd data/data/com/example/sqlite/databases
* sqlite3 itheima.db
* .databases  查询所有数据库
* .tables  查询所有表
* select * from info;
* */