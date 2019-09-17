package com.example.bookzone;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class user_dashboard extends AppCompatActivity {
    LinearLayoutManager mlinearLayoutManager; //sorting
    SharedPreferences msharedPreferences; //for saving stored settings
    RecyclerView mrecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        //Bottom Navigation
        final  BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
         bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.buy:
                        Intent intent=new Intent(user_dashboard.this,user_dashboard.class);
                        startActivity(intent);
                        break;

                    case R.id.sell:
                        Intent intent1=new Intent(user_dashboard.this,sellBook.class);
                        startActivity(intent1);
                        break;

                    case R.id.internship:
                        Intent intent2=new Intent(user_dashboard.this,internship.class);
                        startActivity(intent2);
                        break;

                    case R.id.others:
                        Intent intent3=new Intent(user_dashboard.this,others.class);
                        startActivity(intent3);
                        break;


                }
//                bottomNavigationView.setSelectedItemId(item.getItemId());
                return true;
            }
        });

        mrecyclerView=findViewById(R.id.myRecyclerview);
        mrecyclerView.setHasFixedSize(true);
        //shared Reference
        msharedPreferences=getSharedPreferences("SortSettings",MODE_PRIVATE);
        String mSorting=msharedPreferences.getString("Sort","newest");//Default
        if(mSorting.equals("newest"))
        {
            mlinearLayoutManager=new LinearLayoutManager(this);
           //This will load from bottom to up
            mlinearLayoutManager.setReverseLayout(true);
            mlinearLayoutManager.setStackFromEnd(true);
        }
        else if(mSorting.equals("oldest"))
        {
            mlinearLayoutManager=new LinearLayoutManager(this);
            //This will load from top to bottom
            mlinearLayoutManager.setReverseLayout(false);
            mlinearLayoutManager.setStackFromEnd(false);
        }


        //Linear Layout
        mrecyclerView.setLayoutManager(mlinearLayoutManager);

        //send Query to Firebase

        mfirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mfirebaseDatabase.getReference("Data");


    }



    //search Data
    private void firebaseSearch(String searchText)
    {
        String query=searchText.toLowerCase();
        Query firebaseSearchQuery =mRef.orderByChild("search").startAt(query).endAt(query+ "\uf8ff");
        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Model, ViewHolder>(
                Model.class,
                R.layout.cardview,
                ViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Model model, int i) {

                viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getAuthor(),model.getPrice(),model.getImage(),model.getDes(),model.getStatus());

            }
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder viewHolder=super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //get data from firebase position clicked
                        String myTitle=getItem(position).getTitle();
                        String myAuthor=getItem(position).getAuthor();
                        String myPrice=getItem(position).getPrice();
                        String myDes=getItem(position).getDes();
                        String myImage=getItem(position).getImage();
                        String myStatus=getItem(position).getStatus();

                        //pass this to new activity
                        Intent intent=new Intent(user_dashboard.this,bookPurchase.class);

                        intent.putExtra("image",myImage);
                        intent.putExtra("title",myTitle);
                        intent.putExtra("author",myAuthor);
                        intent.putExtra("price",myPrice);
                        intent.putExtra("des",myDes);
                        intent.putExtra("status",myStatus);
                        startActivity(intent);


                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //Nothing

                    }
                });
                return viewHolder;
            }
        };
        //set Adapter to Search View
        mrecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate Menu to this layout
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter as you change
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        //handler for item options
        if(id==R.id.action_search)
        {
            return  true;
        }
        if (id==R.id.action_sort)
        {
            //display sort dialoug
            showSortDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        //options to display in dialog box
        String[] sortOptions={"Newest","Oldest"};
        //Create Alert box
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("sort by")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                        {
                            //newest
                            //Edit our shared Preferences
                            SharedPreferences.Editor editor=msharedPreferences.edit();
                            editor.putString("Sort","newest");//sort is key and newest is value
                            editor.apply();
                            recreate();//restart activity to take changes


                        }
                        else if(which==1)
                        {
                            //oldest
                            SharedPreferences.Editor editor=msharedPreferences.edit();
                            editor.putString("Sort","oldest");//sort is key and oldest is value
                            editor.apply();
                            recreate();
                        }
                    }
                });
        builder.show();
    }

    //Load data into recycler view
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Model,ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ViewHolder>(
                Model.class,
                R.layout.cardview,
                ViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Model model, int i) {

                viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getAuthor(),model.getPrice(),model.getImage(),model.getDes(),model.getStatus());

            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder viewHolder=super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListner(new ViewHolder.ClickListner() {
                    @Override
                    public void onItemClick(View view, int position) {


                        //get data from firebase position clicked
                        String myTitle=getItem(position).getTitle();
                        String myAuthor=getItem(position).getAuthor();
                        String myPrice=getItem(position).getPrice();
                        String myDes=getItem(position).getDes();
                        String myImage=getItem(position).getImage();
                        String myStatus=getItem(position).getStatus();

                        //pass this to new activity
                        Intent intent=new Intent(user_dashboard.this,bookPurchase.class);

                        intent.putExtra("image",myImage);
                        intent.putExtra("title",myTitle);
                        intent.putExtra("author",myAuthor);
                        intent.putExtra("price",myPrice);
                        intent.putExtra("des",myDes);
                        intent.putExtra("status",myStatus);
                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //Nothing

                    }
                });
                return viewHolder;
            }
        };
        //set Adapter to Recycle View
        mrecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
}
