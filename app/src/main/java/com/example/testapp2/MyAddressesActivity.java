package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.testapp2.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {
    private RecyclerView myAddressesReclerview;
    private Button deliverHereBtn;
    private static AddressesAdaptor addressesAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //video13 this will enable back buttoon
        getSupportActionBar().setTitle("My Addresses");
        myAddressesReclerview=findViewById(R.id.address_recyclerview);
        deliverHereBtn=findViewById(R.id.deliver_here_btn);
        LinearLayoutManager homePageRecycleViewLayoutManager=new LinearLayoutManager(this);
        homePageRecycleViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesReclerview.setLayoutManager(homePageRecycleViewLayoutManager);
        List<AddressesModal> addressesModalList=new ArrayList<>();
        addressesModalList.add(new AddressesModal("Piyush Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",true));
        addressesModalList.add(new AddressesModal("Vijay Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",false));
        addressesModalList.add(new AddressesModal("V. Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",false));
        addressesModalList.add(new AddressesModal("Piyush Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",false));
        addressesModalList.add(new AddressesModal("Piyush Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",false));
        addressesModalList.add(new AddressesModal("Vijay Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",false));
        addressesModalList.add(new AddressesModal("V. Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",false));
        addressesModalList.add(new AddressesModal("Piyush Kumar","560066","40, Pattandur Agrahara, Whitefield, Bengaluru, Karnataka ",false));

         // getting mode (address or select address mode)
        int mode=getIntent().getIntExtra("MODE",-1); //ACCESSING DATA FROM ANOTHER ACTIVITY
        if(mode==SELECT_ADDRESS)
        {
            deliverHereBtn.setVisibility(View.VISIBLE); // come to my address for selecting address for delivering product
            // then set visibility of deliver here on
        }
        else
        {
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdaptor=new AddressesAdaptor(addressesModalList,mode);
        myAddressesReclerview.setAdapter(addressesAdaptor);
        ((SimpleItemAnimator)myAddressesReclerview.getItemAnimator()).setSupportsChangeAnimations(false);  // willstop default fade in
        // animation
        addressesAdaptor.notifyDataSetChanged(); // refresh recyler view (entire list)

    }
    public static void Refreshitem(int deselect,int select) /// to refresh only selected item of layout
    {
        addressesAdaptor.notifyItemChanged(deselect);
        addressesAdaptor.notifyItemChanged(select);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }
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

