package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.testapp2.HomeScreenActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity
{
    private ViewPager productImageViewPager;
    private TabLayout productImageViewPagerIndicator;
    private FloatingActionButton addToWishListBtn;
    private static boolean ALREADY_ADDED_TO_WISHLIST=false;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailTabbedLayout;
    private Button buyNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //video13
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productImageViewPager=findViewById(R.id.product_images_view_pager) ;
        addToWishListBtn=findViewById(R.id.add_to_wishlist_btn);
        productImageViewPagerIndicator=findViewById(R.id.view_pager_indicator);
        // connecting both
        productImageViewPagerIndicator.setupWithViewPager(productImageViewPager,true);

        productDetailsViewPager=findViewById(R.id.product_details_view_pager);
        productDetailTabbedLayout=findViewById(R.id.product_details_tab_layout);
        buyNowBtn=findViewById(R.id.buy_now_btn);

        // setting fragment for product section

        productDetailsViewPager.setAdapter(new ProductDetailsAdaptor(getSupportFragmentManager(),productDetailTabbedLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailTabbedLayout));
        productDetailTabbedLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        List<Integer>productImages=new ArrayList<>();
        productImages.add(R.drawable.slider_banner_ex);
        productImages.add(R.drawable.slider_banner_ex);
        productImages.add(R.drawable.slider_banner_ex);
        productImages.add(R.mipmap.img_phone_4);
        HomeFragment.ProductImageAdaptor productImageAdaptor=new HomeFragment.ProductImageAdaptor(productImages);
        productImageViewPager.setAdapter(productImageAdaptor);

        // setting onclick listner to wishlistbtn
        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ALREADY_ADDED_TO_WISHLIST)
                {
                    ALREADY_ADDED_TO_WISHLIST=false;
                    addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));

                }

                else
                {
                    addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                    ALREADY_ADDED_TO_WISHLIST=true;

                }

            }

        });
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent=new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                startActivity(deliveryIntent);

            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }
// setting menu in action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.main_search_icon)
        // for search icon
        {
            return  true;
        }

        else if(id==R.id.main_cart_icon)
        {
            Intent cartActivity=new Intent(ProductDetailsActivity.this,HomeScreenActivity.class);
            showCart=true;
            // here control is shifted from this to homeScreenActivity (Since my cart is part(fragment) of home screen
            // with the help of showCart we will not load the default fragment of home screen i.e. HomeFragment
            //Instead we will load MyCartFragment using if else
            startActivity(cartActivity);
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