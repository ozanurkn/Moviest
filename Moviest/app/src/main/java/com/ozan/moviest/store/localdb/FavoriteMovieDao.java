package com.ozan.moviest.store.localdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ozan.moviest.model.MovieDetail;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movie")
    List<MovieDetail> getAll();

    @Query("SELECT COUNT(*) from favorite_movie")
    int countFavoriteMovie();

    @Insert
    void addFavoriteMovie(FavoriteMovie... favoriteMovies);

    @Delete
    void removeFavoriteMovie(FavoriteMovie favoriteMovie);

 /* @Query("SELECT * FROM favorite_movie where first_name LIKE  :firstName AND last_name LIKE :lastName")
    User findByName(String firstName, String lastName);*/
}
