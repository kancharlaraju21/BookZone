package com.example.bookzone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class user_forgot_password extends AppCompatActivity {
    TextView signin,signup;
    EditText user_email;
    FloatingActionButton reset_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        signin=findViewById(R.id.user_signin_button);
        signup=findViewById(R.id.user_create);
        user_email=findViewById(R.id.user_email);
        reset_button=findViewById(R.id.user_reset_button);

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_entered=user_email.getText().toString();
                if(!(email_entered.isEmpty())) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email_entered)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Reset Password", "Email sent.");
                                        Toast.makeText(getApplicationContext(),"Reset Link sent to Mail",Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(user_forgot_password.this,login_user.class);
                                        startActivity(intent);

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter Mail Properly",Toast.LENGTH_LONG).show();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_forgot_password.this,login_user.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_forgot_password.this,create_user.class);
                startActivity(intent);

            }
        });

    }
}
