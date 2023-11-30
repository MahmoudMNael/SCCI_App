package com.example.scciapp.HR;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.scciapp.Models.WeeklySession;
import com.example.scciapp.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HRWeeklySessionsRecViewAdapter extends RecyclerView.Adapter<HRWeeklySessionsRecViewAdapter.ViewHolder> {
	private ArrayList<WeeklySession> sessions = new ArrayList<>();
	private Context context;
	
	public void setSessions(ArrayList<WeeklySession> sessions){
		this.sessions = sessions;
		notifyDataSetChanged();
	}
	
	public HRWeeklySessionsRecViewAdapter(Context context) {
		this.context = context;
	}
	
	@NonNull
	@Override
	public HRWeeklySessionsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hr_sessions_list_item, parent, false);
		return new HRWeeklySessionsRecViewAdapter.ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull HRWeeklySessionsRecViewAdapter.ViewHolder holder, int position) {
		holder.parent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HRViewSessionActivity.class);
				intent.putExtra("sessionID", sessions.get(position).getSessionID());
				context.startActivity(intent);
			}
		});
		
		holder.sessionWorkshop.setText(sessions.get(position).getSessionWorkshop());
		holder.sessionType.setText(sessions.get(position).getSessionType());
		holder.sessionDate.setText(sessions.get(position).getSessionDate());
	}
	
	@Override
	public int getItemCount() {
		return sessions.size();
	}
	
	
	public class ViewHolder extends RecyclerView.ViewHolder{
		private TextView sessionWorkshop;
		private TextView sessionType;
		private TextView sessionDate;
		private View parent;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			sessionWorkshop = itemView.findViewById(R.id.sessionWorkshop);
			sessionType = itemView.findViewById(R.id.sessionType);
			sessionDate = itemView.findViewById(R.id.sessionDate);
			parent = itemView.findViewById(R.id.parent);
		}
	}
}
