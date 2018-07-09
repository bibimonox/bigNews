package com.example.android.bignews;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class NewsFetcher extends AsyncTaskLoader<List<News>> {

    private String url;

    public NewsFetcher(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of newss.
        List<News> news = ConnectionUtil.fetchNewsData(url);
        return news;
    }
}
