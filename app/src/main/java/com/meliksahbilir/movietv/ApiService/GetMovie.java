package com.meliksahbilir.movietv.ApiService;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import com.meliksahbilir.movietv.DataModel.Response;
import com.meliksahbilir.movietv.DataModel.MovieResponse;

public class GetMovie {

    @NonNull
    private final GetMovieService service;
    private MutableLiveData<Integer> page;
    private MutableLiveData<List<MovieResponse>> movieResp;
    private MutableLiveData<List<MovieResponse>> selectedResp;

    public GetMovie(GetMovieService service) {
        this.service = service;
        page = new MutableLiveData<>();
        movieResp = new MutableLiveData<>();
        selectedResp = new MutableLiveData<>();
    }

    public MutableLiveData<List<MovieResponse>> getMovieResp() {
        service.getMovie(Client.API_KEY, page.getValue() != null ? page.getValue() + 1 : 1)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        page.setValue(response.body().getPage());
                        if (movieResp.getValue() == null){
                            movieResp.setValue(response.body().getResults());
                        }else{
                            List<MovieResponse> movieResponses = movieResp.getValue();
                            movieResponses.addAll(response.body().getResults());
                            movieResp.setValue(movieResponses);
                        }
                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        movieResp.setValue(null);
                    }
                });
        return movieResp;
    }

    public MutableLiveData<List<MovieResponse>> getMovieResponsebyID(final MovieResponse selectedMovie){
        service.getMoviebyID(selectedMovie.getId(), Client.API_KEY, page.getValue() != null ? page.getValue() + 1 : 1)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        page.setValue(response.body().getPage());
                        if (selectedResp.getValue() == null){
                            if (response.body().getResults().size() != 0){
                                List<MovieResponse> result = response.body().getResults();
                                result.add(0, selectedMovie);
                                selectedResp.setValue(result);
                            }
                        }else{
                            List<MovieResponse> value = selectedResp.getValue();
                            value.addAll(response.body().getResults());
                            selectedResp.setValue(value);
                        }
                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        selectedResp.setValue(null);
                    }
                });

        return selectedResp;
    }

    public void clearData() {
        movieResp.setValue(null);
        selectedResp.setValue(null);
    }

    public MutableLiveData<List<MovieResponse>> getMovieResponse() {
        return movieResp;
    }

    public MutableLiveData<List<MovieResponse>> getMovieRespbyID() {
        return selectedResp;
    }
}
