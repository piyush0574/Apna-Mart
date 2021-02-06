package com.example.testapp2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.testapp2.DBqueries.categoryModelList;
import static com.example.testapp2.DBqueries.firebaseFirestore;
import static com.example.testapp2.DBqueries.lists;
import static com.example.testapp2.DBqueries.loadCategories;
import static com.example.testapp2.DBqueries.loadFragmentDdata;
import static com.example.testapp2.DBqueries.loadedCategoriesNames;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }
    private RecyclerView categoryRecycleView;
    private  CategoryAdaptor categoryAdaptor;
    private  RecyclerView homePageRecycleView;
    private HomePageAdaptor homePageAdaptor;
    private ImageView noInternetConnection;

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        //Internet Check
        noInternetConnection=view.findViewById(R.id.no_internet_connection);
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()==true)
        {
            noInternetConnection.setVisibility(View.GONE);
            // category section
            categoryRecycleView=view.findViewById(R.id.category_recycleview);
            LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecycleView.setLayoutManager(layoutManager);

            categoryAdaptor=new CategoryAdaptor(categoryModelList);
            categoryRecycleView.setAdapter(categoryAdaptor);
            if(categoryModelList.size()==0 || categoryModelList==null)
            {
                loadCategories(categoryAdaptor,getContext());

            }
            else
            {
                categoryAdaptor.notifyDataSetChanged();
            }


   /// setting content for homepage

            homePageRecycleView=view.findViewById(R.id.home_page_recyclar_view);
            LinearLayoutManager homePageRecycleViewLayoutManager=new LinearLayoutManager(getContext());
            homePageRecycleViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homePageRecycleView.setLayoutManager(homePageRecycleViewLayoutManager);
            if(lists.size()==0 || lists==null)
            {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                homePageAdaptor=new HomePageAdaptor(lists.get(0));
                loadFragmentDdata(homePageAdaptor,getContext(),0,"Home");

            }
            else
            {
                homePageAdaptor=new HomePageAdaptor(lists.get(0));
                homePageAdaptor.notifyDataSetChanged();
            }

            homePageRecycleView.setAdapter(homePageAdaptor);



        }
        else
        {
            Glide.with(this).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);

        }
        //Internet Check



       return view;

    }
    // Banner Slider


}