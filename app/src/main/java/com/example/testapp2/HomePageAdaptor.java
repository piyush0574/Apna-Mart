package com.example.testapp2;
/// hERE WE WILL DECLARE THOSE VARIABLE WHICH WILL ACCESSED THROUGH FIREBASE
// REST VARIABLE WE WILL ACCESS FROM HOME FRAGMENT SINCE WE ALREADY HAVE CREATED ADAPTOR FOR RESPECTIVE VIEWS


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdaptor extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPosition=-1;

    public HomePageAdaptor(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // This is to inflate layout
            switch (viewType) {
                case HomePageModel.bannerSlider:
                    View bannerSliderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ad_layout, viewGroup, false);
                    return new BannerSliderViewHolder(bannerSliderView);
                case HomePageModel.STRIP_ADD_BANNER:
                    View stripAdView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_add_layout, viewGroup, false);
                    return new StripAdBannerViewHolder(stripAdView);
                case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                    View horizontalProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout, viewGroup, false);
                    return new HorizontalProductViewHolder(horizontalProductView);
                case HomePageModel.GRID_PRODUCT_VIEW:
                    View gridProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup, false);
                    return new GridProductViewHolder(gridProductView);// we are using same constructor for both grid and horizontal
                case HomePageModel.CATEGORY_GRID_VIEW:
                    View categorygridView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_view_category, viewGroup, false);
                    return new CateGridProductViewHolder(categorygridView);// we are using same co

                default:
                    return null;
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        //Here we do data binding here//
        // we will collect data and pass it to their corrosponding viewHolders ///video19
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.bannerSlider:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder) viewHolder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_ADD_BANNER:
                String resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundcolor();
                ((StripAdBannerViewHolder) viewHolder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String titleHorizontalView = homePageModelList.get(position).getTitle();
                String horizontallayoutColor = homePageModelList.get(position).getBackgroundcolor();
                List<HorizonalProductScrollModel> horizonalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                List<WishListModal>viewAllProductList=homePageModelList.get(position).getViewAllProductList();
                ((HorizontalProductViewHolder) viewHolder).setHorizontalProductLayout(horizonalProductScrollModelList, titleHorizontalView, horizontallayoutColor,viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String titleGridView = homePageModelList.get(position).getTitle();
                String gridlayoutColor = homePageModelList.get(position).getBackgroundcolor();
                List<HorizonalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) viewHolder).setGridLayout(gridProductScrollModelList, titleGridView, gridlayoutColor);
                break;
            case HomePageModel.CATEGORY_GRID_VIEW:
                String titleCategoryGridView = "SHOP BY CATEGORY";
                String cateGridlayoutColor = "#FFEFD5";
                List<CategoryModel> categoryModelList = homePageModelList.get(position).getCategoryModelList();
                ((CateGridProductViewHolder) viewHolder).setCategoryGridLayout(categoryModelList, titleCategoryGridView, cateGridlayoutColor);
                break;
            default:
                return;
        }
        if(lastPosition<position)
        {
            Animation animation=AnimationUtils.loadAnimation(viewHolder.itemView.getContext(),R.anim.fade_in_anim);
            viewHolder.itemView.setAnimation(animation);
            lastPosition=position;
        }



    }


    @Override
    public int getItemViewType(int position) {
        // int t=homePageModelList.get(position).getType();
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.bannerSlider; //video19
            case 1:
                return HomePageModel.STRIP_ADD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            case 4:
                return HomePageModel.CATEGORY_GRID_VIEW;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }


    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private int currentPage;
        private Timer timer;
        final private long DELAY_TIME = 1000;
        final private long PERIOD_TIME = 1000;
        private List<SliderModel> arrangedList;
        ///// banner slider video15
        private ViewPager bannerSliderViewPager;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_viewpager);


        }

        // Banner Slider
        private void setBannerSliderViewPager(List<SliderModel> sliderModelList) {
            currentPage = 2;
            if (timer != null) {
                timer.cancel();
            }
            arrangedList = new ArrayList<>();
            // setting infinite viewpager
            for (int x = 0; x < sliderModelList.size(); x++) {
                arrangedList.add(x, sliderModelList.get(x));
            }
            arrangedList.add(0, sliderModelList.get(sliderModelList.size() - 2));
            arrangedList.add(1, sliderModelList.get(sliderModelList.size() - 1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));


            SliderAdaptor sliderAdaptor = new SliderAdaptor(arrangedList);
            bannerSliderViewPager.setAdapter(sliderAdaptor);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            bannerSliderViewPager.setCurrentItem(currentPage);//setting banner v16
            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        // PageLooper(sliderModelList);
                    }

                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            //  StartBannerSlideShow(arrangedList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //PageLooper(arrangedList);
                    // StopSlideShow();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //   StartBannerSlideShow(arrangedList);
                    }
                    return false;

                }
            });

        }

        private void PageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }


        }

        private void StartBannerSlideShow(final List<SliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);

                }
            }, DELAY_TIME, PERIOD_TIME);

        }

        private void StopSlideShow() {
            timer.cancel();
        }


    }

    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        //Here we access all views like ImageVew ,Contraint Layout
        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);


        }

        public void setStripAd(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.home_icon)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }


    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
        ////Horizontal Product Layout
        private TextView htitle;
        private Button horizontalLayoutViewAllBtn;
        private RecyclerView horizontalRecycleView;
      private ConstraintLayout constraintLayout2;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            //here we have accessed all views and now we will set data
            htitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalLayoutViewAllBtn = itemView.findViewById(R.id.horizonal_layout_view_all_btn);
            horizontalRecycleView = itemView.findViewById(R.id.horizonal_layout_recycle_view);
            constraintLayout2 = itemView.findViewById(R.id.container_horizontal_product);
            horizontalRecycleView.setRecycledViewPool(recycledViewPool);


        }

        public void setHorizontalProductLayout(List<HorizonalProductScrollModel> horizonalProductScrollModelList, final String title, String color,List<WishListModal>viewAllProductList) {
              htitle.setText(title);
              constraintLayout2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            if (horizonalProductScrollModelList.size() < 8) {
                horizontalLayoutViewAllBtn.setVisibility(View.INVISIBLE);
            } else {
                horizontalLayoutViewAllBtn.setVisibility(View.VISIBLE);
                horizontalLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishListModalList=viewAllProductList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("LAYOUTCODE", 0); // 0 FOR RECYCLEVIEW CODE
                        viewAllIntent.putExtra("title", title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }


            HorizonalProductScrollAdaptor horizonalProductScrollAdaptor = new HorizonalProductScrollAdaptor(horizonalProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecycleView.setLayoutManager(linearLayoutManager);
            horizontalRecycleView.setAdapter(horizonalProductScrollAdaptor);
            horizonalProductScrollAdaptor.notifyDataSetChanged();
            // grid Product Layout View

        }
    }

    // grid view
    public class GridProductViewHolder extends RecyclerView.ViewHolder {
        TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridLayout gridLayout;
        private ConstraintLayout constraintLayout;

        //
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.grid_container);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_viewall_btn);
            gridLayout = itemView.findViewById(R.id.grid_layout);

        }

        public void setGridLayout(List<HorizonalProductScrollModel> horizonalProductScrollModelList, String title, String color) {
            gridLayoutTitle.setText(title);
            constraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            // setting data in grid layout
            // for loop to set value of all 8 children layout of grid layout
            for (int x = 0; x < horizonalProductScrollModelList.size(); x++) {
                ImageView productImage = gridLayout.getChildAt(x).findViewById(R.id.horizontal_product_image);  //
                TextView productTitle = gridLayout.getChildAt(x).findViewById(R.id.horizontal_product_layout_title);  //
                TextView productDesc = gridLayout.getChildAt(x).findViewById(R.id.horizontal_product_description);  //
                TextView productPrice = gridLayout.getChildAt(x).findViewById(R.id.horizontal_product_price);  //
                Glide.with(itemView.getContext()).load(horizonalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.icon_paceholder)).into(productImage);
                productTitle.setText(horizonalProductScrollModelList.get(x).getProductTitle());
                productDesc.setText(horizonalProductScrollModelList.get(x).getGetProductDescription());
                productPrice.setText("Rs. " + horizonalProductScrollModelList.get(x).getProductPrice() + " /-");
                if(!title.equals(""))
                {
                    int finalX = x;
                    gridLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent porductDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                            porductDetailsIntent.putExtra("productID",horizonalProductScrollModelList.get(finalX).getProductID());
                            itemView.getContext().startActivity(porductDetailsIntent);
                        }
                    });
                }



            }
           if(!title.equals(""))
           {
               gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       ViewAllActivity.horizonalProductScrollModelList = horizonalProductScrollModelList;
                       Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                       viewAllIntent.putExtra("LAYOUTCODE", 1); // 1 FOR GRID VIEW ALL CODE
                       viewAllIntent.putExtra("title", title);
                       itemView.getContext().startActivity(viewAllIntent);

                   }
               });
           }
        }
    }

    public class CateGridProductViewHolder extends RecyclerView.ViewHolder {
        TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridLayout gridLayout;
        private ConstraintLayout constraintLayout;

        //
        public CateGridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.category_grid_container);
            gridLayoutTitle = itemView.findViewById(R.id.category_grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.category_grid_view_viewall_btn);
            gridLayout = itemView.findViewById(R.id.category_grid_layout);

        }

        public void setCategoryGridLayout(List<CategoryModel> categoryModelList, String title, String color) {
            gridLayoutTitle.setText("SHOP BY CATEGORY");
            constraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            for (int x = 0; x < 8; x++) {
                ImageView categoryIcon = gridLayout.getChildAt(x).findViewById(R.id.category_icon);  //
                TextView categoryTitle = (TextView) gridLayout.getChildAt(x).findViewById(R.id.category_name);  //
                Glide.with(itemView.getContext()).load(categoryModelList.get(x).getCategoryIconIink()).apply(new RequestOptions().placeholder(R.mipmap.home_icon)).into(categoryIcon);
                categoryTitle.setText(categoryModelList.get(x).getCategorynName());
                String categoryName=categoryModelList.get(x).getCategorynName();
                if(!categoryName.equals("Home"))
                {
                    gridLayout.getChildAt(x).setOnClickListener(v -> {
                        Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                        categoryIntent.putExtra("CategoryName",categoryName); // this way we pass data from one
                        //activity to another CategoryName will act as key
                        itemView.getContext().startActivity(categoryIntent);
                    });
                }



            }
        }

        }


    }

