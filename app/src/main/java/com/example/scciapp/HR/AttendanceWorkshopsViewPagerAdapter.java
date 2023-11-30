package com.example.scciapp.HR;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AttendanceWorkshopsViewPagerAdapter extends FragmentStateAdapter {
	public AttendanceWorkshopsViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
		super(fragmentManager, lifecycle);
	}
	
	@NonNull
	@Override
	public Fragment createFragment(int position) {
		if(position == 0) {
			return new AttendanceAppsplashFragment();
		} else if (position == 1){
			return new AttendanceDevologyFragment();
		} else if (position == 2) {
			return new AttendanceMarkativeFragment();
		} else if (position == 3) {
			return new AttendanceInvestFragment();
		} else {
			return new AttendanceTechsolveFragment();
		}
	}
	
	@Override
	public int getItemCount() {
		return 5;
	}
}
