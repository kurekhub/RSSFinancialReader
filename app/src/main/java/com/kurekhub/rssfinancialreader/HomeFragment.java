package com.kurekhub.rssfinancialreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final ListView listview = (ListView) view.findViewById(R.id.news_list);
        String[] values = new String[] {
                "News 1",
                "News 2",
                "News 3",
                "News 4",
                "News 5",
                "News 6",
                "News 7",
                "News 8",
                "News 9",
                "News 10",
                "News 11",
                "News 12"
        };

        final ArrayList<NewsListItem> list = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            list.add(new NewsListItem(values[i]));
        }

        NewsAdapter itemsAdapter = new NewsAdapter(getActivity(), list);
        listview.setAdapter(itemsAdapter);

        return view;
    }
}