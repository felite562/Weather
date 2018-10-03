package com.lu.weatherlu.weather;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lu.weatherlu.weather.data.model.Query;
import com.lu.weatherlu.weather.data.model.Weather;
import com.lu.weatherlu.weather.data.remote.WeatherAPI;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.textViewCity) TextView textViewCity;
    @BindView(R.id.textViewCond) TextView textViewCond;
    @BindView(R.id.textViewUpd) TextView textViewUpd;
    @BindView(R.id.textViewTemp) TextView textViewTemp;
    @BindView(R.id.textViewVis) TextView textViewVis;
    @BindView(R.id.textViewSpeed) TextView textViewSpeed;
    @BindView(R.id.imageView2)
    ImageView ImageView2;
    @BindView(R.id.button) Button button;
    @BindView(R.id.city) EditText city;
    @BindView(R.id.imageView) ImageView mImageView;
    public static String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);}
   @OnClick(R.id.button) public void onClick_button(){
       s = (city.getText() == null)? "" :city.getText().toString();
       WeatherAPI.Factory.getInstance().getWeather("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + s + "%22)%20and%20u%3D'c'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys").enqueue(new Callback<Weather>()
             {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Query query =response.body().getQuery();
                textViewTemp.setText(query.getResults().getChannel().getItem().getCondition().getTemp()+"°C");
                textViewCity.setText(query.getResults().getChannel().getLocation().getCity());

                int weatherIconImageResource = getResources().getIdentifier("icon_" + query.getResults().getChannel().getItem().getCondition().getCode(), "drawable", "com.lu.weatherlu.weather");
                mImageView.setImageResource(weatherIconImageResource);
                //textViewUpd.setText(query.getResults().getChannel().getLastBuildDate());
                textViewUpd.setText("humidity: "+query.getResults().getChannel().getAtmosphere().getHumidity()+"%");
                textViewVis.setText("visibility: "+query.getResults().getChannel().getAtmosphere().getVisibility()+" miles");
                textViewSpeed.setText("wind: "+query.getResults().getChannel().getWind().getSpeed()+" mph NW");
                textViewCond.setText(query.getResults().getChannel().getItem().getCondition().getText());


            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }


}
