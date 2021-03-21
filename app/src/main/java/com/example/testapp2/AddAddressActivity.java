package com.example.testapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddAddressActivity extends AppCompatActivity {
    private Button saveBtn;
    private EditText city,locality,flatNo,landmark,pincode,mobileNo,fullname,alternateMobile;
    public static Dialog loadingDialog;
    private Spinner stateSpinner;
    private String selectedState;
    private String[]stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar=findViewById(R.id.toolbar);
        stateList=getResources().getStringArray(R.array.india_states);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundResource(R.color.teal_200);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a new address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        city=findViewById(R.id.city);
        landmark=findViewById(R.id.landmark);
        locality=findViewById(R.id.locality);
        flatNo=findViewById(R.id.flatNumber);
        pincode=findViewById(R.id.delivery_pincode);
        mobileNo=findViewById(R.id.mobile_number);
        fullname=findViewById(R.id.name);
        alternateMobile=findViewById(R.id.alternate_mobile_no);
        stateSpinner=findViewById(R.id.state_spinner);
        saveBtn=findViewById(R.id.save_btn);
        // loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progess_dialog);
//        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        // loading dialog
        // for state adaptor
        ArrayAdapter spinnerAdaptor=new ArrayAdapter(this, android.R.layout.simple_spinner_item,stateList);
        spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinnerAdaptor);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedState=stateList[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(city.getText()))
                {
                    if(!TextUtils.isEmpty(locality.getText()))
                    {
                        if(!TextUtils.isEmpty(flatNo.getText()))
                        {
                            if(!(TextUtils.isEmpty(pincode.getText())) && pincode.getText().length()==6)
                            {
                                if(!TextUtils.isEmpty(mobileNo.getText()) && mobileNo.getText().length()==10)
                                {
                                    if(!TextUtils.isEmpty(fullname.getText()))
                                    {
                                        loadingDialog.show();
                                        HashMap<String,Object> addAddressMap = new HashMap<>();

                                        String fullAddress=flatNo.getText().toString()+", "+locality.getText().toString()+", "+landmark.getText().toString()+", "+city.getText().toString()+", "+selectedState+" - "+pincode.getText().toString();
                                        addAddressMap.put("addresslistSize",(long)DBqueries.addressesModalList.size()+1);
                                        addAddressMap.put("fullname_"+String.valueOf((long)DBqueries.addressesModalList.size()+1),fullname.getText().toString());
                                        if(TextUtils.isEmpty(alternateMobile.getText()))
                                        {
                                            addAddressMap.put("mobileNo_"+String.valueOf((long)DBqueries.addressesModalList.size()+1),mobileNo.getText().toString());
                                        }
                                        else
                                        {
                                            addAddressMap.put("mobileNo_"+String.valueOf((long)DBqueries.addressesModalList.size()+1),mobileNo.getText().toString()+", "+alternateMobile.getText().toString());
                                        }

                                        addAddressMap.put("address_"+String.valueOf((long)DBqueries.addressesModalList.size()+1),fullAddress);
                                        addAddressMap.put("selected_"+String.valueOf((long)DBqueries.addressesModalList.size()+1),true);
                                        if(DBqueries.addressesModalList.size()>0)
                                        {
                                            addAddressMap.put("selected_"+(long)(DBqueries.selectedAddress+1),false);
                                        }


                                        FirebaseFirestore.getInstance().collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA")
                                                .document("MY_ADDRESSES")
                                                .update(addAddressMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    if(DBqueries.addressesModalList.size()>0)
                                                    {
                                                        DBqueries.addressesModalList.get(DBqueries.selectedAddress).setSelected(false);
                                                    }
                                                    if(TextUtils.isEmpty(alternateMobile.getText()))
                                                    {
                                                        DBqueries.addressesModalList.add(new AddressesModal(fullname.getText().toString()
                                                                ,mobileNo.getText().toString()
                                                                ,fullAddress,true));
                                                    }
                                                    else
                                                    {
                                                        DBqueries.addressesModalList.add(new AddressesModal(fullname.getText().toString()
                                                                ,mobileNo.getText().toString()+", "+alternateMobile.getText().toString()
                                                                ,fullAddress,true));
                                                    }

                                                    if(getIntent().getStringExtra("INTENT").equals("deliveryIntent"))
                                                    {
                                                        Intent deliveryIntent=new Intent(AddAddressActivity.this,DeliveryActivity.class);
                                                        startActivity(deliveryIntent);
                                                    }
                                                    else
                                                    {
                                                        MyAddressesActivity.Refreshitem(DBqueries.selectedAddress,DBqueries.addressesModalList.size()-1);
                                                    }
                                                    DBqueries.selectedAddress=DBqueries.addressesModalList.size()-1;
                                                    finish();

                                                }else
                                                {
                                                    String e2=task.getException().getMessage();
                                                    Toast.makeText(AddAddressActivity.this,e2,Toast.LENGTH_SHORT).show();

                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });
                                    }else
                                    {
                                        fullname.requestFocus();
                                    }
                                }else
                                {
                                    mobileNo.requestFocus();
                                    Toast.makeText(AddAddressActivity.this,"Please provide valid mobile number",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else
                        {
                            pincode.requestFocus();
                            Toast.makeText(AddAddressActivity.this,"Please provide valid pincode",Toast.LENGTH_SHORT).show();
                        }
                    }else
                    {
                        locality.requestFocus();
                    }

                }else
                {
                    city.requestFocus();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}