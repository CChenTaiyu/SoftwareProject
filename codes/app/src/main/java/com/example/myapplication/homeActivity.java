package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;

import com.example.myapplication.Dao.CourseDao;
import com.example.myapplication.Dao.jump;
import com.example.myapplication.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity {
    List<Course> courselist = new ArrayList<>();
    private String username;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new Thread(run).start();
        while (courselist.size()==0){
            continue;
        }
        System.out.println(courselist);
        LinearLayout ll=(LinearLayout) findViewById(R.id.event);
        for(Course p:courselist) {
            TextView tv=new TextView(this);
            tv.setText(p.toString());
            tv.setTextSize(18);
            tv.setWidth(600);
            tv.setHeight(150);
            tv.setBackground(getResources().getDrawable(R.drawable.textview_border));
            ll.addView(tv);
        }
    }

    Runnable run  = new Runnable() {
        @Override
        public void run() {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            username = bundle.getString("Name");
            System.out.println("cnmcnm");
            CourseDao cd = new CourseDao();
            courselist = cd.findcourse(username);
        }
    };

    public void time_home(View view) {
        Intent intent = new Intent(getApplicationContext(), home.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}