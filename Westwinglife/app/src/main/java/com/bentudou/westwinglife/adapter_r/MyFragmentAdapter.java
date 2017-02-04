package com.bentudou.westwinglife.adapter_r;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lzz on 2016/10/19.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        // TODO Auto-generated constructor stub
         this.fragmentList = fragmentList;
         }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
