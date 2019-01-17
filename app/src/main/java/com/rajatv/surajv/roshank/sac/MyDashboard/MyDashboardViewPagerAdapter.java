package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyDashboardViewPagerAdapter  extends FragmentStatePagerAdapter {

    private final List<Fragment> listFragment= new ArrayList<>();
    private final List<String> listTitle=new ArrayList<>();


    public MyDashboardViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return listFragment.get(i);
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }


    public void addFragment(Fragment fragment, String title){
        listFragment.add(fragment);
        listTitle.add(title);
    }

    public void clear(){
        listFragment.clear();
        listTitle.clear();
    }
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /*@Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
*/

}
