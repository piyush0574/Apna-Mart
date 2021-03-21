package com.example.testapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.testapp2.DeliveryActivity.SELECT_ADDRESS;
import static com.example.testapp2.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.testapp2.MyAddressesActivity.Refreshitem;

public class AddressesAdaptor extends RecyclerView.Adapter<AddressesAdaptor.ViewHolder> {
    private List<AddressesModal>addressesModalList;
    private int MODE;
    private int preSelectedposition;

    public AddressesAdaptor(List<AddressesModal> addressesModalList,int MODE) {
        this.addressesModalList = addressesModalList;
        this.MODE=MODE;
        this.preSelectedposition=DBqueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdaptor.ViewHolder holder, int position)
    {
        String name=addressesModalList.get(position).getFullName();
        String address=addressesModalList.get(position).getAddress();
        String pincode=addressesModalList.get(position).getPincode();
        Boolean selected=addressesModalList.get(position).getSelected();
        holder.setData(name,pincode,address,selected,position);
    }

    @Override
    public int getItemCount() {
        return addressesModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname,pincode,address;
        private ImageView icon;
        private LinearLayout linearLayoutOptionContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname=itemView.findViewById(R.id.name);
            pincode=itemView.findViewById(R.id.pincode_shipping);
            address=itemView.findViewById(R.id.address);
            icon=itemView.findViewById(R.id.icon_view);
            linearLayoutOptionContainer=itemView.findViewById(R.id.edit_remove_address_container);
        }
        private void setData(String fname,String pin,String Ad,Boolean selected,int position)
        {
            fullname.setText(fname);
            pincode.setText(pin);
            address.setText(Ad);
            if(MODE==SELECT_ADDRESS)
            {
                icon.setImageResource(R.drawable.check);
                if(selected)
                {
                    preSelectedposition=position;
                    icon.setVisibility(View.VISIBLE);
                }
                else
                {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if(preSelectedposition!=position)
                      {
                          addressesModalList.get(position).setSelected(true);
                          addressesModalList.get(preSelectedposition).setSelected(false);
                          Refreshitem(preSelectedposition,position);
                          preSelectedposition=position;
                          DBqueries.selectedAddress=position;
                      }

                    }
                });


            }
            else if(MODE==MANAGE_ADDRESS)
            {
                linearLayoutOptionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.vertical_dots);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayoutOptionContainer.setVisibility(View.VISIBLE);
                        Refreshitem(preSelectedposition,preSelectedposition);
                        preSelectedposition=position;
                        DBqueries.selectedAddress=position;

                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Refreshitem(preSelectedposition,preSelectedposition);
                        preSelectedposition=-1;
                    }
                });

            }
        }
    }
}
