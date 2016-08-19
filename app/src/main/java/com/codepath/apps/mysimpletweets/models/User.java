package com.codepath.apps.mysimpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by laura_kelly on 8/15/16.
 */
@Parcel(analyze = User.class)
@Table(name="Users")
public class User extends Model {

  @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  private long remoteId;

  @Column(name = "Name")
  private String name;

  @Column(name = "Uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  private long uid;

  @Column(name = "ScreenName", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  private String screenName;

  @Column(name = "ProfileImageUrl")
  private String profileImageUrl;

  @Column(name = "Tagline")
  private String tagline;

  @Column(name = "FollowersCount")
  private int followersCount;

  @Column(name = "FollowingCount")
  private int followingCount;

  public User() {
    super();
  }

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

  public String getTagline() {
    return tagline;
  }

  public int getFollowersCount() {
    return followersCount;
  }

  public int getFriendsCount() {
    return followingCount;
  }

  public static User fromJSON(JSONObject jsonObject) {
    User u = new User();

    try {
      u.name = jsonObject.getString("name");
      u.screenName = jsonObject.getString("screen_name");
      u.profileImageUrl = jsonObject.getString("profile_image_url");
      u.uid = jsonObject.getLong("id");
      u.remoteId = jsonObject.getLong("id");
      u.tagline = jsonObject.getString("description");
      u.followersCount = jsonObject.getInt("followers_count");
      u.followingCount = jsonObject.getInt("friends_count");
      u.save();
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return u;
  }

}
