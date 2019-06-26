package com.ozan.moviest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ozan.moviest.R;
import com.ozan.moviest.adapter.MovieAdapter;
import com.ozan.moviest.databinding.FragmentFavoritiesBinding;
import com.ozan.moviest.helper.BaseFragment;
import com.ozan.moviest.model.Movie;
import com.ozan.moviest.model.MovieDetail;
import com.ozan.moviest.store.localdb.FavoriteMovie;
import com.ozan.moviest.store.localdb.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoritiesFragment extends BaseFragment<FragmentFavoritiesBinding> {
    MovieAdapter movieAdapter;
    public static FavoritiesFragment newInstance(){
        Bundle args = new Bundle();
        FavoritiesFragment fragment = new FavoritiesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FavoritiesFragment(){

    }

    List<Movie> favoriteList;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorities;
    }

    @Override
    public void initView() {

        favoriteList = new ArrayList<>();
        favoriteList =  new FavoriteMovie().convertFavoriteMovieToMovie(MovieDatabase.getDatabase(getContext()).favoriteMovieDao().getAll());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("CountOfFavorite","CountOfFavorite = " + MovieDatabase.getDatabase(getContext()).favoriteMovieDao().countFavoriteMovie());
       if (favoriteList.size() < 1){
           getBinding().emptyLayout.setVisibility(View.VISIBLE);
       }else {
           movieAdapter = new MovieAdapter(getContext(), getBinding().mainRecycler, favoriteList);
           getBinding().mainRecycler.setAdapter(movieAdapter);
       }
    }
}
