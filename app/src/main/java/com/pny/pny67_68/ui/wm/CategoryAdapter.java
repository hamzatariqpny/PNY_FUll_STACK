package com.pny.pny67_68.ui.wm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pny.pny67_68.R;
import com.pny.pny67_68.repository.model.CategoriesData;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Activity activity;
    List<CategoriesData> contactModels;

    public CategoryAdapter(Activity activity, List<CategoriesData> contactModels) {
        this.activity = activity;
        this.contactModels = contactModels;
    }

    // Step 2
    // to convert XML into java object
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_caategory,parent,false);
        return new CategoryViewHolder(view);
    }

    // Step 3
    // to bind data to the java object
    // position = 0

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {

        final CategoriesData categoriesData = contactModels.get(position);

        holder.title.setText(categoriesData.categoryName);
        holder.des.setText(categoriesData.description);

        Glide.with(activity)
                .load(categoriesData.icon)
                .into(holder.imagae);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    // to set the number if rows .
    // Step 1
    // 10 0-9
    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView imagae;
        TextView title;
        TextView des;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imagae = itemView.findViewById(R.id.imageCategory);
            title = itemView.findViewById(R.id.itemText);
            des = itemView.findViewById(R.id.itemDes);

        }
    }



}
