package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.ui.home.HomeFragment;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<CategoryModel> categoryModelList;
    public  CategoryAdapter(List<CategoryModel> categoryModelList){
        this.categoryModelList = categoryModelList;

    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
           String icon = categoryModelList.get(position).getCategoryIconLink();
           String name = categoryModelList.get(position).getCategoryName();
           holder.setCategory(name);

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView categoryIcon;
        private TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);
        }
        private  void setCategoryIcon(){
            //TODO
        }
        private  void setCategory(final String name){
            categoryName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent categoryIntent = new Intent(itemView.getContext(),CategoryActivity.class);
                    categoryIntent.putExtra("CategoryName",name);
                    itemView.getContext().startActivity(categoryIntent);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("CategoryName", name);
//                    Navigation.findNavController(v).navigate(R.id.category_detail,bundle);
                }
            });
        }
    }
}
