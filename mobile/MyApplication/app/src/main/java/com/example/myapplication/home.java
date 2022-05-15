package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class home extends AppCompatActivity {
    private String username;
    String s = "all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("Name");
        ViewFlipper flipper = findViewById(R.id.flipper);
        flipper.startFlipping();
    }
    public void timetable(View view) {
        Intent intent = new Intent(getApplicationContext(),homeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void shopping(View view) {
        Intent intent = new Intent(getApplicationContext(),shopping.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void feedback(View view) {
        Intent intent = new Intent(getApplicationContext(),feedbackActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void UserCenter(View view) {
        Intent intent = new Intent(getApplicationContext(),UserCenter.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void Booking(View view) {
        Intent intent = new Intent(getApplicationContext(),Booking.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        bundle.putString("type",s);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void Logout(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}