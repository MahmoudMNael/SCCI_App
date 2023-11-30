package com.example.scciapp.HR;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scciapp.Models.Participant;
import com.example.scciapp.R;

import java.util.ArrayList;

public class HRParticipantsRecViewAdapter extends RecyclerView.Adapter<HRParticipantsRecViewAdapter.ViewHolder>{
	private ArrayList<Participant> participants = new ArrayList<>();
	private Context context;
	private Boolean isStatic;
	
	public void setParticipants(ArrayList<Participant> participants){
		this.participants = participants;
		notifyDataSetChanged();
	}
	
	public ArrayList<Participant> getParticipants(){
		return this.participants;
	}
	
	public HRParticipantsRecViewAdapter(Context context, Boolean isStatic) {
		this.context = context;
		this.isStatic = isStatic;
	}
	
	@NonNull
	@Override
	public HRParticipantsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view;
		view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hr_participants_list_item, parent, false);
		
		return new HRParticipantsRecViewAdapter.ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull HRParticipantsRecViewAdapter.ViewHolder holder, int position) {
		holder.participantName.setText(participants.get(position).getUserFullName());
		holder.isAttended.setChecked(participants.get(position).getAttended());
		holder.isAttended.setClickable(!isStatic);
		if(!isStatic){
			holder.isAttended.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					participants.get(position).setAttended(holder.isAttended.isChecked());
					notifyDataSetChanged();
				}
			});
			holder.parent.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					holder.isAttended.setChecked(!(holder.isAttended.isChecked()));
					participants.get(position).setAttended(holder.isAttended.isChecked());
					notifyDataSetChanged();
				}
			});
		}
	}
	
	@Override
	public int getItemCount() {
		return participants.size();
	}
	
	
	public class ViewHolder extends RecyclerView.ViewHolder{
		TextView participantName;
		CheckBox isAttended;
		View parent;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			participantName = itemView.findViewById(R.id.participantName);
			isAttended = itemView.findViewById(R.id.isAttended);
			parent = itemView.findViewById(R.id.parent);
		}
	}
}
