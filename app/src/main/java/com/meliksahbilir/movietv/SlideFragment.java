package com.meliksahbilir.movietv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meliksahbilir.movietv.ApiService.ApiClass;
import com.meliksahbilir.movietv.DataModel.MovieResponse;
import com.squareup.picasso.Picasso;

public class SlideFragment extends Fragment {

    private static final String SECTION_NUMBER = "section_number";
    private MovieResponse response;

    public SlideFragment() {
    }

    public static SlideFragment getInstance(int position, MovieResponse response)
    {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", response);
        args.putInt("SECTION_NUMBER", position);
        Log.i("Position : ", String.valueOf(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        response = getArguments().getParcelable("movie");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_movie_details, container, false);

        ImageView image = rootView.findViewById(R.id.image);
        TextView title = rootView.findViewById(R.id.title);
        TextView publish_date = rootView.findViewById(R.id.publish_date);
        TextView vote_average = rootView.findViewById(R.id.vote_average);
        TextView vote_count = rootView.findViewById(R.id.vote_count);
        TextView popularity = rootView.findViewById(R.id.popularity);
        TextView overview = rootView.findViewById(R.id.overview);

        Picasso.get().load(ApiClass.Base_Pic + response.getPoster_path()).fit().into(image);
        title.setText(response.getTitle());
        publish_date.setText(response.getRelease_date());
        vote_average.setText("10 / " + String.valueOf(response.getVote_average()));
        vote_count.setText(String.valueOf(response.getVote_count()));
        popularity.setText(String.valueOf(response.getPopularity()));
        overview.setText(response.getOverview());

        return rootView;
    }
}
