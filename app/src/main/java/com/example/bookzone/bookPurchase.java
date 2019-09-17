package com.example.bookzone;

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

import com.squareup.picasso.Picasso;

public class bookPurchase extends AppCompatActivity {
    ImageView bookPhoto;
    TextView bookTitleMine,bookAuthorMine,bookPriceMine,bookDesMine,bookStatusMine;
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

        //get data from intent
        final String image=getIntent().getStringExtra("image");
        final String title=getIntent().getStringExtra("title");
        final String author=getIntent().getStringExtra("author");
        final String price=getIntent().getStringExtra("price");
        final String des=getIntent().getStringExtra("des");
        final String status=getIntent().getStringExtra("status");

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
