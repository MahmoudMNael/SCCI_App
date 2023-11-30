package com.example.scciapp.HR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.scciapp.HttpClient;
import com.example.scciapp.Models.Participant;
import com.example.scciapp.Models.WeeklySession;
import com.example.scciapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HRAddOrEditSessionActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hradd_or_edit_session);
		
		MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Add New Session");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle extras = getIntent().getExtras();
		Integer sessionWorkshopNum = extras.getInt("sessionWorkshop");
		String sessionWorkshop = null;
		switch (sessionWorkshopNum){
			case 0:
				sessionWorkshop = "Appsplash";
				break;
			case 1:
				sessionWorkshop = "Devology";
				break;
			case 2:
				sessionWorkshop = "Markative";
				break;
			case 3:
				sessionWorkshop = "Investeneur";
				break;
			case 4:
				sessionWorkshop = "Techsolve";
				break;
		}
		
		Genson genson = new GensonBuilder().create();
		
		String url = HttpClient.baseUrl + "/hr/" + sessionWorkshop + "/participants";
		Request request = new Request.Builder().get().url(url).build();
		
		RecyclerView participantsRecView = (RecyclerView) findViewById(R.id.participantsRecView);
		Button submitBtn = (Button) findViewById(R.id.submitBtn);
		HRParticipantsRecViewAdapter adapter = new HRParticipantsRecViewAdapter(HRAddOrEditSessionActivity.this, false);
		HttpClient.getClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
			
			}
			
			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				ResponseBody responseBody = response.body();
				Log.d("Status Code", response.code() + "");
				if (response.isSuccessful() && responseBody != null && response.code() == 200) {
					Map<String, Object> decodedResponse = genson.deserialize(responseBody.string(), Map.class);
					ArrayList<Map<String, Object>> dataList = (ArrayList) decodedResponse.get("data");
					ArrayList<Participant> participants = Participant.getParticipantsList(dataList);
					
					HRAddOrEditSessionActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.setParticipants(participants);
							participantsRecView.setAdapter(adapter);
							participantsRecView.setLayoutManager(new LinearLayoutManager(HRAddOrEditSessionActivity.this, RecyclerView.VERTICAL, false));
						}
					});
				}
				response.close();
			}
		});
		
		RadioButton radioTechnical = (RadioButton) findViewById(R.id.radioTechnical);
		String finalSessionWorkshop = sessionWorkshop;
		submitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = HttpClient.baseUrl + "/hr/sessions/" + finalSessionWorkshop;
				Map<String, Object> data = new HashMap<String, Object>(){{
					if(radioTechnical.isChecked()){
						put("sessionType", "Technical");
					} else {
						put("sessionType", "Softskills");
					}
					put("participants", adapter.getParticipants());
				}};
				String json = genson.serialize(data);
				RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
				Request request = new Request.Builder().post(body).url(url).build();
				HttpClient.getClient().newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(@NonNull Call call, @NonNull IOException e) {
					
					}
					
					@Override
					public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
						ResponseBody responseBody = response.body();
						Log.d("Status Code", response.code() + "");
						if (response.isSuccessful() && responseBody != null && response.code() == 200) {
							Log.d("body", responseBody.string());
						}
						response.close();
						HRAddOrEditSessionActivity.this.finish();
					}
				});
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}