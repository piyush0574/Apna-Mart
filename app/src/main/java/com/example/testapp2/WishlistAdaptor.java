package com.example.testapp2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdaptor extends RecyclerView.Adapter<WishlistAdaptor.ViewHolder>
{
    private boolean wishlist;
    List<WishListModal>wishListModalList;
    private int lastPosition=-1;

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
        String productId=wishListModalList.get(position).getProductId();
        String resource=wishListModalList.get(position).getProductImage();
        String title=wishListModalList.get(position).getProductTitle();
        String discountedPrice=wishListModalList.get(position).getDiscountPrice();
        String cuttedPrice=wishListModalList.get(position).getCuttedPrice();
        String percentageDis=wishListModalList.get(position).getPercentDiscount();
        boolean COD=wishListModalList.get(position).isCOD();
        holder.setMyWishList(resource,title,cuttedPrice,discountedPrice,percentageDis,COD,position,productId);
        if(lastPosition<position)
        {
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in_anim);
            holder.itemView.setAnimation(animation);
            lastPosition=position;
        }


    }

    @Override
    public int getItemCount() {
        return wishListModalList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView productImage;

        private TextView productTitle,cuttedPrice,discountedPrice,percentageDiscount,paymentmethod;
        private Button deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image_my_wishlist);
            productTitle=itemView.findViewById(R.id.product_title_mywishlist);
            cuttedPrice=itemView.findViewById(R.id.cutted_price_mywishlist);
            discountedPrice=itemView.findViewById(R.id.discount_price_mywishlist);
            percentageDiscount=itemView.findViewById(R.id.percent_discount_wishist);
            paymentmethod=itemView.findViewById(R.id.payment_method_wishlist);
             deleteBtn= itemView.findViewById(R.id.delete_wishlist_btn);
            // we are using same item layout for wishlist and  my cart item for testing
            // we don't want delete icon in cart


        }
        private void setMyWishList(String resource,String title,String CP,String DP,String perDiscount,boolean COD,int index,String productId)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.drawable.icon_paceholder).into(productImage);
            productTitle.setText(title);
            discountedPrice.setText("Rs. "+DP);
            cuttedPrice.setText("Rs. "+CP);
            percentageDiscount.setText(perDiscount);
            if(COD)
            {
                paymentmethod.setText("COD available");
            }
            else
            {
                paymentmethod.setText("COD not available");
            }
            if(wishlist)
            {
                deleteBtn.setVisibility(View.VISIBLE);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteBtn.setEnabled(false);// to avoid multiple tapping on delete button by user
                        DBqueries.removeWishList(index,itemView.getContext());
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
                    productDetailsIntent.putExtra("productID",productId);
                    itemView.getContext().startActivity(productDetailsIntent);

                }
            });

//

        }
    }
}
