package com.example.testapp2;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdaptor extends RecyclerView.Adapter{
    private List<CartItemModal>cartItemModalList;

    public CartAdaptor(List<CartItemModal> cartItemModalList) {
        this.cartItemModalList = cartItemModalList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case CartItemModal.CART_ITEM:
                View view_cartitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                return new cartItemViewHolder(view_cartitem);
            case CartItemModal.TOTAL_AMOUNT:
                View view_totalAmount = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                return new cartTotalViewHolder(view_totalAmount);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        switch (cartItemModalList.get(position).getType())
        {
            case CartItemModal.CART_ITEM:
                int resource=cartItemModalList.get(position).getProductImage();
                String title=cartItemModalList.get(position).getProductTitle();
                String cuttedPrie=cartItemModalList.get(position).getCuttedPrice();
                String discountedPrice=cartItemModalList.get(position).getOriginalPrice();
                int qty=cartItemModalList.get(position).getProductQunatity();
                int discount=cartItemModalList.get(position).getDiscount();
                ((cartItemViewHolder)holder).setItemDetails(resource,title,cuttedPrie,discountedPrice,discount,qty);
                break;
            case CartItemModal.TOTAL_AMOUNT:
                int total_items=cartItemModalList.get(position).getTotalNumberOfItems();
                String totalAmount=cartItemModalList.get(position).getTotalAmount();
                String deliveryPrice=cartItemModalList.get(position).getDeliveryPrice();
                String savedAmount=cartItemModalList.get(position).getSavedAmount();
                String amountAfterDiscount=cartItemModalList.get(position).getAmountAfterDiscount();

                ((cartTotalViewHolder)holder).setTotalAmount(total_items,totalAmount,amountAfterDiscount,deliveryPrice,savedAmount);
                break;
            default:
                return;

        }



    }

    @Override
    public int getItemCount() {
        return cartItemModalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModalList.get(position).getType())
        {
            case 0:
                return CartItemModal.CART_ITEM;

            case 1:
                return CartItemModal.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    class cartItemViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView productImage;
        private TextView producttitle;
        private TextView productCuttedprice;
        private TextView productPrice;
        private TextView percentageDiscount;
        private TextView quantity;

        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image_my_cart);
            producttitle =itemView.findViewById(R.id.product_title_mycart_tv);
            productCuttedprice =itemView.findViewById(R.id.cutted_price_my_order);
            productPrice=itemView.findViewById(R.id.product_price_my_cart_tv);
            percentageDiscount=itemView.findViewById(R.id.percent_discount_my_cart);
            quantity=itemView.findViewById(R.id.quantity_my_cart);



        }
        private void  setItemDetails(int resource,String title,String CP,String PP,int PD,int Quantity)
        {
            productImage.setImageResource(resource);
            producttitle.setText(title);
            productCuttedprice.setText(CP);
            productPrice.setText(PP);
            percentageDiscount.setText(PD+" %");
            quantity.setText("Qty:"+1);
            quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog quantityDialog=new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog_layout);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    EditText quantityNumber=quantityDialog.findViewById(R.id.quantity_number);
                    Button cancelBtn=quantityDialog.findViewById(R.id.cancel_in_btn_dilaogbox);
                    Button okBtn=quantityDialog.findViewById(R.id.Ok_up_btn_dilaogbox);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantity.setText("Qty: "+quantityNumber.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();

                }
            });

        }
    }
    class cartTotalViewHolder extends RecyclerView.ViewHolder
    {
         TextView totalItems,totalItemPrice,deliveryPrice,amountAfterDiscount,savedAmount;

        public cartTotalViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems=itemView.findViewById(R.id.total_items_tv);
            totalItemPrice=itemView.findViewById(R.id.total_item_price_tv);
            amountAfterDiscount=itemView.findViewById(R.id.total_price_after_discount_tv);
            deliveryPrice=itemView.findViewById(R.id.delivery_price_tv);
            savedAmount=itemView.findViewById(R.id.saved_amount_tv);


        }
        private void setTotalAmount (int TI,String TIP,String AFD,String DP,String SA)
        {
            totalItems.setText("Price -- "+TI+" items");
            totalItemPrice.setText(TIP);
            amountAfterDiscount.setText(AFD);
            deliveryPrice.setText(DP);
            savedAmount.setText(SA);

        }
    }


}
