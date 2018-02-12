package com.allfeature;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by skycorp on 23/11/17.
 */

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter (FragmentManager fragment){
        super(fragment);

    }


    @Override
    public Fragment getItem(int position) {
        if (position==0){
            SignInFragment SIF = new SignInFragment();
            return SIF;
        }else{
            SignUpFragment SUF = new SignUpFragment();
            return SUF;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "SIGN IN";
        }else{
            return "SIGN UP";
        }
    }
}
