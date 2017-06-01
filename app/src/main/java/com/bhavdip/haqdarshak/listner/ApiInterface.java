package com.bhavdip.haqdarshak.listner;

import com.bhavdip.haqdarshak.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bhavdip on 29/5/17.
 */

public interface ApiInterface {
    @GET("articles")
    Call<NewsResponse> getTopRatedMovies(@Query("source") String source,
                                         @Query("sortBy") String sortBy,
                                         @Query("apiKey") String apiKey);

    @GET("movie/{id}")
    Call<NewsResponse> getMovieDetails(@Path("id") int id, @Query("apiKey") String apiKey);


}
