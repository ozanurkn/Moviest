package com.ozan.moviest.network;

import com.ozan.moviest.model.MovieDetail;
import com.ozan.moviest.model.response.SearchResponse;
import com.ozan.moviest.model.response.TopRatedResponse;
import com.ozan.moviest.model.response.UpComingAndNPResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("movie/top_rated")
    Call<TopRatedResponse> getTopRatedList(@Query("api_key")String apiKey, @Query("language")String language, @Query("page")int page);

    @GET("movie/upcoming")
    Call<UpComingAndNPResponse> getUpcomingList(@Query("api_key")String apiKey, @Query("language")String language, @Query("page")int page);

    @GET("movie/now_playing")
    Call<UpComingAndNPResponse> getNowplayingList(@Query("api_key")String apiKey, @Query("language")String language, @Query("page")int page);

    @GET("search/movie")
    Call<SearchResponse> search(@Query("api_key")String apiKey, @Query("language")String language, @Query("query")String query, @Query("page")int page);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetail(@Path("movie_id")int movieId, @Query("api_key")String apiKey, @Query("language")String language);
}
