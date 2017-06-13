package com.xyd.project.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xyd.project.R;

import java.util.List;

/**
 * Created by xiaojun on 17/6/6.
 */
public class ShareTypeAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public ShareTypeAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.share_detail_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(list.get(position));
        return convertView;
    }
}
