package com.example.bitcoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.loopj.android.http.*;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
      TextView mPriceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        final Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bitcoin", "" + parent.getItemAtPosition(position));
                Log.d("Bitcoin", "position is " + position);
                String finalurl = BASE_URL + parent.getItemAtPosition(position);
                Log.d("Bitcoin", "final url is " + finalurl);
                letsdosomeNetworking(finalurl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected");
            }
        });

    }
        private void letsdosomeNetworking(String ul){

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(ul, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("BITCOIN", "SUCCESS");
                    Log.d("BITCOIN", "JSON: " + response.toString());

                    try {
                        String price = response.getString("last");
                        mPriceTextView.setText(price);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                 public void onFailure(int statusCode,Header[] headers,Throwable e,JSONObject response) {
                    Log.d("BITCOIN", "Request fail! Status code: " + statusCode);
                    Log.d("BITCOIN", "Fail response: " + response);
                    Log.e("ERROR", e.toString());
                }
            });
        }
    }
