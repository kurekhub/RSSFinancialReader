package com.kurekhub.rssfinancialreader;


public class RssItem {
    private final String title;
    private final String link;
    private final String pubDate;

    public RssItem(String title, String link, String pubDate) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }
}
