package com.example.bookzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class My_Account extends AppCompatActivity {
    TextView myUserName,myUserPhone,myUserEmail,mainName,myUserAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__account);
        myUserName=findViewById(R.id.myUserName);
        myUserPhone=findViewById(R.id.myUserPhone);
        myUserEmail=findViewById(R.id.myUserMail);
        mainName=findViewById(R.id.tv_address);
        myUserAddress=findViewById(R.id.myUserAddress);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
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
                myUserName.setText(name);
                myUserEmail.setText(email);
                myUserPhone.setText(phone);
                mainName.setText(name);
                myUserAddress.setText(address);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
