package com.example.testapp2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishlistAdaptor extends RecyclerView.Adapter<WishlistAdaptor.ViewHolder>
{
    private boolean wishlist;
    List<WishListModal>wishListModalList;

    public WishlistAdaptor(List<WishListModal> wishListModalList,boolean wishlist) {
        this.wishListModalList = wishListModalList;
        this.wishlist=wishlist;
    }

    @NonNull
    @Override
    public WishlistAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdaptor.ViewHolder holder, int position)
    {
        int resource=wishListModalList.get(position).getProductImage();
        String title=wishListModalList.get(position).getProductTitle();
        String discountedPrice=wishListModalList.get(position).getDiscountPrice();
        String cuttedPrice=wishListModalList.get(position).getCuttedPrice();
        String percentageDis=wishListModalList.get(position).getPercentDiscount();
        String paymentMethod=wishListModalList.get(position).getPaymentMethod();
        holder.setMyWishList(resource,title,cuttedPrice,discountedPrice,percentageDis,paymentMethod);

    }

    @Override
    public int getItemCount() {
        return wishListModalList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView productImage;

        private TextView productTitle,cuttedPrice,discountedPrice,percentageDiscount,paymentmethod;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image_my_wishlist);
            productTitle=itemView.findViewById(R.id.product_title_mywishlist);
            cuttedPrice=itemView.findViewById(R.id.cutted_price_mywishlist);
            discountedPrice=itemView.findViewById(R.id.discount_price_mywishlist);
            percentageDiscount=itemView.findViewById(R.id.percent_discount_wishist);
            paymentmethod=itemView.findViewById(R.id.payment_method_wishlist);
            ImageView deleteBtn = itemView.findViewById(R.id.delete_wishlist_icon);
            if(wishlist)
            {
                deleteBtn.setVisibility(View.VISIBLE);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(itemView.getContext(),"Delete Pressed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                deleteBtn.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);

                }
            });

        }
        private void setMyWishList(int resource,String title,String CP,String DP,String perDiscount,String payment)
        {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            discountedPrice.setText(DP);
            cuttedPrice.setText(CP);
            percentageDiscount.setText(perDiscount);
            paymentmethod.setText(payment);

        }
    }
}
