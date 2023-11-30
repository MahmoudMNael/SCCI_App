package com.example.scciapp.HR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.scciapp.HttpClient;
import com.example.scciapp.Models.Participant;
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

public class HRViewSessionActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hrview_session);
		
		MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle extras = getIntent().getExtras();
		Long sessionID = extras.getLong("sessionID");
		
		Genson genson = new GensonBuilder().create();
		
		String url = HttpClient.baseUrl + "/hr/session/" + sessionID;
		Request request = new Request.Builder().get().url(url).build();
		
		RecyclerView participantsRecView = (RecyclerView) findViewById(R.id.participantsRecView);
		HRParticipantsRecViewAdapter adapter = new HRParticipantsRecViewAdapter(HRViewSessionActivity.this, true);
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
					Log.d("decoded", dataList.toString());
					ArrayList<Participant> participants = Participant.getParticipantsList(dataList);
					
					HRViewSessionActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							getSupportActionBar().setTitle((String) dataList.get(0).get("userWorkshop"));
							getSupportActionBar().setSubtitle((String) dataList.get(0).get("sessionType") + " " + dataList.get(0).get("sessionDate"));
							adapter.setParticipants(participants);
							participantsRecView.setAdapter(adapter);
							participantsRecView.setLayoutManager(new LinearLayoutManager(HRViewSessionActivity.this, RecyclerView.VERTICAL, false));
						}
					});
				}
				response.close();
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