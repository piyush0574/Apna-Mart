package com.example.testapp2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import static com.example.testapp2.DBqueries.loadFragmentData;
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
    private Button retryBtn;
    private TextView noInternetMessage;
   public static SwipeRefreshLayout swipeRefreshLayout;

   // dummy list
    private List<CategoryModel>dummyCategoryModelList=new ArrayList<>();
    private List<HomePageModel>dummyHomePageModelList=new ArrayList<>();

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
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout_home);
        retryBtn=view.findViewById(R.id.RetryBtn);
        noInternetMessage=view.findViewById(R.id.no_internet_connection_message);
        // category section
        categoryRecycleView=view.findViewById(R.id.category_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(layoutManager);

        homePageRecycleView=view.findViewById(R.id.home_page_recyclar_view);
        LinearLayoutManager homePageRecycleViewLayoutManager=new LinearLayoutManager(getContext());
        homePageRecycleViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecycleView.setLayoutManager(homePageRecycleViewLayoutManager);

        // dummy list
        dummyCategoryModelList.add(new CategoryModel("null",""));
        dummyCategoryModelList.add(new CategoryModel("",""));
        dummyCategoryModelList.add(new CategoryModel("",""));
        dummyCategoryModelList.add(new CategoryModel("",""));
        dummyCategoryModelList.add(new CategoryModel("",""));
        dummyCategoryModelList.add(new CategoryModel("",""));
        dummyCategoryModelList.add(new CategoryModel("",""));
        dummyCategoryModelList.add(new CategoryModel("",""));

        //dummy home fragment modal list
        List <SliderModel>dummysliderModelList=new ArrayList<>();
        dummysliderModelList.add(new SliderModel("","#dfdfdf"));
        dummysliderModelList.add(new SliderModel("","#dfdfdf"));
        dummysliderModelList.add(new SliderModel("","#dfdfdf"));
        dummysliderModelList.add(new SliderModel("","#dfdfdf"));

        List<HorizonalProductScrollModel>dummyhorizonalProductScrollModelList=new ArrayList<>();
        dummyhorizonalProductScrollModelList.add(new HorizonalProductScrollModel("","","","",""));
        dummyhorizonalProductScrollModelList.add(new HorizonalProductScrollModel("","","","",""));
        dummyhorizonalProductScrollModelList.add(new HorizonalProductScrollModel("","","","",""));
        dummyhorizonalProductScrollModelList.add(new HorizonalProductScrollModel("","","","",""));
        dummyhorizonalProductScrollModelList.add(new HorizonalProductScrollModel("","","","",""));
        dummyhorizonalProductScrollModelList.add(new HorizonalProductScrollModel("","","","",""));
        dummyhorizonalProductScrollModelList.add(new HorizonalProductScrollModel("","","","",""));

        dummyHomePageModelList.add(new HomePageModel(dummysliderModelList,0));
        dummyHomePageModelList.add(new HomePageModel(2,"",dummyhorizonalProductScrollModelList,"#dfdfdf",new ArrayList<WishListModal>()));
        dummyHomePageModelList.add(new HomePageModel(3,"",dummyhorizonalProductScrollModelList,"#dfdfdf"));
        dummyHomePageModelList.add(new HomePageModel(4,"","",dummyCategoryModelList));


        categoryAdaptor=new CategoryAdaptor(dummyCategoryModelList);

        homePageAdaptor=new HomePageAdaptor(dummyHomePageModelList);


        //Internet Check
        noInternetConnection=view.findViewById(R.id.no_internet_connection);
        if(ConnectivityCheck.isNetworkAvaliable(getContext()))
        {
            noInternetConnection.setVisibility(View.GONE);
            noInternetMessage.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecycleView.setVisibility(View.VISIBLE);
            homePageRecycleView.setVisibility(View.VISIBLE);

            if(categoryModelList.size()==0 || categoryModelList==null)
            {
                loadCategories(categoryRecycleView,getContext());

            }
            else
            {
                categoryAdaptor=new CategoryAdaptor(categoryModelList);
                categoryAdaptor.notifyDataSetChanged();
            }
            categoryRecycleView.setAdapter(categoryAdaptor);


   /// setting content for homepage


            if(lists.size()==0 || lists==null)
            {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecycleView,getContext(),0,"Home");

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
            categoryRecycleView.setVisibility(View.GONE);
            homePageRecycleView.setVisibility(View.GONE);
             Glide.with(this).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            noInternetMessage.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);


        }

        // swipe refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                RefreshPage();



            }
        });
        // swipe refresh layout
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshPage();
            }
        });

       return view;

    }
    // refresh page
    public void RefreshPage()
    {
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary)
                ,getContext().getResources().getColor(R.color.dark_green),getContext().getResources().getColor(R.color.teal_200));
        categoryModelList.clear();
        loadedCategoriesNames.clear();
        lists.clear();

        if(ConnectivityCheck.isNetworkAvaliable(getContext()))
        {
            categoryRecycleView.setVisibility(View.VISIBLE);
            homePageRecycleView.setVisibility(View.VISIBLE);

            noInternetConnection.setVisibility(View.GONE);
            noInternetMessage.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);

            categoryAdaptor=new CategoryAdaptor(dummyCategoryModelList);
            homePageAdaptor=new HomePageAdaptor(dummyHomePageModelList);
            categoryRecycleView.setAdapter(categoryAdaptor); // this is fake list adaptor
            // original adaptor is in db query
            homePageRecycleView.setAdapter(homePageAdaptor);
            loadCategories(categoryRecycleView,getContext());
            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecycleView,getContext(),0,"Home");

        }
        else
        {
            Glide.with(getContext()).load(R.drawable.no_internet).into(noInternetConnection);
            categoryRecycleView.setVisibility(View.GONE);
            homePageRecycleView.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.VISIBLE);
            noInternetMessage.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);

        }



    }


}