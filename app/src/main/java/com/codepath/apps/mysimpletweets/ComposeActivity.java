package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
  private TwitterClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);
    client = TwitterApplication.getRestClient();
  }

  public void onSubmitTweet(View view) {
    EditText etTweet = (EditText) findViewById(R.id.etTweet);
    String status = etTweet.getText().toString();

    client.postTweet(status, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);

        Intent data = new Intent();
        Tweet composedTweet = Tweet.fromJSON(response);
        data.putExtra("tweet", Parcels.wrap(composedTweet));
        setResult(RESULT_OK, data);
        finish();
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
      }
    });
  }
}
