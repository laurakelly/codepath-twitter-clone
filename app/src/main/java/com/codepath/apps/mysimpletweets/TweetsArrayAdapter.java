package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by laura_kelly on 8/15/16.
 */
public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {

  public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
    mTweets = new ArrayList<Tweet>();
    mContext = context;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivProfileImage;
    public TextView tvUserName;
    public TextView tvBody;
    public TextView tvTimestamp;

    public ViewHolder(View itemView) {
      super(itemView);

      ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
      tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
      tvBody = (TextView) itemView.findViewById(R.id.tvBody);
      tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
    }
  }

  private List<Tweet> mTweets;
  private Context mContext;

  private Context getContext() {
    return mContext;
  }

  @Override
  public TweetsArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);

    ViewHolder viewHolder = new ViewHolder(tweetView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(TweetsArrayAdapter.ViewHolder viewHolder, int position) {
    Tweet tweet = mTweets.get(position);

    ImageView ivProfileImage = viewHolder.ivProfileImage;
    ivProfileImage.setTag(R.string.SCREENNAME_TAG, tweet.getUser().getScreenName());
    TextView tvUserName = viewHolder.tvUserName;
    TextView tvBody = viewHolder.tvBody;
    TextView tvTimestamp = viewHolder.tvTimestamp;

    tvUserName.setText(tweet.getUser().getScreenName());
    tvBody.setText(tweet.getBody());
    tvTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

    ivProfileImage.setImageResource(android.R.color.transparent);
    Picasso.with(getContext())
            .load(tweet.getUser().getProfileImageUrl())
            .into(ivProfileImage);
  }

  @Override
  public int getItemCount() {
    return mTweets.size();
  }

  public Tweet get(int index) {
    return mTweets.get(index);
  }

  public void addAll(List<Tweet> tweets) {
    mTweets.addAll(tweets);
    this.notifyDataSetChanged();
  }

  public void add(Tweet tweet) {
    mTweets.add(0, tweet);
    this.notifyDataSetChanged();
  }

  public void clear() {
    mTweets.clear();
    notifyDataSetChanged();
  }

  // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
  public String getRelativeTimeAgo(String rawJsonDate) {
    String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
    sf.setLenient(true);

    String relativeDate = "";
    try {
      long dateMillis = sf.parse(rawJsonDate).getTime();
      relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
              System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return relativeDate;
  }
}
