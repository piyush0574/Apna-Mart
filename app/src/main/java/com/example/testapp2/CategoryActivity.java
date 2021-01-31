package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
//video21

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //video13
        String title=getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        categoryRecyclerView=findViewById(R.id.category_recycleview);
        List<SliderModel> sliderModelList=new ArrayList<SliderModel>();
        List<HorizonalProductScrollModel>horizonalProductScrollModelList=new ArrayList<>();
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.drawable.fruits,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.my_rewards,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5A","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));
        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(R.mipmap.ic_phone_iphone_24px,"RedMi 5wA","SD625","Rs.266"));


        LinearLayoutManager homePageRecycleViewLayoutManager=new LinearLayoutManager(this);
        homePageRecycleViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(homePageRecycleViewLayoutManager);

        List<HomePageModel> homePageAdaptorList=new ArrayList<>();
        // Here we are showing all our view with numbering index with different type of contructors
     //   homePageAdaptorList.add(new HomePageModel(sliderModelList,0));
        homePageAdaptorList.add(new HomePageModel(1,R.mipmap.slider_banner,"#000000"));
        homePageAdaptorList.add(new HomePageModel(2,"Deals of the Day",horizonalProductScrollModelList));
        homePageAdaptorList.add(new HomePageModel(3,"Deals of the Day",horizonalProductScrollModelList));
        homePageAdaptorList.add(new HomePageModel(1,R.mipmap.slider_banner_ex,"#ff0000"));
        homePageAdaptorList.add(new HomePageModel(2,"Deals of the Day3",horizonalProductScrollModelList));
        homePageAdaptorList.add(new HomePageModel(3,"Category",horizonalProductScrollModelList));
        homePageAdaptorList.add(new HomePageModel(1,R.mipmap.slider_banner,"#ffff00"));


        //here we will create and set adaptor
        HomePageAdaptor homePageAdaptor=new HomePageAdaptor(homePageAdaptorList);
        categoryRecyclerView.setAdapter(homePageAdaptor);
        homePageAdaptor.notifyDataSetChanged();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;  adds items to the action bar if it is present.
        // here seach_icon_menu will be added action bar
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.main_search_icon)
        // for search icon
        {
            return  true;
        }
        else if(id==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}