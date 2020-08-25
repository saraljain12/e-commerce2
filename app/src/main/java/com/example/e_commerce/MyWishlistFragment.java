package com.example.e_commerce;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_commerce.DBqueries.currentuser;
import static com.example.e_commerce.DBqueries.firebaseAuth;


public class MyWishlistFragment extends Fragment {

    private RecyclerView recyclerView;
    View view = null;
    public static Dialog SignInDialog;
    private Dialog loadingDialog;
    public static MyWishlistAdapter myWishlistAdapter;

    public MyWishlistFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (currentuser == null) {
            SignInDialog = new Dialog(getContext());
            SignInDialog.setContentView(R.layout.sign_in_dialog);
            SignInDialog.setCancelable(true);
            SignInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button sign_in = SignInDialog.findViewById(R.id.sign_in_btn);
            Button sign_up = SignInDialog.findViewById(R.id.sign_up_btn);
            SignInDialog.setCancelable(true);
            SignInDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Intent home_intent = new Intent(getContext(), MainActivity.class);
                    startActivity(home_intent);
                }
            });
            sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignInDialog.dismiss();
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    Intent login_intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(login_intent);
                    getActivity().finish();
                }
            });
            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignInDialog.dismiss();
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    Intent login_intent = new Intent(getContext(), RegisterActivity.class);
                    startActivity(login_intent);
                    getActivity().finish();

                }
            });
            SignInDialog.show();
        } else {
            ////// loading dialog
            loadingDialog = new Dialog(getContext());
            loadingDialog.setContentView(R.layout.loading_progress_dialog);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingDialog.show();
            //////

            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
            recyclerView = view.findViewById(R.id.wishlist_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            if(DBqueries.wishlistModelList.size() == 0){
                DBqueries.wishList.clear();
                DBqueries.loadWishlist(getContext(), loadingDialog,true);
            }
            else{
                loadingDialog.dismiss();
            }

            myWishlistAdapter = new MyWishlistAdapter(DBqueries.wishlistModelList, true);
            recyclerView.setAdapter(myWishlistAdapter);
            myWishlistAdapter.notifyDataSetChanged();
        }
        return view;
    }



}