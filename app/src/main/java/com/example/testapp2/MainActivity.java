package com.example.testapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public static boolean isHomeScreenReady=false;
    private ProgressBar progressBar;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser!= null) {
            startActivity(new Intent(MainActivity.this, HomeScreenActivity.class));
            SystemClock.sleep(300);
            finish();
        }
        else
        {
            Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }
}

