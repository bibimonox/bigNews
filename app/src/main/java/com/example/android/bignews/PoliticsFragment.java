package com.example.android.bignews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PoliticsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = ScienceFragment.class.getName();
    private static final String GUARDIAN_POLITICS = "https://content.guardianapis.com/search?q=politics&api-key=0ccc659e-e76e-41cf-9e2c-356e6c3cfec6&show-fields=thumbnail&show-tags=contributor";
    public TextView mEmptyStateTextView;
    /**
     * Adapter for the list of news
     */
    private ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(com.example.android.bignews.R.layout.news_item_list, container, false);
        final ArrayList<News> news = new ArrayList<News>();

        ListView listView = rootView.findViewById(com.example.android.bignews.R.id.list);

        mEmptyStateTextView = rootView.findViewById(com.example.android.bignews.R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        adapter = new ListAdapter(getActivity(), news);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current news story that was clicked on
                News currentNew = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNew.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectionManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(0, null, this).forceLoad();
        } else {

            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = rootView.findViewById(com.example.android.bignews.R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(com.example.android.bignews.R.string.connectionProblem);
        }
        return rootView;
    }


    @Override
        public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
            // Create a new loader for the given URL
            Log.i(LOG_TAG, "test: onCreateLoader() called...");

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplication());

            // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
            String minStories = sharedPrefs.getString(
                    getString(R.string.minArticlesKey),
                    getString(R.string.minArticlesDefault));

            String orderBy = sharedPrefs.getString(
                    getString(R.string.orderByKey),
                    getString(R.string.settingsOrderByDefault)
            );

            // parse breaks apart the URI string that's passed into its parameter
            Uri baseUri = Uri.parse(GUARDIAN_POLITICS);

            // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
            Uri.Builder uriBuilder = baseUri.buildUpon();

            // Append query parameter and its value.
            uriBuilder.appendQueryParameter("page-size", minStories);
            uriBuilder.appendQueryParameter("order-by", orderBy);

            Log.i(LOG_TAG, "test: url is " +uriBuilder.toString());

            return new NewsFetcher(getActivity().getApplication(), uriBuilder.toString());
        }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        adapter.clear();

        View loadingIndicator = getView().findViewById(com.example.android.bignews.R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.newsNull);

        adapter.clear();

        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

}