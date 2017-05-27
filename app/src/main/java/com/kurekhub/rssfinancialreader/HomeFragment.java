package com.kurekhub.rssfinancialreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.kurekhub.rssfinancialreader.database.RssFeederDbHelper;


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String EXTRA_TITLE = "com.kurekhub.rssfinancialreader.EXTRA_TITLE";
    public static final String EXTRA_LINK = "com.kurekhub.rssfinancialreader.EXTRA_LINK";
    public static final String EXTRA_PUB_DATE = "com.kurekhub.rssfinancialreader.EXTRA_PUB_DATE";
    public static final String EXTRA_DESCRIPTION = "com.kurekhub.rssfinancialreader.EXTRA_DESCRIPTION";
    private ProgressBar progressBar;
    private ListView listView;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.rss_list);
            listView.setOnItemClickListener(this);
            startService();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        EditText searchInput = (EditText) view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateNewsView(s.toString());
            }
        });

        return view;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    private void updateNewsView(String searchQuery) {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        RssFeederDbHelper handler = RssFeederDbHelper.getInstance(getActivity());
        SQLiteDatabase db = handler.getWritableDatabase();
        String sqlQuery;
        if(searchQuery == null) {
            sqlQuery = "SELECT * FROM rss_feeder ORDER BY _id ASC";
        }
        else {
            sqlQuery = "SELECT * FROM rss_feeder WHERE title LIKE '%" + searchQuery + "%' ORDER BY _id ASC";
        }
        Log.d("sqlQuery", sqlQuery);
        Cursor rssCursor = db.rawQuery(sqlQuery, null);
        NewsAdapter newsAdapter = new NewsAdapter(getActivity(), rssCursor);
        listView.setAdapter(newsAdapter);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            updateNewsView(null);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsAdapter adapter = (NewsAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), ItemDetails.class);
        intent.putExtra(EXTRA_TITLE, item.getTitle());
        intent.putExtra(EXTRA_LINK, item.getLink());
        intent.putExtra(EXTRA_PUB_DATE, item.getPubDate());
        intent.putExtra(EXTRA_DESCRIPTION, item.getDescription());
        startActivity(intent);
    }
}