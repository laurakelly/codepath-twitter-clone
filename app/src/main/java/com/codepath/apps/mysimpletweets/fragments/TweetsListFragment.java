package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laura_kelly on 8/15/16.
 */
public class TweetsListFragment extends Fragment {
  private ArrayList<Tweet> tweets;
  private TweetsArrayAdapter aTweets;
  private RecyclerView rvTweets;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    tweets = new ArrayList<>();
    aTweets = new TweetsArrayAdapter(getActivity(), tweets);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_tweets_list, parent, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    rvTweets = (RecyclerView) view.findViewById(R.id.rvTweets);
    rvTweets.setAdapter(aTweets);

    rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  public void addAll(List<Tweet> tweets) {
    aTweets.addAll(tweets);
  }
}
