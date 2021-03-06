package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
  TwitterClient client;
  User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String screenName = getIntent().getStringExtra("screenName");

    if (isOnline()) {
      setContentView(R.layout.activity_profile);
      client = TwitterApplication.getRestClient();

      if (screenName != null) {
        client.getUserShow(screenName, new JsonHttpResponseHandler() {
          @Override
          public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            user = User.fromJSON(response);
            getSupportActionBar().setTitle("@" + user.getScreenName());
            populateProfileHeader(user);
          }

          @Override
          public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.d("DEBUG", errorResponse.toString());
          }
        });
      } else {
        client.getUserInfo(new JsonHttpResponseHandler() {
          @Override
          public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            user = User.fromJSON(response);
            getSupportActionBar().setTitle("@" + user.getScreenName());
            populateProfileHeader(user);
          }
        });
      }

      if (savedInstanceState == null) {
        UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, fragmentUserTimeline);

        ft.commit();
      }
    }
  }

  private void populateProfileHeader(User user) {
    TextView tvName = (TextView) findViewById(R.id.tvName);
    TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
    TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
    TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
    ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

    tvName.setText(user.getName());
    tvTagline.setText(user.getTagline());
    tvFollowers.setText(user.getFollowersCount() + " Followers");
    tvFollowing.setText(user.getFriendsCount() + " Following");

    Picasso.with(this)
            .load(user.getProfileImageUrl())
            .into(ivProfileImage);
  }

  public boolean isOnline() {
    Runtime runtime = Runtime.getRuntime();
    try {
      Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
      int     exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e)          { e.printStackTrace(); }
    catch (InterruptedException e) { e.printStackTrace(); }
    return false;
  }
}
