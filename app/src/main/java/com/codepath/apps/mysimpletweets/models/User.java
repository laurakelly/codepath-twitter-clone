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

  public static User fromJSON(JSONObject jsonObject) {
    User u = new User();

    try {
      u.name = jsonObject.getString("name");
      u.screenName = jsonObject.getString("screen_name");
      u.profileImageUrl = jsonObject.getString("profile_image_url");
      u.uid = jsonObject.getLong("id");
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return u;
  }
}
