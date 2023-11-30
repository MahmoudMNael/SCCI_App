package com.example.scciapp.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scciapp.R;
import com.example.scciapp.Models.User;

import java.util.ArrayList;

public class AdminManageAccountsRecViewAdapter extends RecyclerView.Adapter<AdminManageAccountsRecViewAdapter.ViewHolder> {
	
	private ArrayList<User> users = new ArrayList<>();
	private Context context;
	
	public void setUsers(ArrayList<User> users){
		this.users = users;
		notifyDataSetChanged();
	}
	
	public AdminManageAccountsRecViewAdapter(Context context) {
		this.context = context;
	}
	
	@NonNull
	@Override
	public AdminManageAccountsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_manage_accounts_list_item, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull AdminManageAccountsRecViewAdapter.ViewHolder holder, int position) {
		holder.fullName.setText(users.get(position).getUserFullName());
		holder.email.setText(users.get(position).getUserEmail());
		holder.userType.setText(users.get(position).getUserType());
		holder.userWorkshop.setText(users.get(position).getUserWorkshop() == null ? "N/A" : users.get(position).getUserWorkshop());
	}
	
	@Override
	public int getItemCount() {
		return users.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder{
		private ImageView profilePic;
		private TextView fullName;
		private TextView email;
		private TextView userType;
		private TextView userWorkshop;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			profilePic = (ImageView) itemView.findViewById(R.id.profilepic);
			fullName = (TextView) itemView.findViewById(R.id.fullname);
			email = (TextView) itemView.findViewById(R.id.email);
			userType = (TextView) itemView.findViewById(R.id.userType);
			userWorkshop = (TextView) itemView.findViewById(R.id.userWorkshop);
		}
	}
}
