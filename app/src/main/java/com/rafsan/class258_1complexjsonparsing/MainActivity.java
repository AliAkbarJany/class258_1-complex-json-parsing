package com.rafsan.class258_1complexjsonparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);


        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://ali71.000webhostapp.com/apps/complex.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);

                        Log.d("serverRes", String.valueOf(response));

                        try {
                            String movieName = response.getString("movie_name");
                            String director = response.getString("director");

                            textView.setText(movieName+"\n"+director+"\n\n");

                            JSONArray jsonArray = response.getJSONArray("casting");

                            for (int x=0; x<jsonArray.length(); x++){
                                JSONObject jsonObject = jsonArray.getJSONObject(x);

                                String actor = jsonObject.getString("actors");
                                String actress = jsonObject.getString("actress");

                                textView.append(x+" . "+actor+"\n"+actress+"\n \n");
                            }




                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        textView.append("Volley Error");

                    }
                }
        );

        queue.add(jsonObjectRequest);

    }
}