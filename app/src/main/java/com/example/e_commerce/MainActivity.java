package com.example.e_commerce;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class  MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //System.out.println(currentUser.getUid().toString());
        if(currentUser == null){
            sendtoStart();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem sign_out = menu.findItem(R.id.nav_sign_out);
        sign_out.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                mAuth = FirebaseAuth.getInstance();
//                mAuth.signOut();
//                Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(login_intent);
//                finishAffinity();
               final Dialog SignInDialog = new Dialog(MainActivity.this);
                SignInDialog.setContentView(R.layout.sign_in_dialog);
                SignInDialog.setCancelable(true);
                SignInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                Button sign_in = SignInDialog.findViewById(R.id.sign_in_btn);
                Button sign_up = SignInDialog.findViewById(R.id.sign_up_btn);
                sign_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SignInDialog.dismiss();
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                       Intent login_intent = new Intent(MainActivity.this,LoginActivity.class);
                       startActivity(login_intent);
                      finishAffinity();
                    }
                });
                sign_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SignInDialog.dismiss();
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Intent login_intent = new Intent(MainActivity.this,RegisterActivity.class);
                        startActivity(login_intent);
                        finishAffinity();
                    }
                });
                SignInDialog.show();
                return false;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_my_order,R.id.nav_my_reward,R.id.nav_home,R.id.myCartFragment,R.id.nav_my_wishlist,R.id.nav_my_account)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id  = item.getItemId();
//        if(id == R.id.main_search_icon){
//
//            return  true;
//        } else if(id == R.id.main_notification_icon){
//            return  true;
//        }else if(id== R.id.main_cart_icon){
//            return  true;
//        }
//        return super.onOptionsItemSelected(item);
//
//    }
   @Override
        public boolean onOptionsItemSelected(MenuItem item) {
          NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
               return NavigationUI.onNavDestinationSelected(item, navController)
                  || super.onOptionsItemSelected(item);
         }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        //System.out.println(currentUser.getUid().toString());
        if(currentUser == null){
            sendtoStart();
        }

    }
    private void sendtoStart() {
        Intent startintent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(startintent);
        finish();
    }
}