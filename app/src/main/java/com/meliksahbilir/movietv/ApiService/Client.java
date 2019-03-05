package com.meliksahbilir.movietv.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public static final String API_KEY = "4d59eaf6fca0bca32e6bac565e424a32";
    public static final String Base_URL = "https://api.themoviedb.org/3/movie/";
    public static final String Base_PIC = "https://image.tmdb.org/t/p/w500/";

    private static GetMovieService client;

    public static GetMovieService getClient(){
        if (client == null)
            createClient();
        return client;
    }

    private static void createClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Client.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        client = retrofit.create(GetMovieService.class);
    }
}
