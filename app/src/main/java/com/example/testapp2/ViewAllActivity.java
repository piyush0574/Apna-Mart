package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridView gridView;
    public static List<HorizonalProductScrollModel>horizonalProductScrollModelList;
    public static List<WishListModal>wishListModalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //video13
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        int layoutcode=getIntent().getIntExtra("LAYOUTCODE",-1);
        if(layoutcode==0)
        {
            recyclerView=findViewById(R.id.recycler_view);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayout=new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayout);
            // we are using wishlist adaptor to create the list

            WishlistAdaptor wishlistAdaptor=new WishlistAdaptor(wishListModalList,false);
            recyclerView.setAdapter(wishlistAdaptor);
            wishlistAdaptor.notifyDataSetChanged();
        }
        else if(layoutcode==1)
        {

            gridView=findViewById(R.id.grid_view);
            gridView.setVisibility(View.VISIBLE);
             GridProductLayoutAdaptor gridProductLayoutAdaptor=new GridProductLayoutAdaptor(horizonalProductScrollModelList);
            gridView.setAdapter(gridProductLayoutAdaptor);
            gridProductLayoutAdaptor.notifyDataSetChanged();


        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)

        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}