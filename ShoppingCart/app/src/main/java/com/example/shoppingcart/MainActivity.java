package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_name,et_price,et_count;
    private Button bt_add,bt_update,bt_delete,bt_query;
    private ListView lv_show;
    private DbHelper dbHelper;
    private String name,price,count;
    private List<Goods> goodsList;
    private MyAdapter myAdapter;
    private List<Goods> findList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        dbHelper=new DbHelper(MainActivity.this,"data.db",null,1);
        if (goodsList!=null){
            goodsList.clear();
        }
        goodsList=dbHelper.query();
        myAdapter=new MyAdapter(goodsList,MainActivity.this);
        lv_show.setAdapter(myAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete:
                name=et_name.getText().toString();
                if (dbHelper.delete(name)){
                    showToast("删除成功");
                    showListView();
                }else {
                    showToast("删除失败");
                }
                break;
            case R.id.add:
                name=et_name.getText().toString();
                price=et_price.getText().toString();
                count=et_count.getText().toString();
                if (name==null){
                    showToast("名称不能为空！");
                }else {
                    if (dbHelper.add(name,price,count)){
                        showToast("添加成功");
                        showListView();
                    }else {
                        showToast("添加失败");
                    }
                }
                break;
            case R.id.query:
                name=et_name.getText().toString();
                if (findList!=null){
                    findList.clear();
                }
                findList=dbHelper.get(name);
                myAdapter=new MyAdapter(findList,MainActivity.this);
                lv_show.setAdapter(myAdapter);
                break;
            case R.id.update:
                name=et_name.getText().toString();
                price=et_price.getText().toString();
                count=et_count.getText().toString();
                if (dbHelper.update(name,price,count)){
                    showToast("修改成功");
                    showListView();
                }else {
                    showToast("修改失败,名称不能修改");
                }
                break;
        }
    }
    private void init() {
        et_count=findViewById(R.id.et_number);
        et_name=findViewById(R.id.et_name);
        et_price=findViewById(R.id.et_price);
        bt_add=findViewById(R.id.add);
        bt_delete=findViewById(R.id.delete);
        bt_query=findViewById(R.id.query);
        bt_update=findViewById(R.id.update);
        lv_show=findViewById(R.id.listView);
        bt_add.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
    }
    private void showToast(String msg){
        Toast.makeText(MainActivity.this,msg, Toast.LENGTH_SHORT).show();
    }
    private void showListView(){
        goodsList=dbHelper.query();
        myAdapter=new MyAdapter(goodsList,MainActivity.this);
        lv_show.setAdapter(myAdapter);
    }
}
