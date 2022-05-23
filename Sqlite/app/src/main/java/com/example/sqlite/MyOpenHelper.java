package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

	public MyOpenHelper(Context context) {
		super(context, "itheima.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//数据库第一次创建调用此方法
		db.execSQL("create table info (_id integer primary key autoincrement,name varchar(20),phone varchar(20),money varchar(20))");
		db.execSQL("insert into info ('name','phone','money') values ('张三','138888','2000')");
		db.execSQL("insert into info ('name','phone','money') values ('李四','139999','5000')");
		System.out.println("数据库创建成功！");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
