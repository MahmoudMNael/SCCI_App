package com.example.scciapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LandingScreen extends AppCompatActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_screen);
		
		SharedPreferences sharedPreferences = getSharedPreferences("sharedCookies", MODE_PRIVATE);
		Session.cookieString = sharedPreferences.getString("cookie", "");
		
		Genson genson = new GensonBuilder().create();
		String url = "http://10.0.2.2:5000/api/auth/isLoggedIn";
		Request request = new Request.Builder().url(url).addHeader("Cookie", Session.cookieString).build();
		HttpClient.client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				e.printStackTrace();
				
			}
			
			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				ResponseBody responseBody = response.body();
				
				if (response.isSuccessful() && responseBody != null) {
					Log.d("Status Code", response.code() + "");
					Intent intent = new Intent(LandingScreen.this, HomeScreen.class);
					startActivity(intent);
					LandingScreen.this.finish();
				} else {
					Intent intent = new Intent(LandingScreen.this, AuthScreen.class);
					startActivity(intent);
					LandingScreen.this.finish();
				}
			}
		});
	}
}