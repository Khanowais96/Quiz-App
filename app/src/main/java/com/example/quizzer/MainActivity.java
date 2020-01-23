package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button startbtn;
    private Button logout;
    private String[] username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startbtn=findViewById(R.id.start_btn);


        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent category= new Intent(MainActivity.this,CategoriesActivity.class);
                startActivity(category);

            }
        });

        logout= findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent category= new Intent(MainActivity.this,Login.class);
                startActivity(category);
                finish();
            }
        });
    }
}
