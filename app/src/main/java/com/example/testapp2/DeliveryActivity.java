package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddressBtn;
    public  static final int SELECT_ADDRESS=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        List<CartItemModal> cartItemModalList=new ArrayList<>();
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_2,"Redmi K19","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_3,"Redmi K21","Rs.200000/-","Rs.180000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(0,R.mipmap.img_phone_1,"Redmi K20","Rs.20000/-","Rs.18000/-",2,18));
        cartItemModalList.add(new CartItemModal(1,2,"20000","Free","You have saved Rs 3000 on this order","Rs 18000"));
        deliveryRecyclerView=findViewById(R.id.delivery_recyclerview);
        LinearLayoutManager cartmanager=new LinearLayoutManager(this);
        cartmanager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(cartmanager);
        //here we will create and set adaptor
        CartAdaptor cartAdaptor=new CartAdaptor(cartItemModalList);
        deliveryRecyclerView.setAdapter(cartAdaptor);
        cartAdaptor.notifyDataSetChanged();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // setting toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back key
        changeOrAddressBtn=findViewById(R.id.change_edit_address_btn);
        changeOrAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressIntent=new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                // passing data from one activity to another
                addressIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(addressIntent);
            }
        });

    }
    // This is for back arrow on menu bar and onbackpress is for mobile phone back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}