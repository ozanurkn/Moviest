package com.ozan.moviest.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ozan.moviest.R;
import com.ozan.moviest.adapter.MovieAdapter;
import com.ozan.moviest.adapter.ViewPagerAdapter;
import com.ozan.moviest.databinding.ActivityMainBinding;
import com.ozan.moviest.helper.BaseActivity;
import com.ozan.moviest.helper.Constant;
import com.ozan.moviest.helper.Controller;
import com.ozan.moviest.helper.Utils;
import com.ozan.moviest.model.Movie;
import com.ozan.moviest.model.response.SearchResponse;
import com.ozan.moviest.network.Api;
import com.ozan.moviest.network.ApiClient;
import com.ozan.moviest.ui.fragments.FavoritiesFragment;
import com.ozan.moviest.ui.fragments.NowPlayingFragment;
import com.ozan.moviest.ui.fragments.TopRatedFragment;
import com.ozan.moviest.ui.fragments.UpComingFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    public static final String TAG = MainActivity.class.getSimpleName();
    public TopRatedFragment topRatedFragment;
    public NowPlayingFragment nowPlayingFragment;
    public UpComingFragment upComingFragment;
    public FavoritiesFragment favoritiesFragment;
    Call<SearchResponse> searchCall;
    List<Movie> searchList;
    @Override
    protected Context getContext() {
        return MainActivity.this;
    }

    @Override
    protected void initView() {


        setUpViewPager();
        setSupportActionBar(getBinding().toolbar);
        setUpSearchView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 4, getContext());
        getBinding().viewpager.setAdapter(viewPagerAdapter);
        getBinding().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                getBinding().searchView.closeSearch();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        getBinding().tabs.setTabTextColors(
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.colorAccent)
        );
        getBinding().tabs.setupWithViewPager(getBinding().viewpager);

        setUpTabviewFont();
    }

    private void setUpTabviewFont() {
        getBinding().tabs.newTab().setText(getContext().getString(R.string.first_tab));
        getBinding().tabs.newTab().setText(getContext().getString(R.string.second_tab));
        getBinding().tabs.newTab().setText(getContext().getString(R.string.third_tab));
        getBinding().tabs.newTab().setText(getResources().getString(R.string.forth_tab));
        getBinding().tabs.setTabGravity(TabLayout.GRAVITY_CENTER);
        //Utils.setCustomFont(getContext(),getBinding().tabs);
    }

    private void setUpSearchView() {
        getBinding().searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchService(query);
                Utils.hideKeyboard(MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        getBinding().searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                setUpViewPager();
                getBinding().searchingPage.setVisibility(View.GONE);
                getBinding().mainContainer.setVisibility(View.VISIBLE);
            }

        });
    }

    public void searchService(String query){
        getBinding().loadingLayout.setVisibility(View.VISIBLE);
        if (searchCall != null && searchCall.isCanceled()) {
            try {
                searchCall.cancel();
            } catch (Exception er) {
                er.getMessage();
                Log.i(TAG, er.getMessage());
            }
            searchCall = null;
        }
        searchCall = ApiClient.getClient().create(Api.class).search(Constant.API_KEY, Controller.deviceLanguage,query,1);

        searchCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                getBinding().loadingLayout.setVisibility(View.GONE);
                searchCall = null;
                try {
                    if (response.code() == 200) {
                        getBinding().searchingPage.setVisibility(View.VISIBLE);
                        getBinding().mainContainer.setVisibility(View.GONE);
                        searchList = response.body().getResults();
                        MovieAdapter movieAdapter = new MovieAdapter(getContext(), getBinding().recySearch, searchList);
                        getBinding().recySearch.setAdapter(movieAdapter);
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                getBinding().loadingLayout.setVisibility(View.GONE);
                if (searchCall.isCanceled()) {
                    Log.e(TAG, "request was cancelled");
                } else {
                    Log.e(TAG, "other larger issue, i.e. no network connection?");
                }
                searchCall = null;
                t.printStackTrace();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        getBinding().searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        boolean founded = false;
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if( getSupportFragmentManager().findFragmentByTag(getString(R.string.noConnectionStr)) != null)
                founded = true;

            if (founded)
                finishAffinity();
            else
                getSupportFragmentManager().popBackStackImmediate();
        } else {
            if (getBinding().searchView.isSearchOpen()) {
                getBinding().searchView.closeSearch();
                getBinding().searchingPage.setVisibility(View.GONE);
                getBinding().mainContainer.setVisibility(View.VISIBLE);
            } else {
                if (getBinding().viewpager.getCurrentItem() != 0) {
                    getBinding().viewpager.setCurrentItem(0);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Dikkat");
                    builder.setMessage("Uygulamadan çıkmak istediğinize emin misiniz?");
                    builder.setNegativeButton("Hayir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    builder.show();

                }
            }
        }
    }
}
