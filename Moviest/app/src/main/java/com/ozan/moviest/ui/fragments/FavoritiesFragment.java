package com.ozan.moviest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ozan.moviest.R;
import com.ozan.moviest.databinding.FragmentFavoritiesBinding;
import com.ozan.moviest.helper.BaseFragment;
import com.ozan.moviest.model.MovieDetail;

import java.util.ArrayList;
import java.util.List;

public class FavoritiesFragment extends BaseFragment<FragmentFavoritiesBinding> {

    public static FavoritiesFragment newInstance(){
        Bundle args = new Bundle();
        FavoritiesFragment fragment = new FavoritiesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FavoritiesFragment(){

    }

    List<MovieDetail> favoriteList;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorities;
    }

    @Override
    public void initView() {

        favoriteList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /*favoriteList =  MovieDatabase.getDatabase(getContext()).favoriteMovieDao().getAllFavoriteMovie();
       if (favoriteList.size() < 1){
           getBinding().emptyLayout.setVisibility(View.VISIBLE);
       }*/
    }


}
