package com.example.customdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.text.TextUtils;
import android.os.Bundle;

public class CommonDialog extends AlertDialog {
    private TextView titleTv;//标题
    private  TextView messageTv;//消息
    private Button negtiveBn, positiveBn;//确认、取消
    public CommonDialog(Context context){
        super(context);
    }

    private String title;
    private String message;
    private String negtive, positive;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        initView();//初始化界面控件
        initEvent();//初始化界面控件的点击事件
    }
    //初始化界面控件
    private void initView(){
        negtiveBn = (Button)findViewById(R.id.negtive);
        positiveBn = (Button)findViewById(R.id.positive);
        titleTv = (TextView)findViewById(R.id.title);
        messageTv = (TextView)findViewById(R.id.message);
    }
    //初始化界面控件的显示数据
    private void refreshView(){
        //如果自定义了信息，会在弹出框中显示
        if (!TextUtils.isEmpty(title)){
            titleTv.setText(title);//设置标题控件的文本为自定义的title
            titleTv.setVisibility(View.VISIBLE);//标题控件设置为显示状态
        }else {
            titleTv.setVisibility(View.GONE);//标题控件设置为隐藏状态
        }

        if (!TextUtils.isEmpty(message)){
            messageTv.setText(message);
        }

        if (!TextUtils.isEmpty(positive)){
            positiveBn.setText(positive);
        }else {
            positiveBn.setText(" 确定 ");//设置按钮文本
        }
        if (!TextUtils.isEmpty(negtive)){
            negtiveBn.setText(negtive);
        }else {
            negtiveBn.setText(" 取消 ");
        }
    }
    //初始化界面的确定和取消监听器
    private void initEvent(){
        positiveBn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (onClickBottomListener!=null){
                    onClickBottomListener.onPositiveClick();;
                }
            }
        });
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickBottomListener!=null){
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    @Override
    public void show(){
        super.show();
        refreshView();
    }

    public interface OnClickBottomListener{
        void onPositiveClick();//实现“确定”按钮点击事件的方法
        void onNegtiveClick();//“取消”
    }
    //设置按钮回调
    public OnClickBottomListener onClickBottomListener;

    public CommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener){
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }
    public CommonDialog setMaeeage(String message){
        this.message = message;
        return this;
    }
    public CommonDialog setTitle(String title){
        this.title = title;
        return this;
    }
    public CommonDialog setPositive(String positive){
        this.positive = positive;
        return this;
    }
    public CommonDialog setNegtive(String negtive){
        this.negtive = negtive;
        return this;
    }
}
