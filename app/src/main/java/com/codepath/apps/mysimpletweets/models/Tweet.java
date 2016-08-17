package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by laura_kelly on 8/15/16.
 */
@Parcel
public class Tweet {
  public String getBody() {
    return body;
  }

  public Long getUid() {
    return uid;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  private String body;
  private Long uid; // id of tweet

  public User getUser() {
    return user;
  }

  private User user;
  private String createdAt;

  public static Tweet fromJSON(JSONObject jsonObject) {
    Tweet tweet = new Tweet();

    try {
      tweet.body = jsonObject.getString("text");
      tweet.uid = jsonObject.getLong("id");
      tweet.createdAt = jsonObject.getString("created_at");
      tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return tweet;
  }

  public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
    ArrayList<Tweet> tweets = new ArrayList<>();

    for (int i = 0; i < json.length(); i++) {
      try {
        JSONObject tweetJson = json.getJSONObject(i);
        Tweet tweet = Tweet.fromJSON(tweetJson);
        if (tweet != null) {
          tweets.add(tweet);
        }
      } catch (JSONException e) {
        e.printStackTrace();
        continue;
      }
    }

    return tweets;
  }
}
