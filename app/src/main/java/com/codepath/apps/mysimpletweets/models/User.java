package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by laura_kelly on 8/15/16.
 */
public class User {
  public String getName() {
    return name;
  }

  public long getUid() {
    return uid;
  }

  public String getScreenName() {
    return screenName;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  private String name;
  private long uid;
  private String screenName;
  private String profileImageUrl;

  public String getTagline() {
    return tagline;
  }

  public int getFollowersCount() {
    return followersCount;
  }

  public int getFriendsCount() {
    return followingCount;
  }

  private String tagline;
  private int followersCount;
  private int followingCount;

  public static User fromJSON(JSONObject jsonObject) {
    User u = new User();

    try {
      u.name = jsonObject.getString("name");
      u.screenName = jsonObject.getString("screen_name");
      u.profileImageUrl = jsonObject.getString("profile_image_url");
      u.uid = jsonObject.getLong("id");
      u.tagline = jsonObject.getString("description");
      u.followersCount = jsonObject.getInt("followers_count");
      u.followingCount = jsonObject.getInt("friends_count");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return u;
  }

}
