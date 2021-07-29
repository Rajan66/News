package com.example.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.news.CategoryRVModel;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>{

    private ArrayList<CategoryRVModel> categoryRVModels;
    private Context context;
    private CategoryClickInterface categoryClickInterface;
    // WHY DO WE NEED TO MAKE A CATEGORY CLICK INTERFACE VARIABLE AND PASS IT IN THE CONSTRUCTOR?

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModels, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryClickInterface = categoryClickInterface;
        this.categoryRVModels = categoryRVModels;
        this.context = context;
    }

    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_rv_item,parent,false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CategoryRVAdapter.ViewHolder holder, int position) {
        holder.categoryTextView.setText(categoryRVModels.get(position).getCategory());
        Picasso.get().load(categoryRVModels.get(position).getCategoryImageUrl()).into(holder.categoryImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
                //this position goes into the Main Activity where the onCategoryClick is implemented
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }

    //to get the specific category on user's click
    public interface CategoryClickInterface{
        //position parameter so that we can identify the category
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryTextView;
        private ImageView categoryImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.textViewCategory);
            categoryImageView = itemView.findViewById(R.id.imageCategory);
        }
    }
}
