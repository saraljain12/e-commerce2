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
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /////////////// home fake list

        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));
        sliderModelFakeList.add(new SliderModel(null,"#ffffff"));

        List<HorizontalScrollModel> horizontalScrollModelFakeList = new ArrayList<>();
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));
        horizontalScrollModelFakeList.add(new HorizontalScrollModel("","","","",""));

        homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(3,horizontalScrollModelFakeList,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2,horizontalScrollModelFakeList,"","#ffffff",new ArrayList<MyWishlistModel>()));

        /////////////// home fake list


        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
       adapter = new HomePageAdapter(homePageModelFakeList);
        categoryRecyclerView.setAdapter(adapter);

        if(!DBqueries.parentHashmap.containsKey(title.toUpperCase())){
            DBqueries.parentHashmap.put(title.toUpperCase(),new ArrayList<HomePageModel>());
            DBqueries.loadFragmentData(categoryRecyclerView,this,title.toUpperCase());
        }else{
             adapter = new HomePageAdapter(DBqueries.parentHashmap.get(title.toUpperCase()));

        }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
