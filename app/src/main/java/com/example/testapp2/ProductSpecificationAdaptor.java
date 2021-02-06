package com.example.testapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout,parent,false);
                return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdaptor.ViewHolder holder, int position)
    {
                    String featureTitle=productSpecificationModalList.get(position).getFeatureName();
                    String featureValue=productSpecificationModalList.get(position).getFeatureValue();
                     String testTitle=productSpecificationModalList.get(position).getTitle();
                    holder.setFeatures(featureTitle,featureValue,testTitle);

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
        private TextView testTitleTV;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);


        }
        private void setFeatures(final String featureTitle,final String featureDetails,String testTitle)
        {
           featureName=(TextView)itemView.findViewById(R.id.feature_name);
            featureValue=(TextView)itemView.findViewById(R.id.feature_value);
            testTitleTV=itemView.findViewById(R.id.specification_title);

            featureValue.setText(featureDetails);  // this will pass text values to respective TextViews
            featureName.setText(featureTitle);
            testTitleTV.setText(testTitle);
        }


    }
    // Step 5 : Now this layout will be set using linear layout manager in their respective Recycle View of the fragment
    // ProductSpecificationFragment
}
