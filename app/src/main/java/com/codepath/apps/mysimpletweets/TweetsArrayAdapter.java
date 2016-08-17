package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    public ViewHolder(View itemView) {
      super(itemView);

      ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
      tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
      tvBody = (TextView) itemView.findViewById(R.id.tvBody);
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
    TextView tvUserName = viewHolder.tvUserName;
    TextView tvBody = viewHolder.tvBody;

    tvUserName.setText(tweet.getUser().getScreenName());
    tvBody.setText(tweet.getBody());

    ivProfileImage.setImageResource(android.R.color.transparent);
    Picasso.with(getContext())
            .load(tweet.getUser().getProfileImageUrl())
            .into(ivProfileImage);
  }

  @Override
  public int getItemCount() {
    return mTweets.size();
  }

  public void addAll(List<Tweet> tweets) {
    mTweets.addAll(tweets);
    this.notifyDataSetChanged();
  }
}
