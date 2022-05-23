package com.example.album;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coverflow.ui.FeatureCoverFlow;
import java.util.ArrayList;
public class AlbumActivity extends AppCompatActivity {
    private ArrayList<AlbumBean> dataList;
    private TextSwitcher mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initData();
        initView();
    }
    /**
     * 初始化界面控件
     */
    private void initView() {
        mTitle = (TextSwitcher) findViewById(R.id.ts_title);
        mTitle.setFactory(() -> {
            LayoutInflater inflater = LayoutInflater.from(AlbumActivity.this);
            TextView title = (TextView) inflater.inflate(R.layout.item_title, null);
            return title;
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);
        FeatureCoverFlow coverFlow = (FeatureCoverFlow) findViewById(R.id.fcf_coverflow);
        AlbumAdapter adapter = new AlbumAdapter(this);
        adapter.setData(dataList);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnItemClickListener((parent, view, position, id) -> {
            if (position < dataList.size()) {
                Toast.makeText(AlbumActivity.this,
                        getResources().getString(dataList.get(position).titleResId),
                        Toast.LENGTH_SHORT).show();
            }
        });
        coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(getResources().getString(dataList.get(position).titleResId));
            }
            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });
    }
    /**
     * 初始化界面数据
     */
    private void initData() {
        dataList = new ArrayList<>();
        dataList.add(new AlbumBean(R.drawable.img_1, R.string.title1));
        dataList.add(new AlbumBean(R.drawable.img_2, R.string.title2));
        dataList.add(new AlbumBean(R.drawable.img_3, R.string.title3));
        dataList.add(new AlbumBean(R.drawable.img_4, R.string.title4));
        dataList.add(new AlbumBean(R.drawable.img_5, R.string.title5));
    }
    protected long exitTime;//记录第一次点击时的时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(AlbumActivity.this, "再按一次退出3D相册",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                AlbumActivity.this.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}