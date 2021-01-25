package com.example.testapp2;
/// hERE WE WILL DECLARE THOSE VARIABLE WHICH WILL ACCESSED THROUGH FIREBASE
// REST VARIABLE WE WILL ACCESS FROM HOME FRAGMENT SINCE WE ALREADY HAVE CREATED ADAPTOR FOR RESPECTIVE VIEWS


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdaptor extends RecyclerView.Adapter
{
    private List<HomePageModel> homePageModelList;

    public HomePageAdaptor(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        {// This is to inflate layout
            switch (viewType)
            {
                case HomePageModel.bannerSlider:
                    View bannerSliderView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ad_layout,viewGroup,false);
                    return new BannerSliderViewHolder( bannerSliderView);
                case HomePageModel.STRIP_ADD_BANNER:
                    View stripAdView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_add_layout,viewGroup,false);
                    return new StripAdBannerViewHolder(stripAdView);
               case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                 View horizontalProductView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout,viewGroup,false);
                   return new HorizontalProductViewHolder(horizontalProductView);
              case HomePageModel.GRID_PRODUCT_VIEW:
                    View gridProductView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout,viewGroup,false);
                    return new GridProductViewHolder(gridProductView);// we are using same constructor for both grid and horizontal

                default:
                    return null;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        //Here we do data binding here//
        // we will collect data and pass it to their corrosponding viewHolders ///video19
        switch (homePageModelList.get(position).getType())
        {
            case HomePageModel.bannerSlider :
                List<SliderModel>sliderModelList=homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder)viewHolder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_ADD_BANNER :
                    int resource =homePageModelList.get(position).getResource();
                    String color=homePageModelList.get(position).getBackgroundcolor();
                ((StripAdBannerViewHolder)viewHolder).setStripAd(resource,color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW :
                String titleHorizontalView=homePageModelList.get(position).getTitle();
                List<HorizonalProductScrollModel>horizonalProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder)viewHolder).setHorizontalProductLayout(horizonalProductScrollModelList,titleHorizontalView);
                break;
                case HomePageModel.GRID_PRODUCT_VIEW:
                    String titleGridView=homePageModelList.get(position).getTitle();
                    List<HorizonalProductScrollModel>gridProductScrollModelList=homePageModelList.get(position).getHorizontalProductScrollModelList();
                    ((GridProductViewHolder)viewHolder).setGridLayout(gridProductScrollModelList,titleGridView);
                    break;
            default:
                return;
        }

    }

    @Override
    public int getItemViewType(int position) {
       // int t=homePageModelList.get(position).getType();
        switch(homePageModelList.get(position).getType())
        {
            case 0:
                return HomePageModel.bannerSlider; //video19
            case 1:
                return HomePageModel.STRIP_ADD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
             case 3:
                 return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }


    public class BannerSliderViewHolder extends RecyclerView.ViewHolder
    {

        private int currentPage=2;
        private Timer timer;
        final private long DELAY_TIME=3000;
        final private long PERIOD_TIME=3000;
        ///// banner slider video15
        private ViewPager bannerSliderViewPager;
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager=itemView.findViewById(R.id.banner_slider_viewpager);


         }
        // Banner Slider
        private void setBannerSliderViewPager(List<SliderModel>sliderModelList)
        {
            SliderAdaptor sliderAdaptor=new SliderAdaptor(sliderModelList);
            bannerSliderViewPager.setAdapter(sliderAdaptor);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            bannerSliderViewPager.setCurrentItem(currentPage);//setting banner v16
            ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position)
                {
                    currentPage=position;

                }

                @Override
                public void onPageScrollStateChanged(int state)
                {
                    if(state==ViewPager.SCROLL_STATE_IDLE)
                    {
                        PageLooper(sliderModelList);
                    }

                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            StartBannerSlideShow(sliderModelList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    PageLooper(sliderModelList);
                    StopSlideShow();
                    if(event.getAction()==MotionEvent.ACTION_UP)
                    {
                        StartBannerSlideShow(sliderModelList);
                    }
                    return false;

                }
            });

        }
        private  void PageLooper(List<SliderModel>sliderModelList)
        {
            if(currentPage==sliderModelList.size()-2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
            if(currentPage==1)
            {
                currentPage = sliderModelList.size()-3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }



        }
        private void StartBannerSlideShow(final List<SliderModel>sliderModelList)
        {
            Handler handler=new Handler();
            Runnable update=new Runnable() {
                @Override
                public void run() {
                    if(currentPage>=sliderModelList.size())
                    {
                        currentPage=1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++,true);
                }
            };
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);

                }
            },DELAY_TIME,PERIOD_TIME);

        }
        private void StopSlideShow()
        {
            timer.cancel();
        }


    }
    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;
//Here we access all views like ImageVew ,Contraint Layout
        public StripAdBannerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            stripAdImage=itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer=itemView.findViewById(R.id.strip_ad_container);


        }
        public void setStripAd(int resource,String color)
        {
            stripAdImage.setImageResource(resource);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }


    }
    public class HorizontalProductViewHolder extends  RecyclerView.ViewHolder{
        ////Horizontal Product Layout
        public TextView horizontalLayoutTitle;
        private Button horizontalLayoutViewAllBtn;
        private RecyclerView horizontalRecycleView;
        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            //here we have accessed all views and now we will set data
            horizontalLayoutTitle=(TextView)itemView.findViewById(R.id.horizontal_product_title);
            horizontalLayoutViewAllBtn=itemView.findViewById(R.id.horizonal_layout_view_all_btn);
            horizontalRecycleView=itemView.findViewById(R.id.horizonal_layout_recycle_view);

        }
        public void setHorizontalProductLayout(List<HorizonalProductScrollModel>horizonalProductScrollModelList,String title)
        {

           // horizontalLayoutTitle.setText(title);
            if(horizonalProductScrollModelList.size()>8)
            {
                horizontalLayoutViewAllBtn.setVisibility(View.INVISIBLE);
            }else
            {
                horizontalLayoutViewAllBtn.setVisibility(View.VISIBLE);
            }


            HorizonalProductScrollAdaptor horizonalProductScrollAdaptor=new HorizonalProductScrollAdaptor(horizonalProductScrollModelList);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecycleView.setLayoutManager(linearLayoutManager);
            horizontalRecycleView.setAdapter(horizonalProductScrollAdaptor);
            horizonalProductScrollAdaptor.notifyDataSetChanged();
            // grid Product Layout View

        }
    }
    // grid view
    public class GridProductViewHolder extends RecyclerView.ViewHolder
    {
        TextView gridLayoutTitle;
        Button gridLayoutViewBtn;
        GridView gridView;
        //
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewBtn=itemView.findViewById(R.id.grid_product_layout_viewall_btn);
            gridView=itemView.findViewById(R.id.grid_product_layout_gridview);
        }
        public void setGridLayout(List<HorizonalProductScrollModel>horizonalProductScrollModelList,String title)
        {
            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdaptor(horizonalProductScrollModelList));//here list is passed for testing
        }
    }
}
