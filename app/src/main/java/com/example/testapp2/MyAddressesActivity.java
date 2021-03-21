package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.testapp2.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {
    private RecyclerView myAddressesReclerview;
    private Button deliverHereBtn;
    private LinearLayout addNewAddressBtn;
    private TextView noOfAddressSaved;
    private int preSelectedPosition;
    private Dialog loadingDialog;
    private static AddressesAdaptor addressesAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //video13 this will enable back buttoon
        getSupportActionBar().setTitle("My Addresses");
        // loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progess_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        myAddressesReclerview=findViewById(R.id.address_recyclerview);
        deliverHereBtn=findViewById(R.id.deliver_here_btn);
        addNewAddressBtn=findViewById(R.id.add_new_address_btn);
        noOfAddressSaved=findViewById(R.id.number_of_saved_address);
        LinearLayoutManager homePageRecycleViewLayoutManager=new LinearLayoutManager(this);
        homePageRecycleViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesReclerview.setLayoutManager(homePageRecycleViewLayoutManager);
        preSelectedPosition=DBqueries.selectedAddress;
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
        noOfAddressSaved.setText(DBqueries.addressesModalList.size()+"  SAVED ADDRESSES");
        addressesAdaptor=new AddressesAdaptor(DBqueries.addressesModalList,mode);
        myAddressesReclerview.setAdapter(addressesAdaptor);
        ((SimpleItemAnimator)myAddressesReclerview.getItemAnimator()).setSupportsChangeAnimations(false);  // willstop default fade in
        // animation
        addressesAdaptor.notifyDataSetChanged(); // refresh recyler view (entire list)
        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAddress=new Intent(MyAddressesActivity.this,AddAddressActivity.class);
                addAddress.putExtra("INTENT","null");
                startActivity(addAddress);
            }
        });
        deliverHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBqueries.selectedAddress!=preSelectedPosition)
                {
                    loadingDialog.show();
                    int previousAddressIndex=preSelectedPosition;
                    HashMap<String,Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_"+String.valueOf(preSelectedPosition+1),false);
                    updateSelection.put("selected_"+String.valueOf(DBqueries.selectedAddress+1),true);
                    preSelectedPosition=DBqueries.selectedAddress;
                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document(
                            "MY_ADDRESSES").update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                finish();

                            }else
                            {
                                preSelectedPosition=previousAddressIndex;
                                String e2=task.getException().getMessage();
                                Toast.makeText(MyAddressesActivity.this,e2,Toast.LENGTH_SHORT).show();

                            }
                            loadingDialog.dismiss();

                        }
                    });

                }else
                {
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        noOfAddressSaved.setText(DBqueries.addressesModalList.size()+"  SAVED ADDRESSES");
    }

    public static void Refreshitem(int deselect, int select) /// to refresh only selected item of layout
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
            if(DBqueries.selectedAddress!=preSelectedPosition)
            {
                DBqueries.addressesModalList.get(DBqueries.selectedAddress).setSelected(false);
                DBqueries.addressesModalList.get(preSelectedPosition).setSelected(true);
                DBqueries.selectedAddress=preSelectedPosition;
            }
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if(DBqueries.selectedAddress!=preSelectedPosition)
        {
            DBqueries.addressesModalList.get(DBqueries.selectedAddress).setSelected(false);
            DBqueries.addressesModalList.get(preSelectedPosition).setSelected(true);
            DBqueries.selectedAddress=preSelectedPosition;
        }
        super.onBackPressed();
    }
}

