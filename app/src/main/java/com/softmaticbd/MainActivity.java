package com.softmaticbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.softmaticbd.activity.NoteActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView ivLogoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ivLogoId = findViewById(R.id.ivLogoId);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), NoteActivity.class));
            }
        },2000);
    }
}