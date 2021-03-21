package com.example.testapp2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBqueries {
    public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    //This list will store data for all the lists of homemodal type.
    // Video 54 (3)
    public static List< List<HomePageModel>>lists=new ArrayList<>();
    public static List<CategoryModel> categoryModelList=new ArrayList<>();
    public static List<String>loadedCategoriesNames=new ArrayList<>();
    public static List<String>localwishList=new ArrayList<>(); // this will store product id of those
    // items which are in MyWishList
    public static List<WishListModal>wishListModalList=new ArrayList<>();
    public static List<String>localCartList=new ArrayList<>();
    public static List<CartItemModal>cartItemModalList=new ArrayList<>();
    public  static List<AddressesModal>addressesModalList=new ArrayList<>();
    public static  int selectedAddress=-1;
    public  static void loadCategories(RecyclerView cateRecyclerView, final Context context)
    {
        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("Index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));

                            }
                            CategoryAdaptor categoryAdaptor=new CategoryAdaptor(categoryModelList);
                            cateRecyclerView.setAdapter(categoryAdaptor);
                            categoryAdaptor.notifyDataSetChanged();

                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public static void loadFragmentData(RecyclerView homePagerecyclerView, final Context context, int index, String categoryName)
    {
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP DEALS").orderBy("Index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                            {
                                if((long)documentSnapshot.get("viewType")==0)
                                {
                                    List<SliderModel>sliderModelList=new ArrayList<>();
                                    long noOfBaners=(long)documentSnapshot.get("numberOfBanners");
                                    for(long i=1;i<=noOfBaners;i++)
                                    {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner"+i).toString()
                                                ,documentSnapshot.get("banner"+i+"_background").toString()));

                                    }
                                    lists.get(index).add(new HomePageModel(sliderModelList,0));

                                }
                                else if((long)documentSnapshot.get("viewType")==1)
                                {
                                    lists.get(index).add(new HomePageModel(1,documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("strip_ad_background").toString()));

                                }
                                else if((long)documentSnapshot.get("viewType")==2)
                                {
                                    List<WishListModal>viewAllProductList=new ArrayList<>();
                                    List<HorizonalProductScrollModel>horizonalProductScrollModelList=new ArrayList<>();
                                    long noOfProducts=(long)documentSnapshot.get("no_of_products");
                                    for(long i=1;i<=noOfProducts;i++)
                                    {
                                        horizonalProductScrollModelList.add(new HorizonalProductScrollModel(documentSnapshot.get("product_ID_"+i).toString()
                                                ,documentSnapshot.get("product_image"+i).toString()
                                                ,documentSnapshot.get("product_title_"+i).toString()
                                                ,documentSnapshot.get("product_price_"+i).toString()
                                                ,documentSnapshot.get("product_subtitle_"+i).toString()));
                                        viewAllProductList.add(new WishListModal(documentSnapshot.get("product_image"+i).toString()
                                                ,documentSnapshot.get("product_full_title_"+i).toString()
                                                ,documentSnapshot.get("product_price_"+i).toString()
                                                ,documentSnapshot.get("cutted_price_"+i).toString(),"18%"
                                                ,(boolean)documentSnapshot.get("COD_"+i),documentSnapshot.get("product_ID_"+i).toString()));


                                    }
                                    lists.get(index).add(new HomePageModel(2,documentSnapshot.get("layoutTitle").toString(),horizonalProductScrollModelList,documentSnapshot.get("layout_background").toString(),viewAllProductList));

                                    // We will fetch list for  view all button (here we are using wishlist modal)

                                }
                                else if((long)documentSnapshot.get("viewType")==3)
                                {
                                    List<HorizonalProductScrollModel>gridProductList=new ArrayList<>();
                                    long noOfProducts=(long)documentSnapshot.get("no_of_products");
                                    for(long i=1;i<=noOfProducts;i++)
                                    {
                                        gridProductList.add(new HorizonalProductScrollModel(documentSnapshot.get("product_ID_"+i).toString()
                                                ,documentSnapshot.get("product_image"+i).toString()
                                                ,documentSnapshot.get("product_title_"+i).toString()
                                                ,documentSnapshot.get("product_price_"+i).toString()
                                                ,documentSnapshot.get("product_subtitle_"+i).toString()));

                                    }
                                    lists.get(index).add(new HomePageModel(3,documentSnapshot.get("layoutTitle").toString()
                                            ,gridProductList
                                            ,documentSnapshot.get("layout_background").toString()));



                                }
                                else if((long)documentSnapshot.get("viewType")==4)
                                {
                                    /// category grid
                                    lists.get(index).add(new HomePageModel(4,"","",categoryModelList));
                                }
                                else
                                {
                                    return;
                                }


                            }
                            HomePageAdaptor homePageAdaptor=new HomePageAdaptor(lists.get(index));
                            homePagerecyclerView.setAdapter(homePageAdaptor);
                            homePageAdaptor.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);

                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
    public static void loadWishList(final Context context,final Dialog loadingDialog, final boolean loadProductData)
    {
        localwishList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    for(long x=0;x<(long)task.getResult().get("wishlistSize");x++)
                    {
                        localwishList.add(task.getResult().get("product_ID_" + x).toString());
                        if(DBqueries.localwishList.contains(ProductDetailsActivity.localProductId))
                        {
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST=true;
                            if(ProductDetailsActivity.addToWishListBtn!=null)
                            {
                                ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            }


                        }
                        else
                        {
                            if(ProductDetailsActivity.addToWishListBtn!=null)
                            {
                                ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.grayLight));
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST=false;

                        }
                        if(loadProductData) {
                            wishListModalList.clear();
                            String productId=task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(task.getResult().get("product_ID_" + x).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        wishListModalList.add(new WishListModal(task.getResult().get("product_image1").toString()
                                                , task.getResult().get("product_title").toString()
                                                , task.getResult().get("product_price").toString()
                                                , task.getResult().get("cutted_price").toString(), "18% OFF"
                                                , (boolean) task.getResult().get("COD"),productId));
                                        MyWishlistFragment.wishlistAdaptor.notifyDataSetChanged();

                                    } else {
                                        String e = task.getException().getMessage();
                                        Toast.makeText(context, e, Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                        }
                    }

                }
                else
                {
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();

                }
                loadingDialog.dismiss();

            }

        });

    }
    // My Cart
    public static void loadCartList(final Context context,final boolean loadProductData,final Dialog loadingDialog,final TextView badgeCount,TextView cartTotalAmount)
    {
        localCartList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    for(long x=0;x<(long)task.getResult().get("cartlistSize");x++)
                    {
                        localCartList.add(task.getResult().get("product_ID_" + x).toString());
                        if(DBqueries.localCartList.contains(ProductDetailsActivity.localProductId))
                        {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART=true;

                        }
                        else
                        {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART=false;
                        }
                        if(loadProductData) {
                            cartItemModalList.clear();
                            String productId=task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(task.getResult().get("product_ID_" + x).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int index=0;
                                        if(localCartList.size()>=2)
                                        {
                                            index=localCartList.size()-2;
                                        }
                                        cartItemModalList.add(index,new CartItemModal(CartItemModal.CART_ITEM,task.getResult().get("product_image1").toString()
                                                , task.getResult().get("product_title").toString()
                                                , task.getResult().get("cutted_price").toString(),task.getResult().get("product_price").toString()
                                               , (long)1,(long)18,productId,(boolean)task.getResult().get("in_stock")));
                                        if(localCartList.size()==1)
                                        {
                                            cartItemModalList.add(new CartItemModal(CartItemModal.TOTAL_AMOUNT));
                                            LinearLayout continueBtnContainer=(LinearLayout)cartTotalAmount.getParent().getParent();
                                            continueBtnContainer.setVisibility(View.GONE);

                                        }
                                        if(localCartList.size()==0)
                                        {
                                            cartItemModalList.clear();
                                        }
                                        MyCartFragment.cartAdaptor.notifyDataSetChanged();

                                    } else {
                                        String e = task.getException().getMessage();
                                        Toast.makeText(context, e, Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                        }
                    }
                    if(localCartList.size()!=0)
                    {
                        badgeCount.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        badgeCount.setVisibility(View.INVISIBLE);
                    }
                    if(DBqueries.localCartList.size()<99 && DBqueries.localCartList.size()>0)
                    {
                        badgeCount.setText(String.valueOf(DBqueries.localCartList.size()));
                    }
                    else
                    {
                        badgeCount.setText("99");
                    }

                }
                else
                {
                    String error=task.getException().getMessage();
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();

                }
                loadingDialog.dismiss();

            }

        });


    }
    public static void loadAddresses(Context context,Dialog loaddingDialog)
    {
        addressesModalList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_ADDRESSES").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    Intent deliveryIntent;
                    if((long)task.getResult().get("addresslistSize")==0)
                    {
                        deliveryIntent=new Intent(context, AddAddressActivity.class);
                        deliveryIntent.putExtra("INTENT","deliveryIntent");
                    }
                    else
                    {
                        for(long x=1;x<(long)task.getResult().get("addresslistSize")+1;x++)
                        {
                            addressesModalList.add(new AddressesModal(task.getResult().get("fullname_"+x).toString()
                                    ,task.getResult().get("mobileNo_"+x).toString()
                                    ,task.getResult().get("address_"+x).toString(),(boolean)task.getResult().get("selected_"+x)));
                            if((boolean)task.getResult().get("selected_"+x))
                            {
                                selectedAddress=(int)x-1;
                            }
                        }
                        deliveryIntent=new Intent(context, DeliveryActivity.class);
                    }
                    context.startActivity(deliveryIntent);
                    ((Activity) context).finish();
                    loaddingDialog.dismiss();





                }
                else
                {
                    String e2=task.getException().getMessage();
                    Toast.makeText(context,e2,Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    public  static void removeWishList(final int index,final Context context)
    {
        int I=(int)index;
        String removedProductId=localwishList.get(I);
        localwishList.remove(I);

        HashMap<String,Object> updateWishList = new HashMap<>();
        for (int x=0;x<localwishList.size();x++)
        {
            updateWishList.put("product_ID_"+x,localwishList.get(x));
        }
        updateWishList.put("wishlistSize",(long)localwishList.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_WISHLIST").set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    if(wishListModalList.size() !=0)
                    {
                        wishListModalList.remove(I);
                        MyWishlistFragment.wishlistAdaptor.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST=false;
                    Toast.makeText(context,"Removed Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(ProductDetailsActivity.addToWishListBtn!=null)
                    {
                        ProductDetailsActivity.addToWishListBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                    }
                    localwishList.add(removedProductId);
                    String e2=task.getException().getMessage();
                    Toast.makeText(context,e2,Toast.LENGTH_SHORT).show();
                }

            }

        });
        ProductDetailsActivity.running_wishlist_querry=false;

    }
    public static void removeCartList(final int index,final  Context context, final TextView cartTotalAmount)
    {
        int I=(int)index;
        String removedProductId=localCartList.get(I);
        localCartList.remove(I);
        HashMap<String,Object> updateCartList = new HashMap<>();
        for (int x=0;x<localCartList.size();x++)
        {
            updateCartList.put("product_ID_"+x,localCartList.get(x));
        }
        updateCartList.put("cartlistSize",(long)localCartList.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                .document("MY_CART").set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    if(cartItemModalList.size() !=0)
                    {
                        cartItemModalList.remove(I);
                        MyCartFragment.cartAdaptor.notifyDataSetChanged();
                    }
                    if(localCartList.size()==0)
                    {
                        cartItemModalList.clear();
                        LinearLayout continueBtnContainer=(LinearLayout)cartTotalAmount.getParent().getParent();
                        continueBtnContainer.setVisibility(View.GONE);

                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_CART=false;
                    Toast.makeText(context,"Removed Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    localwishList.add(removedProductId);
                    String e2=task.getException().getMessage();
                    Toast.makeText(context,e2,Toast.LENGTH_SHORT).show();
                }

                ProductDetailsActivity.running_cart_querry=false;
                MyCartFragment.loadingDialog.dismiss();
            }
        });


    }

    public static void clearData()
    {
        categoryModelList.clear();
        lists.clear();
        addressesModalList.clear();
        loadedCategoriesNames.clear();
        localwishList.clear();
        localCartList.clear();
        wishListModalList.clear();
        cartItemModalList.clear();

    }

}
