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
 * Created by laura_kelly on 8/15/16.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
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
      client.getMentionsTimeline(new JsonHttpResponseHandler() {
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
    // TODO fetch from db if offline
  }

  @Override
  protected void loadMoreTweetsFromApi(long maxId) {
    client.getMentionsTimeline(new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
        addAll(Tweet.fromJSONArray(json));
      }
    }, maxId);
  }
}
