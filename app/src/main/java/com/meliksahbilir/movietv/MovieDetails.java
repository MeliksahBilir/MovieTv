package com.meliksahbilir.movietv;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.meliksahbilir.movietv.Adapter.ItemAdapter;
import com.meliksahbilir.movietv.ApiService.ApiClass;
import com.meliksahbilir.movietv.ApiService.GetMovie;
import com.meliksahbilir.movietv.ApiService.GetMovieService;
import com.meliksahbilir.movietv.DataModel.MovieResponse;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    private List<MovieResponse> movieResponseList;
    private MovieResponse mmovieResponseList;
    private ScreenSlidePagerAdapter slideAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pager);

        MovieResponse movieResponse = getIntent().getExtras().getParcelable("movie");

        GetMovieService getMovieService = null;
        final GetMovie getMovie = new GetMovie(getMovieService);
        getMovie.getMovieResponsebyID(movieResponse);

        getMovie.getMovieResponsebyID().observe(this, new Observer<List<MovieResponse>>() {
            @Override
            public void onChanged(@Nullable List<MovieResponse> movieResponses) {
                movieResponseList = movieResponses;
                if (movieResponseList != null && movieResponseList.size() > 0) {
                    if (!isDestroyed()) {
                        slideAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                        viewPager = findViewById(R.id.view_pager);
                        viewPager.setAdapter(slideAdapter);
                    }
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

    @Override
    protected void onDestroy() {
        FileUtils.deleteQuietly(getApplicationContext().getCacheDir());
        Log.i("Application Terminated ", "Details");
        super.onDestroy();
    }
}
