package com.ozan.moviest.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ozan.moviest.R;
import com.ozan.moviest.adapter.MovieAdapter;
import com.ozan.moviest.databinding.FragmentTopRatedBinding;
import com.ozan.moviest.helper.BaseFragment;
import com.ozan.moviest.helper.Constant;
import com.ozan.moviest.helper.Controller;
import com.ozan.moviest.model.Movie;
import com.ozan.moviest.model.response.TopRatedResponse;
import com.ozan.moviest.network.Api;
import com.ozan.moviest.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedFragment extends BaseFragment<FragmentTopRatedBinding> {

    public static final String TAG = TopRatedFragment.class.getSimpleName();
    int pageCount = 1;
    int searchPageCount = 1;
    int searchTotalPageSize = 0;
    int totalPageSize = 0;
    String searchQery = "";
    private boolean searchProcess = false;
    Call<TopRatedResponse> topRatedCall;
    List<Movie> topRatedMovieList;
    MovieAdapter movieAdapter;
    boolean loading = false;
    private int lastVisibleItem = 0;
    private int totalItemCount = 0;
    LinearLayoutManager linearLayoutManager;

    public TopRatedFragment() {
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_top_rated;
    }

    @Override
    public void initView() {
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null)
            pageCount = 1;
        topRatedService();
        initRecyclerListener();
    }

    //region TopRatedService
    public void topRatedService() {
        getBinding().loadingLayout.setVisibility(View.VISIBLE);
        if (topRatedCall != null && topRatedCall.isCanceled()) {
            try {
                topRatedCall.cancel();

            } catch (Exception er) {
                er.getMessage();
                Log.i(TAG, er.getMessage());
            }
            topRatedCall = null;
        }
        Api topRatedApi = ApiClient.getClient().create(Api.class);
        topRatedCall = topRatedApi.getTopRatedList(Constant.API_KEY, Controller.deviceLanguage, pageCount);
        loading = true;
        topRatedCall.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                topRatedCall = null;
                loading = false;
                getBinding().loadingLayout.setVisibility(View.GONE);
                try {
                    if (response.code() == 200) {
                        topRatedMovieList = response.body().getResults();
                        totalPageSize = response.body().getPage();
                        if (pageCount == 1) {
                            movieAdapter = new MovieAdapter(getContext(), getBinding().mainRecycler, topRatedMovieList);
                            getBinding().mainRecycler.setAdapter(movieAdapter);
                        } else {
                            movieAdapter.notifyData(topRatedMovieList);
                        }
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                getBinding().loadingLayout.setVisibility(View.GONE);
                if (topRatedCall.isCanceled()) {
                    Log.e(TAG, "request was cancelled");
                } else {
                    Log.e(TAG, "other larger issue, i.e. no network connection?");
                }
                topRatedCall = null;
                t.printStackTrace();
            }
        });
    }

    //endregion

    //region initRecyclerListener
    public void initRecyclerListener() {
        getBinding().mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (loading)
                    return;
                if (topRatedMovieList.size() == 0)
                    return;
                linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition() + 1;
                if (lastVisibleItem == totalItemCount) {
                    pageCount++;
                    topRatedService();
                }

            }
        });
    }
    //endregion
}
