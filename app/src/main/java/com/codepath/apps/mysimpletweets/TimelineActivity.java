package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {

  private SmartFragmentStatePagerAdapter adapterViewPager;
  private ViewPager vpPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    vpPager = (ViewPager) findViewById(R.id.viewpager);
    adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager(), TimelineActivity.this);
    vpPager.setAdapter(adapterViewPager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(vpPager);
  }

  public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
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
        return HomeTimelineFragment.newInstance();
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

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_timeline, menu);
    return true;
  }

  public void onProfileView(MenuItem mi) {
    Intent i = new Intent(this, ProfileActivity.class);
    startActivity(i);
  }

  public void onClickProfile(View view) {
    Intent i = new Intent(this, ProfileActivity.class);
    String screenName = (String) view.getTag(R.string.SCREENNAME_TAG);
    i.putExtra("screenName", screenName);
    startActivity(i);
  }

  private final int REQUEST_CODE = 20;

  public void onComposeView(MenuItem mi) {
    Intent i = new Intent(this, ComposeActivity.class);
    startActivityForResult(i, REQUEST_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
      Tweet composedTweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
      HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
      homeTimelineFragment.addTweet(composedTweet);
      Log.d("DEBUG", Parcels.unwrap(data.getParcelableExtra("tweet")).toString());
    }
  }
}
