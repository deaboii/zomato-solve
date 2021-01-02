package com.deeaboi.zomatosolve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    EditText search_bar;
    Button search_btn;
    TextView textView;

    private FusedLocationProviderClient mfusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;

    private double latitute;
    private double longitude;
    private  int c_id;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_bar = findViewById(R.id.search_bar);
        search_btn = findViewById(R.id.seacrh_btn);
        textView = findViewById(R.id.result);


          mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

           getLocation();

         //  getCity_id();
        // search_btn.setOnClickListener(v -> getLocation());
        //  search_btn.setOnClickListener(v -> getCity_id());

         search_btn.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v)
             {
                 if(search_bar.getText().toString().isEmpty())
                 {
                     Toast.makeText(MainActivity.this, "Please restaurant name ", Toast.LENGTH_SHORT).show();
                 }
                 else {

                     search();
                 }
             }
         });


    }

    private void allowPermission()
    {
        PermissionListener permissionlistener = new PermissionListener()
        {
            @Override
            public void onPermissionGranted()
            {
                //Toast.makeText(LandingActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();


                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    //when pemisson granted do stuff

                    googleMapPermission(); //cheack gps is on or not

                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions)
            {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                finishAndRemoveTask();

            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this app\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET)
                .check();



    }

    private void search()
    {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://developers.zomato.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZomatoApi zomatoApi = retrofit.create(ZomatoApi.class);

        Call<Feed> call = zomatoApi.getData(c_id,"city",search_bar.getText().toString(),1,20);

        call.enqueue(new Callback<Feed>()
        {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                ArrayList<Restaurants> restaurant_List = response.body().getRestaurants();

//                 for (int i=0;i<restaurant_List.size();i++)
//                 {
//
//                      String name=restaurant_List.get(i).getRestaurant().getName().toString();
//
//                      textView.setText(name);
//
//                 }


                for (Restaurants restaurant : restaurant_List) {
                    String content = "";
                    String phone = "";

                    content += "name:" + restaurant.getRestaurant().getName() + "\n";
                    content += "phone" + restaurant.getRestaurant().getPhone_numbers() + "\n\n";

                    textView.append(content);
                }


            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });



    }



    private void getCity_id()
    {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://developers.zomato.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZomatoCityID zomatoCityID = retrofit2.create(ZomatoCityID.class);

        Call<CityFeed> call = zomatoCityID.getCity(latitute,longitude);

        call.enqueue(new Callback<CityFeed>()
        {
            @Override
            public void onResponse(Call<CityFeed> call, Response<CityFeed> response2)
            {
                ArrayList<Location_suggestions>location_suggestions=response2.body().getLocation_suggestions();

                if(location_suggestions.size()==0)
                {

                 //   Toast.makeText(MainActivity.this, "Zomato is not available in your location", Toast.LENGTH_SHORT).show();

                    search_bar.setVisibility(View.GONE);
                    search_btn.setVisibility(View.GONE);

                    textView.setText("Zomato not available in your location");


                }
                else
                    {

                    for (Location_suggestions city_id : location_suggestions)
                    {
                       // String id = "";

                      //  id += "id:" + city_id.getId();

                        c_id=city_id.getId();


                       // Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CityFeed> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mfusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();

//                            if (mLastKnownLocation != null) {
//
//                                latitute =mLastKnownLocation.getLatitude();
//                                longitude = mLastKnownLocation.getLongitude();
//
//
//
//                                //Toast.makeText(MainActivity.this, latitute + "  " + "  " + longitude, Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                                {
                                LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                                locationCallback = new LocationCallback()
                                {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();

                                        latitute =mLastKnownLocation.getLatitude();
                                        longitude = mLastKnownLocation.getLongitude();

                                      //  Toast.makeText(MainActivity.this, latitute + "  " + "  " + longitude, Toast.LENGTH_SHORT).show();

                                        getCity_id();

                                      //  mfusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                mfusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
//                            }
                        }

                        else
                        {
                            Toast.makeText(MainActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    private void googleMapPermission()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(MainActivity.this).checkLocationSettings(builder.build());


        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>()
        {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task)
            {
                try
                {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.

                    getLocation();

                }
                catch (ApiException exception)
                {
                    switch (exception.getStatusCode())
                    {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().


                                resolvable.startResolutionForResult
                                        (
                                                MainActivity.this,
                                                REQUEST_CHECK_SETTINGS);


                            }
                            catch (IntentSender.SendIntentException e)
                            {
                                // Ignore the error.
                            }
                            catch (ClassCastException e)
                            {
                                // Ignore, should be an impossible error.
                            }
                            break;
                          case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            break;
                    }
                }

            }
        });


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to

                        break;
                    default:
                        break;
                }
                break;
        }

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        allowPermission();

    }
}