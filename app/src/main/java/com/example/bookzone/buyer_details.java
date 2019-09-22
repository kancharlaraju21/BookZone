package com.example.bookzone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class buyer_details extends AppCompatActivity {
    EditText buyer_name,buyer_email,buyer_phone,buyer_address;
    Spinner buyerMethod;
    String method;
  //  private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getSupportActionBar().hide();
        //views
        buyer_name=findViewById(R.id.user_name_buy);
        buyer_email=findViewById(R.id.user_email_buy);
        buyer_phone=findViewById(R.id.user_phone_buy);
        buyer_address=findViewById(R.id.user_address_buy);
        buyerMethod=findViewById(R.id.user_paymentMethod);
        FloatingActionButton create=findViewById(R.id.user_buy_button);

        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Credit Card");
        categories.add("Debit Card");
        categories.add("Cash on Delivery");
        categories.add("Online Payment");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        buyerMethod.setAdapter(dataAdapter);
        // Automatic retrive details of user from firebase

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue().toString());
                String name=dataSnapshot.child("name").getValue().toString();
                String phone=dataSnapshot.child("phone").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String address=dataSnapshot.child("address").getValue().toString();
                buyer_name.setText(name);
                buyer_email.setText(email);
                buyer_phone.setText(phone);
                buyer_address.setText(address);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Buyer Click option Method
        //get book details from intent
        //get data from intent
        final String image=getIntent().getStringExtra("image");
        final String title=getIntent().getStringExtra("title");
        final String author=getIntent().getStringExtra("author");
        final String price=getIntent().getStringExtra("price");
        final String des=getIntent().getStringExtra("des");
        final String status=getIntent().getStringExtra("status");
        final String uid=getIntent().getStringExtra("uid");
        buyerMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                method=categories.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //store data to firebase
//                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//                String order=uid;
//                firebaseDatabase.getReference("Users")
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("Orders")
//                        .child(getRandomString())
//                        .setValue(order);

                //get data
                String bname =buyer_name.getText().toString();
                String bemail=buyer_email.getText().toString();
                String bphone=buyer_phone.getText().toString();
                String baddress=buyer_address.getText().toString();
                String bmethod=method;
                //Intent Data Transfer
                Intent intent=new Intent(buyer_details.this,orderd_screen.class);
                intent.putExtra("bname",bname);
                intent.putExtra("bemail",bemail);
                intent.putExtra("bphone",bphone);
                intent.putExtra("bmethod",bmethod);
                intent.putExtra("baddress",baddress);
                intent.putExtra("image",image);
                intent.putExtra("title",title);
                intent.putExtra("author",author);
                intent.putExtra("price",price);
                intent.putExtra("des",des);
                intent.putExtra("status",status);
                intent.putExtra("uid",uid);
                startActivity(intent);


            }
        });

    }
//    private static String getRandomString()
//    {
//        final Random random=new Random();
//        final StringBuilder sb=new StringBuilder(28);
//        for(int i=0;i<28;++i)
//            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
//        return sb.toString();
//    }
}
