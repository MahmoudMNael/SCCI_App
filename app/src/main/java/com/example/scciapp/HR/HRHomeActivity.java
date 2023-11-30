package com.example.scciapp.HR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.scciapp.Admin.AdminAnnouncementsFragment;
import com.example.scciapp.Admin.AdminManageAccountsFragment;
import com.example.scciapp.AuthActivity;
import com.example.scciapp.HttpClient;
import com.example.scciapp.R;
import com.example.scciapp.Session;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HRHomeActivity extends AppCompatActivity {
	private Boolean isPaused = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hrhome);
		
		MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
		bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				if (item.getItemId() == R.id.attendance) {
					getSupportFragmentManager()
						.beginTransaction()
						.setReorderingAllowed(true)
						.replace(R.id.fragmentContainerView, HRAttendanceFragment.class, null)
						.commit();
				} else if (item.getItemId() == R.id.announcements) {
					getSupportFragmentManager()
						.beginTransaction()
						.setReorderingAllowed(true)
						.replace(R.id.fragmentContainerView, HRAnnouncementsFragment.class, null)
						.commit();
				}
				
				return true;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater ().inflate ( R.menu.hr_toolbar_menu,menu );
		return super.onCreateOptionsMenu ( menu );
		
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int itemID = item.getItemId();
		if (itemID == R.id.logoutBtn){
			String url = HttpClient.baseUrl + "/auth/logout";
			RequestBody body = RequestBody.create("", MediaType.parse("application/json; charset=utf-8"));
			Request request = new Request.Builder().post(body).url(url).build();
			HttpClient.getClient().newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(@NonNull Call call, @NonNull IOException e) {
				
				}
				
				@Override
				public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
					ResponseBody responseBody = response.body();
					if (response.isSuccessful() && response.code() == 200 && responseBody != null) {
						SharedPreferences sharedPreferences = getSharedPreferences("sharedCookies", MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						Log.d("Logout", "Logged Out");
						editor.putString("cookie", "");
						editor.apply();
						Session.cookieString = "";
						Intent intent = new Intent(HRHomeActivity.this, AuthActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(intent);
					}
				}
			});
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		isPaused = true;
	}
		@Override
	protected void onResume() {
		super.onResume();
		if (isPaused){
			HRHomeActivity.this.recreate();
			isPaused = false;
		}
	}
}