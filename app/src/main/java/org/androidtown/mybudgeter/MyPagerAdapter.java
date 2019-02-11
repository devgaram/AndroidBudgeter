package org.androidtown.mybudgeter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mData;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

        mData = new ArrayList<>();
        mData.add(new BalanceFragment());
        mData.add(new BudgetPlanFragment());
        mData.add(new SpendListFragment());
    }

    // 리스트뷰의 getView() 메서드에 해당하는 부분
    // 리스트뷰는 각 아이템에 표시할 뷰를 정의했다면, 여기서는 각 위치에 표시할 프래그먼트를 정의해준다.
    @Override
    public Fragment getItem(int i) {
        return mData.get(i);
    }

    @Override
    public int getCount() {
        return 0;
    }
}
