package com.meliksahbilir.movietv.ApiService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.meliksahbilir.movietv.DataModel.Response;

public interface GetMovieService {

    @GET("top_rated?")
    Call<Response> getMovie(@Query("api_key") String api_key, @Query("page") int page);

    @GET("{movie_id}/similar?")
    Call<Response> getMoviebyID(@Path("movie_id") int ID, @Query("api_key") String api_key, @Query("page") int page);
}
