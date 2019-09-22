package com.example.bookzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class bookPurchase extends AppCompatActivity {
    ImageView bookPhoto;
    TextView bookTitleMine,bookAuthorMine,bookPriceMine,bookDesMine,bookStatusMine;
    TextView ownerName,ownerEmail,ownerPhone,ownerAddress;
    Button bookOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_purchase);

        //Action Bar
        ActionBar actionBar=getSupportActionBar();
        //Actionbar Title
        actionBar.setTitle("Book Zone");
        //set back buttons
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Initialise Views
        bookPhoto=findViewById(R.id.bookPicturemine);
        bookTitleMine=findViewById(R.id.myBookTitlemine);
        bookAuthorMine=findViewById(R.id.myBookAuthormine);
        bookPriceMine=findViewById(R.id.myBookPricemine);
        bookDesMine=findViewById(R.id.myBookDescriptionmine);
        bookOrder=findViewById(R.id.orderBook);
        bookStatusMine=findViewById(R.id.myBookStatusmine);
        ownerAddress=findViewById(R.id.myBookOwnerAddress);
        ownerEmail=findViewById(R.id.myBookOwnerEmail);
        ownerPhone=findViewById(R.id.myBookOwnerPhone);
        ownerName=findViewById(R.id.myBookOwnerName);


        //get data from intent
        final String image=getIntent().getStringExtra("image");
        final String title=getIntent().getStringExtra("title");
        final String author=getIntent().getStringExtra("author");
        final String price=getIntent().getStringExtra("price");
        final String des=getIntent().getStringExtra("des");
        final String status=getIntent().getStringExtra("status");
        final String uid=getIntent().getStringExtra("uid");

        //get owner details from database
        String userid=uid;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference bookRef=FirebaseDatabase.getInstance().getReference("Data");
        bookRef.orderByChild("title").equalTo(title);
        String bookid=bookRef.push().getKey();
        System.out.println("raju: "+bookid);
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue().toString();
                String phone=dataSnapshot.child("phone").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String address=dataSnapshot.child("address").getValue().toString();
                System.out.println(email);
                ownerName.setText("Name: "+name);
                ownerEmail.setText("Email: "+email);
                ownerAddress.setText("Address: "+address);
                ownerPhone.setText("Phone: "+phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set Data to views
        bookTitleMine.setText(title);
        bookAuthorMine.setText("by "+author);
        bookPriceMine.setText("\u20B9"+price);
        bookDesMine.setText(des);
        bookStatusMine.setText("Book for "+status);
        Picasso.get().load(image).into(bookPhoto);
        bookOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass data to next activity
                Intent intent=new Intent(bookPurchase.this,buyer_details.class);

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
    //handler on back button

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
