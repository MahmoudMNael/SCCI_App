package com.example.scciapp.HR;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scciapp.Admin.AccountTypesViewPagerAdapter;
import com.example.scciapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HRAttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HRAttendanceFragment extends Fragment {
	
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	public HRAttendanceFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment HRAttendanceFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static HRAttendanceFragment newInstance(String param1, String param2) {
		HRAttendanceFragment fragment = new HRAttendanceFragment();
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
		return inflater.inflate(R.layout.fragment_h_r_attendance, container, false);
	}
	
	private TabLayout tabLayout;
	private ViewPager2 viewPager2;
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
		viewPager2 = (ViewPager2) view.findViewById(R.id.viewpager2);
		
		FragmentManager fragmentManager = getChildFragmentManager();
		AttendanceWorkshopsViewPagerAdapter vpAdapter = new AttendanceWorkshopsViewPagerAdapter(fragmentManager, getLifecycle());
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
		
		FloatingActionButton addNewSessionBtn = (FloatingActionButton) view.findViewById(R.id.addNewSessionBtn);
		
		addNewSessionBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), HRAddOrEditSessionActivity.class);
				intent.putExtra("sessionWorkshop", viewPager2.getCurrentItem());
				startActivity(intent);
			}
		});
		
	}
}