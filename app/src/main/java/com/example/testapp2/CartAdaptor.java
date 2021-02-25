package com.example.testapp2;

import android.app.Dialog;
import android.os.SystemClock;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdaptor extends RecyclerView.Adapter{
    private List<CartItemModal>cartItemModalList;
    private int lastPosition=-1;
    private long mLastClickTime=0;
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
                String resource=cartItemModalList.get(position).getProductImage();
                String title=cartItemModalList.get(position).getProductTitle();
                String cuttedPrie=cartItemModalList.get(position).getCuttedPrice();
                String discountedPrice=cartItemModalList.get(position).getOriginalPrice();
                long qty=cartItemModalList.get(position).getProductQunatity();
                long discount=cartItemModalList.get(position).getDiscount();
                String productID=cartItemModalList.get(position).getProductID();
                ((cartItemViewHolder)holder).setItemDetails(productID,resource,title,cuttedPrie,discountedPrice,discount,qty,position);
                break;
            case CartItemModal.TOTAL_AMOUNT:
                int totalitems=0;
                String deliveryPrice;
                float total_itemPrice=0;
                float amountAfterDiscount=0;
                for(int i=0;i<cartItemModalList.size();i++)
                {
                    if(cartItemModalList.get(i).getType()==CartItemModal.CART_ITEM)
                    {
                        totalitems++;
                        total_itemPrice=total_itemPrice+Float.parseFloat(cartItemModalList.get(i).getCuttedPrice());
                        amountAfterDiscount=amountAfterDiscount+Float.parseFloat(cartItemModalList.get(i).getOriginalPrice());

                    }
                }
                float savedAmount=total_itemPrice-amountAfterDiscount;
                if(total_itemPrice>1000)
                {
                    deliveryPrice="FREE";
                    amountAfterDiscount=total_itemPrice;
                }

                else
                {
                    deliveryPrice="Rs. 60";
                    amountAfterDiscount=total_itemPrice+60;

                }


                ((cartTotalViewHolder)holder).setTotalAmount(totalitems,total_itemPrice,amountAfterDiscount,deliveryPrice,savedAmount);
                break;
            default:
                return;

        }
        if(lastPosition<position)
        {
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in_anim);
            holder.itemView.setAnimation(animation);
            lastPosition=position;
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
        private TextView removeFromcartBtn;

        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image_my_cart);
            producttitle =itemView.findViewById(R.id.product_title_mycart_tv);
            productCuttedprice =itemView.findViewById(R.id.cutted_price_my_order);
            productPrice=itemView.findViewById(R.id.product_price_my_cart_tv);
            percentageDiscount=itemView.findViewById(R.id.percent_discount_my_cart);
            quantity=itemView.findViewById(R.id.quantity_my_cart);
            removeFromcartBtn=itemView.findViewById(R.id.remove_from_cart_tv_btn);

        }
        private void  setItemDetails(String productID,String resource,String title,String CP,String PP,long PD,long Quantity,int index)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.drawable.icon_paceholder).into(productImage);
            producttitle.setText(title);
            productCuttedprice.setText(CP);
            productPrice.setText("Rs. "+PP);
            percentageDiscount.setText(PD+" % OFF");
            quantity.setText("Qty:"+Quantity);
            quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog quantityDialog=new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog_layout);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    EditText quantityNumber=quantityDialog.findViewById(R.id.quantity_number);
                    TextView cancelBtn=quantityDialog.findViewById(R.id.cancel_in_btn_dilaogbox);
                    TextView applyBtn=quantityDialog.findViewById(R.id.Ok_up_btn_dilaogbox);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });
                    applyBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int z=3;
                          String qunant=quantityNumber.getText().toString();
                            int q=Integer.parseInt(qunant);
                            int d=5;
                            if(q>5)
                            {
                                q=5;
                                Toast.makeText(itemView.getContext(),"We're sorry! Only 5 units allowed in each order",Toast.LENGTH_SHORT).show();
                            }
                            quantity.setText("Qty:"+q);
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();

                }
            });
            removeFromcartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Preventing multiple clicks, using threshold of 500 mili second
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        removeFromcartBtn.setEnabled(false);
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if(!ProductDetailsActivity.running_cart_querry)
                    {
                        ProductDetailsActivity.running_cart_querry=true;
                        DBqueries.removeCartList(index,itemView.getContext());
                    }
                    else
                    {
                        removeFromcartBtn.setEnabled(false);
                    }
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
        private void setTotalAmount (int TI,float TIP,float AFD,String DP,float SA)
        {
            totalItems.setText("Price -- "+ TI +" items");
            totalItemPrice.setText("Rs."+ TIP);
            amountAfterDiscount.setText("Rs."+ AFD);
            deliveryPrice.setText("Rs."+DP);
            savedAmount.setText("You saved Rs."+ SA+" on this order.");

        }
    }


}
