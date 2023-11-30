package com.example.scciapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.scciapp.Admin.AdminHomeActivity;
import com.example.scciapp.HR.HRHomeActivity;
import com.example.scciapp.Models.User;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LandingActivity extends AppCompatActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_screen);
		
		SharedPreferences sharedPreferences = getSharedPreferences("sharedCookies", MODE_PRIVATE);
		Session.cookieString = sharedPreferences.getString("cookie", "");
		
		Genson genson = new GensonBuilder().create();
		String url = HttpClient.baseUrl + "/auth/currentUser";
		Request request = new Request.Builder().url(url).addHeader("Cookie", Session.cookieString).build();
		HttpClient.getClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				e.printStackTrace();
				
			}
			
			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				ResponseBody responseBody = response.body();
				
				if (response.isSuccessful() && responseBody != null) {
					Map<String, Object> decodedResponse = genson.deserialize(responseBody.string(), Map.class);
					User user = new User((Map<String, Object>) decodedResponse.get("data"));
					Session.currentUser = user;
					Log.d("Status Code", response.code() + "");
					Intent intent;
					if (Session.currentUser.getUserType().equals("Admin")){
						intent = new Intent(LandingActivity.this, AdminHomeActivity.class);
					} else if (Session.currentUser.getUserType().equals("HR")){
						intent = new Intent(LandingActivity.this, HRHomeActivity.class);
					} else {
						intent = new Intent(LandingActivity.this, HomeActivity.class);
					}
					
					startActivity(intent);
					LandingActivity.this.finish();
				} else {
					Intent intent = new Intent(LandingActivity.this, AuthActivity.class);
					startActivity(intent);
					LandingActivity.this.finish();
				}
			}
		});
	}
}