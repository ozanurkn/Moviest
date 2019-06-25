package com.ozan.moviest.store.localdb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "favorite_movie")
public class FavoriteMovie {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "vote_count")
    private Integer voteCount;

    @ColumnInfo(name = "isvideo")
    private Boolean video;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "popularity")
    private Double popularity;

    @ColumnInfo(name = "posterpath")
    private String posterPath;

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @ColumnInfo(name = "genre_ids")
    private List<Integer> genreIds;

    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "adult")
    private Boolean adult;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "releaseD")
    private String releaseDate;

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
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

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
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
}
