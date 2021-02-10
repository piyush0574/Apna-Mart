package com.example.testapp2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdaptor extends BaseAdapter {
    List<HorizonalProductScrollModel> horizonalProductScrollModelList;
    public GridProductLayoutAdaptor(List<HorizonalProductScrollModel> horizonalProductScrollModelList) {
        this.horizonalProductScrollModelList = horizonalProductScrollModelList;
        // we are using same modal grid product scroll
    }
    @Override
    public int getCount() {
        return horizonalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null; //we have to show only 4 items
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null)
        {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetails=new Intent(parent.getContext(),ProductDetailsActivity.class);
                    productDetails.putExtra("productID",horizonalProductScrollModelList.get(position).getProductID());
                    parent.getContext().startActivity(productDetails);
                }
            });
            ImageView productImage=view.findViewById(R.id.horizontal_product_image);
            TextView productTitle=view.findViewById(R.id.horizontal_product_layout_title);
            TextView productDesc=view.findViewById(R.id.horizontal_product_description);
            TextView productPrice=view.findViewById(R.id.horizontal_product_price);
            Glide.with(parent.getContext()).load(horizonalProductScrollModelList.get(position).getProductImage())
                    .apply(new RequestOptions().placeholder(R.drawable.icon_paceholder)).into(productImage);
            productTitle.setText(horizonalProductScrollModelList.get(position).getProductTitle());
            productPrice.setText("Rs. "+horizonalProductScrollModelList.get(position).getProductPrice()+"/-");
            productDesc.setText(horizonalProductScrollModelList.get(position).getGetProductDescription());

        }else
        {
            view=convertView;

        }

        return  view;
    }
}
