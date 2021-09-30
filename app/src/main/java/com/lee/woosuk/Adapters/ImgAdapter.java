package com.lee.woosuk.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lee.woosuk.DTOs.ImgDTO;
import com.lee.woosuk.R;

import java.util.ArrayList;

public class ImgAdapter extends BaseAdapter {
    private ArrayList<ImgDTO> listViewItemList = new ArrayList<ImgDTO>();

    public ImgAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView icon = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView userName = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView message = (TextView) convertView.findViewById(R.id.textView2) ;
        TextView time = (TextView) convertView.findViewById(R.id.textView3);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView2);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ImgDTO listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        icon.setImageDrawable(listViewItem.getIcon());
        userName.setText(listViewItem.getUserName());
        message.setText(listViewItem.getMessage());
        time.setText(listViewItem.getTime());

        Glide.with(context.getApplicationContext()).load(listViewItem.getImg()).override(480, 720).into(img);

        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addimg(Drawable icon, String userName, String message, String time, String img) {
        ImgDTO item = new ImgDTO();

        item.setIcon(icon);
        item.setUserName(userName);
        item.setMessage(message);
        item.setTime(time);
        item.setImg(img);

        listViewItemList.add(item);
    }
}
