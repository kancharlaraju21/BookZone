package com.example.bookzone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int progress_Status=0;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        final RelativeLayout app_menu=(RelativeLayout)findViewById(R.id.app_menu);
        final TextView percentage=findViewById(R.id.textView);
        final ProgressBar progressBar=findViewById(R.id.progressBar2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progress_Status<100){
                    progress_Status+=1;
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress_Status);
                            percentage.setText(progress_Status+"%");
                        }
                    });
                }
                Intent intent=new Intent(MainActivity.this,login_user.class);
                startActivity(intent);
            }
        }).start();
    }
}
