package com.example.scciapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.scciapp.Admin.AdminHomeActivity;
import com.example.scciapp.HR.HRHomeActivity;
import com.example.scciapp.Models.User;
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

public class AuthActivity extends AppCompatActivity {
	
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
				String url = HttpClient.baseUrl + "/auth/login";
				Map<String, String> userCredentials = new HashMap<String, String>(){{
					put("username", enteredEmail.getText().toString());
					put("password", enteredPassword.getText().toString());
				}};
				String json = genson.serialize(userCredentials);
				RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
				Request request = new Request.Builder().post(body).url(url).build();
				
				loginBtn.setVisibility(View.GONE);
				progressCircular.setVisibility(View.VISIBLE);
				HttpClient.getClient().newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e) {
						e.printStackTrace();
						AuthActivity.this.runOnUiThread(new Runnable() {
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
							Intent intent;
							if (Session.currentUser.getUserType().equals("Admin")){
								intent = new Intent(AuthActivity.this, AdminHomeActivity.class);
							} else if (Session.currentUser.getUserType().equals("HR")){
								intent = new Intent(AuthActivity.this, HRHomeActivity.class);
							} else {
								intent = new Intent(AuthActivity.this, HomeActivity.class);
							}
							
							startActivity(intent);
							AuthActivity.this.finish();
						} else {
							AuthActivity.this.runOnUiThread(new Runnable() {
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