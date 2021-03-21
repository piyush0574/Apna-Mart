package com.example.testapp2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCartFragment() {
        // Required empty public constructor
    }
    private RecyclerView cartItemRecycleView;
    private Button continueBtn;
    public static LinearLayout continuelayout;

    public static Dialog loadingDialog;
    public static CartAdaptor cartAdaptor;
    private TextView totalAmount;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCartFragment.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progess_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        // loading dialog

        // Inflate the layout for this fragment
        // with the help of one recycle view and linearlayout manager ,we will inflate both layouts
        // for cart and total price sections
        // v29

         View view= inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemRecycleView=view.findViewById(R.id.cart_items_recycle_view);
        totalAmount=view.findViewById(R.id.totalcartAmount_Tv_my_cart);
        continuelayout=view.findViewById(R.id.continue_layout);
        LinearLayoutManager cartmanager=new LinearLayoutManager(getActivity());
        cartmanager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecycleView.setLayoutManager(cartmanager);
        if(DBqueries.cartItemModalList.size()==0)
        {
            DBqueries.localCartList.clear();
            DBqueries.loadCartList(getContext(),true,loadingDialog,new TextView(getContext()),totalAmount);
        }
        else
        {
            if(DBqueries.cartItemModalList.get(DBqueries.cartItemModalList.size()-1).getType()==CartItemModal.TOTAL_AMOUNT)
            {
                LinearLayout continueBtnContainer=(LinearLayout)totalAmount.getParent().getParent();
                continueBtnContainer.setVisibility(View.VISIBLE);


            }
            loadingDialog.dismiss();
        }
        //here we will create and set adaptor
        cartAdaptor=new CartAdaptor(DBqueries.cartItemModalList,totalAmount,true);
        cartItemRecycleView.setAdapter(cartAdaptor);
        cartAdaptor.notifyDataSetChanged();
        continueBtn=view.findViewById(R.id.cart_continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModalListDeliveryActivity=new ArrayList<>();
                for(int x=0;x<DBqueries.cartItemModalList.size();x++)
                {
                    CartItemModal cartItemModal=DBqueries.cartItemModalList.get(x);
                    if(cartItemModal.isInStock())
                    {
                        DeliveryActivity.cartItemModalListDeliveryActivity.add(cartItemModal);
                    }
                }
                DeliveryActivity.cartItemModalListDeliveryActivity.add(DeliveryActivity.cartItemModalListDeliveryActivity.size()-1,new CartItemModal(CartItemModal.TOTAL_AMOUNT));
                loadingDialog.show();
                if(DBqueries.addressesModalList.size()==0)
                {
                    DBqueries.loadAddresses(getContext(),loadingDialog);
                }
                else
                {
                    loadingDialog.dismiss();
                    Intent deliveryIntent=new Intent(getContext(),DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }


            }
        });
        return view;


    }

}