package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;

public class TimelineActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
    vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), TimelineActivity.this));

    TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(vpPager);
  }

  public class TweetsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = { "Home", "Mentions" };
    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
      super(fm);
      this.context = context;
    }

    @Override
    public int getCount() {
      return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
      if (position == 0) {
        return new HomeTimelineFragment();
      } else if (position == 1) {
        return new MentionsTimelineFragment();
      } else {
        return null;
      }
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return tabTitles[position];
    }
  }
}
