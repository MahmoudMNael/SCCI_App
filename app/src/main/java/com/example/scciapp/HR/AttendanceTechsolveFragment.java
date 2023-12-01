package com.example.scciapp.HR;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scciapp.HttpClient;
import com.example.scciapp.Models.WeeklySession;
import com.example.scciapp.R;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttendanceTechsolveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceTechsolveFragment extends Fragment {
	
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	public AttendanceTechsolveFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment AttendanceTechsolveFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static AttendanceTechsolveFragment newInstance(String param1, String param2) {
		AttendanceTechsolveFragment fragment = new AttendanceTechsolveFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
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
		return inflater.inflate(R.layout.fragment_attendance_techsolve, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Genson genson = new GensonBuilder().create();
		TextView nothingTxt = (TextView) view.findViewById(R.id.nothingTxt);
		
		String url = HttpClient.baseUrl + "/hr/sessions/Techsolve";
		Request request = new Request.Builder().get().url(url).build();
		
		HttpClient.getClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						nothingTxt.setVisibility(View.VISIBLE);
					}
				});
			}
			
			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				ResponseBody responseBody = response.body();
				Log.d("Status Code", response.code() + "");
				if (response.isSuccessful() && responseBody != null && response.code() == 200) {
					Map<String, Object> decodedResponse = genson.deserialize(responseBody.string(), Map.class);
					ArrayList<Map<String, Object>> dataList = (ArrayList) decodedResponse.get("data");
					ArrayList<WeeklySession> sessions = WeeklySession.getWeeklySessionsList(dataList);
					
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							RecyclerView accountsRecView = (RecyclerView) view.findViewById(R.id.sessionsRecView);
							HRWeeklySessionsRecViewAdapter adapter = new HRWeeklySessionsRecViewAdapter(view.getContext());
							adapter.setSessions(sessions);
							accountsRecView.setAdapter(adapter);
							accountsRecView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
						}
					});
					
				} else {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							nothingTxt.setVisibility(View.VISIBLE);
						}
					});
				}
				response.close();
			}
		});
	}
}