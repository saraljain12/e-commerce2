package com.example.e_commerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.e_commerce.DeliveryActivity.SELECT_ADDRESS;
import static com.example.e_commerce.MyAdressesActivity.refreshitem;
import static com.example.e_commerce.TestActivityForDelivery.MANAGE_ADDRESS;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    private int MODE;
    private List<AdressesModel> adressesModelList ;
    private int preselectedposition ;

    public AddressesAdapter(List<AdressesModel> adressesModelList, int Mode) {
        this.adressesModelList = adressesModelList;
        this.MODE = Mode;
        preselectedposition = DBqueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ViewHolder holder, int position) {
        String name = adressesModelList.get(position).getFullname();
        String address = adressesModelList.get(position).getAddress();
        String pincode = adressesModelList.get(position).getPincode();
        Boolean selected = adressesModelList.get(position).getSelectedAddress();
        holder.setData(name,address,pincode,selected,position);
    }

    @Override
    public int getItemCount() {
        return adressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon_view);
            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
            optionContainer = itemView.findViewById(R.id.option_container);
        }
        private void setData(String username, String useraddress, String userpincode, Boolean selected, final int position){
            fullname.setText(username);
            address.setText(useraddress);
            pincode.setText(userpincode);

            if(MODE == SELECT_ADDRESS){
            icon.setImageResource(android.R.drawable.checkbox_on_background);
                if(selected){
                    icon.setVisibility(View.VISIBLE);
                    preselectedposition = position;
                }
                else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preselectedposition != position) {
                            adressesModelList.get(position).setSelectedAddress(true);
                            adressesModelList.get(preselectedposition).setSelectedAddress(false);
                            refreshitem(preselectedposition, position);
                            preselectedposition = position;
                            DBqueries.selectedAddress = position;
                        }
                    }
                });
            }
            else if (MODE == MANAGE_ADDRESS){
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.icons);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshitem(preselectedposition,preselectedposition);
                        preselectedposition = position;
                    }
                });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshitem(preselectedposition,preselectedposition);
                    preselectedposition = -1;
                }
            });

            }
        }
    }
}
