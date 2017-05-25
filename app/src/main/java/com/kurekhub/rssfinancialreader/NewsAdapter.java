package com.kurekhub.rssfinancialreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsListItem> {
    public NewsAdapter(Context context, ArrayList<NewsListItem> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsListItem newsListItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.news_list_item_title);
        title.setText(newsListItem.title);

        return convertView;
    }
}