package com.example.testapp2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizonalProductScrollAdaptor extends RecyclerView.Adapter<HorizonalProductScrollAdaptor.ViewHolder> {
    private List<HorizonalProductScrollModel>horizonalProductScrollModelList;

    public HorizonalProductScrollAdaptor(List<HorizonalProductScrollModel> horizonalProductScrollModelList) {
        this.horizonalProductScrollModelList = horizonalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizonalProductScrollAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizonalProductScrollAdaptor.ViewHolder viewHolder, int position)
    {
        String resource=horizonalProductScrollModelList.get(position).getProductImage();
        String title=horizonalProductScrollModelList.get(position).getProductTitle();
        String desciption=horizonalProductScrollModelList.get(position).getGetProductDescription();
        String price=horizonalProductScrollModelList.get(position).getProductPrice();
        String productId=horizonalProductScrollModelList.get(position).getProductID();
        viewHolder.setHorizontalLayout(productId,resource,title,desciption,price);

    }

    @Override
    public int getItemCount() {
//        if(horizonalProductScrollModelList.size()>8)
//            return 8;
//        else
//        {
//            return horizonalProductScrollModelList.size();
//        }
        return horizonalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle,productDescription,productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.horizontal_product_image);
            productDescription=itemView.findViewById(R.id.horizontal_product_description);
            productTitle=itemView.findViewById(R.id.horizontal_product_layout_title);
            productPrice=itemView.findViewById(R.id.horizontal_product_price);



        }
        private void setHorizontalLayout(String productId,String resource,String title,String desc,String price)
        {
            Glide.with(itemView.getContext()).load(resource)
                    .apply(new RequestOptions().placeholder(R.drawable.icon_paceholder)).into(productImage);
            productTitle.setText(title);
            productDescription.setText(desc);
            productPrice.setText(price);
            if(!title.equals(""))
            {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        productDetailIntent.putExtra("productID",productId);
                        itemView.getContext().startActivity(productDetailIntent);
                    }
                });
            }
        }


    }
}
