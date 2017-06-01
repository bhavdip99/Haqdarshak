package com.bhavdip.haqdarshak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bhavdip.haqdarshak.adapter.NewsAdapter;
import com.bhavdip.haqdarshak.listner.ApiInterface;
import com.bhavdip.haqdarshak.model.News;
import com.bhavdip.haqdarshak.model.NewsResponse;
import com.bhavdip.haqdarshak.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bhavdip on 29/5/17.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();

    // TODO - insert your newsapi.org/ API KEY here
    private final static String API_KEY = "416681c59bb84778a3a4b24b4acf5a4b";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from newsapi.org", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<NewsResponse> call = apiService.getTopRatedMovies("the-times-of-india", "top", API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                List<News> news = response.body().getArticles();
                Log.d(TAG, "Number of news received: " + news.size());
                recyclerView.setAdapter(new NewsAdapter(news, R.layout.list_item_news, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
