package com.example.bookzone;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class login_user extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email_entered,password_entered;
    TextView create_user,forgot_password,user_gmail;
    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        {
            System.out.println(mAuth.getUid());
            Intent intent = new Intent(login_user.this, user_dashboard.class);
            startActivity(intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_user);
        mAuth = FirebaseAuth.getInstance();
        email_entered=findViewById(R.id.user_email);
        password_entered=findViewById(R.id.user_password);
        create_user=findViewById(R.id.user_create);
        FloatingActionButton login=findViewById(R.id.user_login_button);
        forgot_password=findViewById(R.id.user_forgot_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=email_entered.getText().toString();
                String password=password_entered.getText().toString();
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "Enter Details Properly", Toast.LENGTH_LONG).show();

                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(login_user.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Account Found", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(login_user.this, user_dashboard.class);
                                        startActivity(intent);


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Account Not found", Toast.LENGTH_LONG).show();


                                    }


                                }
                            });
                }

            }
        });
        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(login_user.this, create_user.class);
                startActivity(intent);

            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(login_user.this, user_forgot_password.class);
                startActivity(intent);

            }
        });

    }

}