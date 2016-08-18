package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by laura_kelly on 8/16/16.
 */
public class UserTimelineFragment extends TweetsListFragment {
  private TwitterClient client;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    client = TwitterApplication.getRestClient();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    populateTimeline();
  }

  @Override
  protected void populateTimeline() {
    if (isOnline()) {
      String screenName = getArguments().getString("screen_name");
      client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
          Log.d("DEBUG", json.toString());

          addAll(Tweet.fromJSONArray(json));
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
          Log.d("DEBUG", errorResponse.toString());
        }
      });
    }
  }

  public static UserTimelineFragment newInstance(String screen_name) {
    UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
    Bundle args = new Bundle();
    args.putString("screen_name", screen_name);
    userTimelineFragment.setArguments(args);
    return userTimelineFragment;
  }

  @Override
  protected void loadMoreTweetsFromApi(long maxId) {
    client.getHomeTimeline(new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
        addAll(Tweet.fromJSONArray(json));
      }
    }, maxId);
  }
}
