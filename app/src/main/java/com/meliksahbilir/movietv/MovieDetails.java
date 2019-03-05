package com.meliksahbilir.movietv;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.meliksahbilir.movietv.ApiService.Client;
import com.meliksahbilir.movietv.ApiService.GetMovie;
import com.meliksahbilir.movietv.ApiService.GetMovieService;
import com.meliksahbilir.movietv.DataModel.MovieResponse;

import org.apache.commons.io.FileUtils;

import java.util.List;

public class MovieDetails extends AppCompatActivity {

    private List<MovieResponse> movieResponseList;
    private ScreenSlidePagerAdapter slideAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pager);

        MovieResponse movieResponse = getIntent().getExtras().getParcelable("movie");

        GetMovieService getMovieService = Client.getClient();
        final GetMovie getMovie = new GetMovie(getMovieService);
        getMovie.getMovieResponsebyID(movieResponse);

        getMovie.getMovieRespbyID().observe(this, new Observer<List<MovieResponse>>() {
            @Override
            public void onChanged(@Nullable List<MovieResponse> movieResponses) {
                movieResponseList = movieResponses;
                if (!isDestroyed()) {
                    slideAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                    viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(slideAdapter);
                }
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SlideFragment.getInstance(position, movieResponseList.get(position));
        }

        @Override
        public int getCount() {
            return 20;
        }
    }
}
