package com.kurekhub.rssfinancialreader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private final List<RssItem> items;
    private final Context content;

    static class ViewHolder {
        TextView itemTitle;
    }

    public NewsAdapter(Context content, List<RssItem> items) {
        this.items = items;
        this.content = content;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(content, R.layout.news_list_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.news_list_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemTitle.setText(items.get(position).getTitle());

        return convertView;
    }
}