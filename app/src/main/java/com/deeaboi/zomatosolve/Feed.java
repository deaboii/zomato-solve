package com.deeaboi.zomatosolve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Feed
{

    @SerializedName("restaurants")
    @Expose
    private ArrayList<Restaurants> restaurants;

    public ArrayList<Restaurants> getRestaurants()
    {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurants> restaurants)
    {
        this.restaurants = restaurants;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "restaurants=" + restaurants +
                '}';
    }
}
