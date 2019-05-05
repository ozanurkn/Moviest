package com.ozan.moviest.model;

import com.google.gson.annotations.SerializedName;

public class EventData {

    @SerializedName("dataTypes")
    private DataTypes dataTypes;
    @SerializedName("data")
    private Object data;

    public EventData(DataTypes onQueryTextSubmit, String query) {
        this.dataTypes = onQueryTextSubmit;
        this.data = query;
    }

    public EventData(DataTypes onMovieClicked, Movie movie) {
        this.dataTypes = onMovieClicked;
        this.data = movie;
    }

    public DataTypes getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(DataTypes dataTypes) {
        this.dataTypes = dataTypes;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
