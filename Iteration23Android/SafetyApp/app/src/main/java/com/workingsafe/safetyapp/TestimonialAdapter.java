package com.workingsafe.safetyapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TestimonialAdapter extends RecyclerView.Adapter<TestimonialAdapter.TestimonialVH>{


    private Context context;
    private ArrayList<String> testimonialList;
    private ArrayList<String> urlList;
    private ArrayList<String> srcAuthor;
    public TestimonialAdapter(Context context, ArrayList<String> testimonialList, ArrayList<String> urlList, ArrayList<String> srcAuthorData){
        this.context = context;
        this.testimonialList = testimonialList;
        this.urlList = urlList;
        this.srcAuthor = srcAuthorData;
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
        ImageView itemImgView = holder.imageViewMV;
        TextView titleView = holder.titleTextView;
        TextView descView = holder.descTextViewLD;

        titleView.setText(testimonialList.get(position));
        titleView.setMovementMethod(LinkMovementMethod.getInstance());

        if(position==0 || position==1){
            itemImgView.setImageResource(R.drawable.videoplay);
        }else {
            itemImgView.setImageResource(R.drawable.document);
        }

        descView.setText(srcAuthor.get(position));
        descView.setMovementMethod(LinkMovementMethod.getInstance());

        holder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlList.get(position)));
                context.startActivity(i);
            }
        });
        holder.descTextViewLD.setOnClickListener(new View.OnClickListener() {
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

        ImageView imageViewMV;
        TextView titleTextView;
        TextView descTextViewLD;

        ConstraintLayout rowLayout;

        public TestimonialVH(@NonNull View itemView) {
            super(itemView);
            imageViewMV = itemView.findViewById(R.id.mediaIV);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descTextViewLD = itemView.findViewById(R.id.descTextView);
            rowLayout = itemView.findViewById(R.id.testiRowLayout);
        }
    }
}
