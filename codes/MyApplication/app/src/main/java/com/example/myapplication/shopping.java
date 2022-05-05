package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Dao.jump;

public class shopping extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("Name");
    }

    public void time_home(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}