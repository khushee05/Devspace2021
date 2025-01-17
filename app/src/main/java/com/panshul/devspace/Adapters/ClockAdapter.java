package com.panshul.devspace.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.panshul.devspace.Fragments.ClockFragment;
import com.panshul.devspace.Model.TaskModel;
import com.panshul.devspace.Fragments.PomodoroFragment;
import com.panshul.devspace.R;

import java.util.List;

public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.MyViewHolder> {

    Context context;
    List<TaskModel> taskList;
    ClockFragment fragment;

    public ClockAdapter(Context context, List<TaskModel> taskList,ClockFragment fragment) {
        this.context = context;
        this.taskList = taskList;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time,sessions,name;
        ImageView delete;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ItemTaskName);
            time = itemView.findViewById(R.id.clockTimerTextView);
            sessions = itemView.findViewById(R.id.clockNoOfSessions);
            delete = itemView.findViewById(R.id.clockDeleteImage);
            layout = itemView.findViewById(R.id.clockItemLayout);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.clock_items,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TaskModel item = taskList.get(position);
        holder.time.setText(item.getTime()+" min");
        holder.name.setText(item.getTaskName());
        holder.sessions.setText(item.getTime()/25 +" sessions");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.delete.setEnabled(false);
                int index = taskList.indexOf(item);
                taskList.remove(index);
                saveData();
                notifyDataSetChanged();
                fragment.checkData();
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PomodoroFragment fragment = new PomodoroFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public void saveData(){
        SharedPreferences preferences = context.getSharedPreferences("com.panshul.devspace.tasklist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("taskList",json);
        editor.apply();
    }


}
