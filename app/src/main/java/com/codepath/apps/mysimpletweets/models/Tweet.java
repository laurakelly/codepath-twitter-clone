package com.codepath.apps.mysimpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by laura_kelly on 8/15/16.
 */
@Parcel(analyze = Tweet.class)
@Table(name = "Tweets")
public class Tweet extends Model {

  @Column(name="remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  private long remoteId;

  @Column(name = "Body")
  private String body;

  @Column(name = "Uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  private Long uid; // id of tweet

  @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
  private User user;

  @Column(name = "CreatedAt")
  private String createdAt;

  public String getBody() {
    return body;
  }

  public Long getUid() {
    return uid;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public User getUser() {
    return user;
  }

  public Tweet() {
    super();
  }

  public long getRemoteId() {
    return remoteId;
  }

  public static Tweet fromJSON(JSONObject jsonObject) {
    Tweet tweet = new Tweet();

    try {
      tweet.body = jsonObject.getString("text");
      tweet.uid = jsonObject.getLong("id");
      tweet.remoteId = jsonObject.getLong("id");
      tweet.createdAt = jsonObject.getString("created_at");
      tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
      tweet.user.save();
      tweet.save();
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
