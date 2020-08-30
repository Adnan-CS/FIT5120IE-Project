package com.workingsafe.safetyapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestimonialAdapter extends RecyclerView.Adapter<TestimonialAdapter.TestimonialVH>{


    private Context context;
    private ArrayList<String> testimonialList;
    private ArrayList<String> urlList;
    public TestimonialAdapter(Context context, ArrayList<String> testimonialList, ArrayList<String> urlList){
        this.context = context;
        this.testimonialList = testimonialList;
        this.urlList = urlList;
    }

    @NonNull
    @Override
    public TestimonialVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.testimonial_row, parent, false);
        return new TestimonialAdapter.TestimonialVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestimonialVH holder, final int position) {
        TextView titleView = holder.testiTltRel;
        titleView.setText(testimonialList.get(position));
        titleView.setMovementMethod(LinkMovementMethod.getInstance());
        holder.testiTltRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlList.get(position)));
                context.startActivity(i);
            }
        });
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlList.get(position)));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testimonialList.size();
    }

    public class TestimonialVH extends RecyclerView.ViewHolder {

        TextView testiTltRel;

        ConstraintLayout rowLayout;

        public TestimonialVH(@NonNull View itemView) {
            super(itemView);
            testiTltRel = itemView.findViewById(R.id.rowTextView);
            rowLayout = itemView.findViewById(R.id.testiRowLayout);
        }
    }
}
