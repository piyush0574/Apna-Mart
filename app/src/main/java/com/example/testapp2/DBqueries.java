package com.example.testapp2;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public static List<String>localwishList=new ArrayList<>();
    public static List<WishListModal>wishListModalList=new ArrayList<>();
    public  static void loadCategories(RecyclerView cateRecyclerView, final Context context)
    {
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
    public  static void removeWishList(Integer index,final Context context)
    {
        int i=(int)index;
        localwishList.remove(i);
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
                        wishListModalList.remove(i);
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

                    String e2=task.getException().getMessage();
                    Toast.makeText(context,e2,Toast.LENGTH_SHORT).show();
                }
                if(ProductDetailsActivity.addToWishListBtn!=null)
                {
                    ProductDetailsActivity.addToWishListBtn.setEnabled(true);
                }

            }
        });

    }
    public static void clearData()
    {
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        localwishList.clear();
        wishListModalList.clear();

    }

}
