package com.example.testapp2;

import java.util.List;

public class HomePageModel  {
    private int type;
    public static final int bannerSlider=0;
    public static final int STRIP_ADD_BANNER=1;
    public static final int HORIZONTAL_PRODUCT_VIEW=2;
    public static final int GRID_PRODUCT_VIEW=3;

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
    private String backgroundcolor;

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
    // horizontal product layout And Grid Product
    private String title;
    private List<HorizonalProductScrollModel> horizontalProductScrollModelList;

    public HomePageModel(int type, String title, List<HorizonalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
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
    // horizontal product layout
    //

}
