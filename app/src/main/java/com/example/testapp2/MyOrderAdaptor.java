package com.example.testapp2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdaptor extends RecyclerView.Adapter<MyOrderAdaptor.ViewHolder>
{
    private List<MyOrderItemModal>myOrderItemModalList;

    public MyOrderAdaptor(List<MyOrderItemModal> myOrderItemModalList) {
        this.myOrderItemModalList = myOrderItemModalList;
    }

    @NonNull
    @Override
    public MyOrderAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdaptor.ViewHolder holder, int position)
    {
        int resource=myOrderItemModalList.get(position).getResource();
        String title=myOrderItemModalList.get(position).getProductTitle();
        String deliveryDate=myOrderItemModalList.get(position).getDeliveryDate();
        holder.setData(resource,title,deliveryDate);


    }

    @Override
    public int getItemCount() {
        return myOrderItemModalList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView productTitle,deliveryDate;
        private ImageView productImage,orderIndicator;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image_my_order_tv);
            orderIndicator=itemView.findViewById(R.id.order_delivery_indicator_my_order_tv);
            productTitle=itemView.findViewById(R.id.product_title_my_order_tv);
            deliveryDate=itemView.findViewById(R.id.order_delivered_date_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent=new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });

        }
        private  void setData(int resource,String title,String date)
        {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            deliveryDate.setText(date);
            if(date.equals("Cancelled"))
            {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.btnRed)));
            }
            else
            {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.dark_green)));
            }

        }
    }
}
