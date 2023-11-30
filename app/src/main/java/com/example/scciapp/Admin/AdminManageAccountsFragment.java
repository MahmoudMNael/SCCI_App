package com.example.scciapp.Admin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.scciapp.HttpClient;
import com.example.scciapp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

//import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminManageAccountsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminManageAccountsFragment extends Fragment {
	
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	public AdminManageAccountsFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment AdminManageAccountsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static AdminManageAccountsFragment newInstance(String param1, String param2) {
		AdminManageAccountsFragment fragment = new AdminManageAccountsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	
	private TabLayout tabLayout;
	private ViewPager2 viewPager2;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_admin_manage_accounts, container, false);
	}
	
	int requestcode = 1;
	
	
	Uri fileUri;
	
	private String readTextFromUri(Uri uri) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try (InputStream inputStream =
			     getActivity().getContentResolver().openInputStream(uri);
		     BufferedReader reader = new BufferedReader(
			     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		return stringBuilder.toString();
	}
	
	
	ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
		new ActivityResultContracts.StartActivityForResult(),
		new ActivityResultCallback<ActivityResult>() {
			@Override
			public void onActivityResult(ActivityResult result) {
				if (result.getResultCode() == Activity.RESULT_OK) {
					// There are no request codes
					Intent data = result.getData();
					fileUri = data.getData();
					String url = "http://10.0.2.2:5000/api/admin/account/create";
					
					try {
						String json = readTextFromUri(fileUri);
						
						RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
						Request request = new Request.Builder().post(body).url(url).build();
						
						HttpClient.getClient().newCall(request).enqueue(new Callback() {
							@Override
							public void onFailure(@NonNull Call call, @NonNull IOException e) {
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
									}
								});
								e.printStackTrace();
							}
							
							@Override
							public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
								ResponseBody responseBody = response.body();
								
								if (response.isSuccessful() && responseBody != null) {
									Log.d("Status Code", response.code() + "");
									
									getActivity().runOnUiThread(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(getContext(), "File submitted successfully!", Toast.LENGTH_SHORT).show();
										}
									});
								} else {
									
									getActivity().runOnUiThread(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(getContext(), "File failed to submit!", Toast.LENGTH_SHORT).show();
										}
									});
									Log.d("Status Code", response.code() + "");
								}
							}
						});
						
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
		viewPager2 = (ViewPager2) view.findViewById(R.id.viewpager2);
		FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingactionbtn);
		
		floatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View view1 = LayoutInflater.from(getContext()).inflate(R.layout.upload_file_dialog_layout, null);
				Button uploadBtn = view1.findViewById(R.id.uploadbtn);
				
				
				AlertDialog alertDialog = new MaterialAlertDialogBuilder(getContext())
					.setTitle("Upload Users' File")
					.setView(view1)
					.setNegativeButton("Close", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.create();
				alertDialog.show();
				uploadBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//Create Intent
						Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
						intent.setType("*/*");
						intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
						//Launch activity to get result
						someActivityResultLauncher.launch(intent);
						alertDialog.dismiss();
					}
				});
			}
		});
		
		
		FragmentManager fragmentManager = getParentFragmentManager();
		AccountTypesViewPagerAdapter vpAdapter = new AccountTypesViewPagerAdapter(fragmentManager, getLifecycle());
		viewPager2.setAdapter(vpAdapter);
		
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager2.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			
			}
		});
		
		viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageSelected(int position) {
				tabLayout.selectTab(tabLayout.getTabAt(position));
				super.onPageSelected(position);
			}
		});
		
	}
}