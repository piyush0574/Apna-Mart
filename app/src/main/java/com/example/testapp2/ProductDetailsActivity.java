package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static com.example.testapp2.HomeScreenActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity
{
    public  static MenuItem cartItem;
    private ViewPager productImageViewPager;
    private TabLayout productImageViewPagerIndicator;
    public static FloatingActionButton addToWishListBtn;
    public static boolean ALREADY_ADDED_TO_WISHLIST=false;
    public static boolean ALREADY_ADDED_TO_CART=false;
    private TextView buyNowBtn;
    private FirebaseFirestore firebaseFirestore;
    //product description
    private TextView productTitle,discountedPrice,cuttedPrice,codYesText,codNoText;
    private TextView savedAmountTV,percentDiscountTV;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailTabbedLayout;
    private ConstraintLayout productDetailsOnlyContanier;
    private ConstraintLayout tabbedProductDetailsContainer;
    private TextView productOnlyDescBody;
    private TextView addToCart;
    public  static boolean running_wishlist_querry=false;
    public  static boolean running_cart_querry=false;
    private FirebaseUser currentUser;
    public static List<ProductSpecificationModal>productSpecificationModalList=new ArrayList<>();
    public static String localProductId;
    private  DocumentSnapshot documentSnapshot;
    private Dialog loadingDialog;
    private TextView badgeCount;
    // variable to track event time
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //video13
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // product image viewpager
        productImageViewPager=findViewById(R.id.product_images_view_pager) ;
        addToWishListBtn=findViewById(R.id.add_to_wishlist_btn);
        productImageViewPagerIndicator=findViewById(R.id.view_pager_indicator);
        // connecting both
        productImageViewPagerIndicator.setupWithViewPager(productImageViewPager,true);

        productDetailsViewPager=findViewById(R.id.product_details_view_pager);
        productDetailTabbedLayout=findViewById(R.id.product_details_tab_layout);
        productDetailsOnlyContanier=findViewById(R.id.product_details_container);
        tabbedProductDetailsContainer=findViewById(R.id.product_details_tabs_conatiner);
        productOnlyDescBody=findViewById(R.id.product_details_body_tv);
        addToCart=findViewById(R.id.add_to_cart_tv);

        buyNowBtn=findViewById(R.id.buy_now_btn);
        productTitle=findViewById(R.id.product_title);
        cuttedPrice=findViewById(R.id.cutted_original_price_tv);
        discountedPrice=findViewById(R.id.selling_price_tv);
        codYesText=findViewById(R.id.cod_available_tv);
        codNoText=findViewById(R.id.cod_not_available_tv);
        savedAmountTV=findViewById(R.id.discounted_amount_product_image);
        percentDiscountTV=findViewById(R.id.discount_percent_tv_product_image);
        currentUser=FirebaseAuth.getInstance().getCurrentUser();

        // loading dialog
        loadingDialog=new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progess_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        //

        firebaseFirestore=FirebaseFirestore.getInstance();
        List<String>productImages=new ArrayList<>();
        localProductId=getIntent().getStringExtra("productID");
        firebaseFirestore.collection("PRODUCTS").document(getIntent().getStringExtra("productID")).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            documentSnapshot=task.getResult();
                            for(long x=1;x<(long)documentSnapshot.get("no_of_product_images")+1;x++)
                            {
                                productImages.add(documentSnapshot.get("product_image"+x).toString());

                            }
                            // Product Images
                            ProductImagesAdaptor productImageAdaptor=new ProductImagesAdaptor(productImages);
                            productImageViewPager.setAdapter(productImageAdaptor);
                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            discountedPrice.setText("Rs. "+(documentSnapshot.get("product_price").toString()));
                            cuttedPrice.setText((documentSnapshot.get("cutted_price").toString()));
                            String SP=documentSnapshot.get("product_price").toString();
                            String CP=documentSnapshot.get("cutted_price").toString();

                            savedAmountTV.setText(PriceCalculation.discountAmount(CP,SP));
                            percentDiscountTV.setText(PriceCalculation.discountPercentage(CP,SP)+"% OFF");
                            boolean isCOD=(boolean)documentSnapshot.get("COD");
                            if(isCOD)
                            {
                                codYesText.setVisibility(View.VISIBLE);
                                codNoText.setVisibility(View.GONE);

                            }
                            else
                            {
                                codYesText.setVisibility(View.GONE);
                                codNoText.setVisibility(View.VISIBLE);

                            }
                            // code for removing tabbed layout for product description
                            if((boolean)documentSnapshot.get("use_tabbed_layout"))
                            {
                                tabbedProductDetailsContainer.setVisibility(View.VISIBLE);
                                productDetailsOnlyContanier.setVisibility(View.GONE);
                                ProductDespcriptionFragment.productDescriptionData=documentSnapshot
                                        .get("product_description").toString();
                                // creating list for product product specification fragment
                                long total_no_of_spec_title=(long)documentSnapshot.get("total_spec_titles");
                                for(long i=1;i<total_no_of_spec_title+1;i++)
                                {
                                    productSpecificationModalList.add(new ProductSpecificationModal(
                                            documentSnapshot.get("spec_title_"+i).toString()));
                                    long totalNoOfSpecs=(long)documentSnapshot.get("spec_title_"+i+"_total_fields");
                                    for(long x=1;x<totalNoOfSpecs+1;x++)
                                    {
                                        productSpecificationModalList.add(new ProductSpecificationModal(
                                                documentSnapshot.get("spec_title_"+i+"_field_"+x+"_name").toString()
                                                ,documentSnapshot.get("spec_title_"+i+"_field_"+x+"_value").toString()));


                                    }
                                }


                            }
                            else
                            {
                                tabbedProductDetailsContainer.setVisibility(View.GONE);
                                productDetailsOnlyContanier.setVisibility(View.VISIBLE);
                                productOnlyDescBody.setText(documentSnapshot.get("product_details").toString());
                            }
                            productDetailsViewPager.setAdapter(new ProductDetailsAdaptor(getSupportFragmentManager(),productDetailTabbedLayout.getTabCount()));

                            // wishlist
                            if(currentUser!=null)
                            {
                                if(DBqueries.localwishList.size()==0)
                                {
                                    DBqueries.loadWishList(ProductDetailsActivity.this,loadingDialog,false);
                                }
                                if(DBqueries.localCartList.size()==0)
                                {
                                    DBqueries.loadCartList(ProductDetailsActivity.this,false,loadingDialog,badgeCount);
                                }
                                else
                                {
                                    loadingDialog.dismiss();
                                }

                            }
                            else
                            {
                                loadingDialog.dismiss();
                            }
                            //cart
                            if(DBqueries.localCartList.contains(localProductId))
                            {
                                ALREADY_ADDED_TO_CART=true;
                            }
                            else
                            {
                                ALREADY_ADDED_TO_CART=false;
                            }
                            //cart
                            //wish
                            if(DBqueries.localwishList.contains(localProductId))
                            {
                                ALREADY_ADDED_TO_WISHLIST=true;
                                addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            }
                            else
                            {
                                ALREADY_ADDED_TO_WISHLIST=false;
                                addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.grayLight));
                            }
                            //wish
                        }
                        else
                        {

                            loadingDialog.dismiss();
                            String error=task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this,error,Toast.LENGTH_SHORT).show();
                        }


                    }
                });



        // setting fragment for product section

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
        // setting onclick listner to wishlistbtn
        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null)
                {
                    TwoOptionDialogBox.twoOptionDialogBoxShow(ProductDetailsActivity.this,"Sign In","Sign Up!");

                }else{
                    // Preventing multiple clicks, using threshold of 500 mili second
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                       return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if(!running_wishlist_querry)
                    {
                        running_wishlist_querry=true; // querry is running
                        if(ALREADY_ADDED_TO_WISHLIST)
                        {
                            DBqueries.removeWishList(DBqueries.localwishList.indexOf(localProductId),ProductDetailsActivity.this);
                            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.grayLight));
                        }
                        else{
                            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            HashMap<String,Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_"+String.valueOf(DBqueries.localwishList.size()),localProductId);
                            addProduct.put("wishlistSize", (long) (DBqueries.localwishList.size() + 1));
                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST").update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        if(DBqueries.wishListModalList.size()!=0)
                                        {
                                            DBqueries.wishListModalList.add(new WishListModal(documentSnapshot.get("product_image1").toString()
                                                    ,documentSnapshot.get("product_title").toString()
                                                    ,documentSnapshot.get("product_price").toString()
                                                    ,documentSnapshot.get("cutted_price").toString(),"18%"
                                                    ,(boolean)documentSnapshot.get("COD"),localProductId));
                                        }

                                        addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                        ALREADY_ADDED_TO_WISHLIST=true;
                                        DBqueries.localwishList.add(localProductId);
                                        Toast.makeText(ProductDetailsActivity.this,"Product is added to My WishLists",Toast.LENGTH_SHORT).show();
                                        running_wishlist_querry=false;


                                    }
                                    else
                                    {
                                        running_wishlist_querry=false;
                                        String error=task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this,error,Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }

                    }
                    else
                    {
                        return;
                    }

                }


            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser==null)
                {
                    TwoOptionDialogBox.twoOptionDialogBoxShow(ProductDetailsActivity.this,"Sign In","Sign Up!");

                }else{
                    if(!running_cart_querry)
                    {
                        running_cart_querry=true; // querry is running
                        if(ALREADY_ADDED_TO_CART)
                        {
                            running_cart_querry=false;
                           Toast.makeText(ProductDetailsActivity.this,"This product is already in your cart",Toast.LENGTH_SHORT).show();
                           return;
                        }
                        else{
                            HashMap<String,Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_"+String.valueOf(DBqueries.localCartList.size()),localProductId);
                            addProduct.put("cartlistSize", (long) (DBqueries.localCartList.size() + 1));
                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART").update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        if(DBqueries.cartItemModalList.size()!=0)
                                        { DBqueries.cartItemModalList.add(new CartItemModal(CartItemModal.CART_ITEM,documentSnapshot.get("product_image1").toString()
                                                , documentSnapshot.get("product_title").toString()
                                                , documentSnapshot.get("cutted_price").toString(),documentSnapshot.get("product_price").toString()
                                                , (long)1,(long)18,localProductId));
                                        }

                                        ALREADY_ADDED_TO_CART=true;
                                        DBqueries.localCartList.add(localProductId);
                                        Toast.makeText(ProductDetailsActivity.this,"Product is added to My Cart",Toast.LENGTH_SHORT).show();
                                        invalidateOptionsMenu(); // refresh action bar
                                        running_cart_querry=false;



                                    }
                                    else
                                    {
                                        String error=task.getException().getMessage();
                                        running_cart_querry=false;
                                        Toast.makeText(ProductDetailsActivity.this,error,Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                        }

                    }
                    else
                        return;
                }


            }
        });



        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser!=null)
                {
                    Intent deliveryIntent=new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
                else
                {
                    TwoOptionDialogBox.twoOptionDialogBoxShow(ProductDetailsActivity.this,"Sign In","Sign Up!");
                }


            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        // setting badge count of cart
          cartItem=menu.findItem(R.id.main_cart_icon);
          cartItem.setActionView(R.layout.badge_layout);
          ImageView badgeIcon=cartItem.getActionView().findViewById(R.id.badge_icon);
          badgeIcon.setImageResource(R.mipmap.cart_64);
          badgeCount=cartItem.getActionView().findViewById(R.id.badge_count);
        if(currentUser!=null)
        {
            if(DBqueries.localCartList.size()==0)
            {
                DBqueries.loadCartList(ProductDetailsActivity.this,false,loadingDialog,badgeCount);
            }
            else
            {
                badgeCount.setVisibility(View.VISIBLE);
                if(DBqueries.localCartList.size()<99)
                {
                    badgeCount.setText(String.valueOf(DBqueries.localCartList.size()));
                }
                else
                {
                    badgeCount.setText("99");
                }
            }

        }
          cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentUser!=null)
                    {
                        Intent cartActivity=new Intent(ProductDetailsActivity.this,HomeScreenActivity.class);
                        showCart=true;
                        // here control is shifted from this to homeScreenActivity (Since my cart is part(fragment) of home screen
                        // with the help of showCart we will not load the default fragment of home screen i.e. HomeFragment
                        //Instead we will load MyCartFragment using if else
                        startActivity(cartActivity);
                    }
                    else
                    {
                        TwoOptionDialogBox.twoOptionDialogBoxShow(ProductDetailsActivity.this,"Sign In","Sign Up!");
                    }

                }
            });
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            if(DBqueries.localwishList.size()==0)
            {
                DBqueries.loadWishList(ProductDetailsActivity.this,loadingDialog,false);
            }
            else
            {
                loadingDialog.dismiss();
            }

        }
        else
        {
            loadingDialog.dismiss();
        }
        if(DBqueries.localwishList.contains(localProductId))
        {
            ALREADY_ADDED_TO_WISHLIST=true;
            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
        }
        else
        {
            addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.grayLight));
            ALREADY_ADDED_TO_WISHLIST=false;
        }

        //cart
        if(DBqueries.localCartList.contains(localProductId))
        {
            ALREADY_ADDED_TO_CART=true;
        }
        else
        {
            ALREADY_ADDED_TO_CART=false;
        }
        //cart
        invalidateOptionsMenu();

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

            if(currentUser!=null)
            {
                Intent cartActivity=new Intent(ProductDetailsActivity.this,HomeScreenActivity.class);
                showCart=true;
                // here control is shifted from this to homeScreenActivity (Since my cart is part(fragment) of home screen
                // with the help of showCart we will not load the default fragment of home screen i.e. HomeFragment
                //Instead we will load MyCartFragment using if else
                startActivity(cartActivity);
                return  true;
            }
            else
            {
                TwoOptionDialogBox.twoOptionDialogBoxShow(ProductDetailsActivity.this,"Sign In","Sign Up!");
            }



        }
        else if(id==android.R.id.home)
        {
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);

    }
}