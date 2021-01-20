package com.innerworkindia.jobseeker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.innerworkindia.jobseeker.Model.JobPost;

import java.util.ArrayList;

public class AppliedAdapter extends RecyclerView.Adapter<AppliedAdapter.MyViewHolder> {

    int a;
    String b;
    ArrayList<JobPost> c;
    Context context;
    public AppliedAdapter(Context context, ArrayList<JobPost> c) {
        this.context=context;
        this.c = c;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.t1.setText(c.get(position).jobTitle);
        holder.t2.setText(c.get(position).company);
        String st=c.get(position).maxSalary+"-"+c.get(position).minSalary;
        holder.t3.setText(st);
        holder.t4.setText(c.get(position).location);
        holder.t5.setText(c.get(position).dateTime);
        holder.t6.setText(c.get(position).type);
    }

    @Override
    public int getItemCount() {
        return c.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4,t5,t6;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.c_title);
            t2=itemView.findViewById(R.id.c_name);
            t3=itemView.findViewById(R.id.c_stipend);
            t4=itemView.findViewById(R.id.c_location);
            t5=itemView.findViewById(R.id.c_duration);
            t6=itemView.findViewById(R.id.c_job_or_internship);

        }
    }
}
