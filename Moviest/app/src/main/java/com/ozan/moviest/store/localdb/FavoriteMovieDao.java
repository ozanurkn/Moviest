package com.ozan.moviest.store.localdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM fav_movie")
    List<FavoriteMovie> getAll();

    @Query("SELECT COUNT(*) from fav_movie")
    int countFavoriteMovie();

    @Insert
    void addFavoriteMovie(FavoriteMovie... favoriteMovies);

    @Delete
    void removeFavoriteMovie(FavoriteMovie favoriteMovies);

    @Query("SELECT title FROM fav_movie")
    List<String> getAllTitle();

    @Query("DELETE FROM fav_movie WHERE movie_id = :movie_id")
    void removeById(int movie_id);

   /* @Query("DELETE FROM fav_movie WHERE movie_id=:movie_id")
    boolean removeById(int movie_id);

    @Query("SELECT * FROM fav_movie WHERE movie_id = movie_id")
    FavoriteMovie getMovieByMovieId(int movie_id);*/
}
