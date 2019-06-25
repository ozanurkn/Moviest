package com.ozan.moviest.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ozan.moviest.R;
import com.ozan.moviest.adapter.MovieAdapter;
import com.ozan.moviest.databinding.FragmentMovieDetailBinding;
import com.ozan.moviest.helper.BaseFragment;
import com.ozan.moviest.helper.Constant;
import com.ozan.moviest.helper.Controller;
import com.ozan.moviest.model.Movie;
import com.ozan.moviest.model.MovieDetail;
import com.ozan.moviest.model.response.UpComingAndNPResponse;
import com.ozan.moviest.network.Api;
import com.ozan.moviest.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailFragment extends BaseFragment<FragmentMovieDetailBinding> {
    public static final String TAG = MovieDetailFragment.class.getSimpleName();
    private int movieId;
    Call<MovieDetail> detailCall;
    boolean loading = false;

    public static MovieDetailFragment newInstance(int movieId) {

        Bundle args = new Bundle();
        args.putInt("movie_id",movieId);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_detail;
    }

    @Override
    public void initView() {

        movieId = getArguments().getInt("movie_id");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movieId = bundle.getInt("movie_id");
        }
        movieDetailService();
    }

    //region TopRatedService
    public void movieDetailService() {
        getBinding().loadingLayout.setVisibility(View.VISIBLE);
        if (detailCall != null && detailCall.isCanceled()) {
            try {
                detailCall.cancel();
            } catch (Exception er) {
                er.getMessage();
                Log.i(TAG, er.getMessage());
            }
            detailCall = null;
        }
        detailCall = ApiClient.getClient().create(Api.class).getMovieDetail(movieId, Constant.API_KEY, Controller.deviceLanguage);
        loading = true;
        detailCall.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                detailCall = null;
                loading = false;
                getBinding().loadingLayout.setVisibility(View.GONE);
                try {
                    if (response.code() == 200) {
                        loadingContent(response);
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                getBinding().loadingLayout.setVisibility(View.GONE);
                if (detailCall.isCanceled()) {
                    Log.e(TAG, "request was cancelled");
                } else {
                    Log.e(TAG, "other larger issue, i.e. no network connection?");
                }
                detailCall = null;
                t.printStackTrace();
            }
        });
    }

    public void loadingContent(Response<MovieDetail> response) {
        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .load("https://image.tmdb.org/t/p/w342/" + response.body().getPosterPath())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        getBinding().avlAvatar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        getBinding().avlAvatar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(getBinding().image);
        getBinding().rateText.setText(String.valueOf(response.body().getVoteAverage()));
        getBinding().voteText.setText(String.valueOf(response.body().getVoteCount()));
        getBinding().genres.setText(response.body().getOriginalTitle());
        getBinding().overView.setText(response.body().getOverview());
        getBinding().timeText.setText(response.body().getReleaseDate());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fav_menu,menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_fav);
        menu.removeItem(R.id.action_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_fav){
            item.setIcon(R.drawable.ic_favorite);
            Toast.makeText(getContext(),"Favorilere Eklenmistir",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
