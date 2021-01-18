package com.example.livza.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    public ViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);

        return fragment;
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        /*switch (position) {
            case 0:
                return "Fragment 1";
            case 1:
                return "Fragment 2";
            case 2:
                return "Fragment 3";
        }*/
        return null;
    }
}

