package com.ozan.moviest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ozan.moviest.R;
import com.ozan.moviest.adapter.MovieAdapter;
import com.ozan.moviest.databinding.FragmentNowPlayingBinding;
import com.ozan.moviest.helper.BaseFragment;
import com.ozan.moviest.helper.Constant;
import com.ozan.moviest.helper.Controller;
import com.ozan.moviest.model.Movie;
import com.ozan.moviest.model.response.UpComingAndNPResponse;
import com.ozan.moviest.network.Api;
import com.ozan.moviest.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends BaseFragment<FragmentNowPlayingBinding> {
    public static final String TAG = NowPlayingFragment.class.getSimpleName();
    int pageCount = 1;
    int totalPageSize = 0;
    Call<UpComingAndNPResponse> nowPlayingCall;
    List<Movie> nowPlayingList;
    MovieAdapter movieAdapter;
    boolean loading = false;

    private int lastVisibleItem = 0;
    private int totalItemCount = 0;
    LinearLayoutManager linearLayoutManager;

    public static NowPlayingFragment newInstance() {

        Bundle args = new Bundle();

        NowPlayingFragment fragment = new NowPlayingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NowPlayingFragment() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_now_playing;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null)
            pageCount = 1;
        nowPlayingService();
        getBinding().mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (loading)
                    return;
                if (nowPlayingList.size() == 0)
                    return;
                linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (lastVisibleItem == totalItemCount){
                    pageCount ++;
                    nowPlayingService();
                }
            }
        });
    }

    //region TopRatedService
    public void nowPlayingService() {
        getBinding().loadingLayout.setVisibility(View.VISIBLE);
        if (nowPlayingCall != null && nowPlayingCall.isCanceled()) {
            try {
                nowPlayingCall.cancel();
            } catch (Exception er) {
                er.getMessage();
                Log.i(TAG, er.getMessage());
            }
            nowPlayingCall = null;
        }
        nowPlayingCall = ApiClient.getClient().create(Api.class).getNowplayingList(Constant.API_KEY, Controller.deviceLanguage, pageCount);
        loading = true;
        nowPlayingCall.enqueue(new Callback<UpComingAndNPResponse>() {
            @Override
            public void onResponse(Call<UpComingAndNPResponse> call, Response<UpComingAndNPResponse> response) {
                nowPlayingCall = null;
                loading = false;
                getBinding().loadingLayout.setVisibility(View.GONE);
                try {
                    if (response.code() == 200) {
                        nowPlayingList = response.body().getResults();
                        totalPageSize = response.body().getPage();
                        if (pageCount == 1){
                            movieAdapter = new MovieAdapter(getContext(), getBinding().mainRecycler, nowPlayingList);
                            getBinding().mainRecycler.setAdapter(movieAdapter);
                        }else {
                            movieAdapter.notifyData(nowPlayingList);
                        }
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpComingAndNPResponse> call, Throwable t) {
                getBinding().loadingLayout.setVisibility(View.GONE);
                if (nowPlayingCall.isCanceled()) {
                    Log.e(TAG, "request was cancelled");
                } else {
                    Log.e(TAG, "other larger issue, i.e. no network connection?");
                }
                nowPlayingCall = null;
                t.printStackTrace();
            }
        });
    }
}
