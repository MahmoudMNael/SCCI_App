package com.example.scciapp;


import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AuthScreen extends AppCompatActivity {
	
	private EditText enteredEmail, enteredPassword;
	private ProgressBar progressCircular;
	private Button loginBtn;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth_screen);
		
		SharedPreferences sharedPreferences = getSharedPreferences("sharedCookies", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		
		enteredEmail = (EditText) findViewById(R.id.emailtextinput);
		enteredPassword = (EditText) findViewById(R.id.passwordtextinput);
		loginBtn = (Button) findViewById(R.id.loginbtn);
		progressCircular = (ProgressBar) findViewById(R.id.progresscircular);
		
		
		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Genson genson = new GensonBuilder().create();
				String url = "http://10.0.2.2:5000/api/auth/login";
				Map<String, String> userCredentials = new HashMap<String, String>(){{
					put("username", enteredEmail.getText().toString());
					put("password", enteredPassword.getText().toString());
				}};
				String json = genson.serialize(userCredentials);
				RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
				Request request = new Request.Builder().post(body).url(url).build();
				
				loginBtn.setVisibility(View.GONE);
				progressCircular.setVisibility(View.VISIBLE);
				HttpClient.client.newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e) {
						e.printStackTrace();
						AuthScreen.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loginBtn.setVisibility(View.VISIBLE);
								progressCircular.setVisibility(View.GONE);
							}
						});
						
					}
					
					@Override
					public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
						ResponseBody responseBody = response.body();
						
						if (response.isSuccessful() && responseBody != null) {
							Map<String, Object> decodedResponse = genson.deserialize(responseBody.string(), Map.class);
							User user = new User((Map<String, Object>) decodedResponse.get("data"));
							Session.currentUser = user;
							Log.d("Cookie", response.header("Set-Cookie"));
							Log.d("User", user.toString());
							Session.cookieString = response.header("Set-Cookie");
							editor.putString("cookie", Session.cookieString);
							editor.apply();
							
							Intent intent = new Intent(AuthScreen.this, HomeScreen.class);
							startActivity(intent);
							AuthScreen.this.finish();
						} else {
							AuthScreen.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									loginBtn.setVisibility(View.VISIBLE);
									progressCircular.setVisibility(View.GONE);
								}
							});
						}
					}
				});
			}
		});
		
	}
	
}