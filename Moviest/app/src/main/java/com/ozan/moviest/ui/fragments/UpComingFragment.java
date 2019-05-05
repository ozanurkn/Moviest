package com.ozan.moviest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ozan.moviest.R;
import com.ozan.moviest.adapter.MovieAdapter;
import com.ozan.moviest.databinding.FragmentUpComingBinding;
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

public class UpComingFragment extends BaseFragment<FragmentUpComingBinding>{

    public static final String TAG = UpComingFragment.class.getSimpleName();

    int pageCount = 1;
    int searchPageCount = 1;
    int searchTotalPageSize = 0;
    int totalPageSize = 0;
    String searchQery = "";
    private boolean searchProcess = false;
    Call<UpComingAndNPResponse> upComingCall;
    List<Movie> upComingList;
    MovieAdapter movieAdapter;
    boolean loading = false;

    private int lastVisibleItem = 0;
    private int totalItemCount = 0;
    LinearLayoutManager linearLayoutManager;

    public UpComingFragment(){

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_up_coming;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null)
            pageCount = 1;
        upComingService();

        getBinding().mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (loading)
                    return;
                if (upComingList.size() == 0)
                    return;
                linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (lastVisibleItem == totalItemCount){
                    pageCount ++;
                    upComingService();
                }
            }
        });
    }
    //region TopRatedService
    public void upComingService() {
        getBinding().loadingLayout.setVisibility(View.VISIBLE);
        if (upComingCall != null && upComingCall.isCanceled()) {
            try {
                upComingCall.cancel();
            } catch (Exception er) {
                er.getMessage();
                Log.i(TAG, er.getMessage());
            }
            upComingCall = null;
        }
        upComingCall = ApiClient.getClient().create(Api.class).getUpcomingList(Constant.API_KEY, Controller.deviceLanguage, pageCount);
        loading = true;
        upComingCall.enqueue(new Callback<UpComingAndNPResponse>() {
            @Override
            public void onResponse(Call<UpComingAndNPResponse> call, Response<UpComingAndNPResponse> response) {
                upComingCall = null;
                loading = false;
                getBinding().loadingLayout.setVisibility(View.GONE);
                try {
                    if (response.code() == 200) {
                        upComingList = response.body().getResults();
                        totalPageSize = response.body().getPage();
                        if (pageCount == 1){
                            movieAdapter = new MovieAdapter(getContext(), getBinding().mainRecycler, upComingList);
                            getBinding().mainRecycler.setAdapter(movieAdapter);
                        }else {
                            movieAdapter.notifyData(upComingList);
                        }
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpComingAndNPResponse> call, Throwable t) {
                getBinding().loadingLayout.setVisibility(View.GONE);
                if (upComingCall.isCanceled()) {
                    Log.e(TAG, "request was cancelled");
                } else {
                    Log.e(TAG, "other larger issue, i.e. no network connection?");
                }
                upComingCall = null;
                t.printStackTrace();
            }
        });
    }
}
