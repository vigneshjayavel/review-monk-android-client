package com.reviewmonk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.reviewmonk.fragments.GadgetsFragment;
import com.reviewmonk.fragments.SearchFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	  public TabsPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
	 
	    @Override
	    public Fragment getItem(int index) {
	 
	        switch (index) {
	        case 0:	   
	            return new SearchFragment();
	            
	        case 1:
	            return new GadgetsFragment();
	        }
	 
	        return null;
	    }
	 
	    @Override
	    public int getCount() {
	        // get item count - equal to number of tabs
	        return 2;
	    }
	 

}
