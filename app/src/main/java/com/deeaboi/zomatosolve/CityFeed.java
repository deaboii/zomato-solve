package com.deeaboi.zomatosolve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityFeed
{

    @SerializedName("location_suggestions")
    @Expose
    private ArrayList<Location_suggestions>location_suggestions;

    public ArrayList<Location_suggestions> getLocation_suggestions() {
        return location_suggestions;
    }

    public void setLocation_suggestions(ArrayList<Location_suggestions> location_suggestions) {
        this.location_suggestions = location_suggestions;
    }

    @Override
    public String toString() {
        return "CityFeed{" +
                "location_suggestions=" + location_suggestions +
                '}';
    }
}
