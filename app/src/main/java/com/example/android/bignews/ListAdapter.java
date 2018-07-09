package com.example.android.bignews;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<News> {

    public static final String LOG_TAG = ListAdapter.class.getSimpleName();

    public ListAdapter(Activity context, ArrayList<News> news) {

        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) ;
        {
            listItemView = LayoutInflater.from(getContext()).inflate(com.example.android.bignews.R.layout.list_view, parent, false);
        }

        News currentNew = getItem(position);

        TextView newsTitle = listItemView.findViewById(com.example.android.bignews.R.id.title);
        newsTitle.setText(currentNew.getHeader());

        TextView newsAuthor = listItemView.findViewById(com.example.android.bignews.R.id.author);
        newsAuthor.setText(currentNew.getAuthor());

        TextView newsDate = listItemView.findViewById(com.example.android.bignews.R.id.date);
        newsDate.setText(currentNew.getDate());

        TextView newsCategory = listItemView.findViewById(com.example.android.bignews.R.id.newsCategory);
        newsCategory.setText(currentNew.getCategory());

        return listItemView;

    }
}
