package com.ozan.moviest.store.localdb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.ozan.moviest.model.Movie;
import com.ozan.moviest.model.MovieDetail;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "fav_movie")
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "movie_id")
    private int movieId;
    @ColumnInfo(name = "vote_count")
    private int voteCount;
    @ColumnInfo(name = "vote_average")
    private double voteAverage;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "popularity")
    private Double popularity;
    @ColumnInfo(name = "poster_path")
    private String posterpath;
    @ColumnInfo(name = "original_language")
    private String originalLanguage;
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isContainMovie(List<FavoriteMovie> list, int movieId) {
        boolean isContain = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMovieId() == movieId) {
                isContain = true;
            } else {
                isContain = false;
            }
        }
        return isContain;
    }

    public FavoriteMovie convertMovieDetailToFavMovie(MovieDetail movieDetail) {

        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setMovieId(movieDetail.getId());
        favoriteMovie.setVoteCount(movieDetail.getVoteCount());
        favoriteMovie.setVoteAverage(movieDetail.getVoteAverage());
        favoriteMovie.setTitle(movieDetail.getTitle());
        favoriteMovie.setPopularity(movieDetail.getPopularity());
        favoriteMovie.setPosterpath(movieDetail.getPosterPath());
        favoriteMovie.setOriginalLanguage(movieDetail.getOriginalLanguage());
        favoriteMovie.setOriginalTitle(movieDetail.getOriginalTitle());
        favoriteMovie.setBackdropPath(movieDetail.getBackdropPath());
        favoriteMovie.setOverview(movieDetail.getOverview());
        favoriteMovie.setReleaseDate(movieDetail.getReleaseDate());
        
        return favoriteMovie;
    }

    public List<Movie> convertFavoriteMovieToMovie(List<FavoriteMovie> favoriteMovieList) {

        List<Movie> list = new ArrayList<>();
        for (int i = 0;i<favoriteMovieList.size();i++){
            Movie movie = new Movie();
            movie.setId(favoriteMovieList.get(i).getMovieId());
            movie.setVoteCount(favoriteMovieList.get(i).getVoteCount());
            movie.setVoteAverage(favoriteMovieList.get(i).getVoteAverage());
            movie.setTitle(favoriteMovieList.get(i).getTitle());
            movie.setPopularity(favoriteMovieList.get(i).getPopularity());
            movie.setPosterPath(favoriteMovieList.get(i).getPosterpath());
            movie.setOriginalLanguage(favoriteMovieList.get(i).getOriginalLanguage());
            movie.setOriginalTitle(favoriteMovieList.get(i).getOriginalTitle());
            movie.setBackdropPath(favoriteMovieList.get(i).getBackdropPath());
            movie.setOverview(favoriteMovieList.get(i).getOverview());
            movie.setReleaseDate(favoriteMovieList.get(i).getReleaseDate());
            list.add(movie);
        }
        

        return list;
    }
}
