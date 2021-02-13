package com.example.testapp2;

import android.app.Dialog;
import android.os.Bundle;

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
 * Use the {@link MyWishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyWishlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView wishlistRecycleView;
    private Dialog loadingDialog;
    public static WishlistAdaptor wishlistAdaptor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyWishlistFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static MyWishlistFragment newInstance(String param1, String param2) {
        MyWishlistFragment fragment = new MyWishlistFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        // loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progess_dialog);
//        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        wishlistRecycleView=view.findViewById(R.id.my_wishlist_recycle_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        wishlistRecycleView.setLayoutManager(linearLayoutManager);
       if(DBqueries.wishListModalList.size()==0)
       {
           DBqueries.localwishList.clear();
           DBqueries.loadWishList(getContext(),loadingDialog,true);
       }
       else
       {
           loadingDialog.dismiss();
       }
        wishlistAdaptor=new WishlistAdaptor(DBqueries.wishListModalList,true);
        wishlistRecycleView.setAdapter(wishlistAdaptor);
        wishlistAdaptor.notifyDataSetChanged();
        return view;
    }
}