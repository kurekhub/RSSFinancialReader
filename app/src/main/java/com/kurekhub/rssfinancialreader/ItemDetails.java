package com.kurekhub.rssfinancialreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;

public class ItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent intent = getIntent();
        String title = intent.getStringExtra(HomeFragment.EXTRA_TITLE);
        String link = intent.getStringExtra(HomeFragment.EXTRA_LINK);
        String pubDate = intent.getStringExtra(HomeFragment.EXTRA_PUB_DATE);
        String description = intent.getStringExtra(HomeFragment.EXTRA_DESCRIPTION);

        TextView tvTitle = (TextView) findViewById(R.id.item_details_title);
        TextView tvPubDate = (TextView) findViewById(R.id.item_details_pub_date);

        tvTitle.setText(title);
        tvPubDate.setText(pubDate);

        String imageURL = Jsoup.parse(description).select("img").first().attr("src");
        ImageView imageView = (ImageView) findViewById(R.id.item_details_image);
        description = description.replaceAll("<img(.*?)>", "").trim();

        TextView tvDescription = (TextView) findViewById(R.id.item_details_description);
        tvDescription.setText(description);

        new DownloadImageTask(imageView).execute(imageURL);
    }
}
