package com.example.testapp2;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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
        // Inflate the layout for this fragment
        // with the help of one recycle view and linearlayout manager ,we will inflate both layouts
        // for cart and total price sections
        // v29

       List<CartItemModal>cartItemModalList=new ArrayList<>();
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_2,"Redmi K19","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_3,"Redmi K21","Rs.200000/-","Rs.180000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
         cartItemModalList.add(new CartItemModal(1,2,"20000","Free","You have saved Rs 3000 on this order","Rs 18000"));
         View view= inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemRecycleView=view.findViewById(R.id.cart_items_recycle_view);
        LinearLayoutManager cartmanager=new LinearLayoutManager(getActivity());
        cartmanager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecycleView.setLayoutManager(cartmanager);
        //here we will create and set adaptor
        CartAdaptor cartAdaptor=new CartAdaptor(cartItemModalList);
        cartItemRecycleView.setAdapter(cartAdaptor);
        cartAdaptor.notifyDataSetChanged();
        return view;


    }

}