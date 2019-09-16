package com.foton.panel;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.foton.library.base.activity.BaseLibActivity;

import java.util.ArrayList;

public abstract class BaseActivity extends BaseLibActivity {

    public void setTabLayout(final String[] titles, final ArrayList<Fragment> fragments, TabLayout tabLayout, ViewPager viewPager) {
        for (String str : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(str));
        }

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
