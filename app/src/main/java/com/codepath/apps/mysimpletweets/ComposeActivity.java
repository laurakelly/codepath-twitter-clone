package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
  private TwitterClient client;
  private EditText etTweet;
  private TextView tvCharCount;
  private Button btnTweet;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose);
    client = TwitterApplication.getRestClient();

    etTweet = (EditText) findViewById(R.id.etTweet);
    tvCharCount = (TextView) findViewById(R.id.tvCharCount);
    btnTweet = (Button) findViewById(R.id.btnTweet);

    etTweet.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        int charCount = editable.toString().length();
        int red = getResources().getColor(R.color.red);
        int gray = getResources().getColor(R.color.gray);

        tvCharCount.setText(String.valueOf(charCount));

        if (charCount > 140) {
          tvCharCount.setTextColor(red);
          btnTweet.setEnabled(false);
        } else {
          tvCharCount.setTextColor(gray);
          btnTweet.setEnabled(true);
        }
      }
    });
  }

  public void onTextChanged() {
    // change text

    // if over 140

    // disable button
    // make text red
  }

  public void onSubmitTweet(View view) {
    EditText etTweet = (EditText) findViewById(R.id.etTweet);
    String status = etTweet.getText().toString();

    client.postTweet(status, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Intent data = new Intent();
        Tweet composedTweet = Tweet.fromJSON(response);
        data.putExtra("tweet", Parcels.wrap(composedTweet));
        setResult(RESULT_OK, data);
        finish();
      }
    });
  }
}
