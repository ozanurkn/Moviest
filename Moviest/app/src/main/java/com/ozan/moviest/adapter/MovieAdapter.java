package com.ozan.moviest.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ozan.moviest.R;
import com.ozan.moviest.model.Movie;
import com.ozan.moviest.ui.activities.MainActivity;
import com.ozan.moviest.ui.fragments.MovieDetailFragment;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList;
    private RecyclerView recyclerView;

    public MovieAdapter(Context context, RecyclerView recyclerView, List<Movie> list) {

        this.mContext = context;
        this.movieList = list;
        this.recyclerView = recyclerView;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView image;
        AVLoadingIndicatorView avl_avatar;
        TextView title, txt_reDate, txt_desc, txt_rate, txt_vote;
        ConstraintLayout movie_main_row;


        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            avl_avatar = itemView.findViewById(R.id.avl_avatar);
            title = itemView.findViewById(R.id.title);
            txt_desc = itemView.findViewById(R.id.overView);
            txt_reDate = itemView.findViewById(R.id.releaseDate);
            txt_rate = itemView.findViewById(R.id.rateText);
            txt_vote = itemView.findViewById(R.id.voteText);
            movie_main_row = itemView.findViewById(R.id.movie_main_row);

        }

    }

    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        MovieAdapter.MyViewHolder viewHolder = new MovieAdapter.MyViewHolder(itemView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MovieAdapter.MyViewHolder holder, final int position) {

        Glide.with(mContext)
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .load("https://image.tmdb.org/t/p/w342/" + movieList.get(position).getPosterPath())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.avl_avatar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.avl_avatar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.image);
        holder.title.setText(movieList.get(position).getTitle());
        holder.txt_vote.setText(String.valueOf(movieList.get(position).getVoteCount()));
        holder.txt_reDate.setText(movieList.get(position).getReleaseDate());
        holder.txt_desc.setText(movieList.get(position).getOverview());
        holder.txt_rate.setText(String.valueOf(movieList.get(position).getVoteAverage()));

        PushDownAnim.setPushDownAnimTo(holder.movie_main_row).setScale(PushDownAnim.MODE_STATIC_DP, 5F).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bundle bundle = new Bundle();
                        bundle.putInt("movie_id", movieList.get(position).getId());

                        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                        movieDetailFragment.setArguments(bundle);
                        ((MainActivity)mContext).getBinding().searchingPage.setVisibility(View.GONE);
                        ((MainActivity)mContext).getBinding().mainContainer.setVisibility(View.VISIBLE);
                        ((MainActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,movieDetailFragment).addToBackStack(String.valueOf(R.string.movieDetail)).commit();
                    }
                });
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void notifyData(List<Movie> list) {
        Log.d("notifyData ", list.size() + "");
        this.movieList.addAll(list);
        notifyDataSetChanged();
    }
}