package com.example.android.bignews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Adapter extends FragmentPagerAdapter {
    /** Context of the app */
    private Context context;

    /**
     * Create a new {@link Adapter} object.
     *
     * @param context is the context of the app
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public Adapter(Context context, FragmentManager fm) {
        super(fm);
        this.context =context;
    }

    public Fragment getItem(int position) {

        if (position == 0) {
            return new SportsFragment();
        } else if (position == 1) {
            return new ScienceFragment();
        } else if (position == 2) {
            return new PoliticsFragment();
        } else {
            return new BooksFragment();
        }
    }

    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.sports);
        } else if (position == 1) {
            return context.getString(R.string.science);
        } else if (position == 2) {
            return context.getString(R.string.politics);
        } else {
            return context.getString(R.string.books);
        }

    }
}
