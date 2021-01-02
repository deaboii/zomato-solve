package com.deeaboi.zomatosolve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location_suggestions
{

    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Location_suggestions{" +
                "id=" + id +
                '}';
    }
}
