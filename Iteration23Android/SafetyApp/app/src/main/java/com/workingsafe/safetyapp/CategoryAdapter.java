package com.workingsafe.safetyapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workingsafe.safetyapp.model.CategoryModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {
    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryVH holder, int position) {
        final String title = categoryModelList.get(position).getTitle();
        TextView titleView = holder.title;
        titleView.setText(title);
        CircleImageView crcImageView = holder.circleImageView;
        crcImageView.setImageResource(R.drawable.safewomen);
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.rowLayout.getContext(),SetsActivity.class);
                intent.putExtra("title",title);
                holder.rowLayout.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class CategoryVH extends RecyclerView.ViewHolder{
         CircleImageView circleImageView;
         TextView title;
         LinearLayout rowLayout;
        public CategoryVH(@NotNull View itemview){
            super(itemview);
            circleImageView = itemview.findViewById(R.id.image_view);
            title = itemview.findViewById(R.id.titleTxtId);
            rowLayout = itemview.findViewById(R.id.setItemRowLayout);
        }
    }
}
