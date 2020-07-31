package com.example.e_commerce;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class ProductSpecificationFragment extends Fragment {



    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productsepcificationrecyclerview;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_specification,container,false);

        productsepcificationrecyclerview = view.findViewById(R.id.product_specification_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productsepcificationrecyclerview.setLayoutManager(linearLayoutManager);
        List<ProductSpecificationModel>  productSpecificationModelList = new ArrayList<>();

        productSpecificationModelList.add(new ProductSpecificationModel(0,"general"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(0,"Display"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(0,"Processor"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel(0,"camera"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));
        productSpecificationModelList.add( new ProductSpecificationModel(1,"Ram","4GB"));




        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productsepcificationrecyclerview.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

        return view;
    }
}