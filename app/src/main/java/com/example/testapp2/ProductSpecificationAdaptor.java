package com.example.testapp2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSpecificationAdaptor extends RecyclerView.Adapter<ProductSpecificationAdaptor.ViewHolder> {
// Step 1
    // here we will get the list of modal class
    private List<ProductSpecificationModal>productSpecificationModalList;
    public ProductSpecificationAdaptor(List<ProductSpecificationModal> productSpecificationModalList) {
        this.productSpecificationModalList = productSpecificationModalList;
    }

    @NonNull
    @Override
    public ProductSpecificationAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //step 2
        // inflate the main layout here product_specification_item_layout and pass it to ViewHolder class using
        // View Holder contructor
        switch (viewType)
        {
            case ProductSpecificationModal.SPECIFICATION_BODY:
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout,parent,false);
                return new ViewHolder(view);
            case ProductSpecificationModal.SPECIFICATION_TITLE:
                TextView title=new TextView(parent.getContext()); // creating Textview using code
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                //cp
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(setdp(16,parent.getContext()),setdp(16,parent.getContext()),
                        setdp(16,parent.getContext()),setdp(8,parent.getContext()));
                return new ViewHolder(title);
            default:
                return null;

        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecificationModalList.get(position).getType())
        {
            case 0:
                return ProductSpecificationModal.SPECIFICATION_TITLE;
            case 1:
                return ProductSpecificationModal.SPECIFICATION_BODY;
            default:
                return  -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdaptor.ViewHolder holder, int position)
    {
        // Here we will access data from modal class
        switch (productSpecificationModalList.get(position).getType())
        {
            case ProductSpecificationModal.SPECIFICATION_TITLE:
                holder.setTitle(productSpecificationModalList.get(position).getTitle());
                break;
            case  ProductSpecificationModal.SPECIFICATION_BODY:
                    String featureTitle=productSpecificationModalList.get(position).getFeatureName();
                    String featureValue=productSpecificationModalList.get(position).getFeatureValue();
                    // new we will pass it to setfeatures
                    holder.setFeatures(featureTitle,featureValue);
                    break;
            default:

        }




    }

    @Override
    public int getItemCount() {
        return productSpecificationModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //step 3
        // for the viewHolder class
        // we have two parameters which name and value whose value is filled in the contructor
        // using their respective ids
        private TextView featureName;
        private TextView featureValue;
        private TextView title;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);


        }
        private void setFeatures(final String featureTitle,final String featureDetails)
        {
            String x=featureTitle;
           featureName=(TextView)itemView.findViewById(R.id.feature_name);
            featureValue=(TextView)itemView.findViewById(R.id.feature_value);
            featureValue.setText(featureDetails);  // this will pass text values to respective TextViews
            featureName.setText(featureTitle);
        }
        private void setTitle(String titleText)
        {
            title=(TextView)itemView;
            title.setText(titleText);//cp

        }


    }
    // Step 5 : Now this layout will be set using linear layout manager in respective Recycle View of the fragment
    // ProductSpecificationFragment
    private  int setdp(int dp, Context context)
    {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
