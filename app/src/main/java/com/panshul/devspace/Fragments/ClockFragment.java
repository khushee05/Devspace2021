package com.panshul.devspace.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panshul.devspace.Adapters.ClockAdapter;
import com.panshul.devspace.Model.TaskModel;
import com.panshul.devspace.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClockFragment extends Fragment {

    View view;
    List<TaskModel> taskList;
    RecyclerView recyclerView;
    ConstraintLayout ui1,ui2;
    Button addTask;
    ImageView addtask2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_clock, container, false);
        recyclerView = view.findViewById(R.id.clockListRecyclerView);
        ui1 = view.findViewById(R.id.clockEmptyLayout1);
        ui2 = view.findViewById(R.id.clockLayout);
        addTask = view.findViewById(R.id.emptyTaskButton1);
        addtask2 = view.findViewById(R.id.clockAddImageview);
        onClickListeners();

        loadData();
        adapter();
        checkData();
        return view;
    }
    public void adapter(){
        ClockAdapter adapter = new ClockAdapter(view.getContext(),taskList,ClockFragment.this);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void onClickListeners(){
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFragment fragment = new TaskFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        addtask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFragment fragment = new TaskFragment();
                FragmentManager manager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.panshul.devspace.tasklist",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("taskList","");
        Type type = new TypeToken<ArrayList<TaskModel>>() {}.getType();
        taskList =gson.fromJson(json,type);
        if(taskList==null){
            taskList =new ArrayList<>();
        }
    }
    public void checkData(){
        if (taskList.isEmpty()){
            ui1.setVisibility(View.VISIBLE);
            ui2.setVisibility(View.INVISIBLE);
        }
        else {
            ui2.setVisibility(View.VISIBLE);
            ui1.setVisibility(View.INVISIBLE);
        }
    }
}