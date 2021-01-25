package com.example.testapp2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
    private List<CategoryModel> categoryModelList;

    public CategoryAdaptor(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;// This is list of all category that will come from firebase
    }
//video14
    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.ViewHolder viewHolder, int position) {
// Here we will do all data binding
        String icon=categoryModelList.get(position).getCategoryIconIink();
        String name=categoryModelList.get(position).getCategorynName();
        viewHolder.setCategory(name,position);
        //viewHolder.setCategoryIcon(icon);


    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public  class ViewHolder  extends RecyclerView.ViewHolder{
        private ImageView categoryIcon; // here we will assign link from Firebase to respective placeholder
        private TextView categoryName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon=itemView.findViewById(R.id.category_icon);
            categoryName=itemView.findViewById(R.id.category_name);
        }

        private void setCategory(final String name,final int position)
        {
            categoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position !=0)
                    {
                        Intent categoryIntent=new Intent(itemView.getContext(),CategoryActivity.class);
                        categoryIntent.putExtra("CategoryName",name); // this way we pass data from one
                        //activity to another CategoryName will act as key
                        itemView.getContext().startActivity(categoryIntent);
                    }

                }
            });
        }
    }
}
