package com.example.bookzone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class orderd_screen extends AppCompatActivity {
    ImageView bookPhoto;
    TextView bookTitleMine,bookAuthorMine,bookPriceMine,bookDesMine,bookStatusMine,book_buyerName,book_buyerEmail,
            book_buyerPhone,book_buyerAddress,book_buyerMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderd_screen);
        //Initialise Views
        bookPhoto=findViewById(R.id.bookPicturemine);
        bookTitleMine=findViewById(R.id.myBookTitlemine);
        bookAuthorMine=findViewById(R.id.myBookAuthormine);
        bookPriceMine=findViewById(R.id.myBookPricemine);
        bookDesMine=findViewById(R.id.myBookDescriptionmine);
        bookStatusMine=findViewById(R.id.myBookStatusmine);
        book_buyerName=findViewById(R.id.myBookDeliveryName);
        book_buyerEmail=findViewById(R.id.myBookDeliveryEmail);
        book_buyerAddress=findViewById(R.id.myBookDeliveryAddress);
        book_buyerPhone=findViewById(R.id.myBookDeliveryPhone);
        book_buyerMethod=findViewById(R.id.myBookDeliveryPayment);

        //get data from intent
        String image=getIntent().getStringExtra("image");
        String title=getIntent().getStringExtra("title");
        String author=getIntent().getStringExtra("author");
        String price=getIntent().getStringExtra("price");
        String des=getIntent().getStringExtra("des");
        String status=getIntent().getStringExtra("status");
        String buyer_name=getIntent().getStringExtra("bname");
        String buyer_email=getIntent().getStringExtra("bemail");
        String buyer_phone=getIntent().getStringExtra("bphone");
        String buyer_address=getIntent().getStringExtra("baddress");
        String buyer_method=getIntent().getStringExtra("bmethod");


        //set Data to views
        bookTitleMine.setText(title);
        bookAuthorMine.setText("by "+author);
        bookPriceMine.setText("\u20B9"+price);
        bookDesMine.setText(des);
        bookStatusMine.setText("Book for "+status);
        Picasso.get().load(image).into(bookPhoto);
        book_buyerName.setText("Name: "+buyer_name);
        book_buyerPhone.setText("Phone: "+buyer_phone);
        book_buyerEmail.setText("Email: "+buyer_email);
        book_buyerAddress.setText("Address: "+buyer_address);
        book_buyerMethod.setText(buyer_method);

    }
}
