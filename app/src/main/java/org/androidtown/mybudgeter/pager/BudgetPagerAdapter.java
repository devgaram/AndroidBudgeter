package org.androidtown.mybudgeter.pager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.Pair;

import java.util.List;

public class BudgetPagerAdapter extends FragmentStatePagerAdapter {
    private List<Pair<PagerFragment, String>> fragmentList;

    public BudgetPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPagerData(List<Pair<PagerFragment, String>> fragmentList) {
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i).first;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).second;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
