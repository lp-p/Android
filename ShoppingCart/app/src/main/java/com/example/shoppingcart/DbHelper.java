package com.example.shoppingcart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    ContentValues contentValues=new ContentValues();
    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table data(id Integer primary key autoincrement,name text,price text,count text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean add(String name, String price, String count) {
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("count",count);
        long flag=db.insert("data",null,contentValues);
        return flag>0?true:false;
    }

    public List<Goods> query() {
        List<Goods> list=new ArrayList<>();
        Cursor cursor=db.query("data",null,null,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Goods goods=new Goods();
                goods.setName(cursor.getString(1));
                goods.setPrice(cursor.getString(2));
                goods.setCount(cursor.getString(3));
                list.add(goods);
            }
        }
        return list;
    }

    public boolean update(String name, String price, String count) {
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("count",count);
        long flag=db.update("data",contentValues,"name=?",new String[]{name});
        return flag>0?true:false;
    }

    public List<Goods> get(String name) {
        List<Goods> list=new ArrayList<>();
        Cursor cursor=db.query("data",null,"name=?",new String[]{name},null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Goods goods=new Goods();
                goods.setName(cursor.getString(1));
                goods.setPrice(cursor.getString(2));
                goods.setCount(cursor.getString(3));
                list.add(goods);
            }
        }
        return list;
    }
    public boolean delete(String name) {
        long flag=db.delete("data","name=?",new String[]{name});
        return flag>0?true:false;
    }
}

