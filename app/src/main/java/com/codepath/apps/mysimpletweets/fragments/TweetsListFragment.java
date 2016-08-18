package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Delete;
import com.codepath.apps.mysimpletweets.EndlessRecyclerViewScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laura_kelly on 8/15/16.
 */
public abstract class TweetsListFragment extends Fragment {
  private ArrayList<Tweet> tweets;
  private TweetsArrayAdapter aTweets;
  private RecyclerView rvTweets;
  private SwipeRefreshLayout swipeContainer;

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

    LinearLayoutManager lm = new LinearLayoutManager(getContext());
    rvTweets.setLayoutManager(lm);
    rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(lm) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        long maxId;

        if (aTweets.getItemCount() != 0) {
          maxId = aTweets.get(aTweets.getItemCount() - 1).getUid();
          loadMoreTweetsFromApi(maxId);
        }
      }
    });

    swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        aTweets.clear();
        populateTimeline();
        swipeContainer.setRefreshing(false);
      }
    });
  }

  protected abstract void loadMoreTweetsFromApi(long maxId);
  protected abstract void populateTimeline();

  public void addAll(List<Tweet> tweets) {
    aTweets.addAll(tweets);
  }

  public void addTweet(Tweet tweet) {
    aTweets.add(tweet);
  }

  protected void deletePopulated(List<Tweet> tweets) {
    for (int i = 0; i < tweets.size(); i++) {
      new Delete().from(Tweet.class).where("remote_id = ?", tweets.get(i).getRemoteId()).execute();
    }
  }

  public boolean isOnline() {
    Runtime runtime = Runtime.getRuntime();
    try {
      Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
      int     exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e)          { e.printStackTrace(); }
    catch (InterruptedException e) { e.printStackTrace(); }
    return false;
  }
}
