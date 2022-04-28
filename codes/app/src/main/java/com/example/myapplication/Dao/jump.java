package com.example.myapplication.Dao;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.home;

public class jump extends AppCompatActivity {
    public void back(String username){
        Intent intent = new Intent(getApplicationContext(), home.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
