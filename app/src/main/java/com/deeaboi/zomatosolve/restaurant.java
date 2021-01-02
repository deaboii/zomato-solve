package com.deeaboi.zomatosolve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class restaurant
{
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone_numbers")
    @Expose
    private String phone_numbers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(String phone_numbers) {
        this.phone_numbers = phone_numbers;
    }

    @Override
    public String toString()
    {
        return "restaurant{" +
                "name='" + name + '\'' +
                ", phone_numbers='" + phone_numbers + '\'' +
                '}';
    }
}

