package com.example.scciapp.Admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AccountTypesViewPagerAdapter extends FragmentStateAdapter {
	
	public AccountTypesViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
		super(fragmentManager, lifecycle);
	}
	
	@NonNull
	@Override
	public Fragment createFragment(int position) {
		if(position == 0) {
			return new AdminManageAccountsAdminsFragment();
		} else if (position == 1){
			return new AdminManageAccountsHRFragment();
		} else {
			return new AdminManageAccountsParticipantsFragment();
		}
	}
	
	@Override
	public int getItemCount() {
		return 3;
	}
}
