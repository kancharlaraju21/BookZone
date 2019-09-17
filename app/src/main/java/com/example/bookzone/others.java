package com.example.bookzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;

public class others extends AppCompatActivity {
    TextView signout,myAccount,sellBook,internShip,mainName,aboutUs,buyBook;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference ref = database.child("profiles");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        signout=findViewById(R.id.signout);
        myAccount=findViewById(R.id.myAccount);
        sellBook=findViewById(R.id.sellBook);
        mainName=findViewById(R.id.tv_address);
        internShip=findViewById(R.id.internShipMine);
        buyBook=findViewById(R.id.buyBook);
        aboutUs=findViewById(R.id.aboutUs);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
         //       System.out.println(dataSnapshot.getValue().toString());
                String name=dataSnapshot.child("name").getValue().toString();
                mainName.setText(name);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(others.this,login_user.class);
                startActivity(intent);
            }
        });
        //Bottom Navigation
        final BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.others).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.buy:
                        Intent intent=new Intent(others.this,user_dashboard.class);
                        startActivity(intent);
                        break;

                    case R.id.sell:
                        Intent intent1=new Intent(others.this,sellBook.class);
                        startActivity(intent1);
                        break;

                    case R.id.internship:
                        Intent intent2=new Intent(others.this,internship.class);
                        startActivity(intent2);
                        break;

                    case R.id.others:
                        Intent intent3=new Intent(others.this,others.class);
                        startActivity(intent3);
                        break;
                }
//                bottomNavigationView.setSelectedItemId(item.getItemId());
                return true;
            }
        });

        //Myaccount setting
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(others.this,My_Account.class);
                startActivity(intent);
            }
        });

        //Sell Book
        sellBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(others.this,sellBook.class);
                startActivity(intent);
            }
        });
        //Internship
        internShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(others.this,internship.class);
                startActivity(intent);
            }
        });
        buyBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(others.this,user_dashboard.class);
                startActivity(intent);

            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(others.this,aboutUs.class);
                startActivity(intent);
            }
        });



    }


}
