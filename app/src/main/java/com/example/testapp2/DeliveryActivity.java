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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddressBtn;
    public  static final int SELECT_ADDRESS=0;
    private TextView totalAmount;
    private TextView fullNameShip;
    private  TextView fullAdddressShip;
    public static List<CartItemModal>cartItemModalListDeliveryActivity;
    private TextView pincodeShip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        deliveryRecyclerView=findViewById(R.id.delivery_recyclerview);
        totalAmount=findViewById(R.id.totalcartAmount_Tv_delivery);
        LinearLayoutManager cartmanager=new LinearLayoutManager(this);
        cartmanager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(cartmanager);
        //here we will create and set adaptor
        CartAdaptor cartAdaptor=new CartAdaptor(cartItemModalListDeliveryActivity,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdaptor);
        cartAdaptor.notifyDataSetChanged();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // setting toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back key
        changeOrAddressBtn=findViewById(R.id.change_edit_address_btn);
        fullNameShip=findViewById(R.id.full_name_shipping);
        fullAdddressShip=findViewById(R.id.address_shipping);
        pincodeShip=findViewById(R.id.pincode_shipping);
        changeOrAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressIntent=new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                // passing data from one activity to another
                addressIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(addressIntent);
            }
        });
        fullNameShip.setText(DBqueries.addressesModalList.get(DBqueries.selectedAddress).getFullName());
        fullAdddressShip.setText(DBqueries.addressesModalList.get(DBqueries.selectedAddress).getAddress());
        pincodeShip.setText(DBqueries.addressesModalList.get(DBqueries.selectedAddress).getPincode());

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyCartFragment.cartAdaptor.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fullNameShip.setText(DBqueries.addressesModalList.get(DBqueries.selectedAddress).getFullName());
        fullAdddressShip.setText(DBqueries.addressesModalList.get(DBqueries.selectedAddress).getAddress());
        pincodeShip.setText(DBqueries.addressesModalList.get(DBqueries.selectedAddress).getPincode());
    }
}