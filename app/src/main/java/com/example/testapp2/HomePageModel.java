package com.example.testapp2;

import java.util.List;

public class HomePageModel  {
    private int type;
    public static final int bannerSlider=0;
    public static final int STRIP_ADD_BANNER=1;
    public static final int HORIZONTAL_PRODUCT_VIEW=2;
    public static final int GRID_PRODUCT_VIEW=3;
    public static final int CATEGORY_GRID_VIEW=4;
    private String backgroundcolor;

  // This is will be bannner slider

    private List<SliderModel> sliderModelList;// this list will accessed from db

    public HomePageModel(List<SliderModel> sliderModelList, int type) {
        this.sliderModelList = sliderModelList;
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    // This is will be bannner slider
    //start of strip ad
    private String resource;

    public HomePageModel(int type, String resource, String backgroundcolor) {
        this.type = type;
        this.resource = resource;
        this.backgroundcolor = backgroundcolor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
    // strip ad banner end
    // Grid Product
    private String title;
    private List<HorizonalProductScrollModel> horizontalProductScrollModelList;
    public HomePageModel(int type, String title, List<HorizonalProductScrollModel> horizontalProductScrollModelList, String backgroundcolor) {
        this.type = type;
        this.title = title;
        this.backgroundcolor=backgroundcolor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizonalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }

    public void setHorizontalProductScrollModelList(List<HorizonalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    // Grid Product

    // horizontal product
    private List<WishListModal> viewAllProductList; //  this list will be passed to view all activity
    // when a user clicks on view all button
    public HomePageModel(int type, String title, List<HorizonalProductScrollModel> horizontalProductScrollModelList,String backgroundcolor,List<WishListModal>viewAllList) {
        this.type = type;
        this.title = title;
        this.backgroundcolor=backgroundcolor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProductList=viewAllList;
    }

    public List<WishListModal> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishListModal> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }
    // horizontal product

    // Loading category in grid layout
    private List<CategoryModel>categoryModelList;

    public HomePageModel(int type, String backgroundcolor, String title, List<CategoryModel> categoryModelList) {
        this.type = type;
        this.backgroundcolor = backgroundcolor;
        this.title = title;
        this.categoryModelList = categoryModelList;
    }

    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }
}
