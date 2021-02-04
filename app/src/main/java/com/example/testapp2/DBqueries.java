package com.example.testapp2;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBqueries {
    public static List<CategoryModel> categoryModelList=new ArrayList<CategoryModel>();
    public static FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public static  List<HomePageModel> homePageModalList=new ArrayList<>();
    public  static void loadCategories(final CategoryAdaptor categoryAdaptor,final Context context)
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
    public static void loadFragmentDdata(final HomePageAdaptor homePageAdaptor,final Context context)
    {
        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
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
                                    homePageModalList.add(new HomePageModel(sliderModelList,0));

                                }
                                else if((long)documentSnapshot.get("viewType")==1)
                                {
                                    homePageModalList.add(new HomePageModel(1,documentSnapshot.get("strip_ad_banner").toString(),
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
                                        ,(boolean)documentSnapshot.get("COD_"+i)
                                        ,documentSnapshot.get("product_ID_"+i).toString()));


                                    }
                                    homePageModalList.add(new HomePageModel(2,documentSnapshot.get("layoutTitle").toString(),horizonalProductScrollModelList,documentSnapshot.get("layout_background").toString(),viewAllProductList));

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
                                    homePageModalList.add(new HomePageModel(3,documentSnapshot.get("layoutTitle").toString()
                                            ,gridProductList
                                            ,documentSnapshot.get("layout_background").toString()));



                                }
                                else if((long)documentSnapshot.get("viewType")==4)
                                {
                                  /// category grid
                                    homePageModalList.add(new HomePageModel(4,"","",categoryModelList));
                                }
                                else
                                {
                                    return;
                                }


                            }
                            homePageAdaptor.notifyDataSetChanged();

                        }
                        else
                        {
                            String error=task.getException().getMessage();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}
