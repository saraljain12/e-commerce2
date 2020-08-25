package com.example.e_commerce;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.e_commerce.DBqueries.cartModelList;
import static com.example.e_commerce.DBqueries.cartlist;
import static com.example.e_commerce.DBqueries.currentuser;


public class MyCartFragment extends Fragment {
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    public static LinearLayout cartTotal;
    private TextView totalAmount;
    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().findViewById(R.id.action_bar_logo).setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Cart");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;
    View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        totalAmount = view.findViewById(R.id.total_cart_amount);
        if(currentuser == null ){

        }
        else{


            // Inflate the layout for this fragment

            view = inflater.inflate(R.layout.fragment_my_cart, container, false);

            ////// loading dialog
            loadingDialog = new Dialog(getContext());
            loadingDialog.setContentView(R.layout.loading_progress_dialog);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingDialog.show();
            //////

            cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
            cartTotal =view.findViewById(R.id.linearLayout);
            totalAmount = view.findViewById(R.id.total_cart_amount);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            cartItemsRecyclerView.setLayoutManager(layoutManager);

            if(DBqueries.cartModelList.size() == 0){
                DBqueries.cartlist.clear();
                cartModelList.clear();
                DBqueries.loadCartlist(getContext(), loadingDialog,true, new TextView(getContext()));
            }
            else{

                loadingDialog.dismiss();
            }
//            cartAdapter = new CartAdapter(DBqueries.cartModelList);
//            recyclerView.setAdapter(cartAdapter);
//            cartAdapter.notifyDataSetChanged();
            if(cartModelList.size()==0){
                cartTotal.setVisibility(View.GONE);
            }else{
                cartTotal.setVisibility(View.VISIBLE);
            }

            cartAdapter = new CartAdapter(DBqueries.cartModelList, totalAmount,true);
            cartItemsRecyclerView.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
            continueBtn = view.findViewById(R.id.cart_continue_btn);
            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(view).navigate(R.id.addAddressActivity);
                }
            });
            continueBtn = view.findViewById(R.id.cart_continue_btn);
            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBqueries.loadAddress(getContext());
                }
            });


//       OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//           @Override
//           public void handleOnBackPressed() {
//            Navigation.findNavController(view).navigate(getCallerFragment());
//           }
//        } ;
//        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);
        }
        return view;
    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }


}