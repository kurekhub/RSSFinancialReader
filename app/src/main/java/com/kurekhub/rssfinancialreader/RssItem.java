package com.kurekhub.rssfinancialreader;


public class RssItem {
    private final String title;
    private final String link;

    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }
}
