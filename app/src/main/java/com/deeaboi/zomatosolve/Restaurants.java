package com.deeaboi.zomatosolve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  Restaurants
{

     @SerializedName("restaurant")
     @Expose
     private restaurant restaurant;

     public restaurant getRestaurant()
     {
          return restaurant;
     }

     public void setRestaurant(restaurant restaurant)
     {
          this.restaurant = restaurant;
     }

     @Override
     public String toString()
     {
          return "Restaurants{" +
                  "restaurant=" + restaurant +
                  '}';
     }
}
