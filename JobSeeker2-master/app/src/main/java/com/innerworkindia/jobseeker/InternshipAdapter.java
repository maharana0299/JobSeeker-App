package com.innerworkindia.jobseeker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.innerworkindia.jobseeker.Model.JobPost;

import java.util.ArrayList;

public class InternshipAdapter extends RecyclerView.Adapter<InternshipAdapter.MyViewHolder> {

    int a;
    String b;
    ArrayList<JobPost> c;
    Context context;
    public InternshipAdapter(Context context,int a, String b, ArrayList<JobPost> c) {
        this.context=context;
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_item,parent,false);
        return new InternshipAdapter.MyViewHolder(view);
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
        holder.t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Details.class);
                intent.putExtra("Id",c.get(position).id);
                intent.putExtra("Job Title",c.get(position).jobTitle);
                intent.putExtra("Company",c.get(position).company);
                intent.putExtra("Email",c.get(position).email);
                intent.putExtra("Job Type",c.get(position).jobType);
                intent.putExtra("Location",c.get(position).location);
                intent.putExtra("Min Salary",c.get(position).minSalary);
                intent.putExtra("Max Salary",c.get(position).maxSalary);
                intent.putExtra("Contact Person Name",c.get(position).cpname);
                intent.putExtra("Contact Person Number",c.get(position).cpnum);
                intent.putExtra("Status",c.get(position).status);
                intent.putExtra("Experience",c.get(position).exp);
                intent.putExtra("Education",c.get(position).education);
                intent.putExtra("Job Desc",c.get(position).jDesc);
                intent.putExtra("Date Time",c.get(position).dateTime);
                intent.putExtra("Job Ref Amt",c.get(position).jobReferalamt);
                intent.putExtra("About Comp",c.get(position).aboutComp);
                intent.putExtra("Type",c.get(position).type);
                intent.putExtra("Skill",c.get(position).skills);
                intent.putExtra("Idesc",c.get(position).idesc);
                intent.putExtra("Agency Id",c.get(position).agencyId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return c.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4,t5,t6;
        CardView t7;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.c_title);
            t2=itemView.findViewById(R.id.c_name);
            t3=itemView.findViewById(R.id.c_stipend);
            t4=itemView.findViewById(R.id.c_location);
            t5=itemView.findViewById(R.id.c_duration);
            t6=itemView.findViewById(R.id.c_job_or_internship);
            t7=itemView.findViewById(R.id.items);
        }

    }
}
