package com.lu.weatherlu.weather.data.remote;

import android.net.Uri;
import android.os.AsyncTask;


import com.lu.weatherlu.weather.MainActivity;
import com.lu.weatherlu.weather.data.model.Channel;
import com.lu.weatherlu.weather.data.model.Weather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Luba on 18.02.2018.
 */


public interface WeatherAPI {


       String BASE_URL = "https://query.yahooapis.com/v1/public/";

    @GET  public  Call<Weather> getWeather(@Url String url);
    class Factory {

        private static WeatherAPI service;

        public static WeatherAPI getInstance() {

            if (service == null) {

                Retrofit retrofit =
                        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                                .baseUrl(BASE_URL)
                                .build();



                service = retrofit.create(WeatherAPI.class);
                return service;
            } else {
                return service;
            }
        }
    }
}