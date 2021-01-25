package com.example.testapp2;

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

import java.util.ArrayList;
import java.util.List;

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


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
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
        categoryRecycleView=view.findViewById(R.id.category_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(layoutManager);
        final List<CategoryModel> categoryModelList=new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("Link","Home"));
        categoryModelList.add(new CategoryModel("Link","Home2"));
        categoryModelList.add(new CategoryModel("Link","Home3"));
        categoryModelList.add(new CategoryModel("Link","Home4"));
        categoryModelList.add(new CategoryModel("Link","Home5"));
        categoryModelList.add(new CategoryModel("Link","Home5"));
        categoryModelList.add(new CategoryModel("Link","Home5"));
        categoryModelList.add(new CategoryModel("Link","Home5"));
        categoryModelList.add(new CategoryModel("Link","Home5"));
        categoryModelList.add(new CategoryModel("Link","Home5"));
        categoryModelList.add(new CategoryModel("Link","Home5"));
        categoryAdaptor=new CategoryAdaptor(categoryModelList);
        categoryRecycleView.setAdapter(categoryAdaptor);
        categoryAdaptor.notifyDataSetChanged();

        List<SliderModel>sliderModelList=new ArrayList<SliderModel>();
        sliderModelList=new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.mipmap.home_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.home_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.home_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.error_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.email_in_progress100px,"#077AE4"));

        //  sliderModelList.add(new SliderModel(R.mipmap.email_in_progress100px,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.cart_black,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.email_in_progress_60,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.home_icon,"#077AE4"));


        sliderModelList.add(new SliderModel(R.mipmap.error_icon,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.email_in_progress100px,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.cart_black,"#077AE4"));



       List<HorizonalProductScrollModel>horizonalProductScrollModelList=new ArrayList<>();
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.furniture_icon,"RedMi 5A","SD625","Rs.266"));
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.my_rewards,"RedMi 5A","SD625","Rs.266"));
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5A","SD625","Rs.266"));
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5A","SD625","Rs.266"));
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
       horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.furniture_icon,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.my_rewards,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));

        homePageRecycleView=view.findViewById(R.id.home_page_recyclar_view);
        LinearLayoutManager homePageRecycleViewLayoutManager=new LinearLayoutManager(getContext());
        homePageRecycleViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecycleView.setLayoutManager(homePageRecycleViewLayoutManager);

        List<HomePageModel> homePageAdaptorList=new ArrayList<>();

      // Here we are showing all our view with numbering index with different type of contructors
      //  homePageAdaptorList.add(new HomePageModel(sliderModelList,0));
        homePageAdaptorList.add(new HomePageModel(sliderModelList,0));
        homePageAdaptorList.add(new HomePageModel(1,R.mipmap.slider_banner,"#000000"));
        homePageAdaptorList.add(new HomePageModel(2,"Deals of the Day",horizonalProductScrollModelList));
       homePageAdaptorList.add(new HomePageModel(3,"Deals of the Day",horizonalProductScrollModelList));
       homePageAdaptorList.add(new HomePageModel(1,R.mipmap.slider_banner_ex,"#ff0000"));
       homePageAdaptorList.add(new HomePageModel(2,"Deals of the Day3",horizonalProductScrollModelList));
//        homePageAdaptorList.add(new HomePageModel(sliderModelList,0));

        homePageAdaptorList.add(new HomePageModel(3,"Category",horizonalProductScrollModelList));
       homePageAdaptorList.add(new HomePageModel(1,R.mipmap.slider_banner,"#ffff00"));


        //here we will create and set adaptor
        HomePageAdaptor homePageAdaptor=new HomePageAdaptor(homePageAdaptorList);
        homePageRecycleView.setAdapter(homePageAdaptor);
        homePageAdaptor.notifyDataSetChanged();
       return view;

    }
    // Banner Slider


    public static class ProductImageAdaptor extends PagerAdapter {
        private List<Integer>productImages;

        public ProductImageAdaptor(List<Integer> productImages) {
            this.productImages = productImages;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView productImageView=new ImageView(container.getContext());
            productImageView.setImageResource(productImages.get(position));
            container.addView(productImageView,0);
            return productImageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView)object);
        }

        @Override
        public int getCount() {
            return productImages.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }
}