package com.meliksahbilir.movietv;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.meliksahbilir.movietv.Adapter.ItemAdapter;
import com.meliksahbilir.movietv.ApiService.Client;
import com.meliksahbilir.movietv.ApiService.GetMovie;
import com.meliksahbilir.movietv.ApiService.GetMovieService;
import com.meliksahbilir.movietv.DataModel.MovieResponse;

import org.apache.commons.io.FileUtils;

import java.util.ArrayList;
import java.util.List;


public class MovieActivity extends AppCompatActivity implements ItemAdapter.ItemClickListener {

    private List<MovieResponse> movieResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GetMovieService getMovieService = Client.getClient();
        final GetMovie getMovie = new GetMovie(getMovieService);
        getMovie.getMovieResp();

        final ItemAdapter itemAdapter = new ItemAdapter(new ArrayList<MovieResponse>(0));
        itemAdapter.setClickListener(this);
        recyclerView.setAdapter(itemAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                getMovie.getMovieResp();
            }
        });

        getMovie.getMovieResponse().observe(this, new Observer<List<MovieResponse>>() {
            @Override
            public void onChanged(@Nullable List<MovieResponse> movieResponses) {
                movieResponseList = movieResponses;
                if (movieResponseList != null && movieResponseList.size() > 0) {
                    if (!isDestroyed()) {
                        itemAdapter.setItems(movieResponses);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        final MovieResponse movieResponse = movieResponseList.get(position);
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieResponse);
        startActivity(intent);
    }
}
