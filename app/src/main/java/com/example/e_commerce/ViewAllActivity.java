package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridView gridView;
    int layout;
    public static  List<HorizontalScrollModel> horizontalScrollModelList ;
    public static List<MyWishlistModel> myWishlistModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        String title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout = getIntent().getIntExtra("layout", -1);
        if(layout == 0) {

            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            MyWishlistAdapter myWishlistAdapter = new MyWishlistAdapter(myWishlistModelList, false);
            recyclerView.setAdapter(myWishlistAdapter);
            myWishlistAdapter.notifyDataSetChanged();
        }else {
            gridView = findViewById(R.id.grid_view);
            gridView.setVisibility(View.VISIBLE);

            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalScrollModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
//            List<HorizontalScrollModel> horizontalScrollModelList = new ArrayList<>();
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.back_button, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_camera, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_slideshow, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.googleg_standard_color_18, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_gallery, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.submit, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.mipmap.ic_launcher, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.back_button, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_camera, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_slideshow, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.googleg_standard_color_18, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_gallery, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.submit, "Redmi 5A", "Hello", "5999"));
//            horizontalScrollModelList.add(new HorizontalScrollModel(R.mipmap.ic_launcher, "Redmi 5A", "Hello", "5999"));
//            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalScrollModelList);
//            gridView.setAdapter(gridProductLayoutAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}