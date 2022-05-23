package com.example.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

	public MyOpenHelper(Context context) {
		super(context, "itheima.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//数据库第一次创建调用此方法
		db.execSQL("create table info (_id integer primary key autoincrement,name varchar(20),email varchar(20),pwd varchar(20),sex varchar(2),hobbys varchar(20))");
		System.out.println("数据库创建成功！");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
