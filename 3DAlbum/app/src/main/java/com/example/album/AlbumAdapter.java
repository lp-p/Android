package com.example.album;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
public class AlbumAdapter extends BaseAdapter {
    private ArrayList<AlbumBean> dataList = new ArrayList<>();
    private final Context mContext;
    public AlbumAdapter(Context context) {
        mContext = context;
    }
    public void setData(ArrayList<AlbumBean> dataList) {
        this.dataList = dataList;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }
    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_album, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.iv_img.setImageResource(dataList.get(position).imgResId);
        return convertView;
    }
    public static class ViewHolder {
        public ImageView iv_img;
    }
}
