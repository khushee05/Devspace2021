package com.panshul.devspace.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.devspace.Fragments.ListFragment;
import com.panshul.devspace.Fragments.TaskFragment;
import com.panshul.devspace.Model.TaskModel;
import com.panshul.devspace.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    List<TaskModel> taskList;
    ListFragment fragment;

    public TaskAdapter(Context context, List<TaskModel> taskList, ListFragment fragment) {
        this.context = context;
        this.taskList = taskList;
        this.fragment = fragment;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,info,timer;
        ImageView delete,check;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ItemTaskName);
            info= itemView.findViewById(R.id.ItemTaskContent);
            timer = itemView.findViewById(R.id.ItemTimerText);
            delete = itemView.findViewById(R.id.ItemDeleteImage);
            check = itemView.findViewById(R.id.ItemTickImage);
            layout = itemView.findViewById(R.id.taskLayout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.task_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TaskModel item = taskList.get(position);
        holder.name.setText(item.getTaskName());
        holder.info.setText(item.getTaskContent());
        holder.timer.setText(item.getTime() +" min");
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
                SharedPreferences preferences = context.getSharedPreferences("com.panshul.devspace.taskId",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putString("taskSpecificId",item.getTaskId());
                editor.apply();
                TaskFragment fragment = new TaskFragment();
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
