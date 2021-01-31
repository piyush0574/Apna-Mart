package com.example.testapp2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.testapp2.RegisterActivity.setSignUpFragment;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout frameLayout;
    private static  final int HOME_FRAGMENT=0;
    private static  final int CART_FRAGMENT=1;
    private   int CURRENT_FRAGMENT=-1;
    private static  final int MYORDER_FRAGMENT=2;
    private static final  int MYWISHLIST_FRAGMENT=3;
    private static final  int MYACCOUNT_FRAGMENT=4;
    public static boolean showCart=false;


    private DrawerLayout  drawer;
    private NavigationView navigationView ;
    private ImageView actionBarLogo;
    private  NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        actionBarLogo=findViewById(R.id.action_bar_logo);
          setSupportActionBar(toolbar);
        // commented to remove floating button
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
          drawer = findViewById(R.id.drawer_layout);
          navigationView = findViewById(R.id.nav_view);
         frameLayout=findViewById(R.id.main_framelayout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // first item of navtree is selected (video14)
        if(showCart) // this is for cart option in productDetailsActvity
        {
            drawer.setDrawerLockMode(drawer.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("My Cart",new MyCartFragment(),-2);

        }
        else
        {
            //  If we have default fragment as homefragment,then enable the hamburger button and lock app drawer
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                    .setDrawerLayout(drawer)
                    .build();
             navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(0).setChecked(true);
            //
            setFragment(new HomeFragment(),HOME_FRAGMENT);  // default fragment for homeactivity
        }

    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(CURRENT_FRAGMENT==HOME_FRAGMENT)
            {
                CURRENT_FRAGMENT=-1;
                super.onBackPressed();
            }
            else
            {
                if(showCart)
                {
                   showCart=false ;
                   finish();
                }
                else
                {
                    goToFragment("",new HomeFragment(),HOME_FRAGMENT);
                }

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(CURRENT_FRAGMENT==HOME_FRAGMENT)
        {
            getMenuInflater().inflate(R.menu.home_screen, menu);
            getSupportActionBar().setDisplayShowTitleEnabled(false); //video13
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        navigationView =findViewById(R.id.nav_view);
        if(id==R.id.main_search_icon)
            // for search icon
            {
                return  true;
            }

        else if(id==R.id.main_notification_icon)
        {
            // only for testing
            // creating dialog boc
            final Dialog signIndialog=new Dialog(HomeScreenActivity.this);
            signIndialog.setContentView(R.layout.sign_in_dialog_box);
            signIndialog.setCancelable(true);
            signIndialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            Button signInBtn=signIndialog.findViewById(R.id.sign_in_btn_dilaogbox);
            Button signUpBtn=signIndialog.findViewById(R.id.sign_up_btn_dilaogbox);
            Intent registerIntent=new Intent(HomeScreenActivity.this,RegisterActivity.class);
            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSignUpFragment=false;
                    signIndialog.dismiss();
                    startActivity(registerIntent);

                }
            });
            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIndialog.dismiss();
                    setSignUpFragment=true;
                    startActivity(registerIntent);

                }
            });
            signIndialog.show();
            return  true;

        }
        else if(id==R.id.main_cart_icon)
        {
            goToFragment("My cart",new MyCartFragment(),CART_FRAGMENT);
            return true;

        }
        else if(id==android.R.id.home)
        {
            if(showCart)
            {
                showCart=false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }
    private  void goToFragment(String title,Fragment fragment,int FragmentNo)
    {
      if(FragmentNo!=HOME_FRAGMENT)
      {
          getSupportActionBar().setDisplayShowTitleEnabled(true); //video13
          getSupportActionBar().setTitle(title);
          actionBarLogo.setVisibility(View.GONE);
      }
      else
      {
          getSupportActionBar().setDisplayShowTitleEnabled(false); //video13
          actionBarLogo.setVisibility(View.VISIBLE);// My mall Logo

      }
        drawer.closeDrawer(GravityCompat.START,true); // to close drawer
        invalidateOptionsMenu(); // to remove cart and other option from menu bar
        // this will call OncreateMenu again
        setFragment(fragment,FragmentNo);
        if(CURRENT_FRAGMENT==CART_FRAGMENT)
        {

            navigationView.getMenu().getItem(3).setChecked(true);

        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //  This is for side navigation
   // will have to add line number 24 if this method is not auto population
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id=item.getItemId();

        if(id==R.id.nav_myorders)
        // for  icon
        {
            goToFragment("My Order",new MyOrderFragment(),MYORDER_FRAGMENT);
            return true;

        }

//        else if(id==R.id.nav_myrewards)
//        {
//            return  true;
//
//        }
        else if(id==R.id.nav_mycart)
        {
            goToFragment("My cart",new MyCartFragment(),CART_FRAGMENT);
            return true;

        }
        else if(id==R.id.nav_mywishlist)
        {
            goToFragment("My WishList",new MyWishlistFragment(),MYWISHLIST_FRAGMENT);
            return true;

        }
        else if(id==R.id.nav_myaccount)
        {
            goToFragment("My Account",new MyAccountFragment(),MYACCOUNT_FRAGMENT);
            return true;

        }
        else if(id==R.id.nav_mymall)
        {

            goToFragment("",new HomeFragment(),HOME_FRAGMENT);
            return true;

        }
        else if(id==R.id.nav_signout)
        {
            return true;

        }
        return true;


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private  void setFragment(Fragment fragment,int fragmentNo)
    {
        if(fragmentNo!=CURRENT_FRAGMENT)
        {

            CURRENT_FRAGMENT=fragmentNo;
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in_anim,R.anim.fade_out_anim);
            fragmentTransaction.replace(frameLayout.getId(),fragment);
            fragmentTransaction.commit();
        }

    }

}