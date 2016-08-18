package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.activeandroid.query.Select;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by laura_kelly on 8/15/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {
  private TwitterClient client;

  public static HomeTimelineFragment newInstance() {

    Bundle args = new Bundle();

    HomeTimelineFragment fragment = new HomeTimelineFragment();
    fragment.setArguments(args);
    return fragment;
  }

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
      client.getHomeTimeline(new JsonHttpResponseHandler() {
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
    } else {
      populateFromDB();
    }
  }

  public void populateFromDB() {
    List<Tweet> queryResults = new Select().from(Tweet.class).limit(25).execute();
    addAll(queryResults);
    //deletePopulated(queryResults);
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
