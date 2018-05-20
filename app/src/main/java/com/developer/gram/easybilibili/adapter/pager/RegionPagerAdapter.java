package com.developer.gram.easybilibili.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.developer.gram.easybilibili.bean.region.RegionTypesInfo;
import com.developer.gram.easybilibili.function.home.region.RegionTypeDetailsFragment;
import com.developer.gram.easybilibili.function.home.region.RegionTypeRecommendFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hcc on 16/8/4 14:12
 * 100332338@qq.com
 * <p/>
 * 分区界面PagerAdapter
 */
public class RegionPagerAdapter extends FragmentStatePagerAdapter {

  private int rid;
  private List<String> titles;
  private List<RegionTypesInfo.DataBean.ChildrenBean> childrens;
  private List<Fragment> fragments = new ArrayList<>();

  public RegionPagerAdapter(FragmentManager fm, int rid, List<String> titles,
                            List<RegionTypesInfo.DataBean.ChildrenBean> childrens) {

    super(fm);
    this.rid = rid;
    this.titles = titles;
    this.childrens = childrens;
    initFragments();
  }


  private void initFragments() {
    fragments.add(RegionTypeRecommendFragment.newInstance(rid));
    for(RegionTypesInfo.DataBean.ChildrenBean child : childrens) {
      fragments.add(RegionTypeDetailsFragment.
              newInstance(child.getTid()));
    }
  }


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
    return titles.get(position);
  }
}
