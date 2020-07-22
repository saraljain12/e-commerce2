package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        List<SliderModel> sliderModelList = new ArrayList<>();
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_gallery, "#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.googleg_standard_color_18, "#ffffff"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher, "#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera, "#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.common_full_open_on_phone, "#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_gallery, "#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.googleg_standard_color_18, "#ffffff"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher, "#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera, "#ffffff"));


        List<HorizontalScrollModel> horizontalScrollModelList = new ArrayList<>();
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.back_button, "Redmi 5A", "Hello", "5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_camera, "Redmi 5A", "Hello", "5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_slideshow, "Redmi 5A", "Hello", "5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.googleg_standard_color_18, "Redmi 5A", "Hello", "5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.ic_menu_gallery, "Redmi 5A", "Hello", "5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.drawable.submit, "Redmi 5A", "Hello", "5999"));
        horizontalScrollModelList.add(new HorizontalScrollModel(R.mipmap.ic_launcher, "Redmi 5A", "Hello", "5999"));


        ///////////////////////
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.mipmap.ic_launcher, "#ffffff"));
        homePageModelList.add(new HomePageModel(2, horizontalScrollModelList, "title"));
        homePageModelList.add(new HomePageModel(3, horizontalScrollModelList, "title"));
        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        NavController navController = Navigation.findNavController(this, R.id.fragment);
//        return NavigationUI.onNavDestinationSelected(item, navController)
//                || super.onOptionsItemSelected(item);
//    }

    //    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
